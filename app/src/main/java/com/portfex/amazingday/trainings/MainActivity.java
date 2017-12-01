package com.portfex.amazingday.trainings;


import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.portfex.amazingday.Model.TrainingItem;
import com.portfex.amazingday.Model.TrainingManager;
import com.portfex.amazingday.R;


public class MainActivity extends AppCompatActivity implements TrainingOnClickHandler {

    private RecyclerView mTrainingRecyclerView;

    TrainingManager mTrainingManager;
    TrainingAdapter mTrainingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setElevation(0);


        mTrainingRecyclerView = findViewById(R.id.rv_trainings);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mTrainingRecyclerView.setLayoutManager(layoutManager);
        mTrainingRecyclerView.setHasFixedSize(true);

        mTrainingManager = TrainingManager.getInstance(getApplicationContext());

        mTrainingAdapter = new TrainingAdapter(this,this);

        mTrainingManager.setCallback(mTrainingAdapter);

        mTrainingRecyclerView.setAdapter(mTrainingAdapter);

        mTrainingManager.updateTrainingsView();


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

            //trainingManager.showCreateTrainingDialog();
           //startActivity(new Intent(this, SettingsActivity.class));
            return true;

        } else if (id == R.id.menu_settings) {
            return false;
        }
        return super.onOptionsItemSelected(item);
    }

//
//    @Override
//    public boolean onLongClick(View v) {
//
//        long id = (long) v.getTag();
//
//        Toast.makeText(v.getContext(),  id+"-id : I'm cklicked", Toast.LENGTH_SHORT).show();
//        return true;
//    }

    private void showEditDialog(long id){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        DialogFragment newFragment = TrainingEditDialog.newInstance();
        if (id>0) {
            Bundle bundle = new Bundle();
            bundle.putLong("id", id);
            newFragment.setArguments(bundle);
        }
        newFragment.show(ft, "dialog");

    }



    @Override
    public void onClick(long id) {
        TrainingItem trainingItem= mTrainingManager.getTraining(id);

        Toast.makeText(this, id+"-id : name" + trainingItem.getName(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onLongClick(long id) {
        TrainingItem trainingItem= mTrainingManager.getTraining(id);

        if (trainingItem == null) {
            return;
        }
        showEditDialog(id);

        //Toast.makeText(this, id+"-id : longclick name " + trainingItem.getName(), Toast.LENGTH_SHORT).show();
    }
}
