package com.openclassrooms.realestatemanager.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.openclassrooms.realestatemanager.dao.PictureListTypeConverter;
import com.openclassrooms.realestatemanager.dao.UserTypeConverter;

import java.io.Serializable;
import java.util.ArrayList;

@Entity(tableName = "estates")
public class Estate implements Serializable {

    @PrimaryKey
    @ColumnInfo(name = "id")
    @NonNull
    private String id;
    String description, coverPictureUrl, estateType, address, entryDate, soldDate, city;
    long price, surface, numberOfRoom;
    Boolean isEstatesAvailable = true;
    Boolean school = false;
    Boolean store = false;
    Boolean park = false;
    Boolean parking = false;
    @TypeConverters(UserTypeConverter.class)
    User user;
    @TypeConverters(PictureListTypeConverter.class)
    ArrayList<Picture> pictures = new ArrayList<>();
    private Double latitude, longitude;

    public Estate() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCoverPictureUrl() {
        return coverPictureUrl;
    }

    public void setCoverPictureUrl(String coverPictureUrl) {
        this.coverPictureUrl = coverPictureUrl;
    }

    public String getEstateType() {
        return estateType;
    }

    public void setEstateType(String estateType) {
        this.estateType = estateType;
    }

    public long getNumberOfRoom() {
        return numberOfRoom;
    }

    public void setNumberOfRoom(long numberOfRoom) {
        this.numberOfRoom = numberOfRoom;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public long getSurface() {
        return surface;
    }

    public void setSurface(long surface) {
        this.surface = surface;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getSoldDate() {
        return soldDate;
    }

    public void setSoldDate(String soldDate) {
        this.soldDate = soldDate;
    }

    public Boolean getEstatesAvailable() {
        return isEstatesAvailable;
    }

    public void setEstatesAvailable(Boolean estatesAvailable) {
        isEstatesAvailable = estatesAvailable;
    }

    public Boolean getSchool() {
        return school;
    }

    public void setSchool(Boolean school) {
        this.school = school;
    }

    public Boolean getStore() {
        return store;
    }

    public void setStore(Boolean store) {
        this.store = store;
    }

    public Boolean getPark() {
        return park;
    }

    public void setPark(Boolean park) {
        this.park = park;
    }

    public Boolean getParking() {
        return parking;
    }

    public void setParking(Boolean parking) {
        this.parking = parking;
    }

    public ArrayList<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(ArrayList<Picture> pictures) {
        this.pictures = pictures;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }


}