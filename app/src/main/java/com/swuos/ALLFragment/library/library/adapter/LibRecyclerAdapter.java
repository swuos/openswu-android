package com.swuos.ALLFragment.library.library.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.swuos.ALLFragment.library.library.event.OnItemClickedListener;
import com.swuos.ALLFragment.library.library.model.BookItem;
import com.swuos.swuassistant.R;

import java.util.List;
import java.util.Map;

/**
 * Created by : youngkaaa on 2016/10/27.
 * Contact me : 645326280@qq.com
 */

public class LibRecyclerAdapter extends MultiBaseRecyclerAdapter<String, BookItem> {
    private List<String> titles;
    private List<BookItem> bookItems;
    private OnItemClickedListener mItemClickedListener;

    public LibRecyclerAdapter(Context context, List<String> keys, Map<String, List<BookItem>> data) {
        super(context, keys, data);
    }

    @Override
    public int getTitleLayoutId() {
        return R.layout.recycler_title_layout;
    }

    @Override
    public int getContentLayoutId() {
        return R.layout.recycler_content_layout;
    }

    @Override
    public void bindData(BaseViewHolder holder, ItemResult result) {
        if (result.getType() == TYPE_TITLE) {
            final String title = (String) result.getData();
            TextView textView = holder.getViewById(R.id.textViewRecyclerTitle);
            textView.setText((String) result.getData());
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mItemClickedListener != null) {
                        mItemClickedListener.onClick(TYPE_TITLE, title, v);
                    }
                }
            });
        } else {
            final BookItem item = (BookItem) result.getData();
            ImageView circleView=holder.getViewById(R.id.imageViewRecyclerIcon);
            TextView textViewBookName = holder.getViewById(R.id.textViewRecyclerBookName);
            TextView textViewTime = holder.getViewById(R.id.textViewRecyclerTime);
            TextView textViewISBN=holder.getViewById(R.id.textViewRecyclerISBN);
            textViewBookName.setText(item.getBookName());
            textViewTime.setText(item.getTime());
            textViewISBN.setText(item.getBookIsbn());
            if(item.getKind().equals("借书")){
                circleView.setImageResource(R.drawable.borrow);
            }else if(item.getKind().equals("还书")){
                circleView.setImageResource(R.drawable.back);
            }else if(item.getKind().equals("续借")){
                circleView.setImageResource(R.drawable.retry);
            }

            holder.getViewById(R.id.cardViewRecyclerContent).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mItemClickedListener != null) {
                        mItemClickedListener.onClick(TYPE_TITLE, item, v);
                    }
                }
            });
            if(result.getBorder()==TOP_BORDER){
                holder.getViewById(R.id.viewRecyclerLeftTop).setVisibility(View.INVISIBLE);
                holder.getViewById(R.id.viewRecyclerRightBottom).setVisibility(View.VISIBLE);
                holder.getViewById(R.id.viewRecyclerLeftBottom).setVisibility(View.VISIBLE);
            }else if(result.getBorder()==BOTTOM_BORDER){
                holder.getViewById(R.id.viewRecyclerLeftBottom).setVisibility(View.INVISIBLE);
                holder.getViewById(R.id.viewRecyclerRightBottom).setVisibility(View.INVISIBLE);
                holder.getViewById(R.id.viewRecyclerLeftTop).setVisibility(View.VISIBLE);
            }else {
                holder.getViewById(R.id.viewRecyclerRightBottom).setVisibility(View.VISIBLE);
                holder.getViewById(R.id.viewRecyclerLeftTop).setVisibility(View.VISIBLE);
                holder.getViewById(R.id.viewRecyclerLeftBottom).setVisibility(View.VISIBLE);
            }
        }
    }

    public void setItemClickedListener(OnItemClickedListener itemClickedListener) {
        mItemClickedListener = itemClickedListener;
    }
}
