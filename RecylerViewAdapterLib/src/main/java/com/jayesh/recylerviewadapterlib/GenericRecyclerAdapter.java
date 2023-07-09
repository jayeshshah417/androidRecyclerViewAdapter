package com.jayesh.recylerviewadapterlib;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public abstract class GenericRecyclerAdapter<T> extends RecyclerView.Adapter {
    private int resLayout;
    private boolean hasRowClick;
    private List<T> itemList;
    public GenericRecyclerAdapter(List<T> itemList,int layout,boolean hasRowClick){
        this.resLayout = layout;
        this.itemList = itemList;
        this.hasRowClick = hasRowClick;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resLayout, parent, false);
        return new GenericCustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        onBind(holder,position);
        if(hasRowClick){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onRowClick(holder.getAdapterPosition(), itemList.get(holder.getAdapterPosition()));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if(itemList!=null) {
            return itemList.size();
        }else{
            return 0;
        }
    }

    private class GenericCustomViewHolder extends RecyclerView.ViewHolder{

        public GenericCustomViewHolder(@NonNull View itemView) {
            super(itemView);
            onViewInit(itemView);
        }
    }

    protected abstract void onBind(@NonNull RecyclerView.ViewHolder holder, int position);
    protected abstract void onViewInit(@NonNull View itemView);
    protected abstract void onRowClick(int position,T item);
}
