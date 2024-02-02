package com.openclassrooms.realestatemanager.repository;

import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.openclassrooms.realestatemanager.model.User;

import java.util.Random;

public class UserRepository {

    public static final String COLLECTION_USERS = "users";
    private static volatile UserRepository instance;

    public CollectionReference getUsersCollection() {
        return FirebaseFirestore.getInstance().collection(COLLECTION_USERS);
    }

    public UserRepository() {
    }

    //--- CREATE ---
    public void createUser() {
        FirebaseUser user = getCurrentUser();
        if (user != null) {
            String urlPicture = (user.getPhotoUrl() != null) ? user.getPhotoUrl().toString() : null;
            String username = user.getDisplayName();
            String uid = user.getUid();
            String email = user.getEmail();
            if (urlPicture == null){
                String[] images = {"https://i.pravatar.cc/150?u=a042581f4e29026704a",
                        "https://i.pravatar.cc/150?u=a042581f4e29026704b",
                        "https://i.pravatar.cc/150?u=a042581f4e29026704c",
                        "https://i.pravatar.cc/150?u=a042581f4e29026704d"};

                int randomIndex = new Random().nextInt(images.length);
                String randomUserImage = images[randomIndex];
                urlPicture = randomUserImage;
            }
            User userToCreate = new User(uid, username, urlPicture, email);
            getUsersCollection().add(userToCreate);
        }
    }

    @Nullable
    public FirebaseUser getCurrentUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

}

