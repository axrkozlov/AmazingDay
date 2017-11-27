package com.portfex.amazingday.utilites;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by alexanderkozlov on 11/27/17.
 */

public final class DateUtils {
    public static int composeWeekDays(ArrayList<Boolean> isDayActive) {
        int result = 0;
        for (int i = 0; i < 7; ++i) {
            result |= ((isDayActive.get(i) ? 1 : 0) << i);
        }
        return result;
    }


    public static ArrayList<Boolean> parseWeekDays(Integer fromBase) {
        ArrayList<Boolean> result = new ArrayList<>();
        Integer temp_result;
        String string_result = "AA";
        for (Integer i = 0; i < 7; ++i) {
            temp_result = (fromBase >> i) & 1;
            string_result = string_result + Boolean.toString(((fromBase >> i) & 1) == 1);
            result.add(((fromBase >> i) & 1) == 1);
            //result.add(fromBase & (i << 1)  ? true:false );
        }
        Log.e("WEEK_DAYS_PARSE", "froBase:" + Integer.toString(fromBase) + " result:" + string_result);


        return result;
    }

}
