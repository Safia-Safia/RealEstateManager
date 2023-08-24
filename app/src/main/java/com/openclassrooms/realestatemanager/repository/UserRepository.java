package com.openclassrooms.realestatemanager.repository;

import androidx.annotation.Nullable;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.openclassrooms.realestatemanager.model.User;

public class UserRepository {

    public static final String COLLECTION_USERS = "users";
    private static volatile UserRepository instance;

    public CollectionReference getUsersCollection() {
        return FirebaseFirestore.getInstance().collection(COLLECTION_USERS);
    }

    public UserRepository() {
    }

    public static UserRepository getInstance() {
        UserRepository result = instance;
        if (result != null) {
            return result;
        }
        synchronized (UserRepository.class) {
            if (instance == null) {
                instance = new UserRepository();
            }
            return instance;
        }
    }


    //--- CREATE ---
    public void createUser() {
        FirebaseUser user = getCurrentUser();
        if (user != null) {
            String urlPicture = (user.getPhotoUrl() != null) ? user.getPhotoUrl().toString() : null;
            String username = user.getDisplayName();
            String uid = user.getUid();
            User userToCreate = new User(uid, username, urlPicture);
        }
    }

    @Nullable
    public FirebaseUser getCurrentUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    //--- GET ---
    @Nullable
    public String getCurrentUserUID() {
        FirebaseUser user = getCurrentUser();
        return (user != null) ? user.getUid() : null;
    }


}

