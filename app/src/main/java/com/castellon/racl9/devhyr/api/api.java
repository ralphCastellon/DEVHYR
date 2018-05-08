package com.castellon.racl9.devhyr.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class api {

    private final static String Url = "http://devhyr-final.herokuapp.com/api";

    private static String getBase(){
        return Url + "/";
    }

    public static apiInterface instance(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(api.getBase())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(apiInterface.class);
    }

}
