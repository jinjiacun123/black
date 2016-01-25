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
 * 图片获取与缓存
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
	 * 这是图片缓存的主要方法，先去内存里面看是否有图片对象。没有在去本地查找。
	 * 如果还没有就根据Url路径去那图片，拿到之后根据type的值去判断是否要缓存在本地 ;
	 * @params imageview 要给设置图片的控件
	 * @params imageurl  图片的url路径
	 * @params imageCallBack 一个接口  拿到图片时给控件设值
	 * @params type 参数值  如果 == 1 就是缓存本地  其他就不缓存
	 * 这个方法的缓存只是根据名字来缓存的  必须保证所有的图片的名字都是唯一的
	 * 如果缓存的图片保存的不完整 可能会失败  以后有时间尽量完善
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
		 //在内存缓存中，则返回Bitmap对象
		if(imagecahe.containsKey(imageurl) && imagecahe.get(imageurl).get() != null){
			SoftReference<Bitmap> reference = imagecahe.get(imageurl);
			Bitmap bitmap = reference.get();
				return bitmap;
		}else {
				
			//加上一个对本地缓存的查找   
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
//						Log.i("Test", "网络中1");
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
