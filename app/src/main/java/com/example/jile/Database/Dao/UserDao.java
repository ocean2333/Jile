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
        String sql ="insert into All_User(uuid,name,password,securequestion,ans,tips,iconId,other) values(?,?,?,?,?,?,?,?)";
        db.execSQL(sql,new Object[]{user.getUuid(),user.getName(),user.getPassword(),user.getSecurequestion(),user.getAns(),user.getTips(),user.getIconId(),user.getOther()});
        db.close();

    }
    public  void delete(User user){
        /**
         * todo
         */
    }
    public  void update(){
        /**
         * todo
         */
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
        while(cursor.moveToNext()){

            User m = new User(cursor.getString(uuidindex),cursor.getString(nameindex),cursor.getString(passwordindex),cursor.getString(securequestionindex),cursor.getString(ansindex),cursor.getString(tipsindex),cursor.getInt(iconIdindex),cursor.getString(otherindex),cursor.getString(graphpassindex));
            re.add(m);
        }
        db.close();
        return re;
    }

}
