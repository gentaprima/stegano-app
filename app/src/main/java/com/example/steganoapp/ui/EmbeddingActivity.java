package com.example.steganoapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.example.steganoapp.ui.component.TabViewPager;
import  com.google.android.material.tabs.TabLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TextView;


import com.example.steganoapp.R;

public class EmbeddingActivity extends AppCompatActivity {

    ViewPager viewPager;
    TabLayout tabLayout;
    TextView toolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_embedding);
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        toolbarTitle = findViewById(R.id.toolbar_title);
        tabLayout.addTab(tabLayout.newTab().setText("Embedding"));
        tabLayout.addTab(tabLayout.newTab().setText("History"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final TabViewPager tabViewPager = new TabViewPager(getSupportFragmentManager(),this);
        viewPager.setAdapter(tabViewPager);
        tabLayout.setupWithViewPager(viewPager);
        toolbarTitle.setText(TabViewPager.title[0]);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                toolbarTitle.setText(TabViewPager.title[tab.getPosition()]);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
}