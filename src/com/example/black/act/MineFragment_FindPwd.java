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
 * 找回密码
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
	// 图形验证码
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
					
					Toast.makeText(MineFragment_FindPwd.this, "获取验证码失败", 0)
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

	// 手机验证码
	Handler sms_handler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				String result = (String) msg.obj;
				int update_Info = JsonUtil.getUpdate_Info(result);
				if (update_Info == 0) {
					Toast.makeText(getApplicationContext(), "发送验证码成功", 0)
							.show();
					time.start();
				} else {
					Toast.makeText(getApplicationContext(), "图形验证码不正确，请重新输入", 0)
							.show();
					// isNetworkConnected(fp_ed_phone.getText().toString());
				}
				break;

			default:
				break;
			}
		};
	};

	// 手机号码是否存在
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
						fp_ed_phone.setError("手机号码不存在");
						phone_type = false;
					}else {
						Toast.makeText(getApplicationContext(), "获取图形验证码失败", 0).show();
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
		// 生成验证码
		// register_code.setImageBitmap(Code.getInstance().createBitmap());
		// code = Code.getInstance().getCode();

		// isNetworkConnected();
		time = new TimeCount(60000, 1000);
	}

	// 联网拿图形验证码
	private void isNetworkConnected(String mobile) {
		// 判断网络是否正常
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
			Toast.makeText(MineFragment_FindPwd.this, "网络连接失败", 0).show();
		}
	}

	// 发送信息
	private void getSmsData(String mobile, String imagecode) {
		// 判断网络是否正常
		boolean networkConnected = NetworkUtil
				.isNetworkConnected(MineFragment_FindPwd.this);
		if (networkConnected) {
			// 发送手机验证码
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
			Toast.makeText(getApplicationContext(), "网络连接失败", 0).show();
		}
	}

	// 判断手机号码是否存在
	private void getIs_Phone(String mobile) {
		// 判断网络是否正常
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
			Toast.makeText(getApplicationContext(), "网络连接失败", 0).show();
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
		// 手机号码
		fp_ed_phone = (EditText) findViewById(R.id.fp_ed_phone);
		// 验证码图画
		register_code_bitmap = (ImageView) findViewById(R.id.register_code_bitmap);
		register_code_bitmap.setOnClickListener(this);
		// 输入的图形验证码
		ed_code = (EditText) findViewById(R.id.ed_code);
		// 生成图形验证码成功显示的背景
		rl_sms = (RelativeLayout) findViewById(R.id.rl_sms);
		// 手机验证码
		tv_sms_code = (EditText) findViewById(R.id.tv_sms_code);
		// 获取手机验证码按钮
		change_time = (Button) findViewById(R.id.change_time);
		change_time.setOnClickListener(this);
		// 下一步
		iv_next_step = (ImageView) findViewById(R.id.iv_next_step);
		iv_next_step.setOnClickListener(this);
		// 监听事件
		setOnClickListener();
	}

	private void setOnClickListener() {
		// 手机号码监听事件
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
					fp_ed_phone.setError("号码长度为11位");
				}
			}
		});
		// 图形验证码
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
					ed_code.setError("位数为4位");
				}
			}
		});
		// 手机验证码
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
					tv_sms_code.setError("位数为4位");
				}
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_ActionBar:
			// 返回
			manager.killActivity(MineFragment_FindPwd.this);
			break;
		case R.id.register_code_bitmap:
			// 更换验证码
			// register_code.setImageBitmap(Code.getInstance().createBitmap());
			// code = Code.getInstance().getCode();
			// isNetworkConnected();
			if (fp_ed_phone.getText().toString() != null
					&& !"".equals(fp_ed_phone.getText().toString())) {
				isNetworkConnected(fp_ed_phone.getText().toString());
			} else {
				Toast.makeText(getApplicationContext(), "亲，请输入手机号码", 0).show();
			}
			break;
		case R.id.change_time:
			// 点击改变时间
			if (ed_code != null && !"".equals(ed_code) && ed_code.length() == 4) {
				getSmsData(fp_ed_phone.getText().toString(), ed_code.getText()
						.toString());
			} else {
				Toast.makeText(getApplicationContext(), "图形验证码不正确", 0).show();
			}
			break;
		case R.id.iv_next_step:
			KeyBoardUtil.is_openKeyBoard(getApplicationContext(), MineFragment_FindPwd.this);
			
			// 下一步
			// 手机验证码
			String sms_code = tv_sms_code.getText().toString();
			if (sms_code != null && !"".equals(sms_code)
					&& sms_code.length() == 4) {
				Intent intent = new Intent(MineFragment_FindPwd.this,
						MineFragment_FindPwd_Two.class);
				intent.putExtra("phone", fp_ed_phone.getText().toString());
				intent.putExtra("pic_Validate", sms_code);
				startActivity(intent);
			} else {
				// 手机号码
				String phone1 = fp_ed_phone.getText().toString();
				// 图形验证码
				String string1 = ed_code.getText().toString();
				// 手机验证码
				String string = tv_sms_code.getText().toString();
				if (phone1.length() == 0) {
					Toast.makeText(getApplicationContext(), "手机号码不为空", 0)
							.show();
				} else if (string1.length() == 0) {
					Toast.makeText(getApplicationContext(), "图形验证码不为空", 0)
							.show();
				} else if (string.length() == 0) {
					Toast.makeText(getApplicationContext(), "验证码不为空", 0).show();
				}
			}
			break;
		default:
			break;
		}
	}

	class TimeCount extends CountDownTimer {
		public TimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
		}

		@Override
		public void onFinish() {// 计时完毕时触发
			change_time.setText("重新验证");
			change_time.setClickable(true);
		}

		@Override
		public void onTick(long millisUntilFinished) {// 计时过程显示
			change_time.setClickable(false);
			change_time.setText(millisUntilFinished / 1000 + "秒");
		}
	}
}
