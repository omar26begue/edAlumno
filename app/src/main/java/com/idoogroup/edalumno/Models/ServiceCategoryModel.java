package com.idoogroup.edalumno.Models;

import com.google.gson.annotations.SerializedName;

public class ServiceCategoryModel {

    @SerializedName("name")
    public String name;
    @SerializedName("active")
    public Boolean active;
    @SerializedName("description")
    public String description;
    @SerializedName("id")
    public String id;
    @SerializedName("schoolsId")
    public String schoolsId;
    @SerializedName("schoolId")
    public String schoolId;

    public ServiceCategoryModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
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

    public String getSchoolsId() {
        return schoolsId;
    }

    public void setSchoolsId(String schoolsId) {
        this.schoolsId = schoolsId;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

}
