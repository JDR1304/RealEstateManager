package com.example.realestatemanager;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.realestatemanager.models.Photo;
import com.example.realestatemanager.models.Property;
import com.example.realestatemanager.models.PropertyWithPhoto;
import com.example.realestatemanager.repository.PropertyRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class PropertyViewModel extends ViewModel {

    private PropertyRepository dataSourceProperty;
    private Executor executor;


    public PropertyViewModel(PropertyRepository dataSourceProperty, Executor executor) {
        this.dataSourceProperty = dataSourceProperty;
        this.executor = executor;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void createPropertyWithPhotos(Property property, List<Photo> photos){
        executor.execute(()-> {
            this.dataSourceProperty.createPropertyWithPhotos(property, photos);
        });
    }

    public void updatePropertyWithPhotos(Property property, List <Photo> photos){
        executor.execute(()-> {
        this.dataSourceProperty.updatePropertyWithPhotos(property, photos);
        });
    }

    public LiveData<PropertyWithPhoto> getProperty(long propertyId){
        return this.dataSourceProperty.getProperty(propertyId);
    }

    public LiveData <List<PropertyWithPhoto>> getProperties(){
        return this.dataSourceProperty.getProperties();
    }

    public void deleteById(long id){
        executor.execute(()-> {
        this.dataSourceProperty.deleteById(id);
        });
    }
}
