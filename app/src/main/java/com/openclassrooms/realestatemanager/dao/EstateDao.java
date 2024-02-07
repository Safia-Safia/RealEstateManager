package com.openclassrooms.realestatemanager.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.openclassrooms.realestatemanager.model.Estate;

import java.util.List;
@Dao
public interface EstateDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void createEstate(Estate estate);

    @Query("SELECT * FROM estates ")
    LiveData<List<Estate>> getEstates();

    @Transaction
    @Update
    void updateEstate(Estate estate);
}
