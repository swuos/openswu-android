package com.swuos.ALLFragment.library.lib.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.swuos.ALLFragment.library.lib.model.BookBean2;
import com.swuos.ALLFragment.library.lib.utils.ParserInfo;
import com.swuos.swuassistant.Constant;
import com.swuos.swuassistant.R;

import java.util.List;

/**
 * Created by youngkaaa on 2016/5/27.
 * Email:  645326280@qq.com
 */
public class RecyclerAdapterLibMain extends RecyclerView.Adapter<RecyclerAdapterLibMain.LibMainViewHolder> {

    private List<BookBean2> bookBeen2;
    private Context mContext;
    private OnRecyclerItemClickedListener listener;

    public interface OnRecyclerItemClickedListener {
        void onClicked(int vId, int position);
    }

    public RecyclerAdapterLibMain(Context context, List<BookBean2> bookItems) {
        this.mContext = context;
        this.bookBeen2 = bookItems;
    }

    @Override
    public LibMainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lib_recycler_item, parent, false);

        return new LibMainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LibMainViewHolder holder, final int position) {
        holder.textViewBookName.setText(bookBeen2.get(position).getBookName());
        Bundle bundle = ParserInfo.makeBorrowInfoToBundle(bookBeen2.get(position));
        String latestOpTime = bundle.getString(Constant.LIB_BOOK_LATEST_OP_TIME);
        String firstOpTime = bundle.getString(Constant.LIB_BOOK_FIRST_OP_TIME);
        String latestOpKind = bundle.getString(Constant.LIB_BOOK_LATEST_OP_KIND);
        String firstOpKind = bundle.getString(Constant.LIB_BOOK_FIRST_OP_KIND);

        holder.textViewBarCode.setText("条形码:" + bookBeen2.get(position).getBarCode());

        if (latestOpKind.equals("还书")) {
            holder.textViewBookTime.setText(firstOpTime + " - " + latestOpTime);
            holder.textViewBacked.setVisibility(View.VISIBLE);
            holder.textViewBacked.setText("已还");
            holder.buttonRenew.setVisibility(View.GONE);
        } else if (latestOpKind.equals("预约")) {
            holder.textViewBookTime.setText(firstOpTime + " - " + latestOpTime);
            holder.textViewBacked.setVisibility(View.VISIBLE);
            holder.textViewBacked.setText("预约");
            holder.buttonRenew.setVisibility(View.GONE);
        } else {
            holder.textViewBookTime.setText(firstOpTime + " - " + "now");
            holder.textViewBacked.setVisibility(View.GONE);
            holder.buttonRenew.setVisibility(View.VISIBLE);
            holder.buttonRenew.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClicked(v.getId(), position);
                }
            });
        }
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClicked(view.getId(), position);
            }
        });
    }

    public void setRecyclerItemClickedListener(OnRecyclerItemClickedListener listener) {
        if (listener != null) {
            this.listener = listener;
        }
    }

    @Override
    public int getItemCount() {
        return bookBeen2.size();
    }

    public class LibMainViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewBookName;
        private TextView textViewBookTime;
        private TextView textViewBarCode;
        private TextView textViewBacked;
        private Button buttonRenew;
        private LinearLayout linearLayout;

        public LibMainViewHolder(View itemView) {
            super(itemView);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayoutRecyclerItem);
            textViewBookName = (TextView) itemView.findViewById(R.id.textViewRecyclerLibBookName);
            textViewBookTime = (TextView) itemView.findViewById(R.id.textViewRecyclerLibTime);
            textViewBarCode = (TextView) itemView.findViewById(R.id.textViewRecyclerBarCode);
            buttonRenew = (Button) itemView.findViewById(R.id.btnRecyclerLibRenew);
            textViewBacked = (TextView) itemView.findViewById(R.id.textViewRecyclerLibBacked);
        }
    }
}
