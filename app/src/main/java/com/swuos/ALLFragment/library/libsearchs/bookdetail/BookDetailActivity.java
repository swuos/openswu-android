package com.swuos.ALLFragment.library.libsearchs.bookdetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.swuos.ALLFragment.library.libsearchs.bookdetail.adapter.BookLocationRecycleAdapter;
import com.swuos.ALLFragment.library.libsearchs.bookdetail.api.manager.ServiceManager;
import com.swuos.ALLFragment.library.libsearchs.bookdetail.model.BookLocationInfo;
import com.swuos.ALLFragment.library.libsearchs.bookdetail.model.BookLocationItem;
import com.swuos.ALLFragment.library.libsearchs.search.model.douabn.DoubanBookCoverImage;
import com.swuos.swuassistant.R;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 张孟尧 on 2016/9/7.
 */

public class BookDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG="BookDetailActivity";
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayoutl;
    private BookLocationRecycleAdapter bookLocationRecycleAdapter;
    private RecyclerView recyclerView;
    private ImageView bookcover;
    private TextView writerTextView;
    private TextView suoshuhaoTextView;
    private TextView ISBNTextView;
    private TextView summaryTextView;
    private String title;
    private String writerString;
    private String suoshuhaoString;
    private String ISBNString;
    private String summaryString;
    private String bookCoverUrl;
    private int currentPage;
    private String id;
    private String query;
    private List<BookLocationInfo> locationInfoList = new ArrayList<>();
    private DrawableRequestBuilder<DoubanBookCoverImage> imageDrawableRequestBuilder;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_book_detail);
        getintent();
        bindview();
        imageDrawableRequestBuilder = Glide.with(this)
                .from(DoubanBookCoverImage.class)
                .fitCenter()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL) // 设置本地缓存,缓存源文件和目标图像
                .placeholder(R.mipmap.book_cover);
        initview();
        getLocation(currentPage, id);
    }

    private void bindview() {
        toolbar = (Toolbar) findViewById(R.id.search_book_toolbar);

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back_material);
        toolbar.setNavigationOnClickListener(this);
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white));
        collapsingToolbarLayoutl = (CollapsingToolbarLayout) findViewById(R.id.search_book_collapsingToolabar);
        collapsingToolbarLayoutl.setTitle(title);

        collapsingToolbarLayoutl.setExpandedTitleColor(ContextCompat.getColor(this, R.color.white));
        recyclerView = (RecyclerView) findViewById(R.id.search_book_location_recycleview);
        recyclerView.setNestedScrollingEnabled(false);
        writerTextView = (TextView) findViewById(R.id.search_book_writer);
        suoshuhaoTextView = (TextView) findViewById(R.id.search_book_suoshuhao);
        ISBNTextView = (TextView) findViewById(R.id.search_book_ISBN);
        summaryTextView = (TextView) findViewById(R.id.search_book_summary);
        bookcover = (ImageView) findViewById(R.id.search_book_cover);

    }

    private void initview() {
//        dynamicAddView(collapsingToolbarLayoutl, "CollapsingToolbarLayoutcontent", R.color.colorPrimary);

        writerTextView.setText(writerString);
        suoshuhaoTextView.setText(suoshuhaoString);
        ISBNTextView.setText(ISBNString);
        summaryTextView.setText(summaryString);
        bookLocationRecycleAdapter = new BookLocationRecycleAdapter(this);
        recyclerView.setAdapter(bookLocationRecycleAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DoubanBookCoverImage doubanBookCoverImage = new DoubanBookCoverImage(ISBNString);
        doubanBookCoverImage.setId(ISBNString);
        imageDrawableRequestBuilder.load(doubanBookCoverImage).placeholder(R.mipmap.book_cover).error(R.mipmap.book_cover).crossFade().centerCrop().into(bookcover);
    }

    private void getintent() {
        Intent intent = getIntent();
        title = intent.getStringExtra("bookname");
        writerString = intent.getStringExtra("writer");
        suoshuhaoString = intent.getStringExtra("suoshuhao");
        ISBNString = intent.getStringExtra("ISBN");
        summaryString = intent.getStringExtra("summary");
        currentPage = intent.getIntExtra("currentpage", 0);
        id = intent.getStringExtra("id");
        query = intent.getStringExtra("query");
        bookCoverUrl = intent.getStringExtra("bookCoverUrl");
    }

    @Override
    public void onClick(View v) {
        finish();
    }

    private void getLocation(final int currentPage, final String id) {
        Log.d(TAG,"ID==>"+id);
        ServiceManager.getLocationService().getBookLocation(id, System.currentTimeMillis())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<BookLocationItem>>() {
                    @Override
                    public void onCompleted() {
                        Toast.makeText(BookDetailActivity.this, "onCompleted", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(BookDetailActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(List<BookLocationItem> locationItems) {
                        for(BookLocationItem item:locationItems){
                            Log.d(TAG,"item.getApart()=>"+item.getApart());
                            Log.d(TAG,"item.getLocation()=>"+item.getLocation());
                            Log.d(TAG,"item.getBookstatus()=>"+item.getBookstatus());
                        }
                        bookLocationRecycleAdapter.addItems(locationItems);
                        recyclerView.setAdapter(bookLocationRecycleAdapter);
                    }
                });

    }

}
