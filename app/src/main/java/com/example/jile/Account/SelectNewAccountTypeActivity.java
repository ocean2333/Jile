package com.example.jile.Account;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.jile.R;

import static com.example.jile.Constant.Constants.CASH_ACCOUNT;

public class SelectNewAccountTypeActivity extends AppCompatActivity {
    private Button btnCreateNewCashAccount,btnCreateNewCreditCardAccount,btnCreateNewBankAccount,btnCreateNewFinanceAccount,btnCreateNewVirtualAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_new_account_type);
    }

    private void getBtns(){
        btnCreateNewBankAccount = findViewById(R.id.btnCreateNewBankAccount);
        btnCreateNewCashAccount = findViewById(R.id.btnCreateNewCashAccount);
        btnCreateNewFinanceAccount = findViewById(R.id.btnCreateNewFinanceAccount);
        btnCreateNewCreditCardAccount = findViewById(R.id.btnCreateNewCreditCardAccount);
        btnCreateNewVirtualAccount = findViewById(R.id.btnCreateNewVirtualAccount);
    }

    private void setBtnListener(OnClick onClick){
        btnCreateNewVirtualAccount.setOnClickListener(onClick);
        btnCreateNewBankAccount.setOnClickListener(onClick);
        btnCreateNewCreditCardAccount.setOnClickListener(onClick);
        btnCreateNewCashAccount.setOnClickListener(onClick);
        btnCreateNewFinanceAccount.setOnClickListener(onClick);
    }

    private class OnClick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent intent;
            String accountType;
            switch (v.getId()){
                case R.id.btnCreateNewCashAccount:
                    accountType=CASH_ACCOUNT;
                    break;
                case R.id.btnCreateNewCreditCardAccount:
                    accountType=CASH_ACCOUNT;
                    break;
                case R.id.btnCreateNewBankAccount:
                    //accountType=BANK_ACCOUNT;
                    break;
                case R.id.btnCreateNewFinanceAccount:
                    //accountType=3;
                    break;
                case R.id.btnCreateNewVirtualAccount:
                    //accountType=4;
                    break;
                default:
                    Log.e("error","未知的按键id");
                    throw new IllegalStateException("Unexpected value: " + v.getId());
            }
            intent = new Intent(SelectNewAccountTypeActivity.this,CreateNewAccountActivity.class);
            Bundle bundle = new Bundle();
            //bundle.putInt("type",accountType);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

}