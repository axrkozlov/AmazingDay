package com.portfex.amazingday.utilites;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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

    public static ArrayList<Boolean> parseWeekDays(int fromBase) {
        ArrayList<Boolean> result = new ArrayList<>();
        for (int i = 0; i < 7; ++i) {
            result.add(((fromBase >> i) & 1) == 1);
        }
        return result;
    }

    public static Long getTimeMillis(String time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        if (time.length() == 0) {
            return 0L;
        }

        try {
            Date date = dateFormat.parse(time);
            return date.getTime();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0L;
    }
//    public static String normalizeTimeString(String time) {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
//        if (time.length() == 0) {
//            return "";
//        }
//
//        try {
//            Date date = dateFormat.parse(time);
//            return date.getTime();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return 0L;
//    }

    public static String getTimeString(Long time_ms) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        if (time_ms <= 0) {
            return "";
        }

        try {
            Date date = new Date(time_ms);
            return dateFormat.format(date).toString();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

//    public static ArrayList<Boolean> parseWeekDays(Integer fromBase) {
//        ArrayList<Boolean> result = new ArrayList<>();
//        Integer temp_result;
//        String string_result = "AA";
//        for (Integer i = 0; i < 7; ++i) {
//            temp_result = (fromBase >> i) & 1;
//            string_result = string_result + Boolean.toString(((fromBase >> i) & 1) == 1);
//            result.add(((fromBase >> i) & 1) == 1);
//            //result.add(fromBase & (i << 1)  ? true:false );
//        }
//        Log.e("WEEK_DAYS_PARSE", "froBase:" + Integer.toString(fromBase) + " result:" + string_result);
//
//
//        return result;
//    }

}
