package com.example.jile.Util;

import android.annotation.SuppressLint;
import android.content.res.Resources;

import com.example.jile.Constant.Constants;
import com.example.jile.R;
import com.example.jile.myApplication;

import static com.example.jile.Constant.Constants.THEME_BLUE;
import static com.example.jile.Constant.Constants.THEME_GREEN;
import static com.example.jile.Constant.Constants.THEME_PINK;
import static com.example.jile.Constant.Constants.THEME_RED;
import static com.example.jile.Constant.Constants.THEME_WHITE;
import static com.example.jile.Constant.Constants.THEME_YELLOW;

public class ThemeUtil {
    static String theme = Constants.THEME_YELLOW;

    public static void setModel(String theme) {
        ThemeUtil.theme = theme;
    }

    public static String getTheme() {
        return theme;
    }

    @SuppressLint("ResourceType")
    public static int getColorPrimary(Resources.Theme t) {
        return myApplication.getContext().getResources().getColor(R.color.colorPrimary,t);
    }

}