package com.example.steganoapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.steganoapp.ui.component.TabViewPager;
import  com.google.android.material.tabs.TabLayout;
import android.os.Bundle;

import com.example.steganoapp.R;

public class Embedding extends AppCompatActivity {

    ViewPager viewPager;
    TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_embedding);
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);

        tabLayout.addTab(tabLayout.newTab().setText("Embedding"));
        tabLayout.addTab(tabLayout.newTab().setText("History"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final TabViewPager tabViewPager = new TabViewPager(getSupportFragmentManager(),this);
        viewPager.setAdapter(tabViewPager);
        tabLayout.setupWithViewPager(viewPager);

    }
}