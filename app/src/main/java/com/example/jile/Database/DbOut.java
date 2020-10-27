package com.example.jile.Database;

import android.content.Context;

import com.example.jile.Bean.Account;
import com.example.jile.Bean.Bill;
import com.example.jile.Bean.FirstClass;
import com.example.jile.Bean.Icon;
import com.example.jile.Bean.Mem;
import com.example.jile.Bean.SecondClass;
import com.example.jile.Bean.Store;
import com.example.jile.Bean.User;
import com.example.jile.Constant.Constants;
import com.example.jile.Database.Dao.AccountDao;
import com.example.jile.Database.Dao.BillDao;
import com.example.jile.Database.Dao.FirstClassDao;
import com.example.jile.Database.Dao.IconDao;
import com.example.jile.Database.Dao.MemDao;
import com.example.jile.Database.Dao.SecondClassDao;
import com.example.jile.Database.Dao.StoreDao;
import com.example.jile.Database.Dao.UserDao;
import com.example.jile.LogoActivity;
import com.example.jile.Util.FileUtils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;


public class DbOut {

    /**
     *
     * @param absolutepath  目的路径
     * @param filename  数据包文件名 后缀一定是.data
     * @param context
     * @throws IOException
     */
    public static void UserDataToCSVZip(String absolutepath, String filename,Context context) throws IOException {
        File ff = new File(absolutepath);
        String a =absolutepath+"/xx__xx/";

        filename=new String("jile.data");
        new File(a).mkdir();


        //---------------------------------------------------------------
        //firstclass
        FileOutputStream fosfirstclass = new FileOutputStream(a+"/abc_firstclass.csv");
        OutputStreamWriter oswfirstclass = new OutputStreamWriter(fosfirstclass, "GBK");

        CSVFormat csvFormatfirstclass = CSVFormat.DEFAULT.withHeader("uuid", "type", "name","iconId");
        CSVPrinter csvPrinterfirstclass = new CSVPrinter(oswfirstclass, csvFormatfirstclass);

//        csvPrinter = CSVFormat.DEFAULT.withHeader("姓名", "年龄", "家乡").print(osw);
        FirstClassDao firstClassDao = new FirstClassDao(context, LogoActivity.sp.getString("loginUser",null));
        List<FirstClass> f =firstClassDao.query();
        for (FirstClass firstClass : f) {
            csvPrinterfirstclass.printRecord(firstClass.getUuid(), firstClass.getType(), firstClass.getName(),String.valueOf(firstClass.getIconId()));
        }
        csvPrinterfirstclass.flush();
        csvPrinterfirstclass.close();
        //---------------------------------------------------------------
        //secondclass
        FileOutputStream fossecondclass = new FileOutputStream(a+"/abc_secondclass.csv");
        OutputStreamWriter oswsecondclass = new OutputStreamWriter(fossecondclass, "GBK");

        CSVFormat csvFormatsecondclass = CSVFormat.DEFAULT.withHeader("uuid", "type","firstclass","name","iconId");
        CSVPrinter csvPrintersecondclass = new CSVPrinter(oswsecondclass, csvFormatsecondclass);

//        csvPrinter = CSVFormat.DEFAULT.withHeader("姓名", "年龄", "家乡").print(osw);
        SecondClassDao secondClassDao = new SecondClassDao(context, LogoActivity.sp.getString("loginUser",null));
        List<SecondClass> lsecondclass =secondClassDao.query();
        for (SecondClass secondClass : lsecondclass) {
            csvPrintersecondclass.printRecord(secondClass.getUuid(), secondClass.getType(), secondClass.getFirstclass(),secondClass.getName(),String.valueOf(secondClass.getIconId()));
        }
        csvPrintersecondclass.flush();
        csvPrintersecondclass.close();

        //---------------------------------------------------------------
        //Account
        FileOutputStream fosaccount = new FileOutputStream(a+"/abc_account.csv");
        OutputStreamWriter oswaccount = new OutputStreamWriter(fosaccount, "GBK");

        CSVFormat csvFormataccount = CSVFormat.DEFAULT.withHeader("uuid", "type", "selfname","balance","currency","iconId","note");
        CSVPrinter csvPrinteraccount = new CSVPrinter(oswaccount, csvFormataccount);

//        csvPrinter = CSVFormat.DEFAULT.withHeader("姓名", "年龄", "家乡").print(osw);
        AccountDao accounTDao = new AccountDao(context, LogoActivity.sp.getString("loginUser",null));
        List<Account> laccount =accounTDao.query();
        for (Account accounT : laccount) {
            csvPrinteraccount.printRecord(accounT.getUuid(), accounT.getType(), accounT.getSelfname(),accounT.getBalance().toString(),accounT.getCurrency(),String.valueOf(accounT.getIconId()),accounT.getNote());
        }
        csvPrinteraccount.flush();
        csvPrinteraccount.close();



        //---------------------------------------------------------------
        //Bill
        FileOutputStream fosbill = new FileOutputStream(a+"/abc_bill.csv");
        OutputStreamWriter oswbill = new OutputStreamWriter(fosbill, "GBK");

        CSVFormat csvFormatbill = CSVFormat.DEFAULT.withHeader("uuid", "type", "num","accountname","first","second","member","store","date","iconId","note");
        CSVPrinter csvPrinterbill = new CSVPrinter(oswbill, csvFormatbill);

//        csvPrinter = CSVFormat.DEFAULT.withHeader("姓名", "年龄", "家乡").print(osw);
        BillDao bilLDao = new BillDao(context, LogoActivity.sp.getString("loginUser",null));
        List<Bill> lbill =bilLDao.query();
        for (Bill bilL : lbill) {
            csvPrinterbill.printRecord(bilL.getUuid(), bilL.getType(),bilL.getNum().toString(), bilL.getAccountname(),bilL.getFirst(),bilL.getSecond(),bilL.getMember(),bilL.getStore(),bilL.getDate(),String.valueOf(bilL.getIconId()),bilL.getNote());
        }
        csvPrinterbill.flush();
        csvPrinterbill.close();

        //---------------------------------------------------------------
        //Mem
        FileOutputStream fosmem = new FileOutputStream(a+"/abc_mem.csv");
        OutputStreamWriter oswmem = new OutputStreamWriter(fosmem, "GBK");

        CSVFormat csvFormatmem = CSVFormat.DEFAULT.withHeader("uuid","name");
        CSVPrinter csvPrintermem = new CSVPrinter(oswmem, csvFormatmem);

//        csvPrinter = CSVFormat.DEFAULT.withHeader("姓名", "年龄", "家乡").print(osw);
        MemDao meMDao = new MemDao(context, LogoActivity.sp.getString("loginUser",null));
        List<Mem> lmem =meMDao.query();
        for (Mem meM : lmem) {
            csvPrintermem.printRecord(meM.getUuid(), meM.getName());
        }
        csvPrintermem.flush();
        csvPrintermem.close();

        //---------------------------------------------------------------
        //icon
        FileOutputStream fosicon = new FileOutputStream(a+"/abc_icon.csv");
        OutputStreamWriter oswicon = new OutputStreamWriter(fosmem, "GBK");

        CSVFormat csvFormaticon = CSVFormat.DEFAULT.withHeader("uuid","name","type","iconId");
        CSVPrinter csvPrintericon = new CSVPrinter(oswicon, csvFormaticon);

        IconDao iconDao = new IconDao(context, LogoActivity.sp.getString("loginUser",null));
        List<Icon> licon = iconDao.query();
        for(Icon icon:licon){
            csvPrintericon.printRecord(icon.getUuid(),icon.getName(),icon.getType(),String.valueOf(icon.getIconId()));
        }
        csvPrintericon.flush();
        csvPrintericon.close();


        //---------------------------------------------------------------
        //Store
        FileOutputStream fosstore = new FileOutputStream(a+"/abc_store.csv");
        OutputStreamWriter oswstore = new OutputStreamWriter(fosstore, "GBK");

        CSVFormat csvFormatstore = CSVFormat.DEFAULT.withHeader("uuid", "name");
        CSVPrinter csvPrinterstore = new CSVPrinter(oswstore, csvFormatstore);

//        csvPrinter = CSVFormat.DEFAULT.withHeader("姓名", "年龄", "家乡").print(osw);
        StoreDao storEDao = new StoreDao(context, LogoActivity.sp.getString("loginUser",null));
        List<Store> lstore =storEDao.query();
        for (Store storE : lstore) {
            csvPrinterstore.printRecord(storE.getUuid(), storE.getName());
        }
        csvPrinterstore.flush();
        csvPrinterstore.close();

        //---------------------------------------------------------------
        //User
        //---------------------------------------------------------------

        FileUtils.compressToZip(a,absolutepath,filename);

    }

}
