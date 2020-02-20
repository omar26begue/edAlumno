package com.idoogroup.edalumno.Models;


import java.util.ArrayList;
import java.util.List;

public class NotificacionesAppModel {

    public String fecha;
    public List<NotificacionesModel> notificaciones;

    public NotificacionesAppModel() {
        notificaciones = new ArrayList<>();
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public List<NotificacionesModel> getNotificaciones() {
        return notificaciones;
    }

    public void setNotificaciones(List<NotificacionesModel> notificaciones) {
        this.notificaciones = notificaciones;
    }
}
