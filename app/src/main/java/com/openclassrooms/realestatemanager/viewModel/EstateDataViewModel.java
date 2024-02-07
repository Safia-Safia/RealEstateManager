package com.openclassrooms.realestatemanager.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.model.Estate;
import com.openclassrooms.realestatemanager.model.User;
import com.openclassrooms.realestatemanager.repository.EstateDataRepository;
import com.openclassrooms.realestatemanager.repository.UserDataRepository;

import java.util.List;

public class EstateDataViewModel extends ViewModel {

    private final EstateDataRepository estateDataRepository;
    private final UserDataRepository userDataRepository;

    public EstateDataViewModel(EstateDataRepository estateDataSource, UserDataRepository userDataSource) {
        this.estateDataRepository =estateDataSource;
        this.userDataRepository =userDataSource;
    }

    // Estate methods

    public void createEstate(Estate estate) {
        estateDataRepository.createEstate(estate);
    }

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
}
