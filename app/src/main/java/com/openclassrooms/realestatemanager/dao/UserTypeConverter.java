package com.openclassrooms.realestatemanager.dao;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.openclassrooms.realestatemanager.model.User;

public class UserTypeConverter {

    @TypeConverter
    public String fromUser(User user) {
        return new Gson().toJson(user);
    }

    @TypeConverter
    public User toUser(String json) {
        return new Gson().fromJson(json, User.class);
    }
}
