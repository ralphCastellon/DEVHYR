package com.castellon.racl9.devhyr.api;

import com.castellon.racl9.devhyr.models.accessToken;
import com.castellon.racl9.devhyr.models.categories;
import com.castellon.racl9.devhyr.models.pictures;
import com.castellon.racl9.devhyr.models.products;
import com.castellon.racl9.devhyr.models.user;
import com.castellon.racl9.devhyr.models.userPictures;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface apiInterface {
    //Method GET
    @GET("products")
    Call<List<products>> getProducts(@Header("Authorization") String authorization);

    @GET("pictures")
    Call<List<pictures>> getPictures(@Header("Authorization") String authorization);

    @GET("Users")
    Call<List<user>> getUserInfo(@Header("Authorization") String authorization);

    @GET("userPictures")
    Call<List<userPictures>> getImageProfile(@Header("Authorization") String authorization);

    @GET("categories")
    Call<List<categories>> getCategories(@Header("Authorization") String authorization);


    //Method POST
    @POST("products")
    Call<products> saveProducts(@Body products products, @Header("Authorization") String authorization);

    @POST("pictures")
    Call<pictures> savePicture(@Body pictures pictures,@Header("Authorization") String authorization);

    @POST("userPictures")
    Call<userPictures> saveImageProfile(@Body userPictures userPictures,@Header("Authorization") String authorization);

    @POST("Users")
    Call<user> saveUser(@Body user user);

    @POST("Users/login")
    Call<accessToken> loginUser(@Body user user);
}
