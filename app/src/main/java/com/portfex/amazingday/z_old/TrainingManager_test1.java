package com.portfex.amazingday.z_old;

import android.content.ContentValues;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.CursorLoader;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.widget.Toast;

import com.portfex.amazingday.model.training.Training;
import com.portfex.amazingday.model.training.TrainingsCallback;
import com.portfex.amazingday.data.FakeData;
import com.portfex.amazingday.data.TrainingContract;
import com.portfex.amazingday.data.TrainingDbHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexanderkozlov on 11/28/17.
 */

public class TrainingManager_test1 extends Fragment implements
        LoaderManager.LoaderCallbacks<Cursor> {

    private static final int ID_TRAININGS_LOADER = 41;
    public static final String TAG = "TrainingManager_test1";
    private static TrainingManager_test1 instance;
    private TrainingsCallback mCallback;
    private SQLiteDatabase wDb;
    private SQLiteDatabase rDb;

    private TrainingDbHelper dhHelper;
    private Cursor mCursor;
    private ArrayList<Training> mTrainings;

//    public TrainingManager_test1(Context context) {
//        this.mContext=context;
//        dhHelper = new TrainingDbHelper(mContext);
//        wDb = dhHelper.getWritableDatabase();
//        rDb = dhHelper.getReadableDatabase();
//        insertFakeData();
//        context.getContentResolver();
//
//    }

    public static TrainingManager_test1 getInstance() {
        if (instance == null) {
            instance = new TrainingManager_test1();
        }
        return instance;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        dhHelper = new TrainingDbHelper(getContext());
        wDb = dhHelper.getWritableDatabase();
        rDb = dhHelper.getReadableDatabase();
        insertFakeData();
        getLoaderManager().initLoader(ID_TRAININGS_LOADER, null, this);
        Toast.makeText(getContext(), "createloader", Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onResume() {
        super.onResume();
        Toast.makeText(getContext(), "resumeloader", Toast.LENGTH_SHORT).show();
        mCallback.refreshView(mTrainings);
    }

    public void setCallback(TrainingsCallback mCallback) {
        this.mCallback = mCallback;
    }


    public void insertFakeData() {
        FakeData.insertFakeData(wDb);


    }


    public Training getTraining(Long id) {
        String[] selectionArgs = {id.toString()};
        mCursor = rDb.query(
                TrainingContract.TrainingEntry.TRAININGS_TABLE_NAME,
                null,
                TrainingContract.TrainingEntry._ID + " = ? ",
                selectionArgs,
                null,
                null,
                null
        );
        if (!mCursor.moveToFirst()) {
            return null;
        }
        Training training = new Training(mCursor.getLong(mCursor.getColumnIndex(TrainingContract.TrainingEntry._ID)));
        training.setName(mCursor.getString(mCursor.getColumnIndex(TrainingContract.TrainingEntry.TRAININGS_COLUMN_NAME)));
        training.setDescription(mCursor.getString(mCursor.getColumnIndex(TrainingContract.TrainingEntry.TRAININGS_COLUMN_DESCRIPTION)));
        training.setStartTime(mCursor.getLong(mCursor.getColumnIndex(TrainingContract.TrainingEntry.TRAININGS_COLUMN_START_TIME)));
        training.setTotalTime(mCursor.getLong(mCursor.getColumnIndex(TrainingContract.TrainingEntry.TRAININGS_COLUMN_TOTAL_TIME)));
        training.setLastDate(mCursor.getLong(mCursor.getColumnIndex(TrainingContract.TrainingEntry.TRAININGS_COLUMN_LAST_DATE)));
        training.setWeekDaysComposed(mCursor.getInt(mCursor.getColumnIndex(TrainingContract.TrainingEntry.TRAININGS_COLUMN_REPEAT)));
        mCursor.close();
        return training;
    }

    public ArrayList<Training> getTrainings() {
//        Cursor cursor= new CursorLoader(mContext,
//                TrainingContract.TrainingEntry.CONTENT_URI,
//                null,
//                null,
//                null,
//                null);
//        return null;
        return null;
    }

    /**
     * simple load from db
     */
    public ArrayList<Training> getAllTrainings() {


        mCursor = rDb.query(
                TrainingContract.TrainingEntry.TRAININGS_TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        ArrayList<Training> allTrainings = new ArrayList<>();

        while (mCursor.moveToNext()) {
            Training training = new Training(mCursor.getLong(mCursor.getColumnIndex(TrainingContract.TrainingEntry._ID)));
            training.setName(mCursor.getString(mCursor.getColumnIndex(TrainingContract.TrainingEntry.TRAININGS_COLUMN_NAME)));
            training.setDescription(mCursor.getString(mCursor.getColumnIndex(TrainingContract.TrainingEntry.TRAININGS_COLUMN_DESCRIPTION)));
            training.setStartTime(mCursor.getLong(mCursor.getColumnIndex(TrainingContract.TrainingEntry.TRAININGS_COLUMN_START_TIME)));
            training.setTotalTime(mCursor.getLong(mCursor.getColumnIndex(TrainingContract.TrainingEntry.TRAININGS_COLUMN_TOTAL_TIME)));
            training.setLastDate(mCursor.getLong(mCursor.getColumnIndex(TrainingContract.TrainingEntry.TRAININGS_COLUMN_LAST_DATE)));
            training.setWeekDaysComposed(mCursor.getInt(mCursor.getColumnIndex(TrainingContract.TrainingEntry.TRAININGS_COLUMN_REPEAT)));
            allTrainings.add(training);
        }
        mCursor.close();
        return allTrainings;
    }

    public void updateTrainingsView() {
        if (mCallback == null) return;
        //mCallback.refreshView(getAllTrainings());
    }

    public Boolean removeTraining(Long id) {

        Boolean result = wDb.delete(TrainingContract.TrainingEntry.TRAININGS_TABLE_NAME, TrainingContract.TrainingEntry._ID + "=" + id, null) > 0;
        updateTrainingsView();
        return result;
    }

    public void insertTraining(Training training) {

        if (wDb == null) return;
        if (training == null) return;

        List<ContentValues> list = new ArrayList<>();
        ContentValues cv = new ContentValues();

        cv.put(TrainingContract.TrainingEntry.TRAININGS_COLUMN_NAME, training.getName());
        cv.put(TrainingContract.TrainingEntry.TRAININGS_COLUMN_DESCRIPTION, training.getDescription());
        cv.put(TrainingContract.TrainingEntry.TRAININGS_COLUMN_REPEAT, training.getWeekDaysComposed());
        cv.put(TrainingContract.TrainingEntry.TRAININGS_COLUMN_START_TIME, training.getStartTime());


        list.add(cv);

        try {
            wDb.beginTransaction();
            for (ContentValues c : list) {
                wDb.insert(TrainingContract.TrainingEntry.TRAININGS_TABLE_NAME, null, c);
            }
            wDb.setTransactionSuccessful();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            wDb.endTransaction();
        }

        updateTrainingsView();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {

            case ID_TRAININGS_LOADER:
                Uri forecastQueryUri = TrainingContract.TrainingEntry.CONTENT_URI;
                String[] projection = {
                        TrainingContract.TrainingEntry._ID,
                        TrainingContract.TrainingEntry.TRAININGS_COLUMN_NAME,
                        TrainingContract.TrainingEntry.TRAININGS_COLUMN_DESCRIPTION,
                        TrainingContract.TrainingEntry.TRAININGS_COLUMN_START_TIME,
                        TrainingContract.TrainingEntry.TRAININGS_COLUMN_TOTAL_TIME,
                        TrainingContract.TrainingEntry.TRAININGS_COLUMN_LAST_DATE,
                        TrainingContract.TrainingEntry.TRAININGS_COLUMN_REPEAT

                };

                return new CursorLoader(getContext(),
                        forecastQueryUri,
                        null,
                        null,
                        null,
                        null);

            default:
                throw new RuntimeException("Loader Not Implemented: " + id);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        ArrayList<Training> allTrainings = new ArrayList<>();
        while (data.moveToNext()) {
            Training training = new Training(data.getLong(data.getColumnIndex(TrainingContract.TrainingEntry._ID)));
            training.setName(data.getString(data.getColumnIndex(TrainingContract.TrainingEntry.TRAININGS_COLUMN_NAME)));
            training.setDescription(data.getString(data.getColumnIndex(TrainingContract.TrainingEntry.TRAININGS_COLUMN_DESCRIPTION)));
            training.setStartTime(data.getLong(data.getColumnIndex(TrainingContract.TrainingEntry.TRAININGS_COLUMN_START_TIME)));
            training.setTotalTime(data.getLong(data.getColumnIndex(TrainingContract.TrainingEntry.TRAININGS_COLUMN_TOTAL_TIME)));
            training.setLastDate(data.getLong(data.getColumnIndex(TrainingContract.TrainingEntry.TRAININGS_COLUMN_LAST_DATE)));
            training.setWeekDaysComposed(data.getInt(data.getColumnIndex(TrainingContract.TrainingEntry.TRAININGS_COLUMN_REPEAT)));
            allTrainings.add(training);
        }
        if (mCallback == null) return;
        mTrainings = allTrainings;
        mCallback.refreshView(allTrainings);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }


//
//
//    @Override
//    public Loader<Training> onCreateLoader(int id, Bundle args) {
//        Cursor cursor= new CursorLoader(mContext,
//                TrainingContract.TrainingEntry.CONTENT_URI,
//                null,
//                null,
//                null,
//                null);
//        return null;
//
//    }

//    @Override
//    public void onLoadFinished(Loader<Training> loader, Training data) {
//        ArrayList<Training> allTrainings = new ArrayList<>();
//        while (data.moveToNext())
//        {
//            Training trainingItem = new Training(data.getLong(data.getColumnIndex(TrainingContract.TrainingEntry._ID)));
//            trainingItem.setName(data.getString(data.getColumnIndex(TrainingContract.TrainingEntry.TRAININGS_COLUMN_NAME)));
//            trainingItem.setDescription(data.getString(data.getColumnIndex(TrainingContract.TrainingEntry.TRAININGS_COLUMN_DESCRIPTION)));
//            trainingItem.setStartTime(data.getLong(data.getColumnIndex(TrainingContract.TrainingEntry.TRAININGS_COLUMN_START_TIME)));
//            trainingItem.setTotalTime(data.getLong(data.getColumnIndex(TrainingContract.TrainingEntry.TRAININGS_COLUMN_TOTAL_TIME)));
//            trainingItem.setLastDate(data.getLong(data.getColumnIndex(TrainingContract.TrainingEntry.TRAININGS_COLUMN_LAST_DATE)));
//            trainingItem.setWeekDaysComposed(data.getInt(data.getColumnIndex(TrainingContract.TrainingEntry.TRAININGS_COLUMN_REPEAT)));
//        }
//        if (mCallback==null) return;
//        mCallback.refreshView(allTrainings);
//    }
//
//    @Override
//    public void onLoaderReset(Loader<Training> loader) {
//
//    }


}
