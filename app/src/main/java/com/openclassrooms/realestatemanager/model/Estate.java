package com.openclassrooms.realestatemanager.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Estate implements Parcelable {

    String estateId;
    String description, picturesUri, sellerName, estateType, numberOfRoom, price, surface, address, entryDate, soldDate;
    Boolean isEstatesAvailable = true;

    List<String> picturesList = new ArrayList<>();

    List<String> picturesDescription = new ArrayList<>();

    Boolean school = false;
    Boolean store = false;
    Boolean park = false;
    Boolean parking = false;

    private Double latitude;

    private Double longitude;

    public Estate() {
    }

    public Estate(String estateId, String description, String picturesUri, List<String> pictureDescription, String sellerName, String estateType, String numberOfRoom, String price, String surface, String address, String entryDate, String soldDate, Boolean isEstatesAvailable, List<String> picturesList, Boolean school, Boolean store, Boolean park, Boolean parking, Double latitude, Double longitude) {
        this.estateId = estateId;
        this.description = description;
        this.picturesUri = picturesUri;
        this.picturesDescription = pictureDescription;
        this.sellerName = sellerName;
        this.estateType = estateType;
        this.numberOfRoom = numberOfRoom;
        this.price = price;
        this.surface = surface;
        this.address = address;
        this.entryDate = entryDate;
        this.soldDate = soldDate;
        this.isEstatesAvailable = isEstatesAvailable;
        this.picturesList = picturesList;
        this.school = school;
        this.store = store;
        this.park = park;
        this.parking = parking;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getEstateId() {
        return estateId;
    }

    public void setEstateId(String estateId) {
        this.estateId = estateId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicturesUri() {
        return picturesUri;
    }

    public void setPicturesUri(String picturesUri) {
        this.picturesUri = picturesUri;
    }

    public List<String> getPicturesDescription() {
        return picturesDescription;
    }

    public void setPicturesDescription(List<String> pictureDescription) {
        this.picturesDescription = pictureDescription;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getEstateType() {
        return estateType;
    }

    public void setEstateType(String estateType) {
        this.estateType = estateType;
    }

    public String getNumberOfRoom() {
        return numberOfRoom;
    }

    public void setNumberOfRoom(String numberOfRoom) {
        this.numberOfRoom = numberOfRoom;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSurface() {
        return surface;
    }

    public void setSurface(String surface) {
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

    public List<String> getPicturesList() {
        return picturesList;
    }

    public void setPicturesList(List<String> picturesList) {
        this.picturesList = picturesList;
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

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.estateId);
        dest.writeString(this.description);
        dest.writeString(this.picturesUri);
        dest.writeStringList(this.picturesDescription);
        dest.writeString(this.sellerName);
        dest.writeString(this.estateType);
        dest.writeString(this.numberOfRoom);
        dest.writeString(this.price);
        dest.writeString(this.surface);
        dest.writeString(this.address);
        dest.writeString(this.entryDate);
        dest.writeString(this.soldDate);
        dest.writeValue(this.isEstatesAvailable);
        dest.writeStringList(this.picturesList);
        dest.writeValue(this.school);
        dest.writeValue(this.store);
        dest.writeValue(this.park);
        dest.writeValue(this.parking);
        dest.writeValue(this.latitude);
        dest.writeValue(this.longitude);
    }

    public void readFromParcel(Parcel source) {
        this.estateId = source.readString();
        this.description = source.readString();
        this.picturesUri = source.readString();
        this.picturesDescription = source.createStringArrayList();
        this.sellerName = source.readString();
        this.estateType = source.readString();
        this.numberOfRoom = source.readString();
        this.price = source.readString();
        this.surface = source.readString();
        this.address = source.readString();
        this.entryDate = source.readString();
        this.soldDate = source.readString();
        this.isEstatesAvailable = (Boolean) source.readValue(Boolean.class.getClassLoader());
        this.picturesList = source.createStringArrayList();
        this.school = (Boolean) source.readValue(Boolean.class.getClassLoader());
        this.store = (Boolean) source.readValue(Boolean.class.getClassLoader());
        this.park = (Boolean) source.readValue(Boolean.class.getClassLoader());
        this.parking = (Boolean) source.readValue(Boolean.class.getClassLoader());
        this.latitude = (Double) source.readValue(Double.class.getClassLoader());
        this.longitude = (Double) source.readValue(Double.class.getClassLoader());
    }

    protected Estate(Parcel in) {
        this.estateId = in.readString();
        this.description = in.readString();
        this.picturesUri = in.readString();
        this.picturesDescription = in.createStringArrayList();
        this.sellerName = in.readString();
        this.estateType = in.readString();
        this.numberOfRoom = in.readString();
        this.price = in.readString();
        this.surface = in.readString();
        this.address = in.readString();
        this.entryDate = in.readString();
        this.soldDate = in.readString();
        this.isEstatesAvailable = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.picturesList = in.createStringArrayList();
        this.school = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.store = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.park = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.parking = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.latitude = (Double) in.readValue(Double.class.getClassLoader());
        this.longitude = (Double) in.readValue(Double.class.getClassLoader());
    }

    public static final Creator<Estate> CREATOR = new Creator<Estate>() {
        @Override
        public Estate createFromParcel(Parcel source) {
            return new Estate(source);
        }

        @Override
        public Estate[] newArray(int size) {
            return new Estate[size];
        }
    };


}
