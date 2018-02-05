package com.hashinclude.cmoc.emodulesapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by harsh on 2/5/18.
 */

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
        holder.textView.setText("This is position " + position + " .");
    }

    @Override
    public int getItemCount() {
        return 100;
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public MainViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tempTextView);
        }
    }
}
