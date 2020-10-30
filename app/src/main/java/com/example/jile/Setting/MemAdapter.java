package com.example.jile.Setting;

import android.content.Context;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AlertDialog;

import com.example.jile.Bean.Bill;
import com.example.jile.Bean.Mem;
import com.example.jile.LogoActivity;
import com.example.jile.R;
import com.scwang.smartrefresh.layout.adapter.SmartRecyclerAdapter;
import com.scwang.smartrefresh.layout.adapter.SmartViewHolder;
import com.xuexiang.xui.widget.toast.XToast;

import java.util.Collection;

import static android.view.View.GONE;

public class MemAdapter extends SmartRecyclerAdapter<Mem> {
    Context mContext;
    Collection<Mem> memCollection;

    public MemAdapter(Collection<Mem> collection, int layoutId,Context context) {
        super(collection, layoutId);
        memCollection = collection;
        mContext = context;
    }

    @Override
    protected void onBindViewHolder(SmartViewHolder holder, Mem model, int position) {
        holder.text(R.id.tvTitle,model.getName());
        holder.text(R.id.uuid,model.getUuid());
        holder.findViewById(R.id.btnDelete).setOnClickListener(v->{
            new AlertDialog.Builder(mContext)
                    .setTitle("是否删除该成员")
                    .setIcon(R.drawable.ic_prompt)
                    .setPositiveButton("确定", (arg0, arg1) -> {
                        if(LogoActivity.memDao.query().size()==1){
                            XToast.info(mContext,"不能删除最后一个成员").show();
                        }else{
                            LogoActivity.memDao.delete(LogoActivity.memDao.querybyskey("uuid",model.getUuid()).get(0));
                            for(Bill b:LogoActivity.billDao.query()){
                                if(b.getMember().equals(model.getName())){
                                    LogoActivity.billDao.delete(b);
                                }
                            }
                            memCollection.remove(model);
                            this.refresh(memCollection);
                        }
                    })
                    .setNegativeButton("取消", null)
                    .show();
        });
    }
}
