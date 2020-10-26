package com.example.jile.Database.Dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.jile.Bean.FirstClass;
import com.example.jile.Bean.Icon;
import com.example.jile.Database.DatabaseHelper;

import java.util.LinkedList;
import java.util.List;

public class IconDao {
    private final DatabaseHelper mHelper;
    private final String mUsername;

    public IconDao(Context context, String Username) {
        mUsername=Username;
        mHelper = new DatabaseHelper(context);
    }
    public  void insert(Icon icon){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        String sql ="insert into " + mUsername+"_Icon(uuid,name,type,iconId) values(?,?,?,?)";
        db.execSQL(sql,new Object[]{icon.getUuid(),icon.getName(),icon.getType(),icon.getIconId()});
        db.close();
    }
    public  void delete(Icon icon){
        /**
         * todo
         */
        SQLiteDatabase db = mHelper.getWritableDatabase();
        String sql = "delete from "+mUsername+"_Icon"+" where uuid = "+"'"+icon.getUuid()+"'";
        db.execSQL(sql);
        db.close();
    }
    public  void update(Icon icon){
        /**
         * todo
         */
        delete(icon);
        insert(icon);
    }
    public List<Icon> query(){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        String sql = "select * from "+mUsername+"_Icon";
        Cursor cursor = db.rawQuery(sql,null);
        List<Icon> re = new LinkedList<Icon>();
        /**
         *         private String name;
         */
        int uuidindex = cursor.getColumnIndex("uuid");
        int nameindex = cursor.getColumnIndex("name");
        int typeindex = cursor.getColumnIndex("type");
        int iconIdindex = cursor.getColumnIndex("iconId");


        while(cursor.moveToNext()){

            Icon i = new Icon(cursor.getString(uuidindex),cursor.getString(nameindex),cursor.getString(typeindex),cursor.getInt(iconIdindex));
            re.add(i);
        }
        db.close();
        return re;
    }
    public List<Icon> querybyskey(String keyname,String value) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        String sql = "select * from " + mUsername + "_Icon  where " + keyname + " = " + "'" + value + "'";
        Cursor cursor = db.rawQuery(sql, null);
        List<Icon> re = new LinkedList<>();
        /**
         *         private String name;
         */
        int uuidindex = cursor.getColumnIndex("uuid");
        int nameindex = cursor.getColumnIndex("name");
        int typeindex = cursor.getColumnIndex("type");
        int iconIdindex = cursor.getColumnIndex("iconId");

        while (cursor.moveToNext()) {

            Icon i = new Icon(cursor.getString(uuidindex), cursor.getString(nameindex), cursor.getString(typeindex), cursor.getInt(iconIdindex));
            re.add(i);
        }
        db.close();
        return re;
    }
}
