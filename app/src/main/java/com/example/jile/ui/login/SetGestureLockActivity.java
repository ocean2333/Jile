package com.example.jile.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.example.jile.R;

import java.util.List;

import me.zhanghai.android.patternlock.PatternUtils;
import me.zhanghai.android.patternlock.PatternView;
import me.zhanghai.android.patternlock.SetPatternActivity;

public class SetGestureLockActivity extends SetPatternActivity {

    @Override
    protected void onSetPattern(List<PatternView.Cell> pattern) {
        String patternSha1 = PatternUtils.patternToSha1String(pattern);
        Bundle bundle = getIntent().getExtras();    //得到传过来的bundle
        //Toast.makeText(this,bundle.getString("username")+patternSha1,Toast.LENGTH_SHORT).show();
        addPatternSha1ToDB(patternSha1,bundle.getString("username"));
    }

    // TODO 有没有好点的跳转？
    @Override
    protected void onDestroy() {
        super.onDestroy();
        startActivity(new Intent(SetGestureLockActivity.this,LoginActivity.class));
    }

    // TODO 实现该接口 Save patternSha1 in DB.
    private void addPatternSha1ToDB(String pattenSha1,String username){

    }
}