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

import com.example.jile.R;

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
        tvQuestion.setText(getQuestion(bundle.getString("username")));
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
                if(isAnsCorrect(ans)){
                    startActivity(new Intent(AnsQuestionActivity.this,ChangePasswordActivity.class).putExtras(bundle));
                }else{
                    Toast.makeText(AnsQuestionActivity.this,"答案错误",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // TODO 实现以下接口
    // TODO 检验答案是否正确
    private boolean isAnsCorrect(String s){
        return true;
    }

    // TODO 获得密保问题
    private String getQuestion(String user){
        return user+"这是一个密保问题的测试？";
    }
}