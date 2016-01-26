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
 * 修改头像
 * @author Admin
 *
 */
public class ImageCompress {
	public static Bitmap getSizeOfBitmap(Bitmap bitmap,Context context){
//		ByteArrayOutputStream baos = new ByteArrayOutputStream();
//		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);//这里100的话表示不压缩质量
//		Log.i("Test", "image--->"+baos.toByteArray().length);
//		return baos.toByteArray().length/1024;//读出图片的kb大小
		
		  ByteArrayOutputStream baos = new ByteArrayOutputStream();  
		  bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中  
	        int options = 100;  
	        while ( baos.toByteArray().length / 1024>100) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩         
	            baos.reset();//重置baos即清空baos  
	            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中  
	            options -= 10;//每次都减少10  
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
	        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中  
	        Log.i("Test", "image--->"+baos.toByteArray().length/1024);
	        Bitmap bitmap2 = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片  
	        return bitmap2;  
	}
}
