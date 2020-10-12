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
        String sql ="insert into " + mUsername +"_Bill(uuid,type,num,accountname,first,second,member,store,date,iconId,note) values(?,?,?,?,?,?,?,?,?,?,?)";
        db.execSQL(sql,new Object[]{bill.getUuid(),bill.getType(),bill.getNum().toString(),bill.getAccountname(),bill.getFirst(),bill.getSecond(),bill.getMember(),bill.getStore(),bill.getDate(),bill.getIconId(),bill.getNote()});
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
        int typeindex = cursor.getColumnIndex("type");
        int numindex = cursor.getColumnIndex("num");
        int accountnameindex = cursor.getColumnIndex("accountname");
        int firstindex = cursor.getColumnIndex("first");
        int secondindex = cursor.getColumnIndex("second");
        int memberindex = cursor.getColumnIndex("member");
        int storeindex = cursor.getColumnIndex("store");
        int dateindex = cursor.getColumnIndex("date");
        int iconidindex = cursor.getColumnIndex("iconId");
        int noteindex = cursor.getColumnIndex("note");

        while(cursor.moveToNext()){
            Bill m = new Bill(cursor.getString(uuidindex),cursor.getString(typeindex),new BigDecimal(cursor.getString(numindex)),cursor.getString(accountnameindex),cursor.getString(firstindex),cursor.getString(secondindex),cursor.getString(memberindex),cursor.getString(storeindex),
                    cursor.getString(dateindex),cursor.getInt(iconidindex),cursor.getString(noteindex));
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
        int uuidindex = cursor.getColumnIndex("uuid");
        int typeindex = cursor.getColumnIndex("type");
        int numindex = cursor.getColumnIndex("num");
        int accountnameindex = cursor.getColumnIndex("accountname");
        int firstindex = cursor.getColumnIndex("first");
        int secondindex = cursor.getColumnIndex("second");
        int memberindex = cursor.getColumnIndex("member");
        int storeindex = cursor.getColumnIndex("store");
        int dateindex = cursor.getColumnIndex("date");
        int iconidindex = cursor.getColumnIndex("iconId");
        int noteindex = cursor.getColumnIndex("note");
        while(cursor.moveToNext()){

            Bill m = new Bill(cursor.getString(uuidindex),cursor.getString(typeindex),new BigDecimal(cursor.getString(numindex)),cursor.getString(accountnameindex),cursor.getString(firstindex),cursor.getString(secondindex),cursor.getString(memberindex),cursor.getString(storeindex),
                    cursor.getString(dateindex),cursor.getInt(iconidindex),cursor.getString(noteindex));
            re.add(m);
        }
        db.close();
        return re;
    }
    public List<Bill> querybytimerange(String starttime,String endtime){

        return null;
    }
    public List<Bill> querybytimefrom(String starttime){

        return null;
    }


}

