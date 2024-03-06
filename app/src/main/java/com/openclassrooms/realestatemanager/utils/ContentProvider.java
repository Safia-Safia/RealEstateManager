package com.openclassrooms.realestatemanager.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.openclassrooms.realestatemanager.dao.EstateDao;
import com.openclassrooms.realestatemanager.model.Estate;
import com.openclassrooms.realestatemanager.repository.EstateDatabase;

public class ContentProvider extends android.content.ContentProvider {
    public static final String AUTHORITY = "com.openclassrooms.realestatemanager.provider";
    public static final String TABLE_NAME_ESTATE = Estate.class.getSimpleName();
    public static final Uri URI_ESTATE = Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME_ESTATE);

    private EstateDao estateDao;

    @Override
    public boolean onCreate() {
        Context context = getContext();
        if (context != null) {
            estateDao = EstateDatabase.getInstance(context).estateDao();
        }
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri,
                        @Nullable String[] projection,
                        @Nullable String selection,
                        @Nullable String[] selectionArgs,
                        @Nullable String sortOrder) {
        if (estateDao != null) {
            final Cursor cursor = estateDao.getEstateCursor();
            cursor.setNotificationUri(getContext().getContentResolver(), URI_ESTATE);
            return cursor;
        }

        throw new IllegalArgumentException("Failed to query row for uri " + uri);
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
    public int delete(@NonNull Uri uri,
                      @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri,
                      @Nullable ContentValues values,
                      @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        return 0;
    }
}
