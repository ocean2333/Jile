package com.example.jile.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jile.Bean.User;
import com.example.jile.R;

public class SignUpActivity extends AppCompatActivity {
    private EditText etUsername,etPassword,etRepeatPassword,etQuestion,etAns;
    private Button btnBack,btnNext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        init();
        etQuestion.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 20){
                    etQuestion.setError("问题过长（应小于20个字符）");
                }
            }
        });
        etAns.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 10){
                    etAns.setError("输入超长（应小于10个字符）");
                }
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etPassword.getText().toString().equals(etRepeatPassword.getText().toString())){
                    Intent intent = new Intent(SignUpActivity.this,SetGestureLockActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("username",etUsername.getText().toString());
                    /*bundle.putString("password",etPassword.getText().toString());
                    bundle.putString("question",etQuestion.getText().toString());
                    bundle.putString("ans",etAns.getText().toString());*/
                    addNewUserToDB(createNewUser());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }else{
                    Toast.makeText(SignUpActivity.this,"两次密码不相同",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getComponents(){
        etUsername = findViewById(R.id.etUserName);
        etPassword = findViewById(R.id.etPassword);
        etRepeatPassword = findViewById(R.id.etRepeatPassword);
        etQuestion = findViewById(R.id.etQuestion);
        etAns = findViewById(R.id.etAns);
        btnNext = findViewById(R.id.btnNext);
        btnBack = findViewById(R.id.btnBack);
    }

    private void init(){
        getComponents();
    }

    //点击编辑框以外的地方关闭输入法
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public  boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = { 0, 0 };
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);

            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    private User createNewUser(){
        return new User("567",etUsername.getText().toString(),etPassword.getText().toString(),
                etQuestion.getText().toString(),etAns.getText().toString(),"?",R.drawable.icon_dollar,"hhh","");
    }

    // TODO 实现以下接口
    private void addNewUserToDB(User user){
        Toast.makeText(SignUpActivity.this,"got"+user.getName(),Toast.LENGTH_SHORT).show();
    }
}