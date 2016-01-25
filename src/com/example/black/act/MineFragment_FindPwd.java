package com.example.black.act;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.example.black.R;
import com.example.black.lib.AppManager;
import com.example.black.lib.AsyncBitmapLoader;
import com.example.black.lib.HttpPostThread;
import com.example.black.lib.HttpUrl;
import com.example.black.lib.JsonUtil;
import com.example.black.lib.KeyBoardUtil;
import com.example.black.lib.NetworkUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * �һ�����
 * 
 * @author Admin
 * 
 */
public class MineFragment_FindPwd  extends Activity implements OnClickListener {
	private AppManager manager;
	private String code;
	private ImageView register_code_bitmap;
	private String pic_Validate;
	private EditText fp_ed_phone;
	private ProgressBar pb_find;
	private ImageView iv_next_step;
	private EditText ed_code;
	private Button change_time;
	private TimeCount time;
	private RelativeLayout rl_sms;
	private EditText tv_sms_code;
	private boolean phone_type = false;
	private Bitmap bitmap1;
	private AsyncBitmapLoader loader;
	// ͼ����֤��
	Handler handler = new Handler() {

		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				String result = (String) msg.obj;
				Log.i("Test", result);
				pic_Validate = JsonUtil.getPic_Validate(result);
				if (pic_Validate != null && !"".equals(pic_Validate)) {
					ImageLoader.getInstance().displayImage(pic_Validate, register_code_bitmap);
					rl_sms.setVisibility(View.VISIBLE);	
				} else {
					register_code_bitmap.setImageDrawable(getResources()
							.getDrawable(R.drawable.get_image));
					
					Toast.makeText(MineFragment_FindPwd.this, "��ȡ��֤��ʧ��", 0)
							.show();
					
				}
				pb_find.setVisibility(View.INVISIBLE);
				iv_next_step.setClickable(true);
				break;
			default:

				break;
			}
		};
	};

	// �ֻ���֤��
	Handler sms_handler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				String result = (String) msg.obj;
				int update_Info = JsonUtil.getUpdate_Info(result);
				if (update_Info == 0) {
					Toast.makeText(getApplicationContext(), "������֤��ɹ�", 0)
							.show();
					time.start();
				} else {
					Toast.makeText(getApplicationContext(), "ͼ����֤�벻��ȷ������������", 0)
							.show();
					// isNetworkConnected(fp_ed_phone.getText().toString());
				}
				break;

			default:
				break;
			}
		};
	};

	// �ֻ������Ƿ����
	Handler phone_handler = new Handler() {

		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				String result = (String) msg.obj;
				if (result != null && !"".equals(result)) {
					int check_Mobile = JsonUtil.getCheck_Mobile(result);
					if (check_Mobile == 0) {
						isNetworkConnected(fp_ed_phone.getText().toString());
						phone_type = true;
					} else if(check_Mobile==-1){
						fp_ed_phone.setError("�ֻ����벻����");
						phone_type = false;
					}else {
						Toast.makeText(getApplicationContext(), "��ȡͼ����֤��ʧ��", 0).show();
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
		setContentView(R.layout.activity_mine_fragment_find_pwd);
		initView();
		loader = new AsyncBitmapLoader(getApplicationContext());
		
		manager = AppManager.getInstance();
		manager.addActivity(MineFragment_FindPwd.this);
		// ������֤��
		// register_code.setImageBitmap(Code.getInstance().createBitmap());
		// code = Code.getInstance().getCode();

		// isNetworkConnected();
		time = new TimeCount(60000, 1000);
	}

	// ������ͼ����֤��
	private void isNetworkConnected(String mobile) {
		// �ж������Ƿ�����
		boolean networkConnected = NetworkUtil
				.isNetworkConnected(MineFragment_FindPwd.this);
		if (networkConnected) {
			pb_find.setVisibility(View.VISIBLE);
			iv_next_step.setClickable(false);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			JSONObject object = new JSONObject();
			try {
				object.put("mobile", mobile);
			} catch (Exception e) {
				e.printStackTrace();
			}
			params.add(new BasicNameValuePair("method",
					HttpUrl.pic_validate_method));
			params.add(new BasicNameValuePair("content", object.toString()));
			new HttpPostThread(params, handler, 0).start();
		} else {
			pb_find.setVisibility(View.INVISIBLE);
			iv_next_step.setClickable(true);
			Toast.makeText(MineFragment_FindPwd.this, "��������ʧ��", 0).show();
		}
	}

	// ������Ϣ
	private void getSmsData(String mobile, String imagecode) {
		// �ж������Ƿ�����
		boolean networkConnected = NetworkUtil
				.isNetworkConnected(MineFragment_FindPwd.this);
		if (networkConnected) {
			// �����ֻ���֤��
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			JSONObject object = new JSONObject();
			try {
				object.put("mobile", mobile);
				object.put("imagecode", imagecode);
			} catch (Exception e) {
				e.printStackTrace();
			}
			params.add(new BasicNameValuePair("method",
					HttpUrl.send_validate_method));
			params.add(new BasicNameValuePair("content", object.toString()));
			Log.i("Test", params.toString());
			new HttpPostThread(params, sms_handler, 0).start();
		} else {
			Toast.makeText(getApplicationContext(), "��������ʧ��", 0).show();
		}
	}

	// �ж��ֻ������Ƿ����
	private void getIs_Phone(String mobile) {
		// �ж������Ƿ�����
		boolean networkConnected = NetworkUtil
				.isNetworkConnected(MineFragment_FindPwd.this);
		if (networkConnected) {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			JSONObject object = new JSONObject();
			try {
				object.put("mobile", mobile);
			} catch (Exception e) {
				e.printStackTrace();
			}
			params.add(new BasicNameValuePair("method",
					HttpUrl.check_mobile_method));
			params.add(new BasicNameValuePair("content", object.toString()));
			new HttpPostThread(params, phone_handler, 0).start();
		} else {
			Toast.makeText(getApplicationContext(), "��������ʧ��", 0).show();
		}
	}

	private void initView() {
		// ������
		pb_find = (ProgressBar) findViewById(R.id.pb_find);
		// ����
		RelativeLayout iv_ActionBar = (RelativeLayout) findViewById(R.id.iv_ActionBar);
		iv_ActionBar.setVisibility(View.VISIBLE);
		iv_ActionBar.setOnClickListener(this);
		TextView tv_ClassName = (TextView) findViewById(R.id.tv_ClassName);
		tv_ClassName.setText("�һ�����");
		// �ֻ�����
		fp_ed_phone = (EditText) findViewById(R.id.fp_ed_phone);
		// ��֤��ͼ��
		register_code_bitmap = (ImageView) findViewById(R.id.register_code_bitmap);
		register_code_bitmap.setOnClickListener(this);
		// �����ͼ����֤��
		ed_code = (EditText) findViewById(R.id.ed_code);
		// ����ͼ����֤��ɹ���ʾ�ı���
		rl_sms = (RelativeLayout) findViewById(R.id.rl_sms);
		// �ֻ���֤��
		tv_sms_code = (EditText) findViewById(R.id.tv_sms_code);
		// ��ȡ�ֻ���֤�밴ť
		change_time = (Button) findViewById(R.id.change_time);
		change_time.setOnClickListener(this);
		// ��һ��
		iv_next_step = (ImageView) findViewById(R.id.iv_next_step);
		iv_next_step.setOnClickListener(this);
		// �����¼�
		setOnClickListener();
	}

	private void setOnClickListener() {
		// �ֻ���������¼�
		fp_ed_phone.addTextChangedListener(new TextWatcher() {

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
					getIs_Phone(s.toString());
				} else {
					fp_ed_phone.setError("���볤��Ϊ11λ");
				}
			}
		});
		// ͼ����֤��
		ed_code.addTextChangedListener(new TextWatcher() {

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

				} else {
					ed_code.setError("λ��Ϊ4λ");
				}
			}
		});
		// �ֻ���֤��
		tv_sms_code.addTextChangedListener(new TextWatcher() {

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

				} else {
					tv_sms_code.setError("λ��Ϊ4λ");
				}
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_ActionBar:
			// ����
			manager.killActivity(MineFragment_FindPwd.this);
			break;
		case R.id.register_code_bitmap:
			// ������֤��
			// register_code.setImageBitmap(Code.getInstance().createBitmap());
			// code = Code.getInstance().getCode();
			// isNetworkConnected();
			if (fp_ed_phone.getText().toString() != null
					&& !"".equals(fp_ed_phone.getText().toString())) {
				isNetworkConnected(fp_ed_phone.getText().toString());
			} else {
				Toast.makeText(getApplicationContext(), "�ף��������ֻ�����", 0).show();
			}
			break;
		case R.id.change_time:
			// ����ı�ʱ��
			if (ed_code != null && !"".equals(ed_code) && ed_code.length() == 4) {
				getSmsData(fp_ed_phone.getText().toString(), ed_code.getText()
						.toString());
			} else {
				Toast.makeText(getApplicationContext(), "ͼ����֤�벻��ȷ", 0).show();
			}
			break;
		case R.id.iv_next_step:
			KeyBoardUtil.is_openKeyBoard(getApplicationContext(), MineFragment_FindPwd.this);
			
			// ��һ��
			// �ֻ���֤��
			String sms_code = tv_sms_code.getText().toString();
			if (sms_code != null && !"".equals(sms_code)
					&& sms_code.length() == 4) {
				Intent intent = new Intent(MineFragment_FindPwd.this,
						MineFragment_FindPwd_Two.class);
				intent.putExtra("phone", fp_ed_phone.getText().toString());
				intent.putExtra("pic_Validate", sms_code);
				startActivity(intent);
			} else {
				// �ֻ�����
				String phone1 = fp_ed_phone.getText().toString();
				// ͼ����֤��
				String string1 = ed_code.getText().toString();
				// �ֻ���֤��
				String string = tv_sms_code.getText().toString();
				if (phone1.length() == 0) {
					Toast.makeText(getApplicationContext(), "�ֻ����벻Ϊ��", 0)
							.show();
				} else if (string1.length() == 0) {
					Toast.makeText(getApplicationContext(), "ͼ����֤�벻Ϊ��", 0)
							.show();
				} else if (string.length() == 0) {
					Toast.makeText(getApplicationContext(), "��֤�벻Ϊ��", 0).show();
				}
			}
			break;
		default:
			break;
		}
	}

	class TimeCount extends CountDownTimer {
		public TimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);// ��������Ϊ��ʱ��,�ͼ�ʱ��ʱ����
		}

		@Override
		public void onFinish() {// ��ʱ���ʱ����
			change_time.setText("������֤");
			change_time.setClickable(true);
		}

		@Override
		public void onTick(long millisUntilFinished) {// ��ʱ������ʾ
			change_time.setClickable(false);
			change_time.setText(millisUntilFinished / 1000 + "��");
		}
	}
}
