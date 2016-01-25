package com.example.black.lib;

import android.content.Context;

public class WeixinShareManager {
	private static final int THUMB_SIZE = 150;
	/**
	 * 文字
	 */
	public static final int WEIXIN_SHARE_WAY_TEXT = 1;
	/**
	 * 图片
	 */
	public static final int WEIXIN_SHARE_WAY_PIC = 2;
	/**
	 * 链接
	 */
	public static final int WEIXIN_SHARE_WAY_WEBPAGE = 3;
	/**
	 * 会话
	 */
//	public static final int WEIXIN_SHARE_TYPE_TALK = SendMessageToWX.Req.WXSceneSession;  
	/**
	 * 朋友圈
	 */
//	public static final int WEIXIN_SHARE_TYPE_FRENDS = SendMessageToWX.Req.WXSceneTimeline;
	private static WeixinShareManager instance;
	private static String weixinAppId;
//	private IWXAPI wxApi;
	private Context context;
	
	

	private WeixinShareManager(Context context){
		this.context = context;
		//初始化数据
		weixinAppId = WeixiShareUtil.getWeixinAppId();
		//初始化微信分享代码
		if(weixinAppId != null){
			
//			initWeixinShare(context);
		}
	}
	
	/**
	 * 获取WeixinShareManager实例
	 * 非线程安全，请在UI线程中操作
	 * @return
	 */
	public static WeixinShareManager getInstance(Context context){
		if(instance == null){
			instance = new WeixinShareManager(context);
		}
		return instance;
	}

	
//	private void initWeixinShare(Context context){
//		wxApi = WXAPIFactory.createWXAPI(context, weixinAppId, true);
//		wxApi.registerApp(weixinAppId);
//	}
//	
//	public IWXAPI getapi(){
//		return wxApi;
//	}
	/**
	 * 通过微信分享
	 * @param shareWay 分享的方式（文本、图片、链接）
	 * @param shareType 分享的类型（朋友圈，会话）
	 */
//	public void shareByWeixin( int shareType){
////		String text = "heheh";
////		//初始化一个WXTextObject对象
////		WXTextObject textObj = new WXTextObject();
////		textObj.text = text;
////		//用WXTextObject对象初始化一个WXMediaMessage对象
////		WXMediaMessage msg = new WXMediaMessage();
////		msg.mediaObject = textObj;
////		msg.description = text;
////		//构造一个Req
////		SendMessageToWX.Req req = new SendMessageToWX.Req();
////		//transaction字段用于唯一标识一个请求
////		req.transaction = buildTransaction("textshare");
////		req.message = msg;
////		//发送的目标场景， 可以选择发送到会话 WXSceneSession 或者朋友圈 WXSceneTimeline。 默认发送到会话。
////		req.scene = shareType;
////		wxApi.sendReq(req);
//		
//		String url = "http://www.huanrong9999.com";//收到分享的好友点击信息会跳转到这个地址去  
//		 WXWebpageObject localWXWebpageObject = new WXWebpageObject();  
//		   localWXWebpageObject.webpageUrl = url;
//		   WXMediaMessage localWXMediaMessage = new WXMediaMessage(localWXWebpageObject);
//		   localWXMediaMessage.title = "搜黑";
//		   localWXMediaMessage.description = "搜黑——全国最大的查询曝光平台，Url:www.huanrong9999.com";
//		   Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.logo);
//		   localWXMediaMessage.thumbData = getBitmapBytes(bmp, false);
//		   bmp.recycle();
//
//		// 构造一个Req
//		SendMessageToWX.Req req = new SendMessageToWX.Req();
//		req.transaction = buildTransaction("text/img"); // transaction字段用于唯一标识一个请求
//		req.message = localWXMediaMessage;
//		req.scene = shareType;
//		// 调用api接口发送数据到微信
//		wxApi.sendReq(req);
//	}
	
	/*
	 * 发送微信分享请求
//	 */
//	public void shareByWeixin( int shareType,String url,String content,String title,Bitmap bitmap){
//		 WXWebpageObject localWXWebpageObject = new WXWebpageObject();  
//		   localWXWebpageObject.webpageUrl = url;
//		   WXMediaMessage localWXMediaMessage = new WXMediaMessage(localWXWebpageObject);
//		   localWXMediaMessage.title = title;
//		   localWXMediaMessage.description = content;
////		   Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.logo);
//		   if(bitmap != null){
//			   localWXMediaMessage.thumbData = getBitmapBytes(bitmap, false);
////			   bitmap.recycle();
//		   }
//			SendMessageToWX.Req req = new SendMessageToWX.Req();
//			req.transaction = buildTransaction("text/img");//transaction字段用于唯一标识一个请求
//			req.message = localWXMediaMessage;
//			req.scene = shareType;
//			// 调用api接口发送数据到微信
//			wxApi.sendReq(req);
//	}
//	private  byte[] getBitmapBytes(Bitmap bitmap, boolean paramBoolean) {  
//		Bitmap localBitmap = Bitmap.createBitmap(80, 80, Bitmap.Config.RGB_565);  
//		Canvas localCanvas = new Canvas(localBitmap);   
//		int i;        int j;        
//		if (bitmap.getHeight() > bitmap.getWidth()) {   
//			i = bitmap.getWidth();            
//			j = bitmap.getWidth();   
//			} else {   
//				i = bitmap.getHeight();            
//				j = bitmap.getHeight();        
//				}        while (true) {  
//					localCanvas.drawBitmap(bitmap, new Rect(0, 0, i, j),
//							new Rect(0, 0,80, 80), null);  
//					if (paramBoolean)
//						bitmap.recycle();
//					ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream(); 
//					localBitmap.compress(Bitmap.CompressFormat.JPEG, 100,       
//							localByteArrayOutputStream);            localBitmap.recycle();  
//							byte[] arrayOfByte = localByteArrayOutputStream.toByteArray();    
//							try {                localByteArrayOutputStream.close();          
//							return arrayOfByte;            }
//							catch (Exception e) {            
//								            }           
//							i = bitmap.getHeight();   
//							j = bitmap.getHeight();        }   
//				}
//	//判断app是否安装了微信
//	public boolean isWXAppInstalled(){
//		return wxApi.isWXAppInstalled();
//	}
//	
//	private abstract class ShareContent{
//		protected abstract int getShareWay();
//		protected abstract String getContent();
//		protected abstract String getTitle();
//		protected abstract String getURL();
//		protected abstract int getPicResource();
//		
//	}
//	private String buildTransaction(final String type) {
//		return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
//	}
}
