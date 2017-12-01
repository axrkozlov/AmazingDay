package com.portfex.amazingday.trainings;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.portfex.amazingday.Model.TrainingsChangedCallback;
import com.portfex.amazingday.Model.TrainingItem;
import com.portfex.amazingday.R;
import com.portfex.amazingday.utilites.DateUtils;

import java.text.DateFormatSymbols;
import java.util.ArrayList;

/**
 * Created by alexanderkozlov on 11/16/17.
 */

public class TrainingAdapter extends RecyclerView.Adapter<TrainingAdapter.TrainingViewHolder> implements TrainingsChangedCallback {

//    private Cursor mCursor;
    private Context mContext;
    private ArrayList<TrainingItem> mTrainings;
    private TrainingOnClickHandler mClickHandler;

    public TrainingAdapter(Context context,TrainingOnClickHandler clickHandler) {
        this.mClickHandler=clickHandler;
        this.mContext = context;
    }


    @Override
    public TrainingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.training_item, parent, false);
        TrainingViewHolder  viewHolder = new TrainingViewHolder(view);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(TrainingViewHolder holder, int position) {

        TrainingItem trainingItem = mTrainings.get(position);
        if (trainingItem == null) {
            return;
        }

        holder.nameView.setText(trainingItem.getName());
        holder.descView.setText(trainingItem.getDescription());

        String[] weekDaysText= DateFormatSymbols.getInstance().getShortWeekdays();
        ArrayList<Boolean> weekDays = DateUtils.parseWeekDays(trainingItem.getWeekDaysComposed());
        StringBuffer sb = new StringBuffer("");

        for (int i = 0; i < 7; i++) {
            if (weekDays.get(i)){
                sb.append( weekDaysText[i+1]+" ");
            }
        }

        holder.daysView.setText(sb.toString());
        holder.startTimeView.setText(DateUtils.getTimeString(trainingItem.getStartTime()));
        holder.totalTimeView.setText(DateUtils.getTimeString(trainingItem.getTotalTime()));
        holder.itemView.setTag(trainingItem.getId());


    }

    @Override
    public int getItemCount() {
        if (mTrainings == null) {
            return 0;
        }
        return mTrainings.size();
    }

    @Override
    public void updateTrainingsList(ArrayList<TrainingItem> trainings) {
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
            mClickHandler.onClick((long) v.getTag());
        }

        @Override
        public boolean onLongClick(View v) {

            mClickHandler.onLongClick((long) v.getTag());

            //int adapterPosition = getAdapterPosition();
            //Toast.makeText(v.getContext(), adapterPosition+"-pos", Toast.LENGTH_SHORT).show();
            return true;
        }
    }



//        @Override
//        public boolean onLongClick(View v) {
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


//    public void refreshAdapter(ArrayList<TrainingItem> allTrainings) {
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



