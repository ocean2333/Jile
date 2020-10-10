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

import com.example.jile.LogoActivity;
import com.example.jile.MainView.MainActivity;
import com.example.jile.Bean.Account;
import com.example.jile.R;

import java.math.BigDecimal;
import java.util.List;

import static com.example.jile.Constant.Constants.BANK_ACCOUNT;
import static com.example.jile.Constant.Constants.CASH_ACCOUNT;
import static com.example.jile.Constant.Constants.NET_ACCOUNT;
import static com.example.jile.Constant.Constants.OTHER_ACCOUNT;

public class AccountActivity extends AppCompatActivity {
    private Button btnBack,btnCreateNewAccount;
    private List<Account> cashAccount, bankAccount, netAccount,otherAccount;
    private String totalMoney,deltaMoney,cashMoney, otherMoney, bankMoney, netMoney;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        init();
    }

    @Override
    protected void onResume(){
        super.onResume();
        updateView();
    }

    // TODO 实现以下接口(测
    // TODO 获取所有账户信息 没有则返回null（测
    private void getAccounts(){
        netAccount = LogoActivity.accountDao.querybyskey("type",NET_ACCOUNT);
        cashAccount=LogoActivity.accountDao.querybyskey("type",CASH_ACCOUNT);
        bankAccount =LogoActivity.accountDao.querybyskey("type",BANK_ACCOUNT);
        otherAccount= LogoActivity.accountDao.querybyskey("type",OTHER_ACCOUNT);
    }
    private BigDecimal getMoneysofAccount(List<Account> account){
        BigDecimal sumMoney=new BigDecimal("0");
        for(Account money:account){
            sumMoney=sumMoney.add(money.getBalance());
        }
        return sumMoney;
    }
    private void getMoneys(){
        BigDecimal cashMoneyTemp,otherMoneyTemp,bankMoneyTemp,netMoneyTemp;
        netMoneyTemp=getMoneysofAccount(netAccount);
        cashMoneyTemp=getMoneysofAccount(cashAccount);
        bankMoneyTemp=getMoneysofAccount(bankAccount);
        otherMoneyTemp=getMoneysofAccount(otherAccount);
        netMoney =netMoneyTemp.toString();
        cashMoney=cashMoneyTemp.toString();
        bankMoney =bankMoneyTemp.toString();
        otherMoney =otherMoneyTemp.toString();
        deltaMoney="123.0";
        totalMoney=netMoneyTemp.add(cashMoneyTemp.add(bankMoneyTemp.add(otherMoneyTemp))).toString();
    }
    // TODO 实现以上接口

    private void setListView(){
        ListView lvCash = findViewById(R.id.lvCash);
        ListView lvBank = findViewById(R.id.lvBank);
        ListView lvNet = findViewById(R.id.lvNet);
        ListView lvOther = findViewById(R.id.lvOther);
        if(netAccount !=null){
            ArrayAdapter<Account> cardAdapter = new AccountAdapter(AccountActivity.this,R.layout.adapter_account, netAccount);
            lvNet.setAdapter(cardAdapter);
            lvNet.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                    Toast.makeText(AccountActivity.this,position,Toast.LENGTH_SHORT).show();
                }
            });
        }
        if (cashAccount!=null){
            ArrayAdapter<Account> cashAdapter = new AccountAdapter(AccountActivity.this,R.layout.adapter_account, cashAccount);
            lvCash.setAdapter(cashAdapter);
            lvCash.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                    Toast.makeText(AccountActivity.this,position,Toast.LENGTH_SHORT).show();
                }
            });
        }
        if (bankAccount !=null){
            ArrayAdapter<Account> virtualAdapter = new AccountAdapter(AccountActivity.this,R.layout.adapter_account, bankAccount);
            lvBank.setAdapter(virtualAdapter);
            lvBank.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                    Toast.makeText(AccountActivity.this,position,Toast.LENGTH_SHORT).show();
                }
            });
        }
        if (otherAccount!=null){
            ArrayAdapter<Account> wealthAdapter = new AccountAdapter(AccountActivity.this,R.layout.adapter_account, otherAccount);
            lvOther.setAdapter(wealthAdapter);
            lvOther.setOnItemClickListener(new AdapterView.OnItemClickListener()
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
                startActivity(new Intent(AccountActivity.this,CreateNewAccountActivity.class));
            }
        });
        updateView();
    }

    private void updateView(){
        getAccounts();
        getMoneys();
        TextView tvTotalMoney = findViewById(R.id.tvTotalWealthNum);
        TextView tvDelta = findViewById(R.id.tvCompareToLastMonth);
        TextView tvCashNum = findViewById(R.id.tvCashNum);
        TextView tvBankNum = findViewById(R.id.tvBankNum);
        TextView tvNetNum = findViewById(R.id.tvNetNum);
        TextView tvOtherNum = findViewById(R.id.tvOtherNum);
        tvTotalMoney.setText(totalMoney);
        if(deltaMoney.charAt(0)=='-'){
            tvDelta.setText("相比上个月减少"+(deltaMoney.split("-")[1]));
        }else{
            tvDelta.setText("相比上个月增加"+deltaMoney);
        }
        tvNetNum.setText(netMoney);
        tvCashNum.setText(cashMoney);
        tvBankNum.setText(bankMoney);
        tvOtherNum.setText(otherMoney);
        setListView();
    }
}