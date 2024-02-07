package com.openclassrooms.realestatemanager.utils.Injection;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.openclassrooms.realestatemanager.repository.EstateDataRepository;
import com.openclassrooms.realestatemanager.repository.EstateDatabase;
import com.openclassrooms.realestatemanager.repository.EstateRepository;
import com.openclassrooms.realestatemanager.repository.UserDataRepository;
import com.openclassrooms.realestatemanager.repository.UserRepository;
import com.openclassrooms.realestatemanager.viewModel.EstateDataViewModel;
import com.openclassrooms.realestatemanager.viewModel.EstateViewModel;
import com.openclassrooms.realestatemanager.viewModel.UserViewModel;

import java.util.concurrent.Executor;


public class ViewModelFactory implements ViewModelProvider.Factory {

    private final UserRepository userRepository;

    private final EstateRepository estateRepository;

    private final EstateDataRepository estateDataSource;
    private final UserDataRepository userDataSource;

    public ViewModelFactory(UserRepository userRepository, EstateRepository estateRepository, EstateDataRepository estateDataSource,
                            UserDataRepository userDataSource) {
        this.userRepository = userRepository;
        this.estateRepository = estateRepository;
        this.estateDataSource = estateDataSource;
        this.userDataSource = userDataSource;
    }

    @NonNull
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(UserViewModel.class)) {
            return (T) new UserViewModel(userRepository);
        } else if (modelClass.isAssignableFrom(EstateViewModel.class)) {
        return (T) new EstateViewModel(estateRepository);
    } else if (modelClass.isAssignableFrom(EstateDataViewModel.class)) {
            return (T) new EstateDataViewModel(estateDataSource, userDataSource);
        }
        throw new IllegalArgumentException("Unknown UserViewModel class");
    }
}
