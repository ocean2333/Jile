package com.example.jile.Util;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import com.example.jile.Bean.Icon;
import com.example.jile.Constant.Constants;
import com.example.jile.LogoActivity;
import com.example.jile.R;
import com.xuexiang.xui.XUI;
import com.xuexiang.xui.utils.WidgetUtils;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.util.LinkedList;
import java.util.List;

public class IconSelectorActivity extends AppCompatActivity {
    private ImageButton btnBack;
    private SwipeRecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        XUI.initTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_icon_selector);
        if(getIntent().getExtras()==null){
            finish();
        }
        String type = getIntent().getExtras().getString("type",null);
        List<Icon> iconList = LogoActivity.iconDao.querybyskey("type", type);
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener((v) -> finish());
        recyclerView = findViewById(R.id.recycler_view);
        WidgetUtils.initRecyclerView(recyclerView);
        List<List<Icon>> lli = divideList(iconList);
        recyclerView.setAdapter(new iconSelectorAdapter(lli,R.layout.adapter_icon,(v,p)->{},this));
    }

    /**
     *
     *
     * @return
     * */
    public static Intent startThisActivity(Context context, String type){
        Bundle bundle = new Bundle();
        bundle.putString("type",type);
        Intent intent = new Intent(context, IconSelectorActivity.class);
        intent.putExtras(bundle);
        return intent;
    }

    private List<List<Icon>> divideList(List<Icon> iconList){
        List<List<Icon>> res = new LinkedList<>();
        List<Icon> temp = new LinkedList<>();
        int count = 0;
        while(count<iconList.size()){
            temp.add(iconList.get(count));
            if(count%4==3) {res.add(new LinkedList<>(temp));temp.clear();}
            count++;
        }
        if(!temp.isEmpty()){
            res.add(temp);
        }
        return res;
    }
}