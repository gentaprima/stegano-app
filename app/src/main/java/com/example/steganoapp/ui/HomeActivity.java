package com.example.steganoapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.steganoapp.R;
import com.example.steganoapp.session.SystemDataLocal;
import com.example.steganoapp.ui.users.ProfileActivity;
import com.example.steganoapp.utils.SwitchActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvName;
    CardView cardEmbed,cardExtraction;
    CircleImageView ivProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        SystemDataLocal systemDataLocal = new SystemDataLocal(this);
        tvName = findViewById(R.id.tv_name);
        ivProfile = findViewById(R.id.iv_profile);
        cardEmbed = findViewById(R.id.btn_embed);
        cardEmbed.setOnClickListener(this);
        cardExtraction = findViewById(R.id.cardExtraction);
        cardExtraction.setOnClickListener(this);
        tvName.setText(systemDataLocal.getLoginData().getName());
        ivProfile.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_embed:
                SwitchActivity.mainSwitch(this, EmbeddingActivity.class);
                break;

            case R.id.cardExtraction:
                SwitchActivity.mainSwitch(this,ExtractionActivity.class);
                break;

            case R.id.iv_profile:
                SwitchActivity.mainSwitch(this, ProfileActivity.class);
                break;
            default:
                break;
        }
    }
}