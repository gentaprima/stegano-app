package com.example.steganoapp.ui.users;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.steganoapp.helper.Helper;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.steganoapp.R;
import com.example.steganoapp.model.MessageOnly;
import com.example.steganoapp.session.SystemDataLocal;
import com.example.steganoapp.utils.DialogClass;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout formProfile,formPassword;
    TextView changePassword,changeProfile;
    EditText oldPassword,newPassword,confirmPassword;
    Button btnUpdatePassword,btnProfileBrowse;
    private SystemDataLocal systemDataLocal;
    private ChangePasswordViewModel changePasswordViewModel;
    private android.app.AlertDialog alertDialog;
    ActivityResultLauncher<String> activityResultLauncher;
    String base64;
    TextView tvName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        formProfile =  findViewById(R.id.form_profile);
        formPassword = findViewById(R.id.form_password);
        changePassword= findViewById(R.id.change_password);
        changeProfile= findViewById(R.id.change_profile);
        oldPassword= findViewById(R.id.old_password);
        newPassword= findViewById(R.id.new_password);
        tvName = findViewById(R.id.tv_name);
        confirmPassword= findViewById(R.id.confirm_password);
        confirmPassword= findViewById(R.id.confirm_password);
        btnUpdatePassword = findViewById(R.id.btn_update_password);
        btnProfileBrowse = findViewById(R.id.browse_file);
        systemDataLocal = new SystemDataLocal(this);
        changePasswordViewModel = ViewModelProviders.of(this).get(ChangePasswordViewModel.class);
        btnProfileBrowse.setOnClickListener(this);
        btnUpdatePassword.setOnClickListener(this);
        changePassword.setOnClickListener(this);
        changeProfile.setOnClickListener(this);
        tvName.setText(systemDataLocal.getLoginData().getName());
        View v = getLayoutInflater().inflate(R.layout.loading_alert,null,false);
        alertDialog = DialogClass.dialog(this,v).create();


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), result -> {
            try {
                String[] resultUri = Helper.getRealPathFromURImage(context,result);
                File file = new File(resultUri[1]);
                base64 = Helper.encode(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return super.onCreateView(name, context, attrs);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.change_password:
                formProfile.setVisibility(View.GONE);
                formPassword.setVisibility(View.VISIBLE);
                break;

            case R.id.change_profile:
                formProfile.setVisibility(View.VISIBLE);
                formPassword.setVisibility(View.GONE);
                break;

            case R.id.btn_update_password:
                String old = oldPassword.getText().toString().trim();
                String newPass = newPassword.getText().toString().trim();
                String confirm = confirmPassword.getText().toString().trim();
                String personalNumber = systemDataLocal.getLoginData().getPersonal_number();
                String type = "change_password";
//                View vAlert = getLayoutInflater().inflate(R.layout.loading_alert,null,false);
//                alertDialog = DialogClass.dialog(this,vAlert).create();
                changePasswordViewModel.changePassword(personalNumber,old,newPass,confirm,type).observe(this, new Observer<MessageOnly>() {
                    @Override
                    public void onChanged(MessageOnly messageOnly) {
                        Toast.makeText(getApplicationContext(),messageOnly.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
                break;
            case R.id.browse_file:
                activityResultLauncher.launch("image/*");
                break;
            default:
        }
    }
}