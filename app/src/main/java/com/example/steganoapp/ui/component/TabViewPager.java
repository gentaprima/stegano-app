package com.example.steganoapp.ui.component;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import com.example.steganoapp.ui.menu.EmbeddingMenu;
import com.example.steganoapp.ui.menu.HistoryMenu;


public class TabViewPager extends FragmentPagerAdapter {

    private Context context;
    private String title[]= {"Embedding","History"};

    public TabViewPager(@NonNull FragmentManager fm,Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeViewAt(position);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment =null;
        switch (position) {
            case 0:
                fragment = Fragment.instantiate(context, EmbeddingMenu.class.getName());
                break;
            case 1:
                fragment = Fragment.instantiate(context, HistoryMenu.class.getName());
                break;
            default:
                fragment = Fragment.instantiate(context, EmbeddingMenu.class.getName());
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
