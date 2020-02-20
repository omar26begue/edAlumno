package com.idoogroup.edalumno.Models;


import java.util.ArrayList;
import java.util.List;

public class SubjectModelHomeworks {

    public String name;
    public int hours;
    public String description;
    public String id;
    public List<HomeworksHomeworksModel> homeworksModels;



    public SubjectModelHomeworks() {
        homeworksModels = new ArrayList<>();
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

    public List<HomeworksHomeworksModel> getHomeworksModels() {
        return homeworksModels;
    }

    public void setHomeworksModels(List<HomeworksHomeworksModel> homeworksModels) {
        this.homeworksModels = homeworksModels;
    }
}
