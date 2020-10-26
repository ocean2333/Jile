package com.example.jile.Graph;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jile.Detail.DeatilActivity;
import com.example.jile.Detail.LineElement;
import com.example.jile.R;
import com.example.jile.Util.StatisticsMiddle;
import com.example.jile.Util.TextUtil;
import com.example.jile.Util.ToastUtil;
import com.github.mikephil.charting.data.PieEntry;
import com.xuexiang.xui.adapter.recyclerview.BaseRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.xuexiang.xui.utils.ColorUtils;
import com.xuexiang.xui.widget.progress.HorizontalProgressView;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Collection;
import java.util.List;

import static com.example.jile.Constant.Constants.SEARCH_TYPE_FIRST_CLASS;
import static com.example.jile.Constant.Constants.SEARCH_TYPE_SECOND_CLASS_IN_FIRST_CLASS;

public class BarListAdapter extends BaseRecyclerAdapter<PieEntry>{
    private Context mContext;
    private RecyclerView mRecyclerView;
    private Collection<PieEntry> mData;
    private float sum;
    public BarListAdapter(RecyclerView recyclerView, Collection<PieEntry> data,Context context) {
        super(data);
        mContext = context;
        mData = data;
        for (PieEntry i:mData) {
            sum+=i.getValue();
        }
        mRecyclerView = recyclerView;
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.adapter_bar;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void bindData(@NonNull RecyclerViewHolder holder, int position, PieEntry item) {
        holder.text(R.id.billTitle,item.getLabel());
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        HorizontalProgressView v =  (HorizontalProgressView) holder.findView(R.id.hpv_language);
        if(sum==0){
            sum=1;
        }
        v.setEndProgress((int)Math.abs(item.getValue()/sum*100));
        int newColor= ColorUtils.getRandomColor();
        v.setStartColor(newColor);
        v.setEndColor(newColor);
        v.startProgressAnimation();
        holder.text(R.id.billProportion,"  "+(int)(item.getValue()/sum*100)+"%");
        Button button = (Button)holder.findView(R.id.billMoney);
        button.setText(decimalFormat.format(item.getValue())+" >");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeatilActivity.startThisActivity(mContext,GraphActivity.searchType,
                        GraphActivity.firstClass,GraphActivity.startDate,GraphActivity.endDate);
            }
        });
        button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(GraphActivity.searchType.equals(SEARCH_TYPE_FIRST_CLASS)){
                    GraphActivity.searchType=SEARCH_TYPE_SECOND_CLASS_IN_FIRST_CLASS;
                    GraphActivity.firstClass=item.getLabel();
                    try {
                        GraphRefersh(StatisticsMiddle.getpiebill(GraphActivity.searchType,GraphActivity.billtype,GraphActivity.firstClass,GraphActivity.startDate,GraphActivity.endDate,mContext));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                return false;
            }
        });

    }
    private void GraphRefersh(List<PieEntry> item){
        this.refresh(item);
    }
}