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
//			Toast.makeText(context, "对不起，请您先安装微信客户端!", Toast.LENGTH_LONG).show();;
//		}
	}
	public static Bitmap myShot(Activity activity) {
		// 获取windows中最顶层的view
		View view = activity.getWindow().getDecorView();
		view.buildDrawingCache();
		// 获取状态栏高度
		Rect rect = new Rect();
		view.getWindowVisibleDisplayFrame(rect);
		int statusBarHeights = rect.top;
		Display display = activity.getWindowManager().getDefaultDisplay();
		// 获取屏幕宽和高
		int widths = display.getWidth();
		int heights = display.getHeight();
		// 允许当前窗口保存缓存信息
		view.setDrawingCacheEnabled(true);
		// 去掉状态栏
		Bitmap bmp = Bitmap.createBitmap(view.getDrawingCache(), 0,
		statusBarHeights, widths, heights - statusBarHeights);
		// 销毁缓存信息
		view.destroyDrawingCache();
		return bmp;
		}
}
