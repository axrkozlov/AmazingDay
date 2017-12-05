package com.portfex.amazingday.model.training;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import com.portfex.amazingday.data.TrainingContract;

import java.util.ArrayList;

/**
 * Created by alexanderkozlov on 12/3/17.
 */

public class TrainingsManager implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final int ID_LOADER = 41;
    private static TrainingsManager instance;
    Context mContext;
    TrainingsCallback mCallback;
    ArrayList<Training> mTrainings;
    Cursor mCursor;

    public TrainingsManager(Context context) {
        this.mContext = context;

    }

    public static TrainingsManager getInstance(Context context) {
        if (instance == null) {
            instance = new TrainingsManager(context);
        }
        return instance;

    }

    public void setCallback(TrainingsCallback mCallback) {
        this.mCallback = mCallback;
    }

    public Training getTraining(Long id) {
        if (mTrainings == null) {
            return null;
        }
        for (Training training : mTrainings) {
            if (training.getId().equals(id)) return training;
        }
        return null;
    }

    public void insertTraining(Training training) {

        if (training == null) return;
        Uri uri = TrainingContract.TrainingEntry.CONTENT_URI;
        ContentValues cv = new ContentValues();

        cv.put(TrainingContract.TrainingEntry.TRAININGS_COLUMN_NAME, training.getName());
        cv.put(TrainingContract.TrainingEntry.TRAININGS_COLUMN_DESCRIPTION, training.getDescription());
        cv.put(TrainingContract.TrainingEntry.TRAININGS_COLUMN_REPEAT, training.getWeekDaysComposed());
        cv.put(TrainingContract.TrainingEntry.TRAININGS_COLUMN_START_TIME, training.getStartTime());

        mContext.getContentResolver().insert(uri, cv);

    }


    public void updateTraining(Training training) {

        if (training == null) return;
        Uri uri = TrainingContract.TrainingEntry.CONTENT_URI
                .buildUpon().appendPath(training.getId().toString()).build();
        ContentValues cv = new ContentValues();

        cv.put(TrainingContract.TrainingEntry.TRAININGS_COLUMN_NAME, training.getName());
        cv.put(TrainingContract.TrainingEntry.TRAININGS_COLUMN_DESCRIPTION, training.getDescription());
        cv.put(TrainingContract.TrainingEntry.TRAININGS_COLUMN_REPEAT, training.getWeekDaysComposed());
        cv.put(TrainingContract.TrainingEntry.TRAININGS_COLUMN_START_TIME, training.getStartTime());

        mContext.getContentResolver().update(uri, cv, null, null);

    }

    public void removeTraining(Long id) {
        if (id == 0) return;
        Uri uri = TrainingContract.TrainingEntry.CONTENT_URI
                .buildUpon().appendPath(id.toString()).build();
        mContext.getContentResolver().delete(uri, null, null);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (id == ID_LOADER)
            return new CursorLoader(mContext, TrainingContract.TrainingEntry.CONTENT_URI, null, null, null, null);
        return null;
    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor == null) {
            return;
        }
        if (cursor == mCursor) {
            refreshView();
            return;
        }
        mCursor = cursor;

        mTrainings = new ArrayList<>();
        while (cursor.moveToNext()) {
            Training training = new Training(cursor.getLong(cursor.getColumnIndex(TrainingContract.TrainingEntry._ID)));
            training.setName(cursor.getString(cursor.getColumnIndex(TrainingContract.TrainingEntry.TRAININGS_COLUMN_NAME)));
            training.setDescription(cursor.getString(cursor.getColumnIndex(TrainingContract.TrainingEntry.TRAININGS_COLUMN_DESCRIPTION)));
            training.setStartTime(cursor.getLong(cursor.getColumnIndex(TrainingContract.TrainingEntry.TRAININGS_COLUMN_START_TIME)));
            training.setTotalTime(cursor.getLong(cursor.getColumnIndex(TrainingContract.TrainingEntry.TRAININGS_COLUMN_TOTAL_TIME)));
            training.setLastDate(cursor.getLong(cursor.getColumnIndex(TrainingContract.TrainingEntry.TRAININGS_COLUMN_LAST_DATE)));
            training.setWeekDaysComposed(cursor.getInt(cursor.getColumnIndex(TrainingContract.TrainingEntry.TRAININGS_COLUMN_REPEAT)));
            mTrainings.add(training);
        }

        refreshView();

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    public ArrayList<Training> getTrainings() {
        return mTrainings;
    }

    private void refreshView() {
        mCallback.refreshView(mTrainings);
    }

}


//    @Override
//    public Loader<ArrayList<Training>> onCreateLoader(int id, Bundle args) {
//        if (id==ID_LOADER) return new TrainingLoader(mContext);
//        return null;
//    }
//
//
//    @Override
//    public void onLoadFinished(Loader<ArrayList<Training>> loader, ArrayList<Training> data) {
//        Toast.makeText(mContext, "loaded", Toast.LENGTH_SHORT).show();
//        if (mCallback == null) {
//            return;
//        }
//
//
//        mTrainings=data;
//        mCallback.refreshView(data);
//    }
//
//    @Override
//    public void onLoaderReset(Loader<ArrayList<Training>> loader) {
//
//    }