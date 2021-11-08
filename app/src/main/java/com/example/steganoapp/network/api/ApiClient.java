package com.example.steganoapp.network.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit retrofit = null;

    public  static  Retrofit getClient(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder().baseUrl("http://192.168.100.14/steganografi-api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
