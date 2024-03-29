package com.ph30891.lab8_ph30891.model;

public class GHNItem {
    private String name, code;
    private int quantity, price, weight;

    public GHNItem() {
    }

    public GHNItem(String name, String code, int quantity, int price, int weight) {
        this.name = name;
        this.code = code;
        this.quantity = quantity;
        this.price = price;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public GHNItem setName(String name) {
        this.name = name;
        return this;
    }

    public String getCode() {
        return code;
    }

    public GHNItem setCode(String code) {
        this.code = code;
        return this;
    }

    public int getQuantity() {
        return quantity;
    }

    public GHNItem setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public int getPrice() {
        return price;
    }

    public GHNItem setPrice(int price) {
        this.price = price;
        return this;
    }

    public int getWeight() {
        return weight;
    }

    public GHNItem setWeight(int weight) {
        this.weight = weight;
        return this;
    }
}
