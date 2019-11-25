package com.example.news.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefUtilS {
    private static final String SHARE_PREFS_NAME = "example";

    public static void putBoolean(Context ctx, String key, Boolean value) {
        SharedPreferences pref = ctx.getSharedPreferences(SHARE_PREFS_NAME, Context.MODE_PRIVATE);
        pref.edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolean(Context ctx, String key, Boolean defaultValue) {
        SharedPreferences pref = ctx.getSharedPreferences(SHARE_PREFS_NAME, Context.MODE_PRIVATE);
        return pref.getBoolean(key, defaultValue);
    }

    public static void putString(Context ctx, String key, String value) {
        SharedPreferences pref = ctx.getSharedPreferences(SHARE_PREFS_NAME, Context.MODE_PRIVATE);
        pref.edit().putString(key, value).commit();
    }

    public static String getString(Context ctx, String key, String defaultValue) {
        SharedPreferences pref = ctx.getSharedPreferences(SHARE_PREFS_NAME, Context.MODE_PRIVATE);
        return pref.getString(key, defaultValue);

    }

    public static void pubInt(Context ctx, String key, int value) {
        SharedPreferences pref = ctx.getSharedPreferences(SHARE_PREFS_NAME, Context.MODE_PRIVATE);
        pref.edit().putInt(key, value).commit();
    }
    public  static  int getInt(Context ctx, String key, int defaultValue)
    {
        SharedPreferences pref = ctx.getSharedPreferences(SHARE_PREFS_NAME, Context.MODE_PRIVATE);
        return  pref.getInt(key,defaultValue);
    }
}
