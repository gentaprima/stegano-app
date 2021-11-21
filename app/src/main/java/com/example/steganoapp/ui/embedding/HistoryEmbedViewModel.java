package com.example.steganoapp.ui.embedding;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.steganoapp.model.history.HistoryResponse;
import com.example.steganoapp.network.repository.embed.HistoryEmbedRepository;
import com.example.steganoapp.network.repository.extrac.HistoryExtracRepository;

public class HistoryEmbedViewModel extends ViewModel {
    private HistoryEmbedRepository historyEmbedRepository;

    public HistoryEmbedViewModel(){
        historyEmbedRepository = new HistoryEmbedRepository();
    }

    public LiveData<HistoryResponse> getHistoryEmbed(String type, String isType, String usersId){
        return historyEmbedRepository.getHistoryEmbed(type,isType,usersId);
    }
}
