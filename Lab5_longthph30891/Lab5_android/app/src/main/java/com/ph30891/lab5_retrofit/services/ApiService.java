package com.ph30891.lab5_retrofit.services;

import com.ph30891.lab5_retrofit.model.Distributor;
import com.ph30891.lab5_retrofit.model.Response;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    public static String BASE_URL = "http://10.0.2.2:3000/";

    @GET("/")
    Call <ArrayList<Distributor>> getListDistributor();

    @GET("/search-distributor")
    Call<ArrayList<Distributor>> searchDistributor(@Query("key") String key);

    @POST("/add-distributor")
    Call<Distributor> addDistributor(@Body Distributor distributor);

    @PUT("/update-distributor/{id}")
    Call<Distributor> updateDistributor(@Path("id")String id, @Body Distributor distributor);

    @DELETE("/delete-distributor/{id}")
    Call<Distributor> deleteDistributor(@Path("id")String id);
}
