package com.idoogroup.edalumno.Models;

import com.google.gson.annotations.SerializedName;

public class StudentsRecognitionModel {

    @SerializedName("active")
    public Boolean active;
    @SerializedName("created")
    public String created;
    @SerializedName("id")
    public String id;
    @SerializedName("studentId")
    public String studentId;
    @SerializedName("recognitionId")
    public String recognitionId;
    @SerializedName("frecuenciaId")
    public String frecuenciaId;
    @SerializedName("frecuencia")
    public FrecuenciaModel frecuencia = new FrecuenciaModel();
    @SerializedName("recognition")
    public RecognitionsModel recognition = new RecognitionsModel();

    public StudentsRecognitionModel() {
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getRecognitionId() {
        return recognitionId;
    }

    public void setRecognitionId(String recognitionId) {
        this.recognitionId = recognitionId;
    }

    public String getFrecuenciaId() {
        return frecuenciaId;
    }

    public void setFrecuenciaId(String frecuenciaId) {
        this.frecuenciaId = frecuenciaId;
    }

    public FrecuenciaModel getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(FrecuenciaModel frecuencia) {
        this.frecuencia = frecuencia;
    }

    public RecognitionsModel getRecognition() {
        return recognition;
    }

    public void setRecognition(RecognitionsModel recognition) {
        this.recognition = recognition;
    }

}
