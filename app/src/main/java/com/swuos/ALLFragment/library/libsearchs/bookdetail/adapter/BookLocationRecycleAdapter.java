package com.swuos.ALLFragment.library.libsearchs.bookdetail.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.swuos.ALLFragment.library.libsearchs.bookdetail.model.BookLocationInfo;
import com.swuos.swuassistant.R;

import java.util.List;

/**
 * Created by 张孟尧 on 2016/9/8.
 */

public class BookLocationRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<BookLocationInfo> bookLocationInfoList;

    public BookLocationRecycleAdapter(Context context, List<BookLocationInfo> bookLocationInfoList) {
        this.context = context;
        this.bookLocationInfoList = bookLocationInfoList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.search_book_detail_location_item, parent);

        return new BookLocationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((BookLocationViewHolder) holder).libraryTextView.setText(bookLocationInfoList.get(position).getAddress());
        ((BookLocationViewHolder) holder).isOnShelfTextView.setText(bookLocationInfoList.get(position).getFrameState());
        ((BookLocationViewHolder) holder).shelfTextView.setText(bookLocationInfoList.get(position).getFrameState());
    }

    @Override
    public int getItemCount() {
        return bookLocationInfoList.size();
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
