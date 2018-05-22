package com.swuos.mobile.adapter.lib;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.swuos.mobile.R;
import com.swuos.mobile.entity.LibBookshelfItem;
import com.swuos.mobile.utils.LibUtils;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by youngkaaa on 2018/5/20
 */
public class LibBookshelfAdapter extends RecyclerView.Adapter<LibBookshelfAdapter.LibBookshelfViewHolder> {
    private List<LibBookshelfItem> mItems;
    private Context context;

    public LibBookshelfAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<LibBookshelfItem> items) {
        // 我的书架  不分页  一次性全部取回来 所以不牵扯拼接
        mItems = items;
        notifyDataSetChanged();
    }

    @Override
    public LibBookshelfViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.v_lib_bookshelf_item, parent, false);
        return new LibBookshelfViewHolder(root);
    }

    @Override
    public void onBindViewHolder(LibBookshelfViewHolder holder, int position) {
        LibBookshelfItem item = mItems.get(position);
        String bookBg = LibUtils.getBookBg(position);
        if (TextUtils.isEmpty(bookBg)) {
            bookBg = LibUtils.COLORS[0];
        }
        if (holder.itemView instanceof CardView) {
            ((CardView) holder.itemView).setCardBackgroundColor(Color.parseColor(bookBg));
        }

        Glide.with(holder.itemView).load(item.thumb).into(holder.mIvIcon);

        holder.mTvBookname.setText(item.bookName);
        holder.mTvAuthor.setText(item.author);
        String backTime = LibUtils.getBackTime(context, item);
        if (TextUtils.isEmpty(backTime)) {
            holder.mTvBackTime.setVisibility(View.GONE);
        } else {
            holder.mTvBackTime.setVisibility(View.VISIBLE);
            holder.mTvBackTime.setText(backTime);
        }
        SpannableString remainDay = LibUtils.getBookRemainDay(holder.itemView.getContext(), item);
        if (TextUtils.isEmpty(remainDay)) {
            holder.mTvRemain.setVisibility(View.GONE);
        } else {
            holder.mTvRemain.setVisibility(View.VISIBLE);
            holder.mTvRemain.setText(remainDay);
        }
    }

    @Override
    public int getItemCount() {
        return mItems == null ? 0 : mItems.size();
    }

    class LibBookshelfViewHolder extends RecyclerView.ViewHolder {
        private ImageView mIvIcon;
        private TextView mTvBookname;
        private TextView mTvAuthor;
        private TextView mTvBackTime;
        private View mBtnRenew;
        private TextView mTvRemain;

        public LibBookshelfViewHolder(View itemView) {
            super(itemView);
            mIvIcon = itemView.findViewById(R.id.iv_lib_bookshelf_icon);
            mTvBookname = itemView.findViewById(R.id.tv_lib_bookshelf_name);
            mTvAuthor = itemView.findViewById(R.id.tv_lib_bookshelf_author);
            mTvBackTime = itemView.findViewById(R.id.tv_lib_bookshelf_back_date);
            mBtnRenew = itemView.findViewById(R.id.tv_lib_bookshelf_renew);
            mTvRemain = itemView.findViewById(R.id.tv_lib_bookshelf_remain);
        }
    }
}
