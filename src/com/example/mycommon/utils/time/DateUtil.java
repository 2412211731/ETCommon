package com.example.mycommon.utils.time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	public static long day = 24 * 60 * 60 * 1000;

	public static SimpleDateFormat yymmdd = new SimpleDateFormat("yyyy-MM-dd");

	public static SimpleDateFormat hhmm = new SimpleDateFormat("HH:mm");

	public static SimpleDateFormat mmddhhmm = new SimpleDateFormat(
			"MM-dd HH:mm");

	public static SimpleDateFormat yymmddhhmmss = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	public static String week(int w) {
		switch (w) {
		case 0:
			return "星期天";
		case 1:
			return "星期一";
		case 2:
			return "星期二";
		case 3:
			return "星期三";
		case 4:
			return "星期四";
		case 5:
			return "星期五";
		case 6:
			return "星期六";
		}
		return null;
	}

	// 将时间格式化为如下格式
	// 1. 今天 上午 11:00
	// 2. 04-27 下午 5:00
	// 3. 2011-03-18 上午 8:00
	public static String format_1(Date time) {
		Calendar c = Calendar.getInstance(); // 今天
		long jt = c.getTimeInMillis();
		int jyear = c.get(Calendar.YEAR);
		int jmonth = c.get(Calendar.MONTH);
		int jday = c.get(Calendar.DATE);

		c.add(Calendar.DATE, 1); // 明天
		int myear = c.get(Calendar.YEAR);
		int mmonth = c.get(Calendar.MONTH);
		int mday = c.get(Calendar.DATE);

		c.add(Calendar.DATE, -2); // 昨天
		int zyear = c.get(Calendar.YEAR);
		int zmonth = c.get(Calendar.MONTH);
		int zday = c.get(Calendar.DATE);

		c.add(Calendar.DATE, -1); // 前天
		int qyear = c.get(Calendar.YEAR);
		int qmonth = c.get(Calendar.MONTH);
		int qday = c.get(Calendar.DATE);

		c.setTime(time);
		long t = c.getTimeInMillis();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DATE);
		int hh = c.get(Calendar.HOUR_OF_DAY);
		int hour = c.get(Calendar.HOUR);
		hour = hour == 0 ? 12 : hour;
		int minute = c.get(Calendar.MINUTE);

		if (jt - t < 600000) {
			return "刚刚";
		}

		StringBuffer buffer = new StringBuffer();
		if (year != jyear) {
			buffer.append(year + "-");
		}

		if (year == jyear && month == jmonth && day == jday) {
			buffer.append("今天 ");
		} else if (year == zyear && month == zmonth && day == zday) {
			buffer.append("昨天 ");
		} else if (year == myear && month == mmonth && day == mday) {
			buffer.append("明天 ");
		} else if (year == qyear && month == qmonth && day == qday) {
			buffer.append("前天 ");
		} else {
			buffer.append((month + 1) + "-" + day + " ");
		}

		if (hh < 6) {
			buffer.append("凌晨 ");
		} else if (hh < 9) {
			buffer.append("早上 ");
		} else if (hh < 12) {
			buffer.append("上午 ");
		} else if (hh < 14) {
			buffer.append("中午 ");
		} else if (hh < 19) {
			buffer.append("下午 ");
		} else {
			buffer.append("晚上 ");
		}

		buffer.append(hour + ":" + (minute < 10 ? ("0" + minute) : minute));
		return buffer.toString();
	}

	public static String format_1(String timeStr) {
		try {
			return format_1(yymmddhhmmss.parse(timeStr));
		} catch (ParseException e) {
			return "";
		}
	}

	public static String format_2(String timeStr) {
		try {
			return format_2(yymmddhhmmss.parse(timeStr));
		} catch (ParseException e) {
			return "";
		}
	}

	// 将时间格式化为如下格式
	// 1. 今天
	// 2. 昨天
	// 3. 8月1日
	public static String format_2(Date time) {
		Calendar c = Calendar.getInstance(); // 今天
		long jt = c.getTimeInMillis();
		int jyear = c.get(Calendar.YEAR);
		int jmonth = c.get(Calendar.MONTH);
		int jday = c.get(Calendar.DATE);

		c.add(Calendar.DATE, 1); // 明天
		int myear = c.get(Calendar.YEAR);
		int mmonth = c.get(Calendar.MONTH);
		int mday = c.get(Calendar.DATE);

		c.add(Calendar.DATE, -2); // 昨天
		int zyear = c.get(Calendar.YEAR);
		int zmonth = c.get(Calendar.MONTH);
		int zday = c.get(Calendar.DATE);

		c.add(Calendar.DATE, -1); // 前天
		int qyear = c.get(Calendar.YEAR);
		int qmonth = c.get(Calendar.MONTH);
		int qday = c.get(Calendar.DATE);

		c.setTime(time);
		long t = c.getTimeInMillis();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DATE);
		int hh = c.get(Calendar.HOUR_OF_DAY);
		int hour = c.get(Calendar.HOUR);
		hour = hour == 0 ? 12 : hour;
		int minute = c.get(Calendar.MINUTE);

		if (jt - t < 600000) {
			return "刚刚";
		}

		StringBuffer buffer = new StringBuffer();
		if (year != jyear) {
			buffer.append(year + "年" + (month + 1) + "月" + day + "日");
			return buffer.toString();
		}

		if (year == jyear && month == jmonth && day == jday) {
			buffer.append("今天 ");
		} else if (year == zyear && month == zmonth && day == zday) {
			buffer.append("昨天 ");
		} else if (year == myear && month == mmonth && day == mday) {
			buffer.append("明天 ");
		} else if (isSameWeek(jyear + "-" + (jmonth+1) + "-" + jday, year + "-"
				+ month + "-" + day)) {// 同一周 
			c.setTime(time);
			int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
			switch (dayOfWeek) {
			case Calendar.MONDAY:
				return "星期一";
			case Calendar.TUESDAY:
				return "星期二";
			case Calendar.WEDNESDAY:
				return "星期三";
			case Calendar.THURSDAY:
				return "星期四";
			case Calendar.FRIDAY:
				return "星期五";
			case Calendar.SATURDAY:
				return "星期六";
			case Calendar.SUNDAY:
				return "星期天";
			}
		} else {
			buffer.append((month + 1) + "月" + day + "日");
		}

		return buffer.toString();
	}

	// 计算2个时间差
	public static long sub(String t1, String t2) throws Exception {
		return sub(yymmddhhmmss.parse(t1), yymmddhhmmss.parse(t2));
	}

	public static long sub(Date t1, Date t2) {
		return t1.getTime() - t2.getTime();
	}

	// 方法名称：isSameDate(String date1,String date2)
	// 功能描述：判断date1和date2是否在同一周
	// 输入参数：date1,date2
	// 输出参数：
	// 返 回 值：false 或 true
	// 其它说明：主要用到Calendar类中的一些方法
	// -----------------------------
	public static boolean isSameWeek(String date1, String date2) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date d1 = null;
		Date d2 = null;
		try {
			d1 = format.parse(date1);
			d2 = format.parse(date2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(d1);
		cal2.setTime(d2);
		int subYear = cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR);
		// subYear==0,说明是同一年
		if (subYear == 0) {
			if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2
					.get(Calendar.WEEK_OF_YEAR))
				return true;
		}
		// 例子:cal1是"2005-1-1"，cal2是"2004-12-25"
		// java对"2004-12-25"处理成第52周
		// "2004-12-26"它处理成了第1周，和"2005-1-1"相同了
		// 大家可以查一下自己的日历
		// 处理的比较好
		// 说明:java的一月用"0"标识，那么12月用"11"
		else if (subYear == 1 && cal2.get(Calendar.MONTH) == 11) {
			if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2
					.get(Calendar.WEEK_OF_YEAR))
				return true;
		}
		// 例子:cal1是"2004-12-31"，cal2是"2005-1-1"
		else if (subYear == -1 && cal1.get(Calendar.MONTH) == 11) {
			if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2
					.get(Calendar.WEEK_OF_YEAR))
				return true;

		}
		return false;
	}

}
