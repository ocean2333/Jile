package com.example.jile.Database;

import android.content.Context;

import com.csvreader.CsvReader;
import com.example.jile.Bean.Account;
import com.example.jile.Bean.Bill;
import com.example.jile.Bean.FirstClass;
import com.example.jile.Bean.Icon;
import com.example.jile.Bean.Mem;
import com.example.jile.Bean.SecondClass;
import com.example.jile.Bean.Store;
import com.example.jile.Database.Dao.AccountDao;
import com.example.jile.Database.Dao.BillDao;
import com.example.jile.Database.Dao.FirstClassDao;
import com.example.jile.Database.Dao.IconDao;
import com.example.jile.Database.Dao.MemDao;
import com.example.jile.Database.Dao.SecondClassDao;
import com.example.jile.Database.Dao.StoreDao;
import com.example.jile.LogoActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class DbIn {
    private static final int BUFFER_SIZE = 4096;

    /**

     * zip解压

     * @param srcFile        zip源文件

     * @param destDirPath     解压后的目标文件夹

     * @throws RuntimeException 解压失败会抛出运行时异常

     */

    public static void unZip(File srcFile, String destDirPath) throws RuntimeException {
        long start = System.currentTimeMillis();

        // 判断源文件是否存在

        if (!srcFile.exists()) {
            throw new RuntimeException(srcFile.getPath() + "所指文件不存在");

        }

        // 开始解压

        ZipFile zipFile = null;

        try {
            zipFile = new ZipFile(srcFile);

            Enumeration<?> entries = zipFile.entries();

            while (entries.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) entries.nextElement();

                System.out.println("解压" + entry.getName());

                // 如果是文件夹，就创建个文件夹

                if (entry.isDirectory()) {
                    String dirPath = destDirPath + "/" + entry.getName();

                    File dir = new File(dirPath);

                    dir.mkdirs();

                } else {
                    // 如果是文件，就先创建一个文件，然后用io流把内容copy过去

                    File targetFile = new File(destDirPath + "/" + entry.getName());

                    // 保证这个文件的父文件夹必须要存在

                    if(!targetFile.getParentFile().exists()){
                        targetFile.getParentFile().mkdirs();

                    }

                    targetFile.createNewFile();

                    // 将压缩文件内容写入到这个文件中

                    InputStream is = zipFile.getInputStream(entry);

                    FileOutputStream fos = new FileOutputStream(targetFile);

                    int len;

                    byte[] buf = new byte[BUFFER_SIZE];

                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);

                    }

                    // 关流顺序，先打开的后关闭

                    fos.close();

                    is.close();

                }

            }

            long end = System.currentTimeMillis();

            System.out.println("解压完成，耗时：" + (end - start) +" ms");

        } catch (Exception e) {
            throw new RuntimeException("unzip error from ZipUtils", e);

        } finally {
            if(zipFile != null){
                try {
                    zipFile.close();

                } catch (IOException e) {
                    e.printStackTrace();

                }

            }

        }

    }
    public static List<FirstClass> readCsvFileFirstClass(String filePath){
        List<FirstClass> res = new LinkedList<>();
        try {
            ArrayList<String[]> csvList = new ArrayList<String[]>();
            CsvReader reader = new CsvReader(filePath+"abc_firstclass.csv",',', Charset.forName("GBK"));
          reader.readHeaders(); //跳过表头,不跳可以注释掉

            while(reader.readRecord()){
                csvList.add(reader.getValues()); //按行读取，并把每一行的数据添加到list集合
            }
            reader.close();
            //System.out.println("读取的行数："+csvList.size());
            for(int row=0;row<csvList.size();row++){
                FirstClass firstClass = new FirstClass(csvList.get(row)[0],csvList.get(row)[1],csvList.get(row)[2], Integer.parseInt(csvList.get(row)[3]));
                res.add(firstClass);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
    public static List<SecondClass> readCsvFileSecondClass(String filePath){
        List<SecondClass> res = new LinkedList<>();
        try {
            ArrayList<String[]> csvList = new ArrayList<String[]>();
            CsvReader reader = new CsvReader(filePath+"abc_secondclass.csv",',', Charset.forName("GBK"));
            reader.readHeaders(); //跳过表头,不跳可以注释掉

            while(reader.readRecord()){
                csvList.add(reader.getValues()); //按行读取，并把每一行的数据添加到list集合
            }
            reader.close();
            //System.out.println("读取的行数："+csvList.size());
            for(int row=0;row<csvList.size();row++){

                SecondClass secondClass = new SecondClass(csvList.get(row)[0],csvList.get(row)[1],csvList.get(row)[2],csvList.get(row)[3], Integer.parseInt(csvList.get(row)[4]));
                res.add(secondClass);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
    public static List<Mem> readCsvFileMem(String filePath){
        List<Mem> res = new LinkedList<>();
        try {
            ArrayList<String[]> csvList = new ArrayList<String[]>();
            CsvReader reader = new CsvReader(filePath+"abc_mem.csv",',', Charset.forName("GBK"));
            reader.readHeaders(); //跳过表头,不跳可以注释掉

            while(reader.readRecord()){
                csvList.add(reader.getValues()); //按行读取，并把每一行的数据添加到list集合
            }
            reader.close();
            //System.out.println("读取的行数："+csvList.size());
            for(int row=0;row<csvList.size();row++){

                Mem mem = new Mem(csvList.get(row)[0],csvList.get(row)[1]);
                res.add(mem);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
    public static List<Store> readCsvFileStore(String filePath){
        List<Store> res = new LinkedList<>();
        try {
            ArrayList<String[]> csvList = new ArrayList<String[]>();
            CsvReader reader = new CsvReader(filePath+"abc_store.csv",',', Charset.forName("GBK"));
            reader.readHeaders(); //跳过表头,不跳可以注释掉

            while(reader.readRecord()){
                csvList.add(reader.getValues()); //按行读取，并把每一行的数据添加到list集合
            }
            reader.close();
            //System.out.println("读取的行数："+csvList.size());
            for(int row=0;row<csvList.size();row++){

                Store store =new Store(csvList.get(row)[0],csvList.get(row)[1]);
                res.add(store);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
    public static List<Account> readCsvFileAccount(String filePath){
        List<Account> res = new LinkedList<>();
        try {
            ArrayList<String[]> csvList = new ArrayList<String[]>();
            CsvReader reader = new CsvReader(filePath+"abc_account.csv",',', Charset.forName("GBK"));
            reader.readHeaders(); //跳过表头,不跳可以注释掉

            while(reader.readRecord()){
                csvList.add(reader.getValues()); //按行读取，并把每一行的数据添加到list集合
            }
            reader.close();
            //System.out.println("读取的行数："+csvList.size());
            for(int row=0;row<csvList.size();row++){

                Account account = new Account(csvList.get(row)[0],csvList.get(row)[1],csvList.get(row)[2],new BigDecimal(csvList.get(row)[3]),csvList.get(row)[4],
                        Integer.parseInt(csvList.get(row)[5]),csvList.get(row)[6]);
                res.add(account);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
    public static List<Bill> readCsvFileBill(String filePath){
        List<Bill> res = new LinkedList<>();
        try {
            ArrayList<String[]> csvList = new ArrayList<String[]>();
            CsvReader reader = new CsvReader(filePath+"abc_bill.csv",',', Charset.forName("GBK"));
            reader.readHeaders(); //跳过表头,不跳可以注释掉

            while(reader.readRecord()){
                csvList.add(reader.getValues()); //按行读取，并把每一行的数据添加到list集合
            }
            reader.close();
            //System.out.println("读取的行数："+csvList.size());
            for(int row=0;row<csvList.size();row++){

                Bill bill = new Bill(csvList.get(row)[0],csvList.get(row)[1],new BigDecimal(csvList.get(row)[2]),csvList.get(row)[3]
                ,csvList.get(row)[4],csvList.get(row)[5],csvList.get(row)[6],csvList.get(row)[7],csvList.get(row)[8],Integer.valueOf(csvList.get(row)[9]).intValue(),csvList.get(row)[10]);
                res.add(bill);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public static List<Icon> readCsvFileIcon(String filePath){
        List<Icon> res = new LinkedList<>();
        try {
            ArrayList<String[]> csvList = new ArrayList<String[]>();
            CsvReader reader = new CsvReader(filePath+"abc_icon.csv",',', Charset.forName("GBK"));
            reader.readHeaders(); //跳过表头,不跳可以注释掉

            while(reader.readRecord()){
                csvList.add(reader.getValues()); //按行读取，并把每一行的数据添加到list集合
            }
            reader.close();
            //System.out.println("读取的行数："+csvList.size());
            for(int row=0;row<csvList.size();row++){
                Icon icon = new Icon(csvList.get(row)[0],csvList.get(row)[1],csvList.get(row)[2],Integer.parseInt(csvList.get(row)[3]));
                res.add(icon);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public static void zipToData(String zippath, Context context) {
        File f = new File(zippath);
        String tmpsp = zippath.substring(0,zippath.length()-9);
        f.renameTo(new File(zippath.substring(0,zippath.length()-5)+".zip"));
        unZip(f,tmpsp);
        String newpath = new String(tmpsp+"/xx__xx/");
        List<Account> la = readCsvFileAccount(newpath);
        AccountDao accountDao = new AccountDao(context, LogoActivity.sp.getString("loginUser",null));
        for(Account a : la){
            accountDao.insert(a);
        }
        List<Mem> lm = readCsvFileMem(newpath);
        MemDao memDao = new MemDao(context, LogoActivity.sp.getString("loginUser",null));
        for(Mem m:lm){
            memDao.insert(m);
        }
        List<FirstClass> lf = readCsvFileFirstClass(newpath);

        FirstClassDao  firstClassDao = new FirstClassDao(context, LogoActivity.sp.getString("loginUser",null));
        for(FirstClass fi : lf){
            firstClassDao.insert(fi);
        }

        List<SecondClass> ls = readCsvFileSecondClass(newpath);
        SecondClassDao secondClassDao = new SecondClassDao(context, LogoActivity.sp.getString("loginUser",null));
        for(SecondClass s:ls){
            secondClassDao.insert(s);
        }
        List<Bill> lb = readCsvFileBill(newpath);
        BillDao billDao = new BillDao(context, LogoActivity.sp.getString("loginUser",null));
        for(Bill b:lb){
            billDao.insert(b);
        }
        List<Store> lto= readCsvFileStore(newpath);
        StoreDao storeDao = new StoreDao(context, LogoActivity.sp.getString("loginUser",null));
        for(Store ss:lto){
            storeDao.insert(ss);
        }
        List<Icon> lic= readCsvFileIcon(newpath);
        IconDao iconDao = new IconDao(context, LogoActivity.sp.getString("loginUser",null));
        for(Icon i:lic){
            iconDao.insert(i);
        }

    }
}
