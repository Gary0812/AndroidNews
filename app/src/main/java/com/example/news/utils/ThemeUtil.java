package com.example.news.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.news.R;

public class ThemeUtil {
    public static class ThemeColors {
        public static final int THEME_GREEN = 1;
        public static final int ThEME_BLUE = 2;
        public static final int THEME_GREY = 3;
        public static final int THEME_YELLOW = 4;
        public static final int THEME_RED = 5;
        public static final int THEME_WHITE = 6;
    }

    public static void setBaseTheme(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                "MyThemeShared", context.MODE_PRIVATE);
        int themeType = sharedPreferences.getInt("theme_type", 0);
        int themeId;
        switch (themeType) {
            case ThemeColors.THEME_GREEN:
                themeId = R.style.AppThemeNoAction_Green;
                break;
            case ThemeColors.ThEME_BLUE:
                themeId = R.style.AppThemeNoAction_Blue;
                break;

            case ThemeColors.THEME_GREY:
                themeId = R.style.AppThemeNoAction_Grey;
                break;
            case ThemeColors.THEME_YELLOW:
                themeId = R.style.AppThemeNoAction_Yellow;
                break;
            case ThemeColors.THEME_RED:
                themeId = R.style.AppThemeNoAction_Red;
                break;
            case ThemeColors.THEME_WHITE:
                themeId = R.style.AppThemeNoAction_White;
                break;
            default:
                themeId = R.style.AppThemeNoAction;
        }
        context.setTheme(themeId);
    }

    public static boolean setNewTheme(Context context, int theme) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                "MyThemeShared", context.MODE_PRIVATE);
        SharedPreferences.Editor e = sharedPreferences.edit();
        e.putInt("theme_type",theme);
//        e.apply();
        return  e.commit();//有返回值
    }

}
