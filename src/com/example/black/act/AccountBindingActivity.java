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
 * �ʺŰ�
 * 
 * @author Admin
 * 
 */
public class AccountBindingActivity  extends Activity {
	private ActionBar actionBar;// activity��actionbar
	private ImageView iv_comment_reply_action_icon;// ���˰�ť
	private TextView tv_content_centent;// ���˰�ť
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

	// �˺Ű�(��½)
	Handler handler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				String result = (String) msg.obj;
				UserInfo userInfo = JsonUtil.getLoginInfo(result);
				//Log.i("Test-", "΢�Ű�--------->" + userInfo);
				if (userInfo != null) {
					switch (userInfo.getIs_sucess()) {
					case 0:
						Toast.makeText(getApplicationContext(), "��¼�ɹ�", 0)
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
						Toast.makeText(getApplicationContext(), "��¼ʧ��", 0)
								.show();
						break;
					case -2:
						Toast.makeText(getApplicationContext(), "�������", 0)
								.show();
						break;
					case -3:

						break;
					case -4:
						Toast.makeText(getApplicationContext(), "΢��openid�Ѵ���",
								0).show();
						break;
					default:
						break;
					}
				} else {
					Toast.makeText(getApplicationContext(), "����������ʧ��", 0)
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
		// ����actionbar
		actionBar = this.getActionBar();
		new HomePageController().setActionbar(LayoutInflater.from(this)
				.inflate(R.layout.comment_reply_actionbar, null), actionBar);
		initActionBar();
		initView();
		GetIntent();
	}

	//�ж��ĸ�������
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
				platform="΢��";				
				break;
			case 5:
				platform="QQ";	
				break;
			case 6:
				platform="΢��";	
				break;
			default:
				break;
			}	
			accountbinding_login_text.setText("����ͨ��"+platform+"��¼�Ѻ�");

			if (head_photo != null && !"".equals(head_photo)) {
				//Log.i("Test", "-�õ�������--->" + head_photo);
				ImageLoader.getInstance().displayImage(head_photo,
						accountbinding_usericon,
						ImageLoaderUtil.init_DisplayImageOptions(0));
			} else {
				//Log.i("Test", "-�õ�������--->null");
			}
		}
	}

	private void initView() {
		// ������
		bind_progress = (ProgressBar) findViewById(R.id.bind_progress);
		// ��Ȩƽ̨
		accountbinding_login_text = (TextView) findViewById(R.id.accountbinding_login_text);
		// ͷ��
		accountbinding_usericon = (ImageView) findViewById(R.id.accountbinding_usericon);
		// �ֻ�����
		accountbinding_ed_phone = (EditText) findViewById(R.id.accountbinding_ed_phone);
		// ��½����
		accountbinding_ed_pwd = (EditText) findViewById(R.id.accountbinding_ed_pwd);
		// �û�Э��
		TextView accountbinding_watch_clause = (TextView) findViewById(R.id.accountbinding_watch_clause);
		// �󶨰�ť
		accuntbinding_binding = (ImageView) findViewById(R.id.accuntbinding_binding);
		// ����
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
			//�Ƿ��ƹ��û�(Ĭ��0��1-Ϊ�ƹ��û�)
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
		//Log.i("Test", "�󶨲���--->" + pairs.toString());
	}

	private void initActionBar() {
		iv_comment_reply_action_icon = (ImageView) this
				.findViewById(R.id.iv_comment_reply_action_icon);
		tv_content_centent = (TextView) this
				.findViewById(R.id.tv_content_centent);
		tv_content_centent.setText("�ʺŰ�");
		iv_comment_reply_action_icon
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						AccountBindingActivity.this.finish();
					}
				});
	}
}
