package com.example.steganoapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.steganoapp.R;
import com.example.steganoapp.session.SystemDataLocal;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity {

    private SystemDataLocal systemDataLocal;
    TextView tvName;
    CircleImageView ivProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        systemDataLocal = new SystemDataLocal(this);
        tvName = findViewById(R.id.tv_name);
        ivProfile = findViewById(R.id.iv_profile);

        tvName.setText(systemDataLocal.getLoginData().getName());
    }
}