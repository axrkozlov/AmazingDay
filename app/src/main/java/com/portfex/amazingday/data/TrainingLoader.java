package com.portfex.amazingday.data;

import android.content.AsyncTaskLoader;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.CancellationSignal;
import android.os.OperationCanceledException;
import android.support.v4.content.CursorLoader;

import com.portfex.amazingday.Model.TrainingItem;

import java.util.ArrayList;

/**
 * Created by alexanderkozlov on 12/2/17.
 */

public class TrainingLoader extends AsyncTaskLoader<ArrayList<TrainingItem>> {

    Uri mUri;
    String[] mProjection;
    String mSelection;
    String[] mSelectionArgs;
    String mSortOrder;

    Cursor mCursor;

    public TrainingLoader(Context context, Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        super(context);
        mUri = uri;
        mProjection = projection;
        mSelection = selection;
        mSelectionArgs = selectionArgs;
        mSortOrder = sortOrder;
    }

    @Override
    public ArrayList<TrainingItem> loadInBackground() {


        Cursor cursor =getContext().getContentResolver().query(
                TrainingContract.TrainingEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );
        ArrayList<TrainingItem> allTrainings = new ArrayList<>();
        while (cursor.moveToNext())
        {
            TrainingItem trainingItem = new TrainingItem(cursor.getLong(cursor.getColumnIndex(TrainingContract.TrainingEntry._ID)));
            trainingItem.setName(cursor.getString(cursor.getColumnIndex(TrainingContract.TrainingEntry.TRAININGS_COLUMN_NAME)));
            trainingItem.setDescription(cursor.getString(cursor.getColumnIndex(TrainingContract.TrainingEntry.TRAININGS_COLUMN_DESCRIPTION)));
            trainingItem.setStartTime(cursor.getLong(cursor.getColumnIndex(TrainingContract.TrainingEntry.TRAININGS_COLUMN_START_TIME)));
            trainingItem.setTotalTime(cursor.getLong(cursor.getColumnIndex(TrainingContract.TrainingEntry.TRAININGS_COLUMN_TOTAL_TIME)));
            trainingItem.setLastDate(cursor.getLong(cursor.getColumnIndex(TrainingContract.TrainingEntry.TRAININGS_COLUMN_LAST_DATE)));
            trainingItem.setWeekDaysComposed(cursor.getInt(cursor.getColumnIndex(TrainingContract.TrainingEntry.TRAININGS_COLUMN_REPEAT)));
        }
        return allTrainings;
    }
}


//    @Override
//    public ArrayList<TrainingItem> loadInBackground() {
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
//        ArrayList<TrainingItem> allTrainings = new ArrayList<>();
//
//        while (mCursor.moveToNext()) {
//            TrainingItem trainingItem = new TrainingItem(mCursor.getLong(mCursor.getColumnIndex(TrainingContract.TrainingEntry._ID)));
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

    /**
     * Stores away the application context associated with context.
     * Since Loaders can be used across multiple activities it's dangerous to
     * store the context directly; always use {@link #getContext()} to retrieve
     * the Loader's Context, don't use the constructor argument directly.
     * The Context returned by {@link #getContext} is safe to use across
     * Activity instances.
     *
     * @param context used to retrieve the application context.
     */




