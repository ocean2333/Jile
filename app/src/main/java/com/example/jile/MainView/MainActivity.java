package com.example.jile.MainView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jile.Account.AccountActivity;
import com.example.jile.Detail.DeatilActivity;
import com.example.jile.Graph.GraphActivity;
import com.example.jile.Model.Bill;
import com.example.jile.New.NewBIllActivity;
import com.example.jile.R;
import com.example.jile.Setting.SettingActivity;

import java.math.BigDecimal;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private Button btnToDetail,btnAccount,btnDetail,btnNew,btnGraph,btnSetting,btnModifyBudget;
    private String month,cost,income,budget,todayCost,todayIncome;
    private BigDecimal budgetInput;
    private Bill[] bills;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void getBtns(){
        btnToDetail = findViewById(R.id.btnToDetail);
        btnDetail = findViewById(R.id.btnDetail);
        btnAccount = findViewById(R.id.btnAccount);
        btnNew = findViewById(R.id.btnNew);
        btnGraph = findViewById(R.id.btnGraph);
        btnSetting = findViewById(R.id.btnSetting);
        btnModifyBudget = findViewById(R.id.btnModifyBudget);
    }

    private void setBtnListener(OnClick onClick){
        btnAccount.setOnClickListener(onClick);
        btnDetail.setOnClickListener(onClick);
        btnToDetail.setOnClickListener(onClick);
        btnNew.setOnClickListener(onClick);
        btnSetting.setOnClickListener(onClick);
        btnGraph.setOnClickListener(onClick);
        btnModifyBudget.setOnClickListener(onClick);
    }

    private class OnClick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent intent;
            switch (v.getId()){
                case R.id.btnAccount:
                    intent = new Intent(MainActivity.this, AccountActivity.class);
                    startActivity(intent);
                    break;
                case R.id.btnDetail:
                    intent = new Intent(MainActivity.this, DeatilActivity.class);
                    startActivity(intent);
                    break;
                case R.id.btnNew:
                    intent = new Intent(MainActivity.this, NewBIllActivity.class);
                    startActivity(intent);
                    break;
                case R.id.btnGraph:
                    intent = new Intent(MainActivity.this, GraphActivity.class);
                    startActivity(intent);
                    break;
                case R.id.btnSetting:
                    intent = new Intent(MainActivity.this, SettingActivity.class);
                    startActivity(intent);
                    break;
                case R.id.btnToDetail:
                    intent = new Intent(MainActivity.this, DeatilActivity.class);
                    startActivity(intent);
                    break;
                case R.id.btnModifyBudget:
                    showExitDialog();
                    break;
                default:
                    Log.e("error","未知的按键id");
                    throw new IllegalStateException("Unexpected value: " + v.getId());
            }
        }
    }

    //禁止使用返回键返回并替换为再按一次退出
    private long exitTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-exitTime) > 2000){
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void getMonth(){
            @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM月");
            //获取当前月份
            Date date = new Date(System.currentTimeMillis());
            month = simpleDateFormat.format(date);
    }


    private void showExitDialog(){
        final EditText edt = new EditText(this);
        edt.setMinLines(1);
        edt.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_VARIATION_NORMAL);
        new AlertDialog.Builder(this)
                .setTitle("请输入")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setView(edt)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        setBudgetThisMonth(new BigDecimal(edt.getText().toString()));
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }

    // TODO 实现下列获取信息的接口
    private void getCostAndIncome() {
        cost = "-1000";
        income = "10000";
    }

    private void getbudgetThisMonth(){
        budget = "100000";
    }


    private void setBudgetThisMonth(BigDecimal budget){

    }

    private void getTodayCostAndIncome(){
        todayCost = "-100";
        todayIncome = "1234";
    }

    private void getFiveMostRecentBill(){
        Bill b = new Bill(0,new BigDecimal("666.321"),"t1","t2",
                "zhi","we","qwe","hahaha",R.drawable.icon_dollar,"2020年10月02日 23:24");
        bills = new Bill[]{b,b,b,b,b};
    }
    // TODO 实现以上接口

    private void init(){
        getBtns();
        getbudgetThisMonth();
        getCostAndIncome();
        getMonth();
        getTodayCostAndIncome();
        OnClick onClick = new OnClick();
        setBtnListener(onClick);
        TextView tvMonth = findViewById(R.id.tvMonth);
        TextView tvCost = findViewById(R.id.tvCost);
        TextView tvIncome = findViewById(R.id.tvIncome);
        TextView tvBudget = findViewById(R.id.tvBudgetNum);
        TextView tvIncomeToday = findViewById(R.id.tvIncomeToday);
        TextView tvCostToday = findViewById(R.id.tvCostToday);
        tvCost.setText(cost);
        tvBudget.setText(budget);
        tvIncome.setText(income);
        tvIncomeToday.setText(todayIncome);
        tvCostToday.setText(todayCost);
        tvMonth.setText(month);
        getFiveMostRecentBill();
        ListView listView = findViewById(R.id.lvRecent);
        ArrayAdapter<Bill> arrayAdapter = new BillAdapter(MainActivity.this,R.layout.adapter_bill,bills);
        listView.setAdapter(arrayAdapter);
    }
}


