package com.example.steganoapp.ui.fragment;

import static com.example.steganoapp.helper.Helper.encode;
import static com.example.steganoapp.helper.Helper.getRealPathFromURI;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.steganoapp.R;
import com.example.steganoapp.model.GlobalResponse;
import com.example.steganoapp.model.MessageOnly;
import com.example.steganoapp.session.SystemDataLocal;
import com.example.steganoapp.ui.extraction.ExtractionActivity;
import com.example.steganoapp.ui.extraction.ExtractionViewModel;
import com.example.steganoapp.utils.DialogClass;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

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
    private ExtractionViewModel extractionViewModel;
    SystemDataLocal systemDataLocal;
    ActivityResultLauncher<Intent> resultIntent;
    android.app.AlertDialog alertDialog;
    EditText fileName,edtPassword,secretMessage;
    File file;
    private final int REQUEST_PICK_PHOTO = 2;
    private final int REQUES_WRITE_PERMISION = 786;
    private String mediaPath;
    private String postPath;
    ExtractionActivity activity = (ExtractionActivity) getContext();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        assert container != null;
        View view =  inflater.inflate(R.layout.fragment_extraction, container, false);
        btnBrowse = view.findViewById(R.id.browse_file);
        btnRead = view.findViewById(R.id.btnRead);
        btnBrowse.setOnClickListener(this);
        btnRead.setOnClickListener(this);
        fileName = view.findViewById(R.id.file_name);
        edtPassword = view.findViewById(R.id.edt_password);
        secretMessage = view.findViewById(R.id.secret_message);
        extractionViewModel = ViewModelProviders.of(this).get(ExtractionViewModel.class);
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        systemDataLocal = new SystemDataLocal(getContext());
        resultIntent = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                Uri uri = result.getData() !=null ? result.getData().getData() :null;
                assert uri != null;
                file = new File(getRealPathFromURI(this.requireContext(),uri));
                fileName.setText(file.getName());
            });
    }

//    private void requestPermision() {
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
//            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUES_WRITE_PERMISION);
//        }else{
//            saveImageUpload();
//        }
//    }

//    private void saveImageUpload() {
//        if(mediaPath == null){
//            Toast.makeText(getContext(),"Pilih Gambar terlebih dahulu ...", Toast.LENGTH_LONG).show();
//        }else{
//            final File imageFile = new File(mediaPath);
//            String password = "google123";
//            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-file"),imageFile);
//            final MultipartBody.Part partImage = MultipartBody.Part.createFormData("mp3",imageFile.getName(),requestBody);
//            try {
//                extractionViewModel.extractData(partImage,RequestBody.create(MediaType.parse("text-plain"),password), "show").observe(this, new Observer<MessageOnly>() {
//                    @Override
//                    public void onChanged(MessageOnly messageOnly) {
//                        System.out.println(messageOnly.getMessage());
//                    }
//                });
//            }catch (Exception e){
//                System.out.println(e.getMessage());
//            }
//
//        }
//    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(resultCode == activity.RESULT_OK){
//            if(requestCode == REQUEST_PICK_PHOTO){
//                if(data != null){
//                    Uri selectedFile = data.getData();
//                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
//                    Cursor cursor = getContext().getContentResolver().query(selectedFile,filePathColumn,null,null,null);
//                    if(cursor != null){
//                        cursor.moveToFirst();
//                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                        mediaPath = cursor.getString(columnIndex);
//                        final File file = new File(mediaPath);
//                        fileName.setText(file.getName());
//                    }
//                }
//            }
//        }
//    }


//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
//            saveImageUpload();
//        }
//    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.browse_file:
                Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Audio.Media.INTERNAL_CONTENT_URI);
                resultIntent.launch(intent);
//                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(gallery,REQUEST_PICK_PHOTO);
                break;

            case R.id.btnRead:
                View view = getLayoutInflater().inflate(R.layout.loading_alert,null,false);
                alertDialog = DialogClass.dialog(getContext(),view).create();
                alertDialog.show();
                if(file == null){
                    Toast.makeText(getContext(),"Pilih Media terlebih dahulu ...", Toast.LENGTH_LONG).show();
                    alertDialog.dismiss();
                }else{
                    RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-file"), file);
                    final MultipartBody.Part mp3 = MultipartBody.Part.createFormData("mp3",file.getName(),requestBody);
                    String password = edtPassword.getText().toString().trim();
                    if(password.equals("")){
                        Toast.makeText(getContext(),"Mohon isi field Password",Toast.LENGTH_LONG).show();
                        alertDialog.dismiss();
                    }else{
                        extractionViewModel.extractData(mp3,RequestBody.create(MediaType.parse("text/plain"),password), "show").observe(this, new Observer<MessageOnly>() {
                            @Override
                            public void onChanged(MessageOnly messageOnly) {
                                if(messageOnly.getStatus()){
                                    Toast.makeText(getContext(),messageOnly.getMessage(),Toast.LENGTH_LONG).show();
                                    secretMessage.setText(messageOnly.getMessage());
                                    alertDialog.dismiss();
                                }else{
                                    Toast.makeText(getContext(),messageOnly.getMessage(),Toast.LENGTH_LONG).show();
                                    alertDialog.dismiss();
                                }
                            }
                        });
                    }

                }

                break;

            default:

        }
    }
}