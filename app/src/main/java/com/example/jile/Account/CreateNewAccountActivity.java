package com.example.jile.Account;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.jile.Bean.Account;
import com.example.jile.R;

public class CreateNewAccountActivity extends AppCompatActivity {
    private Button btnConfirm,btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_account);
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnConfirm = findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewAccountToDB(createNewAccount());

            }
        });
    }

    // TODO 构造account
    private Account createNewAccount(){
        return null;
    }

    // TODO 实现以下接口
    private void addNewAccountToDB(Account account){

    }
}