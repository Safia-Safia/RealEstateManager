package com.openclassrooms.realestatemanager.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Property implements Parcelable {
    int price, numberOfRoom ,propertySurface, id;

    String description, picturesUri, sellerName;
    Boolean propertyStatus, school, store, park, parking;
    Date entryDate, soldDate;

    public Property(int price, int numberOfRoom, int propertySurface, String description, String picturesUri, String sellerName, Boolean propertyStatus, Boolean school, Boolean store, Boolean park, Boolean parking, Date entryDate, Date soldDate) {
        this.price = price;
        this.numberOfRoom = numberOfRoom;
        this.propertySurface = propertySurface;
        this.description = description;
        this.picturesUri = picturesUri;
        this.sellerName = sellerName;
        this.propertyStatus = propertyStatus;
        this.school = school;
        this.store = store;
        this.park = park;
        this.parking = parking;
        this.entryDate = entryDate;
        this.soldDate = soldDate;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getNumberOfRoom() {
        return numberOfRoom;
    }

    public void setNumberOfRoom(int numberOfRoom) {
        this.numberOfRoom = numberOfRoom;
    }

    public int getPropertySurface() {
        return propertySurface;
    }

    public void setPropertySurface(int propertySurface) {
        this.propertySurface = propertySurface;
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

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public Date getSoldDate() {
        return soldDate;
    }

    public void setSoldDate(Date soldDate) {
        this.soldDate = soldDate;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.price);
        dest.writeInt(this.numberOfRoom);
        dest.writeInt(this.propertySurface);
        dest.writeString(this.description);
        dest.writeString(this.picturesUri);
        dest.writeString(this.sellerName);
        dest.writeValue(this.propertyStatus);
        dest.writeValue(this.school);
        dest.writeValue(this.store);
        dest.writeValue(this.park);
        dest.writeValue(this.parking);
        dest.writeLong(this.entryDate != null ? this.entryDate.getTime() : -1);
        dest.writeLong(this.soldDate != null ? this.soldDate.getTime() : -1);
    }

    public void readFromParcel(Parcel source) {
        this.price = source.readInt();
        this.numberOfRoom = source.readInt();
        this.propertySurface = source.readInt();
        this.description = source.readString();
        this.picturesUri = source.readString();
        this.sellerName = source.readString();
        this.propertyStatus = (Boolean) source.readValue(Boolean.class.getClassLoader());
        this.school = (Boolean) source.readValue(Boolean.class.getClassLoader());
        this.store = (Boolean) source.readValue(Boolean.class.getClassLoader());
        this.park = (Boolean) source.readValue(Boolean.class.getClassLoader());
        this.parking = (Boolean) source.readValue(Boolean.class.getClassLoader());
        long tmpEntryDate = source.readLong();
        this.entryDate = tmpEntryDate == -1 ? null : new Date(tmpEntryDate);
        long tmpSoldDate = source.readLong();
        this.soldDate = tmpSoldDate == -1 ? null : new Date(tmpSoldDate);
    }

    protected Property(Parcel in) {
        this.price = in.readInt();
        this.numberOfRoom = in.readInt();
        this.propertySurface = in.readInt();
        this.description = in.readString();
        this.picturesUri = in.readString();
        this.sellerName = in.readString();
        this.propertyStatus = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.school = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.store = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.park = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.parking = (Boolean) in.readValue(Boolean.class.getClassLoader());
        long tmpEntryDate = in.readLong();
        this.entryDate = tmpEntryDate == -1 ? null : new Date(tmpEntryDate);
        long tmpSoldDate = in.readLong();
        this.soldDate = tmpSoldDate == -1 ? null : new Date(tmpSoldDate);
    }

    public static final Parcelable.Creator<Property> CREATOR = new Parcelable.Creator<Property>() {
        @Override
        public Property createFromParcel(Parcel source) {
            return new Property(source);
        }

        @Override
        public Property[] newArray(int size) {
            return new Property[size];
        }
    };
}
