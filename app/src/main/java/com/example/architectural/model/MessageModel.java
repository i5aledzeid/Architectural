package com.example.architectural.model;

public class MessageModel {

    private String user;
    private String message;
    private String date;

    // for firebase getting data back
    public MessageModel() {
    }

    public MessageModel(String user, String message, String date) {
        this.user = user;
        this.message = message;
        this.date = date;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
