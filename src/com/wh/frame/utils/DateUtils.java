package com.wh.frame.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import android.util.Log;

public class DateUtils {
	public static ThreadLocal<SimpleDateFormat> M_d = new ThreadLocal<SimpleDateFormat>() {
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("M-d");
		}
	};
	public static ThreadLocal<SimpleDateFormat> yyyy_MM_dde = new ThreadLocal<SimpleDateFormat>() {
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd E");
		}
	};
	private static ThreadLocal<SimpleDateFormat> chineseyyyyMMdd = new ThreadLocal<SimpleDateFormat>() {
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy年MM月dd日");
		}
	};
	private static ThreadLocal<SimpleDateFormat> chineseMMdd = new ThreadLocal<SimpleDateFormat>() {
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("MM月dd日");
		}
	};
	private static ThreadLocal<SimpleDateFormat> yyyy_MM_dd_HHmmss = new ThreadLocal<SimpleDateFormat>() {
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
	};
	private static ThreadLocal<SimpleDateFormat> HHmmss = new ThreadLocal<SimpleDateFormat>() {
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("HH:mm:ss");
		}
	};
	public static ThreadLocal<SimpleDateFormat> yyyyMMddHHmmss = new ThreadLocal<SimpleDateFormat>() {
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyyMMddHHmmss");
		}
	};
	private static ThreadLocal<SimpleDateFormat> MMddHHmmss = new ThreadLocal<SimpleDateFormat>() {
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("MMddHHmmss");
		}
	};
	public static ThreadLocal<SimpleDateFormat> yyyy_MM_dd = new ThreadLocal<SimpleDateFormat>() {
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd");
		}
	};
	public static ThreadLocal<SimpleDateFormat> MMddyyyyHHmmss = new ThreadLocal<SimpleDateFormat>() {
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("MMddyyyyHHmmss");
		}
	};
	

	private static final String TAG = "DateUtils";
	@SuppressWarnings("unused")
	private static String weekStr[] = { "星期日", "星期一", "星期二", "星期三", "星期四",
			"星期五", "星期六" };
	@SuppressWarnings("unused")
	private static String weekStr1[] = { "sunday", "monday", "tuesday",
			"wednesday", "thursday", "friday", "saturday" };

	/**
	 * 获得当月第一天是星期几
	 * 
	 * @param calendar
	 * @return
	 */
	public static int getWeekOfFirstDayInMonth(Calendar calendar) {
		// 取得当月第一天
		int firstDate = calendar.getActualMinimum(Calendar.DATE);

		// calendar设定日期为当月第一天，通过Calendar.DAY_OF_WEEK取得周几
		calendar.set(Calendar.DATE, firstDate);
		int firstDay = calendar.get(Calendar.DAY_OF_WEEK);

		// 将取到的值转换为中国对应的星期值
		if (firstDay > 1) {
			firstDay = firstDay - 1;
		} else if (firstDay == 1) {
			firstDay = firstDay + 6;
		}

		// 1--->星期一，2-->星期二,.....6--->星期六,7--->星期日
		return firstDay;

	}

	/**
	 * 获得当月最后一天是星期几
	 * 
	 * @param calendar
	 * @return
	 */
	public static int getWeekOfEndDayInMonth(Calendar calendar) {
		// 取得当月第一天
		int endDate = calendar.getActualMaximum(Calendar.DATE);

		// calendar设定日期为当月第一天，通过Calendar.DAY_OF_WEEK取得周几
		calendar.set(Calendar.DATE, endDate);
		int endDay = calendar.get(Calendar.DAY_OF_WEEK);

		// 国内地区，lastDay的值为1-7，1表示周日，2表示周一.....7表示周六
		return endDay - 1;

	}

	// 获得前一个月的总天数
	public static int getMaxDaysOfLastMonth() {
		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.MONTH, -1);// 减一个月
		lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		lastDate.roll(Calendar.DATE, -1);// 日期回滚一天，也就是本月最后一天
		int lastMonthMaxDays = lastDate.get(Calendar.DAY_OF_MONTH);
		System.out.println(lastMonthMaxDays);
		return lastMonthMaxDays;
	}

	/**
	 * 获得当前时间的“时间戳”
	 * 
	 * @param date
	 *            需要格式化的日期
	 * @param format
	 *            格式，例如："yyyy年MM月dd日","yyyyMMddHHmmss"
	 * @return timestamp 格式化后的数据
	 */
	public static String getFormatTime(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String timestamp = sdf.format(date);
		return timestamp;
	}

	/**
	 * 毫秒时间转字符串(默认时区)
	 * 
	 * @param milliseconds
	 * @param pattern
	 */
	public static String toDateString(long milliseconds, String pattern) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			sdf.setTimeZone(TimeZone.getDefault());
			return sdf.format(milliseconds);
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 字符串转毫秒时间(指定时区)
	 * 
	 * @param date
	 * @param pattern
	 * @param timeZone
	 * @return
	 */
	public static long toMilliseconds(String date, String pattern,
			String timeZone) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			sdf.setTimeZone(TimeZone.getTimeZone(timeZone));
			return sdf.parse(date).getTime();
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * date转毫秒数
	 * 
	 * @param date
	 * @return
	 */
	public static long toMilliseconds(Date date) {
		long millis = 0;
		try {
			millis = date.getTime();
		} catch (Exception e) {
			millis = new Date().getTime();
		}
		return millis;
	}

	/**
	 * 返回两日期之间相差的精确天数
	 * 
	 * @param startDay
	 * @param endDay
	 * @return
	 */
	public static int getAccurateIntervalDays(Calendar startDay, Calendar endDay) {
		int days = endDay.get(Calendar.DAY_OF_YEAR)
				- startDay.get(Calendar.DAY_OF_YEAR);
		int endYear = endDay.get(Calendar.YEAR);
		if (startDay.get(Calendar.YEAR) != endYear) {
			startDay = (Calendar) startDay.clone();
			do {
				days += startDay.getActualMaximum(Calendar.DAY_OF_YEAR); // 得到当年的实际天数
				startDay.add(Calendar.YEAR, 1);
			} while (startDay.get(Calendar.YEAR) != endYear);
		}
		return days;
	}

	public static int getWeekOfCal(Calendar cal) {
		int week = cal.get(Calendar.DAY_OF_WEEK);
		// 将取到的值转换为中国对应的星期值
		if (week > 1) {
			week = week - 1;
		} else if (week == 1) {
			week = week + 6;
		}
		// 1--->星期一，2-->星期二,.....6--->星期六,7--->星期日
		return week;
	}

	/**
	 * 通过分钟数得到小时
	 * 
	 * @param minute
	 *            (距当日0点的分钟数)
	 * @return
	 * @author nieyinyin
	 */
	public static int getHourFromMinute(int minute) {
		int hour;
		try {
			hour = (int) (minute / 60);
		} catch (Exception e) {
			hour = 0;
		}
		return hour;
	}

	/**
	 * 通过分钟数得到分钟 (距当日0点的 分钟数)
	 * 
	 * @param minute
	 * @return
	 * @author nieyinyin
	 */
	public static int getMinuteFromMinute(int minute) {
		try {
			minute = (int) minute % 60;
		} catch (Exception e) {
			minute = 0;
		}
		return minute;
	}

	/**
	 * 通过毫秒数得到小时
	 * 
	 * @param milliseconds
	 * @return
	 * @author nieyinyin
	 */
	public static int getHourFromMills(long milliseconds) {
		int hour;
		try {
			String hourStr = toDateString(milliseconds, "HH");
			hour = Integer.parseInt(hourStr);
		} catch (Exception e) {
			hour = 0;
		}
		return hour;
	}

	/**
	 * 通过毫秒数得到分钟
	 * 
	 * @param milliseconds
	 * @return
	 * @author nieyinyin
	 */
	public static int getMinuteFromMills(long milliseconds) {
		int minute;
		try {
			String minuteStr = toDateString(milliseconds, "mm");
			minute = Integer.parseInt(minuteStr);
		} catch (Exception e) {
			minute = 0;
		}
		return minute;
	}

	/**
	 * 毫秒数转Calendar
	 * 
	 * @param milliseconds
	 * @return
	 * @author nieyinyin
	 */
	public static Calendar mills2Cal(long milliseconds) {
		Calendar calendar = Calendar.getInstance();
		try {
			Date date = new Date(milliseconds);
			calendar.setTime(date);
		} catch (Exception e) {
			LogUtils.logE(TAG, e.getLocalizedMessage(), e);
		}
		return calendar;
	}

	public static boolean isInNext3Days(Calendar currentCal) {
		boolean flag = false;
		try {
			// 当前时间
			currentCal.set(Calendar.HOUR_OF_DAY, 0);
			currentCal.set(Calendar.MINUTE, 0);
			currentCal.set(Calendar.SECOND, 0);
			currentCal.set(Calendar.MILLISECOND, 0);
			// 今天
			Calendar today = Calendar.getInstance();
			today.set(Calendar.HOUR_OF_DAY, 0);
			today.set(Calendar.MINUTE, 0);
			today.set(Calendar.SECOND, 0);
			today.set(Calendar.MILLISECOND, 0);
			// 明天
			Calendar tomorrow = (Calendar) today.clone();
			tomorrow.add(Calendar.DATE, 1);
			// 后天
			Calendar dayAfterTom = (Calendar) today.clone();
			dayAfterTom.add(Calendar.DATE, 2);

			if (currentCal.compareTo(today) == 0
					|| currentCal.compareTo(tomorrow) == 0
					|| currentCal.compareTo(dayAfterTom) == 0) {
				flag = true;
			}
		} catch (Exception e) {
			LogUtils.logE(TAG, e.getLocalizedMessage(), e);
			flag = false;
		}
		return flag;
	}

	/**
	 * 将毫秒数格式化为时间，如：18:00
	 * 
	 * @param millisTime
	 *            毫秒时间
	 * @return 格式化后的时间 如：18:00
	 */
	public static String millis2HourTime(long millisTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		return sdf.format(new Date(millisTime));
	}

	/**
	 * 将格式化时间转化为毫秒时间
	 * 
	 * @param time
	 *            格式化时间 如2014-1-1
	 * @param pattern
	 *            时间格式 如yyyy-M-d
	 * @return 格式化后的时间 如：18:00
	 */
	public static long formatTime2Millis(String time, String pattern) {

		SimpleDateFormat sdf = new SimpleDateFormat(pattern);

		try {
			return sdf.parse(time).getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			Log.i("DateUtils", "formatTime2Millis error");
		}
		return 0L;
	}

	/**
	 * 获取一天的开始时间：00:00分
	 * 
	 * @return
	 */
	public static long getOneDayStart(Date date) {
		Calendar calStart = Calendar.getInstance();
		calStart.setTime(date);
		calStart.set(Calendar.HOUR_OF_DAY, 0);
		calStart.set(Calendar.MINUTE, 0);
		calStart.set(Calendar.SECOND, 0);
		calStart.set(Calendar.MILLISECOND, 0);

		return calStart.getTimeInMillis();
	}
	/**
	 * 获取形如："2014-07-07 10:30  星期一 " 格式的日期字符串
	 * 
	 * @param date
	 * @return
	 */
	public static String getYMDTimeWeek(Calendar cal) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
			SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

			return sdf.format(cal.getTime()) + "  "
					+timeFormat.format(cal.getTime())+ "  "
			+weekStr[cal.get(Calendar.DAY_OF_WEEK) - 1];
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public static Date addDate(final Date dt, final int num) {
		final Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(dt);
		rightNow.add(Calendar.DATE, num);// 你要加减的日
		final Date result = rightNow.getTime();
		return result;
	}

}
