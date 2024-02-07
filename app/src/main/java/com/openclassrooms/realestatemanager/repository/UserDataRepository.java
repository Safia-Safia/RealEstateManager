package com.openclassrooms.realestatemanager.repository;

import androidx.lifecycle.LiveData;

import com.openclassrooms.realestatemanager.dao.UserDao;
import com.openclassrooms.realestatemanager.model.User;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class UserDataRepository {

    private final UserDao userDao;
    private final Executor executor;

    public UserDataRepository(UserDao userDao) {
        this.userDao = userDao;
        this.executor = Executors.newSingleThreadExecutor();
    }

    public void createUser(User user) {
        executor.execute(() -> userDao.createUser(user));
    }

    public LiveData<List<User>> getUsers() {
        return userDao.getUsers();
    }
}
