package com.openclassrooms.realestatemanager.viewModel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
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

    public LiveData<Boolean> signOut(Context context) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        AuthUI.getInstance().signOut(context).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                result.postValue(true);
            }
        });
        return result;
    }

    public void createUser() {
        userRepository.createUser();
    }
}