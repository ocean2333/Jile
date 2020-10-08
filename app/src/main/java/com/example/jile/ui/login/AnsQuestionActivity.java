package com.example.jile.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jile.Bean.User;
import com.example.jile.LogoActivity;
import com.example.jile.R;

import java.util.List;

public class AnsQuestionActivity extends AppCompatActivity {
    private String ans;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ans_question);
        final Button btnBack = findViewById(R.id.btnBack);
        final Button btnNext = findViewById(R.id.btnNext);
        final EditText etAns = findViewById(R.id.etAns);
        final TextView tvQuestion = findViewById(R.id.tvQuestion);
        final Bundle bundle = getIntent().getExtras();    //得到传过来的bundle
        final String username = bundle.getString("username");
        tvQuestion.setText(getQuestion(username));
        btnNext.setEnabled(false);
        etAns.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ans = s.toString();
                if(ans.equals("")){
                    btnNext.setEnabled(false);
                }else{
                    btnNext.setEnabled(true);
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
                if(isAnsCorrect(ans,username)){
                    startActivity(new Intent(AnsQuestionActivity.this,ChangePasswordActivity.class).putExtras(bundle));
                }else{
                    Toast.makeText(AnsQuestionActivity.this,"答案错误",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // TODO 检验答案是否正确（待测试）
    private boolean isAnsCorrect(String s,String username){
        List<User> userList = LogoActivity.userDao.query();
        User user = null;
        for(User u:userList){
            if(u.getName().equals(username)){
                user = u;
            }
        }
        if(user==null){
            Toast.makeText(AnsQuestionActivity.this,"error in AnsQuestionActivity",Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return user.getAns().equals(s);
        }
    }


    // TODO 获得密保问题(待测试)
    private String getQuestion(String username){
        List<User> userList = LogoActivity.userDao.query();
        User user = null;
        for(User u:userList){
            if(u.getName().equals(username)){
                user = u;
            }
        }
        if(user==null){
            Toast.makeText(AnsQuestionActivity.this,"error in AnsQuestionActivity",Toast.LENGTH_SHORT).show();
            return user+"这是一个密保问题的测试？";
        }else{
            return user.getSecurequestion();
        }
    }
}