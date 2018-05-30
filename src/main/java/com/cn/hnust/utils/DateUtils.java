package com.cn.hnust.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtils {
 private static final Logger logger = LoggerFactory.getLogger(DateUtils.class);
	/**
	 * 获得指定日期在这个月的最后一天
	 * 
	 * @param date
	 * @param datePattern
	 * @return
	 */
	public static String getLastDayOfMonth(String date, String datePattern)
			throws ParseException {

		return DateUtils.getLastDayOfMonth(
				DateUtils.parseToDate(date, datePattern), datePattern);
	}

	public static String getLastDayOfMonth(Date date, String datePattern) {

		return DateUtils.formatDate(DateUtils.getLastDayOfMonth(date),
				datePattern);
	}

	public static Date getLastDayOfMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, 1);
		int dom = c.get(Calendar.DAY_OF_MONTH);
		c.add(Calendar.DATE, -dom);

		return c.getTime();
	}

	/**
	 * 获得指定日期在这个月的第一天
	 * 
	 * @param date
	 * @param datePattern
	 * @return
	 */
	public static String getFirstDayOfMonth(String date, String datePattern)
			throws ParseException {

		return DateUtils.getFirstDayOfMonth(
				DateUtils.parseToDate(date, datePattern), datePattern);
	}

	public static String getFirstDayOfMonth(Date date, String datePattern) {

		return DateUtils.formatDate(DateUtils.getFirstDayOfMonth(date),
				datePattern);
	}

	public static Date getFirstDayOfMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int dom = c.get(Calendar.DAY_OF_MONTH);
		c.add(Calendar.DATE, -dom + 1);
		return c.getTime();
	}

	/**
	 * 检查curDate是否在startDate和endDate内
	 * 
	 * @param curDate
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static boolean isInDateRange(Date curDate, Date startDate,
			Date endDate) {
		if (startDate == null || curDate == null) {
			return false;
		}

		if (curDate.compareTo(startDate) >= 0) {
			if (endDate == null) {
				return true;
			} else if (curDate.compareTo(endDate) <= 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 格式化日期，返回字符串内容 例如："yyyy-MM-dd HH:mm:ss"
	 * 
	 * @param d
	 * @param pattern
	 * @return
	 */
	public static String formatDate(Date d, String pattern) {
		SimpleDateFormat sf = new SimpleDateFormat(pattern);
		return sf.format(d);
	}

	public static String formatDate(Date d) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		return sf.format(d);
	}

	public static String formatCommonDate(Date d) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sf.format(d);
	}

	/**
	 * 返回20051012形式的int
	 * 
	 * @param d
	 *            Date
	 * @return int
	 */
	public static int getIntDate(Date d) {
		return new Integer(formatDate(d, "yyyyMMdd")).intValue();
	}

	/**
	 * 格式化日期，返回字符串内容 例如："yyyy-MM-dd HH:mm:ss"
	 * 
	 * @param d
	 * @param pattern
	 * @return
	 */
	public static String formatDate(Timestamp d, String pattern) {
		SimpleDateFormat sf = new SimpleDateFormat(pattern);
		return sf.format(d);
	}

	/**
	 * 字符串日期格式按照日期模式转换成日期
	 * 
	 * @param sDate
	 *            -- 字符串的日期
	 * @param pattern
	 *            -- 日期格式 （比如：yyyy-MM-dd）
	 * @return
	 * @throws ParseException
	 */
	public static Date parseToDate(String sDate, String pattern)
			throws ParseException {
		SimpleDateFormat sf = new SimpleDateFormat(pattern);
		return sf.parse(sDate);
	}

	public static Date parseToDateWithyyyy_MM_dd(String sDate)
			throws ParseException {
		return parseToDate(sDate, "yyyy-MM-dd");
	}

	public static String parseToDateString(String sDate) throws ParseException {
		StringBuffer date = new StringBuffer(sDate);
		date.insert(4, '-');
		date.insert(7, '-');
		return date.toString();

	}

	public static String parseToDateString(int sDate) throws ParseException {
		return parseToDateString(sDate + "");

	}

	public static Timestamp parseToTimestampWithyyyy_MM_dd(String dateString)
			throws ParseException {
		return new Timestamp(parseToDateWithyyyy_MM_dd(dateString).getTime());
	}

	/**
	 * 取当前时间
	 */
	public static Date getCurrentDate() {
		return new Date();
	}

	public static Date getMinTime(Date dt) {
		Date dt1 = null;
		try {
			dt1 = DateUtils.parseToDate(DateUtils.formatDate(dt, "yyyyMMdd"),
					"yyyyMMdd");
		} catch (ParseException e) {
			logger.error("date formate error ：" + dt + ".   ", e);
		}
		return dt1;
	}

	public static Date getMaxTime(Date dt) {
		Date dt1 = null;
		Calendar ca = Calendar.getInstance();
		ca.setTime(dt);
		ca.add(Calendar.DAY_OF_MONTH, 1);
		dt1 = ca.getTime();
		dt1 = DateUtils.getMinTime(dt1);
		ca.setTime(dt1);
		ca.add(Calendar.SECOND, -1);
		dt1 = ca.getTime();
		return dt1;
	}

	public static int getDay(Date dt) {
		Calendar c = Calendar.getInstance();
		c.setTime(dt);
		return c.get(Calendar.DAY_OF_MONTH);
	}

	public static int getMonth(Date dt) {
		Calendar c = Calendar.getInstance();
		c.setTime(dt);
		return c.get(Calendar.MONTH);
	}
	
	public static int getYear() {
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.YEAR);
	}

	public static int getIntMonth(Date dt) {
		return new Integer(formatDate(dt, "yyyyMM")).intValue();
	}

	public static int getSecondsBetweenDate(Date before, Date after) {
		Long sec = after.getTime() - before.getTime();
		Long t = sec / 1000;
		if (t > 0)
			return t.intValue();
		else
			return 0;
	}

	public static int getMinutesBetweenDate(Date before, Date after) {
		Long sec = after.getTime() - before.getTime();
		Long t = sec / (1000 * 60);
		if (t > 0)
			return t.intValue();
		else
			return 0;
	}

	public static Date parseToMinDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		Date end = null;
		try {
			end = sdf.parse(sdf1.format(date));
		} catch (ParseException e) {
			logger.error("DateUtils parseToMinDate()异常：",e);
		}
		return end;
	}

	public static Date parseToMaxDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
		Date end = null;
		try {
			end = sdf.parse(sdf1.format(date));
		} catch (ParseException e) {
			logger.error("DateUtils parseToMaxDate()异常：",e);
		}
		return end;
	}

	public static Date parseToTodayDesignatedDate(Date date, String time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd " + time);
		Date end = null;
		try {
			end = sdf.parse(sdf1.format(date));
		} catch (ParseException e) {
			logger.error("DateUtils parseToTodayDesignatedDate()异常：",e);
		}
		return end;
	}

	public static Date addMinuteToDate(Date date, int minutes) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.add(Calendar.MINUTE, minutes);
		return ca.getTime();
	}

	public static Date addDayToDate(Date date, int days) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.add(Calendar.DAY_OF_MONTH, days);
		return ca.getTime();
	}
	
	public static String getCurDTTM(){
		StringBuilder str = new StringBuilder();
		Date ca = Calendar.getInstance().getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");	
		str.append(sdf.format(ca)) ;		
		return str.toString() ;
	}
}
