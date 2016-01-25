package com.example.black.lib;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import android.text.TextUtils;

/**
 * �ֻ�������֤
 * 
 * @author Admin
 * 
 */
public class Mobile_PhoneUtil {
	public static boolean isMobileNO(String mobiles) {
		/*
		 * �ƶ���134��135��136��137��138��139��150��151��152��157(TD)��158��159��181��182��183��187��188
		 * ��ͨ��130��131��132��155��156��185��186
		 *  ���ţ�133��153��180��189����1349��ͨ��
		 * �ܽ��������ǵ�һλ�ض�Ϊ1���ڶ�λ�ض�Ϊ3��5��8������λ�õĿ���Ϊ0-9
		 */
		// "[1]"�����1λΪ����1��"[358]"����ڶ�λ����Ϊ3��5��8�е�һ����"\\d{9}"��������ǿ�����0��9�����֣���9λ��
		String telRegex = "[1][358]\\d{9}";
		if (TextUtils.isEmpty(mobiles))
			return false;
		else
			return mobiles.matches(telRegex);
	}

	// ������Ź���(�� @ # $ % ^ & * ( ) < >)
	public static boolean hasSpecialCharacter(String str) {
		// String regEx="[^a-zA-Z0-9]";
		// String regEx="[~!@#$%^&*<>]";
		String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~��@#��%������������+|{}������������������������]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		if (m.find()) {
			return true;
		}
		return false;
	}

	public static boolean isChinese(String str) {
		// String regEx = "[u4e00-u9fa5]";
		String regEx = "^[\u4E00-\u9FA5\uF900-\uFA2D]+$";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		if (m.find()) {
			return true;
		}
		return false;
	}

	// ��������������
	public static String StringFilter(String str) throws PatternSyntaxException {
		// ֻ������ĸ������
		// String regEx = "[^a-zA-Z0-9]";
		// ��������������ַ�
		String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~��@#��%����& amp;*��������+|{}������������������������]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}
}
