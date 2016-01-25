package com.example.black.lib;

import android.content.Context;

public class WeixinShareManager {
	private static final int THUMB_SIZE = 150;
	/**
	 * ����
	 */
	public static final int WEIXIN_SHARE_WAY_TEXT = 1;
	/**
	 * ͼƬ
	 */
	public static final int WEIXIN_SHARE_WAY_PIC = 2;
	/**
	 * ����
	 */
	public static final int WEIXIN_SHARE_WAY_WEBPAGE = 3;
	/**
	 * �Ự
	 */
//	public static final int WEIXIN_SHARE_TYPE_TALK = SendMessageToWX.Req.WXSceneSession;  
	/**
	 * ����Ȧ
	 */
//	public static final int WEIXIN_SHARE_TYPE_FRENDS = SendMessageToWX.Req.WXSceneTimeline;
	private static WeixinShareManager instance;
	private static String weixinAppId;
//	private IWXAPI wxApi;
	private Context context;
	
	

	private WeixinShareManager(Context context){
		this.context = context;
		//��ʼ������
		weixinAppId = WeixiShareUtil.getWeixinAppId();
		//��ʼ��΢�ŷ������
		if(weixinAppId != null){
			
//			initWeixinShare(context);
		}
	}
	
	/**
	 * ��ȡWeixinShareManagerʵ��
	 * ���̰߳�ȫ������UI�߳��в���
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
	 * ͨ��΢�ŷ���
	 * @param shareWay ����ķ�ʽ���ı���ͼƬ�����ӣ�
	 * @param shareType ��������ͣ�����Ȧ���Ự��
	 */
//	public void shareByWeixin( int shareType){
////		String text = "heheh";
////		//��ʼ��һ��WXTextObject����
////		WXTextObject textObj = new WXTextObject();
////		textObj.text = text;
////		//��WXTextObject�����ʼ��һ��WXMediaMessage����
////		WXMediaMessage msg = new WXMediaMessage();
////		msg.mediaObject = textObj;
////		msg.description = text;
////		//����һ��Req
////		SendMessageToWX.Req req = new SendMessageToWX.Req();
////		//transaction�ֶ�����Ψһ��ʶһ������
////		req.transaction = buildTransaction("textshare");
////		req.message = msg;
////		//���͵�Ŀ�곡���� ����ѡ���͵��Ự WXSceneSession ��������Ȧ WXSceneTimeline�� Ĭ�Ϸ��͵��Ự��
////		req.scene = shareType;
////		wxApi.sendReq(req);
//		
//		String url = "http://www.huanrong9999.com";//�յ�����ĺ��ѵ����Ϣ����ת�������ַȥ  
//		 WXWebpageObject localWXWebpageObject = new WXWebpageObject();  
//		   localWXWebpageObject.webpageUrl = url;
//		   WXMediaMessage localWXMediaMessage = new WXMediaMessage(localWXWebpageObject);
//		   localWXMediaMessage.title = "�Ѻ�";
//		   localWXMediaMessage.description = "�Ѻڡ���ȫ�����Ĳ�ѯ�ع�ƽ̨��Url:www.huanrong9999.com";
//		   Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.logo);
//		   localWXMediaMessage.thumbData = getBitmapBytes(bmp, false);
//		   bmp.recycle();
//
//		// ����һ��Req
//		SendMessageToWX.Req req = new SendMessageToWX.Req();
//		req.transaction = buildTransaction("text/img"); // transaction�ֶ�����Ψһ��ʶһ������
//		req.message = localWXMediaMessage;
//		req.scene = shareType;
//		// ����api�ӿڷ������ݵ�΢��
//		wxApi.sendReq(req);
//	}
	
	/*
	 * ����΢�ŷ�������
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
//			req.transaction = buildTransaction("text/img");//transaction�ֶ�����Ψһ��ʶһ������
//			req.message = localWXMediaMessage;
//			req.scene = shareType;
//			// ����api�ӿڷ������ݵ�΢��
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
//	//�ж�app�Ƿ�װ��΢��
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
