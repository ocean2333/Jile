package com.example.jile.Util;

import android.util.Log;

public class TextUtil {
    /**
     * 将表示金钱的字符串简化至只有两位小数（去尾）
     * @param s 由数字组成的字符串
     * */
    public static String simplifyMoney(String s){
        String[] s1 = s.split("\\.");
        Log.d("", "simplifyMoney: "+s1.length);
        if(s1.length == 1){
            return s;
        }else if(s1.length == 2){
            int end = Math.min(s1[1].length(), 2);
            return s1[0]+"."+s1[1].substring(0,end);
        }else{
            return "error string here";
        }
    }
}
