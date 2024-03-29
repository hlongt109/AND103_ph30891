package com.ph30891.lab8_ph30891.model;

import java.io.Serializable;

public class Fruit implements Serializable {
    private String _id;
    private String name;
    private int price, quantity, weight;
    private String image;

    public Fruit(String _id, String name, int price, int quantity, int weight, String image) {
        this._id = _id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.weight = weight;
        this.image = image;
    }

    public Fruit() {
    }


    public String get_id() {
        return _id;
    }

    public Fruit set_id(String _id) {
        this._id = _id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Fruit setName(String name) {
        this.name = name;
        return this;
    }

    public int getPrice() {
        return price;
    }

    public Fruit setPrice(int price) {
        this.price = price;
        return this;
    }

    public int getQuantity() {
        return quantity;
    }

    public Fruit setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public int getWeight() {
        return weight;
    }

    public Fruit setWeight(int weight) {
        this.weight = weight;
        return this;
    }

    public String getImage() {
        return image;
    }

    public Fruit setImage(String image) {
        this.image = image;
        return this;
    }
}
