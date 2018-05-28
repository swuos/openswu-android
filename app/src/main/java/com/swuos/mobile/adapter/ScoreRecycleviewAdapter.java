package com.swuos.mobile.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.swuos.mobile.R;
import com.swuos.mobile.entity.ScoreItem;


import java.util.ArrayList;


public class ScoreRecycleviewAdapter extends RecyclerView.Adapter<ScoreRecycleviewAdapter.ScoreHolder> {
    ArrayList<ScoreItem> sourceScoreArrayList = new ArrayList<>();
    ArrayList<ScoreItem> showScoreArrayList = new ArrayList<>();

    private int min;
    private int max;
    private String filterSubjectContent;

    @Override
    public ScoreHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.v_score_item, parent, false);
        return new ScoreHolder(view);
    }

    @Override
    public void onBindViewHolder(ScoreHolder holder, final int position) {

        holder.lesson_name.setText(showScoreArrayList.get(position).getLessonName());
        holder.lesson_property.setText(showScoreArrayList.get(position).getLessonType());
        holder.lesson_credit.setText(showScoreArrayList.get(position).getCredit());
        holder.lesson_grade.setText(showScoreArrayList.get(position).getGradePoint());
        holder.lesson_point.setText(showScoreArrayList.get(position).getScore());
        holder.lesson_point.setText(showScoreArrayList.get(position).getScore());
        if (position % 2 == 0) {
            holder.lesson_name.setBackgroundColor(0xffeaf1ff);
            holder.lesson_property.setBackgroundColor(0xffeaf1ff);
            holder.lesson_credit.setBackgroundColor(0xffeaf1ff);
            holder.lesson_grade.setBackgroundColor(0xffeaf1ff);
            holder.lesson_point.setBackgroundColor(0xffeaf1ff);
            holder.lesson_point.setBackgroundColor(0xffeaf1ff);
        } else {
            holder.lesson_name.setBackgroundColor(0xfff0f7ff);
            holder.lesson_property.setBackgroundColor(0xfff0f7ff);
            holder.lesson_credit.setBackgroundColor(0xfff0f7ff);
            holder.lesson_grade.setBackgroundColor(0xfff0f7ff);
            holder.lesson_point.setBackgroundColor(0xfff0f7ff);
            holder.lesson_point.setBackgroundColor(0xfff0f7ff);
        }
    }

    public void changeFilter() {
        if (sourceScoreArrayList != null && sourceScoreArrayList.size() != 0) {
            showScoreArrayList.clear();
            for (ScoreItem scoreItem : sourceScoreArrayList) {
                if (filterSubjectContent.contains(scoreItem.getLessonType())) {
                    if (Integer.parseInt(scoreItem.getScore()) >=min&&Integer.parseInt(scoreItem.getScore())<=max)
                    {
                        showScoreArrayList.add(scoreItem);
                    }
                }
            }
            notifyDataSetChanged();
        }
    }

    public void setScoreInterval(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public void setSubject(String filterSubjectContent) {
        this.filterSubjectContent = filterSubjectContent;
    }

    public void addData(ArrayList<ScoreItem> arrayList) {
        this.sourceScoreArrayList.clear();
        this.sourceScoreArrayList.addAll(arrayList);
        this.showScoreArrayList.clear();
        this.showScoreArrayList.addAll(arrayList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return showScoreArrayList.size();
    }

//    public void setOnRecyclerItemClickListener(ScoreRecycleviewAdapter.OnRecyclerItemClickedListener listener) {
//        if (listener != null) {
//            this.listener = listener;
//        }
//    }
//
//    public interface OnRecyclerItemClickedListener {
//        void onItemClick(View view, int position, GradeItem dataItem);
//
//    }

    class ScoreHolder extends RecyclerView.ViewHolder {

        TextView lesson_name;
        TextView lesson_property;
        TextView lesson_credit;
        TextView lesson_grade;
        TextView lesson_point;

        View view;

        ScoreHolder(View itemView) {
            super(itemView);
            lesson_name = (TextView) itemView.findViewById(R.id.lesson_name);
            lesson_property = (TextView) itemView.findViewById(R.id.lesson_property);
            lesson_credit = (TextView) itemView.findViewById(R.id.lesson_credit);
            lesson_grade = (TextView) itemView.findViewById(R.id.lesson_grade);
            lesson_point = (TextView) itemView.findViewById(R.id.lesson_point);
            view = itemView;
        }
    }
}