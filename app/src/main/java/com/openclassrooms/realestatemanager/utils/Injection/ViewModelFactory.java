package com.openclassrooms.realestatemanager.utils.Injection;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.openclassrooms.realestatemanager.repository.EstateRepository;
import com.openclassrooms.realestatemanager.repository.UserRepository;
import com.openclassrooms.realestatemanager.viewModel.EstateViewModel;
import com.openclassrooms.realestatemanager.viewModel.UserViewModel;


public class ViewModelFactory implements ViewModelProvider.Factory {

    private final UserRepository userRepository;

    private final EstateRepository estateRepository;
    public ViewModelFactory(UserRepository userRepository, EstateRepository estateRepository) {
        this.userRepository = userRepository;
        this.estateRepository = estateRepository;
    }

    @NonNull
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(UserViewModel.class)) {
            return (T) new UserViewModel(userRepository);
        } else if (modelClass.isAssignableFrom(EstateViewModel.class)) {
        return (T) new EstateViewModel(estateRepository);
    }
        throw new IllegalArgumentException("Unknown UserViewModel class");
    }
}
