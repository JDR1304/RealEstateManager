package com.example.realestatemanager;

import android.location.Location;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.sqlite.db.SimpleSQLiteQuery;

import com.example.realestatemanager.models.Photo;
import com.example.realestatemanager.models.PropertiesFiltered;
import com.example.realestatemanager.models.Property;
import com.example.realestatemanager.models.PropertyWithPhoto;
import com.example.realestatemanager.repository.PropertyRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class PropertyViewModel extends ViewModel {

    private PropertyRepository dataSourceProperty;
    private Executor executor;
    private LiveData<List<PropertyWithPhoto>> listPropertyFiltered;

    //--------------------------------------
    public MutableLiveData<Location> locationLiveData = new MutableLiveData<>();


    public PropertyViewModel(PropertyRepository dataSourceProperty, Executor executor) {
        this.dataSourceProperty = dataSourceProperty;
        this.executor = executor;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void createPropertyWithPhotos(Property property, List<Photo> photos) {
        executor.execute(() -> {
            this.dataSourceProperty.createPropertyWithPhotos(property, photos);
        });
    }

    public void updatePropertyWithPhotos(Property property, List<Photo> photos) {
        executor.execute(() -> {
            this.dataSourceProperty.updatePropertyWithPhotos(property, photos);
        });
    }

    public LiveData<PropertyWithPhoto> getProperty(long propertyId) {
        return this.dataSourceProperty.getProperty(propertyId);
    }

    public LiveData<List<PropertyWithPhoto>> getProperties() {
        return this.dataSourceProperty.getProperties();
    }

    public void deleteById(long id) {
        executor.execute(() -> {
            this.dataSourceProperty.deleteById(id);
        });
    }

    public void getListFiltered(PropertiesFiltered propertiesFiltered) {

        // Query string
        String queryString = new String();

        // List of bind parameters
        List<Object> args = new ArrayList();

        // Allow to know where place the "WHERE"
        boolean containsCondition = false;

        // Beginning of query string
        queryString += "SELECT * FROM PROPERTY";

        // Optional parts are added to query string and to args upon here

        if (!propertiesFiltered.getPropertyTypeValue().isEmpty()) {
            queryString += " WHERE";
            queryString += " propertyType LIKE ?";
            args.add(propertiesFiltered.getPropertyTypeValue());
            containsCondition = true;
        }

        if (propertiesFiltered.getPriceInDollarsValueMin() != 0) {
            if (containsCondition) {
                queryString += " AND";
            } else {
                queryString += " WHERE";
                containsCondition = true;
            }

            queryString += " priceInDollars >= ?";
            args.add(propertiesFiltered.getPriceInDollarsValueMin());
        }

        if (propertiesFiltered.getPriceInDollarsValueMax() != 0) {
            if (containsCondition) {
                queryString += " AND";
            } else {
                queryString += " WHERE";
                containsCondition = true;
            }

            queryString += " priceInDollars < ?";
            args.add(propertiesFiltered.getPriceInDollarsValueMax());
        }

        if (propertiesFiltered.getSurfaceValueMin() != 0) {
            if (containsCondition) {
                queryString += " AND";
            } else {
                queryString += " WHERE";
                containsCondition = true;
            }

            queryString += " surface >= ?";
            args.add(propertiesFiltered.getSurfaceValueMin());
        }

        if (propertiesFiltered.getSurfaceValueMax() != 0) {
            if (containsCondition) {
                queryString += " AND";
            } else {
                queryString += " WHERE";
                containsCondition = true;
            }

            queryString += " surface < ?";
            args.add(propertiesFiltered.getSurfaceValueMax());
        }

        if (propertiesFiltered.getPriceInDollarsValueMin() != 0) {
            if (containsCondition) {
                queryString += " AND";
            } else {
                queryString += " WHERE";
                containsCondition = true;
            }

            queryString += " priceInDollars >= ?";
            args.add(propertiesFiltered.getPriceInDollarsValueMin());
        }

        if (propertiesFiltered.getNumberOfRoomsValue() != 0) {
            if (containsCondition) {
                queryString += " AND";
            } else {
                queryString += " WHERE";
                containsCondition = true;
            }

            queryString += " numberOfRooms >= ?";
            args.add(propertiesFiltered.getNumberOfRoomsValue());
        }


        if (propertiesFiltered.getNumberOfBathRoomsValue() != 0) {
            if (containsCondition) {
                queryString += " AND";
            } else {
                queryString += " WHERE";
                containsCondition = true;
            }

            queryString += " numberOfBathrooms >= ?";
            args.add(propertiesFiltered.getNumberOfBathRoomsValue());
        }

        if (propertiesFiltered.getNumberOfBedRoomsValue() != 0) {
            if (containsCondition) {
                queryString += " AND";
            } else {
                queryString += " WHERE";
                containsCondition = true;
            }

            queryString += " numberOfBedrooms >= ?";
            args.add(propertiesFiltered.getNumberOfBedRoomsValue());
        }

        if (propertiesFiltered.getCityValue() != null && !propertiesFiltered.getCityValue().isEmpty()) {
            if (containsCondition) {
                queryString += " AND";
            } else {
                queryString += " WHERE";
                containsCondition = true;
            }

            queryString += " city LIKE ?";
            args.add(propertiesFiltered.getCityValue());
        }

        if (propertiesFiltered.isPointsOfInterestSchoolValue()) {
            if (containsCondition) {
                queryString += " AND";
            } else {
                queryString += " WHERE";
                containsCondition = true;
            }

            queryString += " pointsOfInterestSchool == ?";
            int value = propertiesFiltered.isPointsOfInterestSchoolValue() ? 1 : 0;
            args.add(value);
        }

        if (propertiesFiltered.isPointsOfInterestParkValue()) {
            if (containsCondition) {
                queryString += " AND";
            } else {
                queryString += " WHERE";
                containsCondition = true;
            }

            queryString += " pointsOfInterestPark == ?";
            int value = propertiesFiltered.isPointsOfInterestParkValue() ? 1 : 0;
            args.add(value);
        }

        if (propertiesFiltered.isPointsOfInterestStoreValue()) {
            if (containsCondition) {
                queryString += " AND";
            } else {
                queryString += " WHERE";
                containsCondition = true;
            }

            queryString += " pointsOfInterestStore == ?";
            int value = propertiesFiltered.isPointsOfInterestStoreValue() ? 1 : 0;
            args.add(value);
        }

        if (propertiesFiltered.isPropertyStatusValue()) {
            if (containsCondition) {
                queryString += " AND";
            } else {
                queryString += " WHERE";
                containsCondition = true;
            }

            queryString += " propertyStatus == ?";
            int value = propertiesFiltered.isPropertyStatusValue() ? 1 : 0;
            args.add(value);
        }

        SimpleSQLiteQuery query = new SimpleSQLiteQuery(queryString, args.toArray());
        listPropertyFiltered = this.dataSourceProperty.getPropertiesFiltered(query);
    }

    public LiveData<List<PropertyWithPhoto>> getPropertyFiltered() {
        return listPropertyFiltered;
    }

    //-----------------------------------------------------------

    public MutableLiveData<Location> getLocation() {
        return locationLiveData;
    }

    public void setLocation(Location location) {
        this.locationLiveData.postValue(location);
    }
}
