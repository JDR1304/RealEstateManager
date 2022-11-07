package com.example.realestatemanager;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import androidx.room.Room;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.*;

import com.example.realestatemanager.database.RealEstateManagerDataBase;
import com.example.realestatemanager.provider.PropertyWithPhotoContentProvider;


@RunWith(AndroidJUnit4.class)
public class PropertyWithPhotoContentProviderTest {

    // FOR DATA
    private ContentResolver mContentResolver;

    // DATA SET FOR TEST
    private static long PROPERTY_ID = 1;

    @Before

    public void setUp() {

        Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().getContext()
                , RealEstateManagerDataBase.class).allowMainThreadQueries().build();

        mContentResolver = InstrumentationRegistry.getInstrumentation().getContext().getContentResolver();

    }


    @Test
    public void getPropertyWithPhoto() {

        final Cursor cursor = mContentResolver.query(ContentUris.withAppendedId(PropertyWithPhotoContentProvider.URI_PROPERTY_WITH_PHOTO, PROPERTY_ID), null, null, null, null);

        assertThat(cursor, notNullValue());

        assertThat(cursor.moveToFirst(), is(true));

        assertThat(cursor.getString(cursor.getColumnIndexOrThrow("propertyType")), is("Apartment"));

        assertThat(cursor.getDouble(cursor.getColumnIndexOrThrow("priceInDollars")), is(1000000.0));

        assertThat(cursor.getInt(cursor.getColumnIndexOrThrow("pointsOfInterestSchool")), is(0));

    }

}