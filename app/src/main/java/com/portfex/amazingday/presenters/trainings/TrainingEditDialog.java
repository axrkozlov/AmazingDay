package com.portfex.amazingday.presenters.trainings;

import android.app.TimePickerDialog;
import android.support.v7.app.AlertDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.portfex.amazingday.model.training.Training;
import com.portfex.amazingday.model.training.TrainingsManager;
import com.portfex.amazingday.R;
import com.portfex.amazingday.utilites.DateUtils;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by alexanderkozlov on 11/29/17.
 */

public class TrainingEditDialog extends DialogFragment implements View.OnClickListener {

    private TrainingsManager mTrainingsManager;
    private Training mTrainingOld;
    private View mTrainingCreateView;
    private Boolean startTimeChanged;
    private Button mEditCreate;
    private Button mEditUpdate;
    private Button mEditDuplicate;
    private Button mEditDelete;
    private Button mEditStartTime;
    private EditText mEditName;
    private EditText mEditDesc;
    private ToggleButton mEditDay1;
    private ToggleButton mEditDay2;
    private ToggleButton mEditDay3;
    private ToggleButton mEditDay4;
    private ToggleButton mEditDay5;
    private ToggleButton mEditDay6;
    private ToggleButton mEditDay7;
    private String trainingIdTag;
    private long mId;
    private AlertDialog mTrainingCreateDialog;

    public static TrainingEditDialog newInstance() {
        return new TrainingEditDialog();
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        trainingIdTag = getResources().getString(R.string.training_id_tag);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            mId = bundle.getLong(trainingIdTag, 0);
        }

        LayoutInflater inflater = LayoutInflater.from(getContext());

        mTrainingCreateView = inflater.inflate(R.layout.activity_training_edit, null);
        mTrainingsManager = TrainingsManager.getInstance(getContext().getApplicationContext());

        startTimeChanged = false;
        mEditCreate = mTrainingCreateView.findViewById(R.id.bt_training_create);
        mEditUpdate = mTrainingCreateView.findViewById(R.id.bt_training_update);
        mEditDuplicate = mTrainingCreateView.findViewById(R.id.bt_training_duplicate);
        mEditDelete = mTrainingCreateView.findViewById(R.id.bt_training_delete);
        mEditStartTime = mTrainingCreateView.findViewById(R.id.bt_training_create_start_time);
        mEditName = mTrainingCreateView.findViewById(R.id.training_create_name);
        mEditDesc = mTrainingCreateView.findViewById(R.id.training_create_desc);
        mEditDay1 = mTrainingCreateView.findViewById(R.id.tb_day1);
        mEditDay2 = mTrainingCreateView.findViewById(R.id.tb_day2);
        mEditDay3 = mTrainingCreateView.findViewById(R.id.tb_day3);
        mEditDay4 = mTrainingCreateView.findViewById(R.id.tb_day4);
        mEditDay5 = mTrainingCreateView.findViewById(R.id.tb_day5);
        mEditDay6 = mTrainingCreateView.findViewById(R.id.tb_day6);
        mEditDay7 = mTrainingCreateView.findViewById(R.id.tb_day7);

        if (mId > 0) {
            mTrainingOld = mTrainingsManager.getTraining(mId);
            if (mTrainingOld != null) {
                mEditCreate.setVisibility(View.GONE);
                mEditUpdate.setVisibility(View.VISIBLE);
                mEditDuplicate.setVisibility(View.VISIBLE);
                mEditDelete.setVisibility(View.VISIBLE);
                bindTrainingToEdit();
            }
        }

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
        mBuilder.setView(mTrainingCreateView);
        mTrainingCreateDialog = mBuilder.create();
        mTrainingCreateDialog.show();

        mEditCreate.setOnClickListener(this);
        mEditStartTime.setOnClickListener(this);
        mEditUpdate.setOnClickListener(this);
        mEditDuplicate.setOnClickListener(this);
        mEditDelete.setOnClickListener(this);


        return mTrainingCreateDialog;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_training_create:
                createTraining();
                break;
            case R.id.bt_training_create_start_time:
                showTimePickerDialog();
                break;
            case R.id.bt_training_update:
                updateTraining();
                break;

            case R.id.bt_training_delete:
                removeTraining();
                break;

        }
    }

    private void bindTrainingToEdit() {

        if (mTrainingOld == null) {
            return;
        }
        mEditName.setText(mTrainingOld.getName());
        mEditDesc.setText(mTrainingOld.getDescription());

        ArrayList<Boolean> weekDays = DateUtils.parseWeekDays(mTrainingOld.getWeekDaysComposed());
        mEditDay1.setChecked(weekDays.get(0));
        mEditDay2.setChecked(weekDays.get(1));
        mEditDay3.setChecked(weekDays.get(2));
        mEditDay4.setChecked(weekDays.get(3));
        mEditDay5.setChecked(weekDays.get(4));
        mEditDay6.setChecked(weekDays.get(5));
        mEditDay7.setChecked(weekDays.get(6));

        long startTimeLong = mTrainingOld.getStartTime();
        if (startTimeLong > 0) {
            mEditStartTime.setText(DateUtils.getTimeString(startTimeLong));
        }

    }

    private void createTraining() {
        if (mEditName.getText().length() == 0) {
            Toast.makeText(getContext(), "Please, type Name of workout", Toast.LENGTH_SHORT).show();
            return;
        }
        Training training = fill(new Training());
        mTrainingsManager.insertTraining(training);
        mTrainingCreateDialog.dismiss();
    }

    private void updateTraining() {
        if (mId == 0) {
            return;
        }
        if (mEditName.getText().length() == 0) {
            Toast.makeText(getContext(), "Please, type Name of workout", Toast.LENGTH_SHORT).show();
            return;
        }
        Training training = fill(new Training(mId));
        mTrainingsManager.updateTraining(training);
        mTrainingCreateDialog.dismiss();
    }


    private Training fill(Training training) {
        if (training == null) {
            return null;
        }
        training.setName(mEditName.getText().toString());
        training.setDescription(mEditDesc.getText().toString());
        ArrayList<Boolean> weekDays = new ArrayList<>();
        weekDays.add(mEditDay1.isChecked());
        weekDays.add(mEditDay2.isChecked());
        weekDays.add(mEditDay3.isChecked());
        weekDays.add(mEditDay4.isChecked());
        weekDays.add(mEditDay5.isChecked());
        weekDays.add(mEditDay6.isChecked());
        weekDays.add(mEditDay7.isChecked());

        training.setWeekDaysComposed(DateUtils.composeWeekDays(weekDays));

        if (startTimeChanged) {
            long startTimeLong = DateUtils.getTimeMillis(mEditStartTime.getText().toString());
            training.setStartTime(startTimeLong);
        }
        return training;

    }


    private void removeTraining() {
        mTrainingsManager.removeTraining(mId);
        mTrainingCreateDialog.dismiss();
    }


    public void showTimePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        mEditStartTime.setText(String.format("%02d:%02d", hourOfDay, minute));
                        startTimeChanged = true;

                    }

                }, hour, minute, true);
        timePickerDialog.show();
    }

}
