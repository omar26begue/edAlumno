package com.idoogroup.edalumno.Models;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SchoolModel {

    @SerializedName("name")
    public String name;
    @SerializedName("address")
    public String address;
    @SerializedName("location")
    public GeoPointModel location;
    @SerializedName("phoneNumberList")
    public List<String> phoneNumberList;
    @SerializedName("rector")
    public String rector;
    @SerializedName("administrator")
    public String administrator;
    @SerializedName("clavePublica")
    public String clavePublica;
    @SerializedName("clavePrivada")
    public String clavePrivada;
    @SerializedName("id")
    public String id;

    public SchoolModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public GeoPointModel getLocation() {
        return location;
    }

    public void setLocation(GeoPointModel location) {
        this.location = location;
    }

    public List<String> getPhoneNumberList() {
        return phoneNumberList;
    }

    public void setPhoneNumberList(List<String> phoneNumberList) {
        this.phoneNumberList = phoneNumberList;
    }

    public String getRector() {
        return rector;
    }

    public void setRector(String rector) {
        this.rector = rector;
    }

    public String getAdministrator() {
        return administrator;
    }

    public void setAdministrator(String administrator) {
        this.administrator = administrator;
    }

    public String getClavePublica() {
        return clavePublica;
    }

    public void setClavePublica(String clavePublica) {
        this.clavePublica = clavePublica;
    }

    public String getClavePrivada() {
        return clavePrivada;
    }

    public void setClavePrivada(String clavePrivada) {
        this.clavePrivada = clavePrivada;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
