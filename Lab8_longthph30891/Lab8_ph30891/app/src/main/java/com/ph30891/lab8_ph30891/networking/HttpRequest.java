package com.ph30891.lab8_ph30891.networking;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpRequest {
    private ApiService apiService;

    public HttpRequest(){
        apiService = new Retrofit.Builder()
                .baseUrl(ApiService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ApiService.class);
    }
    public ApiService callApi() {return apiService;}
}
