package com.openclassrooms.realestatemanager.viewModel;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.model.Estate;
import com.openclassrooms.realestatemanager.repository.EstateRepository;

import java.util.List;

public class EstateViewModel extends ViewModel {

    private final EstateRepository estateRepository;

    // CONSTRUCTOR
    public EstateViewModel(EstateRepository estateRepository) {
        this.estateRepository = estateRepository;
    }

    public LiveData<Boolean> createEstate(Estate estate) {
        return estateRepository.createEstate(estate);
    }

    public LiveData<List<Estate>> getEstates() {
        return estateRepository.getEstates();
    }
}
