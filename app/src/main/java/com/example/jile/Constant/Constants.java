package com.example.jile.Constant;

import android.icu.text.SimpleDateFormat;

import java.util.logging.SimpleFormatter;

public class Constants {
    public static final String DATA_BASE_NAME = "jile.db";
    public static final int VERSION_CODE = 1;
    public static final String CASH_ACCOUNT = "cashAccount";
    public static final String BANK_ACCOUNT = "bankAccount";
    public static final String NET_ACCOUNT = "netAccount";
    public static final String OTHER_ACCOUNT = "otherAccount";
    public static final SimpleDateFormat DATE_FORMAT_SIMPLE = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
    public static final SimpleDateFormat DATE_FORMAT_COMPLEX = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
    public static final SimpleDateFormat DATE_FORMAT_MONTH_DAY = new SimpleDateFormat("MM月dd日");
    public static final String COST = "cost";
    public static final String INCOME = "income";
    public static final String TRANSFER = "transfer";
}
