package com.openclassrooms.realestatemanager.model;

import android.net.Uri;

import com.google.firebase.firestore.Exclude;
import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class Picture implements Serializable {

    String imageUrl, description;

    @Exclude
    public transient Uri imageUri;

    public Picture() {
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Exclude
    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }


}
