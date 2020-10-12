package com.example.jile.Database.Dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.jile.Bean.Store;
import com.example.jile.Bean.Store;
import com.example.jile.Database.DatabaseHelper;

import java.util.LinkedList;
import java.util.List;

public class StoreDao
{
    private final DatabaseHelper mHelper;
    private final String mUsername;
    
    public StoreDao(Context context, String Username) {
        mUsername=Username;
        mHelper = new DatabaseHelper(context);
    }
    public  void insert(Store store){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        String sql ="insert into " + mUsername+"_Store(uuid,name) values(?,?)";
        db.execSQL(sql,new Object[]{store.getUuid(),store.getName()});
        db.close();
    }
    public  void delete(Store store){
        /**
         * todo
         */
        SQLiteDatabase db = mHelper.getWritableDatabase();
        String sql = "delete from "+mUsername+"_Mem"+" where uuid = "+"'"+store.getUuid()+"'";
        db.execSQL(sql);
        db.close();
    }
    public  void update(Store store){
        /**
         * todo
         */
        delete(store);
        insert(store);
    }
    public List<Store> query(){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        String sql = "select * from "+mUsername+"_Mem";
        Cursor cursor = db.rawQuery(sql,null);
        List<Store> re = new LinkedList<Store>();
        /**
         *         private String name;
         */
        int uuidindex = cursor.getColumnIndex("uuid");
        int nameindex = cursor.getColumnIndex("name");

        while(cursor.moveToNext()){

            Store m = new Store(cursor.getString(uuidindex),cursor.getString(nameindex));
            re.add(m);
        }
        db.close();
        return re;
    }
    public List<Store> querybyskey(String keyname,String value){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        String sql = "select * from "+mUsername+"_Mem  where "+keyname+" = "+"'"+value+"'";
        Cursor cursor = db.rawQuery(sql,null);
        List<Store> re = new LinkedList<Store>();
        /**
         *         private String name;
         */
        int uuidindex = cursor.getColumnIndex("uuid");
        int nameindex = cursor.getColumnIndex("name");

        while(cursor.moveToNext()){

            Store m = new Store(cursor.getString(uuidindex),cursor.getString(nameindex));
            re.add(m);
        }
        db.close();
        return re;
    }
}
