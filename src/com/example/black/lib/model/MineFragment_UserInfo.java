package com.example.black.lib.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/*lib*/
import com.example.black.lib.ImageUtils;
import com.example.black.lib.JsonUtil;

/**
 * 用户信息展示
 * 
 * @author Admin
 * 
 */
public class MineFragment_UserInfo  extends FragmentActivity implements
OnClickListener {
	private TextView tv_name_value;
	private TextView tv_phone_value;
	private TextView tv_sex_value;
	private TextView tv_birthday_value;
	private TextView tv_work_value;
	private TextView tv_address_value;
	
	
	private String         ui_Id;
	private String         ui_Sex;
	private final String[] tabtag         = { "tab_1", "tab_2", "tab_3" };
	private String[]       tabname        = { "我的评论", "我的曝光", "我的关注" };
	private final String[] tabname2       = { "TA的评论", "TA的曝光", "TA的关注" };
	private final Class[]  fragment_class = { Mine_CommentFragment.class,
			                                 Mine_ExosureFragment.class, 
			                                 Mine_AttentionFragment.class };
	private ProgressBar     pb_query;
	private ImageView       user_image;
	private ImageView       iv_update;
	private FrameLayout     fl_background;
	private long            user_id;
	private FragmentTabHost tabhost;
	public static MineFragment_UserInfo userinfo_page;
	//推送
	private String mDeviceID;
	private String token;
	
	// 用户头像
	Handler image_handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				// 联网拿的头像
				Bitmap bitmap = (Bitmap) msg.obj;
				Log.i("jay-test--->", bitmap.toString());
				if (bitmap != null) {
					user_image.setImageBitmap(ImageUtils.resizeImageByWidth(
							bitmap, getWindowManager().getDefaultDisplay()
									.getWidth() / 4));
				}
				if (!bitmap.isRecycled()) {
					bitmap.recycle();
					bitmap = null;
				}
				System.gc();
				break;
			case 1:
				// 获取图片失败默认头像
				setDefaultBitmap(user_image);
				break;
			default:
				break;
			}
		};
	};
	// 用户信息
	Handler handler = new Handler() {

		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				String result = (String) msg.obj;
				// Log.i("jay-----test--->", result);
				if (result != null && !"".equals(result)) {
					Query_UserInfo query_UserInfo = JsonUtil.getQuery_UserInfo(result);
					if (query_UserInfo != null) {
						// Log.i("user-info--->", query_UserInfo.toString());
						// 用户id
						ui_Id = query_UserInfo.getUI_Id();
						String ui_NickName = query_UserInfo.getUI_NickName();
						// 手机号
						String ui_LoginNames = query_UserInfo
								.getUI_LoginNames();
						// 性别
						ui_Sex = query_UserInfo.getUI_Sex();
						// 生日
						String ui_BirthDay = query_UserInfo.getUI_BirthDay();
						// 工作
						String ui_Job = query_UserInfo.getUI_Job();
						// 住址
						String ui_Address = query_UserInfo.getUI_Address();
						// 头像图片
						String ui_Avatar = query_UserInfo.getUI_Avatar();

						if (ui_NickName != null && !"".equals(ui_NickName)) {
							
							if (user_id==Util.getShare_User_id(MineFragment_UserInfo.this)) {
								getSharedPreferences("Login_UserInfo",
										Context.MODE_PRIVATE).edit()
										.putString("nickname", ui_NickName)
										.commit();
							}
							tv_name_value.setText(ui_NickName);
						}
						if (ui_LoginNames != null && !"".equals(ui_LoginNames)) {
							tv_phone_value.setText(ui_LoginNames);
						}
						if (ui_Sex != null && !"".equals(ui_Sex)) {
							if (ui_Sex.equals("1")) {
								tv_sex_value.setText("男");
							} else if (ui_Sex.equals("0")) {
								tv_sex_value.setText("女");
							} else {
								tv_sex_value.setText("保密");
							}
						}
						if (ui_BirthDay != null && !"".equals(ui_BirthDay)) {
							tv_birthday_value.setText(ui_BirthDay);
						}
						// Toast.makeText(getApplicationContext(),
						// "job--->"+ui_Job, 0).show();
						if (ui_Job != null && !"".equals(ui_Job)) {
							tv_work_value.setText(ui_Job);
						}
						if (ui_Address != null && !"".equals(ui_Address)) {
							tv_address_value.setText(ui_Address);
						}
						if (ui_Avatar != null && !"".equals(ui_Avatar)) {
							// Log.i("user_icon---->", ui_Avatar);
							// 加载用户头像
							new MyThread(ui_Avatar).start();
						}
					}
				} else {
					Toast.makeText(getApplicationContext(), "服务器连接失败", 0)
							.show();
				}
				DialogUtil.dismissProgressDialog();
				// pb_query.setVisibility(View.INVISIBLE);
				iv_update.setClickable(true);
				break;
			case 1:
				String result_push = msg.obj.toString();
				final PushInfo pushInfo = JsonUtil.getPushInfo(result_push);
				if(pushInfo != null){
					switch(pushInfo.getIs_sucess()){
					case 0:
						Log.i("push", "注销006==============>" );
						Log.i("push", "success" + pushInfo.getIs_sucess());
						break;
					case -1:
						Log.i("push", "注销007==============>" );
						Log.i("push", "failure" + pushInfo.getIs_sucess());
						break;
					}
				}
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
		setContentView(R.layout.activity_mine_userinfo);
		userinfo_page=this;
		initView();
		initTabHost();
		// 联网拿用户信息
		isNetworkConnected();
		AppManager.getInstance().addActivity(MineFragment_UserInfo.this);
	}

	private void initTabHost() {
		tabhost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		tabhost.setup(MineFragment_UserInfo.this, getSupportFragmentManager(),
				R.id.realtabcontent);
		for (int i = 0; i < tabname.length; i++) {
			tabhost.addTab(
					tabhost.newTabSpec(tabtag[i]).setIndicator(getView(i)),
					fragment_class[i], null);
		}
		tabhost.setCurrentTab(0);
	}

	private View getView(int i) {
		View view = LinearLayout.inflate(MineFragment_UserInfo.this,
				R.layout.activity_mine_userinfo_tab, null);
		TextView tv_text = (TextView) view.findViewById(R.id.tv_text);
		tv_text.setText(tabname[i]);
		return view;
	}

	@Override
	protected void onDestroy() {
		user_image.setImageBitmap(null);
		System.gc();
		super.onDestroy();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Intent intent = getIntent();
		if (intent!=null) {
			//切换到我的曝光
			if(intent.getBooleanExtra("Exosure_Type", false)) tabhost.setCurrentTab(1);
		}
	}

	// 默认头像
	private Bitmap getDrawable() {
		return BitmapFactory.decodeStream((getResources()
				.openRawResource(R.drawable.avatar)));
	}

	private void setDefaultBitmap(ImageView imageView) {
		Bitmap default_bitmap = getDrawable();
		if (default_bitmap != null) {

			imageView.setImageBitmap(ImageUtils.resizeImageByWidth(
					default_bitmap, getWindowManager().getDefaultDisplay()
							.getWidth() / 4));

			if (!default_bitmap.isRecycled()) {
				default_bitmap.recycle();
				default_bitmap = null;
			}
		}
		System.gc();
	}

	private void isNetworkConnected() {
		// 判断网络是否正常
		boolean networkConnected = NetworkUtil
				.isNetworkConnected(MineFragment_UserInfo.this);
		if (networkConnected) {
			DialogUtil.showProgressDialog(MineFragment_UserInfo.this, "加载中...",
					0);
			iv_update.setClickable(false);
			setData();
		} else {
			DialogUtil.dismissProgressDialog();
			// pb_query.setVisibility(View.INVISIBLE);
			iv_update.setClickable(false);
			fl_background.setVisibility(View.VISIBLE);
			Toast.makeText(MineFragment_UserInfo.this, "网络连接失败", 0).show();
		}
	}

	private void setData() {
		iv_update.setClickable(false);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		JSONObject object = new JSONObject();
		try {
			object.put("uid", user_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		params.add(new BasicNameValuePair("method", HttpUrl.userinfo_method));
		params.add(new BasicNameValuePair("content", object.toString()));
		new HttpPostThread(params, handler, 0).start();
	}

	private void initView() {
		// 进度条
		pb_query = (ProgressBar) findViewById(R.id.pb_query);
		// 返回
		RelativeLayout iv_ActionBar = (RelativeLayout) findViewById(R.id.iv_ActionBar);
		iv_ActionBar.setVisibility(View.VISIBLE);
		iv_ActionBar.setOnClickListener(this);
		TextView tv_ClassName = (TextView) findViewById(R.id.tv_ClassName);
		TextView iv_home = (TextView) findViewById(R.id.iv_home);
		iv_home.setText("注销");
		iv_home.setVisibility(View.VISIBLE);
		iv_home.setOnClickListener(this);
		// 背景
		fl_background = (FrameLayout) findViewById(R.id.fl_background);
		// 用户图片
		user_image = (ImageView) findViewById(R.id.user_image);
		setDefaultBitmap(user_image);
		// 昵称
		tv_name_value = (TextView) findViewById(R.id.tv_name_value);
		// 手机
		tv_phone_value = (TextView) findViewById(R.id.tv_phone_value);
		// 性别
		tv_sex_value = (TextView) findViewById(R.id.tv_sex_value);
		// 生日
		tv_birthday_value = (TextView) findViewById(R.id.tv_birthday_value);
		// 职业
		tv_work_value = (TextView) findViewById(R.id.tv_work_value);
		// 所在地
		tv_address_value = (TextView) findViewById(R.id.tv_address_value);
		// 修改信息
		iv_update = (ImageView) findViewById(R.id.iv_update);
		iv_update.setOnClickListener(this);

		Intent intent = getIntent();
		if (intent != null) {
			if (intent.getLongExtra("user_id", -1) == Util
					.getShare_User_id(MineFragment_UserInfo.this)) {
				tv_ClassName.setText("我");
			} else {
				tv_ClassName.setText("TA");
				iv_update.setVisibility(View.INVISIBLE);
				iv_home.setVisibility(View.INVISIBLE);
				tabname = tabname2;
			}
			user_id = intent.getLongExtra("user_id", -1);
		}
	}

	private void NewDialog() {
		// 注销提示窗
		AlertDialog.Builder builder = new AlertDialog.Builder(
				MineFragment_UserInfo.this);
		builder.setTitle("警告");
		builder.setMessage("是否注销?");
		builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub

			}
		});
		builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// 用户修改信息状态
				// getSharedPreferences("Is_Update",
				// Activity.MODE_PRIVATE).edit()
				// .putBoolean("is_update", false).commit();
				
				//在修改登录状态之前上传推送信息
				initPush();
				// 修改登录状态
				getSharedPreferences("Login_UserInfo", Context.MODE_PRIVATE)
						.edit().putLong("user_id", 0)
						.putBoolean("login_type", false).commit();
				
				//关闭推送服务
				PushService.actionStop(MineFragment_UserInfo.this);
				Log.i("push", "注销关闭服务--------------->");
				finish();
			}
		});
		builder.show();
	}

	protected void initPush() {
		if(NetworkUtil.isNetworkConnected(getApplicationContext())){
			new HttpUtil().initOffline(user_id, handler, 1);
		}
		Log.i("push", "注销上传信息完成--------------->");
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == 200) {
			boolean booleanExtra = data.getBooleanExtra("Update_Type", false);
			if (booleanExtra) {
				// pb_query.setVisibility(View.VISIBLE);
				iv_update.setClickable(false);
				isNetworkConnected();
			}
		}
		
		//if (resultCode==100) {
			//Toast.makeText(MineFragment_UserInfo.this, "1--->"+data.getBooleanExtra("Type", false), 0).show();
		//}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_ActionBar:
			// 返回
			finish();
			break;
		case R.id.iv_home:
			// 注销
			NewDialog();
			break;
		case R.id.iv_update:
			// 跳转修改信息
			Intent intent = new Intent(MineFragment_UserInfo.this,
					MineFragment_Update_UserInfo.class);
			intent.putExtra("uid", ui_Id);
			intent.putExtra("name_value", tv_name_value.getText().toString());
			intent.putExtra("phone_value", tv_phone_value.getText().toString());
			intent.putExtra("sex_value", ui_Sex);
			intent.putExtra("birthday_value", tv_birthday_value.getText()
					.toString());
			intent.putExtra("work_value", tv_work_value.getText().toString());
			intent.putExtra("address_value", tv_address_value.getText()
					.toString());
			startActivityForResult(intent, 100);
			break;
		default:
			break;
		}

	}

	class MyThread extends Thread {
		private String image_url;

		public MyThread(String image_url) {
			this.image_url = image_url;
		}

		@Override
		public void run() {
			Bitmap loadImageSync = ImageLoader.getInstance().loadImageSync(
					image_url);
			Message message = new Message();
			if (loadImageSync != null) {
				message.what = 0;
				message.obj = loadImageSync;
				image_handler.sendMessage(message);
			} else {
				image_handler.sendEmptyMessage(1);
			}
			super.run();
		}
	}
}
