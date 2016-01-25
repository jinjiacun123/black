package com.example.black.lib;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

public class ImageLoaderUtil {
	public static DisplayImageOptions init_DisplayImageOptions(int imageRes) {
		DisplayImageOptions options = null;
		try {
			options = new DisplayImageOptions.Builder()
			// 下载期间显示的图片
					.showImageOnLoading(imageRes)
					// uri为空或错误时的图片
					// .showImageForEmptyUri(R.drawable.ic_empty)
					// 加载解码时错误的图片
					// .showImageOnFail(R.drawable.ic_error)
					// 是否缓存内存
					.cacheInMemory(false)
					// 是否缓存sdcard
					.cacheOnDisc(false)
					// 是否考虑JPEG图像EXIF参数（旋转，翻转）
					.considerExifParams(true)
					// 是否设置为圆角，弧度为多少
					//.displayer(new RoundedBitmapDisplayer(20))
					.imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
					.build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return options;
	}
}
