package com.idoogroup.edalumno.Models;

import com.google.gson.annotations.SerializedName;

public class StudentsEvaluationModel {

    @SerializedName("fecha")
    public String fecha;
    @SerializedName("id")
    public String id;
    @SerializedName("studentId")
    public String studentId;
    @SerializedName("subjectId")
    public String subjectId;
    @SerializedName("frecuenciaId")
    public String frecuenciaId;
    @SerializedName("nomCalificacionId")
    public String nomCalificacionId;
    @SerializedName("subject")
    public SubjectsModel subject = new SubjectsModel();
    @SerializedName("nomCalificacion")
    public NomCalificacionModel nomCalificacion = new NomCalificacionModel();
    @SerializedName("frecuencia")
    public FrecuenciaModel frecuencia = new FrecuenciaModel();

    public StudentsEvaluationModel() {
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
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

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getFrecuenciaId() {
        return frecuenciaId;
    }

    public void setFrecuenciaId(String frecuenciaId) {
        this.frecuenciaId = frecuenciaId;
    }

    public String getNomCalificacionId() {
        return nomCalificacionId;
    }

    public void setNomCalificacionId(String nomCalificacionId) {
        this.nomCalificacionId = nomCalificacionId;
    }

    public SubjectsModel getSubject() {
        return subject;
    }

    public void setSubject(SubjectsModel subject) {
        this.subject = subject;
    }

    public NomCalificacionModel getNomCalificacion() {
        return nomCalificacion;
    }

    public void setNomCalificacion(NomCalificacionModel nomCalificacion) {
        this.nomCalificacion = nomCalificacion;
    }

    public FrecuenciaModel getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(FrecuenciaModel frecuencia) {
        this.frecuencia = frecuencia;
    }

}
