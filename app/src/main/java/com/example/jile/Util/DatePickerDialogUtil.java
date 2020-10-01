package com.example.jile.Util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.icu.text.SimpleDateFormat;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TimePicker;

import androidx.appcompat.app.AlertDialog;

import com.example.jile.R;

import java.util.Calendar;

/**
 * 日期时间选择控件 使用方法： private EditText inputDate;<br>
 * 需要设置的日期时间文本编辑框 private String initDateTime="2012年9月3  日 14:44",初始日期时间值
 * 在点击事件中使用： inputDate.setOnClickListener(new OnClickListener()
 */
public class DatePickerDialogUtil implements DatePicker.OnDateChangedListener,
        TimePicker.OnTimeChangedListener {
    /** 日期选择 */
    private DatePicker mDateChoose;
    /** 时间选择 */
    private TimePicker mTimeChoose;
    /** 对话框 */
    private AlertDialog mDialog;
    /** 设置时间 */
    private String mDateTime;
    /** 初始化时间 */
    private String mInitDateTime;
    @SuppressLint("SimpleDateFormat")
    private final SimpleDateFormat sdf = new SimpleDateFormat(
            "yyyy年MM月dd日 HH:mm:ss");
    /** 显示时间 */
    private String showDate;
    /** Activity对象 */
    private final Activity mActivity;

    /**
     * 日期时间弹出选择框函数
     *
     * @param activity
     *            调用父Activity
     * @param dateTime
     *            初始日期时间值，作为弹出窗口的标题和日期时间初始值
     */

    public DatePickerDialogUtil(Activity activity, String dateTime) {
        this.mActivity = activity;
        this.mInitDateTime = dateTime;
    }

    /**
     * 初始化时间日期
     *
     * @param datePicker
     *            日期
     * @param timePicker
     *            时间
     */
    public void init(DatePicker datePicker, TimePicker timePicker) {
        // 初始化Calendar
        Calendar calendar = Calendar.getInstance();
        // datePicker不等于空，并且初始化时间不为空
        if (!(datePicker == null || "".equals(mInitDateTime))) {

        } else {
            mInitDateTime = calendar.get(Calendar.YEAR) + "年"
                    + calendar.get(Calendar.MONTH) + "月"
                    + calendar.get(Calendar.DAY_OF_MONTH) + "日"
                    + calendar.get(Calendar.HOUR_OF_DAY) + ":"
                    + calendar.get(Calendar.MINUTE);
        }
        // 初始化时间
        mDateChoose.init(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), this);
        // 设置当前时间
        mTimeChoose.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
        // 设置当前分钟
        mTimeChoose.setCurrentMinute(calendar.get(Calendar.MINUTE));
    }

    /**
     * 弹出日期时间选择框方法
     *
     * @param dateText
     *            需要设置的日期时间选择框
     * @return
     */
    public AlertDialog datePickerDialog(final Button dateText) {
        LinearLayout mInflater = (LinearLayout) mActivity.getLayoutInflater()
                .inflate(R.layout.datepicker_dialog, null);
        mDateChoose = (DatePicker) mInflater.findViewById(R.id.date_choose);
        mTimeChoose = (TimePicker) mInflater.findViewById(R.id.time_choose);
        // 初始化日期时间
        init(mDateChoose, mTimeChoose);
        // 设置日期为24小时制
        mTimeChoose.setIs24HourView(true);
        // 时间改变事件监听器
        mTimeChoose.setOnTimeChangedListener(this);
        showDate = sdf.format(System.currentTimeMillis());
        // 创建对话框
        mDialog = new AlertDialog.Builder(mActivity).setTitle(mInitDateTime)
                .setView(mInflater)
                .setPositiveButton("设置", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dateText.setText(mDateTime);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dateText.setText(showDate);
                    }
                }).show();
        // 设置日期改变
        onDateChanged(null, 0, 0, 0);
        return mDialog;
    }

    /**
     * 时间监听器
     */
    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        // 设置日期改变
        onDateChanged(null, 0, 0, 0);
    }

    /**
     * 日期监听器
     */
    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
        // 获取日历实例
        Calendar calendar = Calendar.getInstance();
        // 设置当前日期和时间
        calendar.set(mDateChoose.getYear(), mDateChoose.getMonth(),
                mDateChoose.getDayOfMonth(), mTimeChoose.getCurrentHour(),
                mTimeChoose.getCurrentMinute());
        // 格式化字符串
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 hh:mm");
        mDateTime = sdf.format(calendar.getTime());
        mDialog.setTitle(mDateTime);
    }

    /**
     * 截取字符串
     *
     * @param src
     *            数据源
     * @param pattern
     *            匹配模式
     * @param indexOrLast
     *            最后一个下标
     * @param frontOrBack
     *            取前面的值还是后面的值
     * @return
     */
    public static String spliteString(String src, String pattern,
                                      String indexOrLast, String frontOrBack) {
        // 结果
        String result = "";
        int location = -1;
        // 不分大小写相等比较，如果是当前下标
        if (indexOrLast.equalsIgnoreCase("index")) {
            // 取得字符串第一次出现的地方
            location = src.indexOf(pattern);
        } else {
            // 字符串最后一个匹配的位置
            location = src.lastIndexOf(pattern);
        }
        // 不分大小写相等比较，如果实在前面
        if (frontOrBack.equalsIgnoreCase("front")) {
            if (location != -1) {
                // 截取字符串
                result = src.substring(0, location);
            } else {
                if (location != -1) {
                    // 截取字符串
                    result = src.substring(location + 1, src.length());
                }
            }
        }

        return result;
    }

    /**
     * 实现将初始日期时间2016年01月10日 16:45 拆分成年 月 日 时 分 秒,并赋值给calendar
     *
     * @param initDateTime
     *            初始日期时间值 字符串型
     * @return Calendar
     */
    private Calendar getCalendarByInitDate(String initDateTime) {
        // 获取日历实例
        Calendar calendar = Calendar.getInstance();
        /*
         * 将初始日期时间2016年01月10日 16:45 拆分成年 月 日 时 分 秒
         */
        // 日期
        String date = spliteString(mInitDateTime, "日", "index", "front");
        // 时间
        String time = spliteString(mInitDateTime, "日", "index", "back");
        // 年份
        String year = spliteString(date, "年", "index", "front");
        // 月分和日期
        String monthAndDay = spliteString(date, "年", "index", "back");
        // 月
        String month = spliteString(monthAndDay, "月", "index", "front");
        // 日
        String dayOfMonth = spliteString(monthAndDay, "月", "index", "back");
        // 时
        String hour = spliteString(time, ":", "index", "front");
        // 分
        String minute = spliteString(time, ":", "index", "back");
        // 获取当前年份
        int currentYear = Integer.valueOf(year.trim()).intValue();
        // 获取当前月份
        int currentMonth = Integer.valueOf(month.trim()).intValue();
        // 获取当前日
        int currentDay = Integer.valueOf(dayOfMonth.trim()).intValue();
        // 获取当前时
        int currentHour = Integer.valueOf(hour.trim()).intValue();
        // 获取当前分
        int currentMinute = Integer.valueOf(minute.trim()).intValue();
        // 设置当前年、月、日、时、分
        calendar.set(currentYear, currentMonth, currentDay, currentHour,
                currentMinute);
        return calendar;
    }
}
