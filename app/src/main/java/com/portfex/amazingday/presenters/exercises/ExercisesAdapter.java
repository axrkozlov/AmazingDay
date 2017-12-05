package com.portfex.amazingday.presenters.exercises;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.portfex.amazingday.R;
import com.portfex.amazingday.model.exercise.Exercise;

import java.util.ArrayList;

/**
 * Created by alexanderkozlov on 12/5/17.
 */

public class ExercisesAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private ArrayList<Exercise> mExercises;
    private ExerciseClickHandler mExerciseClickHandler;

    public ExercisesAdapter(Context mContext, ExerciseClickHandler mExerciseClickHandler) {
        this.mContext = mContext;
        this.mExerciseClickHandler = mExerciseClickHandler;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.exercise_item, parent, false);
        ExerciseViewHolder viewHolder = new ExerciseViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        if (mExercises == null) {
            return 10;
        }
        return mExercises.size();

    }

    class ExerciseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        View info_bar;


        public ExerciseViewHolder(View itemView) {
            super(itemView);
            info_bar = itemView.findViewById(R.id.exercise_info_bar);

            itemView.setOnLongClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            if (info_bar.getVisibility() == View.VISIBLE) {
            info_bar.setVisibility(View.GONE);
            } else {
                info_bar.setVisibility(View.VISIBLE);
            }
            //Toast.makeText(mContext, "id"+info_bar.toString(), Toast.LENGTH_SHORT).show();

        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }
}
