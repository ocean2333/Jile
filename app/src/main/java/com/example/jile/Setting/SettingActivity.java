package com.example.jile.Setting;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.jile.LogoActivity;
import com.example.jile.R;
import com.example.jile.ui.login.LoginActivity;

public class SettingActivity extends AppCompatActivity {
    private TextView themeSetting,classSetting,data,privacy,switchAccount,about;
    public String theme;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeSettingActivity.setActivityTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        getComponent();
        setListener();
    }

    @Override
    protected void onResume(){
        super.onResume();
        if(!theme.equals(ThemeSettingActivity.theme)){
            ThemeSettingActivity.setActivityTheme(this);
            recreate();
        }
        super.onResume();
    }

    private void getComponent(){
        themeSetting = findViewById(R.id.tvTheme);
        classSetting = findViewById(R.id.tvClassSetting);
        data = findViewById(R.id.tvData);
        privacy = findViewById(R.id.tvPrivacy);
        switchAccount = findViewById(R.id.tvSwitchAccount);
        about = findViewById(R.id.tvAbout);
    }

    private void setListener(){
        themeSetting.setOnClickListener(v->{
            startActivity(new Intent(SettingActivity.this,ThemeSettingActivity.class));
        });
        classSetting.setOnClickListener(v->{
            startActivity(new Intent(SettingActivity.this,SettingClassSelectTypeActivity.class));
        });
        data.setOnClickListener(v->{

        });
        privacy.setOnClickListener(v->{

        });
        switchAccount.setOnClickListener(v->{
            new AlertDialog.Builder(this)
                    .setTitle("退出登录并返回到登陆界面？")
                    .setIcon(R.drawable.ic_prompt)
                    .setPositiveButton("确定", (arg0, arg1) -> {
                        LogoActivity.sp.edit().putString("loginUser",null).apply();
                        startActivity(new Intent(SettingActivity.this, LoginActivity.class));
                    })
                    .setNegativeButton("取消", null)
                    .show();
        });
        about.setOnClickListener(v->{

        });
    }
}