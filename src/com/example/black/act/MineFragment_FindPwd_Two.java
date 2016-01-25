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
import com.example.black.lib.JsonUtil;
import com.example.black.lib.KeyBoardUtil;
import com.example.black.lib.NetworkUtil;
import com.example.black.view.MineFragment_Login;
import com.example.black.view.custom.DialogUtil;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 找回密码第二步
 * 
 * @author Admin
 * 
 */
public class MineFragment_FindPwd_Two  extends Activity implements
OnClickListener {
	private AppManager manager;
	private EditText fp_ed_pwd;
	private EditText fp_re_ed_pwd;
	private boolean pwd_type = false;
	private boolean re_pwd_type = false;
	// 修改密码
	Handler handler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				String result = (String) msg.obj;
				if (result != null && !"".equals(result)) {
					int is_sucess = JsonUtil.getUpdate_Info(result);
					if (is_sucess == 0) {
						Toast.makeText(getApplicationContext(), "修改成功", 0)
								.show();
						Intent intent = new Intent(
								MineFragment_FindPwd_Two.this,
								MineFragment_Login.class);
						manager.killActivity(MineFragment_FindPwd_Two.this);
						manager.killActivity(MineFragment_FindPwd.class);
					} else if (is_sucess == -1) {
						Toast.makeText(getApplicationContext(), "修改失败", 0)
								.show();
					} else if (is_sucess == -3) {
						Toast.makeText(getApplicationContext(), "短信验证码不正确", 0)
								.show();
					} else {
						Toast.makeText(getApplicationContext(), "手机号码不存在", 0)
								.show();
					}
				} else {
					Toast.makeText(getApplicationContext(), "服务器连接失败", 0)
							.show();
				}
				iv_ok.setClickable(true);
				pb_find.setVisibility(View.INVISIBLE);
				break;
			default:
				break;
			}
		};
	};
	private String phone;
	private String pic_Validate;
	private ProgressBar pb_find;
	private ImageView iv_ok;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mine_fragment_find_pwd_three);
		GetIntent();
		initView();
		manager = AppManager.getInstance();
		manager.addActivity(MineFragment_FindPwd_Two.this);
	}

	private void GetIntent() {
		Intent intent = getIntent();
		if (intent != null) {
			phone = intent.getStringExtra("phone");
			pic_Validate = intent.getStringExtra("pic_Validate");
		}
	}

	private void initView() {
		// 进度条
		pb_find = (ProgressBar) findViewById(R.id.pb_find);
		// 返回
		RelativeLayout iv_ActionBar = (RelativeLayout) findViewById(R.id.iv_ActionBar);
		iv_ActionBar.setVisibility(View.VISIBLE);
		iv_ActionBar.setOnClickListener(this);
		TextView tv_ClassName = (TextView) findViewById(R.id.tv_ClassName);
		tv_ClassName.setText("找回密码");
		// 新密码
		fp_ed_pwd = (EditText) findViewById(R.id.fp_ed_pwd);
		// 再次输入密码
		fp_re_ed_pwd = (EditText) findViewById(R.id.fp_re_ed_pwd);
		// 确定
		iv_ok = (ImageView) findViewById(R.id.iv_ok);
		iv_ok.setOnClickListener(this);
		// 监听事件
		setOnClickListener();
	}

	private void setOnClickListener() {
		// 新密码
		fp_ed_pwd.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				if (s != null && !"".equals(s)) {
					if (s.length() > 16 || s.length() < 6) {
						fp_ed_pwd.setError("长度在6到16位之间");
						pwd_type = false;
					} else {
						pwd_type = true;
					}
				} else {
					fp_ed_pwd.setError("密码不为空");
				}
			}
		});
		// 再次输入密码
		fp_re_ed_pwd.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				if (s != null && !"".equals(s)) {
					if (s.length() > 16 || s.length() < 6) {
						fp_re_ed_pwd.setError("长度在6到16位之间");
						re_pwd_type = false;
					} else {
						re_pwd_type = true;
					}
				} else {
					fp_re_ed_pwd.setError("密码不为空");
				}
			}
		});
	}

	// 提示框
	private void ShowDialog(final String phone, final String smscode,
			final String pwd) {
		DialogUtil.ShowDialog(MineFragment_FindPwd_Two.this, "提示", "是否修改?",
				"取消", "确定", null, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// 判断网络是否正常
						boolean networkConnected = NetworkUtil
								.isNetworkConnected(MineFragment_FindPwd_Two.this);
						if (networkConnected) {
							iv_ok.setClickable(false);
							pb_find.setVisibility(View.VISIBLE);
							List<NameValuePair> params = new ArrayList<NameValuePair>();
							JSONObject object = new JSONObject();
							try {
								object.put("mobile", phone);
								object.put("smscode", smscode);
								object.put("new_pswd", pwd);
							} catch (Exception e) {
								e.printStackTrace();
							}
							params.add(new BasicNameValuePair("method",
									HttpUrl.findpwd_method));
							params.add(new BasicNameValuePair("content", object
									.toString()));
							new HttpPostThread(params, handler, 0).start();
						} else {
							iv_ok.setClickable(true);
							pb_find.setVisibility(View.INVISIBLE);
							Toast.makeText(MineFragment_FindPwd_Two.this,
									"网络连接失败", 0).show();
						}
					}
				});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_ActionBar:
			// 返回
			manager.killActivity(MineFragment_FindPwd_Two.this);
			break;
		case R.id.iv_ok:
			KeyBoardUtil.is_openKeyBoard(getApplicationContext(),
					MineFragment_FindPwd_Two.this);

			// 修改密码
			String ed_pwd = fp_ed_pwd.getText().toString();
			String re_ed_pwd = fp_re_ed_pwd.getText().toString();
			if (pwd_type && re_pwd_type && ed_pwd.equals(re_ed_pwd)) {
				if (ed_pwd.equals(re_ed_pwd)) {
					ShowDialog(phone, pic_Validate, ed_pwd);
				} else {
					Toast.makeText(getApplicationContext(), "输入密码不一致", 0).show();
				}
			} else {
				if (ed_pwd.length() == 0 && re_ed_pwd.length() == 0) {
					Toast.makeText(getApplicationContext(), "输入密码不为空", 0).show();
				} else if(!pwd_type){
					Toast.makeText(getApplicationContext(), "长度在6到16位之间", 0).show();
				}else if(!re_pwd_type){
					Toast.makeText(getApplicationContext(), "长度在6到16位之间", 0).show();
				}else if (!ed_pwd.equals(re_ed_pwd)) {
					Toast.makeText(getApplicationContext(), "输入密码不一致", 0).show();
				}
			}
			break;
		default:
			break;
		}

	}
}
