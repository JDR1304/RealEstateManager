package com.example.realestatemanager.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.example.realestatemanager.database.RealEstateManagerDataBase;
import com.example.realestatemanager.models.PropertyWithPhoto;

public class PropertyWithPhotoContentProvider extends ContentProvider {

    public static final String AUTHORITY = "com.example.realestatemanager.provider";

    public static final String TABLE_NAME = PropertyWithPhoto.class.getSimpleName();

    public static final Uri URI_PROPERTY_WITH_PHOTO = Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);

    @Override
    public boolean onCreate() {
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        if (getContext() != null) {

            long userId = ContentUris.parseId(uri);

            final Cursor cursor = RealEstateManagerDataBase.getInstance(getContext()).propertyDao().getPropertyWithPhotoCursor(userId);

            cursor.setNotificationUri(getContext().getContentResolver(), uri);

            return cursor;

        }

        throw new IllegalArgumentException("Failed to query row for uri " + uri);

    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return "vnd.android.cursor.item/" + AUTHORITY + "." + TABLE_NAME;
    }


    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
