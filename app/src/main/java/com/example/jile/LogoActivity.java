package com.example.jile;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.jile.Database.Dao.AccountDao;
import com.example.jile.Database.Dao.BillDao;
import com.example.jile.Database.Dao.FirstClassDao;
import com.example.jile.Database.Dao.IconDao;
import com.example.jile.Database.Dao.MemDao;
import com.example.jile.Database.Dao.SecondClassDao;
import com.example.jile.Database.Dao.StoreDao;
import com.example.jile.Database.Dao.UserDao;
import com.example.jile.Database.DatabaseHelper;
import com.example.jile.MainView.MainActivity;
import com.example.jile.ui.login.LoginActivity;
import com.idescout.sql.SqlScoutServer;
import com.xuexiang.xui.XUI;

public class LogoActivity extends AppCompatActivity {
    public static SharedPreferences sp;
    public static AccountDao accountDao;
    public static BillDao billDao;
    public static MemDao memDao;
    public static UserDao userDao;
    public static FirstClassDao firstClassDao;
    public static SecondClassDao secondClassDao;
    public static StoreDao storeDao;
    public static IconDao iconDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);
        sp = getSharedPreferences("login",MODE_PRIVATE);
        userDao = new UserDao(this);
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
                finish();
            }
        }).start();

    }

    //
    private boolean isLogin(){
        String loginUser = sp.getString("loginUser",null);
        return loginUser != null;
    }

}
