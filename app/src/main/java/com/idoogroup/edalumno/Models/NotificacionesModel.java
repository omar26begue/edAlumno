package com.idoogroup.edalumno.Models;

import com.google.gson.annotations.SerializedName;

public class NotificacionesModel {

    @SerializedName("created")
    public String created;
    @SerializedName("message")
    public String message;
    @SerializedName("leido")
    public Boolean leido;
    @SerializedName("tipo")
    public String tipo;
    @SerializedName("element")
    public String element;
    @SerializedName("id")
    public String id;
    @SerializedName("accountId")
    public String accountId;

    public NotificacionesModel() {
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getLeido() {
        return leido;
    }

    public void setLeido(Boolean leido) {
        this.leido = leido;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

}
