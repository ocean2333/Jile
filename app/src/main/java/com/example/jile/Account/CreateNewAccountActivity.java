package com.example.jile.Account;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.example.jile.Bean.Account;
import com.example.jile.Constant.Constants;
import com.example.jile.LogoActivity;
import com.example.jile.R;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class CreateNewAccountActivity extends AppCompatActivity {
    private Button btnConfirm,btnBack,btnCurrency,btnAccountType;
    private List<String> currencyItems, chineseAccountTypeItems,accountTypeItems;
    private EditText etAccountName,etBalance,etNote;
    private String chineseAccountType,accountType,currency;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_account);
        getComponents();
        chineseAccountType = chineseAccountTypeItems.get(0);
        accountType = accountTypeItems.get(0);
        currency = currencyItems.get(0);
        etBalance.setText("0.00");
        btnCurrency.setText(currencyItems.get(0));
        btnAccountType.setText(chineseAccountTypeItems.get(0));
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewAccountToDB(createNewAccount());
                finish();
            }
        });
        btnAccountType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OptionsPickerView pvOptions2 = new OptionsPickerBuilder(CreateNewAccountActivity.this, new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                        //返回的分别是三个级别的选中位置
                        chineseAccountType = chineseAccountTypeItems.get(options1);
                        accountType = accountTypeItems.get(options1);
                        btnAccountType.setText(chineseAccountType);
                    }
                }).build();
                pvOptions2.setPicker(chineseAccountTypeItems);
                pvOptions2.show();
            }
        });
        btnCurrency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OptionsPickerView pvOptions2 = new OptionsPickerBuilder(CreateNewAccountActivity.this, new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                        //返回的分别是三个级别的选中位置
                        currency = currencyItems.get(options1);
                        btnCurrency.setText(currency);
                    }
                }).build();
                pvOptions2.setPicker(currencyItems);
                pvOptions2.show();
            }
        });
    }

    private void getComponents(){
        btnBack = findViewById(R.id.btnBack);
        btnConfirm = findViewById(R.id.btnConfirm);
        btnCurrency = findViewById(R.id.btnAccountType);
        btnAccountType = findViewById(R.id.btnCurrency);
        etAccountName = findViewById(R.id.etAccountName);
        etBalance = findViewById(R.id.etBalance);
        etNote = findViewById(R.id.etNote);
        currencyItems = Arrays.asList("CNY");
        chineseAccountTypeItems = Arrays.asList("现金账户","银行账户","网络账户","其他账户");
        accountTypeItems = Arrays.asList(Constants.CASH_ACCOUNT,Constants.BANK_ACCOUNT,Constants.NET_ACCOUNT,Constants.OTHER_ACCOUNT);
    }

    // TODO 构造account(待测
    private Account createNewAccount(){
        return new Account(UUID.randomUUID().toString(), accountType,
                etAccountName.getText().toString(), new BigDecimal(etBalance.getText().toString()),
                currency,R.drawable.icon_dollar,
                etNote.getText().toString());
    }

    private void addNewAccountToDB(Account account){
        LogoActivity.accountDao.insert(account);
    }
}