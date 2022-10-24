package com.example.realestatemanager.repository;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.sqlite.db.SimpleSQLiteQuery;

import com.example.realestatemanager.database.PropertyDAO;
import com.example.realestatemanager.models.Photo;
import com.example.realestatemanager.models.Property;
import com.example.realestatemanager.models.PropertyWithPhoto;

import java.util.List;

public class PropertyRepository {

    private final PropertyDAO propertyDAO;

    public PropertyRepository(PropertyDAO propertyDao) {
        this.propertyDAO = propertyDao;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void createPropertyWithPhotos(Property property, List <Photo> photos){
        propertyDAO.createPropertyWithPhotos(property, photos);
    }


    public void updatePropertyWithPhotos(Property property, List <Photo> photos){
        this.propertyDAO.updateProperty(property);
        this.propertyDAO.createPhotos(photos);
    }


    public LiveData<PropertyWithPhoto> getProperty(long propertyId){
        return this.propertyDAO.getProperty(propertyId);
    }


    public LiveData <List<PropertyWithPhoto>> getProperties(){
        return this.propertyDAO.getProperties();
    }

    public void deleteById(long id){
         this.propertyDAO.deleteById(id);
    }

    public LiveData <List<PropertyWithPhoto>> getPropertiesFiltered(SimpleSQLiteQuery query){
        return this.propertyDAO.getPropertiesFiltered(query);
    }
}
