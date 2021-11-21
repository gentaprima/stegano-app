package com.example.steganoapp.network.repository.users;

import androidx.lifecycle.MutableLiveData;

import com.example.steganoapp.model.MessageOnly;
import com.example.steganoapp.network.api.ApiClient;
import com.example.steganoapp.network.api.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordRepository {
    private final ApiInterface apiInterface;

    public ChangePasswordRepository() {
        this.apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    public MutableLiveData<MessageOnly> changePassword(String personalNumber,String oldPassword,String newPassword,String confirmPassword,String type){
        final MutableLiveData<MessageOnly> messageOnlyMutableLiveData = new MutableLiveData<>();
        Call<MessageOnly> requestOrder = apiInterface.changePassword(personalNumber,oldPassword,newPassword,confirmPassword,type);
        requestOrder.enqueue(new Callback<MessageOnly>() {
            @Override
            public void onResponse(Call<MessageOnly> call, Response<MessageOnly> response) {
                System.out.println(response.body());
                if(response.body() != null){
                    messageOnlyMutableLiveData.postValue(response.body());
                }else{
                    messageOnlyMutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<MessageOnly> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
        return messageOnlyMutableLiveData;
    }
}
