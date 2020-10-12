package com.example.jile.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.jile.Bean.Account;
import com.example.jile.Bean.FirstClass;
import com.example.jile.Bean.Mem;
import com.example.jile.Bean.SecondClass;
import com.example.jile.Bean.Store;
import com.example.jile.Constant.Constants;
import com.example.jile.Database.Dao.AccountDao;
import com.example.jile.Database.Dao.FirstClassDao;
import com.example.jile.Database.Dao.MemDao;
import com.example.jile.Database.Dao.SecondClassDao;
import com.example.jile.Database.Dao.StoreDao;
import com.example.jile.R;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;


public class NewTableHelper {

    private String mName;
    private  final  DatabaseHelper mHelper;
    private Context mContext;

    /**
     *
     * @param context
     * @param
     * @param name  数据表名  =  UserName+"_type"
     */
    public NewTableHelper(Context context , String name) {

        this.mName=name;
        mContext =context;
        mHelper = new DatabaseHelper(context);
    }

    public int create(){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        int status=0;
        String sql;
        sql = "create table "+mName+"_Account"+"(uuid varchar,type varchar,selfname varchar,balance varchar,currency varchar,iconId integer,note varchar)";
        db.execSQL(sql);

        sql = "create table "+mName+"_Mem"+"(uuid varchar,name varchar)";
        db.execSQL(sql);

        sql = "create table "+mName+"_Bill"+"(uuid varchar,type varchar,num decimal(15,2),accountname varchar,first varchar,second varchar,member varchar,store varchar,date varchar,iconId integer,note varchar)";
        db.execSQL(sql);

        sql = "create table "+mName+"_Store"+"(uuid varchar,name varchar)";
        db.execSQL(sql);

        sql = "create table "+mName+"_FirstClass"+"(uuid varchar,type varchar,name varchar)";
        db.execSQL(sql);

        sql = "create table "+mName+"_SecondClass"+"(uuid varchar,type varchar,firstclass varchar,name varchar)";

        db.execSQL(sql);

        db.close();

        AccountDao accountDao =new AccountDao(mContext,mName);
        StoreDao storeDao =new StoreDao(mContext,mName);
        MemDao memDao = new MemDao(mContext,mName);
        FirstClassDao firstClassDao = new FirstClassDao(mContext,mName);
        SecondClassDao secondClassDao = new SecondClassDao(mContext,mName);
        accountDao.insert(new Account(UUID.randomUUID().toString(),
                Constants.CASH_ACCOUNT,"现金",new BigDecimal("0"),"CNY", R.drawable.icon_cny,""));
        Store store = new Store(UUID.randomUUID().toString(),"家");
        storeDao.insert(store);
        store = new Store(UUID.randomUUID().toString(),"学校");
        storeDao.insert(store);
        store = new Store(UUID.randomUUID().toString(),"单位");
        storeDao.insert(store);
        memDao.insert(new Mem(UUID.randomUUID().toString(),"我"));

        Map<String, List<String>> map = new HashMap<>();
        map.put("食物饮料",new LinkedList<String>(Arrays.asList(new String[]{"早中晚饭","零食饮料","水果"}) ));
        map.put("衣服食品",new LinkedList<String>(Arrays.asList(new String[]{"衣帽裤袜","鞋子包包","化妆品","饰品"}) ));
        map.put("居家生活",new LinkedList<String>(Arrays.asList(new String[]{"生活用品","房租水电","维修保养"}) ));
        map.put("行车交通",new LinkedList<String>(Arrays.asList(new String[]{"公共交通","尊贵交通"}) ));
        map.put("休闲娱乐",new LinkedList<String>(Arrays.asList(new String[]{"运动健身","游戏","电影聚会"}) ));
        map.put("学习培训",new LinkedList<String>(Arrays.asList(new String[]{"学习资料","培训进修"}) ));
        map.put("医疗保障",new LinkedList<String>(Arrays.asList(new String[]{"治疗","保健品"}) ));

        for(String x: new String[]{"食物饮料", "衣服食品", "居家生活", "行车交通", "休闲娱乐", "学习培训", "医疗保障"}){
            firstClassDao.insert(new FirstClass(UUID.randomUUID().toString(),Constants.COST,x));
            for (String z:map.get(x)) {
                secondClassDao.insert(new SecondClass(UUID.randomUUID().toString(),Constants.COST,x,z));
            }
        }
        map.clear();
        map.put("职业收入",new LinkedList<String>(Arrays.asList(new String[]{"工资收入","投资收入"}) ));
        map.put("其他收入",new LinkedList<String>(Arrays.asList(new String[]{"生活费","赠与收入"}) ));
        for(String x: new String[]{"职业收入", "其他收入"}){
            firstClassDao.insert(new FirstClass(UUID.randomUUID().toString(),Constants.INCOME,x));
            for (String z:map.get(x)) {
                secondClassDao.insert(new SecondClass(UUID.randomUUID().toString(),Constants.INCOME,x,z));
            }
        }
        return  status;
    }
}
