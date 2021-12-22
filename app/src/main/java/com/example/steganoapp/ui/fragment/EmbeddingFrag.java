package com.example.steganoapp.ui.fragment;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.res.AssetFileDescriptor;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.steganoapp.R;
import com.example.steganoapp.helper.Helper;
import com.example.steganoapp.impl.IEmbedding;
import com.example.steganoapp.model.GlobalResponse;
import com.example.steganoapp.obj.EmbedObject;
import com.example.steganoapp.session.SystemDataLocal;
import com.example.steganoapp.ui.embedding.EmbeddingViewModel;
import com.example.steganoapp.utils.DialogClass;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import static com.example.steganoapp.helper.Helper.ApiURL;
import static com.example.steganoapp.helper.Helper.getRealPathFromURI;


public class EmbeddingFrag extends Fragment implements View.OnClickListener, Observer<GlobalResponse>, IEmbedding {
    EditText fileName, secretMessage, password, confirmPassword;
    Button btnBrowse, btnHide, btnCancel;
    ActivityResultLauncher<String> resultIntent;
    android.app.AlertDialog alertDialog;
    EmbeddingViewModel embeddingViewModel;
    String base64 = "";
    SystemDataLocal systemDataLocal;
    AlertDialog.Builder builder;
    View myview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        assert container != null;
        View view = inflater.inflate(R.layout.fragment_embedding_menu, container, false);
        fileName = view.findViewById(R.id.file_name);
        secretMessage = view.findViewById(R.id.secret_message);
        password = view.findViewById(R.id.edt_password);
        confirmPassword = view.findViewById(R.id.edt_confirm_password);
        btnBrowse = view.findViewById(R.id.browse_file);
        btnHide = view.findViewById(R.id.hide_button);
        btnCancel = view.findViewById(R.id.cancel_button);
        embeddingViewModel = ViewModelProviders.of(this).get(EmbeddingViewModel.class);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        systemDataLocal = new SystemDataLocal(getContext());
        btnCancel.setOnClickListener(this);
        btnHide.setOnClickListener(this);
        btnBrowse.setOnClickListener(this);
        resultIntent = registerForActivityResult(new ActivityResultContracts.GetContent(), result -> {
            if(result==null ){
               return;
            }
            String name = getRealPathFromURI(requireContext(),result);
            fileName.setText(name);
            ContentResolver contentResolver = requireContext().getContentResolver();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                AssetFileDescriptor descriptor = contentResolver.openAssetFileDescriptor(result,"r");
                assert descriptor != null;
                InputStream is = descriptor.createInputStream();
                byte[] buf = new byte[1024];
                int n;
                while (-1 != (n = is.read(buf))) {
                    baos.write(buf, 0, n);
                }
                byte[] videoBytes = baos.toByteArray();
                base64 = Base64.encodeToString(videoBytes,0);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.browse_file:
                resultIntent.launch("audio/*");
                break;
            case R.id.hide_button:
                View v = getLayoutInflater().inflate(R.layout.loading_alert, null, false);
                alertDialog = DialogClass.dialog(getContext(), v).create();
                alertDialog.show();
                if (!password.getText().toString().equals(confirmPassword.getText().toString())) {
                    Toast.makeText(getContext(), "Password dan konfirmasi password tidak sesuai!", Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                    break;
                } else {
                    EmbedObject embedObject = new EmbedObject(base64, secretMessage.getText().toString(), password.getText().toString(), systemDataLocal.getLoginData().getId());
                    if (embedObject.isValid()) {
                        onEmbeddingText(embedObject, "hide");
                    }
                }
                break;
            case R.id.cancel_button:
                getActivity().finish();
                break;
//            case R.id.browse_file :
//                break;
            default:
        }
    }


    @Override
    public void onChanged(GlobalResponse globalResponse) {
        alertDialog.dismiss();
        String message = globalResponse.getMessage();
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        if(globalResponse.getStatus()){
            displayDialog(globalResponse);
        }

    }


    @Override
    public void onEmbeddingText(EmbedObject embedObject, String type) {
        embeddingViewModel.setEmbedding(embedObject, type);
        embeddingViewModel.getEmbeddingLiveData().observe(this.getViewLifecycleOwner(), this);
    }

    private void displayDialog(GlobalResponse globalResponse) {
        builder = new AlertDialog.Builder(getContext());
        myview = getLayoutInflater().inflate(R.layout.dialog_download, null, false);
        builder.setView(myview);
        Button btnDownload = myview.findViewById(R.id.btn_download);
        Button btnBack = myview.findViewById(R.id.btn_back);
        AlertDialog alertDialog = builder.show();
        btnDownload.setOnClickListener(v -> {
            Helper.downloadFile(requireContext(), ApiURL + "steganofile/" + globalResponse.getFileName(), globalResponse.getFileName());
            Toast.makeText(getContext(), "Media sedang didownload ...", Toast.LENGTH_LONG).show();
            alertDialog.dismiss();
        });

        btnBack.setOnClickListener(v -> alertDialog.dismiss());
    }
}