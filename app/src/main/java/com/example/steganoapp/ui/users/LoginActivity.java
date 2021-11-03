package com.example.steganoapp.ui.users;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.steganoapp.R;
import com.example.steganoapp.impl.LoginImpl;
import com.example.steganoapp.model.login.ResponseLogin;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, LoginImpl, Observer<ResponseLogin> {

    EditText edtNumber,edtPassword;
    private LoginViewModel loginViewModel;
    ImageButton btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtNumber = findViewById(R.id.edt_number);
        edtPassword = findViewById(R.id.edt_password);
        btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_login){
//            login();
            System.out.println("cacad");
        }

    }

    @Override
    public void login() {
        String number = edtNumber.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        loginViewModel.setLogin(number,password);
        loginViewModel.getUserEntityLiveData().observe(this,this);
    }

    @SuppressLint("ShowToast")
    @Override
    public void onChanged(ResponseLogin responseLogin) {
        if(responseLogin != null){
            if(responseLogin.getStatus()){
                System.out.println(responseLogin.getMessage());
            }
        }
    }
}