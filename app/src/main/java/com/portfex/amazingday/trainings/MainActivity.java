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
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mTrainingRecyclerView;



TrainingManager trainingManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setElevation(0);


        mTrainingRecyclerView = findViewById(R.id.rv_trainings);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mTrainingRecyclerView.setLayoutManager(layoutManager);
        mTrainingRecyclerView.setHasFixedSize(true);
        trainingManager=new TrainingManager(this);
        mTrainingRecyclerView.setAdapter(trainingManager.getTrainingAdapter());

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
            trainingManager.showCreateTrainingDialog();


            //startActivity(new Intent(this, SettingsActivity.class));
            return true;

        } else if (id == R.id.menu_settings) {
            return false;
        }
        return super.onOptionsItemSelected(item);
    }









}
