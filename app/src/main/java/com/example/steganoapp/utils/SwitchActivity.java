package com.example.steganoapp.utils;

import android.content.Context;
import android.content.Intent;

public class SwitchActivity {
    public static void mainSwitch(Context from, Class to){
        Intent intent = new Intent(from,to);
        from.startActivity(intent);
    }
}
