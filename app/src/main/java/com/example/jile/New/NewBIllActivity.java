package com.example.jile.New;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.example.jile.Bean.Account;
import com.example.jile.Bean.Bill;
import com.example.jile.Bean.FirstClass;
import com.example.jile.Bean.Mem;
import com.example.jile.Bean.SecondClass;
import com.example.jile.Bean.Store;
import com.example.jile.Constant.Constants;
import com.example.jile.LogoActivity;
import com.example.jile.R;
import com.example.jile.Setting.ThemeSettingActivity;
import com.example.jile.Util.MoneyWatcher;
import com.example.jile.Util.TextUtil;
import com.example.jile.Util.ToastUtil;
import com.xuexiang.xui.XUI;
import com.xuexiang.xui.widget.picker.widget.TimePickerView;
import com.xuexiang.xui.widget.picker.widget.builder.TimePickerBuilder;
import com.xuexiang.xui.widget.picker.widget.listener.OnTimeSelectListener;
import com.xuexiang.xui.widget.tabbar.EasyIndicator;
import com.xuexiang.xui.widget.tabbar.TabSegment;
import com.xuexiang.xui.widget.toast.XToast;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class NewBIllActivity extends AppCompatActivity {
    private Button btnFirstClass,btnSecondClass,btnSetDate,btnSelectAccount,btnSelectMember,btnSetStore;
    private ImageButton btnBack,btnSave,btnDelete;
    private List<String> firstClassItems,accountItems,memberItems,storeItems;
    private List<List<String>> secondClassItems;
    private String type= Constants.COST;
    private OnClick onClick = new OnClick();
    private EditText etMoneyNumber,etNote;
    private OptionsPickerView<String> pvOptions,pvOptions2,pvOptions3,pvOptions4;
    private String uuid;
    private BigDecimal oldMoneyNum;
    private int initSelection1,initSelection2;
    private Bill bill;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //XUI.initTheme(this);
        ThemeSettingActivity.setActivityTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_bill);
        XUI.initTheme(this);
        if(getIntent().getExtras()==null){
        }else{
            uuid = getIntent().getExtras().getString("uuid");
        }
        try {
            init();
        } catch (ParseException e) {
            e.printStackTrace();
        }
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
        btnDelete = findViewById(R.id.btnDelete);
    }

    //点击编辑框以外的地方关闭输入法
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public  boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = { 0, 0 };
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);

            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * 一系列点击事件的实现
     * */
    private class OnClick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btnSetDate:
                    Calendar calendar = Calendar.getInstance();
                    try {
                        calendar.setTime(Constants.DATE_FORMAT_COMPLEX.parse(btnSetDate.getText().toString()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    TimePickerView pvTime = new TimePickerBuilder(NewBIllActivity.this, new OnTimeSelectListener() {
                        @Override
                        public void onTimeSelected(Date date, View v) {
                            btnSetDate.setText(Constants.DATE_FORMAT_COMPLEX.format(date));
                        }
                    }).setType(true, true, true, true, true, false)//分别对应年月日时分秒，默认全部显示
                       .setTitleText("时间选择")
                       .setDate(calendar)
                       .build();
                    pvTime.show();
                    break;
                case R.id.btnBack:
                    finish();
                    break;
                case R.id.btnSave:
                    if(btnFirstClass.getText().toString().equals("")|| btnSecondClass.getText().toString().equals("")) {
                        XToast.info(NewBIllActivity.this,"请选择正确的分类").show();
                    } else if(btnFirstClass.getText().toString().equals(btnSecondClass.getText().toString())){
                        XToast.info(NewBIllActivity.this,"转入账户和转出账户不能相同！").show();
                    }else if(etMoneyNumber.getText().toString().equals("")){
                         ToastUtil.showShortToast(NewBIllActivity.this,"金额不能为空");
                    }else{
                        BigDecimal num = new BigDecimal(etMoneyNumber.getText().toString());
                        if(num.compareTo(new BigDecimal("0.01"))<0){
                            XToast.info(NewBIllActivity.this,"请输入更大的金额").show();
                        }else if(num.compareTo(new BigDecimal("1000000000"))>0){
                            XToast.info(NewBIllActivity.this,"请输入更小的金额").show();
                        }else if(uuid==null){
                            try {addNewBillToDB(createNewBill());finish();}catch(ParseException e){e.printStackTrace();}
                        }else{
                            try {updateBillInDB(createNewBill());finish();}catch(ParseException e){e.printStackTrace();}
                        }
                    }
                    break;
                case R.id.btnFirstClass:
                case R.id.btnSecondClass:
                    if(firstClassItems.size()==0){XToast.info(NewBIllActivity.this,"无可用一级分类").show();}
                    else{
                        pvOptions = new OptionsPickerBuilder(NewBIllActivity.this, new OnOptionsSelectListener() {
                            @Override
                            public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                                //返回的分别是三个级别的选中位置
                                if(firstClassItems.size()==0){
                                    XToast.info(NewBIllActivity.this,"一级分类为空，无法记账").show();
                                    btnFirstClass.setText("");
                                }else if(secondClassItems.get(options1).size()==0){
                                    XToast.info(NewBIllActivity.this,"二级分类为空，无法选择此分类").show();
                                    btnFirstClass.setText(firstClassItems.get(options1));
                                    btnSecondClass.setText("");
                                }else{
                                    btnFirstClass.setText(firstClassItems.get(options1));
                                    btnSecondClass.setText(secondClassItems.get(options1).get(option2));
                                }
                            }
                        })      .setSelectOptions(initSelection1,initSelection2)
                                .build();
                        pvOptions.setPicker(firstClassItems,secondClassItems);
                        pvOptions.show();
                    }
                    break;
                case R.id.btnSelectAccount:
                    pvOptions2 = new OptionsPickerBuilder(NewBIllActivity.this, new OnOptionsSelectListener() {
                        @Override
                        public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                            //返回的分别是三个级别的选中位置
                            btnSelectAccount.setText(accountItems.get(options1));
                        }
                    }).build();
                    pvOptions2.setPicker(accountItems);
                    pvOptions2.show();
                    break;
                case R.id.btnSelectMember:
                    pvOptions3 = new OptionsPickerBuilder(NewBIllActivity.this, new OnOptionsSelectListener() {
                        @Override
                        public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                            //返回的分别是三个级别的选中位置
                            btnSelectMember.setText(memberItems.get(options1));
                        }
                    }).build();
                    pvOptions3.setPicker(memberItems);
                    pvOptions3.show();
                    break;
                case R.id.btnSetStore:
                    pvOptions4 = new OptionsPickerBuilder(NewBIllActivity.this, new OnOptionsSelectListener() {
                        @Override
                        public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                            btnSetStore.setText(storeItems.get(options1));
                        }
                    }).build();
                    pvOptions4.setPicker(storeItems);
                    pvOptions4.show();
                    break;
                case R.id.btnDelete:
                    Bill bill = LogoActivity.billDao.querybyskey("uuid", uuid).get(0);
                    deleteBillInDB(bill);
                    finish();
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
        btnDelete.setOnClickListener(onClick);
        etMoneyNumber.addTextChangedListener(new MoneyWatcher());
    }

    private void getItems(){
        firstClassItems = new LinkedList<>();
        for(FirstClass f:LogoActivity.firstClassDao.query()){
            if(f.getType().equals(type)){
                firstClassItems.add(f.getName());
            }
        }
        secondClassItems = new LinkedList<>();
        for(String first:firstClassItems){
            List<String> tempList = new LinkedList<>();
            for(SecondClass s:LogoActivity.secondClassDao.querybyskey("firstclass",first)){
                tempList.add(s.getName());
            }
            secondClassItems.add(tempList);
        }
        List<Account> al = LogoActivity.accountDao.query();
        accountItems = new LinkedList<>();
        for(Account a:al){
            accountItems.add(a.getSelfname());
        }
        memberItems = new LinkedList<>();
        for(Mem m:LogoActivity.memDao.query()){
            memberItems.add(m.getName());
        }
        storeItems = new LinkedList<>();
        for(Store s:LogoActivity.storeDao.query()){
            storeItems.add(s.getName());
        }
    }

    /**
     * 向数据库添加新账单，同时更新Account的bablnce
     * */
    private void addNewBillToDB(Bill bill){
        LogoActivity.billDao.insert(bill);
        if(bill.getType().equals(Constants.INCOME)||bill.getType().equals(Constants.COST)){
            List<Account> tempaccount =LogoActivity.accountDao.querybyskey("selfname",bill.getAccountname());
            tempaccount.get(0).setBalance(tempaccount.get(0).getBalance().add(bill.getNum()));
            LogoActivity.accountDao.update(tempaccount.get(0));
        }
        else if(bill.getType().equals(Constants.TRANSFER)){
            List<Account> outaccount =LogoActivity.accountDao.querybyskey("selfname",bill.getFirst());
            outaccount.get(0).setBalance(outaccount.get(0).getBalance().subtract(bill.getNum()));
            LogoActivity.accountDao.update(outaccount.get(0));
            List<Account> inaccount =LogoActivity.accountDao.querybyskey("selfname",bill.getSecond());
            inaccount.get(0).setBalance(inaccount.get(0).getBalance().add(bill.getNum()));
            LogoActivity.accountDao.update(inaccount.get(0));
        }
    }

    /**
     * 向数据库更新旧账单，同时更新Account的bablnce
     * */
    private void updateBillInDB(Bill bill){
        LogoActivity.billDao.update(bill);
        if(bill.getType().equals(Constants.INCOME)||bill.getType().equals(Constants.COST)){
            List<Account> tempaccount =LogoActivity.accountDao.querybyskey("selfname",bill.getAccountname());
            tempaccount.get(0).setBalance(tempaccount.get(0).getBalance().add(bill.getNum()).subtract(oldMoneyNum));
            LogoActivity.accountDao.update(tempaccount.get(0));
        }
        else if(bill.getType().equals(Constants.TRANSFER)){
            List<Account> outaccount =LogoActivity.accountDao.querybyskey("selfname",bill.getFirst());
            outaccount.get(0).setBalance(outaccount.get(0).getBalance().subtract(bill.getNum()).add(oldMoneyNum));
            LogoActivity.accountDao.update(outaccount.get(0));
            List<Account> inaccount =LogoActivity.accountDao.querybyskey("selfname",bill.getSecond());
            inaccount.get(0).setBalance(inaccount.get(0).getBalance().add(bill.getNum()).subtract(oldMoneyNum));
            LogoActivity.accountDao.update(inaccount.get(0));
        }
    }

    /**
     * 向数据库删除账单，同时更新Account的bablnce
     * */
    public static void deleteBillInDB(Bill bill){
        LogoActivity.billDao.delete(bill);
        if(bill.getType().equals(Constants.INCOME)||bill.getType().equals(Constants.COST)){
            List<Account> tempaccount =LogoActivity.accountDao.querybyskey("selfname",bill.getAccountname());
            tempaccount.get(0).setBalance(tempaccount.get(0).getBalance().subtract(bill.getNum()));
            LogoActivity.accountDao.update(tempaccount.get(0));
        }
        else if(bill.getType().equals(Constants.TRANSFER)){
            List<Account> outaccount =LogoActivity.accountDao.querybyskey("selfname",bill.getFirst());
            outaccount.get(0).setBalance(outaccount.get(0).getBalance().add(bill.getNum()));
            LogoActivity.accountDao.update(outaccount.get(0));
            List<Account> inaccount =LogoActivity.accountDao.querybyskey("selfname",bill.getSecond());
            inaccount.get(0).setBalance(inaccount.get(0).getBalance().subtract(bill.getNum()));
            LogoActivity.accountDao.update(inaccount.get(0));
        }
    }

    /**
     * 根据Button控件和EditText控件的值构造bill，有转账和非转账两种构造方法
     * */
    private Bill createNewBill() throws ParseException {
        BigDecimal num = new BigDecimal(TextUtil.simplifyMoney(etMoneyNumber.getText().toString()));
        if(type.equals(Constants.COST)) num = num.abs().negate();
        if(uuid!=null){
            if(!type.equals(Constants.TRANSFER)){
                return new Bill(uuid,type,num,
                        btnSelectAccount.getText().toString(),btnFirstClass.getText().toString(),btnSecondClass.getText().toString(),
                        btnSelectMember.getText().toString(),btnSetStore.getText().toString(),
                        Constants.DATE_FORMAT_SIMPLE.format(Constants.DATE_FORMAT_COMPLEX.parse(btnSetDate.getText().toString())),
                        LogoActivity.iconDao.querybyskey("name",btnFirstClass.getText().toString()).get(0).getIconId(),etNote.getText().toString());
            }else{
                return new Bill(uuid,type,num,
                        "",btnFirstClass.getText().toString(),btnSecondClass.getText().toString(),
                        btnSelectMember.getText().toString(),btnSetStore.getText().toString(),
                        Constants.DATE_FORMAT_SIMPLE.format(Constants.DATE_FORMAT_COMPLEX.parse(btnSetDate.getText().toString())),
                        R.drawable.ic_transfer,etNote.getText().toString());
            }
        }else{
            if(!type.equals(Constants.TRANSFER)){
                return new Bill(UUID.randomUUID().toString(),type,num,
                        btnSelectAccount.getText().toString(),btnFirstClass.getText().toString(),btnSecondClass.getText().toString(),
                        btnSelectMember.getText().toString(),btnSetStore.getText().toString(),
                        Constants.DATE_FORMAT_SIMPLE.format(Constants.DATE_FORMAT_COMPLEX.parse(btnSetDate.getText().toString())),
                        LogoActivity.iconDao.querybyskey("name",btnFirstClass.getText().toString()).get(0).getIconId(),etNote.getText().toString());
            }else{
                return new Bill(UUID.randomUUID().toString(),type,num,
                        "",btnFirstClass.getText().toString(),btnSecondClass.getText().toString(),
                        btnSelectMember.getText().toString(),btnSetStore.getText().toString(),
                        Constants.DATE_FORMAT_SIMPLE.format(Constants.DATE_FORMAT_COMPLEX.parse(btnSetDate.getText().toString())),
                        R.drawable.ic_transfer,etNote.getText().toString());
            }
        }

    }

    private void getAccounts(){
        List<Account> allAccount = LogoActivity.accountDao.query();
        List<String> allAccountName = new ArrayList<>();
        for(Account a:allAccount){
            allAccountName.add(a.getSelfname());
        }
        firstClassItems.clear();
        secondClassItems.clear();
        for(String s:allAccountName){
            List<String> temp = new LinkedList<>(allAccountName);
            firstClassItems.add(s);
            secondClassItems.add(temp);
        }
    }

    private void setEasyIndicator(){

        TabSegment tabSegment = findViewById(R.id.tsType);
        String[] pages = new String[]{"支出","收入","转账"};
        for (String page : pages) {
            tabSegment.addTab(new TabSegment.Tab(page));
        }
        tabSegment.selectTab(TabSegment.MODE_FIXED);
        tabSegment.notifyDataChanged();
        tabSegment.setOnTabClickListener((position) -> {
            boolean needToUpdate;
            switch (position) {
                case 0:
                    needToUpdate = type.equals(Constants.TRANSFER);
                    type=Constants.COST;
                    getItems();
                    if(needToUpdate){
                        updateUIToNormalType();
                    }
                    updateClassItems();
                    break;
                case 1:
                    needToUpdate = type.equals(Constants.TRANSFER);
                    type=Constants.INCOME;
                    getItems();
                    if(needToUpdate){
                        updateUIToNormalType();
                    }
                    updateClassItems();
                    break;
                case 2:
                    needToUpdate = !type.equals(Constants.TRANSFER);
                    type=Constants.TRANSFER;
                    getAccounts();
                    if(needToUpdate){
                        updateUIToTransferType();
                    }
                    updateClassItems();
                    break;
            }
        });
    }

    private void updateClassItems(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(firstClassItems.size()==0){
                    //XToast.info(NewBIllActivity.this,"一级分类为空，无法记账").show();
                    btnFirstClass.setText("");
                    btnSecondClass.setText("");
                }else if(secondClassItems.get(0).size()==0){
                    //XToast.info(NewBIllActivity.this,"二级分类为空，无法选择此分类").show();
                    btnFirstClass.setText(firstClassItems.get(0));
                    btnSecondClass.setText("");
                }else{
                    btnFirstClass.setText(firstClassItems.get(0));
                    btnSecondClass.setText(secondClassItems.get(0).get(0));
                }
            }
        }).start();
    }

    private void updateUIToTransferType(){
        TextView tvClass = findViewById(R.id.tvClass);
        tvClass.setText("账户");
        LinearLayout ll_2 = findViewById(R.id.ll_2);
        ll_2.setVisibility(View.GONE);
    }

    private void updateUIToNormalType(){
        TextView tvClass = findViewById(R.id.tvClass);
        tvClass.setText("分类");
        LinearLayout ll_2 = findViewById(R.id.ll_2);
        ll_2.setVisibility(View.VISIBLE);
        Button btnSelectAccount =  ll_2.findViewById(R.id.btnSelectAccount);
        btnSelectAccount.setText(accountItems.get(0));
    }

    /**
     * 初始化activity，根据是否有bundle决定页面是否有初始值以及点击btnSave的动作
     * */
    @SuppressLint("ResourceAsColor")
    private void init() throws ParseException {
        if(uuid!=null){
            bill = LogoActivity.billDao.querybyskey("uuid",uuid).get(0);
            type = bill.getType();
        }
        getItems();
        getComponents();
        setListener(onClick);
        setEasyIndicator();
        TabSegment tabSegment = findViewById(R.id.tsType);
        tabSegment.selectTab(0,true,false);
        if(firstClassItems.size()==0){
            XToast.info(this,"无可用分类").show();
        }
        if(uuid==null){
            btnFirstClass.setText(firstClassItems.get(0));
            btnSecondClass.setText(secondClassItems.get(0).get(0));
            btnSelectAccount.setText(accountItems.get(0));
            btnSelectMember.setText(memberItems.get(0));
            btnSetStore.setText(storeItems.get(0));
            btnSetDate.setText(Constants.DATE_FORMAT_COMPLEX.format(new Date(System.currentTimeMillis())));
            btnDelete.setVisibility(View.GONE);
        }else{
            if(type.equals(Constants.TRANSFER)){ getAccounts();}
            oldMoneyNum = bill.getNum();
            btnFirstClass.setText(bill.getFirst());
            btnSecondClass.setText(bill.getSecond());
            btnSelectAccount.setText(bill.getAccountname());
            initSelection1 = firstClassItems.indexOf(bill.getFirst());
            initSelection2 = secondClassItems.get(initSelection1).indexOf(bill.getSecond());
            btnSelectMember.setText(bill.getMember());
            btnSetStore.setText(bill.getStore());
            btnSetDate.setText(Constants.DATE_FORMAT_COMPLEX.format(Constants.DATE_FORMAT_SIMPLE.parse(bill.getDate())));
            TextView etMoneyNumber = findViewById(R.id.etMoneyNumber);
            etMoneyNumber.setText(bill.getNum().toPlainString());
            tabSegment.reset();
            if(bill.getType().equals(Constants.COST)){tabSegment.addTab(new TabSegment.Tab("支出"));type=Constants.COST;}
            if(bill.getType().equals(Constants.INCOME)){tabSegment.addTab(new TabSegment.Tab("收入"));type=Constants.INCOME;}
            if(bill.getType().equals(Constants.TRANSFER)){tabSegment.addTab(new TabSegment.Tab("转账"));updateUIToTransferType();type=Constants.TRANSFER;}
            tabSegment.selectTab(0,true,false);
            tabSegment.selectTab(TabSegment.MODE_FIXED);
            tabSegment.notifyDataChanged();
        }
    }

    /**
     *  获得带参数跳转至此Activity的intent
     * @param context 上下文
     * @param uuid 需要跳转进来的bill的uuid
     * */
    public static Intent startThisActivity(Context context,String uuid){
        Bundle bundle = new Bundle();
        bundle.putString("uuid",uuid);
        Intent intent = new Intent(context,NewBIllActivity.class);
        intent.putExtras(bundle);
        return intent;
    }
}