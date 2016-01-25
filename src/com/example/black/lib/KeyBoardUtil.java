package com.example.black.lib;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * ���ؼ���
 * 
 * @author Admin
 * 
 */
public class KeyBoardUtil {

	public static void is_openKeyBoard(Context context,Activity activity) {
		// �õ�InputMethodManager��ʵ��
		InputMethodManager imm = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		// �������
		if (imm.isActive()) {
		
			imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),
					InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}
	
	//��ֹ�༭�ı��򵯳�
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
			// ��ʾ����
			type = manager.showSoftInput(view, InputMethodManager.SHOW_FORCED);
			break;
		case 2:
			// �رռ���
			type = manager.hideSoftInputFromWindow(text.getWindowToken(), 0);
			break;
		}
		return type;
	}
}
