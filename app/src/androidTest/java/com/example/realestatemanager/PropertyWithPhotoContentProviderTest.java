package com.example.realestatemanager;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import androidx.room.OnConflictStrategy;
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


/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class PropertyWithPhotoContentProviderTest {
    // FOR DATA

    private ContentResolver mContentResolver;

    // DATA SET FOR TEST

    private static long USER_ID = 1;

    @Before

    public void setUp() {

        Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().getContext()
                ,

                RealEstateManagerDataBase.class)


                .allowMainThreadQueries()


                .build();


        mContentResolver = InstrumentationRegistry.getInstrumentation().getContext()

                .getContentResolver();

    }

    @Test

    public void getItemsWhenNoItemInserted() {

        final Cursor cursor = mContentResolver.query(ContentUris.withAppendedId(PropertyWithPhotoContentProvider.URI_PROPERTY_WITH_PHOTO, USER_ID), null, null, null, null);

        assertThat(cursor, notNullValue());

        assertThat(cursor.getCount(), is(0));

        cursor.close();

    }



    @Test

    public void getPropertyWithPhoto() {

        // BEFORE : Adding demo item

        //final Uri userUri = mContentResolver.insert(PropertyWithPhotoContentProvider.URI_PROPERTYWITHPHOTO, generatePropertyWithPhoto());

        // TEST

        final Cursor cursor = mContentResolver.query(ContentUris.withAppendedId(PropertyWithPhotoContentProvider.URI_PROPERTY_WITH_PHOTO, USER_ID), null, null, null, null);

        assertThat(cursor, notNullValue());

        //assertThat(cursor.getCount(), is(1));

        assertThat(cursor.moveToFirst(), is(true));

        assertThat(cursor.getString(cursor.getColumnIndexOrThrow("propertyType")), is("Apartment"));

    }

    // ---

    private ContentValues generatePropertyWithPhoto(){

        ContentValues contentValues = new ContentValues();
        contentValues.put("propertyType", "Apartment");
        contentValues.put("priceInDollars", 10_000_000);
        contentValues.put("surface", 100);
        contentValues.put("numberOfRooms", 10);
        contentValues.put("numberOfBathrooms", 5);
        contentValues.put("numberOfBedRooms", 5);
        contentValues.put("description", "Test content provider");
        contentValues.put("pointsOfInterestSchool", false);
        contentValues.put("pointsOfInterestPark", true);
        contentValues.put("pointsOfInterestStore", true);
        contentValues.put("propertyStatus", false);
        contentValues.put("propertyEntryDate", 1657121856);
        contentValues.put("propertySaleDate", 0);
        contentValues.put("realEstateAgentName", "Diaz");
        contentValues.put("street", "35 boulevard republique");
        contentValues.put("postCode", 13410);
        contentValues.put("city", "Lambesc");

        return contentValues;

    }
}