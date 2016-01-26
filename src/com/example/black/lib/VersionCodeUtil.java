package com.example.black.lib;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * �汾��
 * 
 * @author Administrator
 * 
 */
public class VersionCodeUtil {
	/*
	 * ��ȡ��ǰ����İ汾��
	 */
	public static int getVersionCode(Context context) throws Exception {
		// ��ȡpackagemanager��ʵ��
		PackageManager packageManager = context.getPackageManager();
		// getPackageName()���㵱ǰ��İ�����0�����ǻ�ȡ�汾��Ϣ
		PackageInfo packInfo = packageManager.getPackageInfo(
				context.getPackageName(), 0);
		return packInfo.versionCode;
	}
}
