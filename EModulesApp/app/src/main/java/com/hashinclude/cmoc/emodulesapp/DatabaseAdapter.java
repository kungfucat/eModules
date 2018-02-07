package com.hashinclude.cmoc.emodulesapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;

/**
 * Created by harsh on 2/7/18.
 */

public class DatabaseAdapter {
    DatabaseHelper databaseHelper;

    public DatabaseAdapter(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public ArrayList<QuestionModel> getAllData() {
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        String[] columns = {
                DatabaseHelper.ID,
                DatabaseHelper.QUERY,
                DatabaseHelper.SOLUTION,
                DatabaseHelper.CORRECT_ANSWER,
                DatabaseHelper.TOPIC,
                DatabaseHelper.NOTES,
                DatabaseHelper.MARKED,
                DatabaseHelper.TIME_TAKEN,
                DatabaseHelper.FLAGGED};

        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, columns, null, null, null, null, DatabaseHelper.ID);

        ArrayList<QuestionModel> questionModels = new ArrayList<>();
        while (cursor.moveToNext()) {

            QuestionModel temporary = new QuestionModel();
            int questionNumber = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.ID));
            temporary.setId(questionNumber);

            String query = cursor.getString(cursor.getColumnIndex(DatabaseHelper.QUERY));
            temporary.setQuery(query);

            String solution = cursor.getString(cursor.getColumnIndex(DatabaseHelper.SOLUTION));
            temporary.setSolution(solution);

            String correctAnswer = cursor.getString(cursor.getColumnIndex(DatabaseHelper.CORRECT_ANSWER));
            temporary.setCorrect(correctAnswer);

            String topic = cursor.getString(cursor.getColumnIndex(DatabaseHelper.TOPIC));
            temporary.setTopic(topic);

            String notes = cursor.getString(cursor.getColumnIndex(DatabaseHelper.NOTES));
            temporary.setNotes(notes);

            String marked = cursor.getString(cursor.getColumnIndex(DatabaseHelper.MARKED));
            temporary.setMarked(marked);

            String timeTaken = cursor.getString(cursor.getColumnIndex(DatabaseHelper.TIME_TAKEN));
            temporary.setTimeTaken(timeTaken);

            int flagged = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.FLAGGED));
            temporary.setFlagged(flagged);

            questionModels.add(temporary);
        }
        return questionModels;
    }

    public int updateFlagged(int id, int flag) {

        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.FLAGGED, flag);

        String[] whereArgs = {String.valueOf(id)};
        int count = database.update(DatabaseHelper.TABLE_NAME, contentValues,
                DatabaseHelper.ID + " =?", whereArgs);
        return count;
    }

    static class DatabaseHelper extends SQLiteAssetHelper {
//      For details refer : https://github.com/utkarshmttl/eModules/tree/master/DB
        Context context;
        private static final String DATABASE_NAME = "questionsdb.db";
        private static final String TABLE_NAME = "questions";
        private static final int DATABASE_VERSION = 1;
        private static final String ID = "ID";
        private static final String QUERY = "query";
        private static final String SOLUTION = "solution";
        private static final String CORRECT_ANSWER = "correct";
        private static final String TOPIC = "topic";
        private static final String NOTES = "notes";
        private static final String MARKED = "marked";
        private static final String TIME_TAKEN = "time_txt";
        private static final String FLAGGED = "flagged";

        //Need to call the super constructor
        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
        }
    }

}
