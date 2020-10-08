package com.example.jile.MainView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.jile.Bean.Bill;
import com.example.jile.R;

import java.text.ParseException;
import java.util.Calendar;

public class BillAdapter extends ArrayAdapter<Bill> {
    private int resourceId;
    public BillAdapter(@NonNull Context context, int resource, @NonNull Bill[] objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Bill bill = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        ImageView im = view.findViewById(R.id.ivIcon);
        TextView tvTime = view.findViewById(R.id.tvTime);
        TextView tvDay = view.findViewById(R.id.tvDay);
        TextView tvSecondClass = view.findViewById(R.id.tvSecondClass);
        TextView tvStore = view.findViewById(R.id.tvStore);
        TextView tvMoney = view.findViewById(R.id.tvMoney);
        im.setImageResource(bill.getIconId());
        tvTime.setText(getShortDate(bill.getDate().toString()));
        tvDay.setText(getWeek(bill.getDate().toString()));
        tvSecondClass.setText(bill.getFirst());
        tvMoney.setText(bill.getNum().toString());
        tvStore.setText(bill.getStore());
        return view;
    }

    /**
     * 判断当前日期是星期几
     *
     * @param  pTime     设置的需要判断的时间  //格式如2012-09-08
     *

     * @return dayForWeek 判断结果
     * @Exception 发生异常
     */

//  String pTime = "2012-03-12";
    private String getWeek(String pTime) {


        String Week = "周";


        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        Calendar c = Calendar.getInstance();
        try {

            c.setTime(format.parse(pTime));

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            Week += "日";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 2) {
            Week += "一";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 3) {
            Week += "二";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 4) {
            Week += "三";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 5) {
            Week += "四";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 6) {
            Week += "五";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 7) {
            Week += "六";
        }
        return Week;
    }

    private String getShortDate(String date){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        return date.split("年")[1].split(" ")[0];
    }
}
