package com.portfex.amazingday.presenters.trainings;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.portfex.amazingday.model.training.TrainingsCallback;
import com.portfex.amazingday.model.training.Training;
import com.portfex.amazingday.R;
import com.portfex.amazingday.utilites.DateUtils;

import java.text.DateFormatSymbols;
import java.util.ArrayList;

/**
 * Created by alexanderkozlov on 11/16/17.
 */

public class TrainingsAdapter extends RecyclerView.Adapter<TrainingsAdapter.TrainingViewHolder> implements TrainingsCallback {

    private Context mContext;
    private ArrayList<Training> mTrainings;
    private TrainingClickHandler mTrainingClickHandler;

    public TrainingsAdapter(Context context, TrainingClickHandler trainingClickHandler) {
        this.mTrainingClickHandler = trainingClickHandler;
        this.mContext = context;
    }


    @Override
    public TrainingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.training_item, parent, false);
        TrainingViewHolder viewHolder = new TrainingViewHolder(view);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(TrainingViewHolder holder, int position) {

        Training training = mTrainings.get(position);
        if (training == null) {
            return;
        }

        holder.nameView.setText(training.getName());
        holder.descView.setText(training.getDescription());

        String[] weekDaysText = DateFormatSymbols.getInstance().getShortWeekdays();
        ArrayList<Boolean> weekDays = DateUtils.parseWeekDays(training.getWeekDaysComposed());
        StringBuffer sb = new StringBuffer("");

        for (int i = 0; i < 7; i++) {
            if (weekDays.get(i)) {
                sb.append(weekDaysText[i + 1] + " ");
            }
        }

        holder.daysView.setText(sb.toString());
        holder.startTimeView.setText(DateUtils.getTimeString(training.getStartTime()));
        holder.totalTimeView.setText(DateUtils.getTimeString(training.getTotalTime()));
        holder.itemView.setTag(training.getId());

    }

    @Override
    public int getItemCount() {
        if (mTrainings == null) {
            return 0;
        }
        return mTrainings.size();
    }

    @Override
    public void refreshView(ArrayList<Training> trainings) {
        this.mTrainings = trainings;
        if (mTrainings != null) {
            this.notifyDataSetChanged();
        }
    }

    class TrainingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView nameView;
        TextView descView;
        TextView totalTimeView;
        TextView daysView;
        TextView startTimeView;

        public TrainingViewHolder(View itemView) {
            super(itemView);
            itemView.setOnLongClickListener(this);
            itemView.setOnClickListener(this);
            nameView = itemView.findViewById(R.id.training_title);
            descView = itemView.findViewById(R.id.training_desc);
            totalTimeView = itemView.findViewById(R.id.training_desc_total_time);
            daysView = itemView.findViewById(R.id.training_desc_days);
            startTimeView = itemView.findViewById(R.id.training_desc_start_time);

        }

        @Override
        public void onClick(View v) {
            mTrainingClickHandler.onTrainingClick((long) v.getTag());
        }

        @Override
        public boolean onLongClick(View v) {

            mTrainingClickHandler.onTrainingLongClick((long) v.getTag());

            //int adapterPosition = getAdapterPosition();
            //Toast.makeText(v.getContext(), adapterPosition+"-pos", Toast.LENGTH_SHORT).show();
            return true;
        }
    }


//        @Override
//        public boolean onTrainingLongClick(View v) {
//            int adapterPosition = getAdapterPosition();
//
//            long id = (long) v.getTag();
//
//            Toast.makeText(v.getContext(), adapterPosition+"-pos, "+ id+"-id : I'm cklicked", Toast.LENGTH_SHORT).show();
//            return true;
//        }
//    }
//
//    public static class TrainingViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener{
//        TextView nameView;
//        TextView descView;
//        TextView totalTimeView;
//        TextView daysView;
//        TextView startTimeView;
//        public TrainingViewHolder(View itemView) {
//            super(itemView);
//            itemView.setOnLongClickListener(this);
//            nameView = itemView.findViewById(R.id.training_title);
//            descView = itemView.findViewById(R.id.training_desc);
//            totalTimeView = itemView.findViewById(R.id.training_desc_total_time);
//            daysView = itemView.findViewById(R.id.training_desc_days);
//            startTimeView = itemView.findViewById(R.id.training_desc_start_time);
//
//        }


//    public void refreshAdapter(ArrayList<Training> allTrainings) {
//        this.mTrainings = allTrainings;
//        if (allTrainings != null) {
//            this.notifyDataSetChanged();
//        }
//    }

//    public void swapCursor(Cursor newCursor) {
//        if (mCursor != null) mCursor.close();
//        mCursor = newCursor;
//        if (newCursor != null) {
//            this.notifyDataSetChanged();
//        }
//    }

}



