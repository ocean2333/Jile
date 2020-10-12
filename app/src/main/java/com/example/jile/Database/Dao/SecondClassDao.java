package com.example.jile.Database.Dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.jile.Bean.SecondClass;
import com.example.jile.Bean.SecondClass;
import com.example.jile.Database.DatabaseHelper;

import java.util.LinkedList;
import java.util.List;

public class SecondClassDao {
    private final DatabaseHelper mHelper;
    private final String mUsername;

    public SecondClassDao(Context context, String Username) {
        mUsername=Username;
        mHelper = new DatabaseHelper(context);
    }
    public  void insert(SecondClass secondclass){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        String sql ="insert into " + mUsername+"_SecondClass(uuid,type,firstclass,name) values(?,?,?,?)";
        db.execSQL(sql,new Object[]{secondclass.getUuid(),secondclass.getType(),secondclass.getFirstclass(),secondclass.getName()});
        db.close();
    }
    public  void delete(SecondClass secondclass){
        /**
         * todo
         */
        SQLiteDatabase db = mHelper.getWritableDatabase();
        String sql = "delete from "+mUsername+"_SecondClass"+" where uuid = "+"'"+secondclass.getUuid()+"'";
        db.execSQL(sql);
        db.close();
    }
    public  void update(SecondClass secondclass){
        /**
         * todo
         */
        delete(secondclass);
        insert(secondclass);
    }
    public List<SecondClass> query(){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        String sql = "select * from "+mUsername+"_SecondClass";
        Cursor cursor = db.rawQuery(sql,null);
        List<SecondClass> re = new LinkedList<SecondClass>();
        /**
         *         private String name;
         */
        int uuidindex = cursor.getColumnIndex("uuid");
        int nameindex = cursor.getColumnIndex("name");
        int typeindex = cursor.getColumnIndex("type");
        int firstclassindex = cursor.getColumnIndex("firstclass");

        while(cursor.moveToNext()){

            SecondClass m = new SecondClass(cursor.getString(uuidindex),cursor.getString(typeindex),cursor.getString(firstclassindex),cursor.getString(nameindex));
            re.add(m);
        }
        db.close();
        return re;
    }
    public List<SecondClass> querybyskey(String keyname,String value){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        String sql = "select * from "+mUsername+"_SecondClass  where "+keyname+" = "+"'"+value+"'";
        Cursor cursor = db.rawQuery(sql,null);
        List<SecondClass> re = new LinkedList<SecondClass>();
        /**
         *         private String name;
         */
        int uuidindex = cursor.getColumnIndex("uuid");
        int nameindex = cursor.getColumnIndex("name");
        int typeindex = cursor.getColumnIndex("type");
        int firstclassindex = cursor.getColumnIndex("firstclass");
        while(cursor.moveToNext()){

            SecondClass m = new SecondClass(cursor.getString(uuidindex),cursor.getString(typeindex),cursor.getString(firstclassindex),cursor.getString(nameindex));
            re.add(m);
        }
        db.close();
        return re;
    }
}
