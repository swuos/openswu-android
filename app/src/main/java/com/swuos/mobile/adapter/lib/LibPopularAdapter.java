package com.swuos.mobile.adapter.lib;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.swuos.mobile.R;
import com.swuos.mobile.entity.LibPopularItem;

import java.util.List;

/**
 * Created by youngkaaa on 2018/5/19
 */
public class LibPopularAdapter extends RecyclerView.Adapter<LibPopularAdapter.LibPopularViewHolder> {
    private Context mContext;
    private List<LibPopularItem> mItems;

    public LibPopularAdapter(Context context) {
        mContext = context;
    }

    /**
     * 设置列表数据
     *
     * @param items
     * @param autoApend 是否自动拼接到最后 false的话就清空原先的
     */
    public void setData(List<LibPopularItem> items, boolean autoApend) {
        if (mItems == null) {
            mItems = items;
        } else {
            if (!autoApend) {
                mItems.clear();
            }
            mItems.addAll(items);
        }
        notifyDataSetChanged();
    }


    @Override
    public LibPopularViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(mContext).inflate(R.layout.v_lib_popular_item, parent, false);
        return new LibPopularViewHolder(root);
    }

    @Override
    public void onBindViewHolder(LibPopularViewHolder holder, int position) {
        LibPopularItem item = mItems.get(position);
        if (item == null) {
            return;
        }
        holder.tvBookname.setText(item.bookName);
        holder.tvAuthor.setText(item.author);
        holder.tvSummary.setText(item.summary);
    }

    @Override
    public int getItemCount() {
        return mItems == null ? 0 : mItems.size();
    }

    protected static class LibPopularViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView ivIcon;
        private TextView tvBookname;
        private TextView tvAuthor;
        private TextView tvSummary;

        public LibPopularViewHolder(View itemView) {
            super(itemView);
            ivIcon = itemView.findViewById(R.id.iv_lib_icon);
            tvBookname = itemView.findViewById(R.id.tv_lib_item_book_name);
            tvAuthor = itemView.findViewById(R.id.tv_lib_item_author);
            tvSummary = itemView.findViewById(R.id.tv_lib_item_summary);


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            // TODO: 2018/5/22 jump
        }
    }
}
