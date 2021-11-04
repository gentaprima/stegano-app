package com.example.steganoapp.session;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.steganoapp.model.login.Users;
import com.example.steganoapp.model.login.UsersData;

public class SystemDataLocal {
    private SharedPreferences sharedPreferences;
    private Context context;
    private static final String KEY_USER = "User";
    private static final String KEY_ADDR = "address";
    private UsersData usersData;

    public SystemDataLocal(Context context, UsersData usersData) {
        this.context = context;
        this.usersData = usersData;
    }

    public SystemDataLocal(Context context){
        this.context = context;
    }

    public void setSessionLogin(){
        sharedPreferences = context.getSharedPreferences(KEY_USER,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("id",usersData.getId());
        editor.putString("personal_number",usersData.getPersonalNumber());
        editor.putString("name",usersData.getName());
        editor.putString("image",usersData.getImage());
        editor.putBoolean("login",true);
        editor.apply();
    }

    public void editAllSessionLogin(String personal_number,String name,String id,String image){
        sharedPreferences = context.getSharedPreferences(KEY_USER,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("personal_number",personal_number);
        editor.putString("name",name);
        editor.putString("id",id);
        editor.putString("image",image);
        editor.putBoolean("login",true);
        editor.apply();
    }

    public Users getLoginData(){
        sharedPreferences = context.getSharedPreferences(KEY_USER,Context.MODE_PRIVATE);
        String id = sharedPreferences.getString("id","");
        String personalNumber = sharedPreferences.getString("personal_number","");
        String name = sharedPreferences.getString("name","");
        String image = sharedPreferences.getString("image","");
        return new Users(id,name,personalNumber,image);
    }

    public boolean getCheckLogin(){
        sharedPreferences = context.getSharedPreferences(KEY_USER,Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("login",false);
    }

    public void destroySessionLogin(){
        sharedPreferences = context.getSharedPreferences(KEY_USER,Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();
    }
}
