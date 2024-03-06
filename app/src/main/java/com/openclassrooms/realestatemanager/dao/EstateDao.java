package com.openclassrooms.realestatemanager.dao;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.openclassrooms.realestatemanager.model.Estate;

import java.util.List;
@Dao
public interface EstateDao {
    // CONTENT PROVIDER
    @Query("SELECT * FROM estates")
    Cursor getEstateCursor();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void createEstate(Estate estate);

    @Query("SELECT * FROM estates ")
    List<Estate> getEstates();
    @Update
    void updateEstate(Estate estate);

    @Query("SELECT * FROM estates WHERE" +
            " price BETWEEN :minPrice AND :maxPrice " +
            "AND surface BETWEEN :minSurface AND :maxSurface " +
            "AND (school = :isSchoolFilter OR :isSchoolFilter = 0) " +
            "AND (store = :isStoreFilter OR :isStoreFilter = 0) " +
            "AND (park = :isParkFilter OR :isParkFilter = 0) " +
            "AND (parking = :isParkingFilter OR :isParkingFilter = 0) " +
            "AND (soldDate IS NOT NULL OR :isSoldFilter = 0) " +
            "OR :isLastWeekFilter = 0 " +
            "AND (estateType = :selectedEstateType OR :selectedEstateType = '')")
    LiveData<List<Estate>> getFilteredEstates(
            long minPrice, long maxPrice, long minSurface, long maxSurface, boolean isSchoolFilter,
            boolean isStoreFilter, boolean isParkFilter, boolean isParkingFilter,
            boolean isSoldFilter, boolean isLastWeekFilter, String selectedEstateType);

}
