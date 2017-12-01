package com.portfex.amazingday.Model;

import java.util.ArrayList;

/**
 * Created by alexanderkozlov on 12/1/17.
 */

public interface TrainingsChangedCallback {
    void updateTrainingsList(ArrayList<TrainingItem> trainings);
}
