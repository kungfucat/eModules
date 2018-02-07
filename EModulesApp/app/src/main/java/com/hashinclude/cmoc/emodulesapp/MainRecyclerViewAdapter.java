package com.hashinclude.cmoc.emodulesapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by harsh on 2/5/18.
 */
//The Adapter for the RecyclerView used in MainActivity
public class MainRecyclerViewAdapter extends RecyclerView.Adapter<MainRecyclerViewAdapter.MainViewHolder> {

    LayoutInflater layoutInflater;
    Context context;
    ArrayList<QuestionModel> questionModelArrayList;

    public MainRecyclerViewAdapter(Context context, ArrayList<QuestionModel> questionModelArrayList) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.questionModelArrayList = questionModelArrayList;

    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.main_custom_row, parent, false);
        MainViewHolder holder = new MainViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, final int position) {
        TextView mainTextView = holder.mainTextView;
        TextView timeTextView = holder.timeTextView;
        ImageView questionStatus = holder.questionStatusImageView;
        ImageView flaggedImageView = holder.flagStatusImageView;

        int questionNumber = position + 1;

        String markedAnswer = questionModelArrayList.get(position).getMarked();
        String correctAnswer = questionModelArrayList.get(position).getCorrect();
        int flagged = questionModelArrayList.get(position).getFlagged();
        String timeTaken = questionModelArrayList.get(position).getTimeTaken();

        String mainText = questionNumber + ". ";
        //if the user hasn't opened the question, time taken will be NULL, given in the documentation

        //If the time Taken is null, then definitely unattempted
        //If the user opened the question and did not answer, then will show unattempted, but will update the timer in that case
        if (TextUtils.isEmpty(timeTaken) || TextUtils.isEmpty(markedAnswer)) {
            mainText += "Unattempted";
        }

        //FOR STATUS OF QUESTION
        if (TextUtils.isEmpty(markedAnswer)) {
            GlideApp.with(context)
                    .load(R.drawable.normal_status)
                    .into(questionStatus);
        } else if (markedAnswer.equals(correctAnswer)) {
            GlideApp.with(context)
                    .load(R.drawable.success)
                    .into(questionStatus);
            //Time is surely not NULL, and marked answer is correct, so will show "Correct"
            mainText += "Correct";
        } else {
            GlideApp.with(context)
                    .load(R.drawable.error)
                    .into(questionStatus);
            //Time is surely not NULL, and marked answer is incorrect, so will show "Incorrect"
            mainText += "Incorrect";
        }

//        TIME TEXT VIEW
        if (TextUtils.isEmpty(timeTaken)) {
            timeTextView.setText("--:--");
        } else {
            timeTextView.setText(timeTaken);
        }

        mainTextView.setText(mainText);


        //FOR FLAG
        if (flagged == 0) {
            GlideApp.with(context)
                    .load(R.drawable.unflagged)
                    .into(flaggedImageView);
        } else {
            GlideApp.with(context)
                    .load(R.drawable.flagged)
                    .into(flaggedImageView);
        }

        GlideApp.with(context)
                .load(R.drawable.arrowrightcircle)
                .into(holder.rightArrowImageView);
    }

    @Override
    public int getItemCount() {
        return questionModelArrayList.size();
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {
        TextView mainTextView, timeTextView;
        ImageView questionStatusImageView, flagStatusImageView, rightArrowImageView;


        public MainViewHolder(View itemView) {
            super(itemView);
            mainTextView = itemView.findViewById(R.id.tempTextView);
            rightArrowImageView = itemView.findViewById(R.id.rightArrow);
            timeTextView = itemView.findViewById(R.id.timeTextView);
            questionStatusImageView = itemView.findViewById(R.id.questionStatus);
            flagStatusImageView = itemView.findViewById(R.id.flagQuestion);
        }
    }
}
