package com.example.jile.Setting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.jile.Bean.FirstClass;
import com.example.jile.Constant.Constants;
import com.example.jile.LogoActivity;
import com.example.jile.New.NewBIllActivity;
import com.example.jile.R;
import com.example.jile.Util.IconSelectorActivity;
import com.example.jile.Util.ToastUtil;
import com.xuexiang.xui.utils.WidgetUtils;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.util.List;
import java.util.UUID;
import java.util.WeakHashMap;

import static android.view.View.GONE;

public class ClassSettingActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Integer iconId;
    ClassAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeSettingActivity.setActivityTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_setting);
        String type = getIntent().getExtras().getString("type");
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        WidgetUtils.initRecyclerView(recyclerView);
        List<FirstClass> firstClassList =  LogoActivity.firstClassDao.querybyskey("type",type);
        recyclerView.setAdapter(adapter = new ClassAdapter(firstClassList,R.layout.adapter_firstclass,this));
        ImageButton btnAdd = findViewById(R.id.btnAdd);
        LinearLayout newFirstClassSetter = findViewById(R.id.newFirstClassLayout);
        newFirstClassSetter.setVisibility(GONE);
        btnAdd.setOnClickListener(v->{
            newFirstClassSetter.setVisibility(View.VISIBLE);
        });
        ImageButton btnSelectIcon = findViewById(R.id.btnSelectIcon);
        btnSelectIcon.setOnClickListener(v->{
            startActivityForResult(IconSelectorActivity.startThisActivity(this, type),0);
        });
        ImageButton btnUncheck = findViewById(R.id.btnUncheck);
        btnUncheck.setOnClickListener(v->newFirstClassSetter.setVisibility(GONE));
        ImageButton btnCheck = findViewById(R.id.btnCheck);
        EditText et = findViewById(R.id.etTitle);
        findViewById(R.id.btnBack).setOnClickListener(v->finish());
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(LogoActivity.firstClassDao.querybyskey("name",s.toString()).size()!=0){
                    et.setError("已有同名分类了哦");
                    findViewById(R.id.btnCheck).setEnabled(false);
                }else{
                    findViewById(R.id.btnCheck).setEnabled(true);
                }
            }
        });
        btnCheck.setOnClickListener(v->{
            if(iconId==null){
                ToastUtil.showShortToast(this,"请先选择一个图标");
            }else{
                LogoActivity.firstClassDao.insert(new FirstClass(UUID.randomUUID().toString(),
                        type,et.getText().toString(),iconId));
                ((EditText)newFirstClassSetter.findViewById(R.id.etTitle)).setText("");
                newFirstClassSetter.setVisibility(GONE);
                adapter.refresh(LogoActivity.firstClassDao.querybyskey("type",type));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ImageView iv = (ImageView) findViewById(R.id.ivIcon);
        if(data==null) return;
        iconId = data.getExtras().getInt("iconId");
        iv.setImageResource(data.getExtras().getInt("iconId"));
    }

    public static Intent startThisActivity(Context context,String type){
        Bundle bundle = new Bundle();
        bundle.putString("type",type);
        Intent intent = new Intent(context, ClassSettingActivity.class);
        intent.putExtras(bundle);
        return intent;
    }

}