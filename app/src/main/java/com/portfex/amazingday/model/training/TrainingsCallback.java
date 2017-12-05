package com.portfex.amazingday.model.training;

import java.util.ArrayList;

/**
 * Created by alexanderkozlov on 12/1/17.
 */

public interface TrainingsCallback {
    void refreshView(ArrayList<Training> trainings);
}
