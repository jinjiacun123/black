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
 * �޸�����
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
	private boolean old_type = false;// ������
	private boolean new_type = false;// ������
	private boolean re_new_type = false;// �ٴ�����������

	Handler handler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				String result = (String) msg.obj;
				int update_Info = JsonUtil.getUpdate_Info(result);
				if (update_Info == 0) {
					Toast.makeText(getApplicationContext(), "�޸�����ɹ�", 0).show();
					// �޸ĵ�¼״̬
					getSharedPreferences("Login_UserInfo",
							Context.MODE_PRIVATE).edit()
							.putBoolean("login_type", false).commit();
					Intent intent = new Intent(MineFragment_Update_Pwd.this,
							MineFragment_Login.class);
					startActivity(intent);
					manager.killActivity(MineFragment_Update_Pwd.this);
				} else if (update_Info == -1) {
					Toast.makeText(getApplicationContext(), "�޸�����ʧ��", 0).show();
				} else if (update_Info == -2) {
					Toast.makeText(getApplicationContext(), "�û�������", 0).show();
				} else if (update_Info == -3) {
					Toast.makeText(getApplicationContext(), "ԭ���벻��ȷ", 0).show();
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
		// ������
		pb_update = (ProgressBar) findViewById(R.id.pb_update);
		// ����
		RelativeLayout iv_ActionBar = (RelativeLayout) findViewById(R.id.iv_ActionBar);
		iv_ActionBar.setVisibility(View.VISIBLE);
		iv_ActionBar.setOnClickListener(this);
		TextView tv_ClassName = (TextView) findViewById(R.id.tv_ClassName);
		tv_ClassName.setText("�˺Ź���");
		// ������
		old_pwd_value = (EditText) findViewById(R.id.old_pwd_value);
		// ������
		new_pwd_value = (EditText) findViewById(R.id.new_pwd_value);
		// �ٴ���������
		re_new_pwd_value = (EditText) findViewById(R.id.re_new_pwd_value);
		// ȷ���޸�
		next_ok = (ImageView) findViewById(R.id.next_ok);
		next_ok.setOnClickListener(this);
		// �����¼�
		setOnClickListener();
	}

	private void setOnClickListener() {
		// ������
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
						old_pwd_value.setError("������6��16λ֮��");
						old_type = false;
					} else {
						old_type = true;
					}
				} else {
					old_pwd_value.setError("���벻Ϊ��");
				}
			}
		});
		// ������
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
						new_pwd_value.setError("������6��16λ֮��");
						new_type = false;
					} else {
						new_type = true;
					}
				} else {
					new_pwd_value.setError("���벻Ϊ��");
				}
			}
		});
		// �ٴ���������
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
						re_new_pwd_value.setError("������6��16λ֮��");
						re_new_type = false;
					} else {
						re_new_type = true;
					}
				} else {
					re_new_pwd_value.setError("���벻Ϊ��");
				}
			}
		});
	}

	// �û�id
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
		DialogUtil.ShowDialog(MineFragment_Update_Pwd.this, "��ʾ", "�Ƿ��޸�?",
				"ȡ��", "ȷ��", null, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// �ж������Ƿ�����
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
									"��������ʧ��", 0).show();
						}
					}
				});

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_ActionBar:
			// ����
			manager.killActivity(MineFragment_Update_Pwd.this);
			break;
		case R.id.next_ok:
			// ȷ���޸�
			KeyBoardUtil.is_openKeyBoard(getApplicationContext(),
					MineFragment_Update_Pwd.this);

			String old_pwd = old_pwd_value.getText().toString();
			String new_pwd = new_pwd_value.getText().toString();
			String re_new_pwd = re_new_pwd_value.getText().toString();
			if (old_type && new_type && re_new_type) {
				if (new_pwd.equals(re_new_pwd)) {
					String share = getShare();
					// �޸�������ʾ��
					ShowDialog(share, old_pwd, new_pwd);
				} else {
					Toast.makeText(getApplicationContext(), "�����������벻һ��", 0)
							.show();
				}
			} else {
				if (old_pwd.length() == 0) {
					Toast.makeText(getApplicationContext(), "ԭ���벻Ϊ��", 0).show();
				} else if (new_pwd.length() == 0) {
					Toast.makeText(getApplicationContext(), "�����벻Ϊ��", 0).show();
				} else if (re_new_pwd.length() == 0) {
					Toast.makeText(getApplicationContext(), "ȷ�����벻Ϊ��", 0).show();
				} else if (!old_type) {
					Toast.makeText(getApplicationContext(), "������6��16λ֮��", 0)
							.show();
				} else if (!new_type) {
					Toast.makeText(getApplicationContext(), "������6��16λ֮��", 0)
							.show();
				} else if (!re_new_type) {
					Toast.makeText(getApplicationContext(), "������6��16λ֮��", 0)
							.show();
				}
			}
			break;
		default:
			break;
		}

	}
}
