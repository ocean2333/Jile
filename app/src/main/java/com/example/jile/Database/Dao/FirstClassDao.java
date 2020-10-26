package com.example.jile.Database.Dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.jile.Bean.FirstClass;
import com.example.jile.Database.DatabaseHelper;

import java.util.LinkedList;
import java.util.List;

public class FirstClassDao {
    private final DatabaseHelper mHelper;
    private final String mUsername;

    public FirstClassDao(Context context, String Username) {
        mUsername=Username;
        mHelper = new DatabaseHelper(context);
    }
    public  void insert(FirstClass firstclass){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        String sql ="insert into " + mUsername+"_FirstClass(uuid,type,name) values(?,?,?)";
        db.execSQL(sql,new Object[]{firstclass.getUuid(),firstclass.getType() ,firstclass.getName()});
        db.close();
    }
    public  void delete(FirstClass firstclass){
        /**
         * todo
         */
        SQLiteDatabase db = mHelper.getWritableDatabase();
        String sql = "delete from "+mUsername+"_FirstClass"+" where uuid = "+"'"+firstclass.getUuid()+"'";
        db.execSQL(sql);
        db.close();
    }
    public  void update(FirstClass firstclass){
        /**
         * todo
         */
        delete(firstclass);
        insert(firstclass);
    }
    public List<FirstClass> query(){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        String sql = "select * from "+mUsername+"_FirstClass";
        Cursor cursor = db.rawQuery(sql,null);
        List<FirstClass> re = new LinkedList<FirstClass>();
        /**
         *         private String name;
         */
        int uuidindex = cursor.getColumnIndex("uuid");
        int nameindex = cursor.getColumnIndex("name");
        int typeindex = cursor.getColumnIndex("type");


        while(cursor.moveToNext()){

            FirstClass m = new FirstClass(cursor.getString(uuidindex),cursor.getString(typeindex),cursor.getString(nameindex));
            re.add(m);
        }
        db.close();
        return re;
    }
    public List<FirstClass> querybyskey(String keyname,String value){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        String sql = "select * from "+mUsername+"_FirstClass  where "+keyname+" = "+"'"+value+"'";
        Cursor cursor = db.rawQuery(sql,null);
        List<FirstClass> re = new LinkedList<FirstClass>();
        /**
         *         private String name;
         */
        int uuidindex = cursor.getColumnIndex("uuid");
        int nameindex = cursor.getColumnIndex("name");
        int typeindex = cursor.getColumnIndex("type");

        while(cursor.moveToNext()){

            FirstClass m = new FirstClass(cursor.getString(uuidindex),cursor.getString(typeindex),cursor.getString(nameindex));
            re.add(m);
        }
        db.close();
        return re;
    }
}
