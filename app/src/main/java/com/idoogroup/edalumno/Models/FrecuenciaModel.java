package com.idoogroup.edalumno.Models;

import com.google.gson.annotations.SerializedName;

public class FrecuenciaModel {

    @SerializedName("id")
    public String id;
    @SerializedName("tipo_frecuencia")
    public String tipo_frecuencia;

    public FrecuenciaModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipo_frecuencia() {
        return tipo_frecuencia;
    }

    public void setTipo_frecuencia(String tipo_frecuencia) {
        this.tipo_frecuencia = tipo_frecuencia;
    }

}
