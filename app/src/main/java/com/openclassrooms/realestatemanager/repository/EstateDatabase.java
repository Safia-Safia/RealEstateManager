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
                List<Picture> pictureList = new ArrayList<>();

                pictureList.add(createPicture("https://cache.marieclaire.fr/data/photo/w1000_c17/5n/idees-deco-chambre-ado-sous-les-toits.webp", "Chambre"));
                pictureList.add(createPicture("https://www.livinside.com/scripts/files/63f4c1e807b430.58281755/amenagement-salon.webp", "Salon"));
                pictureList.add(createPicture("https://media.ma.cuisinella/-/media/bynder/cuisinella/2024/01/10/05/17/cla_cui_pure_cactus_label_styra_pimp_equateur_2/4x3-cla_cui_pure_cactus_label_styra_pimp_equateur_2.ashx?as=1&w=1920&rev=87c66d596d004573ac363732dfd66303&hash=B347F95B3F0D52DB1C598E9B0E72D269", "Cuisine"));

                estateContentValues.put("id", "143546EZZEF");
                estateContentValues.put("description", "Un bel appartement fictif situé au 4ème étage de cet immeuble.");
                if (!pictureList.isEmpty()) {
                    estateContentValues.put("coverPictureUrl", pictureList.get(0).getImageUrl());
                }
                estateContentValues.put("estateType", "Maison");
                estateContentValues.put("numberOfRoom", 3);
                estateContentValues.put("price", 250000);
                estateContentValues.put("surface", 180);
                estateContentValues.put("address", "23 Rue Rivoli");
                estateContentValues.put("entryDate", "2023-02-14");
                estateContentValues.put("soldDate", "2024-02-07");
                estateContentValues.put("isEstatesAvailable", true);
                estateContentValues.put("school", true);
                estateContentValues.put("store", false);
                estateContentValues.put("park", true);
                estateContentValues.put("parking", true);
                estateContentValues.put("latitude", 48.8592 );
                estateContentValues.put("longitude", -2.3452);
                estateContentValues.put("city", "Paris");

                ContentValues userContentValues = new ContentValues();
                userContentValues.put("userId", "AZODJNE921");
                userContentValues.put("username", "Jean Dupont");
                userContentValues.put("email", "dupont@email.com");

                db.insert("estates", OnConflictStrategy.IGNORE, estateContentValues);
                db.insert("users", OnConflictStrategy.IGNORE, userContentValues);

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
