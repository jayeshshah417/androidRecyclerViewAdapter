package com.jayesh.recylerviewadapterlib;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
/**
 * Use only for Java Generic Recycler Adapter Can be used directly in activity or fragment and also can be Extended
 * <ul> Can be used for list or grid view or anywhere where recycler adapter is used  </ul>
 */
public abstract class GenericRecyclerAdapterJava<T> extends RecyclerView.Adapter {
    private int resLayout;
    private boolean hasRowClick;
    private List<T> itemList;
    public GenericRecyclerAdapterJava(List<T> itemList,int layout,boolean hasRowClick){
        this.resLayout = layout;
        this.itemList = itemList;
        this.hasRowClick = hasRowClick;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resLayout, parent, false);
        return new RecyclerView.ViewHolder(view) {};
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        onBind(holder,position,itemList.get(holder.getAdapterPosition()));
        if(hasRowClick){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onRowClick(holder.getAdapterPosition(), itemList.get(holder.getAdapterPosition()),holder.itemView);
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


    protected abstract void onBind(@NonNull RecyclerView.ViewHolder holder, int position,T item);

    protected abstract void onRowClick(int position,T item, View view);
}
