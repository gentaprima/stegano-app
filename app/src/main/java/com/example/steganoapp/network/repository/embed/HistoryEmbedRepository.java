package com.example.steganoapp.network.repository.embed;

import androidx.lifecycle.MutableLiveData;

import com.example.steganoapp.model.history.HistoryResponse;
import com.example.steganoapp.network.api.ApiClient;
import com.example.steganoapp.network.api.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryEmbedRepository {
    private final ApiInterface apiInterface;

    public HistoryEmbedRepository() {
        this.apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    public MutableLiveData<HistoryResponse> getHistoryEmbed(String type, String isType, String usersId){
        final MutableLiveData<HistoryResponse> responseMutableLiveData = new MutableLiveData<>();
        Call<HistoryResponse> requestOrder = apiInterface.getHistoryExtrac(type,isType,usersId);
        requestOrder.enqueue(new Callback<HistoryResponse>() {
            @Override
            public void onResponse(Call<HistoryResponse> call, Response<HistoryResponse> response) {
                if(response.body() != null){
                    responseMutableLiveData.postValue(response.body());
                }else{
                    responseMutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<HistoryResponse> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
        return responseMutableLiveData;
    }
}
