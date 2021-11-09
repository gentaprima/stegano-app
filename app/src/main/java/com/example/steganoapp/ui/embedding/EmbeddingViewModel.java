package com.example.steganoapp.ui.embedding;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.steganoapp.model.GlobalResponse;
import com.example.steganoapp.model.login.ResponseLogin;
import com.example.steganoapp.network.repository.embed.EmbedRepository;
import com.example.steganoapp.obj.EmbedObject;


public class EmbeddingViewModel extends ViewModel {
    private EmbedRepository embedRepository;
    private LiveData<GlobalResponse> embeddingLiveData;

    public EmbeddingViewModel(){
        embedRepository = new EmbedRepository();
    }

    public void setEmbedding(EmbedObject embedObject, String type){
        embeddingLiveData =embedRepository.embedding(embedObject,type);

    }

    public LiveData<GlobalResponse> getEmbeddingLiveData(){
        return embeddingLiveData;
    }
}
