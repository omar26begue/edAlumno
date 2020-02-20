package com.idoogroup.edalumno.Models;

import com.google.gson.annotations.SerializedName;

public class OwnerModel {

    @SerializedName("active")
    public Boolean active;
    @SerializedName("name")
    public String name;
    @SerializedName("lastName")
    public String lastName;
    @SerializedName("gender")
    public String gender;
    @SerializedName("cellPhoneNumber")
    public String cellPhoneNumber;
    @SerializedName("email")
    public String email;
    @SerializedName("id")
    public String id;
    @SerializedName("parentId")
    public String parentId;
    @SerializedName("schoolId")
    public String schoolId;
    @SerializedName("cycleId")
    public String cycleId;
    @SerializedName("classroomId")
    public String classroomId;

    public OwnerModel() {
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCellPhoneNumber() {
        return cellPhoneNumber;
    }

    public void setCellPhoneNumber(String cellPhoneNumber) {
        this.cellPhoneNumber = cellPhoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getCycleId() {
        return cycleId;
    }

    public void setCycleId(String cycleId) {
        this.cycleId = cycleId;
    }

    public String getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(String classroomId) {
        this.classroomId = classroomId;
    }

}
