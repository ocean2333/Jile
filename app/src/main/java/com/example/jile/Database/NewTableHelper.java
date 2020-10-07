package com.example.jile.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


public class NewTableHelper {

    private String mName;
    private  final  DatabaseHelper mHelper;

    /**
     *
     * @param context
     * @param
     * @param name  数据表名  =  UserName+"_type"
     */
    public NewTableHelper(Context context , String name) {

        this.mName=name;
        mHelper = new DatabaseHelper(context);
    }

    public int create(){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        int status=0;
        String sql;
        sql = "create table "+mName+"_Account"+"(uuid varchar,type varchar,selfname varchar,balance decimal(15,2),currency varchar,iconId integer,other varchar)";
        db.execSQL(sql);

        sql = "create table "+mName+"_Mem"+"(uuid varchar,name varchar)";
        db.execSQL(sql);

        sql = "create table "+mName+"_Bill"+"(uuid varchar,num decimal(15,2),accountname varchar,first varchar,second varchar,member varchar,store varchar,date varchar,iconId integer,other varchar)";
        db.execSQL(sql);

        db.close();

        return  status;
    }
}
