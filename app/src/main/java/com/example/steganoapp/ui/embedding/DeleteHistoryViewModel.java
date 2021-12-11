package com.example.steganoapp.ui.embedding;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.steganoapp.model.MessageOnly;
import com.example.steganoapp.network.repository.DeleteHistoryRepository;

public class DeleteHistoryViewModel extends ViewModel {
    private DeleteHistoryRepository deleteHistoryRepository;

    public DeleteHistoryViewModel(){
        deleteHistoryRepository = new DeleteHistoryRepository();
    }

    public LiveData<MessageOnly> deleteHistory(String type,String id){
        return deleteHistoryRepository.deleteHistory(type,id);
    }
}
