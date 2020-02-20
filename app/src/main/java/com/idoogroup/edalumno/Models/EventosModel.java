package com.idoogroup.edalumno.Models;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


public class EventosModel {

    @SerializedName("date")
    public String horarios;
    @SerializedName("name")
    public String nombreEvento;
    @SerializedName("description")
    public String descripcionEvento;
    @SerializedName("groups")
    public List<String> salon;
    @SerializedName("place")
    public String lugar;
    @SerializedName("placeLocation")
    public GeoPointModel placeLocation;
    @SerializedName("placeAddress")
    public String placeAddress;
    @SerializedName("pickUpPoint")
    public List<PickUpPointModel> pickUpPoint;
    @SerializedName("active")
    public Boolean active;
    @SerializedName("id")
    public String id;
    @SerializedName("teacherId")
    public String teacherId;
    @SerializedName("info")
    public EventosInfoModel info;


    public EventosModel() {
    }

    public String getHorarios() {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat fmt2 = new SimpleDateFormat("dd/MM hh:mm a");
        fmt.setTimeZone(TimeZone.getTimeZone("UTC"));

        try {
            Date date = fmt.parse(horarios);
            return fmt2.format(date);
        } catch (Exception error) {
            Log.e("EDUKO_STUDENTS", error.getMessage());
        }

        return null;
    }

    public void setHorarios(String horarios) {
        this.horarios = horarios;
    }

    public String getNombreEvento() {
        return nombreEvento;
    }

    public void setNombreEvento(String nombreEvento) {
        this.nombreEvento = nombreEvento;
    }

    public String getDescripcionEvento() {
        return descripcionEvento;
    }

    public void setDescripcionEvento(String descripcionEvento) {
        this.descripcionEvento = descripcionEvento;
    }

    public List<String> getSalon() {
        return salon;
    }

    public void setSalon(List<String> salon) {
        this.salon = salon;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public GeoPointModel getPlaceLocation() {
        return placeLocation;
    }

    public void setPlaceLocation(GeoPointModel placeLocation) {
        this.placeLocation = placeLocation;
    }

    public String getPlaceAddress() {
        return placeAddress;
    }

    public void setPlaceAddress(String placeAddress) {
        this.placeAddress = placeAddress;
    }

    public List<PickUpPointModel> getPickUpPoint() {
        return pickUpPoint;
    }

    public void setPickUpPoint(List<PickUpPointModel> pickUpPoint) {
        this.pickUpPoint = pickUpPoint;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public EventosInfoModel getInfo() {
        return info;
    }

    public void setInfo(EventosInfoModel info) {
        this.info = info;
    }

}
