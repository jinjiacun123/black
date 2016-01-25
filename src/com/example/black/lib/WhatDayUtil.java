package com.example.black.lib;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * ���ڰ�����
 * @author stone
 *
 */
public class WhatDayUtil {
	/*
	 * �õ����������ڼ�
	 */
	public static String getWeek(){
		 Calendar c = Calendar.getInstance();
		  c.setTime(new Date());
		  int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		  switch (dayOfWeek) {
		  case 1:
			  return "������";
		  case 2:
			  return "����һ";
		  case 3:
			  return "���ڶ�";
		  case 4:
			  return "������";
		  case 5:
			  return "������";
		  case 6:
			  return "������";
		  default:
			  return "������";
		  }
	}
	/*
	 *���ַ���������ĸ�ʽ  ת����date
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
	 * ��ʱ���ת����ָ���ĸ�ʽ
	 */
	public static String getDateString2 (String format,Date date){
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(date);
	}
	/*
	 * ��ʱ���ת����ָ���ĸ�ʽ
	 */
	public static String getDateString (String format,long time){
		Date date = new Date(time*1000);
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(date);
	}
	/*
	 * ����������ת���ڸ�ʽ
	 * "yyyy-MM-dd HH:mm:ss"
	 */
	public static String getDate(String format){
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		Date date = new Date();
		return formatter.format(date);
	}
	/*
	 * ��õ�ǰʱ�������绹������
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
	
	//��������ʱ��
	public static StringBuffer setPm_time(String time){
		String string = String.valueOf((12+Integer.valueOf(time.substring(0, 2))));
		return new StringBuffer(string).append(time.substring(2, time.length()));
	}
	
	/*
	 * �������ʱ���ǲ��ǽ��� ����ǰ
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
