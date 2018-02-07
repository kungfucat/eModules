package com.hashinclude.cmoc.emodulesapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

//Will be used to load a single question from the db
public class SingleQuestionActivity extends AppCompatActivity {

    //    WebView questionWebView;
    ViewPager questionViewPager;
    QuestionViewPagerAdapter questionViewPagerAdapter;
    DatabaseAdapter databaseAdapter;
    QuestionModel questionModel;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_question);

        //Recieved the intent from MainActivity and showed the corresponding text
        Intent intent = getIntent();
        //position is position in arraylist
        position = intent.getIntExtra("position", 0);
//        Question number is actually position+1 (question start from 1 but arraylist index starts from 0
        int questionNumber = position + 1;
        databaseAdapter = new DatabaseAdapter(this);
        questionModel = databaseAdapter.getDataForASingleRow(questionNumber);
        questionViewPager = findViewById(R.id.questionViewPager);
        questionViewPagerAdapter = new QuestionViewPagerAdapter(getSupportFragmentManager(), questionModel);
        questionViewPager.setAdapter(questionViewPagerAdapter);
    }

    @Override
    public void onBackPressed() {
        Intent returnIntent = getIntent();
        returnIntent.putExtra("currentPosition", position);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }


    class QuestionViewPagerAdapter extends FragmentPagerAdapter {
        String[] tabs = {"Question", "Notes"};
        QuestionModel questionModel;

        public QuestionViewPagerAdapter(FragmentManager fm, QuestionModel questionModel) {
            super(fm);
            this.questionModel = questionModel;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment myFragment = null;
            if (position == 0) {
                QuestionFragment questionFragment = QuestionFragment.newInstance(questionModel);
                myFragment = questionFragment;
            }
            if (position == 1) {
                NotesFragment notesFragment = NotesFragment.newInstance(questionModel);
                myFragment = notesFragment;
            }
            return myFragment;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return tabs[position];
        }
    }

}
