package com.example.jile.Setting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.jile.Bean.FirstClass;
import com.example.jile.Bean.Mem;
import com.example.jile.Bean.Store;
import com.example.jile.Constant.Constants;
import com.example.jile.LogoActivity;
import com.example.jile.R;
import com.example.jile.Util.ToastUtil;
import com.xuexiang.xui.utils.WidgetUtils;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import static android.view.View.GONE;

public class NewMemAndStoreActivity extends AppCompatActivity {
    MemAdapter mAdapter;
    StoreAdapter sAdapter;
    String type;
    List<Mem> memData;
    List<Store> storeData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_mem_and_store);

        type = getIntent().getExtras().getString("type");

        LinearLayout newItem = (LinearLayout)findViewById(R.id.newItem);

        ImageButton btnUncheck = findViewById(R.id.btnUncheck);
        btnUncheck.setOnClickListener(v->newItem.setVisibility(GONE));
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
                switch (type){
                    case Constants.SEARCH_TYPE_MEM:
                        if(LogoActivity.memDao.querybyskey("name",s.toString()).size()!=0){
                            et.setError("已有同名成员了哦");
                            findViewById(R.id.btnCheck).setEnabled(false);
                        }else{
                            findViewById(R.id.btnCheck).setEnabled(true);
                        }
                        break;
                    case Constants.SEARCH_TYPE_STORE:
                        if(LogoActivity.storeDao.querybyskey("name",s.toString()).size()!=0){
                            et.setError("已有同名商家了哦");
                            findViewById(R.id.btnCheck).setEnabled(false);
                        }else{
                            findViewById(R.id.btnCheck).setEnabled(true);
                        }
                        break;
                }
            }
        });
        btnCheck.setOnClickListener(v->{
            switch (type){
                case Constants.SEARCH_TYPE_MEM:
                    LogoActivity.memDao.insert(new Mem(UUID.randomUUID().toString(),
                            et.getText().toString()));
                    ((EditText)newItem.findViewById(R.id.etTitle)).setText("");
                    newItem.setVisibility(GONE);
                    mAdapter.refresh(LogoActivity.memDao.query());
                    break;
                case Constants.SEARCH_TYPE_STORE:
                    LogoActivity.storeDao.insert(new Store(UUID.randomUUID().toString(),
                            et.getText().toString()));
                    ((EditText)newItem.findViewById(R.id.etTitle)).setText("");
                    newItem.setVisibility(GONE);
                    sAdapter.refresh(LogoActivity.storeDao.query());
                    break;
            }
        });
        newItem.setVisibility(View.GONE);
        ((ImageButton)findViewById(R.id.btnBack)).setOnClickListener(v->finish());
        ((ImageButton)findViewById(R.id.btnAdd)).setOnClickListener(v->{
            newItem.setVisibility(View.VISIBLE);
        });

        if(type.equals(Constants.SEARCH_TYPE_MEM)){
            ((EditText)findViewById(R.id.etTitle)).setHint("新的成员");

            SwipeRecyclerView recyclerView = (SwipeRecyclerView) findViewById(R.id.recycler_view);
            WidgetUtils.initRecyclerView(recyclerView);
            recyclerView.setAdapter(mAdapter = new MemAdapter(LogoActivity.memDao.query(),R.layout.adapter_secondclass,this));
        }else{
            ((EditText)findViewById(R.id.etTitle)).setHint("新的商家");

            SwipeRecyclerView recyclerView = (SwipeRecyclerView) findViewById(R.id.recycler_view);
            WidgetUtils.initRecyclerView(recyclerView);
            recyclerView.setAdapter(sAdapter = new StoreAdapter(LogoActivity.storeDao.query(),R.layout.adapter_secondclass,this));
        }

    }

    public static Intent startThisActivity(Context context,String type){
        Bundle bundle = new Bundle();
        bundle.putString("type",type);
        Intent intent = new Intent(context, NewMemAndStoreActivity.class);
        intent.putExtras(bundle);
        return intent;
    }
}