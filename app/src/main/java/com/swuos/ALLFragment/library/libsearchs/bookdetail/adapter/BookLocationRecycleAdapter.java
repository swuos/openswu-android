package com.swuos.ALLFragment.library.libsearchs.bookdetail.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.swuos.ALLFragment.library.libsearchs.bookdetail.model.BookLocationItem;
import com.swuos.swuassistant.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by 张孟尧 on 2016/9/8.
 */

public class BookLocationRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<BookLocationItem> bookLocationInfoList;

    public BookLocationRecycleAdapter(Context context) {
        this.context = context;
        bookLocationInfoList = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_book_detail_location_item, parent, false);

        return new BookLocationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (bookLocationInfoList.size() != 0) {
            ((BookLocationViewHolder) holder).libraryTextView.setText(bookLocationInfoList.get(position).getApartName());
            ((BookLocationViewHolder) holder).isOnShelfTextView.setText(Html.fromHtml(bookLocationInfoList.get(position).getBookstatus()));
            ((BookLocationViewHolder) holder).shelfTextView.setText(bookLocationInfoList.get(position).getLocation());
        }
    }

    @Override
    public int getItemCount() {
        return bookLocationInfoList.size();
    }

    public void addItems(List<BookLocationItem> bookLocationInfo) {
        this.bookLocationInfoList.addAll(bookLocationInfo);
        notifyDataSetChanged();
    }

    private class BookLocationViewHolder extends RecyclerView.ViewHolder {
        private TextView libraryTextView;
        private TextView isOnShelfTextView;
        private TextView shelfTextView;

        public BookLocationViewHolder(View itemView) {
            super(itemView);
            libraryTextView = (TextView) itemView.findViewById(R.id.search_book_location_library);
            isOnShelfTextView = (TextView) itemView.findViewById(R.id.search_book_location_isonshelf);
            shelfTextView = (TextView) itemView.findViewById(R.id.search_book_location_shelf);
        }
    }


}
