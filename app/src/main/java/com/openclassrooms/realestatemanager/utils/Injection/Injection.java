package com.openclassrooms.realestatemanager.utils.Injection;

import android.content.Context;

import com.openclassrooms.realestatemanager.repository.UserRepository;

public class Injection {


    public static UserRepository provideUserDataSource(Context context) {
        return new UserRepository();
    }


    public static ViewModelFactory provideViewModelFactory(Context context) {
        UserRepository userDataSource = provideUserDataSource(context);
        return new ViewModelFactory(userDataSource);
    }
}
