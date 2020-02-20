package com.idoogroup.edalumno.Models;

import com.google.gson.annotations.SerializedName;


public class TareasModel {

    @SerializedName("active")
    public Boolean active;
    @SerializedName("status")
    public String status;
    @SerializedName("qualification")
    public int qualification;
    @SerializedName("dateResolved")
    public String dateResolved;
    @SerializedName("parentCheck")
    public Boolean parentCheck;
    @SerializedName("dateDeliver")
    public String dateDeliver;
    @SerializedName("id")
    public String id;
    @SerializedName("choresId")
    public String choresId;
    @SerializedName("studentId")
    public String studentId;
    @SerializedName("homework")
    public HomeworksModel homework = new HomeworksModel();

    public TareasModel() {
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getQualification() {
        return qualification;
    }

    public void setQualification(int qualification) {
        this.qualification = qualification;
    }

    public String getDateResolved() {
        return dateResolved;
    }

    public void setDateResolved(String dateResolved) {
        this.dateResolved = dateResolved;
    }

    public Boolean getParentCheck() {
        return parentCheck;
    }

    public void setParentCheck(Boolean parentCheck) {
        this.parentCheck = parentCheck;
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

    public String getChoresId() {
        return choresId;
    }

    public void setChoresId(String choresId) {
        this.choresId = choresId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public HomeworksModel getHomework() {
        return homework;
    }

    public void setHomework(HomeworksModel homework) {
        this.homework = homework;
    }

}
