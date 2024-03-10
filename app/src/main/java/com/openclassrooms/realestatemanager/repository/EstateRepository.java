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

import com.openclassrooms.realestatemanager.dao.EstateDao;
import com.openclassrooms.realestatemanager.model.Estate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executor;


public class EstateRepository {
    public static final String COLLECTION_ESTATES = "estates";
    private final EstateDao estateDao;

    public EstateRepository(EstateDao estateDao) {
        this.estateDao = estateDao;
    }


    public CollectionReference getEstateCollection() {
        return FirebaseFirestore.getInstance().collection(COLLECTION_ESTATES);
    }

    public UploadTask uploadImage(Uri imageUri) {
        String uuid = UUID.randomUUID().toString(); // GENERATE UNIQUE STRING
        StorageReference mImageRef = FirebaseStorage.getInstance().getReference("/" + uuid);
        return mImageRef.putFile(imageUri);
    }

    public LiveData<Boolean> createEstate(Estate estate) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        for (int i = 0; i < estate.getPictures().size(); i++) {
            int finalI = i; //Final value de la variable I
            uploadImage(estate.getPictures().get(i).getImageUri()).addOnSuccessListener(taskSnapshot -> {
                taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(url -> {
                    estate.setId(getEstateCollection().document().getId());
                    estate.getPictures().get(finalI).setImageUrl(url.toString());
                    if (finalI == estate.getPictures().size() - 1) {
                        estate.setCoverPictureUrl(estate.getPictures().get(0).getImageUrl());
                        getEstateCollection().document(estate.getId()).set(estate);
                        result.setValue(true);
                    }
                });
            });
        }
        return result;
    }

    public LiveData<List<Estate>> getEstates(Executor executor) {
        MutableLiveData<List<Estate>> result = new MutableLiveData<>();
        executor.execute(() -> result.postValue(estateDao.getEstates()));
        getEstateCollection().get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<Estate> estateList = new ArrayList<>();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Estate estate = document.toObject(Estate.class);
                    // Mettre à jour la base de données locale (Room) avec le nouveau bien immobilier
                    executor.execute(() -> estateDao.createEstate(estate));
                    estateList.add(estate);
                }
                result.postValue(estateList);
            }
        });
        return result;
    }

    public LiveData<Boolean> updateEstate(Estate estate, String estateId, Executor executor) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        Map<String, Object> updatedFields = new HashMap<>();
        updatedFields.put("description", estate.getDescription());
        updatedFields.put("estateType", estate.getEstateType());
        updatedFields.put("address", estate.getAddress());
        updatedFields.put("latitude", estate.getLatitude());
        updatedFields.put("longitude", estate.getLongitude());
        updatedFields.put("city", estate.getCity());
        updatedFields.put("school", estate.getSchool());
        updatedFields.put("store", estate.getStore());
        updatedFields.put("park", estate.getPark());
        updatedFields.put("parking", estate.getParking());
        updatedFields.put("price", estate.getPrice());
        updatedFields.put("surface", estate.getSurface());
        updatedFields.put("numberOfRoom", estate.getNumberOfRoom());
        updatedFields.put("soldDate", estate.getSoldDate());
        for (int i = 0; i < estate.getPictures().size(); i++) {
            int finalI = i;
            if (estate.getPictures().get(i).getImageUri() != null) {
                uploadImage(estate.getPictures().get(i).getImageUri()).addOnSuccessListener(taskSnapshot -> {
                    taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(url -> {
                        estate.getPictures().get(finalI).setImageUrl(url.toString());
                        if (finalI == estate.getPictures().size() - 1) {
                            estate.setCoverPictureUrl(estate.getPictures().get(0).getImageUrl());
                            updatedFields.put("pictures", estate.getPictures());
                            updatedFields.put("coverPictureUrl", estate.getCoverPictureUrl());
                            getEstateCollection().document(String.valueOf(estateId)).update(updatedFields);
                            executor.execute(() -> estateDao.updateEstate(estate));

                            result.setValue(true);
                        }
                    });
                });
            } else if (estate.getPictures().get(i).getImageUrl() != null) {
                if (finalI == estate.getPictures().size() - 1) {
                    estate.setCoverPictureUrl(estate.getPictures().get(0).getImageUrl());
                    updatedFields.put("pictures", estate.getPictures());
                    updatedFields.put("coverPictureUrl", estate.getCoverPictureUrl());
                    Log.e("updateEstate", " "+ updatedFields + " " + estateId);
                    getEstateCollection().document(String.valueOf(estateId)).update(updatedFields);
                    executor.execute(() -> estateDao.updateEstate(estate));
                    result.setValue(true);
                }
            } else {
                Log.e("if error", " " + i);
            }
        }
        return result;
    }

    public LiveData<List<Estate>> getFilteredEstates(
            long minPrice, long maxPrice, long minSurface, long maxSurface, boolean isSchoolFilter,
            boolean isStoreFilter, boolean isParkFilter, boolean isParkingFilter,
            boolean isSoldFilter, boolean isLastWeekFilter,boolean hasThreeOrMorePictures, String selectedEstateType) {
        return estateDao.getFilteredEstates(
                minPrice, maxPrice, minSurface, maxSurface, isSchoolFilter,
                isStoreFilter, isParkFilter, isParkingFilter,
                isSoldFilter, isLastWeekFilter, hasThreeOrMorePictures, selectedEstateType
        );
    }
}
