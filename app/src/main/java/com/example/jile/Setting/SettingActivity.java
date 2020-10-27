package com.example.jile.Setting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import com.example.jile.R;

import static com.example.jile.Constant.Constants.COST;
import static com.example.jile.Constant.Constants.INCOME;

public class SettingActivity extends AppCompatActivity {
    private ImageButton btnToIncomeClass,btnToCostClass,btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        getComponent();
        setListener();
    }

    private void getComponent(){
        btnToIncomeClass = findViewById(R.id.btnToIncomeClassSetting);
        btnToCostClass= findViewById(R.id.btnToCostClassSetting);
        btnBack = findViewById(R.id.btnBack);
    }

    private void setListener(){
        btnToIncomeClass.setOnClickListener(v->{
            startActivity(ClassSettingActivity.startThisActivity(this,INCOME));
        });
        btnToCostClass.setOnClickListener(v->{
            startActivity(ClassSettingActivity.startThisActivity(this,COST));
        });
        btnBack.setOnClickListener(v->finish());
    }
}