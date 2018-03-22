package com.portfex.amazingday.presenters.trainings;


import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.portfex.amazingday.model.training.TrainingsManager;
import com.portfex.amazingday.R;
import com.portfex.amazingday.presenters.exercises.ExercisesActivity;

import static com.portfex.amazingday.model.training.TrainingsManager.ID_LOADER;

//BranchTwo
public class TrainingsActivity extends AppCompatActivity implements TrainingClickHandler, SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    private RecyclerView mTrainingsRecyclerView;


    TrainingsManager mTrainingsManager;
    TrainingsAdapter mTrainingsAdapter;
    private String trainingIdTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainings);
        getSupportActionBar().setElevation(0);
        Toast.makeText(this, "BranchTwo", Toast.LENGTH_SHORT).show();
        trainingIdTag = getResources().getString(R.string.training_id_tag);

        FloatingActionButton fab_add = findViewById(R.id.fab_add_training);
        fab_add.setOnClickListener(this);

        mTrainingsRecyclerView = findViewById(R.id.rv_trainings);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mTrainingsRecyclerView.setLayoutManager(layoutManager);
        mTrainingsRecyclerView.setHasFixedSize(true);

        mTrainingsAdapter = new TrainingsAdapter(this, this);
        mTrainingsManager = TrainingsManager.getInstance(getApplicationContext());

        getSupportLoaderManager().initLoader(ID_LOADER, Bundle.EMPTY, mTrainingsManager);

        mTrainingsManager.setCallback(mTrainingsAdapter);
        mTrainingsRecyclerView.setAdapter(mTrainingsAdapter);


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
            showEditDialog(0);
            return true;
        } else if (id == R.id.menu_settings) {
            return false;
        }
        return super.onOptionsItemSelected(item);
    }


    private void showEditDialog(long id) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        DialogFragment newFragment = TrainingEditDialog.newInstance();
        if (id > 0) {
            Bundle bundle = new Bundle();
            bundle.putLong(trainingIdTag, id);
            newFragment.setArguments(bundle);
        }
        newFragment.show(ft, "dialog");
    }

    @Override
    public void onTrainingClick(long id) {
        //Training trainingItem = mTrainingsManager.getTraining(id);

        //Toast.makeText(this, id + "-id : name" + trainingItem.getName(), Toast.LENGTH_SHORT).show();
        if (id == 0) {
            Toast.makeText(this, "Wrong id: " + id, Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(this, ExercisesActivity.class);
        intent.putExtra(trainingIdTag, id);

        startActivity(intent);

    }

    @Override
    public void onTrainingLongClick(long id) {
        if (id == 0) {
            Toast.makeText(this, "Wrong id: " + id, Toast.LENGTH_SHORT).show();
            return;
        }
        showEditDialog(id);
    }

    @Override
    public void onRefresh() {
        getSupportLoaderManager().restartLoader(ID_LOADER, Bundle.EMPTY, mTrainingsManager);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("TrainingsActivity", "destroy");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_add_training:
                showEditDialog(0);
                break;

        }
    }
}
