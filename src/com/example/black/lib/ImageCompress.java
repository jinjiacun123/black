package com.example.black.lib;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

/**
 * �޸�ͷ��
 * @author Admin
 *
 */
public class ImageCompress {
	public static Bitmap getSizeOfBitmap(Bitmap bitmap,Context context){
//		ByteArrayOutputStream baos = new ByteArrayOutputStream();
//		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);//����100�Ļ���ʾ��ѹ������
//		Log.i("Test", "image--->"+baos.toByteArray().length);
//		return baos.toByteArray().length/1024;//����ͼƬ��kb��С
		
		  ByteArrayOutputStream baos = new ByteArrayOutputStream();  
		  bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);//����ѹ������������100��ʾ��ѹ������ѹ��������ݴ�ŵ�baos��  
	        int options = 100;  
	        while ( baos.toByteArray().length / 1024>100) {  //ѭ���ж����ѹ����ͼƬ�Ƿ����100kb,���ڼ���ѹ��         
	            baos.reset();//����baos�����baos  
	            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);//����ѹ��options%����ѹ��������ݴ�ŵ�baos��  
	            options -= 10;//ÿ�ζ�����10  
	        }  
	        File f = new File(context.getApplicationContext().getCacheDir().getAbsolutePath()+"/", "avg.jpg");
			  if (f.exists()) {
			   f.delete();
			  }
			  try {
			   FileOutputStream out = new FileOutputStream(f);
			   bitmap.compress(Bitmap.CompressFormat.JPEG, options, out);
			   out.flush();
			   out.close();
			  } catch (FileNotFoundException e) {
			   // TODO Auto-generated catch block
			   e.printStackTrace();
			  } catch (Exception e) {
			   // TODO Auto-generated catch block
			   e.printStackTrace();
			  }
	        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//��ѹ���������baos��ŵ�ByteArrayInputStream��  
	        Log.i("Test", "image--->"+baos.toByteArray().length/1024);
	        Bitmap bitmap2 = BitmapFactory.decodeStream(isBm, null, null);//��ByteArrayInputStream��������ͼƬ  
	        return bitmap2;  
	}
}
