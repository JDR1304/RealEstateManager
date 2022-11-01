package com.example.realestatemanager.models;

import android.content.ContentValues;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class PropertyWithPhoto {
    @Embedded
    public Property property;
    @Relation
    (parentColumn = "id", entityColumn = "propertyId")
    public List<Photo> photos;



    public static PropertyWithPhoto fromContentValues(ContentValues values) {

        final PropertyWithPhoto propertyWithPhoto = new PropertyWithPhoto();

        if (values.containsKey("id")) propertyWithPhoto.property.setId(values.getAsLong("id"));

        if (values.containsKey("propertyType")) propertyWithPhoto.property.setPropertyType(values.getAsString("propertyType"));

        if (values.containsKey("priceInDollars")) propertyWithPhoto.property.setPriceInDollars(values.getAsDouble("priceInDollars"));

        if (values.containsKey("surface")) propertyWithPhoto.property.setSurface(values.getAsFloat("surface"));

        if (values.containsKey("numberOfRooms")) propertyWithPhoto.property.setNumberOfRooms(values.getAsInteger("numberOfRooms"));

        if (values.containsKey("numberOfBathrooms")) propertyWithPhoto.property.setNumberOfBathrooms(values.getAsInteger("numberOfBathrooms"));

        if (values.containsKey("numberOfBedRooms")) propertyWithPhoto.property.setNumberOfBedrooms(values.getAsInteger("numberOfBedRooms"));

        if (values.containsKey("description")) propertyWithPhoto.property.setDescription(values.getAsString("description"));

        if (values.containsKey("pointsOfInterestSchool")) propertyWithPhoto.property.setPointsOfInterestSchool(values.getAsBoolean("pointsOfInterestSchool"));

        if (values.containsKey("pointsOfInterestPark")) propertyWithPhoto.property.setPointsOfInterestPark(values.getAsBoolean("pointsOfInterestPark"));

        if (values.containsKey("pointsOfInterestStore")) propertyWithPhoto.property.setPointsOfInterestStore(values.getAsBoolean("pointsOfInterestStore"));

        if (values.containsKey("propertyStatus")) propertyWithPhoto.property.setPropertyStatus(values.getAsBoolean("propertyStatus"));

        if (values.containsKey("propertyEntryDate")) propertyWithPhoto.property.setPropertyEntryDate(values.getAsLong("propertyEntryDate"));

        if (values.containsKey("propertySaleDate")) propertyWithPhoto.property.setPropertySaleDate(values.getAsLong("propertySaleDate"));

        if (values.containsKey("realEstateAgentName")) propertyWithPhoto.property.setRealEstateAgentName(values.getAsString("realEstateAgentName"));

        if (values.containsKey("street")) propertyWithPhoto.property.getAddress().setStreet(values.getAsString("street"));

        if (values.containsKey("postCode")) propertyWithPhoto.property.getAddress().setPostCode(values.getAsInteger("postCode"));

        if (values.containsKey("city")) propertyWithPhoto.property.getAddress().setCity(values.getAsString("city"));

        if (values.containsKey("stringPhoto")) propertyWithPhoto.photos.get(0).setStringPhoto(values.getAsString("stringPhoto"));

        return propertyWithPhoto;

    }

}
