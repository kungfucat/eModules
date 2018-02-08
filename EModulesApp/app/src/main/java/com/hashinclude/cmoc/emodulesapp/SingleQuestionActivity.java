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
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.ogaclejapan.smarttablayout.SmartTabLayout;

//Will be used to load a single question from the db
public class SingleQuestionActivity extends AppCompatActivity {

    //    WebView questionWebView;
    ViewPager questionViewPager;
    Toolbar toolbar;
    QuestionViewPagerAdapter questionViewPagerAdapter;
    DatabaseAdapter databaseAdapter;
    TextView toolBarTextView;
    QuestionModel questionModel;
    int position;
    SmartTabLayout indicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_question);

        Intent intent = getIntent();
        //position is position in arraylist
        //position and questionNumber can be any 2 values when will implement search functionality
        position = intent.getIntExtra("positionInRecyclerView", 0);
        questionModel = intent.getParcelableExtra("questionModel");

        databaseAdapter = new DatabaseAdapter(this);
        questionModel = databaseAdapter.getDataForASingleRow(questionModel.getId());

        questionViewPager = findViewById(R.id.questionViewPager);
        toolBarTextView = findViewById(R.id.toolbarTextView);
        toolbar = findViewById(R.id.questionActivityToolbar);
        indicator = findViewById(R.id.viewpagertab);

        toolBarTextView.setText("Question " + questionModel.getId());

        questionViewPagerAdapter = new QuestionViewPagerAdapter(getSupportFragmentManager(), questionModel);
        questionViewPager.setAdapter(questionViewPagerAdapter);
        indicator.setViewPager(questionViewPager);
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
