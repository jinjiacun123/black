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
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
 * 修改密码
 * 
 * @author Admin
 * 
 */
public class MineFragment_Update_Pwd extends Activity implements
OnClickListener {
	private AppManager manager;
	private EditText old_pwd_value;
	private EditText new_pwd_value;
	private EditText re_new_pwd_value;
	private ImageView next_ok;
	private boolean old_type = false;// 旧密码
	private boolean new_type = false;// 新密码
	private boolean re_new_type = false;// 再次输入新密码

	Handler handler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				String result = (String) msg.obj;
				int update_Info = JsonUtil.getUpdate_Info(result);
				if (update_Info == 0) {
					Toast.makeText(getApplicationContext(), "修改密码成功", 0).show();
					// 修改登录状态
					getSharedPreferences("Login_UserInfo",
							Context.MODE_PRIVATE).edit()
							.putBoolean("login_type", false).commit();
					Intent intent = new Intent(MineFragment_Update_Pwd.this,
							MineFragment_Login.class);
					startActivity(intent);
					manager.killActivity(MineFragment_Update_Pwd.this);
				} else if (update_Info == -1) {
					Toast.makeText(getApplicationContext(), "修改密码失败", 0).show();
				} else if (update_Info == -2) {
					Toast.makeText(getApplicationContext(), "用户不存在", 0).show();
				} else if (update_Info == -3) {
					Toast.makeText(getApplicationContext(), "原密码不正确", 0).show();
				}
				pb_update.setVisibility(View.INVISIBLE);
				next_ok.setClickable(true);
				break;
			default:
				break;
			}
		};
	};
	private ProgressBar pb_update;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mine_update_pwd);
		initView();
		manager = AppManager.getInstance();
		manager.addActivity(MineFragment_Update_Pwd.this);
	}

	private void initView() {
		// 进度条
		pb_update = (ProgressBar) findViewById(R.id.pb_update);
		// 返回
		RelativeLayout iv_ActionBar = (RelativeLayout) findViewById(R.id.iv_ActionBar);
		iv_ActionBar.setVisibility(View.VISIBLE);
		iv_ActionBar.setOnClickListener(this);
		TextView tv_ClassName = (TextView) findViewById(R.id.tv_ClassName);
		tv_ClassName.setText("账号管理");
		// 旧密码
		old_pwd_value = (EditText) findViewById(R.id.old_pwd_value);
		// 新密码
		new_pwd_value = (EditText) findViewById(R.id.new_pwd_value);
		// 再次输入密码
		re_new_pwd_value = (EditText) findViewById(R.id.re_new_pwd_value);
		// 确定修改
		next_ok = (ImageView) findViewById(R.id.next_ok);
		next_ok.setOnClickListener(this);
		// 监听事件
		setOnClickListener();
	}

	private void setOnClickListener() {
		// 旧密码
		old_pwd_value.addTextChangedListener(new TextWatcher() {

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
						old_pwd_value.setError("长度在6到16位之间");
						old_type = false;
					} else {
						old_type = true;
					}
				} else {
					old_pwd_value.setError("密码不为空");
				}
			}
		});
		// 新密码
		new_pwd_value.addTextChangedListener(new TextWatcher() {

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
						new_pwd_value.setError("长度在6到16位之间");
						new_type = false;
					} else {
						new_type = true;
					}
				} else {
					new_pwd_value.setError("密码不为空");
				}
			}
		});
		// 再次输入密码
		re_new_pwd_value.addTextChangedListener(new TextWatcher() {

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
						re_new_pwd_value.setError("长度在6到16位之间");
						re_new_type = false;
					} else {
						re_new_type = true;
					}
				} else {
					re_new_pwd_value.setError("密码不为空");
				}
			}
		});
	}

	// 用户id
	private String getShare() {
		SharedPreferences sharedPreferences = getSharedPreferences(
				"Login_UserInfo", Context.MODE_PRIVATE);
		if (sharedPreferences != null) {
			return String.valueOf(sharedPreferences.getLong("user_id", -1));
		}
		return "-1";
	}

	private void ShowDialog(final String user_id, final String old_pwd,
			final String new_pwd) {
		DialogUtil.ShowDialog(MineFragment_Update_Pwd.this, "提示", "是否修改?",
				"取消", "确定", null, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// 判断网络是否正常
						boolean networkConnected = NetworkUtil
								.isNetworkConnected(MineFragment_Update_Pwd.this);
						if (networkConnected) {
							pb_update.setVisibility(View.VISIBLE);
							next_ok.setClickable(false);
							List<NameValuePair> params = new ArrayList<NameValuePair>();
							JSONObject object = new JSONObject();
							try {
								object.put("uid", user_id);
								object.put("old_pswd", old_pwd);
								object.put("new_pswd", new_pwd);
							} catch (Exception e) {
								e.printStackTrace();
							}
							params.add(new BasicNameValuePair("method",
									HttpUrl.updatepwd_method));
							params.add(new BasicNameValuePair("content", object
									.toString()));
							new HttpPostThread(params, handler, 0).start();
						} else {
							pb_update.setVisibility(View.INVISIBLE);
							next_ok.setClickable(false);
							Toast.makeText(MineFragment_Update_Pwd.this,
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
			manager.killActivity(MineFragment_Update_Pwd.this);
			break;
		case R.id.next_ok:
			// 确认修改
			KeyBoardUtil.is_openKeyBoard(getApplicationContext(),
					MineFragment_Update_Pwd.this);

			String old_pwd = old_pwd_value.getText().toString();
			String new_pwd = new_pwd_value.getText().toString();
			String re_new_pwd = re_new_pwd_value.getText().toString();
			if (old_type && new_type && re_new_type) {
				if (new_pwd.equals(re_new_pwd)) {
					String share = getShare();
					// 修改密码提示框
					ShowDialog(share, old_pwd, new_pwd);
				} else {
					Toast.makeText(getApplicationContext(), "两次输入密码不一致", 0)
							.show();
				}
			} else {
				if (old_pwd.length() == 0) {
					Toast.makeText(getApplicationContext(), "原密码不为空", 0).show();
				} else if (new_pwd.length() == 0) {
					Toast.makeText(getApplicationContext(), "新密码不为空", 0).show();
				} else if (re_new_pwd.length() == 0) {
					Toast.makeText(getApplicationContext(), "确认密码不为空", 0).show();
				} else if (!old_type) {
					Toast.makeText(getApplicationContext(), "长度在6到16位之间", 0)
							.show();
				} else if (!new_type) {
					Toast.makeText(getApplicationContext(), "长度在6到16位之间", 0)
							.show();
				} else if (!re_new_type) {
					Toast.makeText(getApplicationContext(), "长度在6到16位之间", 0)
							.show();
				}
			}
			break;
		default:
			break;
		}

	}
}
