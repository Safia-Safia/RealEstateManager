package com.openclassrooms.realestatemanager.model;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
@Entity(tableName = "users")
public class User implements Serializable {
    @PrimaryKey
    @NonNull
    public String uid;
    public String username;
    @Nullable
    private String urlPicture;
    public String email;
    @Ignore
    public User() { }

    @ColumnInfo(name = "isLogged")
    public boolean isLogged;

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
    public boolean isLogged() {
        return isLogged;
    }
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
