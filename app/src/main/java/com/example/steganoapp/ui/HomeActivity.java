package com.example.steganoapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.steganoapp.R;
import com.example.steganoapp.session.SystemDataLocal;
import com.example.steganoapp.ui.embedding.EmbeddingActivity;
import com.example.steganoapp.ui.extraction.ExtractionActivity;
import com.example.steganoapp.ui.users.LoginActivity;
import com.example.steganoapp.ui.users.ProfileActivity;
import com.example.steganoapp.utils.SwitchActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvName;
    CardView cardEmbed,cardExtraction,cardLogout;
    CircleImageView ivProfile;
    SystemDataLocal systemDataLocal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
  SystemDataLocal systemDataLocal = new SystemDataLocal(this);
        tvName = findViewById(R.id.tv_name);
        ivProfile = findViewById(R.id.iv_profile);
        ivProfile.setVisibility(View.GONE);
        cardEmbed = findViewById(R.id.btn_embed);
        cardEmbed.setOnClickListener(this);
        cardExtraction = findViewById(R.id.cardExtraction);
        cardLogout = findViewById(R.id.cardLogout);
        cardExtraction.setOnClickListener(this);
        cardLogout.setOnClickListener(this);
        tvName.setText(systemDataLocal.getLoginData().getName());
        ivProfile.setOnClickListener(this);
//        systemDataLocal = new SystemDataLocal(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_embed:
                SwitchActivity.mainSwitch(this, EmbeddingActivity.class);
                break;

            case R.id.cardExtraction:
                SwitchActivity.mainSwitch(this, ExtractionActivity.class);
                break;

            case R.id.iv_profile:
                SwitchActivity.mainSwitch(this, ProfileActivity.class);
                break;

            case R.id.cardLogout:
                systemDataLocal.destroySessionLogin();
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                break;
            default:
                break;
        }
    }
}