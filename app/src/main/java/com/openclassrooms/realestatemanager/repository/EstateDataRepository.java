package com.openclassrooms.realestatemanager.repository;

import androidx.lifecycle.LiveData;

import com.openclassrooms.realestatemanager.dao.EstateDao;
import com.openclassrooms.realestatemanager.model.Estate;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class EstateDataRepository {

    private final EstateDao estateDao;
    private final Executor executor;

    public EstateDataRepository(EstateDao estateDao) {
        this.estateDao = estateDao;
        this.executor = Executors.newSingleThreadExecutor();
    }

    public void createEstate(Estate estate) {
        executor.execute(() ->  estateDao.createEstate(estate));
    }

    public LiveData<List<Estate>> getEstates() {
        return estateDao.getEstates();
    }

    public void updateEstate(Estate estate) {
        executor.execute(() -> estateDao.updateEstate(estate));
    }
}
