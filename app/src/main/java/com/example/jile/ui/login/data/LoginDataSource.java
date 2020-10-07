package com.example.jile.ui.login.data;

import com.example.jile.Bean.Mem;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {
    //TODO 实现以下接口
    public Result<Mem> login(String username, String password) {
        try {
            // TODO: 认证用户是否成功登录，并创建一个LoggedInUser 失败则返回
            Mem fakeUser =
                    new Mem(
                            java.util.UUID.randomUUID().toString(),
                            "Jane Doe");
            return new Result.Success<>(fakeUser);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    //TODO 记录登陆状态
    private void rememberLogin(){
        
    }

    // TODO 去除登陆状态，使得打开应用时需要重新认证
    public void logout() {

    }
}