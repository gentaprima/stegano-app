package com.example.steganoapp.ui.users;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.steganoapp.model.MessageOnly;
import com.example.steganoapp.network.repository.users.ChangePasswordRepository;

public class ChangePasswordViewModel extends ViewModel {
    private ChangePasswordRepository changePasswordRepository;

    public ChangePasswordViewModel() {
        changePasswordRepository = new ChangePasswordRepository();
    }

    public LiveData<MessageOnly> changePassword(String personalNumber,String oldPassword,String newPassword,String confirmPassword,String type){
        return changePasswordRepository.changePassword(personalNumber,oldPassword,newPassword,confirmPassword,type);
    }
}
