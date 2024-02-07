package com.openclassrooms.realestatemanager.utils.Injection;

import android.content.Context;

import com.openclassrooms.realestatemanager.repository.EstateDatabase;
import com.openclassrooms.realestatemanager.repository.EstateRepository;
import com.openclassrooms.realestatemanager.repository.UserRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Injection {


    public static UserRepository provideUserDataSource(Context context) {
        return new UserRepository();
    }

    public static EstateRepository provideEstatesDataSource(Context context){
        EstateDatabase database = EstateDatabase.getInstance(context);
        return new EstateRepository(database.estateDao());    }
    public static Executor provideExecutor(){ return Executors.newSingleThreadExecutor(); }


    public static ViewModelFactory provideViewModelFactory(Context context) {
        UserRepository userDataSource = provideUserDataSource(context);
        EstateRepository estateDataSource = provideEstatesDataSource(context);
        Executor executor = provideExecutor();
        return new ViewModelFactory(executor, userDataSource, estateDataSource);
    }
}
