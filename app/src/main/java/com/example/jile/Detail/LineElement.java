package com.example.jile.Detail;

import android.view.View;

import com.example.jile.Bean.Bill;

import java.util.List;

public class LineElement {
    private String title;
    private String balance;
    private String income;
    private String cost;
    private List<View> bills;

    public LineElement(String title, String balance, String income, String cost, List<View> bills) {
        this.title = title;
        this.balance = balance;
        this.income = income;
        this.cost = cost;
        this.bills = bills;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public List<View> getBills() {
        return bills;
    }

    public void setBills(List<View> bills) {
        this.bills = bills;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }
}
