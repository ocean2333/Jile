package com.example.jile.Setting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.jile.R;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        findViewById(R.id.btnBack).setOnClickListener(v->finish());
    }
}