package com.portfex.amazingday.trainings;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.portfex.amazingday.R;
import com.portfex.amazingday.data.TrainingContract;
import com.portfex.amazingday.utilites.DateUtils;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by alexanderkozlov on 11/16/17.
 */

public class TrainingAdapter extends RecyclerView.Adapter<TrainingAdapter.TrainingViewHolder>{

    private Cursor mCursor;
    private Context mContext;
    View mTrainingItemCreateDialogView;

    public TrainingAdapter(Context context, Cursor cursor) {

        this.mContext = context;
        this.mCursor = cursor;
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

        if (!mCursor.moveToPosition(position)) return;

        String name = mCursor.getString(mCursor.getColumnIndex(TrainingContract.TrainingEntry.TRAININGS_COLUMN_NAME));
        String desc = mCursor.getString(mCursor.getColumnIndex(TrainingContract.TrainingEntry.TRAININGS_COLUMN_DESCRIPTION));
        Integer repeat = mCursor.getInt(mCursor.getColumnIndex(TrainingContract.TrainingEntry.TRAININGS_COLUMN_REPEAT));
        Long descStartTime = mCursor.getLong(mCursor.getColumnIndex(TrainingContract.TrainingEntry.TRAININGS_COLUMN_START_TIME));

        long id = mCursor.getLong(mCursor.getColumnIndex(TrainingContract.TrainingEntry._ID));

        String descTrainingDays="";

        String[] weekDaysText= DateFormatSymbols.getInstance().getShortWeekdays();
        ArrayList<Boolean> weekDays = DateUtils.parseWeekDays(repeat);

        for (int i = 0; i < 7; i++) {
            if (weekDays.get(i)){
                descTrainingDays+=weekDaysText[i+1]+" ";
            }
        }

        String descStartTimeText=DateUtils.getTimeString(descStartTime);

        holder.nameView.setText(name);
        holder.descView.setText(desc);
        holder.descDaysView.setText(descTrainingDays);
        holder.descStartTimeView.setText(descStartTimeText);
        holder.itemView.setTag(id);

        //holder.listItemNumberView.setText(Integer.toString(position));
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public static class TrainingViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener{
        TextView nameView;
        TextView descView;
        TextView descTotalTimeView;
        TextView descDaysView;
        TextView descStartTimeView;
        public TrainingViewHolder(View itemView) {
            super(itemView);
            itemView.setOnLongClickListener(this);
            nameView = itemView.findViewById(R.id.training_title);
            descView = itemView.findViewById(R.id.training_desc);
            descTotalTimeView = itemView.findViewById(R.id.training_desc_total_time);
            descDaysView = itemView.findViewById(R.id.training_desc_days);
            descStartTimeView = itemView.findViewById(R.id.training_desc_start_time);

        }

        @Override
        public boolean onLongClick(View v) {
            int adapterPosition = getAdapterPosition();

            long id = (long) itemView.getTag();

            Toast.makeText(v.getContext(), adapterPosition+"-pos, "+ id+"-id : I'm cklicked", Toast.LENGTH_SHORT).show();

            return true;
        }
    }

    public void swapCursor(Cursor newCursor) {
        if (mCursor != null) mCursor.close();
        mCursor = newCursor;
        if (newCursor != null) {
            this.notifyDataSetChanged();
        }
    }

}



