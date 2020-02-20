package com.idoogroup.edalumno.Models;


import com.google.gson.annotations.SerializedName;

public class UserModel {

    @SerializedName("id")
    public String token;
    @SerializedName("account")
    public AccountsModel account;

    public UserModel() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public AccountsModel getAccount() {
        return account;
    }

    public void setAccount(AccountsModel account) {
        this.account = account;
    }

}
