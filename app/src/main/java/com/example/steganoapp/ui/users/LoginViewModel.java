package com.example.steganoapp.ui.users;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.steganoapp.model.login.ResponseLogin;
import com.example.steganoapp.network.repository.users.LoginRepository;

public class LoginViewModel extends ViewModel {
    private LoginRepository loginRepository;
    private LiveData<ResponseLogin> userEntityLiveData;

    public LoginViewModel(){
        loginRepository = new LoginRepository();
    }

    public void setLogin(String number,String password){
        userEntityLiveData =loginRepository.getResponseLogin(number,password);
    }

    public LiveData<ResponseLogin> getUserEntityLiveData(){
        return userEntityLiveData;
    }
}
