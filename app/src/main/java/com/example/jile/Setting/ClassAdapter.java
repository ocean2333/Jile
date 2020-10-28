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
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jile.Bean.Bill;
import com.example.jile.Bean.FirstClass;
import com.example.jile.Bean.SecondClass;
import com.example.jile.LogoActivity;
import com.example.jile.New.NewBIllActivity;
import com.example.jile.R;
import com.example.jile.ui.login.LoginActivity;
import com.scwang.smartrefresh.layout.adapter.SmartRecyclerAdapter;
import com.scwang.smartrefresh.layout.adapter.SmartViewHolder;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.UUID;

import static android.view.View.GONE;

public class ClassAdapter extends SmartRecyclerAdapter<FirstClass> {
    Context mContext;
    ClassAdapter mAdapter;
    Collection<FirstClass> mCollection;
    public ClassAdapter(Collection<FirstClass> collection, int layoutId, Context context) {
        super(collection, layoutId);
        mCollection = collection;
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
        ImageButton imageButton = ((ImageButton)holder.findViewById(R.id.btnDelete));
        imageButton.setOnClickListener(v->{
            LogoActivity.firstClassDao.delete(LogoActivity.firstClassDao.querybyskey("uuid",model.getUuid()).get(0));
            mCollection.remove(model);
            this.refresh(mCollection);
        });
        if(!isNoChild(model.getUuid())) imageButton.setVisibility(GONE);
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
                            for(Bill b:LogoActivity.billDao.querybyskey("second",((TextView)view.findViewById(R.id.tvTitle)).getText().toString())){
                                NewBIllActivity.deleteBillInDB(b);
                            }
                            LogoActivity.secondClassDao.delete(LogoActivity.secondClassDao.querybyskey("uuid",s.getUuid()).get(0));
                            view.setVisibility(GONE);
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
                            .setTitle("是否删除该分类")
                            .setIcon(R.drawable.ic_prompt)
                            .setPositiveButton("确定", (arg0, arg1) -> {
                                LogoActivity.secondClassDao.delete(LogoActivity.secondClassDao.querybyskey("uuid",uuid).get(0));
                                if(isNoChild(model.getUuid())) imageButton.setVisibility(View.VISIBLE);
                                childView.setVisibility(GONE);
                            })
                            .setNegativeButton("取消", null)
                            .show();
                });
                view.setVisibility(GONE);
                ll.addView(childView,0);
                ib.setEnabled(true);
            });
            ll.addView(view,0);
        });
    }

    private boolean isNoChild(String uuid){
        int childNum = LogoActivity.secondClassDao.querybyskey("firstclass",LogoActivity.firstClassDao.querybyskey("uuid",uuid).get(0).getName()).size();
        return childNum == 0;
    }
}
