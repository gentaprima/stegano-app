package com.example.steganoapp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.steganoapp.R;


public class EmbeddingFrag extends Fragment implements View.OnClickListener {
    EditText fileName,secretMessage,password,confirmPassword;
    Button btnBrowse,btnHide,btnCancel;
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
//        btnCancel.setOnClickListener(this);
        btnHide.setOnClickListener(this);
        btnBrowse.setOnClickListener(this);
        return view;
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
            case R.id.hide_button:
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
                startActivity(intent);
                break;
//            case R.id.cancel_button:
//                break;
//            case R.id.browse_file :
//                break;
            default:
        }
    }
}