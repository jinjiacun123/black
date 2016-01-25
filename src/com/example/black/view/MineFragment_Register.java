package com.example.black.view;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.example.black.R;
import com.example.black.lib.AppManager;
import com.example.black.lib.Code;
import com.example.black.lib.HttpPostThread;
import com.example.black.lib.HttpUrl;
import com.example.black.lib.JsonUtil;
import com.example.black.lib.KeyBoardUtil;
import com.example.black.lib.Mobile_PhoneUtil;
import com.example.black.lib.NetworkUtil;
import com.example.black.lib.model.UserInfo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * �û�ע��
 * 
 * @author Admin
 * 
 */
public class MineFragment_Register extends Activity implements OnClickListener {
	private AppManager manager;
	private ImageView register_code;
	private EditText register_ed_phone;
	private EditText register_ed_pwd;
	private EditText register_ed_code;
	private CheckBox cb_clause;
	private boolean phone_type = false;// �ֻ��Ƿ�ע��״̬
	private boolean pwd_type = false;// ����״̬
	private boolean code_type = false;// ��֤��״̬
	// ע��
	Handler handler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				String result = (String) msg.obj;
				// Log.i("jay_test--->>", result);
				if (result != null && !"".equals(result)) {
					UserInfo userInfo = JsonUtil.getUserInfo(result);
					if (userInfo != null) {
						int is_sucess = userInfo.getIs_sucess();
						if (is_sucess == 0) {
							Toast.makeText(getApplicationContext(), "ע��ɹ�", 0)
									.show();
							manager.killActivity(MineFragment_Register.this);
						} else if (is_sucess == -1) {
							Toast.makeText(getApplicationContext(), "ע��ʧ��", 0)
									.show();
						} else {
							Toast.makeText(getApplicationContext(), "�ֻ������Ѵ���",
									0).show();
						}
					} else {
						Toast.makeText(getApplicationContext(), "����������ʧ��", 0)
								.show();
					}
				} else {
					Toast.makeText(getApplicationContext(), "����������ʧ��", 0)
							.show();
				}
				iv_register.setClickable(true);
				pb_register.setVisibility(View.INVISIBLE);
				break;
			default:
				break;
			}
		};
	};

	// �ж��ֻ������Ƿ����
	Handler phone_handler = new Handler() {

		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				String result = (String) msg.obj;
				if (result != null && !"".equals(result)) {
					int check_Mobile = JsonUtil.getCheck_Mobile(result);
					if (check_Mobile == 0) {
						register_ed_phone.setError("�ֻ�������ע��");
						phone_type = false;
					} else if (check_Mobile == -2) {
						Toast.makeText(getApplicationContext(), "����������ʧ��", 0)
								.show();
					} else {
						phone_type = true;
					}
				} else {
					Toast.makeText(getApplicationContext(), "����������ʧ��", 0)
							.show();
				}
				break;

			default:
				break;
			}
		};
	};
	private ProgressBar pb_register;
	private ImageView iv_register;
	private String code;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mine_fragment_register);
		initView();
		manager = AppManager.getInstance();
		manager.addActivity(MineFragment_Register.this);
	}

	private void initView() {
		// ������
		pb_register = (ProgressBar) findViewById(R.id.pb_register);
		// ����
		RelativeLayout iv_ActionBar = (RelativeLayout) findViewById(R.id.iv_ActionBar);
		iv_ActionBar.setVisibility(View.VISIBLE);
		iv_ActionBar.setOnClickListener(this);
		TextView tv_ClassName = (TextView) findViewById(R.id.tv_ClassName);
		tv_ClassName.setText("�û�ע��");
		// tv_ClassName.setMovementMethod(movement);
		// �ֻ�����
		register_ed_phone = (EditText) findViewById(R.id.register_ed_phone);
		// ��¼����
		register_ed_pwd = (EditText) findViewById(R.id.register_ed_pwd);
		// ������֤��
		register_ed_code = (EditText) findViewById(R.id.register_ed_code);
		// ��֤��ͼ��
		register_code = (ImageView) findViewById(R.id.register_code_bitmap);
		register_code.setOnClickListener(this);
		// ��ѡ�û�Э��
		cb_clause = (CheckBox) findViewById(R.id.cb_clause);
		// �鿴�û�Э��
		findViewById(R.id.watch_clause).setOnClickListener(this);
		// ע��
		iv_register = (ImageView) findViewById(R.id.iv_register);
		iv_register.setOnClickListener(this);
		setOnClickListener();
	}

	private void setOnClickListener() {
		// �ֻ�����
		register_ed_phone.addTextChangedListener(new TextWatcher() {

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
				if (s != null && !"".equals(s) && s.length() == 11) {
					// �ж��Ƿ��ǺϷ��ֻ�����
					boolean mobileNO = Mobile_PhoneUtil.isMobileNO(s.toString());
					if (mobileNO) {
						// �Ϸ�
						List<NameValuePair> params = new ArrayList<NameValuePair>();
						JSONObject object = new JSONObject();
						try {
							object.put("mobile", s.toString());
						} catch (Exception e) {
							e.printStackTrace();
						}
						params.add(new BasicNameValuePair("method",
								HttpUrl.check_mobile_method));
						params.add(new BasicNameValuePair("content", object
								.toString()));
						new HttpPostThread(params, phone_handler, 0).start();
					} else {
						register_ed_phone.setError("���벻�Ϸ�");
					}
				} else {
					register_ed_phone.setError("����λ��Ϊ11λ");
				}
			}
		});
		// ����
		register_ed_pwd.addTextChangedListener(new TextWatcher() {

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
					if ((s.length() > 16 || s.length() < 6)) {
						pwd_type = false;
						register_ed_pwd.setError("������6��16λ֮��");
					} else {
						pwd_type = true;
					}
				} else {
					register_ed_pwd.setError("�˺Ż����벻Ϊ��");
				}
			}
		});
		// ��֤��
		register_ed_code.addTextChangedListener(new TextWatcher() {

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
				if (s != null && !"".equals(s) && s.length() == 4) {
					if (code != null && !"".equals(code)) {
						if (code.equals(s.toString())) {
							code_type = true;
						} else {
							code_type = false;
							//register_ed_code.setError("��֤����������");
						}
					} else {
						Toast.makeText(getApplicationContext(), "���ȵ����ȡ��֤��", 0)
								.show();
					}
				} else {
					//register_ed_code.setError("��֤��Ϊ4λ");
				}
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_ActionBar:
			// ����
			manager.killActivity(MineFragment_Register.this);
			break;
		case R.id.iv_register:
			KeyBoardUtil.is_openKeyBoard(getApplicationContext(),
					MineFragment_Register.this);

			// ע��
			if (cb_clause.isChecked() && phone_type && pwd_type && code_type) {
				String phone = register_ed_phone.getText().toString();
				String pwd = register_ed_pwd.getText().toString();
				// �ж������Ƿ�����
				boolean networkConnected = NetworkUtil
						.isNetworkConnected(MineFragment_Register.this);
				if (networkConnected) {
					List<NameValuePair> params = new ArrayList<NameValuePair>();
					JSONObject object = new JSONObject();
					try {
						object.put("mobile", phone);
						object.put("pswd", pwd);
						//�Ƿ��ƹ��û�(Ĭ��0��1-Ϊ�ƹ��û�)
						object.put("sem", 1);
					} catch (Exception e) {
						e.printStackTrace();
					}
					params.add(new BasicNameValuePair("method",
							HttpUrl.register_method));
					params.add(new BasicNameValuePair("content", object
							.toString()));
					new HttpPostThread(params, handler, 0).start();
					iv_register.setClickable(false);
					pb_register.setVisibility(View.VISIBLE);
				} else {
					iv_register.setClickable(false);
					pb_register.setVisibility(View.VISIBLE);
					Toast.makeText(getApplicationContext(), "��������ʧ��", 0).show();
				}
			} else {
				String string = register_ed_phone.getText().toString();
				if(register_ed_phone.getEditableText().length()==0){
					Toast.makeText(getApplicationContext(), "�ֻ����벻Ϊ��", 0)
					.show();
				}else if(register_ed_phone.getEditableText().length()<11){
					Toast.makeText(MineFragment_Register.this, "����λ��Ϊ11λ", 0).show();
				}else if (!phone_type) {
					//Toast.makeText(getApplicationContext(), "�ֻ�������ע��", 0)
							//.show();
				} else if (!pwd_type) {
					Toast.makeText(getApplicationContext(), "������6��16λ֮��", 0)
							.show();
				} else if (!code_type) {
					Toast.makeText(getApplicationContext(), "��֤����������", 0)
							.show();
				} else if (!cb_clause.isChecked()) {
					Toast.makeText(getApplicationContext(), "���ȹ�ѡ�û�Э��", 0)
							.show();
				}
			}
			break;
		case R.id.watch_clause:
			// �鿴�û�Э��
			Intent intent = new Intent(MineFragment_Register.this,
					MineFragment_Clause.class);
			startActivity(intent);
			break;
		case R.id.register_code_bitmap:
			boolean Code_type=false;
			// ������֤��
			String phone = register_ed_phone.getText().toString();
			String pwd = register_ed_pwd.getText().toString();
			if (phone != null && !"".equals(phone) && pwd != null
					&& !"".equals(pwd)) {
				if (phone_type && pwd_type) {
					register_code.setImageBitmap(Code.getInstance()
							.createBitmap());
					code = Code.getInstance().getCode();
						register_ed_code.setText("");																
				} else {
					if (!phone_type) {
						Toast.makeText(getApplicationContext(), "�ֻ�������ע��", 0)
								.show();
					} else if (!pwd_type) {
						Toast.makeText(getApplicationContext(),
								"������6��16λ֮��", 0).show();
					}
				}
			} else {
				Toast.makeText(getApplicationContext(), "�ֻ��Ż����벻Ϊ��", 0).show();
			}
			break;
		default:
			break;
		}

	}
}
