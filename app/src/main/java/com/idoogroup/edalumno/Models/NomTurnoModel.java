package com.idoogroup.edalumno.Models;

import com.google.gson.annotations.SerializedName;

public class NomTurnoModel {

    @SerializedName("nombre")
    public String nombre;
    @SerializedName("horaInicial")
    public String horaInicial;
    @SerializedName("horaFinal")
    public String horaFinal;
    @SerializedName("id")
    public String id;

    public NomTurnoModel() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getHoraInicial() {
        return horaInicial;
    }

    public void setHoraInicial(String horaInicial) {
        this.horaInicial = horaInicial;
    }

    public String getHoraFinal() {
        return horaFinal;
    }

    public void setHoraFinal(String horaFinal) {
        this.horaFinal = horaFinal;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
