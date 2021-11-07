package com.example.steganoapp.ui.users;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.steganoapp.R;
import com.example.steganoapp.model.login.ResponseLogin;
import com.example.steganoapp.session.SystemDataLocal;
import com.example.steganoapp.ui.HomeActivity;
import com.example.steganoapp.utils.DialogClass;
import com.example.steganoapp.utils.SwitchActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, Observer<ResponseLogin> {

    EditText edtNumber,edtPassword;
    private LoginViewModel loginViewModel;
    private android.app.AlertDialog alertDialog;
    private SystemDataLocal systemDataLocal;
    ImageButton btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtNumber = findViewById(R.id.edt_number);
        edtPassword = findViewById(R.id.edt_password);
        btnLogin = findViewById(R.id.btn_login);
        systemDataLocal = new SystemDataLocal(this);
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               login();
            }
        });
        if(systemDataLocal.getCheckLogin()){
            startActivity(new Intent(LoginActivity.this,HomeActivity.class));
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        systemDataLocal = new SystemDataLocal(this);
        if(systemDataLocal.getCheckLogin()){
            SwitchActivity.mainSwitch(this, HomeActivity.class);
            finish();
        }
    }

    public void login(){
        String number = edtNumber.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        View v = getLayoutInflater().inflate(R.layout.loading_alert,null,false);
        alertDialog = DialogClass.dialog(this,v).create();
        alertDialog.show();
        loginViewModel.setLogin(number,password);
        loginViewModel.getUserEntityLiveData().observe(this,this);
    }

    @SuppressLint("ShowToast")
    @Override
    public void onChanged(ResponseLogin responseLogin) {
        if(responseLogin != null){
            if(responseLogin.getStatus()){
                alertDialog.dismiss();
                systemDataLocal = new SystemDataLocal(this,responseLogin.getData());
                systemDataLocal.setSessionLogin();
                SwitchActivity.mainSwitch(this, HomeActivity.class);
                finish();
                Toast.makeText(LoginActivity.this,responseLogin.getMessage(),Toast.LENGTH_SHORT).show();
            }else{
                alertDialog.dismiss();
                System.out.println(responseLogin.getMessage());
                Toast.makeText(LoginActivity.this,responseLogin.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onClick(View v) {

    }
}