package com.openclassrooms.realestatemanager.viewModel;

import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.model.Estate;

public class EstateDataViewModel extends ViewModel {



    // Estate methods

    public void createEstate(Estate estate) {
        //estateDataRepository.createEstate(estate);
    }
/*
    public LiveData<List<Estate>> getEstates() {
        return estateDataRepository.getEstates();
    }

    public void updateEstate(Estate estate) {
        estateDataRepository.updateEstate(estate);
    }

    // User methods

    public void createUser(User user) {
        userDataRepository.createUser(user);
    }

    public LiveData<List<User>> getUsers() {
        return userDataRepository.getUsers();
    }

    public LiveData<User> getCurrentUser() {
        return userDataRepository.getCurrentUser();
    }
    public LiveData<User> getUser() {
        return userDataRepository.getUserByUid(getCurrentUser().getValue().getUid());
    }
    public boolean isCurrentUserLogged() {
        return userDataRepository.isCurrentUserLogged();
    }*/
}
