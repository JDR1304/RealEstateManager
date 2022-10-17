package com.example.realestatemanager.database;

import android.content.ContentValues;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.OnConflictStrategy;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.realestatemanager.models.Photo;
import com.example.realestatemanager.models.Property;

@Database(entities = {Property.class, Photo.class}, version = 2, exportSchema = false)
public abstract class RealEstateManagerDataBase extends RoomDatabase {

    // --- SINGLETON ---
    private static volatile RealEstateManagerDataBase INSTANCE;

    // --- DAO ---
    public abstract PropertyDAO propertyDao();

    // --- INSTANCE ---
    public static RealEstateManagerDataBase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (RealEstateManagerDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RealEstateManagerDataBase.class, "RealEstateManagerDataBase.db")
                            .addCallback(prepopulateDatabase())
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback prepopulateDatabase() {
        return new RoomDatabase.Callback() {


            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);

                ContentValues contentValues = new ContentValues();
                contentValues.put("propertyType", "Apartment");
                contentValues.put("priceInDollars", 1_000_000);
                contentValues.put("surface", 120);
                contentValues.put("numberOfRooms", 9);
                contentValues.put("numberOfBathrooms", 2);
                contentValues.put("numberOfBedRooms", 3);
                contentValues.put("description", "In the center");
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
                db.insert("Property", OnConflictStrategy.IGNORE, contentValues);

                ContentValues contentValuesProperty2 = new ContentValues();
                contentValuesProperty2.put("propertyType", "Apartment");
                contentValuesProperty2.put("priceInDollars", 1_400_000);
                contentValuesProperty2.put("surface", 100);
                contentValuesProperty2.put("numberOfRooms", 8);
                contentValuesProperty2.put("numberOfBathrooms", 3);
                contentValuesProperty2.put("numberOfBedRooms", 4);
                contentValuesProperty2.put("description", "next to the park");
                contentValuesProperty2.put("pointsOfInterestSchool", false);
                contentValuesProperty2.put("pointsOfInterestPark", true);
                contentValuesProperty2.put("pointsOfInterestStore", true);
                contentValuesProperty2.put("propertyStatus", false);
                contentValuesProperty2.put("propertyEntryDate", 1657121856);
                contentValuesProperty2.put("propertySaleDate", 0);
                contentValuesProperty2.put("realEstateAgentName", "Barry");
                contentValuesProperty2.put("street", "15 boulevard republique");
                contentValuesProperty2.put("postCode", 13410);
                contentValuesProperty2.put("city", "Lambesc");
                db.insert("Property", OnConflictStrategy.IGNORE, contentValuesProperty2);

                ContentValues contentValuesPhoto = new ContentValues();
                contentValuesPhoto.put("propertyId", 1);
                contentValuesPhoto.put("name", "Photo name 1");
                contentValuesPhoto.put("stringPhoto", "URI photo 1");
                db.insert("Photo", OnConflictStrategy.IGNORE, contentValuesPhoto);

                ContentValues contentValuesPhoto2 = new ContentValues();
                contentValuesPhoto2.put("propertyId", 1);
                contentValuesPhoto2.put("name", "Photo name 2");
                contentValuesPhoto2.put("stringPhoto", "URI photo 2");
                db.insert("Photo", OnConflictStrategy.IGNORE, contentValuesPhoto2);
            }
        };
    }
}
