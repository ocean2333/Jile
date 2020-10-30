package com.example.jile.Util;

import android.content.Context;
import android.widget.Toast;

import com.xuexiang.xui.widget.toast.XToast;

public class ToastUtil {
    public static void showShortToast(Context context,String msg){
        XToast.info(context,msg,Toast.LENGTH_SHORT).show();
    }
}
