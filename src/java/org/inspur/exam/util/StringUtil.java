package org.inspur.exam.util;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.apache.log4j.Logger;

public class StringUtil {

    private static Logger log = Logger.getLogger(StringUtil.class);

    private final static String dateFormat = "yyyy-MM-dd HH:mm:ss";

    /**
     * 获取32位uuid
     * 
     * @return string
     */
    public static String getUUID() {
	return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 获取当前时间
     * 
     * @return
     */
    public static String getDateTime() {
	return new SimpleDateFormat(dateFormat).format(Calendar.getInstance().getTime());
    }

    /**
     * 根据步长和长度返回一个字符串如：2014-01-02,2014-01-03,从当前时间算
     * 
     * @param length
     *            :时间段个数;step:时间间隔长度，单位为1天;
     * @return
     */
    public static String getDates(int length, int step) {
	StringBuffer dateStr = new StringBuffer();
	Calendar cal = Calendar.getInstance(Locale.CHINA);
	cal = Calendar.getInstance(Locale.CHINA);
	for (int i = 0; i < length; i++) {
	    dateStr.append(new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()) + ",");
	    cal.add(Calendar.DATE, -step);
	}
	return dateStr.toString().substring(0, dateStr.toString().length() - 1);
    }

    /**
     * 获取年份的月份2014-01-01,2014-02-01,每个月的最后一天
     * 
     * @return
     */
    public static String getYearDates() {
	StringBuffer dateStr = new StringBuffer();
	Calendar cal = Calendar.getInstance(Locale.CHINA);
	String year = new SimpleDateFormat("yyyy").format(cal.getTime());
	for (int i = 0; i < 12; i++) {
	    cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	    cal.set(Calendar.YEAR, Integer.parseInt(year));
	    cal.set(Calendar.MONTH, i + 1);
	    cal.set(Calendar.DAY_OF_MONTH, 1);
	    cal.add(Calendar.DAY_OF_MONTH, -1);
	    dateStr.append(new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()) + ",");
	}
	return dateStr.toString().substring(0, dateStr.toString().length() - 1);
    }

    /**
     * 获取年份的月份2014-01-01,2014-02-01，每个月的第一天
     * 
     * @return
     */
    public static String getMonthDates() {
	StringBuffer dateStr = new StringBuffer();
	Calendar cal = Calendar.getInstance(Locale.CHINA);
	String year = new SimpleDateFormat("yyyy").format(cal.getTime());
	for (int i = 0; i < 12; i++) {
	    cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	    cal.set(Calendar.YEAR, Integer.parseInt(year));
	    cal.set(Calendar.MONTH, i);
	    cal.set(Calendar.DAY_OF_MONTH, 1);
	    dateStr.append(new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()) + ",");
	}
	return dateStr.toString().substring(0, dateStr.toString().length() - 1);
    }

    /**
     * 获取当年时间的前days天
     * 
     * @return
     */
    public static String getDateBeforeNow(int days) {
	StringBuffer dateStr = new StringBuffer();
	Calendar cal = Calendar.getInstance(Locale.CHINA);
	cal.add(Calendar.DATE, -days);
	return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()) + " 00:00:00";
    }

    /**
     * 获取当前季度
     * 
     * @return
     */
    public static String getCurQuarter(int l) {
	Calendar cal = Calendar.getInstance(Locale.CHINA);
	int currentMonth = cal.get(Calendar.MONTH) + 1;
	int quarterInt = 1;
	String quarter = "";
	String year = getCurYear(0 - l / 4);
	int remain = l % 4;
	if (currentMonth > 0 && currentMonth <= 3) {
	    quarterInt = 1;
	} else if (currentMonth > 3 && currentMonth <= 6) {
	    quarterInt = 2;
	} else if (currentMonth > 6 && currentMonth <= 9) {
	    quarterInt = 3;
	} else if (currentMonth > 9 && currentMonth <= 12) {
	    quarterInt = 4;
	}
	for (int i = 1; i <= l; i++) {
	    switch (quarterInt) {
	    case 1:
		quarterInt = 4;
		break;
	    case 2:
		quarterInt = 1;
		break;
	    case 3:
		quarterInt = 2;
		break;
	    case 4:
		quarterInt = 3;
		break;
	    default:
		break;
	    }
	}
	return year + "0" + quarterInt;
    }

    /**
     * 获取当前年份
     * 
     * @return
     */
    public static String getCurYear(int l) {
	Calendar cal = Calendar.getInstance(Locale.CHINA);
	cal.add(Calendar.YEAR, l);
	String year = new SimpleDateFormat("yyyy").format(cal.getTime());
	return year.toString();
    }

    /**
     * 获取当年月份的前N个月份
     * 
     * @param n
     * @return
     */
    public static String getYearTrend(int n) {
	StringBuffer sb = new StringBuffer();
	Calendar cal = Calendar.getInstance(Locale.CHINA);
	for (int i = 0; i < n; i++) {
	    cal.add(Calendar.YEAR, -1);
	    sb.append(new SimpleDateFormat("yyyy").format(cal.getTime()) + ",");
	}
	String str = sb.toString();
	str = str.substring(0, str.length() - 1);
	return str;
    }

    /**
     * 获取当前天
     * 
     * @return
     */
    public static String getCurDay() {
	Calendar cal = Calendar.getInstance(Locale.CHINA);
	String day = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
	return day.toString();
    }

    /**
     * 获取当前天前n天
     * 
     * @return
     */
    public static String getCurDay(int n) {
	Calendar cal = Calendar.getInstance(Locale.CHINA);
	cal.add(Calendar.DATE, -n);
	String day = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());

	return day.toString();
    }

    /**
     * 获取当前天前月份，前n月份
     * 
     * @return
     */
    public static String getCurMonth() {
	Calendar cal = Calendar.getInstance(Locale.CHINA);
	cal.add(Calendar.MONTH, -1);
	String day = new SimpleDateFormat("yyyyMM").format(cal.getTime());
	return day.toString();
    }

    /**
     * 获取当前天前月份的前n月份
     * 
     * @return
     */
    public static String getCurMonth(int n) {
	Calendar cal = Calendar.getInstance(Locale.CHINA);
	cal.add(Calendar.MONTH, -n);
	String day = new SimpleDateFormat("yyyyMM").format(cal.getTime());
	return day.toString();
    }

    /**
     * 获取当年月份的前N个月份
     * 
     * @param n
     * @return
     */
    public static String getMonthTrend(int n) {
	StringBuffer sb = new StringBuffer();
	Calendar cal = Calendar.getInstance(Locale.CHINA);
	for (int i = 0; i < n; i++) {
	    cal.add(Calendar.MONTH, -1);
	    sb.append(new SimpleDateFormat("yyyyMM").format(cal.getTime()) + ",");
	}
	String str = sb.toString();
	str = str.substring(0, str.length() - 1);
	return str;
    }

    /**
     * 获取某一月份的前n月份
     * 
     * @param m形如
     *            ：201412
     * @return
     */
    public static String getPrevMonth(String m, int n) {
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
	String day = "";
	try {
	    Date date = simpleDateFormat.parse(m);
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    cal.add(Calendar.MONTH, -n);
	    day = new SimpleDateFormat("yyyyMM").format(cal.getTime());
	} catch (ParseException e) {
	    // TODO Auto-generated catch block
	    log.error("获取" + m + "前" + n + "个月出错：" + e.getMessage());
	}
	return day.toString();
    }

    /**
     * 获取当前时间的前一小时
     * 
     * @return
     */
    public static String getCurDayHour(int n) {
	Calendar cal = Calendar.getInstance(Locale.CHINA);
	cal.add(Calendar.HOUR_OF_DAY, -n);
	String day = new SimpleDateFormat("yyyy-MM-dd HH:00:00").format(cal.getTime());

	return day.toString();
    }

    /**
     * 获取当前时间的前一小时
     * 
     * @return
     */
    public static String getCurDayHourInt(int n) {
	Calendar cal = Calendar.getInstance(Locale.CHINA);
	cal.add(Calendar.HOUR_OF_DAY, -n);
	String day = new SimpleDateFormat("yyyyMMddHH").format(cal.getTime());

	return day.toString();
    }

    /**
     * 获取指定日期的前n天
     * 
     * @param spec_date
     *            指定日期字符串，日期格式yyyy-MM-dd
     * @author fuanlong
     * @return
     * @throws ParseException
     */
    public static String getBeforeDay(String spec_date, int n) throws ParseException {
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

	Calendar cal = Calendar.getInstance(Locale.CHINA);
	cal.setTime(df.parse(spec_date));
	cal.add(Calendar.DATE, -n);
	String day = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());

	return day.toString();
    }

    /**
     * 浮点数出发运算，
     * 
     * @param v1
     * @param v2
     * @param scale
     *            小数点精度
     * @return
     */
    public static double div(double v1, double v2, int scale) {
	if (scale < 0) {
	    scale = 2;
	}
	if (v1 == 0 || v2 == 0) {
	    return 0;
	}
	BigDecimal b1 = new BigDecimal(Double.toString(v1));
	BigDecimal b2 = new BigDecimal(Double.toString(v2));
	return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 浮点数出发运算，
     * 
     * @param v1
     * @param v2
     * @param scale
     *            小数点精度
     * @param isv1gv2
     *            如果v1>v2,返回1
     * @return
     */
    public static double div(double v1, double v2, int scale, boolean isv1gv2) {
	if (v1 > v2 && isv1gv2) {
	    return 1;
	}
	if (scale < 0) {
	    scale = 2;
	}
	if (v1 == 0 || v2 == 0) {
	    return 0;
	}
	BigDecimal b1 = new BigDecimal(Double.toString(v1));
	BigDecimal b2 = new BigDecimal(Double.toString(v2));
	return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 浮点数出发运算，
     * 
     * @param v1
     * @param v2
     * @param scale
     *            小数点精度
     * @return
     */
    public static double multiply(double v1, double v2) {
	if (v1 == 0 || v2 == 0) {
	    return 0;
	}
	BigDecimal b1 = new BigDecimal(Double.toString(v1));
	BigDecimal b2 = new BigDecimal(Double.toString(v2));
	return b1.multiply(b2).doubleValue();
    }

    /**
     * 获取一天之内的所有小时
     * 
     * @return
     */
    public static String getHoursInDay(String cday) {
	String day = getCurDay(0);
	if (null != cday && !"".equals(cday)) {
	    day = cday;
	}
	StringBuffer hoursb = new StringBuffer();
	int m = 0;
	String h = "";
	for (int i = 0; i < 24; i++) {

	    m = i + 1;
	    if (i < 9) {
		h = "0" + m;
	    } else {
		h = "" + m;
	    }
	    hoursb.append(day + " " + h + ":00:00,");
	}
	String hours = hoursb.toString();
	return hours.substring(0, hours.length() - 1);
    }

    /**
     * 获取某年中当前天往前推n天，往后推n天
     * 
     * @return
     */
    public static String getDaysToDays(int n, int y) {
	String day = getCurDay(0);
	day = day.substring(5, 10);
	String year = getCurYear(y) + "-";
	StringBuffer sb = new StringBuffer();
	sb.append("'" + year + day + "',");
	// 当前天
	Calendar cal = Calendar.getInstance(Locale.CHINA);
	// 往前推n天
	for (int i = 0; i < n; i++) {
	    cal.add(Calendar.DATE, -1);
	    day = new SimpleDateFormat("MM-dd").format(cal.getTime());
	    sb.append("'" + year + day + "',");
	}
	// 往后推n天
	// 当前天
	cal = Calendar.getInstance(Locale.CHINA);
	for (int i = 0; i < n; i++) {
	    cal.add(Calendar.DATE, 1);
	    day = new SimpleDateFormat("MM-dd").format(cal.getTime());
	    sb.append("'" + year + day + "',");
	}
	String days = sb.toString();
	return days.substring(0, days.length() - 1);
    }

    
    
    /**
     * 判断字符串为空
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
	if (null == str || 0 == str.length()) {
	    return true;
	}
	return false;
    }
    
    /**
     * 判断字符串不为空
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str) {
	return !isEmpty(str);
    }
    
    /**
     * 判断list不为空
     * @param str
     * @return
     */
    public static boolean isEmptyList(List list) {
	if (null == list || list.isEmpty()) {
	    return true;
	}
	return false;
    }
    
    /**
     * 判断list不为空
     * @param str
     * @return
     */
    public static boolean isNotEmptyList(List list) {
	return !isEmptyList(list);
    }
}
