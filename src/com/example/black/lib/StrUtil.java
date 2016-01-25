package com.example.black.lib;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串处理
 * 
 * @author Admin
 * 
 */
public class StrUtil {

	//是否是汉字
	public static boolean isChinese(char c) {  
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);  
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS  
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS  
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A  
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION  
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION  
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {  
            return true;  
        }  
        return false;  
    }
	
	//输入的是汉字
	public boolean isChinese(String str){
		Pattern p=Pattern.compile("[\u4e00-\u9fa5]");  
	     Matcher m = p.matcher(str);  
	     return m.matches();
	}
	
	// 判断是否为手机号
    public boolean isPhone(String inputText) {
        Pattern p = Pattern
                .compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(inputText);
        return m.matches();
    }

    // 判断格式是否为email
    public boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }
	
	/**
	 *
	 * 是否为网址
	 *([\w-]+\.)+[\w-]+.([^a-z])(/[\w-: ./?%&=]*)?|[a-zA-Z\-\.][\w-]+.([^a-z])(/[\w-: ./?%&=]*)?
	 */
	
	public boolean isWWW(String str){
		Pattern pattern=Pattern.compile("([\\w-]+\\.)+[\\w-]+.([^a-z])(/[\\w-: ./?%&=]*)?|[a-zA-Z\\-\\.][\\w-]+.([^a-z])(/[\\w-: ./?%&=]*)?");
		Matcher matcher=pattern.matcher(str);
		if (matcher.find()) {
			return true;
		}
		return false;
	}
	
	/**
	 * "\\s*|\t|\r|\n"(空格、回车、换行符、制表符)
	 * 过滤空格和换行
	 * 
	 */
	 public static String replaceBlank(String str) {
	        String dest = "";
	        if (str!=null) {
	            Pattern p = Pattern.compile("\\s|\n");
	            Matcher m = p.matcher(str);
	            dest = m.replaceAll("");
	        }
	        return dest;
	    }
	

	/**
	 * 描述：不足2个字符的在前面补“0”.
	 * 
	 * @param str
	 *            指定的字符串
	 * @return 至少2个字符的字符串
	 */
	public static String strFormat2(String str) {
		try {
			if (str.length() <= 1) {
				str = "0" + str;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * 描述：判断一个字符串是否为null或空值.
	 * 
	 * @param str
	 *            指定的字符串
	 * @return true or false
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.trim().length() == 0;
	}
}
