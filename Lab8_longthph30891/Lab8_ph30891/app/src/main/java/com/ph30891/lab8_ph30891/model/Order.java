package com.ph30891.lab8_ph30891.model;

public class Order {
    private String _id;
    private String order_code;
    private String id_user;

    public Order(String _id, String order_code, String id_user) {
        this._id = _id;
        this.order_code = order_code;
        this.id_user = id_user;
    }

    public Order() {
    }

    public String get_id() {
        return _id;
    }

    public Order set_id(String _id) {
        this._id = _id;
        return this;
    }

    public String getOrder_code() {
        return order_code;
    }

    public Order setOrder_code(String order_code) {
        this.order_code = order_code;
        return this;
    }

    public String getId_user() {
        return id_user;
    }

    public Order setId_user(String id_user) {
        this.id_user = id_user;
        return this;
    }
}
