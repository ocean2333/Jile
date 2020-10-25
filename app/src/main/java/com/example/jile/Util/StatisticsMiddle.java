package com.example.jile.Util;

import android.content.Context;

import com.example.jile.Bean.Bill;
import com.example.jile.Constant.Constants;
import com.example.jile.Database.Dao.BillDao;
import com.example.jile.LogoActivity;
import com.github.mikephil.charting.data.PieEntry;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class StatisticsMiddle {
    /**
     * 搜索时间区间内符合搜索要求的Bill并根据分类返回一个List
     * new PieEntry(30f,"一月")
     * 第一个为float的金额，第二个为string  "类型"
     * 排序按百分比从大到小
     * @param searchType 搜索类型，包括
     *   月度收入形式： "SEARCH_TYPE_MONTH"
     *              返回形式：在时间间隔不超过一年（30f,一月）
     *              超过一年（30f，19年5月）
     *   一级分类形式： “SEARCH_TYPE_FIRST_CLASS”,返回所有一级分类信息
     *
     *   二级分类形式:若是输入为"SEARCH_TYPE_SECOND_CLASS",返回所有二级分类信息；
     *              ：若是输入为“SEARCH_TYPE_SECOND_CLASS_IN_FIRST_CLASS”,返回一级分类firstClass下二级分类信息。
     *    账户形式     “SEARCH_TYPE_ACCOUNT”；p
     *    billt
     * */

    public static List<PieEntry> getpiebill(String searchType, String billtype, String firstClass, Date DateStart, Date DateEnd, Context context) throws ParseException {

        BillDao billDao = new BillDao(context, LogoActivity.sp.getString("loginUser",null));
        List<Bill> allBills = billDao.query();
        List<Bill> afterDeal = new LinkedList<>();
        Iterator<Bill> it = allBills.iterator();
        List<List<Bill>> res = new LinkedList<>();
        while (it.hasNext()) {
            Bill bill = (Bill) it.next();
            Date billDate = Constants.DATE_FORMAT_SIMPLE.parse(bill.getDate());
            if(!bill.getType().equals(billtype)) continue;
            if(billDate.getTime()<=DateEnd.getTime()&&billDate.getTime()>=DateStart.getTime() ){
                afterDeal.add(bill);
            }
        }
        List<PieEntry> retu = new LinkedList<>();
        Collections.sort(afterDeal, new Comparator<Bill>() {
            @Override
            public int compare(Bill a1, Bill a2) {
                Date d1 = null;
                try {
                    d1 = Constants.DATE_FORMAT_SIMPLE.parse(a1.getDate());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Date d2 = null;
                try {
                    d2 = Constants.DATE_FORMAT_SIMPLE.parse(a2.getDate());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if(d1.getTime()<d2.getTime()){
                    return -1;
                }else{
                    return 1;
                }
            }
        });
        switch (searchType){
            case Constants.SEARCH_TYPE_MONTH:{
                Calendar startCalendar = Calendar.getInstance();
                Calendar endCalendar = Calendar.getInstance();
                endCalendar.setTime(DateEnd);
                Iterator<Bill> swit = afterDeal.iterator();
                Calendar tmp = startCalendar;
                while (swit.hasNext()) {
                    Bill bill = (Bill) swit.next();
                    Date billDate = Constants.DATE_FORMAT_SIMPLE.parse(bill.getDate());
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(billDate);
                    if(tmp.get(Calendar.YEAR) == calendar.get(Calendar.YEAR) &&  tmp.get(Calendar.MONTH) == calendar.get(Calendar.MONTH)){
                        if(res.isEmpty()){
                            List<Bill> tp1 = new LinkedList<>();
                            tp1.add(bill);
                            res.add(tp1);
                        }
                        int i =res.size()-1;
                        List<Bill> tp2 = res.get(i);
                        tp2.add(bill);
                        res.set(i,tp2);
                    }
                    else{
                        List<Bill> tp3 = new LinkedList<>();
                        tp3.add(bill);
                        res.add(tp3);
                        tmp =calendar;
                    }
                }
                Iterator<List<Bill>> ite = res.iterator();
                while(ite.hasNext()){
                    List<Bill> bs = (List<Bill>)ite.next();
                    Iterator<Bill> ite2 = bs.iterator();
                    double itd =0;
                    while(ite2.hasNext()){
                        Bill bs2 = (Bill)ite2.next();
                        itd=itd+bs2.getNum().doubleValue();
                    }
                    String tis = bs.get(0).getDate();
                    String tism = new String(tis.substring(2,7));
                    PieEntry pe = new PieEntry((float) itd,tism);
                    retu.add(pe);
                }
                Collections.sort(retu, new Comparator<PieEntry>() {
                    @Override
                    public int compare(PieEntry o1, PieEntry o2) {
                        if(o1.getValue()<o2.getValue()) return 1;
                        if(o1.getValue()==o2.getValue()) return 0;
                        else return -1;
                    }
                });
            } break;
            case Constants.SEARCH_TYPE_FIRST_CLASS:{
                Map<String,Integer> mp = new HashMap<>();
                int index =0;
                Iterator<Bill> reit = afterDeal.iterator();

                while(reit.hasNext()){
                    Bill bill = (Bill)reit.next();
                    if(mp.containsKey(bill.getFirst())){
                        int c = mp.get(bill.getFirst());
                        List<Bill> a = res.get(c);
                        a.add(bill);
                        res.set(c,a);
                    }
                    else{
                        mp.put(bill.getFirst(),new Integer(index));
                        List<Bill> a = new LinkedList<>();
                        a.add(bill);
                        res.add(a);
                        index++;
                    }
                }
                Collections.sort(res, new Comparator<List<Bill>>() {
                    @Override
                    public int compare(List<Bill> o1, List<Bill> o2) {
                        Iterator<Bill> cit1 = o1.iterator();
                        Iterator<Bill> cit2 = o2.iterator();
                        BigDecimal c1=new BigDecimal("0");
                        BigDecimal c2=new BigDecimal("0");
                        while(cit1.hasNext()){
                            Bill bill = (Bill) cit1.next();
                            BigDecimal billNum = bill.getNum();
                            c1=c1.add(billNum);
                        }
                        while(cit2.hasNext()){
                            Bill bill = (Bill) cit2.next();
                            BigDecimal billNum = bill.getNum();
                            c2=c2.add(billNum);
                        }
                        return -c1.compareTo(c2);
                    }
                });
                Iterator<List<Bill>> ite = res.iterator();
                while(ite.hasNext()){
                    List<Bill> bs = (List<Bill>)ite.next();
                    Iterator<Bill> ite2 = bs.iterator();
                    double itd =0;
                    while(ite2.hasNext()){
                        Bill bs2 = (Bill)ite2.next();
                        itd=itd+bs2.getNum().doubleValue();
                    }
                    String tism = bs.get(0).getFirst();
                    PieEntry pe = new PieEntry((float) itd,tism);
                    retu.add(pe);
                }
                Collections.sort(retu, new Comparator<PieEntry>() {
                    @Override
                    public int compare(PieEntry o1, PieEntry o2) {
                        if(o1.getValue()<o2.getValue()) return 1;
                        if(o1.getValue()==o2.getValue()) return 0;
                        else return -1;
                    }
                });
            }break;
            case Constants.SEARCH_TYPE_SECOND_CLASS:{
                Map<String,Integer> mp = new HashMap<>();
                int index =0;
                Iterator<Bill> reit = afterDeal.iterator();

                while(reit.hasNext()){
                    Bill bill = (Bill)reit.next();
                    if(mp.containsKey(bill.getSecond())){
                        int c = mp.get(bill.getSecond());
                        List<Bill> a = res.get(c);
                        a.add(bill);
                        res.set(c,a);
                    }
                    else{
                        mp.put(bill.getSecond(),new Integer(index));
                        List<Bill> a = new LinkedList<>();
                        a.add(bill);
                        res.add(a);
                        index++;
                    }
                }

                Collections.sort(res, new Comparator<List<Bill>>() {
                    @Override
                    public int compare(List<Bill> o1, List<Bill> o2) {
                        Iterator<Bill> cit1 = o1.iterator();
                        Iterator<Bill> cit2 = o2.iterator();
                        BigDecimal c1=new BigDecimal("0");
                        BigDecimal c2=new BigDecimal("0");
                        while(cit1.hasNext()){
                            Bill bill = (Bill) cit1.next();
                            BigDecimal billNum = bill.getNum();
                            c1=c1.add(billNum);
                        }
                        while(cit2.hasNext()){
                            Bill bill = (Bill) cit2.next();
                            BigDecimal billNum = bill.getNum();
                            c2=c2.add(billNum);
                        }
                        return -c1.compareTo(c2);
                    }
                });
                Iterator<List<Bill>> ite = res.iterator();
                while(ite.hasNext()){
                    List<Bill> bs = (List<Bill>)ite.next();
                    Iterator<Bill> ite2 = bs.iterator();
                    double itd =0;
                    while(ite2.hasNext()){
                        Bill bs2 = (Bill)ite2.next();
                        itd=itd+bs2.getNum().doubleValue();
                    }
                    String tism = bs.get(0).getSecond();
                    PieEntry pe = new PieEntry((float) itd,tism);
                    retu.add(pe);
                }
                Collections.sort(retu, new Comparator<PieEntry>() {
                    @Override
                    public int compare(PieEntry o1, PieEntry o2) {
                        if(o1.getValue()<o2.getValue()) return 1;
                        if(o1.getValue()==o2.getValue()) return 0;
                        else return -1;
                    }
                });
            }break;
            case Constants.SEARCH_TYPE_SECOND_CLASS_IN_FIRST_CLASS:{
                BillDao billtDao = new BillDao(context, LogoActivity.sp.getString("loginUser",null));
                List<Bill> alltBills = billtDao.query();
                List<Bill> aftertDeal = new LinkedList<>();
                Iterator<Bill> iet = alltBills.iterator();
                List<List<Bill>> rtes = new LinkedList<>();
                while (iet.hasNext()) {
                    Bill bill = (Bill) iet.next();
                    Date billDate = Constants.DATE_FORMAT_SIMPLE.parse(bill.getDate());
                    if(!bill.getType().equals(billtype)) continue;
                    if(!bill.getFirst().equals(firstClass)) continue;
                    if(billDate.getTime()<=DateEnd.getTime()&&billDate.getTime()>=DateStart.getTime() ){
                        aftertDeal.add(bill);
                    }
                }

                Collections.sort(aftertDeal, new Comparator<Bill>() {
                    @Override
                    public int compare(Bill a1, Bill a2) {
                        Date d1 = null;
                        try {
                            d1 = Constants.DATE_FORMAT_SIMPLE.parse(a1.getDate());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Date d2 = null;
                        try {
                            d2 = Constants.DATE_FORMAT_SIMPLE.parse(a2.getDate());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        if(d1.getTime()<d2.getTime()){
                            return -1;
                        }else{
                            return 1;
                        }
                    }
                });

                Map<String,Integer> mp = new HashMap<>();
                int index =0;
                Iterator<Bill> reit = afterDeal.iterator();

                while(reit.hasNext()){
                    Bill bill = (Bill)reit.next();
                    if(mp.containsKey(bill.getSecond())){
                        int c = mp.get(bill.getSecond());
                        List<Bill> a = res.get(c);
                        a.add(bill);
                        res.set(c,a);
                    }
                    else{
                        mp.put(bill.getSecond(),new Integer(index));
                        List<Bill> a = new LinkedList<>();
                        a.add(bill);
                        res.add(a);
                        index++;
                    }
                }

                Collections.sort(res, new Comparator<List<Bill>>() {
                    @Override
                    public int compare(List<Bill> o1, List<Bill> o2) {
                        Iterator<Bill> cit1 = o1.iterator();
                        Iterator<Bill> cit2 = o2.iterator();
                        BigDecimal c1=new BigDecimal("0");
                        BigDecimal c2=new BigDecimal("0");
                        while(cit1.hasNext()){
                            Bill bill = (Bill) cit1.next();
                            BigDecimal billNum = bill.getNum();
                            c1=c1.add(billNum);
                        }
                        while(cit2.hasNext()){
                            Bill bill = (Bill) cit2.next();
                            BigDecimal billNum = bill.getNum();
                            c2=c2.add(billNum);
                        }
                        return -c1.compareTo(c2);
                    }
                });
                Iterator<List<Bill>> ite = res.iterator();
                while(ite.hasNext()){
                    List<Bill> bs = (List<Bill>)ite.next();
                    Iterator<Bill> ite2 = bs.iterator();
                    double itd =0;
                    while(ite2.hasNext()){
                        Bill bs2 = (Bill)ite2.next();
                        itd=itd+bs2.getNum().doubleValue();
                    }
                    String tism = bs.get(0).getSecond();
                    PieEntry pe = new PieEntry((float) itd,tism);
                    retu.add(pe);
                }
                Collections.sort(retu, new Comparator<PieEntry>() {
                    @Override
                    public int compare(PieEntry o1, PieEntry o2) {
                        if(o1.getValue()<o2.getValue()) return 1;
                        if(o1.getValue()==o2.getValue()) return 0;
                        else return -1;
                    }
                });
            }break;
            case Constants.SEARCH_TYPE_ACCOUNT:{
                Map<String,Integer> mp = new HashMap<>();
                int index =0;
                Iterator<Bill> reit = afterDeal.iterator();

                while(reit.hasNext()){
                    Bill bill = (Bill)reit.next();
                    if(mp.containsKey(bill.getAccountname())){
                        int c = mp.get(bill.getAccountname());
                        List<Bill> a = res.get(c);
                        a.add(bill);
                        res.set(c,a);
                    }
                    else{
                        mp.put(bill.getAccountname(),new Integer(index));
                        List<Bill> a = new LinkedList<>();
                        a.add(bill);
                        res.add(a);
                        index++;
                    }
                }
                Collections.sort(res, new Comparator<List<Bill>>() {
                    @Override
                    public int compare(List<Bill> o1, List<Bill> o2) {
                        Iterator<Bill> cit1 = o1.iterator();
                        Iterator<Bill> cit2 = o2.iterator();
                        BigDecimal c1=new BigDecimal("0");
                        BigDecimal c2=new BigDecimal("0");
                        while(cit1.hasNext()){
                            Bill bill = (Bill) cit1.next();
                            BigDecimal billNum = bill.getNum();
                            c1=c1.add(billNum);
                        }
                        while(cit2.hasNext()){
                            Bill bill = (Bill) cit2.next();
                            BigDecimal billNum = bill.getNum();
                            c2=c2.add(billNum);
                        }
                        return -c1.compareTo(c2);
                    }
                });
                Iterator<List<Bill>> ite = res.iterator();
                while(ite.hasNext()){
                    List<Bill> bs = (List<Bill>)ite.next();
                    Iterator<Bill> ite2 = bs.iterator();
                    double itd =0;
                    while(ite2.hasNext()){
                        Bill bs2 = (Bill)ite2.next();
                        itd=itd+bs2.getNum().doubleValue();
                    }
                    String tism = bs.get(0).getAccountname();
                    PieEntry pe = new PieEntry((float) itd,tism);
                    retu.add(pe);
                }
                Collections.sort(retu, new Comparator<PieEntry>() {
                    @Override
                    public int compare(PieEntry o1, PieEntry o2) {
                        if(o1.getValue()<o2.getValue()) return 1;
                        if(o1.getValue()==o2.getValue()) return 0;
                        else return -1;
                    }
                });

            }break;
        }

        return retu;
    }

}
