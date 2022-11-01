package com.example.realestatemanager;

import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.realestatemanager.database.PropertyDAO;
import com.example.realestatemanager.database.RealEstateManagerDataBase;
import com.example.realestatemanager.models.Address;
import com.example.realestatemanager.models.Photo;
import com.example.realestatemanager.models.Property;
import com.example.realestatemanager.models.PropertyWithPhoto;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@RunWith(AndroidJUnit4.class)
public class BaseDeDonneesTest {
    /*
     The rule @InstantTaskExecutorRule force the execution for each test
      to synchronize (Not in a background thread).
     */
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    Address addressApartment = new Address("740 park avenue", 10021, "New York");

    Address addressHouse = new Address("1 countryside avenue", 10021, "New York");

    Property propertyTest = new Property("Apartment", 1_000_000, 120,
            8, 2,3, "Apartment in front of the park", addressApartment,
            false, true, true, false,
            1662826711, 0, "Jerome Diaz");

    Property property2Test = new Property("House", 2_000_000, 180,
            9,3,4, "House in the countryside", addressHouse,
            false, false, false, false,
            1662826711, 0, "Fabien Barry");

    List<Photo> photosTest = new ArrayList<>(Arrays.asList(
            new Photo(1, "URI Photo1", "Photo1 name"),
            new Photo(1, "URI Photo2", "Photo2 name")));

    List<Photo> photos2Test = new ArrayList<>(Arrays.asList(
            new Photo(2, "URI Photo3", "Photo3 name"),
            new Photo(2, "URI Photo4", "Photo4 name")));

    private PropertyDAO propertyDao;
    private RealEstateManagerDataBase db;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, RealEstateManagerDataBase.class)
                .build();
        propertyDao = db.propertyDao();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }


    @Test
    public void createPropertyWithPhotosTest() throws Exception {
        propertyDao.createPropertyWithPhotos(propertyTest, photosTest);
        PropertyWithPhoto readProperty = LiveDataTestUtil.getValue(propertyDao.getProperty(1));

        assertEquals(readProperty.property.getRealEstateAgentName(),"Jerome Diaz" );

    }

    @Test
    public void updatePropertyWithPhotosTest() throws Exception {
        propertyDao.createPropertyWithPhotos(propertyTest, photosTest);

        PropertyWithPhoto readProperty = LiveDataTestUtil.getValue(propertyDao.getProperty(1));

        assertEquals(readProperty.property.getRealEstateAgentName(), "Jerome Diaz");
        readProperty.property.setRealEstateAgentName("Update Name");
        propertyDao.updatePropertyWithPhotos(readProperty.property, photosTest);
        PropertyWithPhoto readPropertyUpdate = LiveDataTestUtil.getValue(propertyDao.getProperty(1));

        assertEquals(readPropertyUpdate.property.getRealEstateAgentName(),"Update Name");
    }

    @Test
    public void getPropertyTest() throws Exception {
        propertyDao.createPropertyWithPhotos(propertyTest, photosTest);

        PropertyWithPhoto readProperty = LiveDataTestUtil.getValue(propertyDao.getProperty(1));

        assertEquals(readProperty.property.getRealEstateAgentName(), "Jerome Diaz");

    }

    @Test
    public void getPropertiesTest() throws Exception {
        propertyDao.createPropertyWithPhotos(propertyTest, photosTest);
        propertyDao.createPropertyWithPhotos(property2Test, photos2Test);

        List <PropertyWithPhoto> propertyWithPhotoList = LiveDataTestUtil.getValue(propertyDao.getProperties());

        assertEquals(propertyWithPhotoList.get(0).property.getRealEstateAgentName(),"Jerome Diaz");
        assertEquals(propertyWithPhotoList.get(1).property.getRealEstateAgentName(), "Fabien Barry");


    }
}
