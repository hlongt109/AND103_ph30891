package com.ph30891.lab8_ph30891.networking;

import com.ph30891.lab8_ph30891.model.Fruit;
import com.ph30891.lab8_ph30891.model.Order;
import com.ph30891.lab8_ph30891.model.ResponseOrder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    public static String BASE_URL = "http://10.0.2.2:3000/";

    @POST("/api/add-order")
    Call<ResponseOrder<Order>> order(@Body Order order);

    @GET("/api/get-list-fruit")
    Call<ArrayList<Fruit>> getListFruit();
}
