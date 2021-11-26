package com.example.steganoapp.ui.fragment;

import static com.example.steganoapp.helper.Helper.ApiURL;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
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
import android.widget.Button;
import android.widget.Toast;

import com.example.steganoapp.R;
import com.example.steganoapp.adapter.HistoryAdapter;
import com.example.steganoapp.helper.Helper;
import com.example.steganoapp.model.history.DataHistory;
import com.example.steganoapp.model.history.HistoryResponse;
import com.example.steganoapp.network.repository.embed.HistoryEmbedRepository;
import com.example.steganoapp.session.SystemDataLocal;
import com.example.steganoapp.ui.embedding.DeleteHistoryViewModel;
import com.example.steganoapp.ui.embedding.HistoryEmbedViewModel;

public class HistoryFrag extends Fragment {

    private SystemDataLocal systemDataLocal;
    private HistoryEmbedViewModel historyEmbedViewModel;
    private DeleteHistoryViewModel deleteHistoryViewModel;
    RecyclerView rv_data;
    AlertDialog.Builder builder;
    View myview;
    android.app.AlertDialog alertDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        assert container != null;
        View view =  inflater.inflate(R.layout.fragment_history_menu, container, false);
        historyEmbedViewModel = ViewModelProviders.of(this).get(HistoryEmbedViewModel.class);
        deleteHistoryViewModel = ViewModelProviders.of(this).get(DeleteHistoryViewModel.class);
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
                historyAdapter.setOnDeleteClickCallBack(new HistoryAdapter.OnItemClickCallBack() {
                    @Override
                    public void onItemClicked(DataHistory dataHistory) {
                        displayDialogDelete(dataHistory);
                    }
                });
                historyAdapter.setOnDownloadClickCallBack(new HistoryAdapter.OnItemClickCallBack() {
                    @Override
                    public void onItemClicked(DataHistory dataHistory) {
                        displaDialogDownload(dataHistory);
                    }
                });
            }
        });
    }
    private void displaDialogDownload(DataHistory dataHistory) {
        builder = new AlertDialog.Builder(getContext());
        myview = getLayoutInflater().inflate(R.layout.dialog_download, null, false);
        builder.setView(myview);
        Button btnDownload = myview.findViewById(R.id.btn_download);
        Button btnBack = myview.findViewById(R.id.btn_back);
        AlertDialog alertDialog = builder.show();
        btnDownload.setOnClickListener(v -> {
            Helper.downloadFile(requireContext(), ApiURL + "steganofile/" + dataHistory.getEmbeddingFile(), dataHistory.getEmbeddingFile());
            Toast.makeText(getContext(), "Media sedang didownload ...", Toast.LENGTH_LONG).show();
            alertDialog.dismiss();
        });

        btnBack.setOnClickListener(v -> alertDialog.dismiss());
    }

    private void displayDialogDelete(DataHistory dataHistory) {
        builder = new AlertDialog.Builder(getContext());
        myview = getLayoutInflater().inflate(R.layout.dialog_delete,null,false);
        builder.setView(myview);
        Button btnDelete = myview.findViewById(R.id.btn_delete);
        Button btnCancel = myview.findViewById(R.id.btn_back);
        AlertDialog alertDialog = builder.show();
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();;
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteHistoryViewModel.deleteHistory("del",dataHistory.getId());
            }
        });
    }
}