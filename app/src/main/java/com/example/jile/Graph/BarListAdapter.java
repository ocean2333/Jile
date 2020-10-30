package com.example.jile.Graph;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jile.Detail.DeatilActivity;
import com.example.jile.Detail.LineElement;
import com.example.jile.LogoActivity;
import com.example.jile.R;
import com.example.jile.Util.StatisticsMiddle;
import com.example.jile.Util.TextUtil;
import com.example.jile.Util.ToastUtil;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.xuexiang.xui.adapter.recyclerview.BaseRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.xuexiang.xui.utils.ColorUtils;
import com.xuexiang.xui.widget.progress.HorizontalProgressView;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Collection;
import java.util.List;

import static com.example.jile.Constant.Constants.COST;
import static com.example.jile.Constant.Constants.SEARCH_TYPE_ACCOUNT;
import static com.example.jile.Constant.Constants.SEARCH_TYPE_FIRST_CLASS;
import static com.example.jile.Constant.Constants.SEARCH_TYPE_MEM;
import static com.example.jile.Constant.Constants.SEARCH_TYPE_MONTH;
import static com.example.jile.Constant.Constants.SEARCH_TYPE_SECOND_CLASS_IN_FIRST_CLASS;

public class BarListAdapter extends BaseRecyclerAdapter<PieEntry>{
    private Context mContext;
    private RecyclerView mRecyclerView;
    private Collection<PieEntry> mData;
    public BarListAdapter(RecyclerView recyclerView, Collection<PieEntry> data,Context context) {
        super(data);
        mContext = context;
        mData = data;
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

        if(Float.compare(GraphActivity.sumBill,0.0f)!=0){
            v.setEndProgress((int)(item.getValue()/GraphActivity.sumBill*100));
            holder.text(R.id.billProportion,"  "+(int)(item.getValue()/GraphActivity.sumBill*100)+"%");
        }
        else{
            v.setEndProgress(100);
        }
        int newColor= ColorUtils.getRandomColor();
        v.setStartColor(newColor);
        v.setEndColor(newColor);
        v.startProgressAnimation();
        ImageView iv =  (ImageView)(holder.findViewById(R.id.billImage));
        if(GraphActivity.searchType.equals(SEARCH_TYPE_MONTH)){
            if(GraphActivity.billtype.equals(COST)){
                iv.setImageResource(R.drawable.ic_monthcost);
            }
            else {
                iv.setImageResource(R.drawable.ic_monthincome);
            }
        }
        else if(GraphActivity.searchType.equals(SEARCH_TYPE_SECOND_CLASS_IN_FIRST_CLASS)){
            iv.setImageResource(LogoActivity.iconDao.querybyskey("name",
                    LogoActivity.secondClassDao.querybyskey("name",item.getLabel()).get(0).getFirstclass()).get(0).getIconId());
        }
        else if(GraphActivity.searchType.equals(SEARCH_TYPE_MEM)){
            if(GraphActivity.billtype.equals(COST)){
                iv.setImageResource(R.drawable.ic_mencost);
            }
            else {
                iv.setImageResource(R.drawable.ic_menincome);
            }
        }
        else if(GraphActivity.searchType.equals(SEARCH_TYPE_ACCOUNT)){
            int iconId = LogoActivity.accountDao.querybyskey("selfname",item.getLabel()).get(0).getIconId();
            iv.setImageResource(iconId);
        }
        else if(GraphActivity.searchType.equals(SEARCH_TYPE_FIRST_CLASS)){
            int iconId = LogoActivity.firstClassDao.querybyskey("name",item.getLabel()).get(0).getIconId();
            iv.setImageResource(iconId);
        }

        Button button = (Button)holder.findView(R.id.billMoney);
        button.setText(decimalFormat.format(item.getValue())+" >");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    mContext.startActivity(DeatilActivity.startThisActivity(mContext,GraphActivity.searchType,
                            GraphActivity.firstClass,GraphActivity.startDate,GraphActivity.endDate));
            }
        });
        button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(GraphActivity.searchType.equals(SEARCH_TYPE_FIRST_CLASS)){
                    GraphActivity.searchType=SEARCH_TYPE_SECOND_CLASS_IN_FIRST_CLASS;
                    GraphActivity.firstClass=item.getLabel();
                    try {
                        List<PieEntry> tempData = StatisticsMiddle.getpiebill(GraphActivity.searchType,
                                GraphActivity.billtype, GraphActivity.firstClass, GraphActivity.startDate, GraphActivity.endDate, mContext);
                        GraphActivity.sumBill= (float) 0;
                        for (PieEntry i:tempData) {
                            GraphActivity.sumBill+=i.getValue();
                        }
                        if(Float.compare(GraphActivity.sumBill,0.0f)!=0){
                            GraphRefersh(tempData);
                        }
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