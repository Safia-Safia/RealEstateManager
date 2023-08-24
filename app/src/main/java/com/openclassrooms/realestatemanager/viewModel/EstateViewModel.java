package com.openclassrooms.realestatemanager.viewModel;

import android.net.Uri;

import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.model.Estate;
import com.openclassrooms.realestatemanager.repository.EstateRepository;

public class EstateViewModel extends ViewModel {

    private final EstateRepository estateRepository;

    // CONSTRUCTOR
    public EstateViewModel(EstateRepository estateRepository) {
        this.estateRepository = estateRepository;
    }

    public void getCreatedEstate(Estate estate) {
        estateRepository.getCreatedEstates(estate);
    }

    public void sendImage(Uri imageUri, Estate estate, String description){
            estateRepository.uploadImage(imageUri).addOnSuccessListener(taskSnapshot -> {
                taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(uri -> {
                    estateRepository.createForURL(uri.toString(), estate, description);
                });;
            });
        }
}
