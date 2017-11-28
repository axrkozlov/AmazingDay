package com.portfex.amazingday.trainings;

import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.portfex.amazingday.R;
import com.portfex.amazingday.data.FakeData;
import com.portfex.amazingday.data.TrainingContract;
import com.portfex.amazingday.data.TrainingDbHelper;
import com.portfex.amazingday.utilites.DateUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by alexanderkozlov on 11/28/17.
 */

public class TrainingManager implements View.OnClickListener {

    private SQLiteDatabase mDb;
    View mTrainingCreateDialogView;
    AlertDialog mTrainingCreateDialog;
    Button mBtStartTime;
    Context mContext;
    public Boolean startTimeChanged;
    private TrainingAdapter mTrainingAdapter;


    public TrainingManager(Context mContext) {
        this.mContext = mContext;
        TrainingDbHelper dbh = new TrainingDbHelper(mContext);
        mDb = dbh.getWritableDatabase();
        FakeData.insertFakeData(mDb);
        Cursor cursor = getAllTrainings();
        mTrainingAdapter=new TrainingAdapter(mContext,cursor);
    }

    public TrainingAdapter getTrainingAdapter() {
        return mTrainingAdapter;
    }

    public void showCreateTrainingDialog() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        mTrainingCreateDialogView = inflater.inflate(R.layout.training_item_edit, null);


        startTimeChanged = false;
        Button bt_create = mTrainingCreateDialogView.findViewById(R.id.bt_training_create);
        Button bt_update = mTrainingCreateDialogView.findViewById(R.id.bt_training_update);
        Button bt_duplicate = mTrainingCreateDialogView.findViewById(R.id.bt_training_duplicate);
        Button bt_delete = mTrainingCreateDialogView.findViewById(R.id.bt_training_delete);
        Button bt_startTime=mTrainingCreateDialogView.findViewById(R.id.bt_training_create_start_time);

        //if (id>0) {

//            bt_create.setVisibility(View.GONE);
//            bt_update.setVisibility(View.VISIBLE);
//            bt_duplicate.setVisibility(View.VISIBLE);
//            bt_delete.setVisibility(View.VISIBLE);
//


        //}

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(mContext);
        mBuilder.setView(mTrainingCreateDialogView);
        mTrainingCreateDialog = mBuilder.create();
        mTrainingCreateDialog.show();

        bt_create.setOnClickListener(this);
        bt_startTime.setOnClickListener(this);


    }

    private Cursor getTraining(long id) {
        return mDb.query(
                TrainingContract.TrainingEntry.TRAININGS_TABLE_NAME,
                null,
                TrainingContract.TrainingEntry._ID + "=" + id,
                null,
                null,
                null,
                null);
    }


    public void showTimePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);

        mBtStartTime = mTrainingCreateDialogView.findViewById(R.id.bt_training_create_start_time);

        TimePickerDialog timePickerDialog = new TimePickerDialog(mContext,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        mBtStartTime.setText(String.format("%02d:%02d", hourOfDay, minute));
                        startTimeChanged = true;

                    }

                }, hour, minute, true);
        timePickerDialog.show();


    }

    public Cursor getAllTrainings() {
        return mDb.query(
                TrainingContract.TrainingEntry.TRAININGS_TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    private boolean removeTraining(long id) {
        return mDb.delete(TrainingContract.TrainingEntry.TRAININGS_TABLE_NAME, TrainingContract.TrainingEntry._ID + "=" + id, null) > 0;
    }

    public void createTraining() {
        EditText mTrainingCreateName = mTrainingCreateDialogView.findViewById(R.id.training_create_name);
        EditText mTrainingCreateDesc = mTrainingCreateDialogView.findViewById(R.id.training_create_desc);
        ToggleButton sun = mTrainingCreateDialogView.findViewById(R.id.tb_day1);
        ToggleButton mon = mTrainingCreateDialogView.findViewById(R.id.tb_day2);
        ToggleButton tue = mTrainingCreateDialogView.findViewById(R.id.tb_day3);
        ToggleButton wed = mTrainingCreateDialogView.findViewById(R.id.tb_day4);
        ToggleButton thu = mTrainingCreateDialogView.findViewById(R.id.tb_day5);
        ToggleButton fri = mTrainingCreateDialogView.findViewById(R.id.tb_day6);
        ToggleButton sat = mTrainingCreateDialogView.findViewById(R.id.tb_day7);

        ArrayList<Boolean> weekDays = new ArrayList<>();
        weekDays.add(sun.isChecked());
        weekDays.add(mon.isChecked());
        weekDays.add(tue.isChecked());
        weekDays.add(wed.isChecked());
        weekDays.add(thu.isChecked());
        weekDays.add(fri.isChecked());
        weekDays.add(sat.isChecked());

        int composedWeekDays = DateUtils.composeWeekDays(weekDays);
        if (mTrainingCreateName.getText().
                length() == 0) {
            Toast.makeText(mContext, "Please, insert Name of workout", Toast.LENGTH_SHORT).show();
            return;
        }

        if (mDb == null) {
            return;
        }

        List<ContentValues> list = new ArrayList<>();
        ContentValues cv = new ContentValues();

        cv.put(TrainingContract.TrainingEntry.TRAININGS_COLUMN_NAME, mTrainingCreateName.getText().toString());
        cv.put(TrainingContract.TrainingEntry.TRAININGS_COLUMN_DESCRIPTION, mTrainingCreateDesc.getText().toString());
        cv.put(TrainingContract.TrainingEntry.TRAININGS_COLUMN_REPEAT, composedWeekDays);

        if (startTimeChanged) {
            String startTime = mBtStartTime.getText().toString();
            long startTimeLong = DateUtils.getTimeMillis(startTime);
            cv.put(TrainingContract.TrainingEntry.TRAININGS_COLUMN_START_TIME, startTimeLong);
        }

        list.add(cv);

        try

        {
            mDb.beginTransaction();
            for (ContentValues c : list) {
                mDb.insert(TrainingContract.TrainingEntry.TRAININGS_TABLE_NAME, null, c);
            }
            mDb.setTransactionSuccessful();
        } catch (
                SQLException e)

        {
            e.printStackTrace();
        } finally

        {
            mDb.endTransaction();
            mTrainingCreateDialog.dismiss();
        }

        mTrainingAdapter.swapCursor(

                getAllTrainings());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_training_create:
                createTraining();
                break;
            case R.id.bt_training_create_start_time:
                showTimePickerDialog();
                break;

        }
    }
}
