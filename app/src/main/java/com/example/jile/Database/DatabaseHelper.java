package com.example.jile.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.jile.Constant.Constants;

public class DatabaseHelper extends SQLiteOpenHelper {
    /**
     *  @param context
     *
     */

    public DatabaseHelper(@Nullable Context context) {
        super(context, Constants.DATA_BASE_NAME, null, Constants.VERSION_CODE);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql;
        sql = "create table "+"All_User"+"(uuid,varchar,name varchar,password varchar,securequestion varchar,ans varchar,tips varchar,iconId integer,other varchar,graphpass varchar, budget varchar)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
