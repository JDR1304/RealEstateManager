package com.example.realestatemanager.repository;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import com.example.realestatemanager.database.PropertyDAO;
import com.example.realestatemanager.models.Photo;
import com.example.realestatemanager.models.Property;
import com.example.realestatemanager.models.PropertyWithPhoto;

import java.util.List;

public class PropertyRepository {

    private PropertyDAO propertyDAO;

    public PropertyRepository(PropertyDAO propertyDao) {
        this.propertyDAO = propertyDao;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void createPropertyWithPhotos(Property property, List <Photo> photos){
        //this.propertyDAO.createProperty(property);
        //this.propertyDAO.createPhotos(photos);
        propertyDAO.createPropertyWithPhotos(property, photos);
    }


    public void updatePropertyWithPhotos(Property property, List <Photo> photos){
        this.propertyDAO.updateProperty(property);
        this.propertyDAO.updatePhotos(photos);
    }


    public LiveData<PropertyWithPhoto> getProperty(long propertyId){
        return this.propertyDAO.getProperty(propertyId);
    }


    public LiveData <List<PropertyWithPhoto>> getProperties(){
        return this.propertyDAO.getProperties();
    }

}
