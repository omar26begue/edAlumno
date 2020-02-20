package com.idoogroup.edalumno.Models;


public class TareasModelHomeworks {

    public Boolean active;
    public String status;
    public int qualification;
    public String dateResolved;
    public Boolean parentCheck;
    public String dateDeliver;
    public String id;
    public String choresId;
    public String studentId;

    public TareasModelHomeworks() {
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

}
