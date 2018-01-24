package com.zivy.utils.date;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

/**
 * 日期处理工具类.
 */
public class DateUtil {

	/** yyyy-MM-dd */
	public static final String yyyy_MM_dd = "yyyy-MM-dd";
	/** yyyyMMdd */
	public static final String yyyyMMdd = "yyyyMMdd";

	/** yyyy-MM */
	public static final String yyyy_MM = "yyyy_MM";

	/** yyyy-MM-dd HH:mm:ss */
	public static final String yyyy_MM_dd_HHmmss = "yyyy-MM-dd HH:mm:ss";
	/** 年-月-日 时:分 */
	public static final String yyyy_MM_dd_HH_mm = "yyyy-MM-dd HH:mm";

	/** yyyy年MM月dd日 */
	public static final String yyyy年MM月dd日 = "yyyy年MM月dd日";

	/** yyyy_MM_dd HH:mm:ss:SSS */
	public static final String yyyyMMddHHmmssSSS = "yyyyMMddHHmmssSSS";
	/** yyyyMMddHHmmssSSS */
	public static final String yyyy_MM_dd_HHmmssSSS = "yyyy_MM_dd HH:mm:ss:SSS";

	/** yyMMddHHmmssSSS */
	public static final String yyMMddHHmmssSSS = "yyMMddHHmmssSSS";

	/** yyyy_M_d */
	private static final String yyyy_M_d = "yyyy-M-d";

	/**
	 * 默认日期类型格试.
	 * 
	 * @see yyyy_MM_dd
	 */
	private static SimpleDateFormat dateFormat = new SimpleDateFormat(yyyy_MM_dd);

	/**
	 * 缺省的时间格式
	 */
	private static final String DAFAULT_TIME_FORMAT = "HH:mm:ss";

	private static SimpleDateFormat timeFormat = new SimpleDateFormat(DAFAULT_TIME_FORMAT);

	private DateUtil() {
		// 私用构造主法.因为此类是工具类.
	}

	/**
	 * 获取格式化实例.
	 * 
	 * @param pattern
	 *            如果为空使用DAFAULT_DATE_FORMAT
	 * @return
	 */
	public static SimpleDateFormat getFormatInstance(String pattern) {
		if (pattern == null || pattern.length() == 0) {
			pattern = yyyy_MM_dd;
		}

		return new SimpleDateFormat(pattern);
	}

	/**
	 * 
	 * 是否是晚上
	 * 
	 * zivy
	 *
	 * 
	 * @return 2016年12月15日
	 */
	public static boolean isNight() {
		int hour = getIntHourNow();
		if(hour<18 && hour>6){
			return false;
		}else{
			return true;
		}
	}

	public static String getCurDatetime(String pattern) {
		if (pattern == null || pattern.length() == 0) {
			pattern = yyyy_MM_dd;
		}

		return formatDate(new Date(), pattern);
	}

	/**
	 * 
	 * 
	 * 计算两个日期的差
	 * 
	 * zivy
	 *
	 * 
	 * @param smdate
	 *            被减数
	 * @param bdate
	 *            减数
	 * @return
	 * @throws ParseException
	 *             2016年11月7日
	 */
	public static int daysBetween(Date edate, Date smdate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		smdate = sdf.parse(sdf.format(smdate));
		edate = sdf.parse(sdf.format(edate));
		Calendar cal = Calendar.getInstance();
		cal.setTime(smdate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(edate);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 * 格式化Calendar
	 * 
	 * @param calendar
	 * @return
	 */
	public static String formatCalendar(Calendar calendar) {
		if (calendar == null) {
			return "";
		}
		return dateFormat.format(calendar.getTime());
	}

	/**
	 * 默认格式化 格式 yyyy-M-d
	 * 
	 * @param d
	 * @return
	 */
	public static String formatDate(Date d) {
		if (d == null) {
			return "";
		}
		return dateFormat.format(d);
	}

	/**
	 * 格式化时间
	 * 
	 * @param calendar
	 * @return
	 */
	public static String formatTime(Date d) {
		if (d == null) {
			return "";
		}
		return timeFormat.format(d);
	}

	/**
	 * 格式化整数型日期
	 * 
	 * @param intDate
	 * @return
	 */
	public static String formatIntDate(Integer intDate) {
		if (intDate == null) {
			return "";
		}
		Calendar c = newCalendar(intDate);
		return formatCalendar(c);
	}

	/**
	 * 根据指定格式化来格式日期.
	 * 
	 * @param date
	 *            待格式化的日期.
	 * @param pattern
	 *            格式化样式或分格,如yyMMddHHmmss
	 * @return 字符串型日期.
	 */
	public static String formatDate(Date date, String pattern) {
		if (date == null) {
			return "";
		}
		if (StringUtils.isBlank(pattern)) {
			return formatDate(date);
		}
		SimpleDateFormat simpleDateFormat = null;
		try {
			simpleDateFormat = new SimpleDateFormat(pattern);
		} catch (Exception e) {
			e.printStackTrace();
			return formatDate(date);
		}
		return simpleDateFormat.format(date);
	}

	/**
	 * 取得Integer型的当前日期
	 * 
	 * @return
	 */
	public static Integer getIntNow() {
		return getIntDate(getNow());
	}

	/**
	 * 取得Integer型的当前日期
	 * 
	 * @return
	 */
	public static Integer getIntToday() {
		return getIntDate(getNow());
	}

	/**
	 * 取得Integer型的当前年份
	 * 
	 * @return
	 */
	public static Integer getIntYearNow() {
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		return year;
	}

	/**
	 * 取得Integer型的当前月份
	 * 
	 * @return
	 */
	public static Integer getIntMonthNow() {
		Calendar c = Calendar.getInstance();
		int month = c.get(Calendar.MONTH) + 1;
		return month;
	}

	/**
	 * 取得Integer型的当前小时
	 * 
	 * @return
	 */
	public static Integer getIntHourNow() {
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 取得Integer型的当前分钟
	 * 
	 * @return
	 */
	public static Integer getIntMinuteNow() {
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.MINUTE);
	}

	public static String getStringToday() {
		return getIntDate(getNow()) + "";
	}

	/**
	 * 根据年月日获取整型日期
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static Integer getIntDate(int year, int month, int day) {
		return getIntDate(newCalendar(year, month, day));
	}

	/**
	 * 某年月的第一天
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static Integer getFirstDayOfMonth(int year, int month) {
		return getIntDate(newCalendar(year, month, 1));
	}

	/**
	 * 某年月的第一天
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static Integer getFirstDayOfThisMonth() {
		Integer year = DateUtil.getIntYearNow();
		Integer month = DateUtil.getIntMonthNow();
		return getIntDate(newCalendar(year, month, 1));
	}

	/**
	 * 某年月的第一天
	 * 
	 * @param date
	 * @return
	 * @time:2008-7-4 上午09:58:55
	 */
	public static Integer getFistDayOfMonth(Date date) {
		Integer intDate = getIntDate(date);
		int year = intDate / 10000;
		int month = intDate % 10000 / 100;
		return getIntDate(newCalendar(year, month, 1));
	}

	/**
	 * 某年月的最后一天
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static Integer getLastDayOfMonth(int year, int month) {
		return intDateSub(getIntDate(newCalendar(year, month + 1, 1)), 1);
	}

	/**
	 * 根据Calendar获取整型年份
	 * 
	 * @param c
	 * @return
	 */
	public static Integer getIntYear(Calendar c) {
		int year = c.get(Calendar.YEAR);
		return year;
	}

	/**
	 * 根据Calendar获取整型日期
	 * 
	 * @param c
	 * @return
	 */
	public static Integer getIntDate(Calendar c) {
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int day = c.get(Calendar.DAY_OF_MONTH);
		return year * 10000 + month * 100 + day;
	}

	/**
	 * 根据Date获取整型年份
	 * 
	 * @param d
	 * @return
	 */
	public static Integer getIntYear(Date d) {
		if (d == null) {
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		return getIntYear(c);
	}

	/**
	 * 根据Date获取整型日期
	 * 
	 * @param d
	 * @return
	 */
	public static Integer getIntDate(Date d) {
		if (d == null) {
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		return getIntDate(c);
	}

	/**
	 * 根据Integer获取Date日期
	 * 
	 * @param n
	 * @return
	 */
	public static Date getDate(Integer n) {
		if (n == null) {
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.set(n / 10000, n / 100 % 100 - 1, n % 100);
		return c.getTime();
	}

	/**
	 * 传入一个日期字符串，转化成一个日期对象
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDate(String date) {
		if (date == null || date.length() == 0) {
			return null;
		}

		try {
			if (date.contains("/")) {
				date = date.replaceAll("/", "-");
			}
			return getFormatInstance(yyyy_M_d).parse(date);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 传入一个日期字符串，转化成一个日期对象
	 * 
	 * @param date
	 * @return
	 */
	public static Timestamp getTimestamp(String date) {
		if (date == null || date.length() == 0) {
			return null;
		}

		try {
			if (date.contains("/")) {
				date = date.replaceAll("/", "-");
			}
			return new Timestamp(getFormatInstance(yyyy_M_d).parse(date).getTime());
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 根据年份Integer获取Date日期
	 * 
	 * @param year
	 * @return
	 */
	public static Date getFirstDayOfYear(Integer year) {
		if (year == null) {
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.set(year, 1, 1);
		return c.getTime();
	}

	/**
	 * 根据年月日生成Calendar
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static Calendar newCalendar(int year, int month, int day) {
		Calendar ret = Calendar.getInstance();
		if (year < 100) {
			year = 2000 + year;
		}
		ret.set(year, month - 1, day);
		return ret;
	}

	/**
	 * 根据整型日期生成Calendar
	 * 
	 * @param date
	 * @return
	 */
	public static Calendar newCalendar(int date) {
		int year = date / 10000;
		int month = (date % 10000) / 100;
		int day = date % 100;

		Calendar ret = Calendar.getInstance();
		ret.set(year, month - 1, day);
		return ret;
	}

	/**
	 * 取得Date型的当前日期
	 * 
	 * @return
	 */
	public static Date getNow() {
		return new Date();
	}

	/**
	 * 
	 * @return zhc 2016年3月12日
	 */
	public static Timestamp getCurTimestamp() {
		Date d = new Date();
		return new Timestamp(d.getTime());
	}

	/**
	 * 返回当前时间的指定格式
	 * 
	 * @param format
	 *            指定的格式参数
	 * 
	 * @return String
	 */
	public static String getNow(String format) {
		return formatDate(new Date(), format);
	}

	/**
	 * 取得Date型的当前日期
	 * 
	 * @return
	 */
	public static Date getToday() {
		return DateUtil.getDate(DateUtil.getIntToday());
	}

	/**
	 * 整数型日期的加法
	 * 
	 * @param date
	 * @param days
	 * @return
	 */
	public static Integer intDateAdd(int date, int days) {
		int year = date / 10000;
		int month = (date % 10000) / 100;
		int day = date % 100;

		day += days;

		return getIntDate(year, month, day);
	}

	/**
	 * 整数型日期的减法
	 * 
	 * @param date
	 * @param days
	 * @return
	 */
	public static Integer intDateSub(int date, int days) {
		return intDateAdd(date, -days);
	}

	/**
	 * 计算两个整型日期之间的天数
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static Integer daysBetweenDate(Integer startDate, Integer endDate) {
		if (startDate == null || endDate == null) {
			return null;
		}
		Calendar c1 = newCalendar(startDate);
		Calendar c2 = newCalendar(endDate);

		Long lg = (c2.getTimeInMillis() - c1.getTimeInMillis()) / 1000 / 60 / 60 / 24;
		return lg.intValue();
	}

	/**
	 * 计算两个整型日期之间的天数
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static Integer daysBetweenDate(Date startDate, Date endDate) {
		if (startDate == null || endDate == null) {
			return null;
		}
		Long interval = endDate.getTime() - startDate.getTime();
		interval = interval / (24 * 60 * 60 * 1000);
		return interval.intValue();
	}

	/**
	 * 取得当前日期.
	 * 
	 * @return 当前日期,字符串类型 格式yyyyMMDD
	 */
	public static String getStringDate() {
		return getStringDate(DateUtil.getNow());
	}

	/**
	 * 根据calendar产生字符串型日期
	 * 
	 * @param d
	 * @returnyyyyMMdd
	 */
	public static String getStringDate(Date d) {
		if (d == null) {
			return "";
		}
		dateFormat.applyPattern(DateUtil.yyyyMMdd);
		return dateFormat.format(d);
	}

	/**
	 * 
	 * @param d
	 * @return yyyy年MM月dd日
	 */
	public static String getFormatStringDate(Date d) {
		if (d == null) {
			return "";
		}
		dateFormat.applyPattern(DateUtil.yyyy年MM月dd日);
		return dateFormat.format(d);
	}

	/**
	 * 返回系统当前时间
	 * 
	 * @return 格式 yyyy-MM-dd HH:mm:ss
	 */
	public static String now_str() {
		dateFormat.applyPattern(DateUtil.yyyy_MM_dd_HHmmss);
		return dateFormat.format(Calendar.getInstance().getTime());
	}

	/**
	 * 返回系统当前日期
	 * 
	 * @return 格式 yyyy-MM-dd
	 */
	public static String today() {
		dateFormat.applyPattern(DateUtil.yyyy_MM_dd);
		return dateFormat.format(Calendar.getInstance().getTime());
	}

	/**
	 * 获取当前系统时间
	 * 
	 * @param Pattern
	 *            表达式
	 * @return 格式化当前系统时间
	 */
	public static String format(String Pattern) {
		dateFormat.applyPattern(Pattern);
		return dateFormat.format(Calendar.getInstance().getTime());
	}

	/**
	 * 将Date日期，转为TimeStemp日期
	 * 
	 * @param d
	 * @return
	 */
	public static Timestamp getTimestamp(Date d) {
		return new Timestamp(d.getTime());
	}

	public static void main(String[] args) {
		int hours = new Date().getHours();
		System.out.println(hours);

		System.out.println(DateUtil.getIntHourNow());
		System.out.println(DateUtil.getIntMinuteNow());
	}

	/**
	 * 传入一个日期字符串，转化成一个日期对象
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDatetime(String date) {
		if (date == null || date.length() == 0) {
			return null;
		}
		if (date.length() == 10) {
			return getDate(date);
		}
		try {
			if (date.contains("/")) {
				date = date.replaceAll("/", "-");
			}
			return getFormatInstance(yyyy_MM_dd_HHmmss).parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 日期加减
	 * 
	 * @param date
	 *            源日期
	 * @param type
	 *            :year为加减addnum年，month为加减addnum月，day为加减addnum天，hour为加减addnum小时
	 *            ...
	 * @return
	 */
	public static Date addDate(Date date, String type, int addnum) {
		Calendar Cal = Calendar.getInstance();
		Cal.setTime(date);
		if (type.equals("year")) {
			Cal.add(java.util.Calendar.YEAR, addnum);
		}
		if (type.equals("month")) {
			Cal.add(java.util.Calendar.MONTH, addnum);
		}
		if (type.equals("day")) {
			Cal.add(java.util.Calendar.DAY_OF_MONTH, addnum);
		}
		if (type.equals("hour")) {
			Cal.add(java.util.Calendar.HOUR_OF_DAY, addnum);
		}
		if (type.equals("minute")) {
			Cal.add(java.util.Calendar.MINUTE, addnum);
		}
		return Cal.getTime();
	}

	/**
	 * 获取月最后一天的号数
	 * 
	 * @return
	 */
	public static int getLastDayOnMonth(int year, int month) {
		Calendar cal = Calendar.getInstance();
		// 年
		cal.set(Calendar.YEAR, year);
		// 月，因为Calendar里的月是从0开始，所以要-1
		cal.set(Calendar.MONTH, month - 1);
		// 日，设为一号
		cal.set(Calendar.DATE, 1);
		// 月份加一，得到下个月的一号
		cal.add(Calendar.MONTH, 1);
		// 下一个月减一为本月最后一天
		cal.add(Calendar.DATE, -1);
		return cal.get(Calendar.DAY_OF_MONTH);// 获得月末是几号

	}

	/**
	 * 传入一个日期字符串，转化成一个日期对象
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDate(String date, String pattern) {
		if (date == null || date.length() == 0) {
			return null;
		}

		try {
			if (date.contains("/")) {
				date = date.replaceAll("/", "-");
			}
			if (date.contains("\\")) {
				date = date.replaceAll("\\", "-");
			}
			if (date.contains(".")) {
				date = date.replaceAll(".", "-");
			}
			return getFormatInstance(pattern).parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获得时间的年数
	 * 
	 * @param date
	 * @return
	 */
	public static int getYear(Date date) {

		Calendar ca = Calendar.getInstance();

		ca.setTime(date);

		return ca.get(Calendar.YEAR);

	}

	/**
	 * 1 获得时间的月数
	 * 
	 * @param date
	 * @return
	 */
	public static int getMonth(Date date) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		return ca.get(Calendar.MONTH) + 1;
	}

	/**
	 * 1 获得时间的月数
	 * 
	 * @param date
	 * @return
	 */
	public static int getDay(Date date) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		return ca.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 获得下个月第一天的日期
	 * 
	 * @param date
	 * @return
	 * @author chenya
	 */
	public static Date getNextMonthFirst(Date date) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.add(Calendar.MONTH, 1);// 加 一个月
		ca.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		return ca.getTime();
	}

	/**
	 * 获得上个月第一天的日期
	 * 
	 * @param date
	 * @return
	 * @author chenya
	 */
	public static Date getPreMonthFirst(Date date) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);

		ca.add(Calendar.MONTH, -1);// 减 一个月
		ca.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		return ca.getTime();
	}

	/**
	 * 获得下一个旬第一天的日期：如2012-10-18为中旬，则下旬的第一天为2012-10-21
	 * 
	 * @param date
	 * @return
	 * @author chenya
	 */
	public static Date getNextXunFirst(Date date) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		int day = ca.get(Calendar.DAY_OF_MONTH);
		if (day > 0 && day < 11) {
			ca.set(Calendar.DATE, 11);
		} else if (day > 10 && day < 21) {
			ca.set(Calendar.DATE, 21);
		} else if (day >= 21) {
			ca.add(Calendar.MONTH, 1);
			ca.set(Calendar.DATE, 1);
		}
		return ca.getTime();
	}

	/**
	 * 获得上一个旬第一天的日期：如2012-10-18为中旬，则上旬的第一天为2012-10-1
	 * 
	 * @param date
	 * @return
	 * @author chenya
	 */
	public static Date getPretXunFirst(Date date) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		int day = ca.get(Calendar.DAY_OF_MONTH);
		if (day > 0 && day < 11) {
			ca.add(Calendar.MONTH, -1);
			ca.set(Calendar.DATE, 21);
		} else if (day > 10 && day < 21) {
			ca.set(Calendar.DATE, 1);
		} else if (day >= 21) {
			ca.set(Calendar.DATE, 11);
		}
		return ca.getTime();
	}

	/**
	 * 获取当前系统的毫秒数
	 * 
	 * @return
	 * @author jiangfl
	 */
	public static Long getTodayMM() {
		Date date = new Date();
		return date.getTime();
	}

	/**
	 * 打印当前系统时间，精确到毫秒
	 * 
	 * @author jiangfl
	 */
	public static void printNow() {
		printNow(null);
	}

	public static void printNow(String info) {
		System.out.println(info + DateUtil.formatDate(new Date(), DateUtil.yyyy_MM_dd_HHmmssSSS));
	}
	// public static Boolean isInner(String time,Integer intervalMin){
	// Date timeD=new Date(time);
	// Date now=addDate(new Date(),"minute",intervalMin);
	// }

}