package com.example.jile.Graph;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jile.Detail.LineElement;
import com.example.jile.R;
import com.example.jile.Util.TextUtil;
import com.example.jile.Util.ToastUtil;
import com.github.mikephil.charting.data.PieEntry;
import com.xuexiang.xui.adapter.recyclerview.BaseRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.xuexiang.xui.widget.progress.HorizontalProgressView;

import java.util.Collection;

public class BarListAdapter extends BaseRecyclerAdapter<PieEntry>{

    private RecyclerView mRecyclerView;
    private Collection<PieEntry> mData;
    private float sum;
    public BarListAdapter(RecyclerView recyclerView, Collection<PieEntry> data) {
        super(data);
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

    @Override
    protected void bindData(@NonNull RecyclerViewHolder holder, int position, PieEntry item) {
        holder.text(R.id.billTitle,item.getLabel());
        HorizontalProgressView v =  (HorizontalProgressView) holder.findView(R.id.hpv_language);
        v.setEndProgress(item.getValue()/sum);
        holder.text(R.id.billProportion,"  "+item.getValue()/sum);
        Button button = (Button)holder.findView(R.id.billMoney);
        button.setText(item.getValue()+" >");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("", "onClick: !");
            }
        });
        button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });

    }
}