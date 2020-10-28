package com.example.jile.Account;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jile.LogoActivity;
import com.example.jile.MainView.MainActivity;
import com.example.jile.Bean.Account;
import com.example.jile.R;
import com.example.jile.Setting.ThemeSettingActivity;
import com.example.jile.Util.TextUtil;
import com.xuexiang.xui.utils.WidgetUtils;
import com.xuexiang.xui.widget.textview.autofit.AutoFitTextView;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static com.example.jile.Constant.Constants.BANK_ACCOUNT;
import static com.example.jile.Constant.Constants.CASH_ACCOUNT;
import static com.example.jile.Constant.Constants.NET_ACCOUNT;
import static com.example.jile.Constant.Constants.OTHER_ACCOUNT;

public class AccountActivity extends AppCompatActivity {
    private ImageButton btnBack,btnCreateNewAccount;
    private List<Account> cashAccount, bankAccount, netAccount,otherAccount;
    private String totalMoney,deltaMoney,cashMoney, otherMoney, bankMoney, netMoney;
    private TextView tvCashNum,tvBankNum,tvNetNum,tvOtherNum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeSettingActivity.setActivityTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        init();
    }

    @Override
    protected void onResume(){
        super.onResume();
        updateView();
    }

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
        netMoney = TextUtil.simplifyMoney(netMoneyTemp.toPlainString());
        cashMoney = TextUtil.simplifyMoney(cashMoneyTemp.toPlainString());
        bankMoney = TextUtil.simplifyMoney(bankMoneyTemp.toPlainString());
        otherMoney = TextUtil.simplifyMoney(otherMoneyTemp.toPlainString());
        deltaMoney="123.0";
        totalMoney=TextUtil.simplifyMoney(netMoneyTemp.add(cashMoneyTemp.add(bankMoneyTemp.add(otherMoneyTemp))).toPlainString());
    }

    private void setListView(){
        List<LineElement> lel = new LinkedList<>();
        String[] bigDecimals = new String[]{cashMoney,bankMoney,netMoney,otherMoney};
        String[] s = new String[]{"现金账户","银行账户","网络账户","其他账户"};
        List<List<Account>> lla = new ArrayList<>();
        lla.add(cashAccount);
        lla.add(bankAccount);
        lla.add(netAccount);
        lla.add(otherAccount);
        for(int i=0;i<4;i++){
            List<View> list = new LinkedList<>();
            for(Account a:lla.get(i)){
                list.add(transformAccountToView(a));
            }
            lel.add(new LineElement(s[i],bigDecimals[i],list));
        }
        RecyclerView ll_3 = findViewById(R.id.ll_3);
        WidgetUtils.initRecyclerView(ll_3);
        ll_3.setAdapter(new AccountTitleAdapter(lel,R.layout.adapter_ako_account));

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
        setListView();
        TextView tvTotalMoney = findViewById(R.id.tvTotalWealthNum);
        tvTotalMoney.setText(totalMoney);
    }

    private View transformAccountToView(Account account){
        View view = LayoutInflater.from(this).inflate(R.layout.adapter_account,null);
        if(account==null){
            return null;
        }else{
            ImageView im = view.findViewById(R.id.imIcon);
            TextView tvName = view.findViewById(R.id.tvName);
            TextView tvMoney = view.findViewById(R.id.tvMoney);
            ImageButton btnModify = view.findViewById(R.id.btnModify);
            TextView uuid = view.findViewById(R.id.uuid);
            im.setImageResource(account.getIconId());
            tvName.setText(account.getSelfname());
            tvMoney.setText(TextUtil.simplifyMoney(account.getBalance().toPlainString()));
            uuid.setText(account.getUuid());
            btnModify.setOnClickListener((v -> {
                startActivity(CreateNewAccountActivity.startThisActivity(this,account.getUuid()));
            }));
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,150);
            view.setLayoutParams(params);
        }
        return view;
    }

}