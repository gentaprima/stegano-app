package com.example.steganoapp.ui.component;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.steganoapp.ui.fragment.EmbeddingFrag;
import com.example.steganoapp.ui.fragment.ExtractionFrag;
import com.example.steganoapp.ui.fragment.HistoryExtractionFrag;
import com.example.steganoapp.ui.fragment.HistoryFrag;

public class TabViewPageExtraction extends FragmentPagerAdapter {
    private Context context;
    public  final static String title[] = {"Extraction","History"};

    public TabViewPageExtraction(@NonNull FragmentManager fm,Context context){
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
                fragment = Fragment.instantiate(context, ExtractionFrag.class.getName());
                break;
            case 1:
                fragment = Fragment.instantiate(context, HistoryExtractionFrag.class.getName());
                break;
            default:
                fragment = Fragment.instantiate(context, ExtractionFrag.class.getName());
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
