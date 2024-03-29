package com.ph30891.lab8_ph30891.model;

import java.util.ArrayList;

public class GHNOrderRequest {
    private int payment_type_id;
    private String note, required_note, return_phone, return_address;
    private String to_name, to_phone, to_address, to_ward_code;
    private int to_district_id, cod_amount;
    private String content;
    private int weight,length, width, height, insurance_value, service_id, service_type_id;
    private ArrayList<GHNItem> items;

    public GHNOrderRequest() {
    }

    public GHNOrderRequest(int payment_type_id, String note, String required_note, String return_phone, String return_address, String to_name, String to_phone, String to_address, String to_ward_code, int to_district_id, int cod_amount, String content, int weight, int length, int width, int height, int insurance_value, int service_id, int service_type_id, ArrayList<GHNItem> items) {
        this.payment_type_id = payment_type_id;
        this.note = note;
        this.required_note = required_note;
        this.return_phone = return_phone;
        this.return_address = return_address;
        this.to_name = to_name;
        this.to_phone = to_phone;
        this.to_address = to_address;
        this.to_ward_code = to_ward_code;
        this.to_district_id = to_district_id;
        this.cod_amount = cod_amount;
        this.content = content;
        this.weight = weight;
        this.length = length;
        this.width = width;
        this.height = height;
        this.insurance_value = insurance_value;
        this.service_id = service_id;
        this.service_type_id = service_type_id;
        this.items = items;
    }

    public GHNOrderRequest(String to_name, String to_phone, String to_address, String to_ward_code, int to_district_id, ArrayList<GHNItem> items) {
        this.to_name = to_name;
        this.to_phone = to_phone;
        this.to_address = to_address;
        this.to_ward_code = to_ward_code;
        this.to_district_id = to_district_id;
        this.items = items;
        payment_type_id = 2;
        note = "Vui lòng gọi khách trươc khi giao";
        required_note = "KHONGCHOXEMHANG";
        return_phone = "0332190444";
        return_address = "39 NTT";
        content = "";
        cod_amount = 0;
        this.items.forEach(item -> {
            cod_amount = item.getPrice();
            weight += item.getWeight();
        });
        length = this.items.size();
        width= 19;
        height= 10;
        insurance_value = cod_amount;
        service_id = 0;
        service_type_id = 2;
    }

    public int getPayment_type_id() {
        return payment_type_id;
    }

    public GHNOrderRequest setPayment_type_id(int payment_type_id) {
        this.payment_type_id = payment_type_id;
        return this;
    }

    public String getNote() {
        return note;
    }

    public GHNOrderRequest setNote(String note) {
        this.note = note;
        return this;
    }

    public String getRequired_note() {
        return required_note;
    }

    public GHNOrderRequest setRequired_note(String required_note) {
        this.required_note = required_note;
        return this;
    }

    public String getReturn_phone() {
        return return_phone;
    }

    public GHNOrderRequest setReturn_phone(String return_phone) {
        this.return_phone = return_phone;
        return this;
    }

    public String getReturn_address() {
        return return_address;
    }

    public GHNOrderRequest setReturn_address(String return_address) {
        this.return_address = return_address;
        return this;
    }

    public String getTo_name() {
        return to_name;
    }

    public GHNOrderRequest setTo_name(String to_name) {
        this.to_name = to_name;
        return this;
    }

    public String getTo_phone() {
        return to_phone;
    }

    public GHNOrderRequest setTo_phone(String to_phone) {
        this.to_phone = to_phone;
        return this;
    }

    public String getTo_address() {
        return to_address;
    }

    public GHNOrderRequest setTo_address(String to_address) {
        this.to_address = to_address;
        return this;
    }

    public String getTo_ward_code() {
        return to_ward_code;
    }

    public GHNOrderRequest setTo_ward_code(String to_ward_code) {
        this.to_ward_code = to_ward_code;
        return this;
    }

    public int getTo_district_id() {
        return to_district_id;
    }

    public GHNOrderRequest setTo_district_id(int to_district_id) {
        this.to_district_id = to_district_id;
        return this;
    }

    public int getCod_amount() {
        return cod_amount;
    }

    public GHNOrderRequest setCod_amount(int cod_amount) {
        this.cod_amount = cod_amount;
        return this;
    }

    public String getContent() {
        return content;
    }

    public GHNOrderRequest setContent(String content) {
        this.content = content;
        return this;
    }

    public int getWeight() {
        return weight;
    }

    public GHNOrderRequest setWeight(int weight) {
        this.weight = weight;
        return this;
    }

    public int getLength() {
        return length;
    }

    public GHNOrderRequest setLength(int length) {
        this.length = length;
        return this;
    }

    public int getWidth() {
        return width;
    }

    public GHNOrderRequest setWidth(int width) {
        this.width = width;
        return this;
    }

    public int getHeight() {
        return height;
    }

    public GHNOrderRequest setHeight(int height) {
        this.height = height;
        return this;
    }

    public int getInsurance_value() {
        return insurance_value;
    }

    public GHNOrderRequest setInsurance_value(int insurance_value) {
        this.insurance_value = insurance_value;
        return this;
    }

    public int getService_id() {
        return service_id;
    }

    public GHNOrderRequest setService_id(int service_id) {
        this.service_id = service_id;
        return this;
    }

    public int getService_type_id() {
        return service_type_id;
    }

    public GHNOrderRequest setService_type_id(int service_type_id) {
        this.service_type_id = service_type_id;
        return this;
    }

    public ArrayList<GHNItem> getItems() {
        return items;
    }

    public GHNOrderRequest setItems(ArrayList<GHNItem> items) {
        this.items = items;
        return this;
    }
}
