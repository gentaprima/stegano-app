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
        final MutableLiveData<GlobalResponse> globalResponseMutableLiveData = new MutableLiveData<>();
        Call<GlobalResponse> requestOrder = apiInterface.embedding(embedObject.getMp3(), embedObject.getSecretMessage(), embedObject.getPassword(), type);
        requestOrder.enqueue(new Callback<GlobalResponse>() {
            @Override
            public void onResponse(@NonNull Call<GlobalResponse> call, @NonNull Response<GlobalResponse> response) {
                System.out.println(response.raw().request().url());
                if (response.body() != null) {
                    globalResponseMutableLiveData.postValue(response.body());
                } else {
                    globalResponseMutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<GlobalResponse> call, @NonNull Throwable t) {
                globalResponseMutableLiveData.postValue(null);
            }
        });
        return  globalResponseMutableLiveData;
    }

}
