package com.example.steganoapp.ui.users;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.steganoapp.R;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout formProfile,formPassword;
    TextView changePassword,changeProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        formProfile =  findViewById(R.id.form_profile);
        formPassword = findViewById(R.id.form_password);
        changePassword= findViewById(R.id.change_password);
        changeProfile= findViewById(R.id.change_profile);

        changePassword.setOnClickListener(this);
        changeProfile.setOnClickListener(this);

    }

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

            default:
        }
    }
}