package com.example.jile;

import android.app.Application;
import android.content.Context;

import com.xuexiang.xui.XUI;

public class myApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        XUI.init(this); //初始化UI框架
        XUI.debug(true);  //开启UI框架调试日志
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
