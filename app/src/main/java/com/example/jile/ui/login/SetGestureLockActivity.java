package com.example.jile.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.jile.Bean.Account;
import com.example.jile.Bean.User;
import com.example.jile.Constant.Constants;
import com.example.jile.Database.Dao.AccountDao;
import com.example.jile.Database.NewTableHelper;
import com.example.jile.LogoActivity;
import com.example.jile.R;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import me.zhanghai.android.patternlock.PatternUtils;
import me.zhanghai.android.patternlock.PatternView;
import me.zhanghai.android.patternlock.SetPatternActivity;

import static com.example.jile.LogoActivity.accountDao;

public class SetGestureLockActivity extends SetPatternActivity {
    private String username,password,question,ans;
    @Override
    protected void onSetPattern(List<PatternView.Cell> pattern) {
        String patternSha1 = PatternUtils.patternToSha1String(pattern);
        Bundle bundle = getIntent().getExtras();    //得到传过来的bundle
        username = bundle.getString("username");
        password = bundle.getString("password");
        question = bundle.getString("question");
        ans = bundle.getString("ans");
        addNewUserToDB(createNewUser(patternSha1));
        //addPatternSha1ToDB(patternSha1,bundle.getString("username"));
    }

    // TODO 有没有好点的跳转（暂不修改）
    @Override
    protected void onDestroy() {
        super.onDestroy();
        startActivity(new Intent(SetGestureLockActivity.this,LoginActivity.class));
    }

    //
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

    private User createNewUser(String patten){
        return new User(UUID.randomUUID().toString(),username,password,
                question,ans,"暂无",R.drawable.icon_dollar,"",patten,"1500");
    }

    // TODO 实现以下接口(待测试)
    private void addNewUserToDB(User user){
        LogoActivity.userDao.insert(user);
        NewTableHelper newTableHelper = new NewTableHelper(this,user.getName());
        newTableHelper.create();
        //Account account = new Account(UUID.randomUUID().toString(), Constants.CASH_ACCOUNT,"现金",new BigDecimal("0"),"CNY",R.drawable.icon_dollar,"");
        //new AccountDao(this,user.getName()).insert(account);
        Toast.makeText(SetGestureLockActivity.this,"got"+user.getName(),Toast.LENGTH_SHORT).show();
    }
}

