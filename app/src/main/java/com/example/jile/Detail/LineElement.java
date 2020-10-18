package com.example.jile.Detail;

import android.view.View;

import com.example.jile.Bean.Bill;

import java.util.List;

public class LineElement {
    private String title;
    private String Balance;
    private List<View> bills;

    public LineElement(String title, String balance, List<View> bills) {
        this.title = title;
        Balance = balance;
        this.bills = bills;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBalance() {
        return Balance;
    }

    public void setBalance(String balance) {
        Balance = balance;
    }

    public List<View> getBills() {
        return bills;
    }

    public void setBills(List<View> bills) {
        this.bills = bills;
    }
}
