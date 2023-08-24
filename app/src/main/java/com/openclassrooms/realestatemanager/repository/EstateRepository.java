package com.openclassrooms.realestatemanager.repository;


import android.net.Uri;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.openclassrooms.realestatemanager.model.Estate;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EstateRepository {
    public static final String COLLECTION_ESTATES = "estates";

    private static volatile EstateRepository instance;

    private UserRepository userRepository;

    public EstateRepository() {
    }

    public CollectionReference getEstateCollection() {
        return FirebaseFirestore.getInstance().collection(COLLECTION_ESTATES);
    }

    public static EstateRepository getInstance() {
        EstateRepository result = instance;
        if (result != null) {
            return result;
        }
        synchronized (UserRepository.class) {
            if (instance == null) {
                instance = new EstateRepository();
            }
            return instance;
        }
    }

    public UploadTask uploadImage(Uri imageUri) {
        String uuid = UUID.randomUUID().toString(); // GENERATE UNIQUE STRING
        StorageReference mImageRef = FirebaseStorage.getInstance().getReference("/"+uuid);
        return mImageRef.putFile(imageUri);
    }

    public void createForURL(String urlImage,Estate estate, String description){
            // Storing Message on Firestore
        Map<String, Object> imageUrlMap = new HashMap<>();
        imageUrlMap.put("image_url", urlImage);
        estate.setPicturesUri(urlImage);
        estate.getPicturesList().add(urlImage);
        estate.getPicturesDescription().add(description);
    }

    public void getCreatedEstates(Estate estate) {
        getEstateCollection().document().set(estate);
    }
}
