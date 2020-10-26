package com.example.jile.Util;

import android.content.Intent;
import android.content.SharedPreferences;

import com.example.jile.LogoActivity;
import com.example.jile.R;

import java.util.HashMap;
import java.util.Map;

/**
 * 管理iconId及其对应的名字，方便根据中文名字搜索对应的iconId
 * 将数据存储至sharedpreference
 * */
public class IconManager {
    private static Map<String,Integer> icons;
    /**
     *
     * */
    public static void loadIconInfo(){
        Map<String,?> allInfo = LogoActivity.sp.getAll();
        icons = new HashMap<>();
        for (String s:allInfo.keySet()) {
            if(s.startsWith("IconManager")){
                String[] res = s.split("@");
                Object iconId = allInfo.get(s);
                icons.put(res[1], (Integer) iconId);
            }
        }
        if(icons.isEmpty()){
            initIcons();
        }
    }

    /**
     *
     * */
    public static void updateIconInfo(){
        SharedPreferences.Editor editor = LogoActivity.sp.edit();
        for(String s:icons.keySet()){
            editor.putInt("IconManager"+s,icons.get(s));
        }
    }

    /**
     *
     * */
    public static int insertNewIcon(String name,int iconId){
        if(icons.get(name)!=null){
            return 1;
        }else{
            icons.put(name,iconId);
            updateIconInfo();
            return 0;
        }
    }

    /**
     *
     * */
    public static void updateIcon(String name,int iconId){
        icons.put(name,iconId);
        updateIconInfo();
    }

    /**
     *
     * */
    public static int deleteIcon(String name){
        if(icons.get(name)==null){
            return 1;
        }else{
            icons.remove(name);
            updateIconInfo();
            return 0;
        }
    }

    /**
     *
     * */
    public static int getIcon(String name){
        if(icons.get(name)==null){
            return -1;
        }else{
            return icons.get(name);
        }
    }

    /**
     * 设置初始的icon
     * */
    private static void initIcons(){
        String keySet[] = new String[]{"人民币"};
        int valueSet[] = new int[]{R.drawable.ic_rmb};
        for(int i=0;i<keySet.length;i++){
            icons.put(keySet[i],valueSet[i]);
        }
    }
}
