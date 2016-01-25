package com.example.black.lib;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * ͼƬ����
 * 
 * @author Admin
 * 
 */
public class ImageUtils {
	/**
	 * �ȱ���ѹ��bitmap��С
	 * 
	 * @param defaultBitmap
	 * @param width
	 * @return
	 */
	public static Bitmap resizeImageByWidth(Bitmap defaultBitmap,
			int targetWidth) {
		int rawWidth = defaultBitmap.getWidth();
		int rawHeight = defaultBitmap.getHeight();
		float targetHeight = targetWidth * (float) rawHeight / rawWidth;
		float scaleWidth = targetWidth / (float) rawWidth;
		float scaleHeight = targetHeight / rawHeight;
		Matrix localMatrix = new Matrix();
		localMatrix.postScale(scaleHeight, scaleWidth);
		return Bitmap.createBitmap(defaultBitmap, 0, 0, rawWidth, rawHeight,
				localMatrix, true);
	}

	/**
	 * Բ��ͼƬ
	 * 
	 * @author Administrator
	 *         ���ͼƬ�������εģ���ratio����Ϊ2�����ͼƬ���������Σ��Լ���������ͼ�ɣ���ͼƬ�س���Ҫ�������Ρ���ʾԲ�Ǳ߳�1
	 *         /4,����8���Դ����ƣ�
	 */
	public static Bitmap toRoundCorner(Bitmap bitmap, float ratio) {
		System.out.println("ͼƬ�Ƿ���Բ��ģʽ��+++++++++++++");
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		canvas.drawRoundRect(rectF, bitmap.getWidth() / ratio,
				bitmap.getHeight() / ratio, paint);

		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		System.out.println("pixels+++++++" + String.valueOf(ratio));

		return output;
	}
}
