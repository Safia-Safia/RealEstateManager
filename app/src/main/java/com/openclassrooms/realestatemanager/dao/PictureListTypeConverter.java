package com.openclassrooms.realestatemanager.dao;

import android.net.Uri;
import androidx.room.TypeConverter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.openclassrooms.realestatemanager.model.Picture;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class PictureListTypeConverter {

    @TypeConverter
    public String fromPicturesList(ArrayList<Picture> pictures) {
        return new Gson().toJson(pictures);
    }

    @TypeConverter
    public ArrayList<Picture> toPicturesList(String json) {
        Type type = new TypeToken<ArrayList<Picture>>() {}.getType();
        return new Gson().fromJson(json, type);
    }

    @TypeConverter
    public String fromUri(Uri uri) {
        return uri == null ? null : uri.toString();
    }

    @TypeConverter
    public Uri toUri(String uriString) {
        return uriString == null ? null : Uri.parse(uriString);
    }
}
