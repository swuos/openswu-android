package com.swuos.ALLFragment.library.libsearchs.search;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.swuos.ALLFragment.library.libsearchs.bookdetail.BookDetailActivity;
import com.swuos.ALLFragment.library.libsearchs.search.adapter.RecycleAdapterSearch;
import com.swuos.ALLFragment.library.libsearchs.search.model.bean.SearchResult;
import com.swuos.ALLFragment.library.libsearchs.search.presenter.ILibSearchPresenter;
import com.swuos.ALLFragment.library.libsearchs.search.presenter.LibSearchPresenterCompl;
import com.swuos.ALLFragment.library.libsearchs.search.view.EndLessOnScrollListener;
import com.swuos.ALLFragment.library.libsearchs.search.view.ILibSearchView;
import com.swuos.swuassistant.BaseActivity;
import com.swuos.swuassistant.R;


/**
 * Created by 张孟尧 on 2016/9/4.
 */
public class SearchActity extends BaseActivity implements ILibSearchView, SearchView.OnQueryTextListener, SwipeRefreshLayout.OnRefreshListener, RecycleAdapterSearch.OnRecyclerItemClickedListener, View.OnClickListener {
    private ILibSearchPresenter libSearchPresenter;
    private SearchView searchView;
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private RecycleAdapterSearch recycleAdapterSearch;
    private SwipeRefreshLayout swipeRefreshLayout;
    private EndLessOnScrollListener endLessOnScrollListener;
    private AlertDialog alertDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_actity_layout);
        libSearchPresenter = new LibSearchPresenterCompl(this, this);
        bindview();
        initview();
    }


    private void initview() {
        dynamicAddView(toolbar, "background", R.color.colorPrimary);
        SearchView.SearchAutoComplete textView = (SearchView.SearchAutoComplete) searchView.findViewById(R.id.search_src_text);
        ImageView searchButtonImageView = (ImageView) searchView.findViewById(R.id.search_mag_icon);
        searchButtonImageView.setOnClickListener(this);
        textView.setTextColor(getResources().getColor(R.color.white));
        searchView.setOnQueryTextListener(this);
        searchView.setIconified(false);
        searchView.setIconifiedByDefault(false);
        searchView.requestFocus();

        swipeRefreshLayout.setOnRefreshListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recycleAdapterSearch = new RecycleAdapterSearch(this);
        recyclerView.setAdapter(recycleAdapterSearch);
        recyclerView.setLayoutManager(linearLayoutManager);
        endLessOnScrollListener = new EndLessOnScrollListener(linearLayoutManager, recycleAdapterSearch) {
            @Override
            public void onLoadMore(int currentPage) {
                libSearchPresenter.SearchMore(currentPage);
            }
        };
        recyclerView.addOnScrollListener(endLessOnScrollListener);

        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        alertDialog = new AlertDialog.Builder(this).setSingleChoiceItems(new String[]{"搜索入口1", "搜索入口2"}, libSearchPresenter.getCheckoutSearch(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                libSearchPresenter.checkoutSearch(which);
                alertDialog.cancel();
            }
        }).setCancelable(true).create();
    }

    private void bindview() {
        searchView = (SearchView) findViewById(R.id.searchView);
        recyclerView = (RecyclerView) findViewById(R.id.search_result_recyclerview);
        toolbar = (Toolbar) findViewById(R.id.search_toolbar);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.search_swipeRefresh);
        setSupportActionBar(toolbar);
    }

    @Override
    public void ShowResult(SearchResult searchResult) {
        swipeRefreshLayout.setRefreshing(false);
        recycleAdapterSearch.setOnRecyclerItemClickListener(this);
        recyclerView.setAdapter(recycleAdapterSearch);
        recycleAdapterSearch.firstAdd(searchResult);
        recycleAdapterSearch.notifyDataSetChanged();
    }

    @Override
    public void ShowMore(SearchResult searchResult) {
        recycleAdapterSearch.addMore(searchResult);
        recycleAdapterSearch.notifyDataSetChanged();
    }

    @Override
    public void ShowError(String message) {
        swipeRefreshLayout.setRefreshing(false);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        //        startActivity(new Intent(this,BookDetailActivity.class));
        swipeRefreshLayout.setRefreshing(true);
        endLessOnScrollListener.clean();
        if (!TextUtils.isEmpty(query)) {
            recycleAdapterSearch.clear();
            libSearchPresenter.cancelSearch();
            libSearchPresenter.firstSearch(query);
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {


        return false;
    }

    @Override
    public void onRefresh() {
        String query = searchView.getQuery().toString();
        if (!TextUtils.isEmpty(query)) {
            recycleAdapterSearch.clear();
            libSearchPresenter.cancelSearch();
            libSearchPresenter.firstSearch(query);
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, libSearchPresenter.getSearchBookItemList().get(position).getBookName(), Toast.LENGTH_SHORT).show();
        Intent inten=new Intent(this,BookDetailActivity.class);
        inten.putExtra("bookname",libSearchPresenter.getSearchBookItemList().get(position).getBookName());
        inten.putExtra("writer",libSearchPresenter.getSearchBookItemList().get(position).getWriter());
        inten.putExtra("suoshuhao",libSearchPresenter.getSearchBookItemList().get(position).getBookSuoshuhao());
        inten.putExtra("ISBN",libSearchPresenter.getSearchBookItemList().get(position).getISBN());
        inten.putExtra("summary",libSearchPresenter.getSearchBookItemList().get(position).getSummary());
        startActivity(inten);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.search_mag_icon:
                alertDialog.show();
            default:
                break;
        }
    }
}
