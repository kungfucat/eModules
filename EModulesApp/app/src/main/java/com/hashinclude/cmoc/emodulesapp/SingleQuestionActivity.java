package com.hashinclude.cmoc.emodulesapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

//Will be used to load a single question from the db
public class SingleQuestionActivity extends AppCompatActivity {

    TextView textView;
    DatabaseAdapter databaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_question);

        //Recieved the intent from MainActivity and showed the corresponding text
        Intent intent = getIntent();
        int position = intent.getIntExtra("position", 1);
        textView = findViewById(R.id.questionTextView);

        databaseAdapter = new DatabaseAdapter(this);

        //Use this questionModel for this activity
        QuestionModel questionModel=databaseAdapter.getDataForASingleRow(position);
        textView.setText("Q. No. = " + position + ".");
//        Log.d("DUMMYTEXT",questionModel.getId()+" : "+questionModel.getTimeTaken()+" : "+questionModel.getFlagged());
    }
}
