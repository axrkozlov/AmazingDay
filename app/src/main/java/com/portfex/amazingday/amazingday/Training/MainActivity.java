package com.portfex.amazingday.amazingday.Training;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.ParseException;
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
import android.widget.Toast;

import com.portfex.amazingday.amazingday.R;
import com.portfex.amazingday.amazingday.Training.TrainingAdapter;
import com.portfex.amazingday.amazingday.data.TestData;
import com.portfex.amazingday.amazingday.data.TrainingContract;
import com.portfex.amazingday.amazingday.data.TrainingDbHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TrainingAdapter mTrainingAdapter;
    private RecyclerView mTrainingRecyclerView;

    private SQLiteDatabase mDb;
    View mTrainingItemCreateDialogView;
    AlertDialog mTrainingCreateDialog;

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

        //TestData.insertFakeData(mDb);


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
            mTrainingItemCreateDialogView = getLayoutInflater().inflate(R.layout.training_item_create, null);

            Button bt_create=mTrainingItemCreateDialogView.findViewById(R.id.bt_training_action);
            bt_create.setText("Create");

            mBuilder.setView(mTrainingItemCreateDialogView);

            mTrainingCreateDialog= mBuilder.create();
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
        EditText mTrainingCreateName =mTrainingItemCreateDialogView.findViewById(R.id.training_create_name);
        EditText mTrainingCreateDesc =mTrainingItemCreateDialogView.findViewById(R.id.training_create_desc);

        if (mTrainingCreateName.getText().length() == 0) {
            Toast.makeText(this, "Please, insert Name of Workout", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mDb == null) {
            return;
        }

        List<ContentValues> list = new ArrayList<>();

        ContentValues cv = new ContentValues();

        cv.put(TrainingContract.TrainingEntry.TRAININGS_COLUMN_NAME, mTrainingCreateName.getText().toString());
        cv.put(TrainingContract.TrainingEntry.TRAININGS_COLUMN_DESCRIPTION, mTrainingCreateDesc.getText().toString());
        list.add(cv);


        try {
            mDb.beginTransaction();
            for (ContentValues c : list) {
                mDb.insert(TrainingContract.TrainingEntry.TRAININGS_TABLE_NAME, null, c);
            }
            mDb.setTransactionSuccessful();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            mDb.endTransaction();
            mTrainingCreateDialog.dismiss();
        }

        mTrainingAdapter.swapCursor(getAllTrainings());

    }
}
