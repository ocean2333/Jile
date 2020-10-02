package com.example.jile.Model;

import java.math.BigDecimal;

public class Account {
    private String name;
    private int iconId;
    private BigDecimal money;
    private String typeOfMoney;
    private String note;
    private int accountType;
    public Account(String name,int iconId,BigDecimal money,String typeOfMoney,String note,int accountType){
        this.name = name;
        this.iconId = iconId;
        this.money = money;
        this.typeOfMoney = typeOfMoney;
        this.note = note;
        this.accountType = accountType;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getTypeOfMoney() {
        return typeOfMoney;
    }

    public void setTypeOfMoney(String typeOfMoney) {
        this.typeOfMoney = typeOfMoney;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int icon) {
        this.iconId = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAccountType() {
        return accountType;
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }
}
