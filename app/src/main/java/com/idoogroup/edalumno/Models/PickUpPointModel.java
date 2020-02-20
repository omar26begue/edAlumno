package com.idoogroup.edalumno.Models;

import com.google.gson.annotations.SerializedName;

public class PickUpPointModel {

    @SerializedName("place")
    public String place;
    @SerializedName("location")
    public GeoPointModel location;

    public PickUpPointModel() {
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public GeoPointModel getLocation() {
        return location;
    }

    public void setLocation(GeoPointModel location) {
        this.location = location;
    }

}
