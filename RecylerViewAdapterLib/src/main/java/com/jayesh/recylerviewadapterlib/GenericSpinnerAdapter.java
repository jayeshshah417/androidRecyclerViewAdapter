package app.imps.Utilities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import app.imps.R;

public abstract class GenericSpinnerAdapter<T> extends ArrayAdapter<T> {
    private Context context;
    private List<T> dataList;
    private int layoutId ;
    public GenericSpinnerAdapter(@NonNull Context context, @NotNull int layout, @NonNull int textViewId, List<T> stringArray) {
        super(context,layout,R.id.text1,stringArray);
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
