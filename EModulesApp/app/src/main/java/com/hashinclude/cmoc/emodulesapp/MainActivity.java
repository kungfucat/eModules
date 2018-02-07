package com.hashinclude.cmoc.emodulesapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //main RecyclerView will hold the data to show on the MainScreen
    RecyclerView mainRecyclerView;
    DatabaseAdapter databaseAdapter;
    ArrayList<QuestionModel> questionModelArrayList;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainRecyclerView = findViewById(R.id.mainRecyclerView);
        context = this;

        databaseAdapter = new DatabaseAdapter(this);
        questionModelArrayList = databaseAdapter.getAllData();


        MainRecyclerViewAdapter adapter = new MainRecyclerViewAdapter(this, questionModelArrayList);
        mainRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mainRecyclerView.setAdapter(adapter);
        mainRecyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));

        //Added the OnTouchListener
        mainRecyclerView.addOnItemTouchListener(new RowClickedListener(this, mainRecyclerView, new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

//                Sent the intent to SingleQuestionActivity
                Intent intent = new Intent(context, SingleQuestionActivity.class);
                //position+1 as at index 0 we have question 1, so passing 1
                intent.putExtra("position", position+1);
                startActivity(intent);
            }
        }));
    }
}
