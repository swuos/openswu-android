package com.swuos.ALLFragment.library.libsearchs.search.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.swuos.ALLFragment.library.libsearchs.search.model.bean.SearchBookItem;
import com.swuos.ALLFragment.library.libsearchs.search.model.bean.SearchResult;
import com.swuos.ALLFragment.library.libsearchs.search.model.douabn.DoubanBookCoverImage;
import com.swuos.swuassistant.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by youngkaaa on 2016/5/25.
 * Email:  645326280@qq.com
 */
public class RecycleAdapterSearch extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<SearchBookItem> searchBookItemList = new ArrayList<>();
    private final DrawableRequestBuilder<DoubanBookCoverImage> imageDrawableRequestBuilder;
    private OnRecyclerItemClickedListener listener;
    private boolean mOpenLoadMore = false;
    private int allBookSize;
    private int FOOTER_VIEW = 1;
    private int ITEM_VIEW = 0;

    /**
     * Instantiates a new Recycle adapter search.
     *
     * @param context the context
     */
    public RecycleAdapterSearch(Context context) {
        this.context = context;
        imageDrawableRequestBuilder = Glide.with(context)
                .from(DoubanBookCoverImage.class)
                .fitCenter()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL) // 设置本地缓存,缓存源文件和目标图像
                .placeholder(R.mipmap.book_cover);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_VIEW) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_activity_item_layout, parent, false);

            return new SearchViewHodler(view);
        } else if (viewType == FOOTER_VIEW) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_footer_view, parent, false);
            return new FooterViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof SearchViewHodler) {
            ((SearchViewHodler) holder).textViewBookName.setText(searchBookItemList.get(position).getBookName());
            //            Glide.with(context)
            //                    .load(searchBookItemList.get(position).getBookCoverUrl())
            //                    .fitCenter()
            //                    .placeholder(R.mipmap.book_cover)
            //                    .error(R.mipmap.book_cover)
            //                    .into(((SearchViewHodler) holder).bookImage);
            DoubanBookCoverImage doubanBookCoverImage = new DoubanBookCoverImage(searchBookItemList.get(position).getISBN());
            doubanBookCoverImage.setId(searchBookItemList.get(position).getISBN());
            imageDrawableRequestBuilder.load(doubanBookCoverImage).into(((SearchViewHodler) holder).bookImage);

            ((SearchViewHodler) holder).textViewBookNumber.setText(searchBookItemList.get(position).getBookNumber());
            ((SearchViewHodler) holder).textViewBookISBN.setText(searchBookItemList.get(position).getISBN());
            ((SearchViewHodler) holder).textViewBooksuoshuhao.setText(searchBookItemList.get(position).getBookSuoshuhao());
            ((SearchViewHodler) holder).textViewBookWriter.setText(searchBookItemList.get(position).getWriter());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(v, position);
                }
            });
        } else if (holder instanceof FooterViewHolder) {
            /**根据是否开启加载更多来决定是否显示加载动画*/
            if (!mOpenLoadMore) {
                holder.itemView.setVisibility(View.INVISIBLE);
            } else
                holder.itemView.setVisibility(View.VISIBLE);


        }
    }


    @Override
    public int getItemCount() {
        return searchBookItemList.size() + 1;
    }

    /**
     * Sets on recycler item click listener.
     *
     * @param listener the listener
     */
    public void setOnRecyclerItemClickListener(OnRecyclerItemClickedListener listener) {
        if (listener != null) {
            this.listener = listener;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isFooterView(position)) {
            return FOOTER_VIEW;
        } else
            return ITEM_VIEW;

    }

    /**
     * 判断是否到达底部
     */
    private boolean isFooterView(int position) {
        return position + 1 == getItemCount();
    }

    /**
     * First add.
     *
     * @param searchResult the search result
     */
    public void firstAdd(SearchResult searchResult) {
        allBookSize = searchResult.getBookSize();

        this.searchBookItemList.clear();
        searchBookItemList.addAll(searchResult.getSearchbookItemList());
        if (searchBookItemList.size() < allBookSize) {
            mOpenLoadMore = true;
        } else {mOpenLoadMore = false;}

        notifyDataSetChanged();
    }

    /**
     * Clear. 清除保存的所有内容,取消加载更多
     */
    public void clear() {
        searchBookItemList.clear();
        mOpenLoadMore = false;
    }

    /**
     * Add more.在加载更多时调用这个方法
     *
     * @param searchResult the search result
     */
    public void addMore(SearchResult searchResult) {
        /**判断是否已经全部加载,否则开启加载更多*/
        if (searchBookItemList.size() < allBookSize) {
            searchBookItemList.addAll(searchResult.getSearchbookItemList());
            if (searchBookItemList.size() < allBookSize) {mOpenLoadMore = true;} else
                mOpenLoadMore = false;
        } else {mOpenLoadMore = false;}
        notifyDataSetChanged();
    }

    /**
     * Ism open load more boolean.判断是否开启加载更多
     *
     * @return the boolean
     */
    public boolean ismOpenLoadMore() {
        return mOpenLoadMore;
    }

    /**
     * The interface On recycler item clicked listener.
     */
    public interface OnRecyclerItemClickedListener {
        /**
         * On item click.
         *
         * @param view     the view
         * @param position the position
         */
        void onItemClick(View view, int position);
    }

    /**
     * The type Search view hodler.
     */
    private class SearchViewHodler extends RecyclerView.ViewHolder {
        private ImageView bookImage;
        private AppCompatTextView textViewBookName;
        private AppCompatTextView textViewBookWriter;
        private AppCompatTextView textViewBooksuoshuhao;
        private AppCompatTextView textViewBookISBN;
        private AppCompatTextView textViewBookNumber;

        private View view;


        /**
         * Instantiates a new Search view hodler.
         *
         * @param itemView the item view
         */
        public SearchViewHodler(View itemView) {
            super(itemView);
            bookImage = (ImageView) itemView.findViewById(R.id.bookimage);
            textViewBookName = (AppCompatTextView) itemView.findViewById(R.id.search_item_bookname);
            textViewBookWriter = (AppCompatTextView) itemView.findViewById(R.id.search_item_bookwriter);
            textViewBooksuoshuhao = (AppCompatTextView) itemView.findViewById(R.id.search_item_booksuoshuhao);
            textViewBookISBN = (AppCompatTextView) itemView.findViewById(R.id.search_item_bookisbn);
            textViewBookNumber = (AppCompatTextView) itemView.findViewById(R.id.search_item_number);
            view = itemView;
        }
    }

    /**
     * The type Footer view holder.
     */
    private class FooterViewHolder extends RecyclerView.ViewHolder {
        private ProgressBar progressBar;

        /**
         * Instantiates a new Footer view holder.
         *
         * @param itemView the item view
         */
        public FooterViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.search_footer_progressBar);
        }
    }
}
