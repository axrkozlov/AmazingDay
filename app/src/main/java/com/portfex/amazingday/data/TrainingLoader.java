package com.portfex.amazingday.data;


import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;
import android.support.v4.content.AsyncTaskLoader;

import com.portfex.amazingday.model.training.Training;

import java.util.ArrayList;

/**
 * Created by alexanderkozlov on 12/2/17.
 */

public class TrainingLoader extends AsyncTaskLoader<ArrayList<Training>> {

    Uri mUri;
    String[] mProjection;
    String mSelection;
    String[] mSelectionArgs;
    String mSortOrder;

    Cursor mCursor;
    ArrayList<Training> mTrainings;
    final ForceLoadContentObserver mObserver;

    public TrainingLoader(Context context) {
        super(context);
        mObserver = new ForceLoadContentObserver();
    }

    public TrainingLoader(Context context, Uri uri, String[] projection, String selection,
                          String[] selectionArgs, String sortOrder) {
        super(context);
        mUri = uri;
        mProjection = projection;
        mSelection = selection;
        mSelectionArgs = selectionArgs;
        mSortOrder = sortOrder;
        mObserver = new ForceLoadContentObserver();
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public ArrayList<Training> loadInBackground() {
        //SystemClock.sleep(2000);
        try {
            mCursor = getContext().getContentResolver().query(
                    TrainingContract.TrainingEntry.CONTENT_URI,
                    null,
                    null,
                    null,
                    null
            );
            if (mCursor != null) {
                try {
                    // Ensure the cursor window is filled.
                    mCursor.getCount();
                    mCursor.registerContentObserver(mObserver);
                } catch (RuntimeException ex) {
                    mCursor.close();
                    throw ex;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        mTrainings = new ArrayList<>();
        while (mCursor.moveToNext()) {
            Training training = new Training(mCursor.getLong(mCursor.getColumnIndex(TrainingContract.TrainingEntry._ID)));
            training.setName(mCursor.getString(mCursor.getColumnIndex(TrainingContract.TrainingEntry.TRAININGS_COLUMN_NAME)));
            training.setDescription(mCursor.getString(mCursor.getColumnIndex(TrainingContract.TrainingEntry.TRAININGS_COLUMN_DESCRIPTION)));
            training.setStartTime(mCursor.getLong(mCursor.getColumnIndex(TrainingContract.TrainingEntry.TRAININGS_COLUMN_START_TIME)));
            training.setTotalTime(mCursor.getLong(mCursor.getColumnIndex(TrainingContract.TrainingEntry.TRAININGS_COLUMN_TOTAL_TIME)));
            training.setLastDate(mCursor.getLong(mCursor.getColumnIndex(TrainingContract.TrainingEntry.TRAININGS_COLUMN_LAST_DATE)));
            training.setWeekDaysComposed(mCursor.getInt(mCursor.getColumnIndex(TrainingContract.TrainingEntry.TRAININGS_COLUMN_REPEAT)));
            mTrainings.add(training);
        }
        return mTrainings;
    }


}


//    @Override
//    public ArrayList<Training> loadInBackground() {
//        Cursor cursor = TrainingContract.TrainingEntry.TRAININGS_TABLE_NAME.query(
//                TrainingContract.TrainingEntry.TRAININGS_TABLE_NAME,
//                null,
//                null,
//                null,
//                null,
//                null,
//                null
//        );
//
//        ArrayList<Training> allTrainings = new ArrayList<>();
//
//        while (mCursor.moveToNext()) {
//            Training trainingItem = new Training(mCursor.getLong(mCursor.getColumnIndex(TrainingContract.TrainingEntry._ID)));
//            trainingItem.setName(mCursor.getString(mCursor.getColumnIndex(TrainingContract.TrainingEntry.TRAININGS_COLUMN_NAME)));
//            trainingItem.setDescription(mCursor.getString(mCursor.getColumnIndex(TrainingContract.TrainingEntry.TRAININGS_COLUMN_DESCRIPTION)));
//            trainingItem.setStartTime(mCursor.getLong(mCursor.getColumnIndex(TrainingContract.TrainingEntry.TRAININGS_COLUMN_START_TIME)));
//            trainingItem.setTotalTime(mCursor.getLong(mCursor.getColumnIndex(TrainingContract.TrainingEntry.TRAININGS_COLUMN_TOTAL_TIME)));
//            trainingItem.setLastDate(mCursor.getLong(mCursor.getColumnIndex(TrainingContract.TrainingEntry.TRAININGS_COLUMN_LAST_DATE)));
//            trainingItem.setWeekDaysComposed(mCursor.getInt(mCursor.getColumnIndex(TrainingContract.TrainingEntry.TRAININGS_COLUMN_REPEAT)));
//            allTrainings.add(trainingItem);
//        }
//        mCursor.close();
//        return allTrainings;
//    }


