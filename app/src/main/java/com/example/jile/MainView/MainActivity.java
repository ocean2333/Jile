package com.example.jile.MainView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jile.Account.AccountActivity;
import com.example.jile.Bean.User;
import com.example.jile.Constant.Constants;
import com.example.jile.Database.Dao.AccountDao;
import com.example.jile.Database.Dao.BillDao;
import com.example.jile.Database.Dao.FirstClassDao;
import com.example.jile.Database.Dao.MemDao;
import com.example.jile.Database.Dao.SecondClassDao;
import com.example.jile.Database.Dao.StoreDao;
import com.example.jile.Detail.DeatilActivity;
import com.example.jile.Graph.GraphActivity;
import com.example.jile.Bean.Bill;
import com.example.jile.LogoActivity;
import com.example.jile.New.NewBIllActivity;
import com.example.jile.R;
import com.example.jile.Setting.SettingActivity;
import com.example.jile.Util.DateUtil;
import com.example.jile.Util.TextUtil;
import com.xuexiang.xui.XUI;
import com.xuexiang.xui.utils.WidgetUtils;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static com.xuexiang.xui.XUI.getContext;

public class MainActivity extends AppCompatActivity {
    private Button btnToDetail,btnAccount,btnDetail,btnNew,btnGraph,btnSetting,btnModifyBudget;
    private String month,cost,income,budget,todaycost,todayincome;
    private BigDecimal budgetInput;
    private SwipeRefreshLayout swipeRefreshLayout;
    private static Bill[] bills;
    private SwipeRecyclerView recyclerView;
    /**
     * 获得登录信息并初始化Dao 初始化界面
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        XUI.initTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String user = LogoActivity.sp.getString("loginUser",null);
        if(user!=null){
            LogoActivity.accountDao = new AccountDao(this,user);
            LogoActivity.billDao = new BillDao(this,user);
            LogoActivity.memDao = new MemDao(this,user);
            LogoActivity.firstClassDao = new FirstClassDao(this,user);
            LogoActivity.storeDao = new StoreDao(this,user);
            LogoActivity.secondClassDao = new SecondClassDao(this,user);
        }else{
            Toast.makeText(this,"error in getLoginUser",Toast.LENGTH_SHORT).show();
        }
        try {
            init();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从其他界面切换回来时更新界面元素
     * */
    @Override
    protected void onResume() {
        super.onResume();
        try {
            updateView();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从billDao中获得所有bill并按日期由近至远排序
     * */
    private Bill[] getBill(Bill[] bill){
        bill = LogoActivity.billDao.query().toArray(new Bill[LogoActivity.billDao.query().size()]);
        Arrays.sort(bill, new Comparator<Bill>() {
            @Override
            public int compare(Bill o1, Bill o2) {
                return o2.getDate().compareTo(o1.getDate());
            }
        });
        return bill;
    }

    /**
     * 获得组件
     * */
    private void getComponents(){
        btnToDetail = findViewById(R.id.btnToDetail);
        btnDetail = findViewById(R.id.btnDetail);
        btnAccount = findViewById(R.id.btnAccount);
        btnNew = findViewById(R.id.btnNew);
        btnGraph = findViewById(R.id.btnGraph);
        btnSetting = findViewById(R.id.btnSetting);
        btnModifyBudget = findViewById(R.id.btnModifyBudget);
        swipeRefreshLayout = findViewById(R.id.refresh_layout);
    }

    /**
     * 设置listener
     * */
    private void setBtnListener(OnClick onClick){
        btnAccount.setOnClickListener(onClick);
        btnDetail.setOnClickListener(onClick);
        btnToDetail.setOnClickListener(onClick);
        btnNew.setOnClickListener(onClick);
        btnSetting.setOnClickListener(onClick);
        btnGraph.setOnClickListener(onClick);
        btnModifyBudget.setOnClickListener(onClick);
    }

    /**
     * OnClickListener
     * */
    private class OnClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent;
            switch (v.getId()){
                case R.id.btnAccount:
                    intent = new Intent(MainActivity.this, AccountActivity.class);
                    startActivity(intent);
                    break;
                case R.id.btnToDetail:
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
                    LogoActivity.sp.edit().putString("loginUser",null).apply();
                    intent = new Intent(MainActivity.this, SettingActivity.class);
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

    /**
     * 禁止使用返回键返回并替换为再按一次退出
     */
    private long exitTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-exitTime) > 2000){
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                //finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 修改预算，展示dialog
     * */
    private void showExitDialog(){
        final EditText edt = new EditText(this);
        edt.setMinLines(1);
        edt.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_VARIATION_NORMAL);
        new AlertDialog.Builder(this)
                .setTitle("请输入预算")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setView(edt)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        String b = edt.getText().toString();
                        setBudgetThisMonth(new BigDecimal(b));
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                TextView tvBudget = findViewById(R.id.tvBudgetNum);
                                tvBudget.setText(edt.getText().toString());
                            }
                        }).start();
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }

    // TODO
    private static Bill[] getbillbytime(Bill[] bill,Date startDate,Date endDate) throws ParseException {
        Bill[] tempBill=new Bill[bill.length];
        List<Bill> lb = new ArrayList<>();
        int i=0;
        for(Bill temp:bill){
            java.text.SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd-hh-mm");
            Date date = simpleDateFormat.parse(temp.getDate());
            if (date.before(endDate) && date.after(startDate)){
                lb.add(temp);
            }
        }
        return lb.toArray(new Bill[0]);
    }

    /**
     * 获得月收入支出，日收入支出
     * */
    private void getCostAndIncome(Bill[] bill,String type) throws ParseException {
        BigDecimal tempcost=new BigDecimal("0");
        BigDecimal tempincome=new BigDecimal("0");
        Calendar rightNow = Calendar.getInstance();
        Date endtime = rightNow.getTime();
        if(type.equals("month")){
            rightNow.add(Calendar.MONTH,-1);
        }
        else if(type.equals("day")){
            rightNow.add(Calendar.DAY_OF_YEAR,-1);
        }
        Date starttime = rightNow.getTime();
        Bill[] tempBill = getbillbytime(bill,starttime,endtime);
        for(Bill i:tempBill){
            if (i.getType().equals(Constants.COST)){
                tempcost=tempcost.add(i.getNum());
            }
            else if(i.getType().equals(Constants.INCOME)){
                tempincome=tempincome.add(i.getNum());
            }
        }
        if(type.equals("month")){
            cost = tempcost.toString();
            income = tempincome.toString();
        }
        else if(type.equals("day")){
            todaycost = tempcost.toString();
            todayincome = tempincome.toString();
        }
    }

    /**
     * 获得和设定预算
     * */
    private void getBudgetThisMonth(){
        String s = LogoActivity.sp.getString("loginUser","");
        List<User> list = LogoActivity.userDao.querybyskey("name",s);
        budget = list.get(0).getBudget();
    }
    private void setBudgetThisMonth(BigDecimal budget){
        User user = LogoActivity.userDao.querybyskey("name",LogoActivity.sp.getString("loginUser","")).get(0);
        user.setBudget(budget.toPlainString());
        LogoActivity.userDao.update(user);
    }

    /**
     * 向recycleView提供数据
     * */
    private List<Bill> getFiveMostRecentBillEachTime(int t){
        int size=7;
        int count=0;
        hasMore=true;
        List<Bill> fiveMostRecentBill = new LinkedList<>();
        while(t*size+count<bills.length&&count<size){
            fiveMostRecentBill.add(bills[t*size+count]);
            if(t*size+count+1==bills.length) hasMore=false;
            count+=1;
        }
        isEmpty = fiveMostRecentBill.isEmpty();
        Log.d("", "getFiveMostRecentBillEachTime: "+t+hasMore+isEmpty);
        return fiveMostRecentBill;
    }

    private SwipeRefreshLayout.OnRefreshListener mRefreshListener = this::refreshData;
    private boolean hasMore,isEmpty;
    private int mIndex=0;
    private Handler mHandler = new Handler();
    private BillRecyclerAdapter mAdapter;
    MaterialLoadMoreView mLoadMoreView;
    /**
     * 启动下拉刷新
     * */
    private void autoRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        refreshData();
    }
    /**
     * 下拉刷新
     * */
    private void refreshData() {
        mIndex = 0;
        recyclerView.loadMoreFinish(isEmpty, true);
        mHandler.postDelayed(() -> {
            mAdapter.refresh(getFiveMostRecentBillEachTime(mIndex));
            if (swipeRefreshLayout != null) {
                swipeRefreshLayout.setRefreshing(false);
            }
            enableLoadMore();
        }, 1000);
    }
    private boolean mEnableLoadMore;
    /**
     * 确保有数据加载了才开启加载更多
     */
    private void enableLoadMore() {
        if (recyclerView != null && !mEnableLoadMore) {
            mEnableLoadMore = true;
            useMaterialLoadMore();
            // 加载更多的监听。
            recyclerView.setLoadMoreListener(mLoadMoreListener);
            recyclerView.loadMoreFinish(isEmpty, hasMore);
        }
    }

    private void useMaterialLoadMore() {
        if (mLoadMoreView == null) {
            mLoadMoreView = new MaterialLoadMoreView(getContext());
        }
        recyclerView.addFooterView(mLoadMoreView);
        recyclerView.setLoadMoreView(mLoadMoreView);
    }
    /**
     * 确保有数据加载了才开启加载更多
     */
    private void disEnableLoadMore() {
        if (recyclerView != null && mEnableLoadMore) {
            mEnableLoadMore = false;
            disableMaterialLoadMore();
            // 加载更多的监听。
            recyclerView.setLoadMoreListener(null);
            recyclerView.loadMoreFinish(false, false);
        }
    }

    private void disableMaterialLoadMore() {
        if (mLoadMoreView == null) {
            mLoadMoreView = new MaterialLoadMoreView(getContext());
        }
        recyclerView.removeFooterView(mLoadMoreView);
        recyclerView.setLoadMoreView(null);
    }

    /**
     * 加载更多。
     */
    private SwipeRecyclerView.LoadMoreListener mLoadMoreListener = new SwipeRecyclerView.LoadMoreListener() {
        @Override
        public void onLoadMore() {
            mIndex ++;
            mHandler.postDelayed(() -> {
                mAdapter.loadMore(getFiveMostRecentBillEachTime(mIndex));
                // 数据完更多数据，一定要掉用这个方法。
                // 第一个参数：表示此次数据是否为空。
                // 第二个参数：表示是否还有更多数据。
                if (recyclerView != null) {
                    recyclerView.loadMoreFinish(isEmpty, hasMore);
                }
                if (mIndex >= 1000) {
                    disEnableLoadMore();
                }
                // 如果加载失败调用下面的方法，传入errorCode和errorMessage。
                // errorCode随便传，你自定义LoadMoreView时可以根据errorCode判断错误类型。
                // errorMessage是会显示到loadMoreView上的，用户可以看到。
                // mRecyclerView.loadMoreError(0, "请求网络失败");
            }, 1000);
        }
    };

    /**
     * 初始化
     * */
    private void init() throws ParseException {
        getComponents();
        OnClick onClick = new OnClick();
        setBtnListener(onClick);
        updateView();
    }

    /**
     * 更新ui
     * */
    private void updateView() throws ParseException {
        bills = getBill(bills);
        TextView tvMonth = findViewById(R.id.tvMonth);
        TextView tvCost = findViewById(R.id.tvCost);
        TextView tvIncome = findViewById(R.id.tvIncome);
        TextView tvBudget = findViewById(R.id.tvBudgetNum);
        TextView tvIncomeToday = findViewById(R.id.tvIncomeToday);
        TextView tvCostToday = findViewById(R.id.tvCostToday);
        recyclerView = findViewById(R.id.recycler_view);
        getBudgetThisMonth();
        tvBudget.setText(budget);
        getCostAndIncome(bills,"month");
        tvCost.setText(TextUtil.simplifyMoney(cost));
        tvIncome.setText(TextUtil.simplifyMoney(income));
        getCostAndIncome(bills,"day");
        tvIncomeToday.setText(todayincome);
        tvCostToday.setText(todaycost);
        tvMonth.setText(DateUtil.getMonth());
        WidgetUtils.initRecyclerView(recyclerView);
        recyclerView.setAdapter(mAdapter = new BillRecyclerAdapter(getFiveMostRecentBillEachTime(mIndex),R.layout.adapter_bill,(v,p)->{
            Bundle bundle = new Bundle();
            TextView tvUuid = v.findViewById(R.id.tvUuid);
            bundle.putString("uuid",tvUuid.getText().toString());
            Intent intent = new Intent(this, NewBIllActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }));
        swipeRefreshLayout.setColorSchemeColors(0xff0099cc, 0xffff4444, 0xff669900, 0xffaa66cc, 0xffff8800);
        swipeRefreshLayout.setOnRefreshListener(mRefreshListener);
        autoRefresh();
    }
}



