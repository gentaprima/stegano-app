package com.example.steganoapp.network.repository.users;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.steganoapp.model.login.ResponseLogin;
import com.example.steganoapp.network.api.ApiClient;
import com.example.steganoapp.network.api.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepository {
    private ApiInterface apiInterface;

    public LoginRepository(){
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    public MutableLiveData<ResponseLogin> getResponseLogin(String personalNumber,String password){
        final MutableLiveData<ResponseLogin> responseLoginMutableLiveData = new MutableLiveData<>();
        Call<ResponseLogin> requestOrder = apiInterface.loginUser(personalNumber,password);
        requestOrder.enqueue(new Callback<ResponseLogin>() {
            @Override
            public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                try {

                    System.out.println(response.body());
                }catch (Exception e){
                    Log.d("s",e.getMessage());
                }
//                if(response.body() != null){
//
//                    responseLoginMutableLiveData.postValue(response.body());
//                }else{
//                    responseLoginMutableLiveData.postValue(null);
//                }
            }

            @Override
            public void onFailure(Call<ResponseLogin> call, Throwable t) {
                System.out.println(t.getMessage());
                responseLoginMutableLiveData.postValue(null);
            }
        });
        return responseLoginMutableLiveData;
    }
}
