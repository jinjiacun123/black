package com.example.black.lib;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.view.Display;
import android.view.View;

public class WeiXinShareController {

	public static void sendToWeiXin(Context context,int type,String url ,String connten,String title,Bitmap bitmap){
		WeixinShareManager wsm = WeixinShareManager.getInstance(context);
//		if(wsm.isWXAppInstalled()){
//			wsm.shareByWeixin(type, url,connten, title, bitmap);
//		}else{
//			Toast.makeText(context, "�Բ��������Ȱ�װ΢�ſͻ���!", Toast.LENGTH_LONG).show();;
//		}
	}
	public static Bitmap myShot(Activity activity) {
		// ��ȡwindows������view
		View view = activity.getWindow().getDecorView();
		view.buildDrawingCache();
		// ��ȡ״̬���߶�
		Rect rect = new Rect();
		view.getWindowVisibleDisplayFrame(rect);
		int statusBarHeights = rect.top;
		Display display = activity.getWindowManager().getDefaultDisplay();
		// ��ȡ��Ļ��͸�
		int widths = display.getWidth();
		int heights = display.getHeight();
		// ����ǰ���ڱ��滺����Ϣ
		view.setDrawingCacheEnabled(true);
		// ȥ��״̬��
		Bitmap bmp = Bitmap.createBitmap(view.getDrawingCache(), 0,
		statusBarHeights, widths, heights - statusBarHeights);
		// ���ٻ�����Ϣ
		view.destroyDrawingCache();
		return bmp;
		}
}
