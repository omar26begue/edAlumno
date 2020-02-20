package com.idoogroup.edalumno.Models;

import com.google.gson.annotations.SerializedName;

public class DiaSemanaModel {

    @SerializedName("nombre")
    public String nombre;
    @SerializedName("id")
    public String id;

    public DiaSemanaModel() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
