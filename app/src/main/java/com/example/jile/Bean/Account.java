package com.example.jile.Bean;

import java.math.BigDecimal;

public class Account {
    public Account(){

    }

    public Account(String uuid, String type, String selfname, BigDecimal balance, String currency, int iconId, String note) {
        this.uuid = uuid;
        this.type = type;
        this.selfname = selfname;
        this.balance = balance;
        this.currency = currency;
        this.iconId = iconId;
        this.note = note;
    }

    private String uuid;
    private String type;   //种类
    private String selfname;  //自定义名
    private BigDecimal balance;   //余额
    private String currency;   //币种
    private int iconId;
    private  String note;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSelfname() {
        return selfname;
    }

    public void setSelfname(String selfname) {
        this.selfname = selfname;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
