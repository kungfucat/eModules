package com.hashinclude.cmoc.emodulesapp;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import co.ceryle.radiorealbutton.RadioRealButton;
import co.ceryle.radiorealbutton.RadioRealButtonGroup;


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


    public RadioRealButton[] currentChosenButton(View row, String toCheck) {
        RadioRealButton[] correctButton = new RadioRealButton[1];
        switch (toCheck) {
            case "a":
                correctButton[0] = row.findViewById(R.id.radioButtonA);
                break;
            case "b":
                correctButton[0] = row.findViewById(R.id.radioButtonB);
                break;

            case "c":
                correctButton[0] = row.findViewById(R.id.radioButtonC);
                break;

            case "d":
                correctButton[0] = row.findViewById(R.id.radioButtonD);
                break;

            case "e":
                correctButton[0] = row.findViewById(R.id.radioButtonE);
                break;
        }
        return correctButton;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View row = inflater.inflate(R.layout.fragment_question, container, false);
        final Bundle bundle = getArguments();
        final QuestionModel questionModel = bundle.getParcelable("questionModel");


        final DatabaseAdapter adapter = new DatabaseAdapter(getContext());

        final RadioRealButtonGroup answerButtonGroup = row.findViewById(R.id.answerRadioButtonGroup);
        WebView questionWebView = row.findViewById(R.id.questionWebView);
        Button submitButton = row.findViewById(R.id.submitButton);
        final int[] optionSelected = {-1};
        final RadioRealButton[] chosenButton = new RadioRealButton[1];

        //TODO:NEED TO UPDATE THIS WITH AN ACTUAL TIMER
        if (TextUtils.isEmpty(questionModel.getTimeTaken())) {
            questionModel.setTimeTaken("00:00");
            adapter.updateTime(questionModel.getId(), "00:00");
        }

        questionWebView.getSettings().setJavaScriptEnabled(true);
        questionWebView.loadDataWithBaseURL("", questionModel.getQuery(), "text/html", "UTF-8", "");

        if (TextUtils.isEmpty(questionModel.getMarked())) {
            answerButtonGroup.setOnClickedButtonListener(new RadioRealButtonGroup.OnClickedButtonListener() {
                @Override
                public void onClickedButton(RadioRealButton button, int position) {
                    optionSelected[0] = position;
                    chosenButton[0] = button;
                }
            });


            submitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String chosenAnswer = "";
                    RadioRealButton[] correctButton;
                    switch (optionSelected[0]) {
                        case -1:
                            Toast.makeText(getContext(), "Select an option.", Toast.LENGTH_SHORT).show();
                            return;
                        case 0:
                            chosenAnswer = "a";
                            break;

                        case 1:
                            chosenAnswer = "b";
                            break;

                        case 2:
                            chosenAnswer = "c";
                            break;

                        case 3:
                            chosenAnswer = "d";
                            break;

                        case 4:
                            chosenAnswer = "e";
                            break;
                    }
                    answerButtonGroup.setClickable(false);
                    if (chosenAnswer.equals(questionModel.getCorrect())) {
                        chosenButton[0].setBackgroundColor(Color.GREEN);
                    } else {
                        chosenButton[0].setBackgroundColor(Color.RED);
                        correctButton = currentChosenButton(row, questionModel.getCorrect());
                        correctButton[0].setBackgroundColor(Color.GREEN);
                    }
                    adapter.updateMarked(questionModel.getId(), chosenAnswer);
                    view.setOnClickListener(null);
                }
            });
        } else {
            answerButtonGroup.setClickable(false);
            submitButton.setClickable(false);
            RadioRealButton[] buttonMarked = currentChosenButton(row, questionModel.getMarked());
            RadioRealButton[] buttonCorrect = currentChosenButton(row, questionModel.getCorrect());
            buttonCorrect[0].setBackgroundColor(Color.GREEN);
            buttonMarked[0].setBackgroundColor(Color.RED);
        }


        return row;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //had to save state to avoid a small bug
        setRetainInstance(true);
    }
}
