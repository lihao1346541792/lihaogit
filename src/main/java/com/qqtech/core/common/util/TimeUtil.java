package com.qqtech.core.common.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.joda.time.Seconds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 用来处理Timestamp的数据
 * 
 * @author wangzhh
 */
public class TimeUtil {
	public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final Logger logger = LoggerFactory.getLogger(TimeUtil.class);

	/**
	 * 当年第一天凌晨
	 * 
	 * @return
	 */
	public static Timestamp yearStartDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH, 1);
		calendar.set(Calendar.DATE, 1);
		calendar.set(Calendar.HOUR, -12);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return new Timestamp(calendar.getTimeInMillis());
	}

	/** 由出生日期获得年龄 */
	public static int getAge(Timestamp birthDay) throws Exception {
		Calendar cal = Calendar.getInstance();
		if (cal.before(birthDay)) {
			return 0;
		}
		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH);
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
		cal.setTime(birthDay);
		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH);
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
		int age = yearNow - yearBirth;
		if (monthNow <= monthBirth) {
			if (monthNow == monthBirth) {
				if (dayOfMonthNow < dayOfMonthBirth)
					age--;
			} else {
				age--;
			}
		}
		return age;
	}

	/**
	 * 给某个时间加上分钟数
	 * 
	 * @param t
	 * @param mins
	 * @return
	 */
	public static Timestamp addMins(Timestamp t, int mins) {
		DateTime inputTime = new DateTime(t);
		return new Timestamp(inputTime.plusMinutes(mins).getMillis());
	}

	/**
	 * 给某个时间加上小时数
	 * 
	 * @param t
	 * @param hours
	 * @return
	 */
	public static Timestamp addHours(Timestamp t, int hours) {
		return addMins(t, hours * 60);
	}

	/**
	 * 给某个时间加上天数
	 * 
	 * @param t
	 * @param days
	 * @return
	 */
	public static Timestamp addDays(Timestamp t, int days) {
		return addHours(t, days * 24);
	}

	/**
	 * 和当前时间的年数差
	 * 
	 * @param data
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static int yearsOfTwo(Date data) {
		return (new Date().getYear() - data.getYear());
	}

	/**
	 * 两个时间年数差
	 * 
	 * @param oldTime
	 * @param newTime
	 * @return
	 */
	public static int yearsOfTwo(Timestamp oldTime, Timestamp newTime) {
		long years = (newTime.getTime() - oldTime.getTime()) / (1000 * 60 * 60 * 24 * 365);
		return (int) years;
	}

	public static final String[] WEEKS = { "周日", "周一", "周二", "周三", "周四", "周五", "周六", "周日" };

	public static Date getDateByFormat(String timeStr, String format) {
		if (StringUtil.isBlank(timeStr)) {
			return null;
		}
		if (StringUtil.isBlank(format)) {
			format = DATE_FORMAT;
		}
		DateFormat sdf = new SimpleDateFormat(format);
		try {
			return sdf.parse(timeStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取可以发送短信的有效时间段，返回秒
	 * 
	 * @param range
	 *            时间段
	 * @param unit
	 *            时间单位
	 * @return
	 */
	public static long getRangeSeconds(int range, String unit) {
		int level = 1;
		if ("分钟".equals(unit)) {
			level = 60;
		} else if ("小时".equals(unit)) {
			level = 60 * 60;
		}
		return range * level;
	}

	/**
	 * 把字符串按照既定格式转换为时间
	 * 
	 * @param str
	 * @return
	 */
	public static Timestamp getTimeByFormat(String str, String fat) {
		DateFormat format = new SimpleDateFormat(fat);
		format.setLenient(false);
		Timestamp ts = null;
		try {
			ts = new Timestamp(format.parse(str).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return ts;
	}

	/**
	 * 按照指定格式转换日期
	 * 
	 * @param time
	 * @param str
	 * @return
	 */
	public static Timestamp getTimeByTimeFormat(Timestamp time, String str) {
		String strTime = getStringDateByFormat(time, str);
		return getTimeByFormat(strTime, str);
	}

	/**
	 * 根据时间 返回样式 昨天 14:30
	 * 
	 * @param time
	 * @return
	 */
	public static String getDayMinutesByFormat(Timestamp time) {
		if (time == null) {
			return "";
		}
		Timestamp day = TimeUtil.getTimeByTimeFormat(time, "yyyy-MM-dd");
		String strDay = TimeUtil.time2cnDay(day);
		String strMinutes = getStringDateByFormat(time, "HH:mm");
		return strDay + " " + strMinutes;

	}

	/**
	 * 把时间按照既定格式输出字符串
	 * 
	 * @param t
	 * @param format
	 * @return
	 */
	public static String getStringDateByFormat(Date t, String format) {
		Timestamp time = new Timestamp(t.getTime());
		return getStringTimeByFormat(time, format);
	}

	/**
	 * 把时间按照既定格式输出字符串
	 * 
	 * @param t
	 * @param format
	 * @return
	 */
	public static String getStringTimeByFormat(Timestamp t, String format) {
		if (null == t) {
			return "";
		}
		String rt = "";
		DateFormat sdf = new SimpleDateFormat(format);
		try {
			rt = sdf.format(t);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rt;
	}

	/**
	 * 几天后同一时刻
	 * 
	 * @return
	 */
	public static Timestamp afterDays(Timestamp t, int days) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(t);
		calendar.add(Calendar.DATE, days);
		return new Timestamp(calendar.getTimeInMillis());
	}

	/**
	 * 几天前或后的开始一刻
	 * 
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static Timestamp startWithDays(Timestamp t, int days) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(t);
		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + days);
		if (calendar.getTime().getHours() > 11) {
			calendar.set(Calendar.HOUR, -12);
		} else {
			calendar.set(Calendar.HOUR, 0);
		}
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return new Timestamp(calendar.getTimeInMillis());
	}

	/**
	 * 几天前或后的晚上最后一刻
	 * 
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static Timestamp lastSecondWithDays(Timestamp t, int days) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(t);
		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + days);
		if (calendar.getTime().getHours() > 11) {
			calendar.set(Calendar.HOUR, 11);
		} else {
			calendar.set(Calendar.HOUR, 23);
		}
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		return new Timestamp(calendar.getTimeInMillis());
	}

	/**
	 * 获取当前的日期和时间
	 * 
	 * @return
	 */
	public static Timestamp now() {
		return new Timestamp(System.currentTimeMillis());
	}

	/**
	 * 当月第一天凌晨
	 * 
	 * @return
	 */
	public static Timestamp monthStartDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DATE, 1);
		calendar.set(Calendar.HOUR, -12);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return new Timestamp(calendar.getTimeInMillis());
	}

	/**
	 * 当月最后一天凌晨
	 * 
	 * @return
	 */
	public static Timestamp monthEndDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR, 11);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		return new Timestamp(calendar.getTimeInMillis());
	}

	/**
	 * 两个时间小时差
	 * 
	 * @param oldTime
	 * @param newTime
	 * @return
	 */
	public static int hoursOfTwo(Timestamp oldTime, Timestamp newTime) {
		long hoursBetween = (newTime.getTime() - oldTime.getTime()) / (1000 * 60 * 60);
		return (int) hoursBetween;
	}

	/**
	 * 两个时间分钟差
	 * 
	 * @param oldTime
	 * @param newTime
	 * @return
	 */
	public static int minsOfTwo(Timestamp oldTime, Timestamp newTime) {
		long minsBetween = (newTime.getTime() - oldTime.getTime()) / (1000 * 60);
		return (int) minsBetween;
	}

	/**
	 * 两个时间秒差
	 * 
	 * @param oldTime
	 * @param newTime
	 * @return
	 */
	public static int secondsOfTwo(Timestamp oldTime, Timestamp newTime) {
		long secondsBetween = (newTime.getTime() - oldTime.getTime()) / (1000);
		return (int) secondsBetween;
	}

	/**
	 * 上month月第一天凌晨(month -1:上一个月;1下一个月)
	 * 
	 * @return
	 */
	public static Timestamp monthStartDay(int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONDAY) + month);
		calendar.set(Calendar.DATE, 1);
		calendar.set(Calendar.HOUR, -12);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return new Timestamp(calendar.getTimeInMillis());
	}

	/**
	 * 上month月最后一天凌晨(month -1:上一个月;1下一个月)
	 * 
	 * @return
	 */
	public static Timestamp monthEndDay(int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONDAY) + month);
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR, 11);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		return new Timestamp(calendar.getTimeInMillis());
	}

	/**
	 * 把html5 datetime-local字符串转换为时间
	 * 
	 * @param dateStr
	 *            日期字符串 2015-05-20T08:47:47.463或2015-05-20T08:47:47
	 * @return
	 */
	public static Timestamp getTimeByDatetimeLocal(String dateStr) {
		// http://stackoverflow.com/questions/27827614/conversion-from-datetime-local-to-java-sql-timestamp
		if (StringUtil.isBlank(dateStr)) {
			return null;
		}
		dateStr = dateStr.replaceAll("T", " ");
		try {
			return Timestamp.valueOf(dateStr);
		} catch (Exception e) {
			logger.error("日期处理出现异常，dateStr={},e={}", dateStr, e);
			return TimeUtil.getTimeByFormat(dateStr, "yyyy-MM-dd HH:mm");
		}
	}

	/**
	 * 把时间转换为html5 datetime-local字符串
	 * 
	 * @return
	 */
	public static String getDatetimeLocal(Timestamp time) {
		if (time == null) {
			return "";
		}
		String dateStr = TimeUtil.getStringTimeByFormat(time, "yyyy-MM-dd HH:mm:ss");
		return dateStr.replaceAll(" ", "T");
	}

	/**
	 * 日期转模糊中文描述
	 *
	 * @param time
	 * @return 中文描述, x分钟前, x小时前,
	 */
	public static String time2cn(Timestamp time) {
		if (time == null) {
			return "";
		}
		DateTime now = new DateTime();
		DateTime inputTime = new DateTime(time);

		int diffMinutes = Minutes.minutesBetween(inputTime, now).getMinutes();
		int diffHour = Hours.hoursBetween(inputTime, now).getHours();
		if (diffMinutes < 0) {
			return inputTime.toString("yyyy-MM-dd HH:mm");
		} else if (diffMinutes < 1) {
			return "刚刚";
		} else if (diffMinutes < 60) {
			return diffMinutes + "分钟前";
		} else if (diffHour >= 1 && diffHour < 24) {
			return diffHour + "小时前";
		} else if (diffHour >= 24 && (now.getYear() == inputTime.getYear())) {// 本年内，只显示日期
			return inputTime.toString("MM-dd");
		} else {// 超过一年显示完整日期
			return inputTime.toString("yyyy-MM-dd");
		}
	}

	/**
	 * 日期转模糊中文描述 转换为今天，昨天,日期
	 *
	 * @param time
	 * @return 中文描述, 今天，昨天，日期
	 */
	public static String time2cnDay(Timestamp time) {
		if (time == null) {
			return "";
		}
		DateTime now = new DateTime();
		DateTime inputTime = new DateTime(time);

		// 例子. today = 2015-06-15T23:59:59.000+08:00
		DateTime today = now.dayOfYear().roundFloorCopy().minusSeconds(1);
		DateTime yesterday = today.minusDays(1);
		DateTime beforYesterday = yesterday.minusDays(1);
		if (inputTime.isAfter(now)) {
			return inputTime.toString("yyyy-MM-dd HH:mm");
		} else if (inputTime.isAfter(today)) {
			return "今天";
		} else if (inputTime.isAfter(yesterday)) {
			return "昨天";
		} else if (inputTime.isAfter(beforYesterday)) {
			return "前天";
		} else if (now.getYear() == inputTime.getYear()) {// 本年内，只显示日期
			return inputTime.toString("MM-dd");
		} else {// 超过一年显示完整日期
			return inputTime.toString("yyyy-MM-dd");
		}
	}

	/**
	 * 日期转模糊中文描述 转换为今天，昨天,前天,日期
	 *
	 * @param time
	 * @return 中文描述, 今天HH:mm,昨天,前天,日期
	 */
	public static String time2cnDayMin(Timestamp time) {
		if (time == null) {
			return "";
		}
		DateTime now = new DateTime();
		DateTime inputTime = new DateTime(time);
		DateTime today = now.dayOfYear().roundFloorCopy().minusSeconds(1);
		DateTime yesterday = today.minusDays(1);
		DateTime beforYesterday = yesterday.minusDays(1);
		if (inputTime.isAfter(now)) {
			return inputTime.toString("yyyy-MM-dd HH:mm");
		} else if (inputTime.isAfter(today)) {
			return "今天" + inputTime.toString("HH:mm");
		} else if (inputTime.isAfter(yesterday)) {
			return "昨天";
		} else if (inputTime.isAfter(beforYesterday)) {
			return "前天";
		} else if (now.getYear() == inputTime.getYear()) {// 本年内，只显示日期
			return inputTime.toString("MM-dd");
		} else {// 超过一年显示完整日期
			return inputTime.toString("yyyy-MM-dd");
		}
	}

	/**
	 * 获取天数差2-后天 1-明天 0-今天 -1昨天 -2前天
	 * 
	 * @param t
	 * @return
	 */
	public static int getDays(Timestamp t) {
		if (t == null) {
			return 0;
		}
		DateTime now = new DateTime();
		DateTime today = now.dayOfYear().roundFloorCopy().minusSeconds(0);
		int seconds = Seconds.secondsBetween(today, new DateTime(t)).getSeconds();
		int days = seconds / (60 * 60 * 24);
		return days;
	}

	/**
	 * 获取名称：2-后天 1-明天 0-今天 -1昨天 -2前天
	 * 
	 * @param t
	 * @return
	 */
	public static String getDayName(Timestamp t) {
		if (t == null) {
			return "";
		}
		String name = "";
		int days = getDays(t);
		if (days == 0) {
			name = "今天";
		} else if (days == 1) {
			name = "明天";
		} else if (days == 2) {
			name = "后天";
		} else if (days == -1) {
			name = "昨天";
		} else if (days == -2) {
			name = "前天";
		} else {
			DateTime input = new DateTime(t);
			if (new DateTime().getYear() == input.getYear()) {
				name = input.toString("MM-dd HH:mm");
			} else {
				name = input.toString("yyyy-MM-dd HH:mm");
			}
		}
		return name;
	}

	/**
	 * 日期转模糊中文描述 转换为(今天,明天,后天,昨天,前,天,日期)+HH:mm
	 *
	 * @param t1
	 * @return 中文描述(今天,昨天,前天,日期)+HH:mm,两个时间是同一日，则只显示一个日期
	 */
	public static String time2cn4TwoDay(Timestamp t1, Timestamp t2) {
		if (t1 == null || t2 == null) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		int days1 = getDays(t1);
		if (days1 == getDays(t2)) {// 同一天
			sb.append(getDayName(t1));
			if (days1 > -3 && days1 < 3) {
				sb.append(new DateTime(t1).toString("HH:mm"));
			}
			sb.append("~").append(new DateTime(t2).toString("HH:mm"));
		} else {
			sb.append(getDayName(t1));
			if (days1 > -3 && days1 < 3) {
				sb.append(new DateTime(t1).toString("HH:mm"));
			}
			sb.append("~").append(getDayName(t2));
			int days2 = getDays(t2);
			if (days2 > -3 && days2 < 3) {
				sb.append(new DateTime(t2).toString("HH:mm"));
			}
		}
		return sb.toString();
	}

	public static void main(String[] arg) {
		DateTime d = new DateTime();
		System.out.println(d.getMillis());
		Timestamp t = new Timestamp(d.getMillis());
		System.out.println(t);
		System.out.println(new DateTime().getMillis());
	}
}
