package com.example.jile.New;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
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
import com.example.jile.Model.Bill;
import com.example.jile.R;
import com.example.jile.Util.DatePickerDialogUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class NewBIllActivity extends AppCompatActivity {
    private Button btnFirstClass,btnSecondClass,btnSetDate,btnBack,btnSave;
    private List<String> firstClassItems;
    private List<List<String>> secondClassItems;
    private int type=0;
    private OnClick onClick = new OnClick();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_bill);
        init();

    }

    private void getBtns(){
        btnSetDate = findViewById(R.id.btnSetDate);
        btnBack = findViewById(R.id.btnBack);
        btnSave = findViewById(R.id.btnSave);
        btnFirstClass = findViewById(R.id.btnFirstClass);
        btnSecondClass = findViewById(R.id.btnSecondClass);
    }

    private class OnClick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btnSetDate:
                    TimePickerView pvTime = new TimePickerBuilder(NewBIllActivity.this, new OnTimeSelectListener() {
                        @Override
                        public void onTimeSelect(Date date,View v) {//选中事件回调
                            btnSetDate.setText(getTime(date));
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

                    break;
                case R.id.btnFirstClass:
                    OptionsPickerView pvOptions = new OptionsPickerBuilder(NewBIllActivity.this, new OnOptionsSelectListener() {
                        @Override
                        public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                            //返回的分别是三个级别的选中位置
                            String ftx = firstClassItems.get(options1);
                            String stx = secondClassItems.get(options1).get(option2);
                            btnFirstClass.setText(ftx);
                            btnSecondClass.setText(stx);
                        }
                    }).build();
                    pvOptions.setPicker(firstClassItems,secondClassItems);
                    pvOptions.show();
            }
        }
    }

    // TODO 获得各级分类数据
    private void getItems(){
        firstClassItems = Arrays.asList("a", "b", "ccccccc");
        secondClassItems = Arrays.asList(Arrays.asList("0","asdasd","nmsl"),Arrays.asList("1","asdasd","nmsl"),Arrays.asList("2","asdasd","nmsl"));
    }


    private void setListener(OnClick onClick){
        btnFirstClass.setOnClickListener(onClick);
        btnSetDate.setOnClickListener(onClick);
        btnSave.setOnClickListener(onClick);
        btnBack.setOnClickListener(onClick);
        btnSecondClass.setOnClickListener(onClick);
    }

    // TODO 构造BILL
    private Bill createNewBill(){
        return null;
    }

    private void addNewBillToDB(Bill bill){
        // TODO 添加与数据库的对接
    }

    private String getNowTime(){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");// HH:mm:ss
        //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

    private String getTime(Date date){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");// HH:mm:ss
        //获取当前时间
        return simpleDateFormat.format(date);
    }

    private void setRadioGroupListener(){
        RadioGroup rb = findViewById(R.id.rgType);
        rb.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (group.getCheckedRadioButtonId()) {
                    case R.id.rbExpenses:
                        type=0;
                        break;
                    case R.id.rbIncome:
                        type=1;
                        break;
                    case R.id.rbTransfer:
                        type=2;
                        break;
                }
            }
        });
    }

    // TODO 把所有初始化操作放进init
    private void init(){
        getItems();
        getBtns();
        setListener(onClick);
        setRadioGroupListener();
        btnFirstClass.setText(firstClassItems.get(0));
        btnSecondClass.setText(secondClassItems.get(0).get(0));
        btnSetDate.setText(getNowTime());
    }
}