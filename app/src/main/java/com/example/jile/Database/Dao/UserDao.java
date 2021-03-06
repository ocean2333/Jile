package com.example.jile.Database.Dao;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.jile.Bean.User;
import com.example.jile.Database.DatabaseHelper;

import java.util.LinkedList;
import java.util.List;

/**
 * 用户信息Dao
 */
public class UserDao {
    private final DatabaseHelper mHelper;
    public UserDao(Context context) {
        mHelper = new DatabaseHelper(context);
    }

    public  void insert(User user){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        String sql ="insert into All_User(uuid,name,password,securequestion,ans,tips,iconId,other,graphpass,budget) values(?,?,?,?,?,?,?,?,?,?)";
        db.execSQL(sql,new Object[]{user.getUuid(),user.getName(),user.getPassword(),user.getSecurequestion(),user.getAns(),user.getTips(),user.getIconId(),user.getOther(),user.getGraphpass(),user.getBudget()});
        db.close();
    }
    public  void delete(User user){
        /**
         * todo
         */
        SQLiteDatabase db = mHelper.getWritableDatabase();
        String sql = "delete from All_User where uuid = "+ "\""+user.getUuid()+"\"";
        db.execSQL(sql);
        db.close();
    }
    public  void update(User user){
        /**
         * todo
         */
        delete(user);
        insert(user);
    }

    public List<User> query(){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        String sql = "select * from All_User";
        Cursor cursor = db.rawQuery(sql,null);
        List<User> re = new LinkedList<User>();

        /**
         *     private String name;
         *     private String password;
         *     private String securequestion;
         *     private String ans;
         *     private String tips;
         *     private int iconId;
         *     private String other;
         */
        int uuidindex = cursor.getColumnIndex("uuid");
        int nameindex = cursor.getColumnIndex("name");
        int passwordindex = cursor.getColumnIndex("password");
        int securequestionindex = cursor.getColumnIndex("securequestion");
        int ansindex = cursor.getColumnIndex("ans");
        int tipsindex = cursor.getColumnIndex("tips");
        int iconIdindex = cursor.getColumnIndex("iconId");
        int otherindex = cursor.getColumnIndex("other");
        int graphpassindex = cursor.getColumnIndex("graphpass");
        int budgetindex = cursor.getColumnIndex("budget");

        while(cursor.moveToNext()){

            User m = new User(cursor.getString(uuidindex),cursor.getString(nameindex),cursor.getString(passwordindex),cursor.getString(securequestionindex),cursor.getString(ansindex),cursor.getString(tipsindex),cursor.getInt(iconIdindex),cursor.getString(otherindex),cursor.getString(graphpassindex),cursor.getString(budgetindex));
            re.add(m);
        }
        db.close();
        return re;
    }
    public List<User> querybyskey(String keyname,String value){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        String sql = "select * from All_User where "+keyname+" = "+"\""+value+"\"";
        Cursor cursor = db.rawQuery(sql,null);
        List<User> re = new LinkedList<User>();

        /**
         *     private String name;
         *     private String password;
         *     private String securequestion;
         *     private String ans;
         *     private String tips;
         *     private int iconId;
         *     private String other;
         */
        int uuidindex = cursor.getColumnIndex("uuid");
        int nameindex = cursor.getColumnIndex("name");
        int passwordindex = cursor.getColumnIndex("password");
        int securequestionindex = cursor.getColumnIndex("securequestion");
        int ansindex = cursor.getColumnIndex("ans");
        int tipsindex = cursor.getColumnIndex("tips");
        int iconIdindex = cursor.getColumnIndex("iconId");
        int otherindex = cursor.getColumnIndex("other");
        int graphpassindex = cursor.getColumnIndex("graphpass");
        int budgetindex = cursor.getColumnIndex("budget");
        while(cursor.moveToNext()){

            User m = new User(cursor.getString(uuidindex),cursor.getString(nameindex),cursor.getString(passwordindex),cursor.getString(securequestionindex),cursor.getString(ansindex),cursor.getString(tipsindex),cursor.getInt(iconIdindex),cursor.getString(otherindex),cursor.getString(graphpassindex),cursor.getString(budgetindex));
            re.add(m);
        }
        db.close();
        return re;
    }

}

