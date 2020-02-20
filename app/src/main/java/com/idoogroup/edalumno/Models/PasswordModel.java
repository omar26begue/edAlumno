package com.idoogroup.edalumno.Models;


import com.google.gson.annotations.Expose;

public class PasswordModel {

    @Expose
    private String oldPassword;

    @Expose
    private String newPassword;


    public PasswordModel() {
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
