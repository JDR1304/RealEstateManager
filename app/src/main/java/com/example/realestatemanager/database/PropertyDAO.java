package com.example.realestatemanager.database;

import android.database.Cursor;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.room.Transaction;
import androidx.room.Update;
import androidx.sqlite.db.SupportSQLiteQuery;

import com.example.realestatemanager.models.Photo;
import com.example.realestatemanager.models.Property;
import com.example.realestatemanager.models.PropertyWithPhoto;

import java.util.List;

@Dao
public abstract class PropertyDAO {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract long createProperty(Property property);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void createPhotos(List <Photo> photos);

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Transaction
    public void createPropertyWithPhotos(Property property, List <Photo> photos){
        long propertyId = createProperty(property);
        photos.forEach(photo -> photo.setPropertyId(propertyId));
        createPhotos(photos);

    }

    @Update(onConflict = OnConflictStrategy.REPLACE)
    public abstract void updateProperty(Property property);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    public abstract void updatePhotos (List <Photo> photos);

    @Transaction
    public void updatePropertyWithPhotos(Property property, List <Photo> photos){
        updateProperty(property);
        createPhotos(photos);
    }

    @Query("SELECT * FROM Property WHERE id = :propertyId")
    public abstract LiveData <PropertyWithPhoto> getProperty(long propertyId);


    @Query("SELECT * FROM Property")
    public abstract LiveData <List<PropertyWithPhoto>> getProperties();

    @Query("DELETE FROM Photo WHERE id = :id")
    public abstract void deleteById(long id);

    @RawQuery
    public abstract LiveData <List<PropertyWithPhoto>> getPropertiesFiltered(SupportSQLiteQuery query);

    /*
    Methode for the content provider
     */
    @Query("SELECT * FROM Property JOIN Photo ON Property.id = Photo.propertyId WHERE Property.id = :propertyId")
    public abstract Cursor getPropertyWithPhotoCursor (long propertyId);

}
