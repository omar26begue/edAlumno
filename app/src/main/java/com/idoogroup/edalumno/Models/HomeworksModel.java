package com.idoogroup.edalumno.Models;

import com.google.gson.annotations.SerializedName;


public class HomeworksModel {

    @SerializedName("name")
    public String name;
    @SerializedName("description")
    public String description;
    @SerializedName("active")
    public Boolean active;
    @SerializedName("created")
    public String created;
    @SerializedName("dateDeliver")
    public String dateDeliver;
    @SerializedName("id")
    public String id;
    @SerializedName("teacherId")
    public String teacherId;
    @SerializedName("subjectId")
    public String subjectId;
    @SerializedName("frecuenciaId")
    public String frecuenciaId;
    @SerializedName("subject")
    public SubjectsModel subject = new SubjectsModel();
    //public TareasModel tarea = new TareasModel();

    public HomeworksModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getDateDeliver() {
        return dateDeliver;
    }

    public void setDateDeliver(String dateDeliver) {
        this.dateDeliver = dateDeliver;
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

    public SubjectsModel getSubject() {
        return subject;
    }

    public void setSubject(SubjectsModel subject) {
        this.subject = subject;
    }
}
