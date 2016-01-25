package com.example.black.act;

import java.util.ArrayList;
import java.util.List;

import com.example.black.R;
import com.example.black.lib.NetworkUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

/**
 * 查看图片
 * 
 * @author Admin
 * 
 */
public class SearchActivity_Images  extends Activity {
	private Boolean type = false;
	private List<Bitmap> bitmaps;
	private ViewPager vp_content;
	private ProgressBar pb_images;
	private TextView img_count;
	
	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				bitmaps = (List<Bitmap>) msg.obj;
				if (bitmaps != null && bitmaps.size() > 0) {
					vp_content.setAdapter(new MyAdapter(bitmaps));
					img_count.setText("1/" + bitmaps.size());
				} else {
					Toast.makeText(getApplicationContext(), "获取图片失败", 0).show();
				}
				pb_images.setVisibility(View.INVISIBLE);
				break;
			case 1:
				pb_images.setVisibility(View.INVISIBLE);
				Toast.makeText(getApplicationContext(), "获取图片失败", 0).show();
				break;
			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_images);
		initView();
		GetData();
	}

	@Override
	protected void onDestroy() {
		if (bitmaps != null && bitmaps.size() > 0) {
			for (int i = 0; i < bitmaps.size(); i++) {
				Bitmap bitmap = bitmaps.get(i);
				if (bitmap != null && !bitmap.isRecycled()) {
					bitmap.recycle();
					bitmap = null;
				}
			}
			bitmaps.clear();
			bitmaps = null;
			System.gc();
		}
		super.onDestroy();
	}

	private void GetData() {
		// 判断网络是否正常
		boolean networkConnected = NetworkUtil
				.isNetworkConnected(SearchActivity_Images.this);
		if (networkConnected) {
			new MyThread().start();
		} else {
			pb_images.setVisibility(View.INVISIBLE);
			Toast.makeText(getApplicationContext(), "网络连接失败", 0).show();
		}

	}

	private List<String> GetIntent() {
		Intent intent = getIntent();
		if (intent != null) {
			ArrayList<String> search_images = intent
					.getStringArrayListExtra("search_images");
			if (search_images != null && search_images.size() > 0) {
				return search_images;
			}
		}
		return null;
	}

	private void initView() {
		// 进度条
		pb_images = (ProgressBar) findViewById(R.id.pb_images);
		// 滑动的页面
		vp_content = (ViewPager) findViewById(R.id.vp_content);
		//vp_content.setOffscreenPageLimit(1);
		RelativeLayout rl_content = (RelativeLayout) findViewById(R.id.rl_content);
		img_count = (TextView) findViewById(R.id.img_count);
		
		rl_content.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		vp_content.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				img_count.setText((arg0 + 1) + "/" + bitmaps.size());
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	class MyAdapter extends PagerAdapter {
		private List<Bitmap> bitmaps;

		public MyAdapter(List<Bitmap> bitmaps) {
			this.bitmaps = bitmaps;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return bitmaps.size();
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0.equals(arg1);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			View view = LinearLayout.inflate(SearchActivity_Images.this,
					R.layout.activity_search_item, null);
			RelativeLayout rl_content = (RelativeLayout) view
					.findViewById(R.id.rl_content);
			rl_content.setGravity(Gravity.CENTER);
			// assert view != null;
			ImageView imageView = new ImageView(SearchActivity_Images.this);
			Bitmap bitmap = bitmaps.get(position);
			Display defaultDisplay = getWindowManager().getDefaultDisplay();
			if (bitmap != null) {
				if (bitmap.getWidth() >= defaultDisplay.getWidth() - 10
						|| bitmap.getHeight() >= defaultDisplay.getHeight() - 70) {
					imageView.setLayoutParams(new LayoutParams(defaultDisplay
							.getWidth() - 10, defaultDisplay.getHeight() - 70));
					// imageView.setScaleType(ScaleType.FIT_XY);
				} else {
					imageView.setLayoutParams(new LayoutParams(
							android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
							android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
					// imageView.setScaleType(ScaleType.FIT_XY);
				}
				imageView.setImageBitmap(bitmap);
				rl_content.addView(imageView);
				
				view.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						finish();
					}
				});
			}
			container.addView(rl_content, 0);
			return rl_content;
		}
	}

	class MyThread extends Thread {
		@Override
		public void run() {
			List<Bitmap> bitmaps = new ArrayList<Bitmap>();
			List<String> getIntent = GetIntent();
			if (getIntent != null && getIntent.size() > 0) {
				for (int i = 0; i < getIntent.size(); i++) {
					String string = getIntent.get(i);
					if (string != null && !"".equals(string)) {
						Bitmap loadImageSync = ImageLoader.getInstance()
								.loadImageSync(getIntent.get(i));
						if (loadImageSync != null) {
							bitmaps.add(loadImageSync);
						}
					}
				}
				Message message = new Message();
				message.what = 0;
				message.obj = bitmaps;
				handler.sendMessage(message);
			} else {
				handler.sendEmptyMessage(1);
			}
			super.run();
		}
	}
}
