package com.idoogroup.edalumno.Models;

import com.google.gson.annotations.SerializedName;

public class CalendarizacionModel {

    @SerializedName("fecha")
    public String fecha;
    @SerializedName("assistance")
    public Boolean assistance;
    @SerializedName("id")
    public String id;
    @SerializedName("nomTurnoId")
    public String nomTurnoId;
    @SerializedName("subjectId")
    public String subjectId;
    @SerializedName("teacherId")
    public String teacherId;
    @SerializedName("roomId")
    public String roomId;
    @SerializedName("classroomId")
    public String classroomId;
    @SerializedName("schoolId")
    public String schoolId;
    @SerializedName("diaSemanaId")
    public String diaSemanaId;
    @SerializedName("weekId")
    public String weekId;
    @SerializedName("subject")
    public SubjectsModel subject = new SubjectsModel();
    @SerializedName("diaSemana")
    public DiaSemanaModel diaSemana = new DiaSemanaModel();
    @SerializedName("nomTurno")
    public NomTurnoModel nomTurno = new NomTurnoModel();
    @SerializedName("teacher")
    public TeacherModel teacher = new TeacherModel();

    public CalendarizacionModel() {
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Boolean getAssistance() {
        return assistance;
    }

    public void setAssistance(Boolean assistance) {
        this.assistance = assistance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNomTurnoId() {
        return nomTurnoId;
    }

    public void setNomTurnoId(String nomTurnoId) {
        this.nomTurnoId = nomTurnoId;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(String classroomId) {
        this.classroomId = classroomId;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getDiaSemanaId() {
        return diaSemanaId;
    }

    public void setDiaSemanaId(String diaSemanaId) {
        this.diaSemanaId = diaSemanaId;
    }

    public String getWeekId() {
        return weekId;
    }

    public void setWeekId(String weekId) {
        this.weekId = weekId;
    }

    public SubjectsModel getSubject() {
        return subject;
    }

    public void setSubject(SubjectsModel subject) {
        this.subject = subject;
    }

    public DiaSemanaModel getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(DiaSemanaModel diaSemana) {
        this.diaSemana = diaSemana;
    }

    public NomTurnoModel getNomTurno() {
        return nomTurno;
    }

    public void setNomTurno(NomTurnoModel nomTurno) {
        this.nomTurno = nomTurno;
    }

    public TeacherModel getTeacher() {
        return teacher;
    }

    public void setTeacher(TeacherModel teacher) {
        this.teacher = teacher;
    }

}
