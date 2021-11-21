package com.example.steganoapp.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.steganoapp.R;
import com.example.steganoapp.adapter.HistoryAdapter;
import com.example.steganoapp.model.history.HistoryResponse;
import com.example.steganoapp.network.repository.embed.HistoryEmbedRepository;
import com.example.steganoapp.session.SystemDataLocal;
import com.example.steganoapp.ui.embedding.HistoryEmbedViewModel;

public class HistoryFrag extends Fragment {

    private SystemDataLocal systemDataLocal;
    private HistoryEmbedViewModel historyEmbedViewModel;
    RecyclerView rv_data;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_history_menu, container, false);
        assert container != null;
        View view =  inflater.inflate(R.layout.fragment_history_menu, container, false);
        historyEmbedViewModel = ViewModelProviders.of(this).get(HistoryEmbedViewModel.class);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        systemDataLocal = new SystemDataLocal(getContext());
        loadData();
        rv_data = view.findViewById(R.id.rv_data);
    }

    @SuppressLint("FragmentLiveDataObserve")
    private void loadData() {
        String usersId = systemDataLocal.getLoginData().getId();
        historyEmbedViewModel.getHistoryEmbed("get","encode",usersId).observe(this, new Observer<HistoryResponse>() {
            @Override
            public void onChanged(HistoryResponse historyResponse) {
                HistoryAdapter historyAdapter = new HistoryAdapter(getContext(),historyResponse.getData());
                RecyclerView.LayoutManager lm = new LinearLayoutManager(getContext());
                rv_data.setAdapter(historyAdapter);
                rv_data.setLayoutManager(lm);

            }
        });
    }
}