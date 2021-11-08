package com.example.steganoapp.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.steganoapp.R;

public class ExtractionFrag extends Fragment implements View.OnClickListener {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    Button btnBrowse,btnRead;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        assert container != null;
        View view =  inflater.inflate(R.layout.fragment_extraction, container, false);
        btnBrowse = view.findViewById(R.id.browse_file);
        btnRead = view.findViewById(R.id.btnRead);
        btnBrowse.setOnClickListener(this);
        return view;

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.browse_file:
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
                startActivity(intent);
                break;

            case R.id.btnRead:

                break;

            default:

        }
    }
}