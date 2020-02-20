package com.idoogroup.edalumno.Models;

import com.google.gson.annotations.SerializedName;

public class SubjectsModel {

    @SerializedName("name")
    public String name;
    @SerializedName("hours")
    public int hours;
    @SerializedName("description")
    public String description;
    @SerializedName("id")
    public String id;
    @SerializedName("schoolId")
    public String schoolId;

    public SubjectsModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

}
