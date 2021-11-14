package com.example.steganoapp.network.api;

import com.example.steganoapp.model.GlobalResponse;
import com.example.steganoapp.model.MessageOnly;
import com.example.steganoapp.model.login.ResponseLogin;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("login.php")
    Call<ResponseLogin> loginUser(@Field("personal_number") String personalNumber,
                                  @Field("password") String password);
    @FormUrlEncoded
    @POST("stegano.php?")
    Call<GlobalResponse> embedding(@Field("mp3")String mp3,
                                   @Field("secret_message") String secretMessage,
                                   @Field("password")String password,
                                   @Field("users_id") String usersID,
                                   @Query("type") String type);


    @Multipart
    @POST("stegano.php?")
    Call<MessageOnly> extraction(@Part MultipartBody.Part mp3,
                                 @Part("password") RequestBody password,
                                 @Query("type") String type);
}
