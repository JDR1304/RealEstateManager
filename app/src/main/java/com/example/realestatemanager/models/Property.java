package com.example.realestatemanager.models;


import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity
public class Property {

    @PrimaryKey (autoGenerate = true)
    private long id;
    @NonNull
    private String propertyType;
    private double priceInDollars;
    private float surface;
    private int numberOfRooms;
    private String description;
    @Embedded
    private Address address;
    private boolean pointsOfInterestSchool;
    private boolean pointsOfInterestPark;
    private boolean pointsOfInterestStore;
    private boolean propertyStatus;
    private long propertyEntryDate;
    private long propertySaleDate;
    @NonNull
    private String realEstateAgentName;

    /** Key for PROPERTY **/
    public static final String PROPERTY_KEY = "PROPERTY_KEY";


    public Property(@NonNull String propertyType, double priceInDollars, float surface,
                    int numberOfRooms, String description, @NonNull Address address, boolean pointsOfInterestSchool,
                    boolean pointsOfInterestPark, boolean pointsOfInterestStore, boolean propertyStatus,
                    long propertyEntryDate, long propertySaleDate, @NonNull String realEstateAgentName) {
        this.propertyType = propertyType;
        this.priceInDollars = priceInDollars;
        this.surface = surface;
        this.numberOfRooms = numberOfRooms;
        this.description = description;
        this.address = address;
        this.pointsOfInterestSchool = pointsOfInterestSchool;
        this.pointsOfInterestPark = pointsOfInterestPark;
        this.pointsOfInterestStore = pointsOfInterestStore;
        this.propertyStatus = propertyStatus;
        this.propertyEntryDate = propertyEntryDate;
        this.propertySaleDate = propertySaleDate;
        this.realEstateAgentName = realEstateAgentName;

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public double getPriceInDollars() {
        return priceInDollars;
    }

    public void setPriceInDollars(double priceInDollars) {
        this.priceInDollars = priceInDollars;
    }

    public float getSurface() {
        return surface;
    }

    public void setSurface(float surface) {
        this.surface = surface;
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public boolean isPointsOfInterestSchool() {
        return pointsOfInterestSchool;
    }

    public void setPointsOfInterestSchool(boolean pointsOfInterestSchool) {
        this.pointsOfInterestSchool = pointsOfInterestSchool;
    }

    public boolean isPointsOfInterestPark() {
        return pointsOfInterestPark;
    }

    public void setPointsOfInterestPark(boolean pointsOfInterestPark) {
        this.pointsOfInterestPark = pointsOfInterestPark;
    }

    public boolean isPointsOfInterestStore() {
        return pointsOfInterestStore;
    }

    public void setPointsOfInterestStore(boolean pointsOfInterestStore) {
        this.pointsOfInterestStore = pointsOfInterestStore;
    }

    public boolean isPropertyStatus() {
        return propertyStatus;
    }

    public void setPropertyStatus(boolean propertyStatus) {
        this.propertyStatus = propertyStatus;
    }

    public long getPropertyEntryDate() {
        return propertyEntryDate;
    }

    public void setPropertyEntryDate(long propertyEntryDate) {
        this.propertyEntryDate = propertyEntryDate;
    }

    public long getPropertySaleDate() {
        return propertySaleDate;
    }

    public void setPropertySaleDate(long propertySaleDate) {
        this.propertySaleDate = propertySaleDate;
    }

    public String getRealEstateAgentName() {
        return realEstateAgentName;
    }

    public void setRealEstateAgentName(String realEstateAgentName) {
        this.realEstateAgentName = realEstateAgentName;
    }

}
