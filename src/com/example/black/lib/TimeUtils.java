package com.example.black.lib;

/**
 * ��ֹ�ؼ�ͬһʱ���ε��
 * 
 * @author Admin
 * 
 */
public class TimeUtils {
	private static long lastClickTime;

	//�����ڶ�ε���¼�ֻ����һ��
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
