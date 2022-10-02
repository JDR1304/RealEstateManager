package com.example.realestatemanager.models;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class PropertyWithPhoto {
    @Embedded
    public Property property;
    @Relation
    (parentColumn = "id", entityColumn = "propertyId")
    public List<Photo> photos;
}
