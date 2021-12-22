package com.example.steganoapp.ui.extraction;

import static com.example.steganoapp.helper.Helper.getRealPathFromURImage;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.res.AssetFileDescriptor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.steganoapp.R;
import com.example.steganoapp.model.MessageOnly;
import com.example.steganoapp.session.SystemDataLocal;
import com.example.steganoapp.ui.component.TabViewPageExtraction;
import com.example.steganoapp.ui.component.TabViewPager;
import com.example.steganoapp.utils.DialogClass;
import com.google.android.material.tabs.TabLayout;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ExtractionActivity extends AppCompatActivity implements View.OnClickListener {

    ViewPager viewPager;
    TabLayout tabLayout;
    TextView toolbarTitle;
    Button btnBrowse,btnRead;
    private ExtractionViewModel extractionViewModel;
    SystemDataLocal systemDataLocal;
    ActivityResultLauncher<String> resultIntent;
    android.app.AlertDialog alertDialog;
    EditText fileName,edtPassword,secretMessage;
    File file;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extraction);

        toolbarTitle = findViewById(R.id.toolbar_title);
        toolbarTitle.setText("Extraction");
        btnBrowse = findViewById(R.id.browse_file);
        btnRead = findViewById(R.id.btnRead);
        btnBrowse.setOnClickListener(this);
        btnRead.setOnClickListener(this);
        fileName = findViewById(R.id.file_name);
        edtPassword = findViewById(R.id.edt_password);
        secretMessage = findViewById(R.id.secret_message);
        extractionViewModel = ViewModelProviders.of(this).get(ExtractionViewModel.class);
        systemDataLocal = new SystemDataLocal(getApplicationContext());
        resultIntent = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri o) {
                String[] data = getRealPathFromURImage(getApplicationContext(),o);
                String name = data[0];
                fileName.setText(name);
                ContentResolver contentResolver = getApplicationContext().getContentResolver();
                try {
                    AssetFileDescriptor descriptor = contentResolver.openAssetFileDescriptor(o,"r");
                    assert descriptor != null;
                    InputStream is = descriptor.createInputStream();
                    File tempFile = File.createTempFile("prefix", "suffix",getApplicationContext().getCacheDir());
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
//        viewPager = findViewById(R.id.viewPager);
//        tabLayout.addTab(tabLayout.newTab().setText("Extraction"));
//        tabLayout.addTab(tabLayout.newTab().setText("History"));
//        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
//
//        final TabViewPageExtraction tabViewPager = new TabViewPageExtraction(getSupportFragmentManager(),this);
//        viewPager.setAdapter(tabViewPager);
//        tabLayout.setupWithViewPager(viewPager);
//        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                toolbarTitle.setText(TabViewPager.title[tab.getPosition()]);
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.browse_file:
                resultIntent.launch("audio/*");
                break;
            case R.id.btnRead:
                View view = getLayoutInflater().inflate(R.layout.loading_alert,null,false);
                alertDialog = DialogClass.dialog(getApplicationContext(),view).create();
                alertDialog.show();
                if(file == null){
                    Toast.makeText(getApplicationContext(),"Pilih Media terlebih dahulu ...", Toast.LENGTH_LONG).show();
                    alertDialog.dismiss();
                }else{
                    RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-file"), file);
                    final MultipartBody.Part mp3 = MultipartBody.Part.createFormData("mp3",file.getName(),requestBody);
                    String password = edtPassword.getText().toString().trim();
                    if(password.equals("")){
                        Toast.makeText(getApplicationContext(),"Mohon isi field Password",Toast.LENGTH_LONG).show();
                        alertDialog.dismiss();
                    }else{
                        extractionViewModel.extractData(mp3,RequestBody.create(MediaType.parse("text/plain"),password), "show").observe(this, new Observer<MessageOnly>() {
                            @Override
                            public void onChanged(MessageOnly messageOnly) {
                                if(messageOnly.getStatus()){
                                    Toast.makeText(getApplicationContext(),"Berhasil mengambil secret message",Toast.LENGTH_LONG).show();
                                    secretMessage.setText(messageOnly.getMessage());
                                    alertDialog.dismiss();
                                }else{
                                    Toast.makeText(getApplicationContext(),messageOnly.getMessage(),Toast.LENGTH_LONG).show();
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