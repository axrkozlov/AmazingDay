package com.portfex.amazingday.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by alexanderkozlov on 11/18/17.
 */

public class TrainingContract {
    public static final String CONTENT_AUTHORITY = "com.portfex.amazingday";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_TRAININGS = "training";

    public static final class TrainingEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_TRAININGS).build();


        public static final String TRAININGS_TABLE_NAME = "trainings";

        public static final String TRAININGS_COLUMN_NAME = "name";

        public static final String TRAININGS_COLUMN_DESCRIPTION = "description";

        public static final String TRAININGS_COLUMN_REPEAT = "repeat";

        public static final String TRAININGS_COLUMN_START_TIME = "start_time";

        public static final String TRAININGS_COLUMN_TOTAL_TIME = "total_time";

        public static final String TRAININGS_COLUMN_LAST_DATE = "last_date";


        public static final String EXERCISES_TABLE_NAME = "exercises";

        public static final String EXERCISES_COLUMN_NAME = "name";

        public static final String EXERCISES_COLUMN_TRAINING_ID = "training_id";

        public static final String EXERCISES_COLUMN_DESCRIPTION = "description";

        public static final String EXERCISES_COLUMN_NORM_TIME = "norm_time";

        public static final String EXERCISES_COLUMN_NORM_REPS = "norm_reps";

        public static final String EXERCISES_COLUMN_SETS = "sets";

        public static final String EXERCISES_COLUMN_WEIGHT = "weight";

        public static final String EXERCISES_COLUMN_REST_TIME = "rest_time";


        /*
        TODO results table

      */


    }
}
