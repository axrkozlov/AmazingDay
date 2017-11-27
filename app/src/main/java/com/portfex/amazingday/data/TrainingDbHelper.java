package com.portfex.amazingday.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.portfex.amazingday.data.TrainingContract.*;

/**
 * Created by alexanderkozlov on 11/18/17.
 */

public class TrainingDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "trainings.db";

    private static final int DATABASE_VERSION = 1;


    public TrainingDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_TRAININGS_TABLE = "CREATE TABLE " + TrainingEntry.TRAININGS_TABLE_NAME + " (" +
                TrainingEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                TrainingEntry.TRAININGS_COLUMN_NAME + " TEXT NOT NULL, " +
                TrainingEntry.TRAININGS_COLUMN_DESCRIPTION + " TEXT, " +
                TrainingEntry.TRAININGS_COLUMN_REPEAT + " INTEGER, " +
                TrainingEntry.TRAININGS_COLUMN_START_TIME + " TIMESTAMP, " +
                TrainingEntry.TRAININGS_COLUMN_TOTAL_TIME + " TIMESTAMP, " +
                TrainingEntry.TRAININGS_COLUMN_LAST_DATE + " TIMESTAMP" +
                "); ";

        db.execSQL(SQL_CREATE_TRAININGS_TABLE);

        final String SQL_CREATE_EXERCISE_TABLE = "CREATE TABLE " + TrainingEntry.EXERCISES_TABLE_NAME + " (" +
                TrainingEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                TrainingEntry.EXERCISES_COLUMN_NAME + " TEXT NOT NULL, " +
                TrainingEntry.EXERCISES_COLUMN_TRAINING_ID + " INTEGER NOT NULL, " +
                TrainingEntry.EXERCISES_COLUMN_DESCRIPTION + " TEXT, " +
                TrainingEntry.EXERCISES_COLUMN_NORM_TIME + " TIMESTAMP, " +
                TrainingEntry.EXERCISES_COLUMN_NORM_REPS + " INTEGER, " +
                TrainingEntry.EXERCISES_COLUMN_SETS + " INTEGER, " +
                TrainingEntry.EXERCISES_COLUMN_WEIGHT + " INTEGER, " +
                TrainingEntry.EXERCISES_COLUMN_REST_TIME + " TIMESTAMP" +
                "); ";

        db.execSQL(SQL_CREATE_EXERCISE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TrainingEntry.TRAININGS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TrainingEntry.EXERCISES_TABLE_NAME);

        onCreate(db);
    }
}
