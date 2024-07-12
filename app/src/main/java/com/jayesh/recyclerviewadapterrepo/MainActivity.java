package com.jayesh.recyclerviewadapterrepo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jayesh.recylerviewadapterlib.GenericRecyclerAdapter;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<String> stringlist = new ArrayList<>();
    RecyclerView recyclerView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.rv);
        stringlist.add("Add");
        stringlist.add("Subtract");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        GenericRecyclerAdapter genericRecyclerAdapter = new GenericRecyclerAdapter(stringlist,R.layout.activity_main,true){


            @Override
            protected void onBind(@NonNull RecyclerView.ViewHolder holder, int position,Object item) {
                String itemItem = (String) item;
                TextView textView= holder.itemView.findViewById(R.id.tv);
                textView.setText(itemItem);
            }

            @Override
            protected void onRowClick(int position, Object item) {
                Log.d("Onclicked","position "+position+"\t item"+(String) item);
            }
        };
        recyclerView.setAdapter(genericRecyclerAdapter);
        genericRecyclerAdapter.notifyDataSetChanged();
    }

}