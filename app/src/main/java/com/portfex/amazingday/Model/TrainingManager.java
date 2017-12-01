package com.portfex.amazingday.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.portfex.amazingday.data.FakeData;
import com.portfex.amazingday.data.TrainingContract;
import com.portfex.amazingday.data.TrainingDbHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexanderkozlov on 11/28/17.
 */

public class TrainingManager {

    private static TrainingManager instance;
    private TrainingsChangedCallback mCallback;
    private SQLiteDatabase wDb;
    private SQLiteDatabase rDb;

    private Context mContext;
    private TrainingDbHelper dhHelper;
    private Cursor mCursor;

    public TrainingManager(Context context) {
        this.mContext=context;
        dhHelper = new TrainingDbHelper(mContext);
        wDb = dhHelper.getWritableDatabase();
        rDb = dhHelper.getReadableDatabase();
        insertFakeData();
    }

    public static TrainingManager getInstance(Context context) {
        if (instance == null) {
            instance = new TrainingManager(context);
        }
        return instance;
    }

    public void setCallback(TrainingsChangedCallback mCallback) {
        this.mCallback = mCallback;
    }



    public void insertFakeData() {
        FakeData.insertFakeData(wDb);
    }


    public TrainingItem getTraining(Long id) {
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
        if (!mCursor.moveToFirst()){return null;}
        TrainingItem trainingItem = new TrainingItem(mCursor.getLong(mCursor.getColumnIndex(TrainingContract.TrainingEntry._ID)));
        trainingItem.setName(mCursor.getString(mCursor.getColumnIndex(TrainingContract.TrainingEntry.TRAININGS_COLUMN_NAME)));
        trainingItem.setDescription(mCursor.getString(mCursor.getColumnIndex(TrainingContract.TrainingEntry.TRAININGS_COLUMN_DESCRIPTION)));
        trainingItem.setStartTime(mCursor.getLong(mCursor.getColumnIndex(TrainingContract.TrainingEntry.TRAININGS_COLUMN_START_TIME)));
        trainingItem.setTotalTime(mCursor.getLong(mCursor.getColumnIndex(TrainingContract.TrainingEntry.TRAININGS_COLUMN_TOTAL_TIME)));
        trainingItem.setLastDate(mCursor.getLong(mCursor.getColumnIndex(TrainingContract.TrainingEntry.TRAININGS_COLUMN_LAST_DATE)));
        trainingItem.setWeekDaysComposed(mCursor.getInt(mCursor.getColumnIndex(TrainingContract.TrainingEntry.TRAININGS_COLUMN_REPEAT)));
        mCursor.close();
        return trainingItem;
    }


    public ArrayList<TrainingItem> getAllTrainings() {


        mCursor = rDb.query(
                TrainingContract.TrainingEntry.TRAININGS_TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        ArrayList<TrainingItem> allTrainings = new ArrayList<>();

        while (mCursor.moveToNext()) {
            TrainingItem trainingItem = new TrainingItem(mCursor.getLong(mCursor.getColumnIndex(TrainingContract.TrainingEntry._ID)));
            trainingItem.setName(mCursor.getString(mCursor.getColumnIndex(TrainingContract.TrainingEntry.TRAININGS_COLUMN_NAME)));
            trainingItem.setDescription(mCursor.getString(mCursor.getColumnIndex(TrainingContract.TrainingEntry.TRAININGS_COLUMN_DESCRIPTION)));
            trainingItem.setStartTime(mCursor.getLong(mCursor.getColumnIndex(TrainingContract.TrainingEntry.TRAININGS_COLUMN_START_TIME)));
            trainingItem.setTotalTime(mCursor.getLong(mCursor.getColumnIndex(TrainingContract.TrainingEntry.TRAININGS_COLUMN_TOTAL_TIME)));
            trainingItem.setLastDate(mCursor.getLong(mCursor.getColumnIndex(TrainingContract.TrainingEntry.TRAININGS_COLUMN_LAST_DATE)));
            trainingItem.setWeekDaysComposed(mCursor.getInt(mCursor.getColumnIndex(TrainingContract.TrainingEntry.TRAININGS_COLUMN_REPEAT)));
            allTrainings.add(trainingItem);
        }
        mCursor.close();
        return allTrainings;
    }
    public void updateTrainingsView(){
        if (mCallback==null) return;
        mCallback.updateTrainingsList(getAllTrainings());
    }

    public Boolean removeTraining(Long id) {

        Boolean result= wDb.delete(TrainingContract.TrainingEntry.TRAININGS_TABLE_NAME, TrainingContract.TrainingEntry._ID + "=" + id, null) > 0;
        updateTrainingsView();
        return result;
    }

    public void insertTraining(TrainingItem training) {

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

//
//    TrainingManager(Context mContext) {
//        this.mContext = mContext;
//        TrainingDbHelper dbh = new TrainingDbHelper(mContext);
//        mDb = dbh.getWritableDatabase();
//        FakeData.insertFakeData(mDb);
//        Cursor cursor = getAllTrainings();
//        mTrainingAdapter = new TrainingAdapter(mContext, cursor);
//
//    }
//
//
//
//
//
//
//    TrainingAdapter getTrainingAdapter() {
//        return mTrainingAdapter;
//    }

//    void showCreateTrainingDialog() {
//        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
////        Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
////        if (prev != null) {
////            ft.remove(prev);
////        }
////        ft.addToBackStack(null);
//
//        // Create and show the dialog.
//        DialogFragment newFragment = TrainingEditDialog.newInstance();
//        newFragment.show(ft, "dialog");
//

    //    void showCreateTrainingDialog() {
//        LayoutInflater inflater = LayoutInflater.from(mContext);
//        mTrainingCreateDialogView = inflater.inflate(R.layout.training_item_edit, null);
//
//
//        startTimeChanged = false;
//        mEditCreate = mTrainingCreateDialogView.findViewById(R.id.bt_training_create);
//        mEditUpdate = mTrainingCreateDialogView.findViewById(R.id.bt_training_update);
//        mEditDuplicate = mTrainingCreateDialogView.findViewById(R.id.bt_training_duplicate);
//        mEditDelete = mTrainingCreateDialogView.findViewById(R.id.bt_training_delete);
//        mEditStartTime = mTrainingCreateDialogView.findViewById(R.id.bt_training_create_start_time);
//        mEditName = mTrainingCreateDialogView.findViewById(R.id.training_create_name);
//        mEditDesc = mTrainingCreateDialogView.findViewById(R.id.training_create_desc);
//        mEditDay1 = mTrainingCreateDialogView.findViewById(R.id.tb_day1);
//        mEditDay2 = mTrainingCreateDialogView.findViewById(R.id.tb_day2);
//        mEditDay3 = mTrainingCreateDialogView.findViewById(R.id.tb_day3);
//        mEditDay4 = mTrainingCreateDialogView.findViewById(R.id.tb_day4);
//        mEditDay5 = mTrainingCreateDialogView.findViewById(R.id.tb_day5);
//        mEditDay6 = mTrainingCreateDialogView.findViewById(R.id.tb_day6);
//        mEditDay7 = mTrainingCreateDialogView.findViewById(R.id.tb_day7);
//
//        //if (id>0) {
//
////            bt_create.setVisibility(View.GONE);
////            bt_update.setVisibility(View.VISIBLE);
////            bt_duplicate.setVisibility(View.VISIBLE);
////            bt_delete.setVisibility(View.VISIBLE);
////
//
//
//        //}
//
//        AlertDialog.Builder mBuilder = new AlertDialog.Builder(mContext);
//        mBuilder.setView(mTrainingCreateDialogView);
//        mTrainingCreateDialog = mBuilder.create();
//        mTrainingCreateDialog.show();
//
//        mEditCreate.setOnClickListener(this);
//        mEditStartTime.setOnClickListener(this);
//
//
//    }
//
//    private Cursor getTraining(long id) {
//        return mDb.query(
//                TrainingContract.TrainingEntry.TRAININGS_TABLE_NAME,
//                null,
//                TrainingContract.TrainingEntry._ID + "=" + id,
//                null,
//                null,
//                null,
//                null);
//    }
//
//

//
//
//    }
//
//    public Cursor getAllTrainings() {
//        return mDb.query(
//                TrainingContract.TrainingEntry.TRAININGS_TABLE_NAME,
//                null,
//                null,
//                null,
//                null,
//                null,
//                null
//        );
//    }
//
//    private boolean removeTraining(long id) {
//        return mDb.delete(TrainingContract.TrainingEntry.TRAININGS_TABLE_NAME, TrainingContract.TrainingEntry._ID + "=" + id, null) > 0;
//    }
//

//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.bt_training_create:
//                createTraining();
//                break;
//            case R.id.bt_training_create_start_time:
//                showTimePickerDialog();
//                break;
//
//        }
//    }
}
