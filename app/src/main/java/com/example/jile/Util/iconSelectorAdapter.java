package com.example.jile.Util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.jile.Bean.Icon;
import com.example.jile.R;
import com.scwang.smartrefresh.layout.adapter.SmartRecyclerAdapter;
import com.scwang.smartrefresh.layout.adapter.SmartViewHolder;

import java.util.Collection;
import java.util.List;

public class iconSelectorAdapter extends SmartRecyclerAdapter<List<Icon>> {
    Context mContext;
    Intent intent;
    Activity activity;
    public iconSelectorAdapter(int layoutId) {
        super(layoutId);
    }

    public iconSelectorAdapter(Collection<List<Icon>> collection, int layoutId) {
        super(collection, layoutId);
    }

    public iconSelectorAdapter(Collection<List<Icon>> collection, int layoutId, SmartViewHolder.OnItemClickListener listener, Activity activity) {
        super(collection, layoutId, listener);
        this.activity = activity;
    }

    @Override
    protected void onBindViewHolder(SmartViewHolder holder, List<Icon> model, int position) {
        if(model.size()>0){
            ImageView ib = holder.findViewById(R.id.ib1);
            int iconId = model.get(0).getIconId();
            ib.setImageResource(iconId);
            ib.setOnClickListener((v)->{
                Intent intent = new Intent();
                intent.putExtra("iconId",iconId);
                activity.setResult(0,intent);
                activity.finish();
            });
        }
        if(model.size()>1){
            ImageView ib = holder.findViewById(R.id.ib2);
            int iconId = model.get(1).getIconId();
            ib.setImageResource(iconId);
            ib.setOnClickListener((v)->{
                Intent intent = new Intent();
                intent.putExtra("iconId",iconId);
                activity.setResult(0,intent);
                activity.finish();
            });
        }
        if(model.size()>2){
            ImageView ib = holder.findViewById(R.id.ib3);
            int iconId = model.get(2).getIconId();
            ib.setImageResource(iconId);
            ib.setOnClickListener((v)->{
                Intent intent = new Intent();
                intent.putExtra("iconId",iconId);
                activity.setResult(0,intent);
                activity.finish();
            });
        }
        if(model.size()>3){
            ImageView ib = holder.findViewById(R.id.ib4);
            int iconId = model.get(3).getIconId();
            ib.setImageResource(iconId);
            ib.setOnClickListener((v)->{
                Intent intent = new Intent();
                intent.putExtra("iconId",iconId);
                activity.setResult(0,intent);
                activity.finish();
            });
        }

    }
}
