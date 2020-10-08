package com.example.jile.Database.Dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.jile.Bean.Bill;
import com.example.jile.Database.DatabaseHelper;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

/**
 * 收支明细Dao
 */
public class BillDao {
    private final DatabaseHelper mHelper;
    private final String mUsername;
    public BillDao(Context context, String Username) {
        mUsername =Username;
        mHelper = new DatabaseHelper(context);
    }
    public  void insert(Bill bill){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        String sql ="insert into " + mUsername +"_Bill(uuid,num,accountname,first,second,member,store,date,iconId,other) values(?,?,?,?,?,?,?,?,?,?)";
        db.execSQL(sql,new Object[]{bill.getUuid(),bill.getNum(),bill.getAccountname(),bill.getFirst(),bill.getSecond(),bill.getMember(),bill.getStore(),bill.getDate(),bill.getIconId(),bill.getOther()});
        db.close();
    }
    public  void delete(Bill bill){
        /**
         * todo
         */
        SQLiteDatabase db = mHelper.getWritableDatabase();
        String sql = "delete from "+mUsername+"_Bill"+" where uuid = "+"'"+bill.getUuid()+"'";
        db.execSQL(sql);
        db.close();
    }
    public  void update(Bill bill){
        /**
         * todo
         */
        delete(bill);
        insert(bill);
    }
    public List<Bill> query(){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        String sql = "select * from "+ mUsername +"_Bill";
        Cursor cursor = db.rawQuery(sql,null);
        List<Bill> re = new LinkedList<Bill>();
        /**
         *     private BigDecimal num;
         *     private String accountname;
         *     private String first;
         *     private String second;
         *     private String member;
         *     private String store;
         *     private String date;
         *     private int iconId;
         *     private String other;
         */
        int uuidindex = cursor.getColumnIndex("uuid");
        int numindex = cursor.getColumnIndex("num");
        int accountnameindex = cursor.getColumnIndex("accountname");
        int firstindex = cursor.getColumnIndex("first");
        int secondindex = cursor.getColumnIndex("second");
        int memberindex = cursor.getColumnIndex("member");
        int storeindex = cursor.getColumnIndex("store");
        int dateindex = cursor.getColumnIndex("date");
        int iconidindex = cursor.getColumnIndex("iconId");
        int otherindex = cursor.getColumnIndex("other");

        while(cursor.moveToNext()){

            Bill m = new Bill(cursor.getString(uuidindex),new BigDecimal(cursor.getDouble(numindex)),cursor.getString(accountnameindex),cursor.getString(firstindex),cursor.getString(secondindex),cursor.getString(memberindex),cursor.getString(storeindex),cursor.getString(dateindex),cursor.getInt(iconidindex),cursor.getString(otherindex));
            re.add(m);
        }
        db.close();
        return re;
    }

    public List<Bill> querybyskey(String keyname,String value){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        String sql = "select * from "+ mUsername +"_Bill where "+keyname+" = "+"'"+value+"'";
        Cursor cursor = db.rawQuery(sql,null);
        List<Bill> re = new LinkedList<Bill>();
        /**
         *     private BigDecimal num;
         *     private String accountname;
         *     private String first;
         *     private String second;
         *     private String member;
         *     private String store;
         *     private String date;
         *     private int iconId;
         *     private String other;
         */
        int uuidindex = cursor.getColumnIndex("uuid");
        int numindex = cursor.getColumnIndex("num");
        int accountnameindex = cursor.getColumnIndex("accountname");
        int firstindex = cursor.getColumnIndex("first");
        int secondindex = cursor.getColumnIndex("second");
        int memberindex = cursor.getColumnIndex("member");
        int storeindex = cursor.getColumnIndex("store");
        int dateindex = cursor.getColumnIndex("date");
        int iconidindex = cursor.getColumnIndex("iconId");
        int otherindex = cursor.getColumnIndex("other");

        while(cursor.moveToNext()){

            Bill m = new Bill(cursor.getString(uuidindex),new BigDecimal(cursor.getDouble(numindex)),cursor.getString(accountnameindex),cursor.getString(firstindex),cursor.getString(secondindex),cursor.getString(memberindex),cursor.getString(storeindex),cursor.getString(dateindex),cursor.getInt(iconidindex),cursor.getString(otherindex));
            re.add(m);
        }
        db.close();
        return re;
    }
}

