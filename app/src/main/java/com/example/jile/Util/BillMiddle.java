package com.example.jile.Util;

import android.content.Context;

import com.example.jile.Bean.Account;
import com.example.jile.Bean.Bill;
import com.example.jile.Bean.FirstClass;
import com.example.jile.Bean.Mem;
import com.example.jile.Bean.SecondClass;
import com.example.jile.Bean.Store;
import com.example.jile.Constant.Constants;
import com.example.jile.Database.Dao.BillDao;
import com.example.jile.LogoActivity;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


/**
 * 搜索时间区间内符合搜索要求的Bill并根据分类返回一个List
 * 例如：
 *     搜索时间-日时，每个时间区间内带有bill的日子的所有bill都加入到一个单独的list里，然后
 * 把所有list<bill>按日期由近到远排序
 *     搜索一级分类时，把每个时间区间内带有bill的一级分类的所有bill都加入到一个单独的list里，
 * 然后把所有list<bill>按支出bill的总金额大小排序，支出越多越前
 * @param searchType 搜索类型，包括SEARCHTYPE_DAY = "day";
 *                                SEARCHTYPE_WEEK = "week";
 *                                SEARCHTYPE_MONTH = "month";
 *                                SEARCHTYPE_YEAR = "year",
 *                                FirstClass,
 *                                SecondClass,
 *                                Account,
 *                                Mem,
 *                                Store
 * @param DateStart Date类型的时间，包括这一天
 * @param DateEnd Date类型的时间，包括这一天
 * */
public class BillMiddle {
    public static List<List<Bill>>getBill(FirstClass searchType, Date DateStart, Date DateEnd, Context context) throws ParseException {
        BillDao billDao = new BillDao(context, LogoActivity.sp.getString("loginUser",null));
        List<Bill> allBills = billDao.query();
        List<Bill> afterDeal = new LinkedList<>();
        Iterator<Bill> it = allBills.iterator();
        List<List<Bill>> res = new LinkedList<>();
        while (it.hasNext()) {
            Bill bill = (Bill) it.next();
            Date billDate = Constants.DATE_FORMAT_SIMPLE.parse(bill.getDate());
            if(bill.getType().equals(Constants.TRANSFER)) continue;
            if(billDate.getTime()<=DateEnd.getTime()&&billDate.getTime()>=DateStart.getTime()){
                afterDeal.add(bill);
            }
        }
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
        return res;
    }
    public static List<List<Bill>>getBill(Mem searchType, Date DateStart, Date DateEnd, Context context) throws ParseException {
        BillDao billDao = new BillDao(context, LogoActivity.sp.getString("loginUser",null));
        List<Bill> allBills = billDao.query();
        List<Bill> afterDeal = new LinkedList<>();
        Iterator<Bill> it = allBills.iterator();
        List<List<Bill>> res = new LinkedList<>();
        while (it.hasNext()) {
            Bill bill = (Bill) it.next();
            Date billDate = Constants.DATE_FORMAT_SIMPLE.parse(bill.getDate());
            if(bill.getType().equals(Constants.TRANSFER)) continue;
            if(billDate.getTime()<=DateEnd.getTime()&&billDate.getTime()>=DateStart.getTime()){
                afterDeal.add(bill);
            }
        }
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
        Map<String,Integer> mp = new HashMap<>();
        int index =0;
        Iterator<Bill> reit = afterDeal.iterator();

        while(reit.hasNext()){
            Bill bill = (Bill)reit.next();
            if(mp.containsKey(bill.getMember())){
                int c = mp.get(bill.getMember());
                List<Bill> a = res.get(c);
                a.add(bill);
                res.set(c,a);
            }
            else{
                mp.put(bill.getMember(),new Integer(index));
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
        return res;
    }
    public static List<List<Bill>>getBill(Store searchType, Date DateStart, Date DateEnd, Context context) throws ParseException {
        BillDao billDao = new BillDao(context, LogoActivity.sp.getString("loginUser",null));
        List<Bill> allBills = billDao.query();
        List<Bill> afterDeal = new LinkedList<>();
        Iterator<Bill> it = allBills.iterator();
        List<List<Bill>> res = new LinkedList<>();
        while (it.hasNext()) {
            Bill bill = (Bill) it.next();
            Date billDate = Constants.DATE_FORMAT_SIMPLE.parse(bill.getDate());
            if(bill.getType().equals(Constants.TRANSFER)) continue;
            if(billDate.getTime()<=DateEnd.getTime()&&billDate.getTime()>=DateStart.getTime()){
                afterDeal.add(bill);
            }
        }
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
        Map<String,Integer> mp = new HashMap<>();
        int index =0;
        Iterator<Bill> reit = afterDeal.iterator();

        while(reit.hasNext()){
            Bill bill = (Bill)reit.next();
            if(mp.containsKey(bill.getStore())){
                int c = mp.get(bill.getStore());
                List<Bill> a = res.get(c);
                a.add(bill);
                res.set(c,a);
            }
            else{
                mp.put(bill.getStore(),new Integer(index));
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
        return res;
    }
    public static List<List<Bill>>getBill(SecondClass searchType, Date DateStart, Date DateEnd, Context context) throws ParseException {
        BillDao billDao = new BillDao(context, LogoActivity.sp.getString("loginUser",null));
        List<Bill> allBills = billDao.query();
        List<Bill> afterDeal = new LinkedList<>();
        Iterator<Bill> it = allBills.iterator();
        List<List<Bill>> res = new LinkedList<>();
        while (it.hasNext()) {
            Bill bill = (Bill) it.next();
            Date billDate = Constants.DATE_FORMAT_SIMPLE.parse(bill.getDate());
            if(bill.getType().equals(Constants.TRANSFER)) continue;
            if(billDate.getTime()<=DateEnd.getTime()&&billDate.getTime()>=DateStart.getTime()){
                afterDeal.add(bill);
            }
        }
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
        return res;
    }
    public static List<List<Bill>>getBill(Account searchType, Date DateStart, Date DateEnd, Context context) throws ParseException {
        BillDao billDao = new BillDao(context, LogoActivity.sp.getString("loginUser",null));
        List<Bill> allBills = billDao.query();
        List<Bill> afterDeal = new LinkedList<>();
        Iterator<Bill> it = allBills.iterator();
        List<List<Bill>> res = new LinkedList<>();
        while (it.hasNext()) {
            Bill bill = (Bill) it.next();
            Date billDate = Constants.DATE_FORMAT_SIMPLE.parse(bill.getDate());
            if(bill.getType().equals(Constants.TRANSFER)) continue;
            if(billDate.getTime()<=DateEnd.getTime()&&billDate.getTime()>=DateStart.getTime()){
                afterDeal.add(bill);
            }
        }
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
        return res;
    }


    public static List<List<Bill>>getBill(String searchType, Date DateStart, Date DateEnd, Context context) throws ParseException {
        BillDao billDao = new BillDao(context, LogoActivity.sp.getString("loginUser",null));
        List<Bill> allBills = billDao.query();
        List<Bill> afterDeal = new LinkedList<>();
        Iterator<Bill> it = allBills.iterator();
        List<List<Bill>> res = new LinkedList<>();
        while (it.hasNext()) {
            Bill bill = (Bill) it.next();
            Date billDate = Constants.DATE_FORMAT_SIMPLE.parse(bill.getDate());
            if(bill.getType().equals(Constants.TRANSFER)) continue;
            if(billDate.getTime()<=DateEnd.getTime()&&billDate.getTime()>=DateStart.getTime() ){
                afterDeal.add(bill);
            }
        }
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
            case Constants.SEARCH_TYPE_DAY:{
                Calendar startCalendar = Calendar.getInstance();
                startCalendar.setTime(DateStart);
                Calendar endCalendar = Calendar.getInstance();
                endCalendar.setTime(DateEnd);
                Iterator<Bill> swit = afterDeal.iterator();
                Calendar tmp = startCalendar;
                while (swit.hasNext()) {
                    Bill bill = (Bill) swit.next();
                    if(bill.getType().equals(Constants.TRANSFER)) continue;
                    Date billDate = Constants.DATE_FORMAT_SIMPLE.parse(bill.getDate());
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(billDate);
                    if(tmp.get(Calendar.YEAR) == calendar.get(Calendar.YEAR) &&  tmp.get(Calendar.MONTH) == calendar.get(Calendar.MONTH) &&tmp.get(Calendar.DATE) == calendar.get(Calendar.DATE)){
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
            } break;
            case Constants.SEARCH_TYPE_WEEK:{

            } break;
            case Constants.SEARCH_TYPE_MONTH:{
                Calendar startCalendar = Calendar.getInstance();
                Calendar endCalendar = Calendar.getInstance();
                endCalendar.setTime(DateEnd);
                Iterator<Bill> swit = afterDeal.iterator();
                Calendar tmp = startCalendar;
                while (swit.hasNext()) {
                    Bill bill = (Bill) swit.next();
                    if(bill.getType().equals(Constants.TRANSFER)) continue;
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
            } break;
            case Constants.SEARCH_TYPE_YEAR:{
                Calendar startCalendar = Calendar.getInstance();
                Calendar endCalendar = Calendar.getInstance();
                endCalendar.setTime(DateEnd);
                Iterator<Bill> swit = afterDeal.iterator();
                Calendar tmp = startCalendar;
                while (swit.hasNext()) {
                    Bill bill = (Bill) swit.next();
                    if(bill.getType().equals(Constants.TRANSFER)) continue;
                    Date billDate = Constants.DATE_FORMAT_SIMPLE.parse(bill.getDate());
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(billDate);
                    if(tmp.get(Calendar.YEAR) == calendar.get(Calendar.YEAR) ){
                        if(res.isEmpty()){
                            List<Bill> tp1 = new LinkedList<>();
                            tp1.add(bill);
                            res.add(tp1);
                            continue;
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
            } break;
        }
        return res;
    }
}
