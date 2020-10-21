package com.example.jile.Graph;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jile.Constant.Constants;
import com.example.jile.Detail.DeatilActivity;
import com.example.jile.Detail.ExpandableListAdapter;
import com.example.jile.R;
import com.example.jile.Util.ToastUtil;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.xuexiang.xui.XUI;
import com.xuexiang.xui.utils.ColorUtils;
import com.xuexiang.xui.utils.WidgetUtils;
import com.xuexiang.xui.widget.alpha.XUIAlphaTextView;
import com.xuexiang.xui.widget.layout.ExpandableLayout;
import com.xuexiang.xui.widget.picker.widget.TimePickerView;
import com.xuexiang.xui.widget.picker.widget.builder.TimePickerBuilder;
import com.xuexiang.xui.widget.picker.widget.listener.OnTimeSelectListener;
import com.xuexiang.xui.widget.tabbar.EasyIndicator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import java.util.List;

import static com.example.jile.Constant.Constants.COST;
import static com.example.jile.Constant.Constants.INCOME;

public class GraphActivity extends AppCompatActivity implements OnChartValueSelectedListener {

    private EasyIndicator mGraph ;
    private View view1,view2;
    private List<View> viewList;//view数组
    private PieChart mPieChart;
    private List<PieEntry> mGraphData ;
    private RecyclerView recyclerView;
    public static String searchType ,billtype;
    private ExpandableLayout expandableLayout1;
    private XUIAlphaTextView expand_button;
    public static Date startDate,endDate;
    private TextView textView;
    private GraphActivity.OnClick onClick = new GraphActivity.OnClick();
    private Button btnback,btnCostGraphByKind,btnCostGraphByAccount,btnIncomeGraphByKind,
            btnIncomeGraphByAccount,btnMonthIncome,btnMonthCost,btnBillDetail,
            btnSetStartDate,btnSetEndDate;
    private BarListAdapter barListAdapter;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        XUI.initTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphpie);
        mGraphData=getpiebill(searchType,billtype,startDate,endDate);
        initViewPager();
        getComponents();
        initChartStyle();
        if(!mGraphData.isEmpty()){
            initPieChart();
            initBarChart();
        }
        else{
            ToastUtil.showShortToast(this,"无符合目标数据");
        }
        expandableLayout1.setOnExpansionChangedListener((expansion, state) -> Log.d("expandableLayout1", "State: " + state));
        setListener(onClick);
        btnBillDetail.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                update();
                return true;
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void initBarChart() {
        barListAdapter = new BarListAdapter(recyclerView,mGraphData);
        WidgetUtils.initRecyclerView(recyclerView);
        recyclerView.setAdapter(barListAdapter);
        textView.setText("总计\n  "+getTotal(mGraphData));
    }

    private void initViewPager(){
        ViewPager mViewPager = (ViewPager) findViewById(R.id.view_pager);
        LayoutInflater inflater=getLayoutInflater();
        view1 = inflater.inflate(R.layout.fragment_pie, null);
        view2 = inflater.inflate(R.layout.fragment_bar,null);
        // 将要分页显示的View装入数组中
        viewList = new ArrayList<View>();
        viewList.add(view1);
        viewList.add(view2);
        PagerAdapter pagerAdapter = new PagerAdapter() {
            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                // TODO Auto-generated method stub
                return arg0 == arg1;
            }

            @Override
            public int getCount() {
                // TODO Auto-generated method stub
                return viewList.size();
            }

            @Override
            public void destroyItem(ViewGroup container, int position,
                                    Object object) {
                // TODO Auto-generated method stub
                container.removeView(viewList.get(position));
            }
            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                // TODO Auto-generated method stub
                container.addView(viewList.get(position));
                return viewList.get(position);
            }
        };
        mViewPager.setAdapter(pagerAdapter);
    }

    private void getComponents() {
        btnback = findViewById(R.id.btnBack);
        expandableLayout1 = findViewById(R.id.expandable_layout_1);
        expand_button = findViewById(R.id.expand_button);
        btnCostGraphByKind=findViewById(R.id.btnCostGraphByKind);
        btnCostGraphByAccount=findViewById(R.id.btnCostGraphByAccount);
        btnIncomeGraphByKind=findViewById(R.id.btnIncomeGraphByKind);
        btnIncomeGraphByAccount=findViewById(R.id.btnIncomeGraphByAccount);
        btnMonthIncome=findViewById(R.id.btnMonthIncome);
        btnMonthCost=findViewById(R.id.btnMonthCost);
        btnSetStartDate=findViewById(R.id.btnSetStartDate);
        btnSetEndDate=findViewById(R.id.btnSetEndDate);

        mPieChart=view1.findViewById(R.id.pie_chart);
        btnBillDetail=view1.findViewById(R.id.btnBillDetail);

        textView=view2.findViewById(R.id.textView);
        recyclerView= view2.findViewById(R.id.bar_recycler_view);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onValueSelected(Entry e, Highlight h) {
        PieEntry pe = (PieEntry)e;
        searchType = pe.getLabel();
        btnBillDetail.setText(pe.getLabel()+" "+pe.getValue()+" >");
    }

    @Override
    public void onNothingSelected() {
        btnBillDetail.setText("");
    }

    private class OnClick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date(System.currentTimeMillis()));
            switch (v.getId()){
                case R.id.btnBack:
                    finish();
                    break;
                case R.id.expand_button:
                    if (expandableLayout1.isExpanded()) {
                        expandableLayout1.collapse();
                    } else {
                        expandableLayout1.expand();
                    }
                    break;
                case R.id.btnCostGraphByKind:
                    searchType = "FirstClass";
                    billtype = COST;
                    expand_button.setText(btnCostGraphByKind.getText());
                    update();
                    break;
                case R.id.btnCostGraphByAccount:
                    searchType = "Account";
                    billtype = INCOME;
                    expand_button.setText(btnCostGraphByAccount.getText());
                    update();
                    break;
                case R.id.btnIncomeGraphByAccount:
                    searchType = "Account";
                    billtype = INCOME;
                    expand_button.setText(btnIncomeGraphByAccount.getText());
                    update();
                    break;
                case R.id.btnIncomeGraphByKind:
                    searchType = "FirstClass";
                    billtype = INCOME;
                    expand_button.setText(btnIncomeGraphByKind.getText());
                    update();
                    break;
                case R.id.btnMonthIncome:
                    searchType = "month";
                    billtype = INCOME;
                    expand_button.setText(btnMonthIncome.getText());
                    update();
                    break;
                case R.id.btnMonthCost:
                    searchType = "month";
                    billtype = COST;
                    expand_button.setText(btnMonthCost.getText());
                    update();
                    break;
                case R.id.btnSetEndDate:
                    TimePickerView mDateEndPicker = new TimePickerBuilder(GraphActivity.this, new OnTimeSelectListener() {
                        @Override
                        public void onTimeSelected(Date date, View v) {
                            endDate = date;
                            btnSetEndDate.setText(Constants.DATE_FORMAT_YEAR_MONTH_DAY.format(date));
                            update();
                        }
                    })
                            .setType(true,true,true,false,false,false)
                            .setDate(calendar)
                            .setTitleText("结束日期选择")
                            .build();
                    mDateEndPicker.show();
                    break;
                case R.id.btnSetStartDate:
                    TimePickerView mDateStartPicker = new TimePickerBuilder(GraphActivity.this, new OnTimeSelectListener() {
                        @Override
                        public void onTimeSelected(Date date, View v) {
                            endDate = date;
                            btnSetStartDate.setText(Constants.DATE_FORMAT_YEAR_MONTH_DAY.format(date));
                            update();
                        }
                    })
                            .setType(true,true,true,false,false,false)
                            .setDate(calendar)
                            .setTitleText("结束日期选择")
                            .build();
                    mDateStartPicker.show();
                    break;
            }
        }
    }

    public void update() {
        mGraphData=getpiebill(searchType,billtype,startDate,endDate);
        if(!mGraphData.isEmpty()){
            mGraphData.add(new PieEntry(30,"test"));
            mPieChart.setCenterText(new SpannableString("总计\n"+getTotal(mGraphData).toString()));
            initPieChart();
            barListAdapter.refresh(mGraphData);
        }
        else{
            ToastUtil.showShortToast(this,"无符合目标数据");
        }

    }

    private void initPieChart(){
        PieDataSet dataSet = new PieDataSet(mGraphData,"");
        ArrayList<Integer> colors = new ArrayList<Integer>();
        for (int i=0;i<=mGraphData.size();i++){
            colors.add(ColorUtils.getRandomColor());
        }
        dataSet.setColors(colors);

        PieData pieData = new PieData(dataSet);
        pieData.setDrawValues(true);
        pieData.setValueFormatter(new PercentFormatter(mPieChart));
        pieData.setValueTextSize(12f);
        pieData.setValueTextColor(Color.WHITE);

        Description description = new Description();
        description.setText("");
        mPieChart.setDescription(description);
        mPieChart.setData(pieData);
        mPieChart.invalidate();
    }

    private void setListener(GraphActivity.OnClick onClick){
        btnback.setOnClickListener(onClick);
        expand_button.setOnClickListener(onClick);
        btnCostGraphByKind.setOnClickListener(onClick);
        btnCostGraphByAccount.setOnClickListener(onClick);
        btnIncomeGraphByKind.setOnClickListener(onClick);
        btnIncomeGraphByAccount.setOnClickListener(onClick);
        btnMonthIncome.setOnClickListener(onClick);
        btnMonthCost.setOnClickListener(onClick);
        btnSetStartDate.setOnClickListener(onClick);
        btnSetEndDate.setOnClickListener(onClick);
    }



    protected void initChartStyle() {
        //使用百分百显示
        mPieChart.setUsePercentValues(true);
        mPieChart.getDescription().setEnabled(false);
        mPieChart.setExtraOffsets(5, 10, 5, 5);

        //设置拖拽的阻尼，0为立即停止
        mPieChart.setDragDecelerationFrictionCoef(0.95f);

        //设置初始值
        searchType="FirstClass";
        billtype=COST;
        Calendar calendar = Calendar.getInstance();
        endDate = new Date(System.currentTimeMillis());
        calendar.setTime(endDate);
        calendar.add(Calendar.MONTH,-1);
        startDate=calendar.getTime();
        mGraphData=getpiebill(searchType,billtype,startDate,endDate);

        //设置图标中心文字
        mPieChart.setCenterText(new SpannableString("总计\n"+getTotal(mGraphData).toString()));
        mPieChart.setDrawCenterText(true);
        //设置图标中心空白，空心
        mPieChart.setDrawHoleEnabled(true);
        //设置空心圆的弧度百分比，最大100
        mPieChart.setHoleRadius(50f);
        mPieChart.setHoleColor(Color.WHITE);
        //设置透明弧的样式
        mPieChart.setTransparentCircleColor(Color.WHITE);
        mPieChart.setTransparentCircleAlpha(110);
        mPieChart.setTransparentCircleRadius(61f);

        //设置可以旋转
        mPieChart.setRotationAngle(0);
        mPieChart.setRotationEnabled(true);
        mPieChart.setHighlightPerTapEnabled(true);

        mPieChart.setOnChartValueSelectedListener(this);
    }
    /**
     * 搜索时间区间内符合搜索要求的Bill并根据分类返回一个List
     * new PieEntry(30f,"一月")
     * 第一个为float的金额，第二个为string  "类型"
     * 排序按百分比从大到小
     * @param searchType 搜索类型，包括
     *   月度收入形式： "month"
     *              返回形式：在时间间隔不超过一年（30f,一月）
     *              超过一年（30f，19年5月）
     *   一级分类形式： “FirstClass”  ；
     *   二级分类形式：    “食品” （输入一个一级分类的名称，返回所有所属二级分类信息）；
     *                   若是输入为"SecondClass",返回所有二级分类信息；
     *    账户形式     “Account”；
     * @param startDate Date类型的时间，包括这一天
     * @param endDate Date类型的时间，包括这一天
     * */

    public static List<PieEntry> getpiebill(String searchType, String billtype, Date startDate, Date endDate){
        List<PieEntry> strings = new ArrayList<>();
        strings.add(new PieEntry(1f,"yyf"));
        strings.add(new PieEntry(1000f,"男生"));
        strings.add(new PieEntry(70f,"女生"));
        return strings;
    }
    /**
     * 统计符合要求的所有或收入
     * */
    private Float getTotal(List<PieEntry> bill){
        float sum=0;
        if(!bill.isEmpty()){
            for(PieEntry i:bill){
                sum+=i.getValue();
            }
        }
        return sum;
    }

}