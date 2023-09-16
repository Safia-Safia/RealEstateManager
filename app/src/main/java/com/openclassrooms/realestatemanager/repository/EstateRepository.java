package com.openclassrooms.realestatemanager.repository;


import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.openclassrooms.realestatemanager.model.Estate;
import com.openclassrooms.realestatemanager.model.Picture;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

public class EstateRepository {
    public static final String COLLECTION_ESTATES = "estates";

    private static volatile EstateRepository instance;

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
        StorageReference mImageRef = FirebaseStorage.getInstance().getReference("/" + uuid);
        return mImageRef.putFile(imageUri);
    }

    public LiveData<Boolean> createEstate(Estate estate) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        for (int i = 0; i < estate.getPictures().size(); i++) {
            int finalI = i; //Valeur finale de la variable I
            uploadImage(estate.getPictures().get(i).getImageUri()).addOnSuccessListener(taskSnapshot -> {
                taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(url -> {
                    estate.getPictures().get(finalI).setImageUrl(url.toString());
                    if (finalI == estate.getPictures().size() - 1) {
                        estate.setCoverPictureUrl(estate.getPictures().get(1).getImageUrl());
                        getEstateCollection().document().set(estate);
                        result.setValue(true);
                    }
                });
            });
        }

        return result;
    }

    public LiveData<List<Estate>> getEstates() {
        MutableLiveData<List<Estate>> result = new MutableLiveData<>();
        getEstateCollection().get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<Estate> estateList= new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Estate estate = document.toObject(Estate.class);
                            estate.setId(document.getId());
                            estateList.add(estate);
                        }
                        result.postValue(estateList);
                    }
                });
        return result;
    }
}
