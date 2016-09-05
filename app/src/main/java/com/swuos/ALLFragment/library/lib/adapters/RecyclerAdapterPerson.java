package com.swuos.ALLFragment.library.lib.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.swuos.swuassistant.R;

import java.util.List;

/**
 * Created by : youngkaaa on 2016/9/4.
 * Contact me : 645326280@qq.com
 */
public class RecyclerAdapterPerson extends RecyclerView.Adapter<RecyclerAdapterPerson.MyViewHolderPerson> {
    private List<String> vals;
    private List<String> keys;

    public RecyclerAdapterPerson(List<String> keys, List<String> vals){
        this.vals=vals;
        this.keys=keys;
    }

    @Override
    public MyViewHolderPerson onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView= LayoutInflater.from(parent.getContext()).inflate(R.layout.lib_recycler_item_person_view,parent,false);
        return new MyViewHolderPerson(rootView);
    }

    @Override
    public void onBindViewHolder(MyViewHolderPerson holder, int position) {
        holder.textViewKey.setText(keys.get(position));
        holder.textViewVal.setText(vals.get(position));
    }

    @Override
    public int getItemCount() {
        return keys.size();
    }

    public class MyViewHolderPerson extends RecyclerView.ViewHolder{
        private TextView textViewKey;
        private TextView textViewVal;
        public MyViewHolderPerson(View itemView) {
            super(itemView);
            textViewKey= (TextView) itemView.findViewById(R.id.textViewItemPersonKey);
            textViewVal= (TextView) itemView.findViewById(R.id.textViewItemPersonVal);
        }
    }
}
