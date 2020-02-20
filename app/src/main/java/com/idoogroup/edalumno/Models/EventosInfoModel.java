package com.idoogroup.edalumno.Models;

import com.google.gson.annotations.SerializedName;

public class EventosInfoModel {

    @SerializedName("participant")
    public boolean participant;
    @SerializedName("confirmed")
    public boolean confirmed;
    @SerializedName("id")
    public String id;
    @SerializedName("eventId")
    public String eventId;
    @SerializedName("studentId")
    public String studentId;

    public boolean isParticipant() {
        return participant;
    }

    public void setParticipant(boolean participant) {
        this.participant = participant;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

}
