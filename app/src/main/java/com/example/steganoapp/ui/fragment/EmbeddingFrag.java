package com.example.steganoapp.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

import static com.example.steganoapp.helper.Helper.ApiURL;
import static com.example.steganoapp.helper.Helper.encode;
import static com.example.steganoapp.helper.Helper.getRealPathFromURI;


public class EmbeddingFrag extends Fragment implements View.OnClickListener , Observer<GlobalResponse>, IEmbedding {
    EditText fileName,secretMessage,password,confirmPassword;
    Button btnBrowse,btnHide,btnCancel;
    ActivityResultLauncher<Intent> resultIntent;
     android.app.AlertDialog alertDialog;
    EmbeddingViewModel embeddingViewModel;
    String base64 = "";
    SystemDataLocal systemDataLocal;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        assert container != null;
        View view =  inflater.inflate(R.layout.fragment_embedding_menu, container, false);
        fileName = view.findViewById(R.id.file_name);
        secretMessage = view.findViewById(R.id.secret_message);
        password = view.findViewById(R.id.edt_password);
        confirmPassword = view.findViewById(R.id.edt_confirm_password);
        btnBrowse = view.findViewById(R.id.browse_file);
        btnHide  = view.findViewById(R.id.hide_button);
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
        resultIntent = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    Uri uri = result.getData() !=null ? result.getData().getData() :null;
                    assert uri != null;
                    File file = new File(getRealPathFromURI(this.requireContext(),uri));
                    fileName.setText(file.getName());
                    try {
                        base64 = encode(file);
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
                Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
                resultIntent.launch(intent);
                break;
            case R.id.hide_button:
                View v = getLayoutInflater().inflate(R.layout.loading_alert,null,false);
                alertDialog = DialogClass.dialog(getContext(),v).create();
                alertDialog.show();
                if(!password.getText().toString().equals(confirmPassword.getText().toString())){
                    Toast.makeText(getContext(), "Password dan konfirmasi password tidak sesuai!" ,Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                    break;
                }else{
                    EmbedObject embedObject = new EmbedObject(base64,secretMessage.getText().toString(),password.getText().toString(),systemDataLocal.getLoginData().getId());
                    if(embedObject.isValid()){
                        onEmbeddingText(embedObject,"hide");
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
        String message =  globalResponse.getMessage();
        alertDialog.dismiss();
        System.out.println(globalResponse.getFileName());
        Toast.makeText(getContext(), message ,Toast.LENGTH_SHORT).show();
        Helper.downloadFile(requireContext(),ApiURL+"steganofile/"+globalResponse.getFileName(),globalResponse.getFileName());

    }

    @Override
    public void onEmbeddingText(EmbedObject embedObject,String type) {
        embeddingViewModel.setEmbedding(embedObject,type);
        embeddingViewModel.getEmbeddingLiveData().observe(this.getViewLifecycleOwner(),this);
    }


}