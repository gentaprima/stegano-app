package com.example.steganoapp.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.steganoapp.R;
import com.example.steganoapp.adapter.HistoryAdapter;
import com.example.steganoapp.model.history.HistoryResponse;
import com.example.steganoapp.session.SystemDataLocal;
import com.example.steganoapp.ui.extraction.HistoryExtracViewModel;

public class HistoryExtractionFrag extends Fragment  implements  View.OnClickListener{

    SystemDataLocal systemDataLocal;
    private HistoryExtracViewModel historyExtracViewModel;
//    private HistoryAdapter historyAdapter;
    RecyclerView rvData;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_history_extraction, container, false);
        assert container != null;
        View view =  inflater.inflate(R.layout.fragment_history_extraction, container, false);
        historyExtracViewModel = ViewModelProviders.of(this).get(HistoryExtracViewModel.class);
        return  view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        systemDataLocal = new SystemDataLocal(getContext());
        rvData = view.findViewById(R.id.rv_data);

        loadDataHistory();
    }

    @SuppressLint("FragmentLiveDataObserve")
    public void loadDataHistory(){
        String usersId = systemDataLocal.getLoginData().getId();
        historyExtracViewModel.getHistoryExtrac("get","decode",usersId).observe(this, historyResponse -> {
            if(historyResponse.getStatus()){
                HistoryAdapter historyAdapter = new HistoryAdapter(getContext(), historyResponse.getData(), v -> System.out.println("KOCAK SIH"));
                RecyclerView.LayoutManager lm = new LinearLayoutManager(getContext());
                rvData.setAdapter(historyAdapter);
                rvData.setLayoutManager(lm);
            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}