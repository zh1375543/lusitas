package com.wzx.tipcontent.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.wzx.tipcontent.kit.MyApplication;


/**
 * Created by Quill on
 */
public class AppSP {
    public static final String FILE_NAME="app";
    public static SharedPreferences sp;
    public static final String IS_LOGIN = "is_login";
    public static final String ACCOUNT = "account";
    public static final String PASSWORD = "password";
    public static final String IS_OPEN_MAIN = "is_open_main";

    public static void putString(String key, String value){
        if (sp==null){
            sp = MyApplication.getInstance().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        }
        sp.edit().putString(key,value).commit();
    }

    public static String getString(String key){
        if (sp==null){
            sp = MyApplication.getInstance().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        }
        return sp.getString(key,null);
    }

    public static void putBoolean(String key, boolean value){
        if (sp==null){
            sp = MyApplication.getInstance().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        }
        sp.edit().putBoolean(key,value).commit();
    }

    public static boolean getBoolean(String key){
        if (sp==null){
            sp = MyApplication.getInstance().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        }
        return sp.getBoolean(key, false);
    }


    //清空
    public static void cleanString() {
        sp.edit().clear().commit();
    }
}
