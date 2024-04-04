package com.ph30891.lab6_ph30891.networking;

import com.ph30891.lab6_ph30891.model.Distributor;
import com.ph30891.lab6_ph30891.model.Fruit;
import com.ph30891.lab6_ph30891.model.Page;
import com.ph30891.lab6_ph30891.model.Response;
import com.ph30891.lab6_ph30891.model.User;

import java.util.ArrayList;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ApiService {
    public static String BASE_URL = "http://10.0.2.2:3000/";

    @Multipart
    @POST("/api/register-send-email")
    Call<Response<User>> register(@Part("username")RequestBody username,
                                  @Part("password")RequestBody password,
                                  @Part("email")RequestBody email,
                                  @Part("name") RequestBody name,
                                  @Part MultipartBody.Part avatar);

    @POST("/api/login")
    Call<Response<User>> login(@Body User user);

    @GET("/api/get-list-fruit")
    Call<Response<ArrayList<Fruit>>> getListFruit();

    @Multipart
    @POST("/api/add-fruit-with-file-image")
    Call<Response<Fruit>> addFruitWithFileImage(@PartMap Map<String, RequestBody> requestBodyMap,
                                                @Part ArrayList<MultipartBody.Part> list_image);

    @GET("/api/get-list-distributor")
    Call<Response<ArrayList<Distributor>>> getListDistributor();

//    @GET("/api/get-page-fruit")
//    Call<Response<Page<ArrayList<Fruit>>>> getPageFruit(@Header("Authorization")String token,
//                                                        @Query("page")int page);

    @GET("/api/get-page-fruit")
    Call<Response<Page<ArrayList<Fruit>>>> getPageFruit(@QueryMap Map<String,String> stringMap);

    @GET("/api/get-fruit-by-id/{id}") // get chi tiet fruit
    Call<Response<Fruit>> getFruitById(@Path("id")String id);

    @DELETE("/api/destroy-fruit-by-id/{id}")
    Call<Response<Fruit>> removeFruitById(@Path("id")String id);

    @Multipart
    @PUT("/api/update-fruit-by-id/{id}")
    Call<Response<Fruit>> updateFruitById(@Path("id")String id,
                                          @PartMap Map<String, RequestBody> requestBodyMap,
                                          @Part ArrayList<MultipartBody.Part> list_image);


    @PUT("/api/update-fruit-by-id/{id}")
    Call<Response<Fruit>> updateFruitByIdNotImg(@Path("id")String id,@Body Fruit fruit);

}
