package com.example.jile.Account;

import android.view.View;

import java.util.List;

public class LineElement {
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public List<View> getView() {
        return view;
    }

    public void setView(List<View> view) {
        this.view = view;
    }

    public LineElement(String name, String balance, List<View> view) {
        this.name = name;
        this.balance = balance;
        this.view = view;
    }

    String balance;
    List<View> view;
}
