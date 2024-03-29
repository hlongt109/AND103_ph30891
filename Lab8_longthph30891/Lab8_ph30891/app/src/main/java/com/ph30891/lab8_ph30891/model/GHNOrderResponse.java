package com.ph30891.lab8_ph30891.model;

public class GHNOrderResponse {
    private String order_code;

    public GHNOrderResponse(String order_code) {
        this.order_code = order_code;
    }

    public GHNOrderResponse() {
    }

    public String getOrder_code() {
        return order_code;
    }

    public GHNOrderResponse setOrder_code(String order_code) {
        this.order_code = order_code;
        return this;
    }
}
