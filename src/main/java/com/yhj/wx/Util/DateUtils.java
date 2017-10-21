package com.yhj.wx.Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;


/**
 * 
 * =============================================================================
 *
 * 描述：日期处理工具类
 *
 * 作者： 颜鸿江
 *
 *
 * 日期： 2017-10-23下午3:08:23
 * 
 * =============================================================================
 */

public class DateUtils {

	/** 定义常量 **/
	public static final String DATE_FULL_STR = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_FULL_STR_ = "yyyy/MM/dd";
	public static final String DATE_LONG_STR = "yyyy-MM-dd kk:mm:ss.SSS";
	public static final String DATE_SMALL_STR = "yyyy-MM-dd";
	public static final String DATE_KEY_STR = "yyMMddHHmmss";
	public static final String DATE_All_KEY_STR = "yyyyMMddHHmmss";
	public static final String DATE_TIME_DEFAULT = "HH:mm:ss";

	/** 每天小时数 */
	private static final long HOURS_PER_DAY = 24;
	/** 每小时分钟数 */
	private static final long MINUTES_PER_HOUR = 60;
	/** 每分钟秒数 */
	private static final long SECONDS_PER_MINUTE = 60;
	/** 每秒的毫秒数 */
	private static final long MILLIONSECONDS_PER_SECOND = 1000;
	/** 每分钟毫秒数 */
	private static final long MILLIONSECONDS_PER_MINUTE = MILLIONSECONDS_PER_SECOND
			* SECONDS_PER_MINUTE;
	/** 每天毫秒数 */
	@SuppressWarnings("unused")
	private static final long MILLIONSECONDS_SECOND_PER_DAY = HOURS_PER_DAY
			* MINUTES_PER_HOUR * SECONDS_PER_MINUTE * MILLIONSECONDS_PER_SECOND;

	public static TimeZone TIMEZONE_UTC = TimeZone.getTimeZone("UTC");

	
	private DateUtils() {
		/* cannot be instantiated */
		throw new UnsupportedOperationException("cannot be instantiated");
	}
	
	
	/**
	 * 获取系统当前时间（yyyy-MM-dd HH:mm:ss）
	 * 
	 * @return
	 */
	public static String getNowTime() {
		SimpleDateFormat df = new SimpleDateFormat(DATE_FULL_STR);
		return df.format(new Date());
	}
	

	/**
	 * 获取系统当前时间(指定返回类型)
	 * 
	 * @return
	 */
	public static String getNowTime(String type) {
		SimpleDateFormat df = new SimpleDateFormat(type);
		return df.format(new Date());
	}

	/**
	 * 使用预设格式提取字符串日期
	 * 
	 * @param date
	 *            日期字符串
	 * @return
	 */
	public static Date parse(String date) {
		return parse(date, DATE_FULL_STR);
	}

	/**
	 * 将时间戳返回dateFormat格式的日期字符串
	 * 
	 * @param timestamp
	 * @param dateFormat
	 * @return String
	 */
	public static String timestampToDate(long timestamp, String dateFormat) {
		String date = new SimpleDateFormat(dateFormat).format(new Date(
				timestamp));
		return date;
	}

	/**
	 * 将时间戳返回("yyyy-MM-dd HH:mm:ss)格式的日期字符串
	 * 
	 * @param timestamp
	 * @return String
	 */
	public static String timestampToDate(long timestamp) {
		return timestampToDate(timestamp, DATE_FULL_STR);
	}

	/**
	 * 将日期字符串转成日期
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static Date parse(String date, String pattern) {
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		try {
			return df.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 得到两个日期相差的天数
	 */
	public static int getBetweenDay(Date date1, Date date2) {
		Calendar d1 = new GregorianCalendar();
		d1.setTime(date1);
		Calendar d2 = new GregorianCalendar();
		d2.setTime(date2);
		int days = d2.get(Calendar.DAY_OF_YEAR) - d1.get(Calendar.DAY_OF_YEAR);
		// System.out.println("days=" + days);
		int y2 = d2.get(Calendar.YEAR);
		if (d1.get(Calendar.YEAR) != y2) {
			// d1 = (Calendar) d1.clone();
			do {
				days += d1.getActualMaximum(Calendar.DAY_OF_YEAR);
				d1.add(Calendar.YEAR, 1);
			} while (d1.get(Calendar.YEAR) != y2);
		}
		return days;
	}


	/***
	 * 指定日期时间分钟上加上分钟数
	 * 
	 * @param date
	 * @param minutes
	 * @return
	 */
	public static Date changeMinute(Date date, int minutes) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MINUTE, minutes);
		return cal.getTime();
	}

	/**
	 * 指定日期时间上加上时间数
	 * 
	 * @param date
	 * @param hours
	 * @return
	 */
	public static Date changeHours(Date date, int hours) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR_OF_DAY, hours);
		return cal.getTime();
	}

	/**
	 * 指定的日期加减天数
	 * 
	 * @param date
	 * @param days
	 * @return
	 */
	public static Date changeDays(Date date, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, days);
		return cal.getTime();
	}

	/**
	 * 指定的日期加减年数
	 * 
	 * @param date
	 * @param years
	 * @return
	 */
	public static Date changeYear(Date date, int years) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.YEAR, years);
		return cal.getTime();
	}

	/**
	 * 获得两个日期之间相差的分钟数。（date1 - date2）
	 * 
	 * @param date1
	 * @param date2
	 * @return 返回两个日期之间相差的分钟数值
	 */
	public static int intervalMinutes(Date date1, Date date2) {
		long intervalMillSecond = date1.getTime() - date2.getTime();
		// 相差的分钟数 = 相差的毫秒数 / 每分钟的毫秒数 (小数位采用进位制处理，即大于0则加1)
		return (int) (intervalMillSecond / MILLIONSECONDS_PER_MINUTE + (intervalMillSecond
				% MILLIONSECONDS_PER_MINUTE > 0 ? 1 : 0));
	}

	/**
	 * 获得两个日期之间相差的秒数差（date1 - date2）
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int intervalSeconds(Date date1, Date date2) {
		long intervalMillSecond = date1.getTime() - date2.getTime();
		return (int) (intervalMillSecond / MILLIONSECONDS_PER_SECOND + (intervalMillSecond
				% MILLIONSECONDS_PER_SECOND > 0 ? 1 : 0));
	}

	/**
	 * 比较date1，date2两个时间
	 * 
	 * @return boolean
	 * @throws
	 */
	public static boolean compareDate(Date date1, Date date2) {
		return date1.before(date2);
	}

	/**
	 * 比较date1，date2，两个时间字符串
	 * 
	 * @return boolean
	 * @throws
	 */
	public static boolean compareDate(String date1, String date2) {
		Date dt1, dt2;
		dt1 = parse(date1);
		dt2 = parse(date2);
		return compareDate(dt1, dt2);
	}

	/**
	 * 比较传入的date与当前时间 <br>
	 * 返回结果: 1 比当前时间大，0.当前时间相同，-1：比当前时间小
	 * 
	 * @param date
	 * @return int
	 */
	public static int compareDateWithNow(Date date) {
		Date now = new Date();
		int rnum = date.compareTo(now);
		return rnum;
	}

	/**
	 * 传入进来的timestamp与当前时间的进行比较 <br>
	 * 返回结果: 1 比当前时间大，0.当前时间相同，-1：比当前时间小
	 * 
	 * @param
	 * @return
	 */
	public static int compareDateWithNow(long timestamp) {
		long now = nowDateToTimestamp();
		if (timestamp > now) {
			return 1;
		} else if (timestamp < now) {
			return -1;
		} else {
			return 0;
		}
	}

	/**
	 * 返回当前时间的时间戳
	 * 
	 * @return long
	 */
	public static long nowDateToTimestamp() {
		long timestamp = new Date().getTime();
		return timestamp;
	}

	/**
	 * 判断是否闰年
	 * 
	 * @param year
	 * @return
	 */
	public static boolean isLeapYear(int year) {
		return (year % 400 == 0) || ((year % 4 == 0) && (year % 100 != 0));
	}

	/**
	 * 一个月有几天
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static int dayInMonth(int year, int month) {
		boolean yearleap = isLeapYear(year);
		int day;
		if (yearleap && month == 2) {
			day = 29;
		} else if (!yearleap && month == 2) {
			day = 28;
		} else if (month == 4 || month == 6 || month == 9 || month == 11) {
			day = 30;
		} else {
			day = 31;
		}
		return day;
	}

	/**
	 * 获取输入 周的开始日期
	 * 
	 * @param week
	 *            like 201412
	 * @return 8位日期 like 20140317
	 */
	public static String getWeekBeginDate(String week) {
		if (week == null || "".equals(week) || week.length() < 5) {
			throw new RuntimeException("由于缺少必要的参数，系统无法进行制定的周换算.");
		}
		try {
			Calendar cal = Calendar.getInstance();
			cal.setFirstDayOfWeek(Calendar.MONDAY); // 设置一个星期的第一天为星期1，默认是星期日
			cal.set(Calendar.YEAR, Integer.parseInt(week.substring(0, 4)));
			cal.set(Calendar.WEEK_OF_YEAR,
					Integer.parseInt(week.substring(4, week.length())));
			cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
			return df.format(cal.getTime());
		} catch (Exception e) {
			throw new RuntimeException("进行周运算时输入得参数不符合系统规格." + e);
		}
	}

	/**
	 * 获取输入 周的结束日期
	 * 
	 * @param week
	 *            like 201412
	 * @return 8位日期 like 20140323
	 */
	public static String getWeekEndDate(String week) {
		if (week == null || "".equals(week) || week.length() < 5) {
			throw new RuntimeException("由于缺少必要的参数，系统无法进行制定的周换算.");
		}
		try {
			Calendar cal = Calendar.getInstance();
			cal.setFirstDayOfWeek(Calendar.MONDAY); // 设置一个星期的第一天为星期1，默认是星期日
			cal.set(Calendar.YEAR, Integer.parseInt(week.substring(0, 4)));
			cal.set(Calendar.WEEK_OF_YEAR,
					Integer.parseInt(week.substring(4, week.length())));
			cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
			return df.format(cal.getTime());
		} catch (Exception e) {
			throw new RuntimeException("进行周运算时输入得参数不符合系统规格." + e);
		}
	}

	/**
	 * 带时区的格式化时间
	 * 
	 * @param date
	 * @param format
	 * @param timeZone
	 * @return
	 */
	public static String formatDateTimeZone(Date date, String format,
			TimeZone timeZone) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		sdf.setTimeZone(timeZone);
		return sdf.format(date);
	}

	/**
	 * 将指定的日期转换成Unix时间戳
	 * 
	 * @param date
	 *            需要转换的日期 yyyy-MM-dd HH:mm:ss
	 * @return long 时间戳
	 */
	public static long dateToUnixTimestamp(String date) {
		long timestamp = 0;
		try {
			timestamp = new SimpleDateFormat(DATE_FULL_STR).parse(date)
					.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return timestamp;
	}

	/**
	 * 将指定的日期转换成Unix时间戳
	 * 
	 * @param date
	 *            需要转换的日期 yyyy-MM-dd
	 * @return long 时间戳
	 */
	public static long dateToUnixTimestamp(String date, String dateFormat) {
		long timestamp = 0;
		try {
			timestamp = new SimpleDateFormat(dateFormat).parse(date).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return timestamp;
	}

	/**
	 * 将当前日期转换成Unix时间戳
	 * 
	 * @return long 时间戳
	 */
	public static long dateToUnixTimestamp() {
		long timestamp = new Date().getTime();
		return timestamp;
	}

	/**
	 * 将Unix时间戳转换成日期
	 * 
	 * @param timestamp
	 *            时间戳
	 * @return String 日期字符串
	 */
	public static String unixTimestampToDate(long timestamp) {
		SimpleDateFormat sd = new SimpleDateFormat(DATE_FULL_STR);
		sd.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		return sd.format(new Date(timestamp));
	}

	/**
	 * 将Unix时间戳转换成日期
	 * 
	 * @param timestamp
	 *            时间戳
	 * @return String 日期字符串
	 */
	public static String timeStamp2Date(long timestamp, String dateFormat) {
		String date = new SimpleDateFormat(dateFormat).format(new Date(
				timestamp));
		return date;
	}

	/**
	 * 将Unix时间戳转换成日期
	 * 
	 * @param timestamp
	 *            时间戳
	 * @return String 日期字符串
	 */
	public static String timeStamp2Date(long timestamp) {
		String date = new SimpleDateFormat(DATE_FULL_STR).format(new Date(
				timestamp));
		return date;
	}

	/**
	 * 根据生日获取年龄
	 * 
	 * @param birthDay
	 * @return
	 */
	public static int getAge(Date birthDay) {
		Calendar cal = Calendar.getInstance();

		if (cal.before(birthDay)) {
			return -1;
		}

		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH) + 1;
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
		cal.setTime(birthDay);

		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH);
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

		int age = yearNow - yearBirth;

		if (monthNow <= monthBirth) {
			if (monthNow == monthBirth) {
				// monthNow==monthBirth
				if (dayOfMonthNow < dayOfMonthBirth) {
					age--;
				} else {
					// do nothing
				}
			} else {
				// monthNow>monthBirth
				age--;
			}
		} else {
			// monthNow<monthBirth
			// donothing
		}

		return age;
	}

	/**
	 * 获取当前时间的毫秒值
	 * 
	 * @return
	 */
	public static Long nowTimeInMillis() {
		return Calendar.getInstance().getTimeInMillis();
	}



	

	/**
	 * 将分钟 转成 （xx小时：yy分钟）
	 * 
	 * @param sportTime
	 * @return
	 */
	public static String toFormatTime(int sportTime) {
		int hour = sportTime / 60;
		int minute = sportTime - hour * 60;
		return String.format("%02d 小时:%02d 分钟", hour, minute);
	}

	/**
	 * 将秒转成 （xx小时：yy分钟 ： zz秒）
	 * 
	 * @param sportTime
	 * @return
	 */
	public static String toFormatTimeSecond(int sportTime) {
		int hour = sportTime / 3600;
		int minute = (sportTime - hour * 3600) / 60;
		int second = sportTime - hour * 3600 - minute * 60;
		return String.format("%02d:%02d:%02d", hour, minute, second);
	}

}
