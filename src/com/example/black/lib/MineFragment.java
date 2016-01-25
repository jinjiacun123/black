package com.example.black.lib;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 我的页面
 * 
 * @author admin
 * 
 */
public class MineFragment extends Fragment implements OnClickListener {
	private View view;// 缓存Fragment view
	private RelativeLayout rl_background1;
	private RelativeLayout rl_login;
	private RelativeLayout rl_register;
	private ImageView background_icon1;
	private TextView background_name1;
	private RelativeLayout rl_background2;

	// 用户头像
	Handler handler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				Bitmap bitmap = (Bitmap) msg.obj;
				if (bitmap != null) {
					background_icon1.setImageBitmap(
							bitmap);
					if (bitmap.isRecycled()) {
						bitmap.recycle();
						bitmap = null;
					}
					System.gc();
				} else {
					// Toast.makeText(getActivity(), "获取头像失败", 0).show();
				}
				break;

			default:
				break;
			}
		}
	};

	// 版本号
	Handler CodeHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				VersionCode code = (VersionCode) msg.obj;
				if (code != null) {
					// 服务器版本
					int versionCode = code.getVersionCode();
					//Log.i("jay-test--->", versionCode + "");
					try {
						if (versionCode > VersionCodeUtil
								.getVersionCode(getActivity())) {
							// 判断网络是否正常
							boolean networkConnected = NetworkUtil
									.isNetworkConnected(getActivity());
							if (networkConnected) {
								// apk路径
								String path = code.getPath();
								// 文件名
								String label = code.getLabel();
								if (path != null && !"".equals(path)) {
									new UpdateManager(getActivity(), path,
											label).checkUpdateInfo();
								} else {
									Toast.makeText(getActivity(), "下载路径不存在 ", 0)
											.show();
								}
							} else {
								Toast.makeText(getActivity(), "网络连接失败", 0)
										.show();
							}
						} else {
							DialogUtil.ShowDialog(getActivity(), "版本更新",
									"恭喜您,您的已经是最新版本了!", null, "确定", null, null);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				break;
			case 1:
				Toast.makeText(getActivity(), "服务器连接失败", 0).show();
				break;
			default:
				break;
			}
		};
	};
	private long user_id;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.activity_mine_fragment, null);
		}
		// 缓存的view需要判断是否已经被加过parent，
		// 如果有parent需要从parent删除，
		// 要不然会发生这个view已经有parent的错误。
		ViewGroup parent = (ViewGroup) view.getParent();
		if (parent != null) {
			parent.removeView(view);
		}
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
	}

	private void initView() {
		// 个人中心标题
		TextView tv_ClassName = (TextView) getActivity().findViewById(
				R.id.tv_ClassName);
		tv_ClassName.setText("个人中心");
		// 登录
		rl_login = (RelativeLayout) getActivity().findViewById(R.id.rl_login);
		rl_login.setOnClickListener(this);
		// 注册
		rl_register = (RelativeLayout) getActivity().findViewById(
				R.id.rl_register);
		rl_register.setOnClickListener(this);
		// 登录成功状态显示的背景(查看用户信息)
		rl_background1 = (RelativeLayout) getActivity().findViewById(
				R.id.rl_background1);
		// 登录成功状态显示的背景(修改密码)
		rl_background2 = (RelativeLayout) getActivity().findViewById(
				R.id.rl_background2);
		// 用户头像
		background_icon1 = (ImageView) getActivity().findViewById(
				R.id.background_icon1);
		// 用户昵称
		background_name1 = (TextView) getActivity().findViewById(
				R.id.background_name1);
		// 检测版本
		getActivity().findViewById(R.id.rl_detection).setOnClickListener(this);
		// 关于搜黑
		getActivity().findViewById(R.id.rl_us).setOnClickListener(this);
		//意见反馈
		getActivity().findViewById(R.id.rl_feedback).setOnClickListener(this);;
	}

	@Override
	public void onDestroy() {
		background_icon1.setImageBitmap(null);
		System.gc();
		super.onDestroy();
	}
	
	// 修改用户昵称
	@Override
	public void onResume() {
		// 是否登录状态
		SharedPreferences sharedPreferences = getActivity()
				.getSharedPreferences("Login_UserInfo", Context.MODE_PRIVATE);
		if (sharedPreferences != null) {
			boolean login_type = sharedPreferences.getBoolean("login_type",
					false);
			if (login_type) {
				user_id = sharedPreferences.getLong("user_id", -1);
				background_name1.setText("欢迎您,  "
						+ sharedPreferences.getString("nickname",
								"欢迎您,  注册用户的昵称"));
				// 判断网络是否正常
				boolean networkConnected = NetworkUtil
						.isNetworkConnected(getActivity());
				if (networkConnected) {
					// 用户头像
					new Mythread(sharedPreferences.getString("head_portrait",
							null)).start();
				} else {
					Toast.makeText(getActivity(), "网络连接失败", 0).show();
				}
				// 是否修改了用户信息
				// SharedPreferences shared =
				// getActivity().getSharedPreferences(
				// "Is_Update", Activity.MODE_PRIVATE);
				// if (shared != null) {
				// boolean boolean1 = shared.getBoolean("is_update", false);
				// if (boolean1) {
				// background_name1
				// .setText("欢迎您,  "
				// + shared.getString("user_name",
				// "欢迎您,  注册用户的昵称"));
				// shared.edit().putBoolean("is_update", false).commit();
				// }
				// }

				// 显示登录成功后的效果
				rl_background1.setVisibility(View.VISIBLE);
				rl_background1.setOnClickListener(this);
				rl_background2.setVisibility(View.VISIBLE);
				rl_background2.setOnClickListener(this);
				//
				rl_login.setVisibility(View.INVISIBLE);
				rl_register.setVisibility(View.INVISIBLE);
			} else {
				rl_background1.setVisibility(View.INVISIBLE);
				rl_background2.setVisibility(View.INVISIBLE);
				rl_login.setVisibility(View.VISIBLE);
				rl_register.setVisibility(View.VISIBLE);
			}
		}
		super.onResume();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_login:
			// 登录
			Intent intent = new Intent(getActivity(), MineFragment_Login.class);
			startActivity(intent);
			break;
		case R.id.rl_register:
			// 注册
			Intent intent2 = new Intent(getActivity(),
					MineFragment_Register.class);
			startActivity(intent2);
			break;
		case R.id.rl_detection:
			// 检测版本
			// 判断网络是否正常
			boolean networkConnected = NetworkUtil
					.isNetworkConnected(getActivity());
			if (networkConnected) {
				new VersonCodeThread().start();
			} else {
				Toast.makeText(getActivity(), "网络连接失败", 0).show();
			}
			break;
		case R.id.rl_us:
			// 关于搜黑
			Intent intent3 = new Intent(getActivity(),
					MineFragment_AboutUs.class);
			startActivity(intent3);
			break;
		case R.id.rl_background1:
			// 登录成功后的状态(查看用户信息)
			Intent intent5 = new Intent(getActivity(),
					MineFragment_UserInfo.class);
			intent5.putExtra("watch_type", true);
			intent5.putExtra("user_id", user_id);
			startActivity(intent5);
			break;
		case R.id.rl_background2:
			// 登录成功后的状态(修改密码)
			Intent intent4 = new Intent(getActivity(),
					MineFragment_Update_Pwd.class);
			startActivity(intent4);
			break;
		case R.id.rl_feedback:
			//反馈
			Intent intent6=new Intent(getActivity(),MineFragment_Feedback.class);
			startActivity(intent6);
			break;
		default:
			break;
		}

	}

	class Mythread extends Thread {
		private String image_url;

		public Mythread(String image_url) {
			this.image_url = image_url;
		}

		@Override
		public void run() {
			Bitmap loadImageSync = ImageLoader.getInstance().loadImageSync(
					image_url);
			if (loadImageSync != null) {
				Message message = new Message();
				message.what = 0;
				message.obj = loadImageSync;
				handler.sendMessage(message);
			}
			super.run();
		}
	}

	class VersonCodeThread extends Thread {

		@Override
		public void run() {
			try {
				HttpClient client = new DefaultHttpClient();
				 HttpPost request = new HttpPost(UpdateManager.update_url);
				HttpResponse execute = client.execute(request);
				Message message = new Message();
				if (execute.getStatusLine().getStatusCode() == 200) {
					VersionCode versionCode = PullParseService
							.getVersionCode(execute.getEntity().getContent());
					if (versionCode != null) {
						message.what = 0;
						message.obj = versionCode;
						CodeHandler.sendMessage(message);
					}
				} else {
					CodeHandler.sendEmptyMessage(1);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			super.run();
		}
	}
}
