package com.openclassrooms.realestatemanager.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;

public class User {
    public String uid;
    public String username;
    @Nullable
    private String urlPicture;

    public String email;
    public User() { }

    public User(String uid, String username, @Nullable String urlPicture, String email) {
        this.uid = uid;
        this.username = username;
        this.urlPicture = urlPicture;
        this.email = email;
    }

    // --- GETTERS ---
    public String getUid() { return uid; }
    public String getUsername() { return username; }
    @Nullable
    public String getUrlPicture() { return urlPicture; }

    public String getEmail() {
        return email;
    }


    // --- SETTERS ---
    public void setUsername(String username) { this.username = username; }
    public void setUid(String uid) { this.uid = uid; }
    public void setUrlPicture(@Nullable String urlPicture) { this.urlPicture = urlPicture; }

    public void setEmail(String email) {
        this.email = email;
    }

}
