package com.example.jile.Database.Dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.jile.Bean.Account;
import com.example.jile.Database.DatabaseHelper;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

/**
 * 账户表Dao
 */
public class AccountDao {

    private final DatabaseHelper mHelper;
    private final String mUsername;

    public AccountDao(Context context, String Username) {
        mHelper = new DatabaseHelper(context);
        mUsername = Username;
    }

    public  void insert(Account account){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        String sql ="insert into " + mUsername +"_Account(uuid,type,selfname,balance,currency,iconId,note) values(?,?,?,?,?,?,?)";
        db.execSQL(sql,new Object[]{account.getUuid(),account.getType(),account.getSelfname(),account.getBalance().toString(),account.getCurrency(),account.getIconId(),account.getNote()});
        db.close();
    }

    public  void delete(Account account){
        /**
         * todo
         */
        SQLiteDatabase db = mHelper.getWritableDatabase();
        String sql = "delete from "+mUsername+"_Account"+" where uuid = "+"\""+account.getUuid()+"\"";
        db.execSQL(sql);
        db.close();
    }

    public  void update(Account account){
        /**
         * todo
         */
        delete(account);
        insert(account);
    }

    public List<Account> query(){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        String sql = "select * from "+mUsername+"_Account";
        Cursor cursor = db.rawQuery(sql,null);
        List<Account> re = new LinkedList<Account>();

        /**
         *     private String type;   //种类
         *     private String selfname;  //自定义名
         *     private BigDecimal balance;   //余额
         *     private String currency;   //币种
         *     private int iconId;
         *     private  String other;
         */
        int uuidindex = cursor.getColumnIndex("uuid");
        int typeindex = cursor.getColumnIndex("type");
        int selfnameindex = cursor.getColumnIndex("selfname");
        int balanceindex = cursor.getColumnIndex("balance");
        int currencyindex = cursor.getColumnIndex("currency");
        int iconIdindex = cursor.getColumnIndex("iconId");
        int noteindex = cursor.getColumnIndex("note");
        while(cursor.moveToNext()){
            Account m = new Account(cursor.getString(uuidindex),cursor.getString(typeindex),cursor.getString(selfnameindex),new BigDecimal(cursor.getString(balanceindex)),cursor.getString(currencyindex),cursor.getInt(iconIdindex),cursor.getString(noteindex));
            re.add(m);
        }
        db.close();
        return re;
    }

    public List<Account> querybyskey(String keyname,String value){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        String sql = "select * from "+mUsername+"_Account where "+keyname+" = "+"\""+value+"\"";
        Cursor cursor = db.rawQuery(sql,null);
        List<Account> re = new LinkedList<Account>();

        /**
         *     private String type;   //种类
         *     private String selfname;  //自定义名
         *     private BigDecimal balance;   //余额
         *     private String currency;   //币种
         *     private int iconId;
         *     private  String other;
         */
        int uuidindex = cursor.getColumnIndex("uuid");
        int typeindex = cursor.getColumnIndex("type");
        int selfnameindex = cursor.getColumnIndex("selfname");
        int balanceindex = cursor.getColumnIndex("balance");
        int currencyindex = cursor.getColumnIndex("currency");
        int iconIdindex = cursor.getColumnIndex("iconId");
        int noteindex = cursor.getColumnIndex("note");
        while(cursor.moveToNext()){

            Account m = new Account(cursor.getString(uuidindex),cursor.getString(typeindex),cursor.getString(selfnameindex),new BigDecimal(cursor.getString(balanceindex)),cursor.getString(currencyindex),cursor.getInt(iconIdindex),cursor.getString(noteindex));
            re.add(m);
        }
        db.close();
        return re;
    }
}
