package com.example.realestatemanager;

import androidx.lifecycle.LiveData;

import com.example.realestatemanager.database.PropertyDAO;
import com.example.realestatemanager.models.Address;
import com.example.realestatemanager.models.Photo;
import com.example.realestatemanager.models.Property;
import com.example.realestatemanager.models.PropertyWithPhoto;

import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class PropertyRepositoryTest {

    @Mock
    PropertyDAO propertyDAO;


    Address addressApartment = new Address("740 park avenue", 10021, "New York");

    Address addressHouse = new Address("1 countryside avenue", 10021, "New York");

    Property propertyTest = new Property("Apartment", 1_000_000, 120,
            8, "Apartment in front of the park", addressApartment,
            false, true, true, false,
            1662826711, 0, "Jerome Diaz");

    Property property2Test = new Property("House", 2_000_000, 180,
            9, "House in the countryside", addressHouse,
            false, false, false, false,
            1662826711, 0, "Fabien Barry");

    List<Photo> photosTest = new ArrayList<Photo>(Arrays.asList(
            new Photo(1, "URI Photo1", "Photo1 name"),
            new Photo(1, "URI Photo2", "Photo2 name")));

    List<Photo> photos2Test = new ArrayList<Photo>(Arrays.asList(
            new Photo(2, "URI Photo3", "Photo3 name"),
            new Photo(2, "URI Photo4", "Photo4 name")));

    public void createPropertyWithPhotosTest(){


    }


    public void updatePropertyWithPhotosTest(){

    }


    public LiveData<PropertyWithPhoto> getPropertyTest(long propertyId){
        return null ;
    }


    public LiveData <List<PropertyWithPhoto>> getPropertiesTest(){
        return null;
    }


}
