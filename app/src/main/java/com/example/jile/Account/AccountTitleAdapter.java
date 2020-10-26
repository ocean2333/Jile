package com.example.jile.Account;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.jile.Bean.Account;
import com.example.jile.R;
import com.scwang.smartrefresh.layout.adapter.SmartRecyclerAdapter;
import com.scwang.smartrefresh.layout.adapter.SmartViewHolder;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.util.Collection;

public class AccountTitleAdapter extends SmartRecyclerAdapter<LineElement> {
    public AccountTitleAdapter(Collection<LineElement> collection, int layoutId) {
        super(collection, layoutId);
    }
    @Override
    protected void onBindViewHolder(SmartViewHolder holder, LineElement model, int position) {
        holder.text(R.id.tvTitle,model.getName());
        holder.text(R.id.tvMoney,model.getBalance());
        LinearLayout ll = (LinearLayout) holder.findView(R.id.ll);
        for(View b:model.getView()){
            if(b.getParent()!=null){
                ((ViewGroup) b.getParent()).removeView(b);
            }
            ll.addView(b);
        }
        Log.d("TAG", "onBindViewHolder: "+ll.getChildCount());
    }
}
