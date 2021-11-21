package com.example.steganoapp.ui.fragment;

import static com.example.steganoapp.helper.Helper.encode;
import static com.example.steganoapp.helper.Helper.getRealPathFromURI;
import static com.example.steganoapp.helper.Helper.getRealPathFromURImage;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
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
import android.util.Base64;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;
import org.apache.commons.io.IOUtils;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import org.apache.commons.*;

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
    ActivityResultLauncher<String> resultIntent;
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
        resultIntent = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri o) {
                String[] data = getRealPathFromURImage(requireContext(),o);
                String name = data[0];
                fileName.setText(name);
                ContentResolver contentResolver = requireContext().getContentResolver();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                try {
                    AssetFileDescriptor descriptor = contentResolver.openAssetFileDescriptor(o,"r");
                    assert descriptor != null;
                    InputStream is = descriptor.createInputStream();
                    File tempFile = File.createTempFile("prefix", "suffix",requireContext().getCacheDir());
                    tempFile.deleteOnExit();
                    FileOutputStream out = new FileOutputStream(tempFile);
                    IOUtils.copy(is, out);
                    file = tempFile;
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.browse_file:
                resultIntent.launch("audio/*");
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
                                    Toast.makeText(getContext(),"Berhasil mengambil secret message",Toast.LENGTH_LONG).show();
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