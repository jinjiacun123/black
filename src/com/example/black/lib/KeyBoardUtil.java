package com.example.black.lib;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * 隐藏键盘
 * 
 * @author Admin
 * 
 */
public class KeyBoardUtil {

	public static void is_openKeyBoard(Context context,Activity activity) {
		// 得到InputMethodManager的实例
		InputMethodManager imm = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		// 如果开启
		if (imm.isActive()) {
		
			imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),
					InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}
	
	//禁止编辑文本框弹出
	public static void set_Hidden_KeyBoard(Context context){
		((Activity) context).getWindow().setSoftInputMode(
			       WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	}
	
	
	public static boolean set_isShowOfHidden_KeyBoard(Context context,
			int number, View view, EditText text) {
		boolean type = false;
		InputMethodManager manager = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		switch (number) {
		case 1:
			// 显示键盘
			type = manager.showSoftInput(view, InputMethodManager.SHOW_FORCED);
			break;
		case 2:
			// 关闭键盘
			type = manager.hideSoftInputFromWindow(text.getWindowToken(), 0);
			break;
		}
		return type;
	}
}
