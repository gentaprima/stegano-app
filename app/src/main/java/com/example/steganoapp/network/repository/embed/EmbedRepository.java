package com.example.steganoapp.network.repository.embed;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.steganoapp.model.GlobalResponse;
import com.example.steganoapp.model.login.ResponseLogin;
import com.example.steganoapp.network.api.ApiClient;
import com.example.steganoapp.network.api.ApiInterface;
import com.example.steganoapp.obj.EmbedObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmbedRepository {
    private final ApiInterface apiInterface;

    public EmbedRepository(){
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    public MutableLiveData<GlobalResponse> embedding(EmbedObject embedObject,String type) {
        System.out.println("oke");
        final MutableLiveData<GlobalResponse> globalResponseMutableLiveData = new MutableLiveData<>();
        System.out.println(embedObject.getMp3());
        Call<GlobalResponse> requestOrder = apiInterface.embedding(embedObject.getMp3(), embedObject.getSecretMessage(), embedObject.getPassword(),embedObject.getUsersID(), type);
        requestOrder.enqueue(new Callback<GlobalResponse>() {
            @Override
            public void onResponse( Call<GlobalResponse> call, Response<GlobalResponse> response) {
                if (response.body() != null) {
                    globalResponseMutableLiveData.postValue(response.body());
                } else {
                    globalResponseMutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure( Call<GlobalResponse> call,  Throwable t) {
                globalResponseMutableLiveData.postValue(null);
            }
        });
        return  globalResponseMutableLiveData;
    }

}
