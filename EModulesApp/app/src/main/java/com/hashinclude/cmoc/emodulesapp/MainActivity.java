package com.hashinclude.cmoc.emodulesapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    RecyclerView mainRecyclerView;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainRecyclerView = findViewById(R.id.mainRecyclerView);
        context=this;

        MainRecyclerViewAdapter adapter = new MainRecyclerViewAdapter(this);
        mainRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mainRecyclerView.setAdapter(adapter);

        mainRecyclerView.addOnItemTouchListener(new RowClickedListener(this, mainRecyclerView, new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                Log.d("TAG",position+"");

                Intent intent=new Intent(context, SingleQuestionActivity.class);
                intent.putExtra("number", position);
                startActivity(intent);
            }
        }));
    }
}
