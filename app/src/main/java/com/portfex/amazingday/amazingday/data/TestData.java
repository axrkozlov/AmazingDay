package com.portfex.amazingday.amazingday.data;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.ParseException;
import android.util.Log;

import com.portfex.amazingday.amazingday.data.TrainingContract.*;

import java.util.Date;
import java.util.ArrayList;

import java.util.List;

/**
 * Created by alexanderkozlov on 11/18/17.
 */

public class TestData {

    public static void insertFakeData(SQLiteDatabase db) {
        if (db == null) {
            return;
        }
        //create a list of fake guests
        List<ContentValues> list = new ArrayList<>();

        ContentValues cv = new ContentValues();

        long lastDate_ms=0;



        try
        {
            Date lastDate=new Date();
            lastDate_ms = lastDate.getTime();
            Log.e("TESTDATE",lastDate.toString());
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

        cv.put(TrainingEntry.TRAININGS_COLUMN_NAME, "Jump");
        cv.put(TrainingEntry.TRAININGS_COLUMN_DESCRIPTION, "With dumbbell behind the head, with straight back");
        cv.put(TrainingEntry.TRAININGS_COLUMN_REPEAT, "1 per 2 day");
        cv.put(TrainingEntry.TRAININGS_COLUMN_START_TIME, lastDate_ms);
        cv.put(TrainingEntry.TRAININGS_COLUMN_TOTAL_TIME, lastDate_ms);
        cv.put(TrainingEntry.TRAININGS_COLUMN_LAST_DATE,  lastDate_ms);
        list.add(cv);

        cv = new ContentValues();
        cv.put(TrainingEntry.TRAININGS_COLUMN_NAME, "ChestUp");
        cv.put(TrainingEntry.TRAININGS_COLUMN_DESCRIPTION, "Complete workouts designed to build the lower, middle and upper chest");
        cv.put(TrainingEntry.TRAININGS_COLUMN_REPEAT, "1 per 2 day");
        cv.put(TrainingEntry.TRAININGS_COLUMN_START_TIME, lastDate_ms);
        cv.put(TrainingEntry.TRAININGS_COLUMN_TOTAL_TIME, lastDate_ms);
        cv.put(TrainingEntry.TRAININGS_COLUMN_LAST_DATE,  lastDate_ms);
        list.add(cv);

        cv = new ContentValues();
        cv.put(TrainingEntry.TRAININGS_COLUMN_NAME, "Triceps Workout");
        cv.put(TrainingEntry.TRAININGS_COLUMN_DESCRIPTION, "If you want big arms you need to build your triceps");
        cv.put(TrainingEntry.TRAININGS_COLUMN_REPEAT, "1 per 2 day");
        cv.put(TrainingEntry.TRAININGS_COLUMN_START_TIME, lastDate_ms);
        cv.put(TrainingEntry.TRAININGS_COLUMN_TOTAL_TIME, lastDate_ms);
        cv.put(TrainingEntry.TRAININGS_COLUMN_LAST_DATE,  lastDate_ms);
        list.add(cv);


        try
        {
            db.beginTransaction();
            //clear the table first
            db.delete (TrainingEntry.TRAININGS_TABLE_NAME,null,null);
            //go through the list and add one by one
            for(ContentValues c:list){
                db.insert(TrainingEntry.TRAININGS_TABLE_NAME, null, c);
            }
            db.setTransactionSuccessful();
        }
        catch (SQLException e) {
            //too bad :(
        }
        finally
        {
            db.endTransaction();
        }
    }
}
