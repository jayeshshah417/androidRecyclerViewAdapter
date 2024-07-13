package com.jayesh.recylerviewadapterlib

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList
/**
 * Use only for Kotlin Generic Recycler Adapter Can be used directly in activity or fragment and also can be Extended
 * <ul> Can be used for list or grid view or anywhere where recycler adapter is used  </ul>
 */
class GenericRecyclerAdapterKtx<T>(
    var context:Context,
    var itemList:List<T>,
    var layout:Int,
    var onRowClick:(position:Int,item:T,view:View)->Unit ,
    var onBind:(holder:RecyclerView.ViewHolder,position:Int,item:T)->Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val view = LayoutInflater.from(context).inflate(layout,parent,false)
        return object :RecyclerView.ViewHolder(view){}
    }

    override fun getItemCount(): Int {
       return itemList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        onBind(holder,position,itemList.get(position))
        holder.itemView.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                onRowClick(holder.adapterPosition,itemList.get(holder.adapterPosition),holder.itemView)
            }
        })
    }


}