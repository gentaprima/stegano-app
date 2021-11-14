package com.example.steganoapp.ui.extraction;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.steganoapp.model.GlobalResponse;
import com.example.steganoapp.model.MessageOnly;
import com.example.steganoapp.network.repository.extrac.ExtracRepository;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ExtractionViewModel extends ViewModel {
    private ExtracRepository extracRepository;

    public ExtractionViewModel(){
        extracRepository = new ExtracRepository();
    }

    public LiveData<MessageOnly> extractData(MultipartBody.Part mp3, RequestBody password, String type){
        return extracRepository.extraction(mp3,password,type);
    }
}
