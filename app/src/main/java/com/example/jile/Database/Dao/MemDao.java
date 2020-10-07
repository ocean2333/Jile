package com.example.jile.Database.Dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.jile.Bean.Mem;
import com.example.jile.Database.DatabaseHelper;

import java.util.LinkedList;
import java.util.List;

/**
 * 用户成员Dao
 */
public class MemDao {
    private final DatabaseHelper mHelper;
    private final String mUsername;
    public MemDao(Context context,String Username) {
        mUsername=Username;
        mHelper = new DatabaseHelper(context);
    }
    public  void insert(Mem mem){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        String sql ="insert into " + mUsername+"_Mem(uuid,name) values(?,?)";
        db.execSQL(sql,new Object[]{mem.getUuid(),mem.getName()});
        db.close();
    }
    public  void delete(Mem mem){
        /**
         * todo
         */
    }
    public  void update(){
        /**
         * todo
         */
    }
    public List<Mem> query(){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        String sql = "select * from "+mUsername+"_Mem";
        Cursor cursor = db.rawQuery(sql,null);
        List<Mem> re = new LinkedList<Mem>();
        /**
         *         private String name;
         */
        int uuidindex = cursor.getColumnIndex("uuid");
        int nameindex = cursor.getColumnIndex("name");

        while(cursor.moveToNext()){

            Mem m = new Mem(cursor.getString(uuidindex),cursor.getString(nameindex));
            re.add(m);
        }
        db.close();
        return re;
    }
}
