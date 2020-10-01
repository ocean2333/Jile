package com.example.jile.Account;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.jile.MainView.MainActivity;
import com.example.jile.R;

public class AccountActivity extends AppCompatActivity {
    private Button btnBack,btnCreateNewAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        btnBack = findViewById(R.id.btnBack);
        btnCreateNewAccount = findViewById(R.id.btnCreateNewAccount);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccountActivity.this, MainActivity.class));
            }
        });
        btnCreateNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccountActivity.this,SelectNewAccountTypeActivity.class));
            }
        });
    }
}