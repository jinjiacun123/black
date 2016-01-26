package com.example.black.lib;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import com.example.black.R;
import com.example.black.lib.model.Company;
import com.example.black.lib.model.DarkTerraceActivity;
import com.example.black.lib.model.NoAttestationActivity;
import com.example.black.lib.model.NoStorageActivity;
import com.example.black.lib.model.QualifiedTerraceActivity;

import junit.framework.Assert;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.os.Parcelable;
import android.util.Log;

public class Util {
	private static final String TAG = "SDK_Sample.Util";

	// 登陆状态
	public static boolean getLoginType(Context context) {
		SharedPreferences shared = context.getSharedPreferences(
											"Login_UserInfo", 
											Context.MODE_PRIVATE);
		
		if (shared != null) {
			return shared.getBoolean("login_type", false);
		}
		return false;
	}

	// 登陆id
	public static long getShare_User_id(Context context) {
		SharedPreferences shared = context.getSharedPreferences(
											"Login_UserInfo", 
											Context.MODE_PRIVATE);
		if (shared != null) {
			return shared.getLong("user_id", 0);
		}
		return 0;
	}
	
	//登陆是否跳转注册页状态
	public static boolean getRegisterType(Context context){
		return context.getSharedPreferences("Login_UserInfo", 
				                             Context.MODE_PRIVATE)
				      .getBoolean("type", false);
	}
	
	//修改状态
	public static boolean UpdateRegisterType(Context context,boolean type){
		return context.getSharedPreferences("Login_UserInfo", Context.MODE_PRIVATE)
				      .edit()
				      .putBoolean("type", type)
				      .commit();
	}
	
	//跳转平台筛选
	public static void JumpToResultPage(Context context,List<Company> list, 
			                            int position) {
		Company company = list.get(position);
		if (company != null) {
			List<Company> list2=new ArrayList<Company>();
			list2.add(company);
			Intent intent = new Intent();
			intent.putExtra("search_key", company.getCompany_name());
			intent.putExtra("position", 0);
			intent.putExtra("mqtt_push_type", 2);
			intent.putParcelableArrayListExtra("companys",
					(ArrayList<? extends Parcelable>) list2);
			switch (company.getAuth_level()) {
			// 黑平台
			case "006001":
				intent.setClass(context,
						DarkTerraceActivity.class);
				break;
			// 未验证
			case "006002":
				intent.setClass(context,
						NoAttestationActivity.class);
				break;
			// 合规
			case "006003":
				intent.setClass(context,
						QualifiedTerraceActivity.class);
				break;
	 		default:
				intent.setClass(context,
						NoStorageActivity.class);
				break;
			}
			context.startActivity(intent);
		}
	}

	public static int getId(String name) {
		try {
			Field field = R.id.class.getField(name);
			return Integer.parseInt(field.get(null).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static byte[] bmpToByteArray(final Bitmap bmp,
			final boolean needRecycle) {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		bmp.compress(CompressFormat.PNG, 100, output);
		if (needRecycle) {
			bmp.recycle();
		}

		byte[] result = output.toByteArray();
		try {
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public static byte[] getHtmlByteArray(final String url) {
		URL htmlUrl = null;
		InputStream inStream = null;
		try {
			htmlUrl = new URL(url);
			URLConnection connection         = htmlUrl.openConnection();
			HttpURLConnection httpConnection = (HttpURLConnection) connection;
			int responseCode                 = httpConnection.getResponseCode();
			
			if (responseCode == HttpURLConnection.HTTP_OK) {
				inStream = httpConnection.getInputStream();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] data = inputStreamToByte(inStream);

		return data;
	}

	public static byte[] inputStreamToByte(InputStream is) {
		try {
			ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
			int ch;
			while ((ch = is.read()) != -1) {
				bytestream.write(ch);
			}
			byte imgdata[] = bytestream.toByteArray();
			bytestream.close();
			return imgdata;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static byte[] readFromFile(String fileName, 
			                          int offset, int len) {
		if (fileName == null) {
			return null;
		}

		File file = new File(fileName);
		if (!file.exists()) {
			Log.i(TAG, "readFromFile: file not found");
			return null;
		}

		if (len == -1) {
			len = (int) file.length();
		}

		Log.d(TAG, "readFromFile : offset = " + offset + " len = " + len
				+ " offset + len = " + (offset + len));

		if (offset < 0) {
			Log.e(TAG, "readFromFile invalid offset:" + offset);
			return null;
		}
		if (len <= 0) {
			Log.e(TAG, "readFromFile invalid len:" + len);
			return null;
		}
		if (offset + len > (int) file.length()) {
			Log.e(TAG, "readFromFile invalid file len:" + file.length());
			return null;
		}

		byte[] b = null;
		try {
			RandomAccessFile in = new RandomAccessFile(fileName, "r");
			b = new byte[len]; // 创建合适文件大小的数组
			in.seek(offset);
			in.readFully(b);
			in.close();

		} catch (Exception e) {
			Log.e(TAG, "readFromFile : errMsg = " + e.getMessage());
			e.printStackTrace();
		}
		return b;
	}

	private static final int MAX_DECODE_PICTURE_SIZE = 1920 * 1440;

	public static Bitmap extractThumbNail(final String path, 
			                              final int height,
			                              final int width, 
			                              final boolean crop) {
		Assert.assertTrue(path != null && !path.equals("") && height > 0
				&& width > 0);

		BitmapFactory.Options options = new BitmapFactory.Options();

		try {
			options.inJustDecodeBounds = true;
			Bitmap tmp = BitmapFactory.decodeFile(path, options);
			if (tmp != null) {
				tmp.recycle();
				tmp = null;
			}

			Log.d(TAG, "extractThumbNail: round=" + width + "x" + height
					+ ", crop=" + crop);
			final double beY = options.outHeight * 1.0 / height;
			final double beX = options.outWidth * 1.0 / width;
			Log.d(TAG, "extractThumbNail: extract beX = " + beX + ", beY = "
					+ beY);
			options.inSampleSize = (int) (crop ? (beY > beX ? beX : beY)
					: (beY < beX ? beX : beY));
			if (options.inSampleSize <= 1) {
				options.inSampleSize = 1;
			}

			// NOTE: out of memory error
			while (options.outHeight * options.outWidth / options.inSampleSize > MAX_DECODE_PICTURE_SIZE) {
				options.inSampleSize++;
			}

			int newHeight = height;
			int newWidth = width;
			if (crop) {
				if (beY > beX) {
					newHeight = (int) (newWidth * 1.0 * options.outHeight / options.outWidth);
				} else {
					newWidth = (int) (newHeight * 1.0 * options.outWidth / options.outHeight);
				}
			} else {
				if (beY < beX) {
					newHeight = (int) (newWidth * 1.0 * options.outHeight / options.outWidth);
				} else {
					newWidth = (int) (newHeight * 1.0 * options.outWidth / options.outHeight);
				}
			}

			options.inJustDecodeBounds = false;

			Log.i(TAG, "bitmap required size=" + newWidth + "x" + newHeight
					+ ", orig=" + options.outWidth + "x" + options.outHeight
					+ ", sample=" + options.inSampleSize);
			Bitmap bm = BitmapFactory.decodeFile(path, options);
			if (bm == null) {
				Log.e(TAG, "bitmap decode failed");
				return null;
			}

			Log.i(TAG,
					"bitmap decoded size=" + bm.getWidth() + "x"
							+ bm.getHeight());
			final Bitmap scale = Bitmap.createScaledBitmap(bm, newWidth,
					newHeight, true);
			if (scale != null && scale.isRecycled()) {
				bm.recycle();
				bm = null;
				bm = scale;
			}			
			return bm;

		} catch (final OutOfMemoryError e) {
			Log.e(TAG, "decode bitmap failed: " + e.getMessage());
			options = null;
		}

		return null;
	}
}
