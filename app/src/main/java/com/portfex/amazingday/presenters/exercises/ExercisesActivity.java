package com.portfex.amazingday.presenters.exercises;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.portfex.amazingday.R;
import com.portfex.amazingday.model.exercise.ExercisesManager;
import com.portfex.amazingday.model.training.TrainingsManager;
import com.portfex.amazingday.presenters.trainings.TrainingsAdapter;

import static com.portfex.amazingday.model.training.TrainingsManager.ID_LOADER;

public class ExercisesActivity extends AppCompatActivity implements ExerciseClickHandler, View.OnClickListener {

    long mId;
    private String trainingIdTag;
    private RecyclerView mExercisesRecyclerView;
    ExercisesManager mExercisesManager;
    ExercisesAdapter mExercisesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(0);

        trainingIdTag = getResources().getString(R.string.training_id_tag);

        FloatingActionButton fab = findViewById(R.id.fab_add_exercises);
        fab.setOnClickListener(this);
        Intent intent = getIntent();
        if (intent.hasExtra(trainingIdTag)) {
            mId = intent.getLongExtra(trainingIdTag, 0);
            Toast.makeText(this, "ID:" + Long.toString(mId), Toast.LENGTH_SHORT).show();
        }

        mExercisesRecyclerView = findViewById(R.id.rv_exercises);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mExercisesRecyclerView.setLayoutManager(layoutManager);
        mExercisesRecyclerView.setHasFixedSize(true);

        mExercisesAdapter = new ExercisesAdapter( this, this);
        //mExercisesManager = TrainingsManager.getInstance(getApplicationContext());

        //getSupportLoaderManager().initLoader(ID_LOADER, Bundle.EMPTY, mExercisesManager);

        //mExercisesManager.setCallback(mExercisesAdapter);
        mExercisesRecyclerView.setAdapter(mExercisesAdapter);

        //exerciseIdTag = getResources().getString(R.string.exercise_id_tag);

    }

    @Override
    public void onClick(View v) {
        Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @Override
    public void onExerciseClick(long id) {

    }

    @Override
    public void onExerciseLongClick(long id) {

    }
}
