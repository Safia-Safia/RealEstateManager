package com.openclassrooms.realestatemanager.utils.Injection;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.openclassrooms.realestatemanager.repository.UserRepository;
import com.openclassrooms.realestatemanager.viewModel.UserViewModel;


public class ViewModelFactory implements ViewModelProvider.Factory {

    private final UserRepository userRepository;

    public ViewModelFactory(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(UserViewModel.class)) {
            return (T) new UserViewModel(userRepository);
        }
        throw new IllegalArgumentException("Unknown UserViewModel class");
    }
}
