package com.jayesh.recylerviewadapterlib;

import android.content.Context;
import android.database.Cursor;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.SectionIndexer;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;

public class RecylerViewAdapter extends RecyclerView.Adapter implements
        RecyclerView.OnClickListener, SectionIndexer {
    public static final String TAG = RecylerViewAdapter.class.getSimpleName();

    private Integer mLayout = null;
    private LayoutInflater mInflater = null;
    private OnViewCreateListener mOnViewCreateListener = null;
    private HashMap<Integer, OnRowViewClickListener> mViewClickListeners = new HashMap<>();
    private HashMap<String, View> mViewCache = new HashMap<String, View>();
    private Boolean mCacheViews = false;
    private OnViewBindListener mOnViewBindListener = null;
    private BeforeBindUpdateData mBeforeBindUpdateData = null;
    private Context mContext = null;
    private IOnItemClickListener mIOnItemClickListener = null;
    private RecyclerView mListView = null;
    private Boolean hasIndexers = false;
    private String mIndexerColumn = null;
    private HashMap<String, Integer> azIndexers = new HashMap<>();
    private String[] sections = new String[0];
    private List<ODataRow> dataRows;

    public RecylerViewAdapter(Context context, int layout) {
        super();
        mLayout = layout;
        mInflater = LayoutInflater.from(context);
        mContext = context;
    }


    @Override
    public void onClick(View view) {

    }
    public void setOnItemClickListener(IOnItemClickListener listener){
        mIOnItemClickListener=listener;
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }




    private Object getValue(Cursor c, String column) {
        Object value = false;
        int index = c.getColumnIndex(column);
        switch (c.getType(index))
        {
            case Cursor.FIELD_TYPE_NULL:
                value = false;
                break;
            case Cursor.FIELD_TYPE_BLOB:
            case Cursor.FIELD_TYPE_STRING:
                value = c.getString(index);
                break;
            case Cursor.FIELD_TYPE_FLOAT:
                value = c.getFloat(index);
                break;
            case Cursor.FIELD_TYPE_INTEGER:
                value = c.getInt(index);
                break;
        }
        return value;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemView = mInflater.inflate(mLayout,parent,false);
        // Based on the Layout  Create a ViewHolder  with  getting all the ID's
        /*OViewHolder holder = new OViewHolder(itemView, new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
              // TODO ADD LISTENER LOGIC
            }
        });*/
        return new OViewHolder(itemView,mIOnItemClickListener);
    }


    public  class OViewHolder extends RecyclerView.ViewHolder{
        public OViewHolder(View itemView, final RecylerViewAdapter.IOnItemClickListener listener1) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener1!=null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener1.onItemClick(itemView,position,getItem(position));
                        }
                    }
                }
            });
        }
    }

    public void setData(List<ODataRow> products)
    {
        dataRows = products;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final ODataRow row = dataRows.get(position);
        final View view = holder.itemView ;
        if (mBeforeBindUpdateData != null) {
            row.addAll(mBeforeBindUpdateData.updateDataRow(row));
        }

        final View mView = view;
        for (final int id : mViewClickListeners.keySet()) {
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    if (mView.findViewById(id) != null) {
                        mView.findViewById(id).setOnClickListener(
                                new View.OnClickListener() {

                                    @Override
                                    public void onClick(View v) {
                                        OnRowViewClickListener listener = mViewClickListeners
                                                .get(id);
                                        listener.onRowViewClick(position, row, v,
                                                view);
                                    }
                                });
                    } else {
                        Log.i("View @id/",""
                                + mContext.getResources().getResourceEntryName(
                                id) + " not found");
                    }
                }
            }, 100);
        }
        if (mOnViewBindListener != null) {
            mOnViewBindListener.onViewBind(view, row);
        }
    }

    @Override
    public int getItemCount() {
        if(null != dataRows)
        {
            return dataRows.size();
        }
        return 0;
    }


    public void setOnRowViewClickListener(int view_id,
                                          OnRowViewClickListener listener) {
        mViewClickListeners.put(view_id, listener);
    }

    public void setOnViewCreateListener(OnViewCreateListener viewCreateListener) {
        mOnViewCreateListener = viewCreateListener;
    }

    public void setOnViewBindListener(OnViewBindListener bindListener) {
        mOnViewBindListener = bindListener;
    }

    public void setBeforeBindUpdateData(BeforeBindUpdateData updater) {
        mBeforeBindUpdateData = updater;
    }

    public interface OnRowViewClickListener {
        public void onRowViewClick(int position, ODataRow row, View view,
                                   View parent);
    }

    public interface OnViewBindListener {
        public void onViewBind(View view, ODataRow row);
    }

    public interface BeforeBindUpdateData {
        public ODataRow updateDataRow(ODataRow cr);
    }

    public interface OnViewCreateListener {
        public View onViewCreated(Context context, ViewGroup view, Cursor cr,
                                  int position);
    }

    public void handleItemClickListener(RecyclerView absListView, IOnItemClickListener listener) {
        mIOnItemClickListener = listener;
        mListView = absListView;
        if (mListView != null && mIOnItemClickListener != null) {
            mListView.setOnClickListener(this);
        }
    }

    private Boolean mDoubleClick = false;
    private Integer mDoubleClickItemIndex = -1;


    public void setHasSectionIndexers(boolean hasSectionIndexers, String onColumn) {
        hasIndexers = hasSectionIndexers;
        mIndexerColumn = onColumn;
    }

    // Section Indexers
    @Override
    public Object[] getSections() {
        return sections;
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        return azIndexers.get(sections[sectionIndex]);
    }

    @Override
    public int getSectionForPosition(int position) {
        return azIndexers.get(sections[position]);
    }


    public ODataRow getItem(int position)
    {
        return dataRows.get(position);
    }

    public interface IOnItemClickListener {
        public void onItemClick(View view, int position,ODataRow row);
    }


}


