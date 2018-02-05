package com.hashinclude.cmoc.emodulesapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by harsh on 2/5/18.
 */
//The Adapter for the RecyclerView used in MainActivity
public class MainRecyclerViewAdapter extends RecyclerView.Adapter<MainRecyclerViewAdapter.MainViewHolder> {

    LayoutInflater layoutInflater;
    Context context;

    public MainRecyclerViewAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);

    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.main_custom_row, parent, false);
        MainViewHolder holder = new MainViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
//        The images added are just for demonstration purposes as of now
//        GlideApp should be used to load images as shown below
        holder.mainTextView.setText(position + ". Unattempted");
        if(position%3==0){
            GlideApp.with(context)
                    .load(R.drawable.error)
                    .into(holder.questionStatusImageView);
        }
        else if(position%3==1){
            GlideApp.with(context)
                    .load(R.drawable.success)
                    .into(holder.questionStatusImageView);
        }
        else {
            GlideApp.with(context)
                    .load(R.drawable.normal_status)
                    .into(holder.questionStatusImageView);
        }

        if(position%2==0){
            GlideApp.with(context)
                    .load(R.drawable.unflagged)
                    .into(holder.flagStatusImageView);
        }
        else {
            GlideApp.with(context)
                    .load(R.drawable.flagged)
                    .into(holder.flagStatusImageView);
        }


        GlideApp.with(context)
                .load(R.drawable.arrowrightcircle)
                .into(holder.rightArrowImageView);
    }

    @Override
    public int getItemCount() {
        return 100;
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {
        TextView mainTextView,timeTextView;
        ImageView questionStatusImageView, flagStatusImageView,rightArrowImageView;


        public MainViewHolder(View itemView) {
            super(itemView);
            mainTextView = itemView.findViewById(R.id.tempTextView);
            rightArrowImageView=itemView.findViewById(R.id.rightArrow);
            timeTextView=itemView.findViewById(R.id.timeTextView);
            questionStatusImageView=itemView.findViewById(R.id.questionStatus);
            flagStatusImageView=itemView.findViewById(R.id.flagQuestion);
        }
    }
}
