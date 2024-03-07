package com.ph30891.baitapbuoi2_ph30891.view.model;

public class Message {
    private String email;
    private String message;
    private String dateTime;

    public Message(String email, String message, String dateTime) {
        this.email = email;
        this.message = message;
        this.dateTime = dateTime;
    }

    public Message() {
    }

    public String getEmail() {
        return email;
    }

    public Message setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Message setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getDateTime() {
        return dateTime;
    }

    public Message setDateTime(String dateTime) {
        this.dateTime = dateTime;
        return this;
    }
}
