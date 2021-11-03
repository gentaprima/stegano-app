package com.example.steganoapp.network.api;

import com.example.steganoapp.model.login.ResponseLogin;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("login.php")
    Call<ResponseLogin> loginUser(@Field("personal_number") String personalNumber,
                                  @Field("password") String password);

}
