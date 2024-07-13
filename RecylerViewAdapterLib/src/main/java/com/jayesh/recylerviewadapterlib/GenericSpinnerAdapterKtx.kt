package com.jayesh.recylerviewadapterlib

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import java.util.ArrayList
/**
 * Use only for Kotlin Generic Spinner Adapter Can be used directly in activity or fragment and also can be Extended
 * <ul> Can be used for list or grid view or anywhere where recycler adapter is used  </ul>
 */
class GenericSpinnerAdapterKtx<T>(
    var context: Context,
    var itemList:List<T>,
    var layout:Int,
    var getViewItem:(position:Int,convertView:View?,parent:ViewGroup)->View,
    var getDropDownViewItem:(position:Int,convertView:View?,parent:ViewGroup)->View,
    var onItemSelected:(position:Int,item:T)->Unit
) : ArrayAdapter<T>(context,layout) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        getViewItem(position, convertView, parent)
        return super.getView(position, convertView, parent)
    }
    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        getDropDownViewItem(position, convertView, parent)
        convertView?.setOnClickListener({
            onItemSelected(position,itemList.get(position))
        })
        return super.getDropDownView(position, convertView, parent)
    }
}