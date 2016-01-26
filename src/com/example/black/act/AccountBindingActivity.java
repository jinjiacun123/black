package com.example.black.act;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.example.black.R;
import com.example.black.lib.HttpPostThread;
import com.example.black.lib.HttpUrl;
import com.example.black.lib.ImageLoaderUtil;
import com.example.black.lib.JsonUtil;
import com.example.black.lib.Util;
import com.example.black.lib.model.UserInfo;
import com.example.black.main.MainActivity;
import com.example.black.view.MineFragment_Clause;
import com.example.black.view.MineFragment_Login;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 锟绞号帮拷
 * 
 * @author Admin
 * 
 */
public class AccountBindingActivity  extends Activity {
	private ActionBar actionBar;// activity锟斤拷actionbar
	private ImageView iv_comment_reply_action_icon;// 锟斤拷锟剿帮拷钮
	private TextView tv_content_centent;// 锟斤拷锟剿帮拷钮
	private String openid;
	private String head_photo;
	private String nickname;
	private ImageView accountbinding_usericon;
	private EditText accountbinding_ed_phone;
	private EditText accountbinding_ed_pwd;
	private ImageView accuntbinding_binding;
	private ImageView accuntbinding_skip;
	private ProgressBar bind_progress;
	private int platform_type;

	// 锟剿号帮拷(锟斤拷陆)
	Handler handler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				String result = (String) msg.obj;
				UserInfo userInfo = JsonUtil.getLoginInfo(result);
				//Log.i("Test-", "微锟脚帮拷--------->" + userInfo);
				if (userInfo != null) {
					switch (userInfo.getIs_sucess()) {
					case 0:
						Toast.makeText(getApplicationContext(), "锟斤拷录锟缴癸拷", 0)
								.show();

						getSharedPreferences("Login_UserInfo",
								Context.MODE_PRIVATE)
								.edit()
								.putBoolean("login_type", true)
								.putLong("user_id",
										Long.valueOf(userInfo.getUser_id()))
								.putString("nickname", userInfo.getNickname())
								.putString("sex", userInfo.getSex())
								.putString("head_portrait",
										userInfo.getHead_portrait())
								.putString("cur_date", userInfo.getCur_date())
								.commit();
						
						if (Util.getRegisterType(AccountBindingActivity.this)) {
							Intent intent=new Intent(AccountBindingActivity.this,MainActivity.class);
							startActivity(intent);
							Util.UpdateRegisterType(AccountBindingActivity.this, false);
						}
						
						finish();
						MineFragment_Login.login_page.finish();
						break;
					case -1:
						Toast.makeText(getApplicationContext(), "锟斤拷录失锟斤拷", 0)
								.show();
						break;
					case -2:
						Toast.makeText(getApplicationContext(), "锟斤拷锟斤拷锟斤拷锟�", 0)
								.show();
						break;
					case -3:

						break;
					case -4:
						Toast.makeText(getApplicationContext(), "微锟斤拷openid锟窖达拷锟斤拷",
								0).show();
						break;
					default:
						break;
					}
				} else {
					Toast.makeText(getApplicationContext(), "锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷失锟斤拷", 0)
							.show();
				}
				bind_progress.setVisibility(View.GONE);
				accuntbinding_binding.setClickable(true);
				accuntbinding_skip.setClickable(true);
				break;
			default:
				break;
			}
		};
	};
	private TextView accountbinding_login_text;

	@SuppressLint("NewApi") @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account_binding);
		actionBar = this.getActionBar();
		new HomePageController().setActionbar(LayoutInflater.from(this)
				.inflate(R.layout.comment_reply_actionbar, null), actionBar);
		initActionBar();
		initView();
		GetIntent();
	}

	//锟叫讹拷锟侥革拷锟斤拷锟斤拷锟斤拷
	private void GetIntent() {
		Intent intent = getIntent();
		if (intent != null) {
			platform_type = intent.getIntExtra("platform_type", 0);
			openid = intent.getStringExtra("openid");
			head_photo = intent.getStringExtra("head_photo");
			nickname = intent.getStringExtra("nickname");

			String platform="";
			switch (platform_type) {
			case 4:
				platform="微锟斤拷";				
				break;
			case 5:
				platform="QQ";	
				break;
			case 6:
				platform="微锟斤拷";	
				break;
			default:
				break;
			}	
			accountbinding_login_text.setText("锟斤拷锟斤拷通锟斤拷"+platform+"锟斤拷录锟窖猴拷");

			if (head_photo != null && !"".equals(head_photo)) {
				//Log.i("Test", "-锟矫碉拷锟斤拷锟斤拷锟斤拷--->" + head_photo);
				ImageLoader.getInstance().displayImage(head_photo,
						accountbinding_usericon,
						ImageLoaderUtil.init_DisplayImageOptions(0));
			} else {
				//Log.i("Test", "-锟矫碉拷锟斤拷锟斤拷锟斤拷--->null");
			}
		}
	}

	private void initView() {
		// 锟斤拷锟斤拷锟斤拷
		bind_progress = (ProgressBar) findViewById(R.id.bind_progress);
		// 锟斤拷权平台
		accountbinding_login_text = (TextView) findViewById(R.id.accountbinding_login_text);
		// 头锟斤拷
		accountbinding_usericon = (ImageView) findViewById(R.id.accountbinding_usericon);
		// 锟街伙拷锟斤拷锟斤拷
		accountbinding_ed_phone = (EditText) findViewById(R.id.accountbinding_ed_phone);
		// 锟斤拷陆锟斤拷锟斤拷
		accountbinding_ed_pwd = (EditText) findViewById(R.id.accountbinding_ed_pwd);
		// 锟矫伙拷协锟斤拷
		TextView accountbinding_watch_clause = (TextView) findViewById(R.id.accountbinding_watch_clause);
		// 锟襟定帮拷钮
		accuntbinding_binding = (ImageView) findViewById(R.id.accuntbinding_binding);
		// 锟斤拷锟斤拷
		accuntbinding_skip = (ImageView) findViewById(R.id.accuntbinding_skip);

		accuntbinding_binding.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String phone = accountbinding_ed_phone.getText().toString();
				String pwd = accountbinding_ed_pwd.getText().toString();
				bind_progress.setVisibility(View.VISIBLE);
				accuntbinding_binding.setClickable(false);
				accuntbinding_skip.setClickable(false);
				setData(phone, pwd);
			}
		});

		accuntbinding_skip.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				bind_progress.setVisibility(View.VISIBLE);
				accuntbinding_binding.setClickable(false);
				accuntbinding_skip.setClickable(false);
				setData("", "");
			}
		});

		accountbinding_watch_clause.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(AccountBindingActivity.this,
						MineFragment_Clause.class);
				startActivity(intent);
			}
		});
	}

	private void setData(String mobile, String passwd) {
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		JSONObject object = new JSONObject();
		try {
			object.put("openid", openid);
			object.put("mobile", mobile);
			object.put("passwd", passwd);
			object.put("nickname", nickname);
			object.put("head_photo", head_photo);
			//锟角凤拷锟狡癸拷锟矫伙拷(默锟斤拷0锟斤拷1-为锟狡癸拷锟矫伙拷)
			object.put("sem", 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		switch (platform_type) {
		case 4:
			pairs.add(new BasicNameValuePair("method",
					HttpUrl.entry_weixin_method));
			break;
		case 5:
			pairs.add(new BasicNameValuePair("method",
					HttpUrl.entry_qq_method));
			break;
		case 6:
			pairs.add(new BasicNameValuePair("method",
					HttpUrl.entry_weibo_method));
			break;
		default:
			break;
		}
		pairs.add(new BasicNameValuePair("content", object.toString()));
		new HttpPostThread(pairs, handler, 0).start();
		//Log.i("Test", "锟襟定诧拷锟斤拷--->" + pairs.toString());
	}

	private void initActionBar() {
		iv_comment_reply_action_icon = (ImageView) this
				.findViewById(R.id.iv_comment_reply_action_icon);
		tv_content_centent = (TextView) this
				.findViewById(R.id.tv_content_centent);
		tv_content_centent.setText("锟绞号帮拷");
		iv_comment_reply_action_icon
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						AccountBindingActivity.this.finish();
					}
				});
	}
}
