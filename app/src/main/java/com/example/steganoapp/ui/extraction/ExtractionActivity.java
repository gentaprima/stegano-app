package com.example.steganoapp.ui.extraction;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.TextView;

import com.example.steganoapp.R;
import com.example.steganoapp.ui.component.TabViewPageExtraction;
import com.example.steganoapp.ui.component.TabViewPager;
import com.google.android.material.tabs.TabLayout;

public class ExtractionActivity extends AppCompatActivity {

    ViewPager viewPager;
    TabLayout tabLayout;
    TextView toolbarTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extraction);
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        toolbarTitle = findViewById(R.id.toolbar_title);
        tabLayout.addTab(tabLayout.newTab().setText("Extraction"));
        tabLayout.addTab(tabLayout.newTab().setText("History"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final TabViewPageExtraction tabViewPager = new TabViewPageExtraction(getSupportFragmentManager(),this);
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