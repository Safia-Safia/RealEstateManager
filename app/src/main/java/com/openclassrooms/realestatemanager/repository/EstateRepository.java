package com.openclassrooms.realestatemanager.repository;


import androidx.lifecycle.MutableLiveData;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.openclassrooms.realestatemanager.model.Estate;

import java.util.List;

public class EstateRepository {
    public static final String COLLECTION_ESTATES = "estates";

    private static volatile EstateRepository instance;

    public EstateRepository(){
    }

    public CollectionReference getEstateCollection (){
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

}
