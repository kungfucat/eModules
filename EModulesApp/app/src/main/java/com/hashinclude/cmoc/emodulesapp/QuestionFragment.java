package com.hashinclude.cmoc.emodulesapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionFragment extends Fragment {

    public QuestionFragment() {
        // Required empty public constructor
    }
    public static QuestionFragment newInstance(QuestionModel questionModel) {
        QuestionFragment fragment = new QuestionFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("questionModel", questionModel);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View row= inflater.inflate(R.layout.fragment_question, container, false);
        Bundle bundle = getArguments();
        QuestionModel questionModel = bundle.getParcelable("questionModel");
        WebView questionWebView = row.findViewById(R.id.questionWebView);
        questionWebView.getSettings().setJavaScriptEnabled(true);
        questionWebView.loadDataWithBaseURL("", questionModel.getQuery(), "text/html", "UTF-8", "");

        return row;
    }

}
