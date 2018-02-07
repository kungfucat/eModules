package com.hashinclude.cmoc.emodulesapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
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
    MainRecyclerViewAdapter adapter;
    Vibrator vibrator;
    public static int REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainRecyclerView = findViewById(R.id.mainRecyclerView);
        context = this;
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        databaseAdapter = new DatabaseAdapter(this);
        questionModelArrayList = databaseAdapter.getAllData();


        adapter = new MainRecyclerViewAdapter(this, questionModelArrayList);
        mainRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mainRecyclerView.setAdapter(adapter);
        mainRecyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));

        //Added the OnTouchListener
        mainRecyclerView.addOnItemTouchListener(new RowClickedListener(this, mainRecyclerView, new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                vibrator.vibrate(40);
//                Sent the intent to SingleQuestionActivity
                Intent intent = new Intent(context, SingleQuestionActivity.class);
                intent.putExtra("position", position);
                questionModelArrayList.get(position).setTimeTaken("00:00");
                startActivityForResult(intent, REQUEST_CODE);
            }

            //flag question on long click
            @Override
            public void onLongItemClick(View view, int position) {
                vibrator.vibrate(70);
                if (questionModelArrayList.get(position).getFlagged() == 0) {
                    questionModelArrayList.get(position).setFlagged(1);
                    //id is 1 index based, but position is 0 based
                    databaseAdapter.updateFlagged(position+1,1);
                } else {
                    questionModelArrayList.get(position).setFlagged(0);
                    //id is 1 index based, but position is 0 based
                    databaseAdapter.updateFlagged(position+1,0);
                }
                adapter.notifyDataSetChanged();
            }
        }));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                int position = data.getIntExtra("currentPosition", 0);
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
//        this is to update for the warning sign, because if i update it just before sending the intent
//        then it looks kind of awkward, so when we come back, onResume will be called and so doing it here
        adapter.notifyDataSetChanged();
    }
}
