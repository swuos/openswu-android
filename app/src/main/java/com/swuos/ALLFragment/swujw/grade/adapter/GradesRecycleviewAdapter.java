package com.swuos.ALLFragment.swujw.grade.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.swuos.ALLFragment.swujw.grade.model.GradeItem;
import com.swuos.swuassistant.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 张孟尧 on 2016/10/20.
 */

public class GradesRecycleviewAdapter extends RecyclerView.Adapter<GradesRecycleviewAdapter.MyHolder> {
    List<GradeItem> datas = new ArrayList<>();
    GradesRecycleviewAdapter.OnRecyclerItemClickedListener listener;

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grades_recycleview_item, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, final int position) {

        holder.kmText.setText(datas.get(position).getKcmc());
        holder.cjText.setText(datas.get(position).getCj());
        holder.jdText.setText(datas.get(position).getJd());
        holder.xfText.setText(datas.get(position).getXf());
        holder.ksxzText.setText(datas.get(position).getKsxzText());
        holder.kcxzText.setText(datas.get(position).getKcxzText());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, position, datas.get(position));
            }
        });

    }

    public void addData(List<GradeItem> dataItemList) {
        datas.clear();
        datas.addAll(dataItemList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void setOnRecyclerItemClickListener(GradesRecycleviewAdapter.OnRecyclerItemClickedListener listener) {
        if (listener != null) {
            this.listener = listener;
        }
    }

    public interface OnRecyclerItemClickedListener {
        void onItemClick(View view, int position, GradeItem dataItem);

    }

    class MyHolder extends RecyclerView.ViewHolder {

        TextView kmText;
        TextView cjText;
        TextView jdText;
        TextView xfText;
        TextView ksxzText;
        TextView kcxzText;

        View view;

        MyHolder(View itemView) {
            super(itemView);
            kmText = (TextView) itemView.findViewById(R.id.grades_recycleview_km);
            cjText = (TextView) itemView.findViewById(R.id.grades_recycleview_cj);
            jdText = (TextView) itemView.findViewById(R.id.grades_recycleview_jd);
            xfText = (TextView) itemView.findViewById(R.id.grades_recycleview_xf);
            ksxzText = (TextView) itemView.findViewById(R.id.grades_recycleview_ksxz);
            kcxzText = (TextView) itemView.findViewById(R.id.grades_recycleview_kcxz);

            view = itemView;
        }
    }
}
