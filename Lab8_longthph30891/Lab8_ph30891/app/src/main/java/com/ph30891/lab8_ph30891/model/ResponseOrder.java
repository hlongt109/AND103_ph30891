package com.ph30891.lab8_ph30891.model;

public class ResponseOrder<T>{
    private int status;
    private String message;
    private T data;

    public ResponseOrder() {
    }

    public ResponseOrder(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public ResponseOrder<T> setStatus(int status) {
        this.status = status;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ResponseOrder<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getData() {
        return data;
    }

    public ResponseOrder<T> setData(T data) {
        this.data = data;
        return this;
    }
}
