package com.gallops.mobile.jmvclibrary.view;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jianyuyouhun.inject.ViewInjector;

import java.util.ArrayList;
import java.util.List;

/**
 * recyclerView适配器
 * Created by wangyu on 2018/4/3.
 */

public abstract class SimpleRecyclerAdapter <T, VH extends SimpleRecyclerAdapter.ViewHolder> extends RecyclerView.Adapter<VH> {
    protected Context context;
    private List<T> data;

    public SimpleRecyclerAdapter(Context context) {
        this.context = context;
        this.data = new ArrayList<>();
    }

    public void setData(List<T> data) {
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    public void addData(List<T> data) {
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    public void addData(T data) {
        this.data.add(data);
        notifyDataSetChanged();
    }

    public void removeData(T data) {
        this.data.remove(data);
        notifyDataSetChanged();
    }

    public void removeData(List<T> data) {
        this.data.removeAll(data);
        notifyDataSetChanged();
    }

    public List<T> getData() {
        return data;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public T getItem(int pos) {
        return data.get(pos);
    }

    @Deprecated
    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(getLayoutId(), parent, false);
        return onCreateViewHolder(view, viewType);
    }

    @LayoutRes
    protected abstract int getLayoutId();

    public abstract VH onCreateViewHolder(View view, int viewType);

    @Deprecated
    @Override
    public void onBindViewHolder(VH holder, int position) {
        onBindViewHolder(holder, getItem(position), position);
    }

    public abstract void onBindViewHolder(VH holder, T data, int position);

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
            ViewInjector.inject(this, itemView);
        }
    }
}

