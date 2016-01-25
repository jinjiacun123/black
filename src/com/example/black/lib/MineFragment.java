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
 * �ҵ�ҳ��
 * 
 * @author admin
 * 
 */
public class MineFragment extends Fragment implements OnClickListener {
	private View view;// ����Fragment view
	private RelativeLayout rl_background1;
	private RelativeLayout rl_login;
	private RelativeLayout rl_register;
	private ImageView background_icon1;
	private TextView background_name1;
	private RelativeLayout rl_background2;

	// �û�ͷ��
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
					// Toast.makeText(getActivity(), "��ȡͷ��ʧ��", 0).show();
				}
				break;

			default:
				break;
			}
		}
	};

	// �汾��
	Handler CodeHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				VersionCode code = (VersionCode) msg.obj;
				if (code != null) {
					// �������汾
					int versionCode = code.getVersionCode();
					//Log.i("jay-test--->", versionCode + "");
					try {
						if (versionCode > VersionCodeUtil
								.getVersionCode(getActivity())) {
							// �ж������Ƿ�����
							boolean networkConnected = NetworkUtil
									.isNetworkConnected(getActivity());
							if (networkConnected) {
								// apk·��
								String path = code.getPath();
								// �ļ���
								String label = code.getLabel();
								if (path != null && !"".equals(path)) {
									new UpdateManager(getActivity(), path,
											label).checkUpdateInfo();
								} else {
									Toast.makeText(getActivity(), "����·�������� ", 0)
											.show();
								}
							} else {
								Toast.makeText(getActivity(), "��������ʧ��", 0)
										.show();
							}
						} else {
							DialogUtil.ShowDialog(getActivity(), "�汾����",
									"��ϲ��,�����Ѿ������°汾��!", null, "ȷ��", null, null);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				break;
			case 1:
				Toast.makeText(getActivity(), "����������ʧ��", 0).show();
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
		// �����view��Ҫ�ж��Ƿ��Ѿ����ӹ�parent��
		// �����parent��Ҫ��parentɾ����
		// Ҫ��Ȼ�ᷢ�����view�Ѿ���parent�Ĵ���
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
		// �������ı���
		TextView tv_ClassName = (TextView) getActivity().findViewById(
				R.id.tv_ClassName);
		tv_ClassName.setText("��������");
		// ��¼
		rl_login = (RelativeLayout) getActivity().findViewById(R.id.rl_login);
		rl_login.setOnClickListener(this);
		// ע��
		rl_register = (RelativeLayout) getActivity().findViewById(
				R.id.rl_register);
		rl_register.setOnClickListener(this);
		// ��¼�ɹ�״̬��ʾ�ı���(�鿴�û���Ϣ)
		rl_background1 = (RelativeLayout) getActivity().findViewById(
				R.id.rl_background1);
		// ��¼�ɹ�״̬��ʾ�ı���(�޸�����)
		rl_background2 = (RelativeLayout) getActivity().findViewById(
				R.id.rl_background2);
		// �û�ͷ��
		background_icon1 = (ImageView) getActivity().findViewById(
				R.id.background_icon1);
		// �û��ǳ�
		background_name1 = (TextView) getActivity().findViewById(
				R.id.background_name1);
		// ���汾
		getActivity().findViewById(R.id.rl_detection).setOnClickListener(this);
		// �����Ѻ�
		getActivity().findViewById(R.id.rl_us).setOnClickListener(this);
		//�������
		getActivity().findViewById(R.id.rl_feedback).setOnClickListener(this);;
	}

	@Override
	public void onDestroy() {
		background_icon1.setImageBitmap(null);
		System.gc();
		super.onDestroy();
	}
	
	// �޸��û��ǳ�
	@Override
	public void onResume() {
		// �Ƿ��¼״̬
		SharedPreferences sharedPreferences = getActivity()
				.getSharedPreferences("Login_UserInfo", Context.MODE_PRIVATE);
		if (sharedPreferences != null) {
			boolean login_type = sharedPreferences.getBoolean("login_type",
					false);
			if (login_type) {
				user_id = sharedPreferences.getLong("user_id", -1);
				background_name1.setText("��ӭ��,  "
						+ sharedPreferences.getString("nickname",
								"��ӭ��,  ע���û����ǳ�"));
				// �ж������Ƿ�����
				boolean networkConnected = NetworkUtil
						.isNetworkConnected(getActivity());
				if (networkConnected) {
					// �û�ͷ��
					new Mythread(sharedPreferences.getString("head_portrait",
							null)).start();
				} else {
					Toast.makeText(getActivity(), "��������ʧ��", 0).show();
				}
				// �Ƿ��޸����û���Ϣ
				// SharedPreferences shared =
				// getActivity().getSharedPreferences(
				// "Is_Update", Activity.MODE_PRIVATE);
				// if (shared != null) {
				// boolean boolean1 = shared.getBoolean("is_update", false);
				// if (boolean1) {
				// background_name1
				// .setText("��ӭ��,  "
				// + shared.getString("user_name",
				// "��ӭ��,  ע���û����ǳ�"));
				// shared.edit().putBoolean("is_update", false).commit();
				// }
				// }

				// ��ʾ��¼�ɹ����Ч��
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
			// ��¼
			Intent intent = new Intent(getActivity(), MineFragment_Login.class);
			startActivity(intent);
			break;
		case R.id.rl_register:
			// ע��
			Intent intent2 = new Intent(getActivity(),
					MineFragment_Register.class);
			startActivity(intent2);
			break;
		case R.id.rl_detection:
			// ���汾
			// �ж������Ƿ�����
			boolean networkConnected = NetworkUtil
					.isNetworkConnected(getActivity());
			if (networkConnected) {
				new VersonCodeThread().start();
			} else {
				Toast.makeText(getActivity(), "��������ʧ��", 0).show();
			}
			break;
		case R.id.rl_us:
			// �����Ѻ�
			Intent intent3 = new Intent(getActivity(),
					MineFragment_AboutUs.class);
			startActivity(intent3);
			break;
		case R.id.rl_background1:
			// ��¼�ɹ����״̬(�鿴�û���Ϣ)
			Intent intent5 = new Intent(getActivity(),
					MineFragment_UserInfo.class);
			intent5.putExtra("watch_type", true);
			intent5.putExtra("user_id", user_id);
			startActivity(intent5);
			break;
		case R.id.rl_background2:
			// ��¼�ɹ����״̬(�޸�����)
			Intent intent4 = new Intent(getActivity(),
					MineFragment_Update_Pwd.class);
			startActivity(intent4);
			break;
		case R.id.rl_feedback:
			//����
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
