package com.example.jile.Setting;

import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.jile.Bean.FirstClass;
import com.example.jile.Bean.SecondClass;
import com.example.jile.LogoActivity;
import com.example.jile.R;
import com.scwang.smartrefresh.layout.adapter.SmartRecyclerAdapter;
import com.scwang.smartrefresh.layout.adapter.SmartViewHolder;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.UUID;

public class ClassAdapter extends SmartRecyclerAdapter<FirstClass> {
    Context mContext;

    public ClassAdapter(Collection<FirstClass> collection, int layoutId,Context context) {
        super(collection, layoutId);
        mContext = context;
    }

    @Override
    protected void onBindViewHolder(SmartViewHolder holder, FirstClass model, int position) {
        holder.image(R.id.ivIcon,model.getIconId());
        holder.text(R.id.tvTitle,model.getName());
        holder.text(R.id.uuid,model.getUuid());
        ImageButton ib = holder.findViewById(R.id.btnAdd);
        LinearLayout ll = holder.findViewById(R.id.ll);
        ll.removeAllViews();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,200);
        for(SecondClass s:LogoActivity.secondClassDao.querybyskey("firstclass",model.getName())){
            View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_secondclass,null);
            view.setLayoutParams(params);
            TextView textView = view.findViewById(R.id.tvTitle);
            textView.setText(s.getName());
            textView = view.findViewById(R.id.uuid);
            textView.setText(s.getUuid());
            view.findViewById(R.id.btnDelete).setOnClickListener(v2->{
                new AlertDialog.Builder(mContext)
                        .setTitle("确认删除")
                        .setIcon(R.drawable.ic_prompt)
                        .setPositiveButton("确定", (arg0, arg1) -> {
                            LogoActivity.secondClassDao.delete(LogoActivity.secondClassDao.querybyskey("uuid",s.getUuid()).get(0));
                            view.setVisibility(View.GONE);
                        })
                        .setNegativeButton("取消", null)
                        .show();
            });
            ll.addView(view);
        }
        ib.setOnClickListener((v)->{
            ib.setEnabled(false);
            Log.d("T", "onBindViewHolder: ");
            View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_secondclass_on_edit,null);
            view.setLayoutParams(params);
            EditText et = view.findViewById(R.id.etTitle);
            et.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if(LogoActivity.secondClassDao.querybyskey("name",s.toString()).size()!=0){
                        et.setError("已有同名分类了哦");
                        view.findViewById(R.id.btnCheck).setEnabled(false);
                    }else{
                        view.findViewById(R.id.btnCheck).setEnabled(true);
                    }
                }
            });
            view.findViewById(R.id.btnUncheck).setOnClickListener(v1->{
                ll.removeViewAt(0);
                ib.setEnabled(true);
            });
            view.findViewById(R.id.btnCheck).setOnClickListener(v1 -> {
                String uuid = UUID.randomUUID().toString();
                LogoActivity.secondClassDao.insert(new SecondClass(uuid,model.getType(),
                        model.getName(),et.getText().toString(),model.getIconId()));
                View childView = LayoutInflater.from(mContext).inflate(R.layout.adapter_secondclass,null);
                childView.setLayoutParams(params);
                TextView tv = childView.findViewById(R.id.tvTitle);
                tv.setText(et.getText().toString());
                tv = childView.findViewById(R.id.uuid);
                tv.setText(uuid);
                childView.findViewById(R.id.btnDelete).setOnClickListener(v2->{
                    new AlertDialog.Builder(mContext)
                            .setTitle("请输入预算")
                            .setIcon(R.drawable.ic_prompt)
                            .setPositiveButton("确定", (arg0, arg1) -> {
                                LogoActivity.secondClassDao.delete(LogoActivity.secondClassDao.querybyskey("uuid",uuid).get(0));
                                childView.setVisibility(View.GONE);
                            })
                            .setNegativeButton("取消", null)
                            .show();
                });
                view.setVisibility(View.GONE);
                ll.addView(childView,0);
                ib.setEnabled(true);
            });
            ll.addView(view,0);
        });
    }
}
