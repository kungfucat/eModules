package com.hashinclude.cmoc.emodulesapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class NotesFragment extends Fragment {


    public NotesFragment() {
        // Required empty public constructor
    }

    public static NotesFragment newInstance(QuestionModel questionModel) {
        NotesFragment fragment = new NotesFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("questionModel", questionModel);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View row = inflater.inflate(R.layout.fragment_notes, container, false);
        Bundle bundle = getArguments();
        QuestionModel questionModel = bundle.getParcelable("questionModel");
        EditText notesEditText=row.findViewById(R.id.notesEditText);
        String notes=questionModel.getNotes();
        if(TextUtils.isEmpty(notes)){
            notesEditText.setHint("Type notes here");
        }
        else{
            notesEditText.setText(notes);
        }
        return row;
    }

}
