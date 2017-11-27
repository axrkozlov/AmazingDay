package com.portfex.amazingday.trainings;

import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TrainingAdapter mTrainingAdapter;
    private RecyclerView mTrainingRecyclerView;

    private SQLiteDatabase mDb;
    View mTrainingItemCreateDialogView;
    AlertDialog mTrainingCreateDialog;
    Button mBtStartTime;
    public Boolean startTimeChanged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setElevation(0);


        mTrainingRecyclerView = findViewById(R.id.rv_trainings);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mTrainingRecyclerView.setLayoutManager(layoutManager);

        mTrainingRecyclerView.setHasFixedSize(true);

        TrainingDbHelper dbh = new TrainingDbHelper(this);

        mDb = dbh.getWritableDatabase();

        FakeData.insertFakeData(mDb);


        Cursor cursor = getAllTrainings();
        mTrainingAdapter = new TrainingAdapter(this, cursor);
        mTrainingRecyclerView.setAdapter(mTrainingAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.main_add) {
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
            mTrainingItemCreateDialogView = getLayoutInflater().inflate(R.layout.training_item_edit, null);

            Button bt_create = mTrainingItemCreateDialogView.findViewById(R.id.bt_training_action);
            bt_create.setText("Create");

            mBuilder.setView(mTrainingItemCreateDialogView);
            startTimeChanged=false;
            mTrainingCreateDialog = mBuilder.create();
            mTrainingCreateDialog.show();


            //startActivity(new Intent(this, SettingsActivity.class));
            return true;

        }


//        if (id == R.id.action_map) {
//            openPreferredLocationInMap();
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    private Cursor getAllTrainings() {
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

    public void addTraining(View view) {
        EditText mTrainingCreateName = mTrainingItemCreateDialogView.findViewById(R.id.training_create_name);
        EditText mTrainingCreateDesc = mTrainingItemCreateDialogView.findViewById(R.id.training_create_desc);
        ToggleButton sun = mTrainingItemCreateDialogView.findViewById(R.id.tb_day1);
        ToggleButton mon = mTrainingItemCreateDialogView.findViewById(R.id.tb_day2);
        ToggleButton tue = mTrainingItemCreateDialogView.findViewById(R.id.tb_day3);
        ToggleButton wed = mTrainingItemCreateDialogView.findViewById(R.id.tb_day4);
        ToggleButton thu = mTrainingItemCreateDialogView.findViewById(R.id.tb_day5);
        ToggleButton fri = mTrainingItemCreateDialogView.findViewById(R.id.tb_day6);
        ToggleButton sat = mTrainingItemCreateDialogView.findViewById(R.id.tb_day7);

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

                length() == 0)

        {
            Toast.makeText(this, "Please, insert Name of workout", Toast.LENGTH_SHORT).show();
            return;
        }

        if (mDb == null)

        {
            return;
        }

        List<ContentValues> list = new ArrayList<>();

        ContentValues cv = new ContentValues();

        cv.put(TrainingContract.TrainingEntry.TRAININGS_COLUMN_NAME, mTrainingCreateName.getText().

                toString());
        cv.put(TrainingContract.TrainingEntry.TRAININGS_COLUMN_DESCRIPTION, mTrainingCreateDesc.getText().

                toString());
        cv.put(TrainingContract.TrainingEntry.TRAININGS_COLUMN_REPEAT, composedWeekDays);

        if (startTimeChanged) {
            String startTime = mBtStartTime.getText().toString();
            long startTimeLong = DateUtils.normalizeTimeFromString(startTime);
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

    public void showTimePickerDialog(View view) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);

        mBtStartTime = view.findViewById(R.id.bt_training_create_start_time);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        mBtStartTime.setText(hourOfDay + ":" + minute);
                        startTimeChanged=true;

                    }

                }, hour, minute, true);
        timePickerDialog.show();


    }
}
