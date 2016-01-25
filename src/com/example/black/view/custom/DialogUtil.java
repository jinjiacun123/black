package com.example.black.view.custom;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.support.v4.app.FragmentActivity;

public class DialogUtil {
	private static String mDialogTag = "dialog";
	private static MyDialogFragment newFragment;

	public static void ShowDialog(Context context, String title,
			String message, String positive_name, String negative_name,
			OnClickListener listener_1,
			OnClickListener listener_2) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title).setMessage(message);
		builder.setPositiveButton(positive_name, listener_1);
		builder.setNegativeButton(negative_name, listener_2);
		builder.show();
	}
	
	//自定义dialog
	public static MyDialogFragment showProgressDialog(Context context,String message,int indeterminateDrawable) {
		FragmentActivity activity = (FragmentActivity)context; 
		newFragment = MyDialogFragment.newInstance(indeterminateDrawable,message);
		FragmentTransaction ft = activity.getFragmentManager().beginTransaction();
        // 指定一个系统转场动画   
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);  
		newFragment.show(ft, mDialogTag);
	    return newFragment;
    }
	
	public static void dismissProgressDialog(){
		if (newFragment!=null) {
			newFragment.dismiss();
		}		
	}
}
