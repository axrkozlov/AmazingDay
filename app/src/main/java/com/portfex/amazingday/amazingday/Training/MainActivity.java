package com.portfex.amazingday.amazingday.Training;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;

import com.portfex.amazingday.amazingday.R;
import com.portfex.amazingday.amazingday.Training.TrainingAdapter;
import com.portfex.amazingday.amazingday.data.TestData;
import com.portfex.amazingday.amazingday.data.TrainingContract;
import com.portfex.amazingday.amazingday.data.TrainingDbHelper;

public class MainActivity extends AppCompatActivity {

    private TrainingAdapter mTrainingAdapter;
    private RecyclerView mTrainingRecyclerView;
    private SQLiteDatabase mDb;
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

        TestData.insertFakeData(mDb);


        Cursor cursor = getAllGuests();
        mTrainingAdapter=new TrainingAdapter(this, cursor);
        mTrainingRecyclerView.setAdapter(mTrainingAdapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* Use AppCompatActivity's method getMenuInflater to get a handle on the menu inflater */
        MenuInflater inflater = getMenuInflater();
        /* Use the inflater's inflate method to inflate our menu layout to this menu */
        inflater.inflate(R.menu.main, menu);
        /* Return true so that the menu is displayed in the Toolbar */
        return true;
    }



    private Cursor getAllGuests() {
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

}
