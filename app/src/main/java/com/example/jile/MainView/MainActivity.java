package com.example.jile.MainView;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.jile.Account.AccountActivity;
import com.example.jile.Detail.DeatilActivity;
import com.example.jile.Graph.GraphActivity;
import com.example.jile.New.NewBIllActivity;
import com.example.jile.R;
import com.example.jile.Setting.SettingActivity;

public class MainActivity extends AppCompatActivity {
    private Button btnToDetail,btnAccount,btnDetail,btnNew,btnGraph,btnSetting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        OnClick onClick = new OnClick();
        getBtns();
        setBtnListener(onClick);
    }

    private void getBtns(){
        btnToDetail = findViewById(R.id.btnToDetail);
        btnDetail = findViewById(R.id.btnDetail);
        btnAccount = findViewById(R.id.btnAccount);
        btnNew = findViewById(R.id.btnNew);
        btnGraph = findViewById(R.id.btnGraph);
        btnSetting = findViewById(R.id.btnSetting);
    }

    private void setBtnListener(OnClick onClick){
        btnAccount.setOnClickListener(onClick);
        btnDetail.setOnClickListener(onClick);
        btnToDetail.setOnClickListener(onClick);
        btnNew.setOnClickListener(onClick);
        btnSetting.setOnClickListener(onClick);
        btnGraph.setOnClickListener(onClick);
    }

    private class OnClick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent intent;
            switch (v.getId()){
                case R.id.btnAccount:
                    intent = new Intent(MainActivity.this, AccountActivity.class);
                    break;
                case R.id.btnDetail:
                    intent = new Intent(MainActivity.this, DeatilActivity.class);
                    break;
                case R.id.btnNew:
                    intent = new Intent(MainActivity.this, NewBIllActivity.class);
                    break;
                case R.id.btnGraph:
                    intent = new Intent(MainActivity.this, GraphActivity.class);
                    break;
                case R.id.btnSetting:
                    intent = new Intent(MainActivity.this, SettingActivity.class);
                    break;
                case R.id.btnToDetail:
                    intent = new Intent(MainActivity.this, DeatilActivity.class);
                    break;
                default:
                    Log.e("error","未知的按键id");
                    throw new IllegalStateException("Unexpected value: " + v.getId());
            }
            startActivity(intent);
        }
    }

    //禁止使用返回键返回并替换为再按一次退出
    private long exitTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-exitTime) > 2000){
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}


