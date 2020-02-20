package com.idoogroup.edalumno.Models;

import com.google.gson.annotations.SerializedName;

public class DayEvalModel {

    @SerializedName("active")
    public Boolean active;
    @SerializedName("qualitative")
    public String qualitative;
    @SerializedName("quantitative")
    public int quantitative = 5;
    @SerializedName("description")
    public String description;
    @SerializedName("id")
    public String id;
    @SerializedName("schoolId")
    public String schoolId;

    public DayEvalModel() {
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getQualitative() {
        return qualitative;
    }

    public void setQualitative(String qualitative) {
        this.qualitative = qualitative;
    }

    public int getQuantitative() {
        return quantitative;
    }

    public void setQuantitative(int quantitative) {
        this.quantitative = quantitative;
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
