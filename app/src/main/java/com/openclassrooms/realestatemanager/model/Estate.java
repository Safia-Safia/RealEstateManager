package com.openclassrooms.realestatemanager.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Estate implements Parcelable {
    int id;
    String description, picturesUri, sellerName, estateType, numberOfRoom, price, surface, address,entryDate, soldDate;
    Boolean propertyStatus, school, store, park, parking;

    public Estate(){

    }
    public Estate(int id, String description, String picturesUri, String sellerName, String estateType, String numberOfRoom, String price, String surface, String address, String entryDate, String soldDate, Boolean propertyStatus, Boolean school, Boolean store, Boolean park, Boolean parking) {
        this.id = id;
        this.description = description;
        this.picturesUri = picturesUri;
        this.sellerName = sellerName;
        this.estateType = estateType;
        this.numberOfRoom = numberOfRoom;
        this.price = price;
        this.surface = surface;
        this.address = address;
        this.entryDate = entryDate;
        this.soldDate = soldDate;
        this.propertyStatus = propertyStatus;
        this.school = school;
        this.store = store;
        this.park = park;
        this.parking = parking;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Boolean getPropertyStatus() {
        return propertyStatus;
    }

    public void setPropertyStatus(Boolean propertyStatus) {
        this.propertyStatus = propertyStatus;
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.description);
        dest.writeString(this.picturesUri);
        dest.writeString(this.sellerName);
        dest.writeString(this.estateType);
        dest.writeString(this.numberOfRoom);
        dest.writeString(this.price);
        dest.writeString(this.surface);
        dest.writeString(this.address);
        dest.writeValue(this.propertyStatus);
        dest.writeValue(this.school);
        dest.writeValue(this.store);
        dest.writeValue(this.park);
        dest.writeValue(this.parking);
        dest.writeString(this.entryDate);
        dest.writeString(this.soldDate);
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readInt();
        this.description = source.readString();
        this.picturesUri = source.readString();
        this.sellerName = source.readString();
        this.estateType = source.readString();
        this.numberOfRoom = source.readString();
        this.price = source.readString();
        this.surface = source.readString();
        this.address = source.readString();
        this.entryDate = source.readString();
        this.soldDate = source.readString();
        this.propertyStatus = (Boolean) source.readValue(Boolean.class.getClassLoader());
        this.school = (Boolean) source.readValue(Boolean.class.getClassLoader());
        this.store = (Boolean) source.readValue(Boolean.class.getClassLoader());
        this.park = (Boolean) source.readValue(Boolean.class.getClassLoader());
        this.parking = (Boolean) source.readValue(Boolean.class.getClassLoader());
    }

    protected Estate(Parcel in) {
        this.id = in.readInt();
        this.description = in.readString();
        this.picturesUri = in.readString();
        this.sellerName = in.readString();
        this.estateType = in.readString();
        this.numberOfRoom = in.readString();
        this.price = in.readString();
        this.surface = in.readString();
        this.address = in.readString();
        this.entryDate = in.readString();
        this.soldDate = in.readString();
        this.propertyStatus = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.school = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.store = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.park = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.parking = (Boolean) in.readValue(Boolean.class.getClassLoader());
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
