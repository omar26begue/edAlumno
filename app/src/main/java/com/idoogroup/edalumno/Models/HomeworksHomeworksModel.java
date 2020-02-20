package com.idoogroup.edalumno.Models;


public class HomeworksHomeworksModel {

    public String name;
    public String description;
    public Boolean active;
    public String created;
    public String dateDeliver;
    public String id;
    public String teacherId;
    public String subjectId;
    public String subjectName;
    public String frecuenciaId;
    public TareasModelHomeworks tarea = new TareasModelHomeworks();

    public HomeworksHomeworksModel() {
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

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getFrecuenciaId() {
        return frecuenciaId;
    }

    public void setFrecuenciaId(String frecuenciaId) {
        this.frecuenciaId = frecuenciaId;
    }

    public TareasModelHomeworks getTarea() {
        return tarea;
    }

    public void setTarea(TareasModelHomeworks tarea) {
        this.tarea = tarea;
    }
}
