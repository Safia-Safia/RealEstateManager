package com.openclassrooms.realestatemanager.repository;

import android.content.ContentValues;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.OnConflictStrategy;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.openclassrooms.realestatemanager.dao.EstateDao;
import com.openclassrooms.realestatemanager.model.Estate;
import com.openclassrooms.realestatemanager.model.Picture;
import com.openclassrooms.realestatemanager.model.User;

import java.util.ArrayList;
import java.util.List;

@Database(entities = {Estate.class, User.class}, version = 1 ,exportSchema = false)
public abstract class EstateDatabase extends RoomDatabase {

    // --- SINGLETON ---
    private static volatile EstateDatabase INSTANCE;

    // --- DAO ---
    public abstract EstateDao estateDao();

    // --- INSTANCE ---
    public static EstateDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (EstateDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    EstateDatabase.class, "EstateDatabase.db")
                            .addCallback(prepopulateDatabase())
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    // ---

    private static Callback prepopulateDatabase(){
        return new Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
                ContentValues estateContentValues = new ContentValues();


                db.insert("estates", OnConflictStrategy.IGNORE, estateContentValues);
            }
        };
    }

    private static Picture createPicture(String imageUrl, String description) {
        Picture picture = new Picture();
        picture.setImageUrl(imageUrl);
        picture.setDescription(description);
        return picture;
    }
}
