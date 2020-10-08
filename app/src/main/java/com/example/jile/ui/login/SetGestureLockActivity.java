package com.example.jile.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.jile.Bean.User;
import com.example.jile.LogoActivity;
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

    // TODO 有没有好点的跳转（暂不修改）
    @Override
    protected void onDestroy() {
        super.onDestroy();
        startActivity(new Intent(SetGestureLockActivity.this,LoginActivity.class));
    }

    // TODO 实现该接口 Save patternSha1 in DB.(待测试)
    private void addPatternSha1ToDB(String pattenSha1,String username){
        List<User> userList = LogoActivity.userDao.query();
        User user = null;
        for(User u:userList){
            if(u.getName().equals(username)){
                user = u;
            }
        }
        if(user==null){
            Toast.makeText(SetGestureLockActivity.this,"error in SetGestureLockActivity",Toast.LENGTH_SHORT).show();
        }else{
            user.setGraphpass(pattenSha1);
            Log.d("TAG", "addPatternSha1ToDB: "+pattenSha1);
            LogoActivity.userDao.update(user);
        }
    }
}