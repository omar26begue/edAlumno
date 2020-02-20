package com.idoogroup.edalumno.Models;

import com.google.gson.annotations.SerializedName;
import com.idoogroup.edalumno.Helpers.Moment.Moment;

import java.util.Date;

public class AccountsModel {

    @SerializedName("active")
    public Boolean active;
    @SerializedName("created")
    public String created;
    @SerializedName("lastAccess")
    public String lastAccess;
    @SerializedName("realm")
    public String realm;
    @SerializedName("email")
    public String email;
    @SerializedName("emailVerified")
    public String emailVerified;
    @SerializedName("owner")
    public OwnerModel owner;
    @SerializedName("id")
    public String id;

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getLastAccess() {
        if (lastAccess == null) {
            Moment moment = new Moment();
            moment.display().format("yyyy/MM/dd");
            return moment.display().format("yyyy/MM/dd");
        }
        return lastAccess.substring(0, 10);
    }

    public void setLastAccess(String lastAccess) {
        this.lastAccess = lastAccess;
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

    public String getEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(String emailVerified) {
        this.emailVerified = emailVerified;
    }

    public OwnerModel getOwner() {
        return owner;
    }

    public void setOwner(OwnerModel owner) {
        this.owner = owner;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
