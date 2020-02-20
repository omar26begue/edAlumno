package com.idoogroup.edalumno.Models;

import com.google.gson.annotations.SerializedName;

public class GeoPointModel {

    @SerializedName("lat")
    public double lat;
    @SerializedName("lng")
    public double lng;

    public GeoPointModel() {
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

}
