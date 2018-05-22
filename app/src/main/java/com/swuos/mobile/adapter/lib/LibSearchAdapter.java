package com.swuos.mobile.adapter.lib;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.swuos.mobile.R;
import com.swuos.mobile.entity.LibSearchItem;

import java.util.List;

/**
 * Created by youngkaaa on 2018/5/21
 */
public class LibSearchAdapter extends RecyclerView.Adapter<LibSearchAdapter.LibSearchViewHolder> {
    private List<LibSearchItem> mItems;

    /**
     * 设置刷新列表数据  搜索结果不分页 一次性全部返回
     * @param items
     */
    public void setData(List<LibSearchItem> items) {
        if (mItems == null) {
            mItems = items;
        } else {
            mItems.addAll(items);
        }
        notifyDataSetChanged();
    }

    @Override
    public LibSearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.v_lib_search_item, parent, false);
        return new LibSearchViewHolder(root);
    }

    @Override
    public void onBindViewHolder(LibSearchViewHolder holder, int position) {
        LibSearchItem item = mItems.get(position);
        holder.tvBookname.setText(item.bookName);
        holder.tvAuthor.setText(item.author);
        holder.tvIsbn.setText(item.isbn);
        holder.tvRemainNum.setText(item.remainNum);
        holder.tvPublisher.setText(item.publisher);
    }

    @Override
    public int getItemCount() {
        return mItems == null ? 0 : mItems.size();
    }

    class LibSearchViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivIcon;
        private TextView tvBookname;
        private TextView tvAuthor;
        private TextView tvIsbn;
        private TextView tvRemainNum;
        private TextView tvPublisher;

        public LibSearchViewHolder(View itemView) {
            super(itemView);

            ivIcon = itemView.findViewById(R.id.iv_lib_icon);
            tvBookname = itemView.findViewById(R.id.tv_lib_item_book_name);
            tvAuthor = itemView.findViewById(R.id.tv_lib_item_author);
            tvIsbn = itemView.findViewById(R.id.tv_lib_item_isbn);
            tvRemainNum = itemView.findViewById(R.id.tv_lib_item_remain_num);
            tvPublisher = itemView.findViewById(R.id.tv_lib_item_publisher);
        }
    }
}
