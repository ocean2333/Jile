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
import com.example.jile.Bean.Bill;
import com.example.jile.R;
import com.example.jile.Util.TextUtil;
import com.scwang.smartrefresh.layout.adapter.SmartRecyclerAdapter;
import com.scwang.smartrefresh.layout.adapter.SmartViewHolder;

import java.util.Collection;
import java.util.List;

public class AccountAdapter extends SmartRecyclerAdapter<Account> {
    private int resourceId;

    public AccountAdapter(int layoutId) {
        super(layoutId);
    }

    public AccountAdapter(Collection<Account> collection, int layoutId) {
        super(collection, layoutId);
    }

    public AccountAdapter(Collection<Account> collection, int layoutId, SmartViewHolder.OnItemClickListener listener) {
        super(collection, layoutId, listener);
    }

    @Override
    protected void onBindViewHolder(SmartViewHolder holder, Account model, int position) {
        holder.image(R.id.imIcon,model.getIconId());
        holder.text(R.id.tvName,model.getSelfname());
        holder.text(R.id.tvMoney,TextUtil.simplifyMoney(model.getBalance().toPlainString()));
        holder.text(R.id.uuid,model.getUuid());
    }
}
