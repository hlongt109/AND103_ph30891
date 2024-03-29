package com.ph30891.lab8_ph30891.networking;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GHNRequest {
    // shop id cuar tai khoan ghn
    public final static String SHOPID = "191531";
    // token cua tai khoan ghn
    public final static String TokenGHN = "8ef5d687-ec1f-11ee-a6e6-e60958111f48";
    private GHNServices ghnServices;

    public GHNRequest(){
        // tao intercept
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(chain -> {
            Request request = chain.request().newBuilder()
                    .addHeader("ShopId",SHOPID)
                    .addHeader("Token",TokenGHN)
                    .build();
            return  chain.proceed(request);
        });
        // create retrofit
        ghnServices = new Retrofit.Builder()
                .baseUrl(GHNServices.GHN_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build().create(GHNServices.class);
    }
    public GHNServices callAPI(){
        return ghnServices;
    }
}
