package com.example.jile.MainView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.jile.Bean.Bill;
import com.example.jile.Constant.Constants;
import com.example.jile.New.NewBIllActivity;
import com.example.jile.R;
import com.example.jile.Util.DateUtil;
import com.example.jile.myApplication;
import com.scwang.smartrefresh.layout.adapter.SmartRecyclerAdapter;
import com.scwang.smartrefresh.layout.adapter.SmartViewHolder;

import java.text.ParseException;
import java.util.Collection;

import static com.xuexiang.xui.XUI.getContext;

public class BillRecyclerAdapter extends SmartRecyclerAdapter<Bill> {
    Context mContext;
    public BillRecyclerAdapter(int layoutId) {
        super(layoutId);
    }

    public BillRecyclerAdapter(Collection<Bill> collection, int layoutId) {
        super(collection, layoutId);
    }

    public BillRecyclerAdapter(Collection<Bill> collection, int layoutId, SmartViewHolder.OnItemClickListener listener, Context context) {
        super(collection, layoutId, listener);
        mContext = context;
    }

    @Override
    protected void onBindViewHolder(SmartViewHolder holder, Bill model, int position) {
        holder.image(R.id.ivIcon,model.getIconId());
        try {
            holder.text(R.id.tvTime,DateUtil.getShortDate(model.getDate()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.text(R.id.tvDay,DateUtil.getWeek(model.getDate()));
        if(!model.getType().equals(Constants.TRANSFER)){
            holder.text(R.id.tvSecondClass,model.getSecond());
        }else{
            holder.text(R.id.tvSecondClass,"从 "+model.getFirst()+" 转入 "+model.getSecond());
        }
        holder.text(R.id.tvMoney, model.getNum().toPlainString());
        holder.text(R.id.tvAccount,model.getAccountname());
        holder.text(R.id.tvUuid,model.getUuid());
        Button btnModify =  holder.findViewById(R.id.btnModify);
        btnModify.setOnClickListener((l)->{
            Bundle bundle = new Bundle();
            bundle.putString("uuid",model.getUuid());
            Intent intent = new Intent(getContext(), NewBIllActivity.class);
            intent.putExtras(bundle);
            mContext.startActivity(intent);
        });
    }
}
