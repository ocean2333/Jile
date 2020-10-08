package com.example.jile.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.example.jile.Bean.User;
import com.example.jile.LogoActivity;
import com.example.jile.MainView.MainActivity;

import java.util.List;

import me.zhanghai.android.patternlock.ConfirmPatternActivity;
import me.zhanghai.android.patternlock.PatternUtils;
import me.zhanghai.android.patternlock.PatternView;

public class MyConfirmPatternActivity extends ConfirmPatternActivity {

    private String username;
    @Override
    protected boolean isStealthModeEnabled() {
        return false;
    }

    @Override
    protected boolean isPatternCorrect(List<PatternView.Cell> pattern) {
        String patternSha1 = null;
        Bundle bundle = getIntent().getExtras();
        username = bundle.getString("username");
        patternSha1 = getPattenSha1(username);
        return TextUtils.equals(PatternUtils.patternToSha1String(pattern), patternSha1);
    }

    @Override
    protected void onForgotPassword() {

        startActivity(new Intent(this, SetGestureLockActivity.class));
        // Finish with RESULT_FORGOT_PASSWORD.
        super.onForgotPassword();
    }

    @Override
    protected void onConfirmed(){
        setResult(RESULT_OK);
        // 往sharedpreference里存当前user
        LogoActivity.sp.edit().putString("loginUser",username).apply();
        startActivity(new Intent(MyConfirmPatternActivity.this, MainActivity.class));
    }

    // TODO: 实现该接口 Get saved pattern sha1(待测试).
    private String getPattenSha1(String username){
        List<User> userList = LogoActivity.userDao.query();
        User user = null;
        for(User u:userList){
            if(u.getName().equals(username)){
                user = u;
            }
        }
        if(user==null){
            Toast.makeText(MyConfirmPatternActivity.this,"error in SetGestureLockActivity",Toast.LENGTH_SHORT).show();
        }else{
            Log.d("TAG", "getPattenSha1: "+user.getGraphpass());
            return user.getGraphpass();
        }
        return "";
    }
}