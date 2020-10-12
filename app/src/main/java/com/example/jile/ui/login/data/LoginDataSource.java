package com.example.jile.ui.login.data;

import android.widget.Toast;

import com.example.jile.Bean.Mem;
import com.example.jile.Bean.User;
import com.example.jile.LogoActivity;
import com.example.jile.MainView.MainActivity;
import com.example.jile.ui.login.LoginActivity;
import com.example.jile.ui.login.SetGestureLockActivity;

import java.io.IOException;
import java.util.List;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {
    public Result<Mem> login(String username, String password) {
        try {
            if(authenticate(username,password)){
                Mem User = userInfo(username);
                saveLoginUser(username);
                return new Result.Success<>(User);
            }else{
                return new Result.Failed("Wrong Password or User is not EXIST!!!");
            }
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    //
    private boolean authenticate(String username,String password){
        List<User> userList = LogoActivity.userDao.query();
        User user = null;
        for(User u:userList){
            if(u.getName().equals(username)){
                user = u;
            }
        }
        if(user==null){
            return false;
        }else{
            return user.getPassword().equals(password);
        }
    }

    //
    private Mem userInfo(String username){
        return new Mem(java.util.UUID.randomUUID().toString(),
                username);
    }

    // 往sharedpreference里存当前user
    private void saveLoginUser(String username){
        LogoActivity.sp.edit().putString("loginUser",username).apply();
    }

    // 暂不实现
    public void logout() {
    }
}