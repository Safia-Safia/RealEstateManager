package com.openclassrooms.realestatemanager.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.model.Estate;
import com.openclassrooms.realestatemanager.repository.EstateRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class EstateViewModel extends ViewModel {

    private final EstateRepository estateRepository;
    private final Executor executor;

    // CONSTRUCTOR
    public EstateViewModel(EstateRepository estateRepository, Executor executor) {
        this.estateRepository = estateRepository;
        this.executor = executor;
    }

  public LiveData<Boolean> createEstate(Estate estate) {
      return estateRepository.createEstate(estate);
    }

    public LiveData<Boolean> updateEstate(Estate estate, String estateId) {
        return estateRepository.updateEstate(estate, estateId,executor);
    }

    public LiveData<List<Estate>> getEstates() {
        return estateRepository.getEstates(executor);
    }

    public LiveData<List<Estate>> getFilteredEstates(
            long minPrice, long maxPrice, long minSurface, long maxSurface, boolean isSchoolFilter,
            boolean isStoreFilter, boolean isParkFilter, boolean isParkingFilter, boolean isSoldFilter,
            boolean isLastWeekFilter, boolean hasThreeOrMorePictures, String selectedEstateType) {
        return estateRepository.getFilteredEstates(
                minPrice, maxPrice, minSurface, maxSurface, isSchoolFilter,
                isStoreFilter, isParkFilter, isParkingFilter, isSoldFilter,
                isLastWeekFilter,hasThreeOrMorePictures, selectedEstateType
        );
    }

}
