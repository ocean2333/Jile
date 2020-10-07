package com.example.jile.Bean;

public class User {


    public User(String uuid, String name, String password, String securequestion, String ans, String tips, int iconId, String other,String graphpass) {
        this.uuid = uuid;
        this.name = name;
        this.password = password;
        this.securequestion = securequestion;
        this.ans = ans;
        this.tips = tips;
        this.iconId = iconId;
        this.other = other;
        this.graphpass = graphpass;
    }
    private String pattenSha1;
    private String uuid;
    private String name;
    private String password;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSecurequestion() {
        return securequestion;
    }

    public void setSecurequestion(String securequestion) {
        this.securequestion = securequestion;
    }

    public String getAns() {
        return ans;
    }

    public void setAns(String ans) {
        this.ans = ans;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    private String securequestion;
    private String ans;
    private String tips;
    private int iconId;
    private String other;
    private String graphpass;

    public String getGraphpass() {
        return graphpass;
    }

    public void setGraphpass(String graphpass) {
        this.graphpass = graphpass;
    }
}
