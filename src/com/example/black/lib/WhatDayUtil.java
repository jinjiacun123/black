package com.example.black.lib;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 日期帮助类
 * @author stone
 *
 */
public class WhatDayUtil {
	/*
	 * 得到今天是星期几
	 */
	public static String getWeek(){
		 Calendar c = Calendar.getInstance();
		  c.setTime(new Date());
		  int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		  switch (dayOfWeek) {
		  case 1:
			  return "星期日";
		  case 2:
			  return "星期一";
		  case 3:
			  return "星期二";
		  case 4:
			  return "星期三";
		  case 5:
			  return "星期四";
		  case 6:
			  return "星期五";
		  default:
			  return "星期六";
		  }
	}
	/*
	 *把字符串按传入的格式  转换成date
	 */
	public  static Date getDate2(String format,String time){
		Date date = null;
		SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINA);
		try {
			date = sdf.parse(time);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
	/*
	 * 把时间戳转换成指定的格式
	 */
	public static String getDateString2 (String format,Date date){
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(date);
	}
	/*
	 * 把时间戳转换成指定的格式
	 */
	public static String getDateString (String format,long time){
		Date date = new Date(time*1000);
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(date);
	}
	/*
	 * 按传过来的转日期格式
	 * "yyyy-MM-dd HH:mm:ss"
	 */
	public static String getDate(String format){
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		Date date = new Date();
		return formatter.format(date);
	}
	/*
	 * 获得当前时间是下午还是上午
	 */
	public static Boolean getHours(long time){
		Date date = new Date(time);
		int hours = date.getHours();
		if(hours<12){
			return true;
		}else{
			return false;
		}
	}
	
	//设置下午时间
	public static StringBuffer setPm_time(String time){
		String string = String.valueOf((12+Integer.valueOf(time.substring(0, 2))));
		return new StringBuffer(string).append(time.substring(2, time.length()));
	}
	
	/*
	 * 看传入的时间是不是今天 或以前
	 */
	public static String isDate(String str){
		String str3 = str.substring(0, str.indexOf(" "));
		java.util.Date nowdate=new java.util.Date(); 
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
		SimpleDateFormat sdf3 = new SimpleDateFormat("HH:mm:ss", Locale.CHINA);
		String str2 = sdf.format(nowdate);
		Date d2 = null;
		Date d = null;
		Date d3 = null;
		try {
			d = sdf.parse(str3);
			d2 = sdf.parse(str2);
			d3 = sdf2.parse(str);
		} catch (Exception e) {
			e.printStackTrace();
		}

		boolean flag = d.before(d2);
		if(flag){
			return str.substring(0, str.indexOf(" "));
		}else{
			return sdf3.format(d3);
		}
	}
}
