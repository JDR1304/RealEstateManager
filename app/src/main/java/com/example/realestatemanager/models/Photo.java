package com.example.realestatemanager.models;

import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Photo {

    @PrimaryKey(autoGenerate = true)
    private long id;
    @NonNull
    private long propertyId;
    private String name;
    private String stringPhoto;

    public Photo(long propertyId, String stringPhoto, String name) {
        this.propertyId = propertyId;
        this.stringPhoto = stringPhoto;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(long propertyOwnerId) {
        this.propertyId = propertyOwnerId;
    }

    public String getStringPhoto(){
        return stringPhoto;
    }

    public void setStringPhoto(String str){
        stringPhoto = str;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Uri stringToUri(String str) {
        return str == null ? null : Uri.parse(str);
    }

    public String uriToString(Uri uri) {
        return uri == null ? null : uri.toString();
    }
}
