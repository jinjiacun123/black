package com.example.black.lib;

/**
 * 防止控件同一时间多次点击
 * 
 * @author Admin
 * 
 */
public class TimeUtils {
	private static long lastClickTime;

	//两秒内多次点击事件只触发一次
	public static boolean isFastDoubleClick(long max_time) {
		long time = System.currentTimeMillis();
		long timeD = time - lastClickTime;
		if (0 < timeD && timeD < max_time) {
			return false;
		}
		lastClickTime = time;
		return true;
	}
}
