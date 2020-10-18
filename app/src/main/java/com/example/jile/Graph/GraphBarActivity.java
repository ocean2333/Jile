package com.example.jile.Graph;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.jile.R;
import com.github.mikephil.charting.charts.PieChart;
import com.xuexiang.xui.widget.alpha.XUIAlphaTextView;
import com.xuexiang.xui.widget.layout.ExpandableLayout;

public class GraphBarActivity extends AppCompatActivity {
    ExpandableLayout expandableLayout1;
    XUIAlphaTextView expand_button;
    private GraphBarActivity.OnClick onClick = new GraphBarActivity.OnClick();
    private Button btnback,btnCostGraphByKind,btnCostGraphByAccount,btnIncomeGraphByKind,
            btnIncomeGraphByAccount,btnMonthIncome,btnMonthCost,btnSetDate,btnSetBarChart,btnSetPieChart;

    private void init(){
        getComponents();
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
        btnSetDate=findViewById(R.id.btnSetDate);
        btnSetBarChart=findViewById(R.id.btnSetBarChart);
        btnSetPieChart=findViewById(R.id.btnSetPieChart);

    }
    private class OnClick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent intent;
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
                case R.id.btnSetDate:
                    break;
                case R.id.btnSetPieChart:
                    intent = new Intent(GraphBarActivity.this, GraphPieActivity.class);
                    startActivity(intent);
                    break;

            }
        }
    }
    private void setListener(GraphBarActivity.OnClick onClick){
        btnback.setOnClickListener(onClick);
        expand_button.setOnClickListener(onClick);
        btnCostGraphByKind.setOnClickListener(onClick);
        btnCostGraphByAccount.setOnClickListener(onClick);
        btnIncomeGraphByKind.setOnClickListener(onClick);
        btnIncomeGraphByAccount.setOnClickListener(onClick);
        btnMonthIncome.setOnClickListener(onClick);
        btnMonthCost.setOnClickListener(onClick);
        btnSetDate.setOnClickListener(onClick);
        btnSetBarChart.setOnClickListener(onClick);
        btnSetPieChart.setOnClickListener(onClick);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphpie);
        init();
        expandableLayout1.setOnExpansionChangedListener((expansion, state) -> Log.d("expandableLayout1", "State: " + state));
        setListener(onClick);
    }
}