package com.example.jile.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import java.util.List;

import me.zhanghai.android.patternlock.ConfirmPatternActivity;
import me.zhanghai.android.patternlock.PatternUtils;
import me.zhanghai.android.patternlock.PatternView;

public class MyConfirmPatternActivity extends ConfirmPatternActivity {
    @Override
    protected boolean isStealthModeEnabled() {
        // TODO: Return the value from SharedPreferences.
        return false;
    }

    @Override
    protected boolean isPatternCorrect(List<PatternView.Cell> pattern) {
        String patternSha1 = null;
        Bundle bundle = getIntent().getExtras();
        patternSha1 = getPattenSha1(bundle.getString("username"));
        return TextUtils.equals(PatternUtils.patternToSha1String(pattern), patternSha1);
    }

    @Override
    protected void onForgotPassword() {

        startActivity(new Intent(this, SetGestureLockActivity.class));

        // Finish with RESULT_FORGOT_PASSWORD.
        super.onForgotPassword();
    }

    // TODO: 实现该接口 Get saved pattern sha1.
    private String getPattenSha1(String username){
        return "qweqwrqwrt";
    }
}