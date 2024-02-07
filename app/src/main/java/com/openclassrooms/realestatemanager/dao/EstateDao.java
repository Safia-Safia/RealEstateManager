package com.openclassrooms.realestatemanager.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.openclassrooms.realestatemanager.model.Estate;

import java.util.List;

public interface EstateDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void createEstate(Estate estate);

    @Query("SELECT * FROM Estate ")
    LiveData<List<Estate>> getEstates();

    @Update
    void updateEstate(Estate estate);
}
