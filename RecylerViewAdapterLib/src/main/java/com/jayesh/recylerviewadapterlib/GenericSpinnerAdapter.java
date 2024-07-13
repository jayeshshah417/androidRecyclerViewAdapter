package com.jayesh.recylerviewadapterlib;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.jayesh.recylerviewadapterlib.R;

import java.util.List;

/**
 * Use only for Java Generic Spinner Adapter Can be used directly in activity or fragment and also can be Extended
 * <ul> Can be used for list or grid view or anywhere where recycler adapter is used  </ul>
 */
public abstract class GenericSpinnerAdapter<T> extends ArrayAdapter<T> {
    private Context context;
    private List<T> dataList;
    private int layoutId ;
    public GenericSpinnerAdapter(@NonNull Context context, @NonNull int layout, @NonNull int textViewId, List<T> stringArray) {
        super(context,layout, R.id.text2,stringArray);
        this.context = context;
        this.dataList = stringArray;
        this.layoutId = layout;
    }


    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(layoutId, parent, false);

        return getDataView(position,view,parent,dataList.get(position));
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(layoutId, parent, false);
        return getDropDownDataView(position,view,parent,dataList.get(position));
    }

    public abstract View getDataView(final int position, @Nullable View convertView, @NonNull ViewGroup parent, T item);
    public abstract View getDropDownDataView(final int position, @Nullable View convertView, @NonNull ViewGroup parent, T item);


}
