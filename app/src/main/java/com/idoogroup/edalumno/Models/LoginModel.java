package com.idoogroup.edalumno.Models;

import com.google.gson.annotations.Expose;

public class LoginModel {

    @Expose
    private String realm;

    @Expose
    private String email;

    @Expose
    private String password;


    public LoginModel() {
    }


    public String getRealm() {
        return realm;
    }

    public void setRealm(String realm) {
        this.realm = realm;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
