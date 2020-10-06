package com.example.jile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.jile.MainView.MainActivity;
import com.example.jile.ui.login.LoginActivity;

public class LogoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000); // 休眠1秒
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //根据登陆状态切换至主页或登录页
                Intent intent;
                if(isLogin()){
                    intent = new Intent(LogoActivity.this, MainActivity.class);
                }else{
                    intent = new Intent(LogoActivity.this,LoginActivity.class);
                }
                startActivity(intent);
            }
        }).start();

    }

    // TODO 判断是否已有登录账号
    private boolean isLogin(){
        return false;
    }

}
