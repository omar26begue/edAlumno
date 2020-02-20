package com.idoogroup.edalumno.Models;

import com.google.gson.annotations.SerializedName;

public class AssistancesModel {

    @SerializedName("assistance")
    public Boolean assistance;
    @SerializedName("fecha")
    public String fecha;
    @SerializedName("verificada")
    public Boolean verificada;
    @SerializedName("participation")
    public Boolean participation;
    @SerializedName("calification")
    public int calification;
    @SerializedName("emotionalEvaluation")
    public String emotionalEvaluation;
    @SerializedName("id")
    public String id;
    @SerializedName("studentId")
    public String studentId;
    @SerializedName("nomEmotionalEvaluationId")
    public String nomEmotionalEvaluationId;
    @SerializedName("calendarizacionId")
    public String calendarizacionId;
    @SerializedName("dayEvalId")
    public String dayEvalId;
    @SerializedName("dayEval")
    public DayEvalModel dayEval = new DayEvalModel();
    @SerializedName("calendarizacion")
    public CalendarizacionModel calendarizacion = new CalendarizacionModel();

    public AssistancesModel() {
    }

    public Boolean getAssistance() {
        return assistance;
    }

    public void setAssistance(Boolean assistance) {
        this.assistance = assistance;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Boolean getVerificada() {
        return verificada;
    }

    public void setVerificada(Boolean verificada) {
        this.verificada = verificada;
    }

    public Boolean getParticipation() {
        return participation;
    }

    public void setParticipation(Boolean participation) {
        this.participation = participation;
    }

    public int getCalification() {
        return calification;
    }

    public void setCalification(int calification) {
        this.calification = calification;
    }

    public String getEmotionalEvaluation() {
        return emotionalEvaluation;
    }

    public void setEmotionalEvaluation(String emotionalEvaluation) {
        this.emotionalEvaluation = emotionalEvaluation;
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

    public String getNomEmotionalEvaluationId() {
        return nomEmotionalEvaluationId;
    }

    public void setNomEmotionalEvaluationId(String nomEmotionalEvaluationId) {
        this.nomEmotionalEvaluationId = nomEmotionalEvaluationId;
    }

    public String getCalendarizacionId() {
        return calendarizacionId;
    }

    public void setCalendarizacionId(String calendarizacionId) {
        this.calendarizacionId = calendarizacionId;
    }

    public String getDayEvalId() {
        return dayEvalId;
    }

    public void setDayEvalId(String dayEvalId) {
        this.dayEvalId = dayEvalId;
    }

    public DayEvalModel getDayEval() {
        return dayEval;
    }

    public void setDayEval(DayEvalModel dayEval) {
        this.dayEval = dayEval;
    }

    public CalendarizacionModel getCalendarizacion() {
        return calendarizacion;
    }

    public void setCalendarizacion(CalendarizacionModel calendarizacion) {
        this.calendarizacion = calendarizacion;
    }

}
