package com.portfex.amazingday.trainings;


import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import android.support.v4.app.LoaderManager;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.portfex.amazingday.data.TrainingLoader;

import java.util.ArrayList;

import static com.portfex.amazingday.Model.TrainingManager.ID_LOADER;


public class MainActivity extends AppCompatActivity implements TrainingOnClickHandler, SwipeRefreshLayout.OnRefreshListener {

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

        mTrainingAdapter = new TrainingAdapter(this, this);
        mTrainingManager = TrainingManager.getInstance(getApplicationContext());

        getSupportLoaderManager().initLoader(ID_LOADER, Bundle.EMPTY, mTrainingManager);

        mTrainingManager.setCallback(mTrainingAdapter);
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
            showEditDialog(0);

            //trainingManager.showCreateTrainingDialog();
            //startActivity(new Intent(this, SettingsActivity.class));
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
            bundle.putLong("id", id);
            newFragment.setArguments(bundle);
        }
        newFragment.show(ft, "dialog");

    }


    @Override
    public void onClick(long id) {
        //TrainingItem trainingItem = mTrainingManager.getTraining(id);

        //Toast.makeText(this, id + "-id : name" + trainingItem.getName(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onLongClick(long id) {
        if (id==0) {
            Toast.makeText(this, "Wrong id: " + id, Toast.LENGTH_SHORT).show();
            return;
        }
        showEditDialog(id);


    }


    @Override
    public void onRefresh() {

    }
}

//    private class stubLoaderCallbacks implements LoaderManager.LoaderCallbacks<ArrayList<TrainingItem>>{
//
//
//        @Override
//        public android.support.v4.content.Loader<ArrayList<TrainingItem>> onCreateLoader(int id, Bundle args) {
//            if (id==ID_LOADER) return new TrainingLoader(MainActivity.this);
//            return null;
//        }
//
//        @Override
//        public void onLoadFinished(android.support.v4.content.Loader<ArrayList<TrainingItem>> loader, ArrayList<TrainingItem> data) {
//            Toast.makeText(MainActivity.this, "loaded", Toast.LENGTH_SHORT).show();
//        }
//
//        @Override
//        public void onLoaderReset(android.support.v4.content.Loader<ArrayList<TrainingItem>> loader) {
//
//        }
//    }