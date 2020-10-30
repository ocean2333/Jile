package com.example.jile.Setting;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;

import com.example.jile.Bean.Bill;
import com.example.jile.Bean.Mem;
import com.example.jile.Bean.Store;
import com.example.jile.LogoActivity;
import com.example.jile.R;
import com.scwang.smartrefresh.layout.adapter.SmartRecyclerAdapter;
import com.scwang.smartrefresh.layout.adapter.SmartViewHolder;
import com.xuexiang.xui.widget.toast.XToast;

import java.util.Collection;

public class StoreAdapter extends SmartRecyclerAdapter<Store> {
    Context mContext;
    Collection<Store> memCollection;

    public StoreAdapter(Collection<Store> collection, int layoutId,Context context) {
        super(collection, layoutId);
        memCollection = collection;
        mContext = context;
    }

    @Override
    protected void onBindViewHolder(SmartViewHolder holder, Store model, int position) {
        holder.text(R.id.tvTitle,model.getName());
        holder.text(R.id.uuid,model.getUuid());
        holder.findViewById(R.id.btnDelete).setOnClickListener(v->{
            new AlertDialog.Builder(mContext)
                    .setTitle("是否删除该商家")
                    .setIcon(R.drawable.ic_prompt)
                    .setPositiveButton("确定", (arg0, arg1) -> {
                        if(LogoActivity.storeDao.query().size()==1){
                            XToast.info(mContext,"不能删除最后一个商家").show();
                        }else{
                            LogoActivity.storeDao.delete(LogoActivity.storeDao.querybyskey("uuid",model.getUuid()).get(0));
                            memCollection.remove(model);
                            for(Bill b:LogoActivity.billDao.query()){
                                if(b.getStore().equals(model.getName())){
                                    LogoActivity.billDao.delete(b);
                                }
                            }
                            this.refresh(memCollection);
                        }
                    })
                    .setNegativeButton("取消", null)
                    .show();
        });
    }
}