package com.portfex.amazingday.amazingday.Training;

import android.content.Context;
import android.database.Cursor;
import android.net.ParseException;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.portfex.amazingday.amazingday.R;
import com.portfex.amazingday.amazingday.data.TrainingContract;

import java.sql.Date;

/**
 * Created by alexanderkozlov on 11/16/17.
 */

public class TrainingAdapter extends RecyclerView.Adapter<TrainingAdapter.TrainingViewHolder>{

    private Cursor mCursor;
    private Context mContext;

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

        long startTime = mCursor.getLong(mCursor.getColumnIndex(TrainingContract.TrainingEntry.TRAININGS_COLUMN_START_TIME));

        Date lastDate=new Date(startTime);

        holder.nameView.setText(name);
        holder.descView.setText(desc+";"+lastDate.toString());



        //holder.listItemNumberView.setText(Integer.toString(position));
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public static class TrainingViewHolder extends RecyclerView.ViewHolder{
        TextView nameView;
        TextView descView;
        public TrainingViewHolder(View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.training_title);
            descView = itemView.findViewById(R.id.training_desc);
        }
    }
}



