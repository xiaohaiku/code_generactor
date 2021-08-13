package com.code.generactor.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateUtil {
    //=========================已经完成过的方法=======================================
    public static final String PATTERN_14 = "yyyyMMddHHmmss";
    private static final SimpleDateFormat formatter14 = new SimpleDateFormat(PATTERN_14);

    public static void main(String[] args) throws ParseException {

    }

    /**
     * 获取当前日期字符串,格式为：2012-05-26 20:14:00
     * 调用该方法使用时间约为10毫秒
     */
    public static String format() {
        return format(null, null);
    }

    /**
     * 以format的格式输出当前时间
     *
     * @param format 日期格式
     * @return
     */
    public static String format(String format) {
        return format(null, format);
    }

    /**
     * 指定日期以2012-05-26 20:14:00格式输出
     *
     * @param date 所指定的日期
     * @return
     */
    public static String format(Date date) {
        return format(date, null);
    }

    /**
     * 指定日期date以format格式输出
     *
     * @param date   所指定的日期
     * @param format 所指定的格式
     * @return
     */
    public static String format(Date date, String format) {
        if (date == null) {//如果传过来的Date类型为空  则直接用系统当前时间
            date = new Date();
        }
        if (format == null) {//传过来的格式为空 则用默认的yyyy-MM-dd HH:mm:ss的格式
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat formatter = new SimpleDateFormat(format);//用给定的模式日期格式符号构造 SimpleDateFormat
        String nowDate = formatter.format(date);//将一个 Date 格式化为日期/时间字符串
        return nowDate;
    }


    /**
     * 根据毫秒数以2012-05-26 20:14:00格式输出
     *
     * @param time 毫秒数
     * @return
     */
    public static String format(long time) {
//		System.out.println(new Date(time));//将指定毫秒数转成Tue May 29 16:14:26 CST 2012的时间格式
        return format(new Date(time), null);
    }

    /**
     * 根据毫秒数以format格式输出
     *
     * @param time   毫秒数
     * @param format 日期格式
     * @return
     */
    public static String format(long time, String format) {
        return format(new Date(time), format);
    }

    /**
     * 以指定日期格式时间，获取一个Date类
     *
     * @param formatDate 指定格式的日期2012-05-26 20:14:00
     * @return
     */
    public static Date getDate(String formatDate) {
        SimpleDateFormat sdf = null;
        if (formatDate != null && formatDate.length() < 11) {
            sdf = new SimpleDateFormat("yyyy-MM-dd");
        } else {
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
        try {
            Date d = sdf.parse(formatDate);
            return d;
        } catch (Exception e) {
            e.printStackTrace();
            dateExperction(formatDate + "转换成date类失败");
        }
        return null;
    }

    /**
     * 日期util中出现格式异常抓捕
     *
     * @param error
     */
    private static void dateExperction(String error) {
        System.out.println(error);
    }


}
