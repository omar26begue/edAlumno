package com.idoogroup.edalumno.Models;

import com.google.gson.annotations.Expose;

public class ResetModel {

    @Expose
    private String email;


    public ResetModel() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
