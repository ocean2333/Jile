package com.example.jile.Model;

public class User {
    private String userName,password,hint,question,ans;
    private boolean isLogin;
    private String pattenSha1;
    public User(String userName, String password, String hint, String question, String ans,boolean isLogin,String pattenSha1) {
        this.userName = userName;
        this.password = password;//加密或者不加密 应该都行
        this.hint = hint;// 这个暂无
        this.question = question;
        this.ans = ans;
        this.isLogin = isLogin;
        this.pattenSha1 = pattenSha1;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public String getAns() {
        return ans;
    }

    public void setAns(String ans) {
        this.ans = ans;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPattenSha1() {
        return pattenSha1;
    }

    public void setPattenSha1(String pattenSha1) {
        this.pattenSha1 = pattenSha1;
    }
}
