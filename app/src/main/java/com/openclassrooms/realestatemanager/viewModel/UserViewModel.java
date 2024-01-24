package com.openclassrooms.realestatemanager.viewModel;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseUser;
import com.openclassrooms.realestatemanager.repository.UserRepository;

public class UserViewModel extends ViewModel {
   //-- REPOSITORIES
    private final UserRepository userRepository;

    // CONSTRUCTOR
    public UserViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // -- USER'S METHODS --

    public FirebaseUser getCurrentUser() {
        return userRepository.getCurrentUser();
    }

    public Boolean isCurrentUserLogged() {
        return (this.getCurrentUser() != null);
    }

    public void signOut(Context context) {
        AuthUI.getInstance().signOut(context);
    }

    public void createUser() {
        userRepository.createUser();
    }
}