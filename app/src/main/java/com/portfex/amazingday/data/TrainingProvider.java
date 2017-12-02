package com.portfex.amazingday.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static com.portfex.amazingday.data.TrainingContract.*;
import static com.portfex.amazingday.data.TrainingContract.TrainingEntry.*;

/**
 * Created by alexanderkozlov on 12/2/17.
 */

public class TrainingProvider extends ContentProvider {

    public static final String TAG = TrainingProvider.class.getSimpleName();

    private static final int TRAININGS = 100;
    private static final int TRAINING_ID = 101;
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private TrainingDbHelper mDbHelper;

    public static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

        matcher.addURI(TrainingContract.CONTENT_AUTHORITY, PATH_TRAININGS, TRAININGS);
        matcher.addURI(TrainingContract.CONTENT_AUTHORITY, PATH_TRAININGS + "/#", TRAINING_ID);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        mDbHelper = new TrainingDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor;
        switch (sUriMatcher.match(uri)){
            case TRAINING_ID:
                cursor = mDbHelper.getReadableDatabase().query(
                        TRAININGS_TABLE_NAME,
                        projection,
                        TrainingContract.TrainingEntry._ID + " = ? ",
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case TRAININGS:
                cursor = mDbHelper.getReadableDatabase().query(
                        TRAININGS_TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int numRowsDeleted;

        if (null == selection) selection = "1";

        switch (sUriMatcher.match(uri)) {

            case TRAININGS:
                numRowsDeleted = mDbHelper.getWritableDatabase().delete(
                        TRAININGS_TABLE_NAME,
                        selection,
                        selectionArgs);

                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (numRowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return numRowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
