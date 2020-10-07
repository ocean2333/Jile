package com.example.jile.Account;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.jile.Bean.Account;
import com.example.jile.R;

import java.util.List;

public class AccountAdapter extends ArrayAdapter<Account> {
    private int resourceId;
    public AccountAdapter(@NonNull Context context, int resource, @NonNull List<Account> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position,View convertView,ViewGroup parent){
        Account account = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        ImageView im = view.findViewById(R.id.imIcon);
        TextView tv1 = view.findViewById(R.id.tvName);
        TextView tv2 = view.findViewById(R.id.tvMoney);
        im.setImageResource(account.getIconId());
        tv1.setText(account.getSelfname());
        tv2.setText(account.getBalance().toString());
        return view;
    }

}
