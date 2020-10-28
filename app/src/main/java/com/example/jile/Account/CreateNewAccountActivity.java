package com.example.jile.Account;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.example.jile.Bean.Account;
import com.example.jile.Bean.Bill;
import com.example.jile.Constant.Constants;
import com.example.jile.LogoActivity;
import com.example.jile.New.NewBIllActivity;
import com.example.jile.R;
import com.example.jile.Setting.ThemeSettingActivity;
import com.example.jile.Util.IconSelectorActivity;
import com.example.jile.Util.ToastUtil;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class CreateNewAccountActivity extends AppCompatActivity {
    private ImageButton btnConfirm,btnBack;
    private Button btnCurrency,btnAccountType;
    private ImageButton btnSelectIcon,btnDelete;
    private List<String> currencyItems, chineseAccountTypeItems,accountTypeItems;
    private EditText etAccountName,etBalance,etNote;
    private String chineseAccountType,accountType,currency;
    private String uuid;
    private Account account;
    private Integer iconId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeSettingActivity.setActivityTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_account);
        if(getIntent().getExtras()!=null){
            uuid = getIntent().getExtras().getString("uuid",null);
            account = LogoActivity.accountDao.querybyskey("uuid",uuid).get(0);
        }
        getComponents();
        init();
    }

    private void getComponents(){
        btnBack = findViewById(R.id.btnBack);
        btnConfirm = findViewById(R.id.btnConfirm);
        btnCurrency = findViewById(R.id.btnAccountType);
        btnAccountType = findViewById(R.id.btnCurrency);
        etAccountName = findViewById(R.id.etAccountName);
        etBalance = findViewById(R.id.etBalance);
        etNote = findViewById(R.id.etNote);
        btnDelete = findViewById(R.id.btnDelete);
        currencyItems = Arrays.asList("CNY");
        btnSelectIcon = findViewById(R.id.btnSelectIcon);
        chineseAccountTypeItems = Arrays.asList("现金账户","银行账户","网络账户","其他账户");
        accountTypeItems = Arrays.asList(Constants.CASH_ACCOUNT,Constants.BANK_ACCOUNT,Constants.NET_ACCOUNT,Constants.OTHER_ACCOUNT);
    }

    private Account createNewAccount(){
        if(uuid==null){
            return new Account(UUID.randomUUID().toString(), accountType,
                    etAccountName.getText().toString(), new BigDecimal(etBalance.getText().toString()),
                    currency,iconId,
                    etNote.getText().toString());
        }else{
            return new Account(uuid, accountType,
                    etAccountName.getText().toString(), new BigDecimal(etBalance.getText().toString()),
                    currency,iconId,
                    etNote.getText().toString());
        }
    }

    private void addNewAccountToDB(Account account){
        LogoActivity.accountDao.insert(account);
    }

    private void init(){
        if(uuid==null){
            chineseAccountType = chineseAccountTypeItems.get(0);
            accountType = accountTypeItems.get(0);
            currency = currencyItems.get(0);
            etBalance.setText("0.00");
            btnCurrency.setText(currencyItems.get(0));
            btnAccountType.setText(chineseAccountTypeItems.get(0));
            btnDelete.setVisibility(View.GONE);
        }else{
            chineseAccountType = chineseAccountTypeItems.get(accountTypeItems.indexOf(account.getType()));
            accountType = account.getType();
            currency = account.getCurrency();
            etAccountName.setText(account.getSelfname());
            etBalance.setText(account.getBalance().toPlainString());
            btnCurrency.setText(currencyItems.get(0));
            btnAccountType.setText(chineseAccountType);
            etNote.setText(account.getNote());
            iconId = account.getIconId();
            ((ImageView)findViewById(R.id.ivIcon)).setImageResource(account.getIconId());
        }
        setLinstener();
    }

    private void setLinstener(){
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        etAccountName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(LogoActivity.accountDao.querybyskey("selfname",etAccountName.getText().toString()).size()>0){
                    ToastUtil.showShortToast(CreateNewAccountActivity.this,"已经有同名的账户了呢");
                    btnConfirm.setEnabled(false);
                }else{
                    btnConfirm.setEnabled(true);
                }
            }
        });
        btnConfirm.setOnClickListener(v -> {
            if(iconId == null){
                ToastUtil.showShortToast(this,"请选择一个图标");
            }else{
                if(uuid==null){
                    addNewAccountToDB(createNewAccount());
                }else{
                    updateAccountInDB(createNewAccount());
                }
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
        btnDelete.setOnClickListener((v -> {
            Account account = LogoActivity.accountDao.querybyskey("uuid",uuid).get(0);
            for(Bill b:LogoActivity.billDao.querybyskey("accountname",account.getSelfname())){
                NewBIllActivity.deleteBillInDB(b);
            }
            for(Bill b:LogoActivity.billDao.querybyskey("first",account.getSelfname())){
                NewBIllActivity.deleteBillInDB(b);
            }
            for(Bill b:LogoActivity.billDao.querybyskey("second",account.getSelfname())){
                NewBIllActivity.deleteBillInDB(b);
            }
            LogoActivity.accountDao.delete(account);
            finish();
        }));
        btnSelectIcon.setOnClickListener(v->{
            startActivityForResult(IconSelectorActivity.startThisActivity(this,Constants.ACCOUNT),0);
        });
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ImageView iv = (ImageView) findViewById(R.id.ivIcon);
        if(data==null) return;
        iconId = data.getExtras().getInt("iconId");
        iv.setImageResource(data.getExtras().getInt("iconId"));
    }

    public static Intent startThisActivity(Context context, String uuid){
        Bundle bundle = new Bundle();
        bundle.putString("uuid",uuid);
        Intent intent = new Intent(context, CreateNewAccountActivity.class);
        intent.putExtras(bundle);
        return intent;
    }

    private void updateAccountInDB(Account account){
        LogoActivity.accountDao.update(account);
    }
}