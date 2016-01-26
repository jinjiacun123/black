package com.example.black.lib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.black.R;
import com.example.black.act.NatureAndIndustryAdapter;
import com.example.black.lib.model.ExosureContent;
import com.example.black.lib.model.ExosureImage;
import com.example.black.lib.model.MineFragment_UserInfo;
import com.example.black.lib.model.NatureAndIndustry;
import com.example.black.view.MineFragment_Login;
import com.example.black.view.SelectPicPopupWindow;
import com.example.black.view.custom.DialogUtil;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

/**
 * �ع��ҳ��
 * 
 * @author admin
 * 
 */
public class ExosureFragment extends Fragment implements OnClickListener{
	// �ع��ϴ�ͼƬ���
		private final String Exosure_number = "001001";
		private View view;// ����Fragment view
		private ImageView iv_content;
		private Spinner nature_value;
		private Spinner industry_value;
		private EditText name_value;
		private EditText money_value;
		private EditText url_value;
		private EditText content_value;
		private String nature_number = null;;// ��˾���ʱ��
		private String industry_number = null;// ������ҵ���
		private List<String> image_path = new ArrayList<String>();// �洢��ͼƬ·������
		private boolean gongsi_type = false;// ��˾״̬
		private boolean hangye_type = false;// ��ҵ״̬
		private boolean name_type = false;// ��˾����״̬
		private boolean money_type = false;// ���״̬
		private boolean url_type = false;// ���״̬
		private boolean content_type = false;// ���״̬
		private boolean Delete_type = true;// �Ƿ�ɾ��ͼƬ��״̬
		// �ϴ�ͼƬ
		Handler handler = new Handler() {

			@Override
			public void handleMessage(android.os.Message msg) {
				switch (msg.what) {
				case 0:
					Map<String, String> maps = (Map<String, String>) msg.obj;
					if (maps != null) {
						Log.i("jay-test-->", maps.toString());
						// Log.i("map---->", maps + "");
						setExosure(maps);
						// maps.clear();
						image_count = 0;
						setNullBitmap(iv_pic1);
						setNullBitmap(iv_pic2);
						setNullBitmap(iv_pic3);
						setNullBitmap(iv_pic4);
						setNullBitmap(iv_pic5);
						iv_add.setVisibility(View.VISIBLE);
						pb_exosure.setVisibility(View.INVISIBLE);
						up_value.setText("δѡ���ļ�");
						System.gc();
					} else {
						Toast.makeText(getActivity(), "�ϴ�ͼƬʧ��", 0).show();
					}
					break;

				default:
					break;
				}
			}

		};

		// ��Ҫ�ع�

		Handler Exosure_handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 0:
					String result = (String) msg.obj;
					 //Log.i("Test--->", "�ع�״̬--->"+result);
					int Info = JsonUtil.getUpdate_Info(result);
					if (Info == 0) {
						rl_background.setClickable(false);
						pb_exosure.setVisibility(View.INVISIBLE);
						Toast.makeText(getActivity(), "�ع�ɹ�", 0).show();
						name_value.setText("");
						money_value.setText("");
						url_value.setText("");
						content_value.setText("");
						
						Intent intent=new Intent(getActivity(),MineFragment_UserInfo.class);
						intent.putExtra("watch_type", true);
						intent.putExtra("Exosure_Type", true);
						intent.putExtra("user_id", Util.getShare_User_id(getActivity()));
						startActivity(intent);
					} else if (Info == -1) {
						Toast.makeText(getActivity(), "�ع�ʧ��", 0).show();
					} else {
						Toast.makeText(getActivity(), "����������ʧ��", 0).show();
					}
					pb_exosure.setVisibility(View.INVISIBLE);
					break;

				default:
					break;
				}
			};
		};
		private String image_url;
		private List<NatureAndIndustry> nature_AddData;
		private List<NatureAndIndustry> industry_AddData;
		private ProgressBar pb_exosure;
		private int image_count = 0;
		private TextView up_value;
		private FrameLayout fl_add;
		private boolean add_type = true;
		private ImageView iv_pic1;
		private ImageView iv_pic2;
		private ImageView iv_pic3;
		private ImageView iv_pic4;
		private ImageView iv_pic5;
		private ImageView iv_add;
		private ImageView iv_exposure;
		private ExoThread exoThread;
		private ExosureContent exosureContent;
		private boolean type = false;
		private String replaceBlank;
		private RelativeLayout rl_background;

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			// ��ֹ�ı��༭������
			getActivity().getWindow().setSoftInputMode(
					WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

			if (view == null) {
				view = inflater.inflate(R.layout.activity_exosure_fragment, null);
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
			// TODO Auto-generated method stub
			super.onActivityCreated(savedInstanceState);
			initView();
		}

		//�ϴ�ͼƬ
		private void isNetworkConnected() {
			//����״̬
			boolean networkConnected = NetworkUtil
					.isNetworkConnected(getActivity());
			if (networkConnected) {
				if (image_path != null && image_path.size() > 0) {
					pb_exosure.setVisibility(View.VISIBLE);
					new ExoThread(image_path).start();
				} else {
					setExosure(null);
					pb_exosure.setVisibility(View.VISIBLE);
				}
			} else {
				Toast.makeText(getActivity(), "��������ʧ��", 0).show();
				pb_exosure.setVisibility(View.INVISIBLE);
			}
		}

		private void initView() {
			// ������ͼƬ
			fl_add = (FrameLayout) findViewById(R.id.fl_add);
			// ���ͼƬ��ť
			iv_add = (ImageView) findViewById(R.id.iv_add);
			iv_add.setOnClickListener(this);
			// ͼƬ����
			iv_pic1 = (ImageView) findViewById(R.id.iv_pic1);
			iv_pic2 = (ImageView) findViewById(R.id.iv_pic2);
			iv_pic3 = (ImageView) findViewById(R.id.iv_pic3);
			iv_pic4 = (ImageView) findViewById(R.id.iv_pic4);
			iv_pic5 = (ImageView) findViewById(R.id.iv_pic5);
			// ������
			pb_exosure = (ProgressBar) findViewById(R.id.pb_exosure);
			// ����
			TextView tv_ClassName = (TextView) findViewById(R.id.tv_ClassName);
			tv_ClassName.setText("��ƽ̨�ع�");
			// ��˾����
			nature_value = (Spinner) findViewById(R.id.nature_value);
			//��˾��������+������
			nature_AddData = Nature_AddData();
			if (nature_AddData != null && nature_AddData.size() > 0) {
				NatureAndIndustryAdapter adapter = new NatureAndIndustryAdapter(
						getActivity(), nature_AddData);
				nature_value.setAdapter(adapter);
			}
			// ������ҵ
			industry_value = (Spinner) findViewById(R.id.industry_value);
			//������ҵ����+������
			industry_AddData = Industry_AddData();
			if (industry_AddData != null && industry_AddData.size() > 0) {
				NatureAndIndustryAdapter adapter = new NatureAndIndustryAdapter(
						getActivity(), industry_AddData);
				industry_value.setAdapter(adapter);
			}
			// ��˾����
			name_value = (EditText) findViewById(R.id.name_value);
			// �漰���
			money_value = (EditText) findViewById(R.id.money_value);
			// ��˾��ַ
			url_value = (EditText) findViewById(R.id.url_value);
			// �ع�����
			content_value = (EditText) findViewById(R.id.content_value);
			// �ϴ�ͼƬ��ʾ
			up_value = (TextView) findViewById(R.id.up_value);
			// �ϴ�ͼƬ
			findViewById(R.id.rl_up).setOnClickListener(this);
			// �عⰴť
			iv_exposure = (ImageView) findViewById(R.id.iv_exposure);
			iv_exposure.setOnClickListener(this);
			// ����
			rl_background = (RelativeLayout) findViewById(R.id.rl_background);
			setOnClickListener();
		}

		private View findViewById(int id) {
			return getActivity().findViewById(id);
		}

		private void setOnClickListener() {
			// ��˾���ʵ���¼�
			nature_value.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> parent, View view,
						int position, long id) {
					nature_number = nature_AddData.get(position).getNumber();
					// gongsi_type = true;
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {
					// TODO Auto-generated method stub

				}

			});
			// ������ҵ����¼�
			industry_value.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> parent, View view,
						int position, long id) {
					// if (position != 0) {
					industry_number = industry_AddData.get(position).getNumber();
					// hangye_type = true;
					// } else {
					// industry_number = "";
					// hangye_type = false;
					// }
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {
					// TODO Auto-generated method stub

				}
			});
			// ��˾����
			name_value.addTextChangedListener(new TextWatcher() {

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
					if (s != null && !"".equals(s) && s.toString().length() > 0) {
						name_type = true;
					} else {
						// name_value.setError("��˾���Ʋ�Ϊ��");
						name_type = false;
					}
				}
			});
			// �漰���
			money_value.addTextChangedListener(new TextWatcher() {

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
					if (s != null && !"".equals(s) && s.toString().length() > 0) {
						money_type = true;
					} else {
						// money_value.setError("�漰��Ϊ��");
						money_type = false;
					}
				}
			});
			// ��˾��ַ
			url_value.addTextChangedListener(new TextWatcher() {

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
					if (s != null && !"".equals(s) && s.toString().length() > 0) {
						url_type = true;
					} else {
						// url_value.setError("��˾��ַ��Ϊ��");
						url_type = false;
					}
				}
			});
			// �ع�����
			content_value.addTextChangedListener(new TextWatcher() {

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
					replaceBlank = StrUtil.replaceBlank(s.toString());
					// String replaceAll = s.toString().replaceAll(" ", "");
					if (replaceBlank != null && !"".equals(replaceBlank)
							&& replaceBlank.length() > 0) {
						content_type = true;
					} else {
						// content_value.setError("�ع����ݲ�Ϊ��");
						content_type = false;
					}
				}
			});
		}

		// ��ܻ���
		private List<NatureAndIndustry> Organization_AddData() {
			List<NatureAndIndustry> list = new ArrayList<NatureAndIndustry>();
			list.add(new NatureAndIndustry("000000", "��ܻ���"));
			list.add(new NatureAndIndustry("002001", "�������ܻ���"));
			list.add(new NatureAndIndustry("002002", "����ܻ���"));
			return list;
		}

		// ��˾����
		private List<NatureAndIndustry> Nature_AddData() {
			List<NatureAndIndustry> list = new ArrayList<NatureAndIndustry>();
			list.add(new NatureAndIndustry("003001", "��˾"));
			list.add(new NatureAndIndustry("003002", "ƽ̨"));
			return list;
		}

		// ������ҵ
		private List<NatureAndIndustry> Industry_AddData() {
			List<NatureAndIndustry> list = new ArrayList<NatureAndIndustry>();
			//list.add(new NatureAndIndustry("004001", "�����"));
			list.add(new NatureAndIndustry("004002", "���"));
			list.add(new NatureAndIndustry("004004","������Ʒ"));
			list.add(new NatureAndIndustry("004005","����"));
			return list;
		}

		// ����ɾ��ͼƬ
		private void setOnLongClickListener(final ImageView image) {

			image.setOnLongClickListener(new OnLongClickListener() {

				@Override
				public boolean onLongClick(View v) {
					DialogUtil.ShowDialog(getActivity(), "����", "�Ƿ�ɾ��?", "ȡ��", "ȷ��",
							null, new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									image_count--;
									image.setImageBitmap(null);
									image.setVisibility(View.GONE);
									if (image_count > 0) {
										up_value.setText("��ѡ���ļ�" + (image_count));
										iv_add.setVisibility(View.VISIBLE);
									} else {
										up_value.setText("δѡ���ļ�");
									}
									Delete_type = false;
									Log.i("ɾ��ͼƬ---------->   ", ""+image_count);
								}
							});

					return false;
				}
			});

		}

		// ����滻ͼƬ
		private void setOnImageChangeListener(ImageView image) {
			image.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					type = false;
					Intent intent = new Intent(
							Intent.ACTION_PICK,
							android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
					startActivityForResult(intent, 0);
				}
			});
		}

		//������������ͼƬ
		@Override
		public void onActivityResult(int requestCode, int resultCode, Intent data) {
			if (resultCode == 200) {
				image_url = data.getStringExtra("image_url");
				// Toast.makeText(getActivity(), "jay_test--->"+image_url,
				// 0).show();
				if (image_url != null && !"".equals(image_url)) {
					//if (Delete_type) {
						image_count++;
						image_path.add(image_url);
				//	}
					//Log.i("��ȡͼƬ---------->   ", ""+image_count);	
					switch (image_count) {
					case 1:
						setBitmap(iv_pic1, image_url, image_count);
						setOnLongClickListener(iv_pic1);
						// setOnImageChangeListener(iv_pic1);
						break;
					case 2:
						setBitmap(iv_pic2, image_url, image_count);
						setOnLongClickListener(iv_pic2);
						// setOnImageChangeListener(iv_pic2);
						break;
					case 3:
						setBitmap(iv_pic3, image_url, image_count);
						setOnLongClickListener(iv_pic3);
						// setOnImageChangeListener(iv_pic3);
						break;
					case 4:
						setBitmap(iv_pic4, image_url, image_count);
						setOnLongClickListener(iv_pic4);
						// setOnImageChangeListener(iv_pic4);
						break;
					case 5:
						setBitmap(iv_pic5, image_url, image_count);
						setOnLongClickListener(iv_pic5);
						// setOnImageChangeListener(iv_pic5);
						Toast.makeText(getActivity(), "��,����ϴ�����ͼƬ", 0).show();
						fl_add.setVisibility(View.INVISIBLE);
						iv_add.setVisibility(View.INVISIBLE);
						iv_exposure.setClickable(true);
						break;
					default:
						break;
					}
				}
			}
			super.onActivityResult(requestCode, resultCode, data);
		}

		// ���ý�Ҫ�ϴ���չʾͼƬ
		@SuppressWarnings("deprecation")
		private void setBitmap(ImageView view, String image_paths, int image_count) {
			// Bitmap bitmap = BitmapFactory.decodeFile(image_path);
			Display display = getActivity().getWindowManager().getDefaultDisplay();
			// ͼƬ����ѹ��
			Bitmap bitmap = Util.extractThumbNail(image_paths,
					display.getWidth() / 5, display.getHeight() / 5, true);

			// Bitmap bitmap = BitmapFactory.decodeFile(image_paths, 1);
			if (bitmap != null) {
				// �ߴ�ѹ��
				Bitmap bitmap2 = ImageUtils.resizeImageByWidth(bitmap,
						getActivity().getWindowManager().getDefaultDisplay()
								.getWidth() / 7);
				if (bitmap2 != null) {
					up_value.setText("��ѡ���ļ�" + image_count);
					view.setImageBitmap(bitmap2);
					view.setVisibility(View.VISIBLE);
					// if (!bitmap2.isRecycled()) {
					// bitmap2.recycle();
					// bitmap2 = null;
					// }
				} else {
					image_count--;
					image_path.remove(image_url);
					return;
				}
				// if (!bitmap.isRecycled()) {
				// bitmap.recycle();
				// bitmap = null;
				// }
				// System.gc();
			} else {
				image_count--;
				image_path.remove(image_url);
				return;
			}
			Delete_type=true;
			Log.i("��ʾͼƬ---------->   ", ""+image_count);	
		}

		private void setNullBitmap(ImageView view) {
			view.setImageBitmap(null);
			view.setVisibility(View.GONE);
		}

		@Override
		public void onDestroy() {
			setNullBitmap(iv_pic1);
			setNullBitmap(iv_pic2);
			setNullBitmap(iv_pic3);
			setNullBitmap(iv_pic4);
			setNullBitmap(iv_pic5);
			System.gc();
			super.onDestroy();
		}

		private void setExosure(Map<String, String> map) {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			JSONObject object = new JSONObject();
			try {
				if (exosureContent != null) {
					object.put("user_id", exosureContent.getUser_id());
					object.put("nature", exosureContent.getNature_value());
					object.put("trade", exosureContent.getIndustry_value());
					object.put("company_name", exosureContent.getName_value());
					object.put("amount", exosureContent.getMoney_value());
					object.put("website", exosureContent.getUrl_value());
					object.put("content", exosureContent.getContent_value());
				}
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			try {
				if (map != null) {
					for (int i = 0; i < 5; i++) {
						if (map.get("pic_" + (i + 1)) != null
								&& !"".equals(map.get("pic_" + (i + 1)))) {
							object.put("pic_" + (i + 1), map.get("pic_" + (i + 1)));
						} else {
							object.put("pic_" + (i + 1), "");
						}
					}
				} else {
					for (int i = 0; i < 5; i++) {
						object.put("pic_" + (i + 1), "");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			params.add(new BasicNameValuePair("method", HttpUrl.exposure_method));
			params.add(new BasicNameValuePair("content", object.toString()));
			// Log.i("jay_test-->", object.toString());
			new HttpPostThread(params, Exosure_handler, 0).start();
		};

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.rl_up:
				rl_background.setOnClickListener(this);
				iv_exposure.setClickable(false);
				fl_add.setVisibility(View.VISIBLE);
				rl_background.setClickable(true);
				break;
			case R.id.iv_exposure:
				// �ر������
				KeyBoardUtil.is_openKeyBoard(getActivity(), getActivity());
				// �عⰴť
				// �Ƿ��ѵ�¼
				SharedPreferences shared = getActivity().getSharedPreferences(
						"Login_UserInfo", Context.MODE_PRIVATE);
				if (shared != null) {
					boolean login_type = shared.getBoolean("login_type", false);
					if (login_type) {
						// Log.i("��¼״̬--->", login_type+"");
						long user_id = shared.getLong("user_id", 0);

						String string3 = name_value.getText().toString();//
						String string4 = money_value.getText().toString();//
						String string5 = url_value.getText().toString();//
						String string6 = content_value.getText().toString();//
						if (name_type && content_type) {
							if(!TextUtils.isEmpty(string5) && !new StrUtil().isWWW(string5)){
									Toast.makeText(getActivity(), "��˾��ַ���Ϸ�", 0).show(); break;
							} 
							exosureContent = new ExosureContent(
									String.valueOf(user_id), nature_number,
									industry_number, string3, string4, string5,
									replaceBlank);
							isNetworkConnected();
						} else {
							if (!name_type) {
								// Log.i("jay_test--->", "no");
								Toast.makeText(getActivity(), "��˾���Ʋ�Ϊ��", 0).show();
							} else if (!content_type) {
								Toast.makeText(getActivity(), "�ع����ݲ�Ϊ��", 0).show();
							}
						}
					} else {
						Intent intent = new Intent(getActivity(),
								MineFragment_Login.class);
						startActivity(intent);
					}
				} else {
					Intent intent = new Intent(getActivity(),
							MineFragment_Login.class);
					startActivity(intent);
				}
				break;
			case R.id.iv_add:
				// ���ͼƬ��ť
				// ��ȡ����ͼƬ

				Intent intent = new Intent(getActivity(),
						SelectPicPopupWindow.class);
				startActivityForResult(intent, 100);
				break;
			case R.id.rl_background:
				iv_exposure.setClickable(true);
				fl_add.setVisibility(View.INVISIBLE);
				rl_background.setClickable(false);
				break;
			default:
				break;
			}
		}

		class ExoThread extends Thread {

			private List<String> list;
			private ExosureImage exosureImage;

			public ExoThread(List<String> list) {
				this.list = list;
			}

			@Override
			public void run() {
				Map<String, String> map = new HashMap<String, String>();// ͼƬid;
				for (int i = 0; i < list.size(); i++) {
					String upLoad = HttpUtil.UpLoad(list.get(i), Exosure_number);
					if (upLoad != null && !"".equals(upLoad)) {
						exosureImage = JsonUtil.getExosureImageId(upLoad);
						if (exosureImage != null) {
							int is_sucess = exosureImage.getIs_sucess();
							if (is_sucess == 0) {
								map.put("pic_" + (i + 1), exosureImage.getId());
							}
						}
					}
				}
				// �ϴ��ɹ����ͼƬid
				Message message = new Message();
				message.what = 0;
				message.obj = map;
				handler.sendMessage(message);
				image_path.clear();
			}
		}
}
