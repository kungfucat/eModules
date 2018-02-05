package com.hashinclude.cmoc.emodulesapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    //main RecyclerView will hold the data to show on the MainScreen
    RecyclerView mainRecyclerView;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainRecyclerView = findViewById(R.id.mainRecyclerView);
        context = this;


        MainRecyclerViewAdapter adapter = new MainRecyclerViewAdapter(this);
        mainRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mainRecyclerView.setAdapter(adapter);
        mainRecyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));


        //Added the OnTouchListener
        mainRecyclerView.addOnItemTouchListener(new RowClickedListener(this, mainRecyclerView, new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                Log.d("TAG",position+"");
//                Sent the intent to SingleQuestionActivity
                Intent intent = new Intent(context, SingleQuestionActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        }));
    }
}
