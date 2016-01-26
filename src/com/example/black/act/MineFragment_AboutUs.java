package com.example.black.act;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.example.black.R;
import com.example.black.lib.AppManager;
import com.example.black.lib.HttpPostThread;
import com.example.black.lib.HttpUrl;
import com.example.black.lib.ImageLoaderUtil;
import com.example.black.lib.JsonUtil;
import com.example.black.lib.NetworkUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 关于我们
 * 
 * @author Administrator
 * 
 */
public class MineFragment_AboutUs extends Activity implements OnClickListener {
	private AppManager manager;

	Handler handler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				String result = (String) msg.obj;
				String update_url = JsonUtil.getUs(result);
				
				Log.i("john", "update_url is --------->" +update_url );
				
				if (update_url != null && !"".equals(update_url)) {
					boolean networkConnected = NetworkUtil
							.isNetworkConnected(MineFragment_AboutUs.this);
					if (networkConnected) {
						DisplayImageOptions options = ImageLoaderUtil
								.init_DisplayImageOptions(0);
						ImageLoader.getInstance().displayImage(update_url,
								iv_right, options, null);
					} else {
						Toast.makeText(MineFragment_AboutUs.this, "网络连接失败", 0).show();
					}
				} else {
					Toast.makeText(MineFragment_AboutUs.this, "服务器连接失败", 0).show();
				}
				
				//String pic_Validate = JsonUtil.getUs(result);
				//byte[] decode2 = Base64.decode(pic_Validate, Base64.DEFAULT);
				//String dec = new String(decode2);
				//if (dec != null && !"".equals(dec)) {
					//dec = dec.replaceAll("&amp;", "");
					//dec = dec.replaceAll("quot;", "\"");
					//dec = dec.replaceAll("lt;", "<");
					//dec = dec.replaceAll("gt;", ">");
					//dec = dec.replaceAll("&", "");
					// wv_aboutus.getSettings().setDefaultTextEncodingName("utf-8");
					// wv_aboutus.getSettings().setJavaScriptEnabled(true);
					// wv_aboutus.loadData(dec, "text/html;charset=utf-8",
					// null);
					//wv_aboutus.loadDataWithBaseURL(null, dec, "text/html",
							//"utf-8", null);
				//} else {
					//Toast.makeText(getApplicationContext(), "暂无数据", 0).show();
				//}
				break;

			default:
				break;
			}
		};
	};

	private WebView wv_aboutus;

	private LinearLayout ll_content;

	private ImageView iv_right;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mine_fragment_aboutus);
		initView();
//		getData();
		manager = AppManager.getInstance();
		manager.addActivity(MineFragment_AboutUs.this);
	}

	private void getData() {
		// 关于我们
		// 判断网络是否正常
		boolean networkConnected = NetworkUtil
				.isNetworkConnected(MineFragment_AboutUs.this);
		if (networkConnected) {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			JSONObject object = new JSONObject();
			try {
				object.put("id", 10);
			} catch (Exception e) {
				e.printStackTrace();
			}
			params.add(new BasicNameValuePair("method", HttpUrl.News_method));
			params.add(new BasicNameValuePair("content", object.toString()));
			new HttpPostThread(params, handler, 0).start();
		} else {
			Toast.makeText(MineFragment_AboutUs.this, "网络连接失败", 0).show();
		}
	}

	private void initView() {
		// 返回
		RelativeLayout iv_ActionBar = (RelativeLayout) findViewById(R.id.iv_ActionBar);
		iv_ActionBar.setVisibility(View.VISIBLE);
		iv_ActionBar.setOnClickListener(this);
		TextView tv_ClassName = (TextView) findViewById(R.id.tv_ClassName);
		tv_ClassName.setText("关于口碑");
		// 关于
		//wv_aboutus = (WebView) findViewById(R.id.wv_aboutus);
		ll_content = (LinearLayout) findViewById(R.id.ll_content);
		iv_right = (ImageView) findViewById(R.id.iv_right);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_ActionBar:
			// 返回
			manager.killActivity(MineFragment_AboutUs.this);
			break;

		default:
			break;
		}

	}
}
