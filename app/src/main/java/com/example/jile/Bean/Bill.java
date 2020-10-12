package com.example.jile.Bean;

import java.math.BigDecimal;

public class Bill{


    private String uuid;
    private String type;
    private BigDecimal num;
    private String accountname;  //此处为非user的
    private String first;
    private String second;
    private String member;
    private String store;
    private String date;
    private int iconId;
    private String note;

    public Bill(String uuid, String type, BigDecimal num, String accountname, String first, String second, String member, String store, String date, int iconId, String note) {
        this.uuid = uuid;
        this.type = type;
        this.num = num;
        this.accountname = accountname;
        this.first = first;
        this.second = second;
        this.member = member;
        this.store = store;
        this.date = date;
        this.iconId = iconId;
        this.note = note;
    }

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

    public BigDecimal getNum() {
        return num;
    }

    public void setNum(BigDecimal num) {
        this.num = num;
    }

    public String getAccountname() {
        return accountname;
    }

    public void setAccountname(String accountname) {
        this.accountname = accountname;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getSecond() {
        return second;
    }

    public void setSecond(String second) {
        this.second = second;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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
