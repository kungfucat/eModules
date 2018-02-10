package com.hashinclude.cmoc.emodulesapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import xyz.danoz.recyclerviewfastscroller.vertical.VerticalRecyclerViewFastScroller;

public class MainActivity extends AppCompatActivity {

    //main RecyclerView will hold the data to show on the MainScreen
    RecyclerView mainRecyclerView;
    VerticalRecyclerViewFastScroller fastScroller;
    DatabaseAdapter databaseAdapter;
    ArrayList<QuestionModel> questionModelArrayList;
    Context context;
    MainRecyclerViewAdapter adapter;
    Vibrator vibrator;
    public static int REQUEST_CODE = 100;

    TextView correctTextView, incorrectTextView, unattemptedTextView;
    int countCorrect, countIncorrect, countUnattempted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mainRecyclerView = findViewById(R.id.mainRecyclerView);
        context = this;
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        databaseAdapter = new DatabaseAdapter(this);
        questionModelArrayList = databaseAdapter.getAllData();

        correctTextView = findViewById(R.id.numberOfCorrect);
        incorrectTextView = findViewById(R.id.numberOfIncorrect);
        unattemptedTextView = findViewById(R.id.numberOfUnattempted);

        adapter = new MainRecyclerViewAdapter(this, questionModelArrayList);
        mainRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mainRecyclerView.setAdapter(adapter);
        mainRecyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        fastScroller = findViewById(R.id.fast_scroller);
        fastScroller.setRecyclerView(mainRecyclerView);


        countCorrect = 0;
        countIncorrect = 0;
        countUnattempted = 0;
        for (int i = 0; i < questionModelArrayList.size(); i++) {
            if (TextUtils.isEmpty(questionModelArrayList.get(i).getMarked())) {
                countUnattempted++;
            } else if (questionModelArrayList.get(i).getMarked().equals(questionModelArrayList.get(i).getCorrect())) {
                countCorrect++;
            } else {
                countIncorrect++;
            }
        }

        correctTextView.setText(String.format("%03d", countCorrect));
        incorrectTextView.setText(String.format("%03d", countIncorrect));
        unattemptedTextView.setText(String.format("%03d", countUnattempted));


        mainRecyclerView.setOnScrollListener(fastScroller.getOnScrollListener());

        //Added the OnTouchListener
        mainRecyclerView.addOnItemTouchListener(new RowClickedListener(this, mainRecyclerView, new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                vibrator.vibrate(40);
//                Sent the intent to SingleQuestionActivity
                Intent intent = new Intent(context, SingleQuestionActivity.class);
                intent.putExtra("positionInRecyclerView", position);
                intent.putExtra("questionModel", questionModelArrayList.get(position));
                startActivityForResult(intent, REQUEST_CODE);
            }

            //flag question on long click
            @Override
            public void onLongItemClick(View view, int position) {
                vibrator.vibrate(70);
                if (questionModelArrayList.get(position).getFlagged() == 0) {
                    questionModelArrayList.get(position).setFlagged(1);
                    //id is 1 index based, but position is 0 based
                    databaseAdapter.updateFlagged(position + 1, 1);
                    Toast.makeText(context, "Flagged Question No." + questionModelArrayList.get(position).getId(), Toast.LENGTH_SHORT).show();
                } else {
                    questionModelArrayList.get(position).setFlagged(0);
                    //id is 1 index based, but position is 0 based
                    databaseAdapter.updateFlagged(position + 1, 0);
                    Toast.makeText(context, "Unflagged Question No." + questionModelArrayList.get(position).getId(), Toast.LENGTH_SHORT).show();
                }
                adapter.notifyDataSetChanged();
            }
        }));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                int position = data.getIntExtra("recyclerViewPosition", 0);
                int idOfQuestion = data.getIntExtra("idOfQuestion", 1);
                QuestionModel questionModel = databaseAdapter.getDataForASingleRow(idOfQuestion);
                questionModelArrayList.set(position, questionModel);
                if (questionModel.getMarked() != null) {
                    if (questionModel.getMarked() == questionModel.getCorrect()) {
                        countUnattempted--;
                        countCorrect++;
                    } else {
                        countUnattempted--;
                        countIncorrect++;
                    }
                }
                correctTextView.setText(String.format("%03d", countCorrect));
                incorrectTextView.setText(String.format("%03d", countIncorrect));
                unattemptedTextView.setText(String.format("%03d", countUnattempted));
                adapter.notifyDataSetChanged();
            }
        }
    }

}
