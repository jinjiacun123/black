package com.example.black.lib;

import android.content.Context;

public class DisplayUtil {
	 /** 
     * ��dip��dpֵת��Ϊpxֵ����֤�ߴ��С����
     */  
    public static int dip2px(Context context, float dpValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (dpValue * scale + 0.5f);  
    }  
  
    /** 
     * ��pxֵת��Ϊdip��dpֵ����֤�ߴ��С����
     */  
    public static int px2dip(Context context, float pxValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (pxValue / scale + 0.5f);  
    }  
    
    /**
     * ��spֵת��Ϊpxֵ����֤���ִ�С����
     * @param context
     * @param spValue
     * @return
     */
    public static int sp2px(Context context, float spValue) { 
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity; 
        return (int) (spValue * fontScale + 0.5f); 
    } 
    
    /**
     * ��pxֵת��Ϊspֵ����֤���ִ�С����
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2sp(Context context, float pxValue) { 
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity; 
        return (int) (pxValue / fontScale + 0.5f); 
    } 
}
