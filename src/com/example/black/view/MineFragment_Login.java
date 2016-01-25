package com.example.black.view;

import com.example.black.R;
import com.example.black.act.AccountBindingActivity;
import com.example.black.act.MineFragment_FindPwd;
import com.example.black.lib.AppManager;
import com.example.black.lib.HttpPostThread;
import com.example.black.lib.HttpUrl;
import com.example.black.lib.HttpUtil;
import com.example.black.lib.JsonUtil;
import com.example.black.lib.KeyBoardUtil;
import com.example.black.lib.NetworkUtil;
import com.example.black.lib.PushInfo;
import com.example.black.lib.Util;
import com.example.black.lib.model.UserInfo;
import com.example.black.lib.umeng.UMStaticConstant;
import com.example.black.main.MainActivity;
import com.example.black.service.PushService;
import com.example.black.view.custom.DialogUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import com.umeng.socialize.bean.HandlerRequestCode;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.bean.StatusCode;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.SocializeClientListener;
import com.umeng.socialize.controller.listener.SocializeListeners.UMAuthListener;
import com.umeng.socialize.controller.listener.SocializeListeners.UMDataListener;
import com.umeng.socialize.exception.SocializeException;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.socialize.utils.OauthHelper;
import com.umeng.socialize.weixin.controller.UMWXHandler;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings.Secure;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
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
 * �û���¼
 * 
 * @author Admin
 * 
 */
public class MineFragment_Login extends FragmentActivity implements OnClickListener {
	private AppManager manager;
	private EditText input_user;
	private EditText input_pwd;
	private ImageView iv_login;
	private ProgressBar pb_login;
	private ImageView login_winxin;
	private ImageView login_qq;
	private ImageView login_sina;
	private String openid;
	private String nickname;
	private String headimgurl;
	private CheckBox cb_pwd;
	public static MineFragment_Login login_page;
	private RelativeLayout iv_ActionBar;
	private int platform_type = 0;// ƽ̨����
	
	//����
	private String mDeviceID;
	private long user_id;
	private String token;

	// ����ƽ̨��Controller, �����������SDK�����á������ȴ���
	private UMSocialService mController = UMServiceFactory
			.getUMSocialService("com.umeng.login");

	// �鿴�Ƿ��
	Handler check_handler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				String result = (String) msg.obj;
				 //Log.i("Test", "�Ƿ�󶨰�--->" + result);
				switch (JsonUtil.check_loginname(result)) {
				case -4:
					Toast.makeText(getApplicationContext(), "����������ʧ��", 0)
							.show();
					DialogUtil.dismissProgressDialog();
					break;
				case 0:
					//Log.i("Test", "�Ѱ�--->" + result);
					// �Ѱ�
					// ��½
					List<NameValuePair> params = new ArrayList<NameValuePair>();
					JSONObject object = new JSONObject();
					try {
						object.put("openid", openid);
					} catch (Exception e) {
						e.printStackTrace();
					}

					// ��½��Ӧƽ̨
					switch (platform_type) {
					case 4:
						params.add(new BasicNameValuePair("method",
								HttpUrl.login_weixin_method));
						break;
					case 5:
						params.add(new BasicNameValuePair("method",
								HttpUrl.login_qq_method));
						break;
					case 6:
						params.add(new BasicNameValuePair("method",
								HttpUrl.login_weibo_method));
						break;
					default:
						break;
					}
					params.add(new BasicNameValuePair("content", object
							.toString()));
					new HttpPostThread(params, WX_login, 0).start();
					//Log.i("Test", "��½����--->" + params.toString());
					break;
				case -1:
					// δ��
					// ��ת��
					Intent intent = new Intent();
					intent.setClass(MineFragment_Login.this,
							AccountBindingActivity.class);
					intent.putExtra("platform_type", platform_type);
					intent.putExtra("openid", openid);
					intent.putExtra("head_photo", headimgurl);
					intent.putExtra("nickname", nickname);
					MineFragment_Login.this.startActivity(intent);
					DialogUtil.dismissProgressDialog();
					break;
				case -2:
					Toast.makeText(getApplicationContext(), "loginname�������Ϸ�", 0)
							.show();
					DialogUtil.dismissProgressDialog();
					break;
				case -3:
					Toast.makeText(getApplicationContext(), "safekey�������Ϸ�", 0)
							.show();
					DialogUtil.dismissProgressDialog();
					break;
				default:
					break;
				}
				break;
			default:
				break;
			}
		};
	};

	// ��������½
	Handler WX_login = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				String result = (String) msg.obj;
				//Log.i("Test", "��������½----->"+result);
				UserInfo userInfo = JsonUtil.getLoginInfo(result);
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
						
						//�������ͷ���
						PushService.actionStart(MineFragment_Login.this);
						Log.i("push", "�������ͷ���-------------->");
						//�ϴ�������Ϣ
						initPush();
						
						if (Util.getRegisterType(MineFragment_Login.this)) {
							Intent intent=new Intent(MineFragment_Login.this,MainActivity.class);
							startActivity(intent);
							Util.UpdateRegisterType(MineFragment_Login.this, false);
						}
						finish();
						break;
					case -1:
						Toast.makeText(getApplicationContext(), "��¼ʧ��", 0)
								.show();
						break;
					case -2:
						Toast.makeText(getApplicationContext(),
								"΢��OpenId�������Ϸ�", 0).show();
						break;
					case -3:
						Toast.makeText(getApplicationContext(),
								"΢��OpenId�����ڻ��������", 0).show();
						break;
					case -4:
						Toast.makeText(getApplicationContext(), "�û������Ƶ�¼", 0)
								.show();
						break;
					case -5:
						Toast.makeText(getApplicationContext(), "�û����ʵ�IP������", 0)
								.show();
					case -6:
						Toast.makeText(getApplicationContext(), "�ӿڱ���", 0)
								.show();
						break;
					case -100:
						Toast.makeText(getApplicationContext(), "����Ĳ������ڿ�ֵ", 0)
								.show();
					default:
						break;
					}
				} else {
					Toast.makeText(getApplicationContext(), "����������ʧ��", 0)
							.show();
				}
				DialogUtil.dismissProgressDialog();
				break;
			default:
				break;
			}
		};
	};

	// ��½
	Handler handler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				String result = (String) msg.obj;
				final UserInfo userInfo = JsonUtil.getLoginInfo(result);
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
						//�������ͷ���
						PushService.actionStart(MineFragment_Login.this);
						Log.i("push", "�������ͷ���-------------->");
						
						//�ϴ�������Ϣ
						initPush();
						
						if (Util.getRegisterType(MineFragment_Login.this)) {
							Intent intent=new Intent(MineFragment_Login.this,MainActivity.class);
							startActivity(intent);
							Util.UpdateRegisterType(MineFragment_Login.this, false);
						}
						finish();
						break;
					case -1:
						Toast.makeText(getApplicationContext(), "��¼ʧ��", 0)
								.show();
						break;
					case -2:
						Toast.makeText(getApplicationContext(), "�û��������ڻ��������",
								0).show();
						break;
					case -3:
						Toast.makeText(getApplicationContext(), "�û������Ƶ�¼", 0)
								.show();
						break;
					case -4:
						Toast.makeText(getApplicationContext(), "�û����ʵ�IP������", 0)
								.show();
						break;
					default:
						break;
					}
				} else {
					Toast.makeText(getApplicationContext(), "����������ʧ��", 0)
							.show();
				}
				//pb_login.setVisibility(View.INVISIBLE);
				DialogUtil.dismissProgressDialog();
				//iv_login.setClickable(true);
				break;
			case 1:
				String result_push = msg.obj.toString();
				final PushInfo pushInfo = JsonUtil.getPushInfo(result_push);
				if(pushInfo != null){
					switch(pushInfo.getIs_sucess()){
					case 0:
						Log.i("push", "login006==============>" );
						Log.i("push", "success" + pushInfo.getIs_sucess());
						break;
					case -1:
						Log.i("push", "login007==============>" );
						Log.i("push", "failure" + pushInfo.getIs_sucess());
						break;
					}
				}
				break;

			default:
				break;
			}
		}
	};

	// ��¼״̬
	Handler login_hander = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				String result = (String) msg.obj;
				//Log.i("��¼״̬--->", result);
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
		setContentView(R.layout.activity_mine_fragment_login);
		login_page=this;
		addQZoneQQPlatform();
		initView();
		//manager = AppManager.getInstance();
		//manager.addActivity(MineFragment_Login.this);
	}
	
	protected void initPush() {
		mDeviceID = Secure.getString(this.getContentResolver(), Secure.ANDROID_ID);
		user_id = Util.getShare_User_id(getApplicationContext());
		token = "souhei/" + user_id + "_" + mDeviceID;  
		
		if(Util.getLoginType(getApplicationContext())){
			if(NetworkUtil.isNetworkConnected(getApplicationContext())){
				new HttpUtil().initPush(user_id, 0,  token, handler, 1);
			}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	    /**ʹ��SSO��Ȩ����������´��� */
	    UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(requestCode) ;
	    if(ssoHandler != null){
	       ssoHandler.authorizeCallBack(requestCode, resultCode, data);
	    }
	}

	private void addQZoneQQPlatform() {
		// ע�⣺��΢����Ȩ��ʱ�򣬱��봫��appSecret
		// wx967daebe835fbeac������΢�ſ���ƽ̨ע��Ӧ�õ�AppID, ������Ҫ�滻����ע���AppID
		// ���΢��ƽ̨
		UMWXHandler wxHandler = new UMWXHandler(MineFragment_Login.this,
				UMStaticConstant.WXAPPID, UMStaticConstant.WXAPPKEY);
		// �ɶ����Ȩ
		wxHandler.setRefreshTokenAvailable(false);
		wxHandler.addToSocialSDK();
		
		// ���QQ
		UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(
				MineFragment_Login.this, UMStaticConstant.QQAPPID,
				UMStaticConstant.QQAPPKEY);
		qqSsoHandler.addToSocialSDK();
		mController.getConfig().getSsoHandler(HandlerRequestCode.QQ_REQUEST_CODE).isClientInstalled();

		// ����sina SSO(���¼)����
		mController.getConfig().setSsoHandler(new SinaSsoHandler());
	}

	private void initView() {
		//΢�ŵ�½
		login_winxin = (ImageView) this.findViewById(R.id.login_winxin1);
		//qq��½
		login_qq = (ImageView) this.findViewById(R.id.login_qq1);
		//���˵�½
		login_sina = (ImageView) this.findViewById(R.id.login_sina1);
		// ������
		pb_login = (ProgressBar) findViewById(R.id.pb_login);
		// ����
		iv_ActionBar = (RelativeLayout) findViewById(R.id.iv_ActionBar);
		iv_ActionBar.setVisibility(View.VISIBLE);
		iv_ActionBar.setOnClickListener(this);
		TextView tv_ClassName = (TextView) findViewById(R.id.tv_ClassName);
		tv_ClassName.setText("�û���¼");
		TextView iv_home = (TextView) findViewById(R.id.iv_home);
		iv_home.setText("ע��");
		iv_home.setVisibility(View.VISIBLE);
		iv_home.setOnClickListener(this);
		// �˺�
		input_user = (EditText) findViewById(R.id.input_user);
		// ����
		input_pwd = (EditText) findViewById(R.id.input_pwd);
		// �Ƿ���ʾ����
		// cb_pwd = (CheckBox) findViewById(R.id.cb_pwd);
		// �һ�����
		findViewById(R.id.jump_findpwd).setOnClickListener(this);
		// ��¼
		iv_login = (ImageView) findViewById(R.id.iv_login);
		iv_login.setOnClickListener(this);

		setOnClickListener();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//�Ƿ��½
		if (Util.getRegisterType(MineFragment_Login.this)) {
			iv_ActionBar.setVisibility(View.INVISIBLE);
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return Util.getRegisterType(MineFragment_Login.this)? false:super.onKeyDown(keyCode, event);
	}
	
	// �ж��Ƿ���Ȩ
	private Boolean isAuthenticated(SHARE_MEDIA SHARE_MEDIA) {
		return OauthHelper.isAuthenticated(MineFragment_Login.this, SHARE_MEDIA);
	}

	private void setOnClickListener() {
		// // �Ƿ���ʾ����
		// cb_pwd.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		//
		// @Override
		// public void onCheckedChanged(CompoundButton buttonView,
		// boolean isChecked) {
		// if (isChecked) {
		// // ����Ϊ������ʾ
		// input_pwd.setInputType(InputType.TYPE_CLASS_TEXT);
		// input_pwd
		// .setTransformationMethod(HideReturnsTransformationMethod
		// .getInstance());
		// } else {
		// // ����Ϊ������ʾ
		// input_pwd
		// .setInputType(InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD);
		// input_pwd
		// .setTransformationMethod(PasswordTransformationMethod
		// .getInstance());
		// }
		// }
		// });
		//

		login_winxin.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if(!mController.getConfig().getSsoHandler(HandlerRequestCode.WX_REQUEST_CODE).isClientInstalled()){
					Toast.makeText(MineFragment_Login.this, "����û�а�װ΢��", 0).show();
					return ;
				}else{
					platform_type = 4;
					Login_Platform(SHARE_MEDIA.WEIXIN, platform_type);
				};
			}
		});

		login_qq.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				//Boolean authenticated = isAuthenticated(SHARE_MEDIA.QQ);
				//if (!authenticated) {
				platform_type = 5;
				Login_Platform(SHARE_MEDIA.QQ, platform_type);}
			//}
		});

		login_sina.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				//Boolean authenticated = isAuthenticated(SHARE_MEDIA.QZONE);
				//if (!authenticated) {
				platform_type = 6;
				Login_Platform(SHARE_MEDIA.SINA, platform_type);}
			//}
		});
	}

	/**
	 * ��Ȩ�������Ȩ�ɹ������ȡ�û���Ϣ</br>
	 */
	@SuppressWarnings("unused")
	private void Login_Platform(final SHARE_MEDIA platform, final int login_type) {
		mController.doOauthVerify(MineFragment_Login.this, platform,
				new UMAuthListener() {

					@Override
					public void onStart(SHARE_MEDIA platform) {
						Log.i("Test", "��Ȩ��ʼ--->");
					}

					@Override
					public void onError(SocializeException e,
							SHARE_MEDIA platform) {
						Log.i("Test", "��Ȩ����--->");
					}

					@Override
					public void onComplete(Bundle value, SHARE_MEDIA platform) {
						DialogUtil.showProgressDialog(MineFragment_Login.this,"��¼��...", 0 );
						Log.i("Test", "��Ȩ���-->");
						final String uid = value.getString("uid");
						if (value != null && !TextUtils.isEmpty(uid)) {
							Log.i("Test", "uid----->" + uid);
							// ��ȡ�û���Ϣ
							mController.getPlatformInfo(
									MineFragment_Login.this, platform,
									new UMDataListener() {

										@Override
										public void onStart() {
											Log.i("Test", "��ȡƽ̨���ݿ�ʼ--->");
										}

										@Override
										public void onComplete(int status,
												Map<String, Object> info) {
											//
											if (status == 200 && info != null) {										
												Log.i("Test", "��ȡƽ̨����--->"
														+ info);
												// ������Ψһ��ʶ
												openid = uid;												
												check_loginname(
														openid,
														login_type,
														HttpUrl.check_loginname_method,
														check_handler);
																
												switch (login_type) {
												case 4:
													nickname =(String) info.get("nickname");
													headimgurl=(String) info.get("headimgurl");
													break;
												case 5:
													nickname =(String) info.get("screen_name");
													headimgurl=(String) info.get("profile_image_url");
													break;
												case 6:
													nickname =(String) info.get("screen_name");
													headimgurl=(String) info.get("profile_image_url");
													break;
												default:
													break;
												}
												//Log.i("Test", "-�س�--->"+nickname);
												//Log.i("Test", "-ͷ��--->"+headimgurl);
												if (isAuthenticated(SHARE_MEDIA.QQ)) logout(SHARE_MEDIA.QQ);
												if (isAuthenticated(SHARE_MEDIA.QZONE)) logout(SHARE_MEDIA.QZONE);
												if (isAuthenticated(SHARE_MEDIA.WEIXIN)) logout(SHARE_MEDIA.WEIXIN);
												if (isAuthenticated(SHARE_MEDIA.SINA)) logout(SHARE_MEDIA.SINA);
											} else {
												Log.i("Test", "��ȡƽ̨���ݷ�������--->");
											}
										}
									});
						} else {
							Toast.makeText(MineFragment_Login.this, "��Ȩʧ��",
									Toast.LENGTH_SHORT).show();
						}
					}

					@Override
					public void onCancel(SHARE_MEDIA platform) {
						Log.i("Test", "��Ȩȡ��--->");
					}
				});
	}

	// ����Ƿ�󶨵������˺�
	private void check_loginname(String openid, int logintype, String method,
			Handler handler) {
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		JSONObject object = new JSONObject();
		try {
			// �Ƿ��
			object.put("loginname", openid);
			object.put("logintype", logintype);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		params.add(new BasicNameValuePair("method", method));
		params.add(new BasicNameValuePair("content", object.toString()));
		new HttpPostThread(params, handler, 0).start();
		//Log.i("Test", "params---->" + params.toString());
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();				
	}

	/**
	 * ע�����ε�¼</br>
	 */
	@SuppressWarnings("unused")
	private void logout(final SHARE_MEDIA platform) {
		mController.deleteOauth(MineFragment_Login.this, platform,
				new SocializeClientListener() {

					@Override
					public void onStart() {
						//Log.i("Test", "��ȡonStart--->");
					}

					@Override
					public void onComplete(int status, SocializeEntity entity) {
						//Log.i("Test", "��ȡonComplete--->");
						String showText = "���" + platform.toString() + "ƽ̨��Ȩ�ɹ�";
						if (status != StatusCode.ST_CODE_SUCCESSED) {
							showText = "���" + platform.toString() + "ƽ̨��Ȩʧ��["
									+ status + "]";
						}
						//Log.i("Test", "ע��ƽ̨--->" + showText);
					}
				});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.jump_findpwd:
			// �һ�����
			Intent intent = new Intent(MineFragment_Login.this,
					MineFragment_FindPwd.class);
			startActivity(intent);
			break;
		case R.id.iv_login:
			KeyBoardUtil.is_openKeyBoard(getApplicationContext(),
					MineFragment_Login.this);

			// ��¼
			String user = input_user.getText().toString();
			String pwd = input_pwd.getText().toString();
			if (user != null && pwd != null && !"".equals(user)
					&& !"".equals(pwd)) {
				// �ж������Ƿ�����
				boolean networkConnected = NetworkUtil
						.isNetworkConnected(MineFragment_Login.this);
				if (networkConnected) {

						DialogUtil.showProgressDialog(MineFragment_Login.this,"��¼��...", 0 );
					
					//iv_login.setClickable(false);
					//pb_login.setVisibility(View.VISIBLE);
					List<NameValuePair> params = new ArrayList<NameValuePair>();
					JSONObject object = new JSONObject();
					try {
						object.put("mobile", user);
						object.put("pswd", pwd);
					} catch (Exception e) {
						e.printStackTrace();
					}
					params.add(new BasicNameValuePair("method",
							HttpUrl.login_method));
					params.add(new BasicNameValuePair("content", object
							.toString()));
					new HttpPostThread(params, handler, 0).start();
				} else {
					//iv_login.setClickable(true);
					//pb_login.setVisibility(View.INVISIBLE);
					DialogUtil.dismissProgressDialog();
					Toast.makeText(MineFragment_Login.this, "��������ʧ��", 0).show();
				}
				// ��ȡ��¼��Ϣ----------
				// List<NameValuePair> login_params = new
				// ArrayList<NameValuePair>();
				// JSONObject object2 = new JSONObject();
				// login_params.add(new BasicNameValuePair("method",
				// HttpUrl.get_login_method));
				// login_params.add(new BasicNameValuePair("content", object2
				// .toString()));
				// new HttpPostThread(login_params, login_hander, 0).start();
			} else {
				Toast.makeText(getApplicationContext(), "�˺Ż����벻Ϊ��", 0).show();
			}
			break;
		case R.id.iv_ActionBar:
			// ������һҳ
			//manager.killActivity(MineFragment_Login.this);
			finish();
			break;
		case R.id.iv_home:
			// ע��
			Intent intent2 = new Intent(MineFragment_Login.this,
					MineFragment_Register.class);
			startActivity(intent2);
			break;
		default:
			break;
		}
	}

	Handler cancel_bind = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				String result = (String) msg.obj;
				//Log.i("Test", "���״̬--->" + result);
				break;

			default:
				break;
			}
		};
	};
}
