package com.example.realestatemanager.repository;

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


    public void createPropertyWithPhotos(Property property, List <Photo> photos){
        this.propertyDAO.createProperty(property);
        this.propertyDAO.createPhotos(photos);

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
