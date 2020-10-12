package com.example.jile.New;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.example.jile.Bean.Account;
import com.example.jile.Bean.Bill;
import com.example.jile.Constant.Constants;
import com.example.jile.Database.Dao.BillDao;
import com.example.jile.LogoActivity;
import com.example.jile.R;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class NewBIllActivity extends AppCompatActivity {
    private Button btnFirstClass,btnSecondClass,btnSetDate,btnBack,btnSave,btnSelectAccount,btnSelectMember,btnSetStore;
    private List<String> firstClassItems,accountItems,memberItems,storeItems;
    private List<List<String>> secondClassItems;
    private String type= Constants.COST;
    private OnClick onClick = new OnClick();
    private EditText etMoneyNumber,etNote;
    private String firstClass,secondClass,accountName,member,store,time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_bill);
        init();
    }

    private void getComponents(){
        btnSetDate = findViewById(R.id.btnSetDate);
        btnBack = findViewById(R.id.btnBack);
        btnSave = findViewById(R.id.btnSave);
        btnFirstClass = findViewById(R.id.btnFirstClass);
        btnSecondClass = findViewById(R.id.btnSecondClass);
        btnSelectAccount = findViewById(R.id.btnSelectAccount);
        btnSelectMember = findViewById(R.id.btnSelectMember);
        btnSetStore = findViewById(R.id.btnSetStore);
        etMoneyNumber = findViewById(R.id.etMoneyNumber);
        etNote = findViewById(R.id.etNote);
    }

    private class OnClick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btnSetDate:
                    TimePickerView pvTime = new TimePickerBuilder(NewBIllActivity.this, new OnTimeSelectListener() {
                        @Override
                        public void onTimeSelect(Date date,View v) {//选中事件回调
                            btnSetDate.setText(getNowTime(date));
                            time = getTime(date);
                        }
                    }).setType(new boolean[]{true, true, true, true, true, false})//分别对应年月日时分秒，默认全部显示
                      .build();
                    //pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
                    pvTime.show();
                    break;
                case R.id.btnBack:
                    finish();
                    break;
                case R.id.btnSave:
                    addNewBillToDB(createNewBill());
                    finish();
                    break;
                case R.id.btnFirstClass:
                    OptionsPickerView pvOptions = new OptionsPickerBuilder(NewBIllActivity.this, new OnOptionsSelectListener() {
                        @Override
                        public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                            //返回的分别是三个级别的选中位置
                            firstClass = firstClassItems.get(options1);
                            secondClass = secondClassItems.get(options1).get(option2);
                            btnFirstClass.setText(firstClass);
                            btnSecondClass.setText(secondClass);
                        }
                    }).build();
                    pvOptions.setPicker(firstClassItems,secondClassItems);
                    pvOptions.show();
                    break;
                case R.id.btnSelectAccount:
                    OptionsPickerView pvOptions2 = new OptionsPickerBuilder(NewBIllActivity.this, new OnOptionsSelectListener() {
                        @Override
                        public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                            //返回的分别是三个级别的选中位置
                            accountName = accountItems.get(options1);
                            btnSelectAccount.setText(accountName);
                        }
                    }).build();
                    pvOptions2.setPicker(accountItems);
                    pvOptions2.show();
                    break;
                case R.id.btnSelectMember:
                    OptionsPickerView pvOptions3 = new OptionsPickerBuilder(NewBIllActivity.this, new OnOptionsSelectListener() {
                        @Override
                        public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                            //返回的分别是三个级别的选中位置
                            member = memberItems.get(options1);
                            btnSelectMember.setText(member);
                        }
                    }).build();
                    pvOptions3.setPicker(memberItems);
                    pvOptions3.show();
                    break;
                case R.id.btnSetStore:
                    OptionsPickerView pvOptions4 = new OptionsPickerBuilder(NewBIllActivity.this, new OnOptionsSelectListener() {
                        @Override
                        public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                            //返回的分别是三个级别的选中位置
                            store = storeItems.get(options1);
                            btnSetStore.setText(store);
                        }
                    }).build();
                    pvOptions4.setPicker(storeItems);
                    pvOptions4.show();
                    break;
            }
        }
    }

    private void setListener(OnClick onClick){
        btnFirstClass.setOnClickListener(onClick);
        btnSetDate.setOnClickListener(onClick);
        btnSave.setOnClickListener(onClick);
        btnBack.setOnClickListener(onClick);
        btnSecondClass.setOnClickListener(onClick);
        btnSelectMember.setOnClickListener(onClick);
        btnSelectAccount.setOnClickListener(onClick);
        btnSetStore.setOnClickListener(onClick);
    }
    // TODO 实现下列接口
    // TODO 获得各级分类数据
    private void getItems(){
        firstClassItems = Arrays.asList("a", "b", "ccccccc");
        secondClassItems = Arrays.asList(Arrays.asList("0","asdasd","nmsl"),Arrays.asList("1","asdasd","nmsl"),Arrays.asList("2","asdasd","nmsl"));
        List<Account> al = LogoActivity.accountDao.query();
        accountItems = new ArrayList<>();
        for(Account a:al){
            accountItems.add(a.getSelfname());
        }
        memberItems = Arrays.asList("自己","lzy","zyh");
        storeItems = Arrays.asList("home","school","mall");
    }
    // TODO 实现以上接口

    // TODO 添加bill到数据库(测
    private void addNewBillToDB(Bill bill){
        LogoActivity.billDao.insert(bill);
        List<Account> tempaccount =LogoActivity.accountDao.querybyskey("selfname",bill.getAccountname());
        tempaccount.get(0).setBalance(tempaccount.get(0).getBalance().add(bill.getNum()));
        LogoActivity.accountDao.update(tempaccount.get(0));
    }

    // TODO 获得最近用过的店（测
    private String getMostRecentStore(){
        String recentStore = LogoActivity.sp.getString("recentStore",null);
        if(recentStore==null){
            return storeItems.get(0);
        }else{
            return recentStore;
        }
    }
    // TODO 更新最近用过的店(测
    private void modifyMostRecentStore(String s){
        LogoActivity.sp.edit().putString("recentStore",s).apply();
    }

    // TODO 构造BILL(测
    private Bill createNewBill(){
        return new Bill(UUID.randomUUID().toString(),type,new BigDecimal(etMoneyNumber.getText().toString()),
                accountName,firstClass,secondClass,member,store,time,R.drawable.icon_dollar,etNote.getText().toString());
    }

    private String getNowTime(){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");// HH:mm:ss
        //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

    private String getNowTime(Date date){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");// HH:mm:ss
        //获取时间
        return simpleDateFormat.format(date);
    }

    private String getTime(Date date){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm");// HH:mm:ss
        //获取时间
        return simpleDateFormat.format(date);
    }

    private void setRadioGroupListener(){
        RadioGroup rg = findViewById(R.id.rgType);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (group.getCheckedRadioButtonId()) {
                    case R.id.rbExpenses:
                        type=Constants.COST;
                        break;
                    case R.id.rbIncome:
                        type=Constants.INCOME;
                        break;
                    case R.id.rbTransfer:
                        type=Constants.TRANSFER;
                        break;
                }
            }
        });
    }

    private void init(){
        getItems();
        getComponents();
        setListener(onClick);
        setRadioGroupListener();
        btnFirstClass.setText(firstClassItems.get(0));
        btnSecondClass.setText(secondClassItems.get(0).get(0));
        btnSelectAccount.setText(accountItems.get(0));
        btnSelectMember.setText(memberItems.get(0));
        btnSetStore.setText(getMostRecentStore());
        btnSetDate.setText(getNowTime());
        accountName = accountItems.get(0);
        firstClass = firstClassItems.get(0);
        secondClass = secondClassItems.get(0).get(0);
        member = memberItems.get(0);
        store = getMostRecentStore();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm");// HH:mm:ss
        time = simpleDateFormat.format(new Date(System.currentTimeMillis()));
    }
}