package com.example.black.lib;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

public class ImageLoaderUtil {
	public static DisplayImageOptions init_DisplayImageOptions(int imageRes) {
		DisplayImageOptions options = null;
		try {
			options = new DisplayImageOptions.Builder()
			// �����ڼ���ʾ��ͼƬ
					.showImageOnLoading(imageRes)
					// uriΪ�ջ����ʱ��ͼƬ
					// .showImageForEmptyUri(R.drawable.ic_empty)
					// ���ؽ���ʱ�����ͼƬ
					// .showImageOnFail(R.drawable.ic_error)
					// �Ƿ񻺴��ڴ�
					.cacheInMemory(false)
					// �Ƿ񻺴�sdcard
					.cacheOnDisc(false)
					// �Ƿ���JPEGͼ��EXIF��������ת����ת��
					.considerExifParams(true)
					// �Ƿ�����ΪԲ�ǣ�����Ϊ����
					//.displayer(new RoundedBitmapDisplayer(20))
					.imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
					.build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return options;
	}
}
