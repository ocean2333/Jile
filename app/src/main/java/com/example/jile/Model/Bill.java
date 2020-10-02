package com.example.jile.Model;

import java.math.BigDecimal;

public class Bill {
    private int type;
    private BigDecimal money;
    private String firstClass;
    private String secondClass;
    private String account;
    private String member;
    private String store;
    private String note;
    private String time;
    private int iconId;

    public Bill(int type, BigDecimal money, String firstClass, String secondClass, String account, String member, String store, String note,int iconId,String time) {
        this.type = type;
        this.money = money;
        this.firstClass = firstClass;
        this.secondClass = secondClass;
        this.account = account;
        this.member = member;
        this.store = store;
        this.note = note;
        this.iconId = iconId;
        this.time = time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getFirstClass() {
        return firstClass;
    }

    public void setFirstClass(String firstClass) {
        this.firstClass = firstClass;
    }

    public String getSecondClass() {
        return secondClass;
    }

    public void setSecondClass(String secondClass) {
        this.secondClass = secondClass;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
