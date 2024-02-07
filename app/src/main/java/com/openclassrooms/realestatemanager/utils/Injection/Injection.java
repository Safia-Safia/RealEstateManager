package com.openclassrooms.realestatemanager.utils.Injection;

import android.content.Context;

import com.openclassrooms.realestatemanager.repository.EstateDataRepository;
import com.openclassrooms.realestatemanager.repository.EstateDatabase;
import com.openclassrooms.realestatemanager.repository.EstateRepository;
import com.openclassrooms.realestatemanager.repository.UserDataRepository;
import com.openclassrooms.realestatemanager.repository.UserRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Injection {


    public static UserRepository provideUserDataSource(Context context) {
        return new UserRepository();
    }

    public static EstateRepository provideEstatesDataSource(Context context){
        return new EstateRepository();
    }

    public static UserDataRepository provideUsersDataSource(Context context) {
        EstateDatabase database = EstateDatabase.getInstance(context);
        return new UserDataRepository(database.userDao());
    }

    public static EstateDataRepository provideEstateDataSource(Context context) {
        EstateDatabase database = EstateDatabase.getInstance(context);
        return new EstateDataRepository(database.estateDao());
    }


    public static ViewModelFactory provideViewModelFactory(Context context) {
        UserRepository userDataSource = provideUserDataSource(context);
        EstateRepository estateDataSource = provideEstatesDataSource(context);

        EstateDataRepository estatesDataSource = provideEstateDataSource(context);
        UserDataRepository usersDataSource = provideUsersDataSource(context);
        return new ViewModelFactory(userDataSource, estateDataSource,estatesDataSource,usersDataSource);
    }
}
