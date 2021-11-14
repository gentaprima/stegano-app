package com.example.steganoapp.network.repository.extrac;

import androidx.lifecycle.MutableLiveData;

import com.example.steganoapp.model.GlobalResponse;
import com.example.steganoapp.model.MessageOnly;
import com.example.steganoapp.network.api.ApiClient;
import com.example.steganoapp.network.api.ApiInterface;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExtracRepository {
    private final ApiInterface apiInterface;

    public ExtracRepository() {
        this.apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    public MutableLiveData<MessageOnly> extraction(MultipartBody.Part mp3, RequestBody password, String type){
        final MutableLiveData<MessageOnly> responseMutableLiveData = new MutableLiveData<>();
        Call<MessageOnly> requestOrder = apiInterface.extraction(mp3,password,type);
        requestOrder.enqueue(new Callback<MessageOnly>() {
            @Override
            public void onResponse(Call<MessageOnly> call, Response<MessageOnly> response) {
                System.out.println(response.body());
                if(response.body() != null){
                    responseMutableLiveData.postValue(response.body());
                }else{
                    responseMutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<MessageOnly> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
        return responseMutableLiveData;
    }
}
