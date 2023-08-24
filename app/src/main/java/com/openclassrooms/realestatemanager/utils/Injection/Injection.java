package com.openclassrooms.realestatemanager.utils.Injection;

import android.content.Context;

import com.openclassrooms.realestatemanager.repository.EstateRepository;
import com.openclassrooms.realestatemanager.repository.UserRepository;

public class Injection {


    public static UserRepository provideUserDataSource(Context context) {
        return new UserRepository();
    }

    public static EstateRepository provideEstatesDataSource(Context context){
        return new EstateRepository();
    }


    public static ViewModelFactory provideViewModelFactory(Context context) {
        UserRepository userDataSource = provideUserDataSource(context);
        EstateRepository estateDataSource = provideEstatesDataSource(context);
        return new ViewModelFactory(userDataSource, estateDataSource);
    }
}
