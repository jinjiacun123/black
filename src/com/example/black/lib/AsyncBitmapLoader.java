package com.example.black.lib;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

/**
 * ͼƬ��ȡ�뻺��
 * @author Admin
 * 
 */
public class AsyncBitmapLoader {

	private HashMap<String , SoftReference<Bitmap>>  imagecahe = null;
	private Context context;
	private BitmapFactory.Options bitmapOptions;
	public AsyncBitmapLoader(Context context) {
		imagecahe = new HashMap<String , SoftReference<Bitmap>>();
		this.context = context;
		bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inSampleSize = 1;
	}

	
	/*
	 * ����ͼƬ�������Ҫ��������ȥ�ڴ����濴�Ƿ���ͼƬ����û����ȥ���ز��ҡ�
	 * �����û�о͸���Url·��ȥ��ͼƬ���õ�֮�����type��ֵȥ�ж��Ƿ�Ҫ�����ڱ��� ;
	 * @params imageview Ҫ������ͼƬ�Ŀؼ�
	 * @params imageurl  ͼƬ��url·��
	 * @params imageCallBack һ���ӿ�  �õ�ͼƬʱ���ؼ���ֵ
	 * @params type ����ֵ  ��� == 1 ���ǻ��汾��  �����Ͳ�����
	 * ��������Ļ���ֻ�Ǹ��������������  ���뱣֤���е�ͼƬ�����ֶ���Ψһ��
	 * ��������ͼƬ����Ĳ����� ���ܻ�ʧ��  �Ժ���ʱ�価������
	 */
	public Bitmap loaderBitmap(final ImageView imageview ,
			                  final String imageurl ,
			                  final ImageCallBack imageCallBack,
			                  final int type){
		
		
		//
		final Handler handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				imagecahe.put(imageurl, new SoftReference<Bitmap>((Bitmap)msg.obj));
				imageCallBack.imageLoader(imageview,(Bitmap)msg.obj);
			}
		};
		 //���ڴ滺���У��򷵻�Bitmap����
		if(imagecahe.containsKey(imageurl) && imagecahe.get(imageurl).get() != null){
			SoftReference<Bitmap> reference = imagecahe.get(imageurl);
			Bitmap bitmap = reference.get();
				return bitmap;
		}else {
				
			//����һ���Ա��ػ���Ĳ���   
			String bitmapname = imageurl.substring(imageurl.lastIndexOf("/")+1);
			File cachefile = new File(context.getApplicationContext().getCacheDir().getAbsolutePath()+"/iamge/");
			
			File[] cachefiles = cachefile.listFiles();
			int i = 0;
			if(cachefiles != null && type == 1){
					for (; i < cachefiles.length; i++) {
						if (bitmapname .equals(cachefiles[i].getName())) {
							{
								break;
							}
						}
					 }
					if(i<cachefiles.length){
						Bitmap bitmap = BitmapFactory.decodeFile(context.getApplicationContext().getCacheDir().getAbsolutePath()+"/iamge/"+bitmapname);
						imagecahe.put(imageurl, new SoftReference<Bitmap>(bitmap));
						return bitmap;
					}else{
//						Log.i("Test", "������1");
						new Thread(){
							@Override
							public void run() {
								InputStream bitmapis = HttpUtils.getInputStream(imageurl);
								Bitmap bitmap = BitmapFactory.decodeStream(bitmapis, null, bitmapOptions);
								imagecahe.put(imageurl, new SoftReference<Bitmap>(bitmap));
								Message msg = new Message();
								msg.obj = bitmap;
								handler.sendMessage(msg);
								
								if(bitmap!=null ){
									File dir = new File(context.getApplicationContext().getCacheDir().getAbsolutePath()+"/iamge/");
									if(!dir.exists()){
										dir.mkdirs();
									}
									File bitmapfile = new File(context.getApplicationContext().getCacheDir().getAbsolutePath()+"/iamge/"+imageurl.substring(imageurl.lastIndexOf("/")+1));
									if(!bitmapfile.exists()){
										try {
											bitmapfile.createNewFile();
										} catch (Exception e) {
											e.printStackTrace();
										}
									}
									FileOutputStream fos ;
									
									try {
										fos = new FileOutputStream(bitmapfile);
										bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
										fos.close();
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
								
								if (bitmap != null && !bitmap.isRecycled())
									bitmap= null;
							};
						}.start();
					}
				}
			else{
				new Thread(){
					@Override
					public void run() {
						InputStream bitmapis = HttpUtils.getInputStream(imageurl);
						Log.i("Test", "imageurl-->"+imageurl);
						Bitmap bitmap = BitmapFactory.decodeStream(bitmapis, null, bitmapOptions);
						imagecahe.put(imageurl, new SoftReference<Bitmap>(bitmap));
						Message msg = new Message();
						msg.obj = bitmap;
						handler.sendMessage(msg);
						if(bitmap!=null ){
							File dir = new File(context.getApplicationContext().getCacheDir().getAbsolutePath()+"/iamge/");
							if(!dir.exists()){
								dir.mkdirs();
							}
							File bitmapfile = new File(context.getApplicationContext().getCacheDir().getAbsolutePath()+"/iamge/"+imageurl.substring(imageurl.lastIndexOf("/")+1));
							if(!bitmapfile.exists()){
								try {
									bitmapfile.createNewFile();
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
							FileOutputStream fos ;
							
							try {
								fos = new FileOutputStream(bitmapfile);
								bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
								fos.close();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						
						if (bitmap != null && !bitmap.isRecycled())
	                        bitmap = null;
					};
				}.start();
			}
		}
		 return null;    
	}
}
