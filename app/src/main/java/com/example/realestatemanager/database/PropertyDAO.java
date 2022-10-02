package com.example.realestatemanager.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.realestatemanager.models.Photo;
import com.example.realestatemanager.models.Property;
import com.example.realestatemanager.models.PropertyWithPhoto;

import java.util.List;

@Dao
public abstract class PropertyDAO {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void createProperty(Property property);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void createPhotos(List <Photo> photos);

    @Transaction
    public void createPropertyWithPhotos(Property property, List <Photo> photos){
        createProperty(property);
        createPhotos(photos);
    }

    @Update(onConflict = OnConflictStrategy.REPLACE)
    public abstract void updateProperty(Property property);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    public abstract void updatePhotos (List <Photo> photos);

    @Transaction
    public void updatePropertyWithPhotos(Property property, List <Photo> photos){
        updateProperty(property);
        updatePhotos(photos);
    }

    @Query("SELECT * FROM Property WHERE id = :propertyId")
    public abstract LiveData <PropertyWithPhoto> getProperty(long propertyId);


    @Query("SELECT * FROM Property")
    public abstract LiveData <List<PropertyWithPhoto>> getProperties();


}
