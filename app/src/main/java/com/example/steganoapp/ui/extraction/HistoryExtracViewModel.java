package com.example.steganoapp.ui.extraction;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.steganoapp.model.history.HistoryResponse;
import com.example.steganoapp.network.repository.extrac.HistoryExtracRepository;

public class HistoryExtracViewModel extends ViewModel {
    private HistoryExtracRepository historyExtracRepository;

    public HistoryExtracViewModel(){
        historyExtracRepository = new HistoryExtracRepository();
    }

    public LiveData<HistoryResponse> getHistoryExtrac(String type, String isType, String usersId){
        return historyExtracRepository.getHistoryExtrac(type,isType,usersId);
    }
}
