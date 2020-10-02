package com.example.jile.Account;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jile.MainView.MainActivity;
import com.example.jile.Model.Account;
import com.example.jile.R;

import java.math.BigDecimal;
import java.util.Arrays;

public class AccountActivity extends AppCompatActivity {
    private Button btnBack,btnCreateNewAccount;
    private Account[] cashAccount,wealthAccount,virtualAccount,cardAccount;
    private String totalMoney,deltaMoney,cashMoney,wealthMoney,virtualMoney,cardMoney;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        init();
    }
    // TODO 实现以下接口
    // TODO 获取所有账户信息 没有则返回null
    private void getAccounts(){
        cardAccount=null;
        cashAccount=null;
        virtualAccount=null;
        Account sampleAccount = new Account("shili",R.drawable.icon_dollar,new BigDecimal("12346.654321"),"CNY","nothing",4);
        wealthAccount= new Account[]{sampleAccount, sampleAccount};
    }
    private void getMoneys(){
        totalMoney = "123456.78";
        deltaMoney = "-9990.12";
        cashMoney = "12.34";
        wealthMoney = "56.78";
        virtualMoney = "12341.32";
        cardMoney = "2647.34";
    }
    // TODO 实现以上接口

    private void setListView(){
        ListView lvCard = findViewById(R.id.lvCard);
        ListView lvWealth = findViewById(R.id.lvWealth);
        ListView lvVirtual = findViewById(R.id.lvVirtual);
        ListView lvCash = findViewById(R.id.lvCash);
        if(cardAccount!=null){
            ArrayAdapter<Account> cardAdapter = new AccountAdapter(AccountActivity.this,R.layout.adapter_account, Arrays.asList(cardAccount));
            lvCard.setAdapter(cardAdapter);
            lvCard.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                    Toast.makeText(AccountActivity.this,position,Toast.LENGTH_SHORT).show();

                }
            });
        }
        if (cashAccount!=null){
            ArrayAdapter<Account> cashAdapter = new AccountAdapter(AccountActivity.this,R.layout.adapter_account, Arrays.asList(cashAccount));
            lvCash.setAdapter(cashAdapter);
            lvCash.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                    Toast.makeText(AccountActivity.this,position,Toast.LENGTH_SHORT).show();

                }
            });
        }
        if (virtualAccount!=null){
            ArrayAdapter<Account> virtualAdapter = new AccountAdapter(AccountActivity.this,R.layout.adapter_account, Arrays.asList(virtualAccount));
            lvVirtual.setAdapter(virtualAdapter);
            lvVirtual.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                    Toast.makeText(AccountActivity.this,position,Toast.LENGTH_SHORT).show();

                }
            });
        }
        if (wealthAccount!=null){
            ArrayAdapter<Account> wealthAdapter = new AccountAdapter(AccountActivity.this,R.layout.adapter_account, Arrays.asList(wealthAccount));
            lvWealth.setAdapter(wealthAdapter);
            lvWealth.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                    Toast.makeText(AccountActivity.this,position,Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void init(){
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
        getAccounts();
        getMoneys();
        TextView tvTotalMoney = findViewById(R.id.tvTotalWealthNum);
        TextView tvDelta = findViewById(R.id.tvCompareToLastMonth);
        TextView tvCashNum = findViewById(R.id.tvCashNum);
        TextView tvWealthNum = findViewById(R.id.tvWealthNum);
        TextView tvCardNum = findViewById(R.id.tvCardNum);
        TextView tvVirtualNum = findViewById(R.id.tvVirtualNum);
        tvTotalMoney.setText(totalMoney);
        if(deltaMoney.charAt(0)=='-'){
            tvDelta.setText("相比上个月减少"+(deltaMoney.split("-")[1]));
        }else{
            tvDelta.setText("相比上个月增加"+deltaMoney);
        }
        tvCardNum.setText(cardMoney);
        tvCashNum.setText(cashMoney);
        tvWealthNum.setText(wealthMoney);
        tvVirtualNum.setText(virtualMoney);
        setListView();
    }
}