package com.ph30891.lab8_ph30891.networking;

import com.ph30891.lab8_ph30891.model.District;
import com.ph30891.lab8_ph30891.model.DistrictRequest;
import com.ph30891.lab8_ph30891.model.Fruit;
import com.ph30891.lab8_ph30891.model.Order;
import com.ph30891.lab8_ph30891.model.Province;
import com.ph30891.lab8_ph30891.model.Response;
import com.ph30891.lab8_ph30891.model.ResponseGHN;
import com.ph30891.lab8_ph30891.model.Ward;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    public static String BASE_URL = "http://10.0.2.2:3000/";

    @POST("add-order")
    Call<retrofit2.Response<Response<Order>>> order(@Body Order order);

    @GET("/api/get-list-fruit")
    Call<ArrayList<Fruit>> getListFruit();
}
