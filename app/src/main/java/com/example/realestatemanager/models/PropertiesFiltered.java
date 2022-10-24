package com.example.realestatemanager.models;

public class PropertiesFiltered {

    private String propertyTypeValue;
    private double priceInDollarsValueMin;
    private double priceInDollarsValueMax;
    private float surfaceValueMin;
    private float surfaceValueMax;
    private int numberOfMediaValue;
    private int numberOfRoomsValue;
    private int numberOfBathRoomsValue;
    private int numberOfBedRoomsValue;
    private String cityValue;
    private boolean pointsOfInterestSchoolValue;
    private boolean pointsOfInterestParkValue;
    private boolean pointsOfInterestStoreValue;
    private boolean propertyStatusValue;

    public PropertiesFiltered(String propertyTypeValue, double priceInDollarsValueMin,
                              double priceInDollarsValueMax, float surfaceValueMin,
                              float surfaceValueMax, int numberOfMediaValue, int numberOfRoomsValue,
                              int numberOfBathRoomsValue, int numberOfBedRoomsValue,
                              String cityValue, boolean pointsOfInterestSchoolValue,
                              boolean pointsOfInterestParkValue, boolean pointsOfInterestStoreValue,
                              boolean propertyStatusValue) {
        this.propertyTypeValue = propertyTypeValue;
        this.priceInDollarsValueMin = priceInDollarsValueMin;
        this.priceInDollarsValueMax = priceInDollarsValueMax;
        this.surfaceValueMin = surfaceValueMin;
        this.surfaceValueMax = surfaceValueMax;
        this.numberOfMediaValue = numberOfMediaValue;
        this.numberOfRoomsValue = numberOfRoomsValue;
        this.numberOfBathRoomsValue = numberOfBathRoomsValue;
        this.numberOfBedRoomsValue = numberOfBedRoomsValue;
        this.cityValue = cityValue;
        this.pointsOfInterestSchoolValue = pointsOfInterestSchoolValue;
        this.pointsOfInterestParkValue = pointsOfInterestParkValue;
        this.pointsOfInterestStoreValue = pointsOfInterestStoreValue;
        this.propertyStatusValue = propertyStatusValue;
    }

    public String getPropertyTypeValue() {
        return propertyTypeValue;
    }

    public void setPropertyTypeValue(String propertyTypeValue) {
        this.propertyTypeValue = propertyTypeValue;
    }

    public double getPriceInDollarsValueMin() {
        return priceInDollarsValueMin;
    }

    public void setPriceInDollarsValueMin(double priceInDollarsValueMin) {
        this.priceInDollarsValueMin = priceInDollarsValueMin;
    }

    public double getPriceInDollarsValueMax() {
        return priceInDollarsValueMax;
    }

    public void setPriceInDollarsValueMax(double priceInDollarsValueMax) {
        this.priceInDollarsValueMax = priceInDollarsValueMax;
    }

    public float getSurfaceValueMin() {
        return surfaceValueMin;
    }

    public void setSurfaceValueMin(float surfaceValueMin) {
        this.surfaceValueMin = surfaceValueMin;
    }

    public float getSurfaceValueMax() {
        return surfaceValueMax;
    }

    public void setSurfaceValueMax(float surfaceValueMax) {
        this.surfaceValueMax = surfaceValueMax;
    }

    public int getNumberOfMediaValue() {
        return numberOfMediaValue;
    }

    public void setNumberOfMediaValue(int numberOfMediaValue) {
        this.numberOfMediaValue = numberOfMediaValue;
    }

    public int getNumberOfRoomsValue() {
        return numberOfRoomsValue;
    }

    public void setNumberOfRoomsValue(int numberOfRoomsValue) {
        this.numberOfRoomsValue = numberOfRoomsValue;
    }

    public int getNumberOfBathRoomsValue() {
        return numberOfBathRoomsValue;
    }

    public void setNumberOfBathRoomsValue(int numberOfBathRoomsValue) {
        this.numberOfBathRoomsValue = numberOfBathRoomsValue;
    }

    public int getNumberOfBedRoomsValue() {
        return numberOfBedRoomsValue;
    }

    public void setNumberOfBedRoomsValue(int numberOfBedRoomsValue) {
        this.numberOfBedRoomsValue = numberOfBedRoomsValue;
    }

    public String getCityValue() {
        return cityValue;
    }

    public void setCityValue(String cityValue) {
        this.cityValue = cityValue;
    }

    public boolean isPointsOfInterestSchoolValue() {
        return pointsOfInterestSchoolValue;
    }

    public void setPointsOfInterestSchoolValue(boolean pointsOfInterestSchoolValue) {
        this.pointsOfInterestSchoolValue = pointsOfInterestSchoolValue;
    }

    public boolean isPointsOfInterestParkValue() {
        return pointsOfInterestParkValue;
    }

    public void setPointsOfInterestParkValue(boolean pointsOfInterestParkValue) {
        this.pointsOfInterestParkValue = pointsOfInterestParkValue;
    }

    public boolean isPointsOfInterestStoreValue() {
        return pointsOfInterestStoreValue;
    }

    public void setPointsOfInterestStoreValue(boolean pointsOfInterestStoreValue) {
        this.pointsOfInterestStoreValue = pointsOfInterestStoreValue;
    }

    public boolean isPropertyStatusValue() {
        return propertyStatusValue;
    }

    public void setPropertyStatusValue(boolean propertyStatusValue) {
        this.propertyStatusValue = propertyStatusValue;
    }
}
