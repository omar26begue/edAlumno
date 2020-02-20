package com.idoogroup.edalumno.Models;

import com.google.gson.annotations.SerializedName;

public class RecognitionsModel {

    @SerializedName("active")
    public Boolean active;
    @SerializedName("name")
    public String name;
    @SerializedName("created")
    public String created;
    @SerializedName("description")
    public String description;
    @SerializedName("id")
    public String id;
    @SerializedName("schoolId")
    public String schoolId;

    public RecognitionsModel() {
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

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
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
