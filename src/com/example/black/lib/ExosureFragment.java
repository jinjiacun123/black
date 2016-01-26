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
 * 曝光的页面
 * 
 * @author admin
 * 
 */
public class ExosureFragment extends Fragment implements OnClickListener{
	// 曝光上传图片编号
		private final String Exosure_number = "001001";
		private View view;// 缓存Fragment view
		private ImageView iv_content;
		private Spinner nature_value;
		private Spinner industry_value;
		private EditText name_value;
		private EditText money_value;
		private EditText url_value;
		private EditText content_value;
		private String nature_number = null;;// 公司性质编号
		private String industry_number = null;// 所属行业编号
		private List<String> image_path = new ArrayList<String>();// 存储的图片路径集合
		private boolean gongsi_type = false;// 公司状态
		private boolean hangye_type = false;// 行业状态
		private boolean name_type = false;// 公司名称状态
		private boolean money_type = false;// 金额状态
		private boolean url_type = false;// 金额状态
		private boolean content_type = false;// 金额状态
		private boolean Delete_type = true;// 是否删除图片的状态
		// 上传图片
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
						up_value.setText("未选择文件");
						System.gc();
					} else {
						Toast.makeText(getActivity(), "上传图片失败", 0).show();
					}
					break;

				default:
					break;
				}
			}

		};

		// 我要曝光

		Handler Exosure_handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 0:
					String result = (String) msg.obj;
					 //Log.i("Test--->", "曝光状态--->"+result);
					int Info = JsonUtil.getUpdate_Info(result);
					if (Info == 0) {
						rl_background.setClickable(false);
						pb_exosure.setVisibility(View.INVISIBLE);
						Toast.makeText(getActivity(), "曝光成功", 0).show();
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
						Toast.makeText(getActivity(), "曝光失败", 0).show();
					} else {
						Toast.makeText(getActivity(), "服务器连接失败", 0).show();
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
			// 禁止文本编辑器弹出
			getActivity().getWindow().setSoftInputMode(
					WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

			if (view == null) {
				view = inflater.inflate(R.layout.activity_exosure_fragment, null);
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
			// TODO Auto-generated method stub
			super.onActivityCreated(savedInstanceState);
			initView();
		}

		//上传图片
		private void isNetworkConnected() {
			//联网状态
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
				Toast.makeText(getActivity(), "网络连接失败", 0).show();
				pb_exosure.setVisibility(View.INVISIBLE);
			}
		}

		private void initView() {
			// 店家添加图片
			fl_add = (FrameLayout) findViewById(R.id.fl_add);
			// 添加图片按钮
			iv_add = (ImageView) findViewById(R.id.iv_add);
			iv_add.setOnClickListener(this);
			// 图片集合
			iv_pic1 = (ImageView) findViewById(R.id.iv_pic1);
			iv_pic2 = (ImageView) findViewById(R.id.iv_pic2);
			iv_pic3 = (ImageView) findViewById(R.id.iv_pic3);
			iv_pic4 = (ImageView) findViewById(R.id.iv_pic4);
			iv_pic5 = (ImageView) findViewById(R.id.iv_pic5);
			// 进度条
			pb_exosure = (ProgressBar) findViewById(R.id.pb_exosure);
			// 标题
			TextView tv_ClassName = (TextView) findViewById(R.id.tv_ClassName);
			tv_ClassName.setText("黑平台曝光");
			// 公司性质
			nature_value = (Spinner) findViewById(R.id.nature_value);
			//公司性质数据+适配器
			nature_AddData = Nature_AddData();
			if (nature_AddData != null && nature_AddData.size() > 0) {
				NatureAndIndustryAdapter adapter = new NatureAndIndustryAdapter(
						getActivity(), nature_AddData);
				nature_value.setAdapter(adapter);
			}
			// 所属行业
			industry_value = (Spinner) findViewById(R.id.industry_value);
			//所属行业数据+适配器
			industry_AddData = Industry_AddData();
			if (industry_AddData != null && industry_AddData.size() > 0) {
				NatureAndIndustryAdapter adapter = new NatureAndIndustryAdapter(
						getActivity(), industry_AddData);
				industry_value.setAdapter(adapter);
			}
			// 公司名称
			name_value = (EditText) findViewById(R.id.name_value);
			// 涉及金额
			money_value = (EditText) findViewById(R.id.money_value);
			// 公司网址
			url_value = (EditText) findViewById(R.id.url_value);
			// 曝光内容
			content_value = (EditText) findViewById(R.id.content_value);
			// 上传图片显示
			up_value = (TextView) findViewById(R.id.up_value);
			// 上传图片
			findViewById(R.id.rl_up).setOnClickListener(this);
			// 曝光按钮
			iv_exposure = (ImageView) findViewById(R.id.iv_exposure);
			iv_exposure.setOnClickListener(this);
			// 背景
			rl_background = (RelativeLayout) findViewById(R.id.rl_background);
			setOnClickListener();
		}

		private View findViewById(int id) {
			return getActivity().findViewById(id);
		}

		private void setOnClickListener() {
			// 公司性质点击事件
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
			// 所属行业点击事件
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
			// 公司名称
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
						// name_value.setError("公司名称不为空");
						name_type = false;
					}
				}
			});
			// 涉及金额
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
						// money_value.setError("涉及金额不为空");
						money_type = false;
					}
				}
			});
			// 公司网址
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
						// url_value.setError("公司网址不为空");
						url_type = false;
					}
				}
			});
			// 曝光内容
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
						// content_value.setError("曝光内容不为空");
						content_type = false;
					}
				}
			});
		}

		// 监管机构
		private List<NatureAndIndustry> Organization_AddData() {
			List<NatureAndIndustry> list = new ArrayList<NatureAndIndustry>();
			list.add(new NatureAndIndustry("000000", "监管机构"));
			list.add(new NatureAndIndustry("002001", "贵金属监管机构"));
			list.add(new NatureAndIndustry("002002", "外汇监管机构"));
			return list;
		}

		// 公司性质
		private List<NatureAndIndustry> Nature_AddData() {
			List<NatureAndIndustry> list = new ArrayList<NatureAndIndustry>();
			list.add(new NatureAndIndustry("003001", "公司"));
			list.add(new NatureAndIndustry("003002", "平台"));
			return list;
		}

		// 所属行业
		private List<NatureAndIndustry> Industry_AddData() {
			List<NatureAndIndustry> list = new ArrayList<NatureAndIndustry>();
			//list.add(new NatureAndIndustry("004001", "贵金属"));
			list.add(new NatureAndIndustry("004002", "外汇"));
			list.add(new NatureAndIndustry("004004","大宗商品"));
			list.add(new NatureAndIndustry("004005","其他"));
			return list;
		}

		// 长按删除图片
		private void setOnLongClickListener(final ImageView image) {

			image.setOnLongClickListener(new OnLongClickListener() {

				@Override
				public boolean onLongClick(View v) {
					DialogUtil.ShowDialog(getActivity(), "警告", "是否删除?", "取消", "确定",
							null, new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									image_count--;
									image.setImageBitmap(null);
									image.setVisibility(View.GONE);
									if (image_count > 0) {
										up_value.setText("已选择文件" + (image_count));
										iv_add.setVisibility(View.VISIBLE);
									} else {
										up_value.setText("未选择文件");
									}
									Delete_type = false;
									Log.i("删除图片---------->   ", ""+image_count);
								}
							});

					return false;
				}
			});

		}

		// 点击替换图片
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

		//依次设置五张图片
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
					//Log.i("读取图片---------->   ", ""+image_count);	
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
						Toast.makeText(getActivity(), "亲,最多上传五张图片", 0).show();
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

		// 设置将要上传的展示图片
		@SuppressWarnings("deprecation")
		private void setBitmap(ImageView view, String image_paths, int image_count) {
			// Bitmap bitmap = BitmapFactory.decodeFile(image_path);
			Display display = getActivity().getWindowManager().getDefaultDisplay();
			// 图片质量压缩
			Bitmap bitmap = Util.extractThumbNail(image_paths,
					display.getWidth() / 5, display.getHeight() / 5, true);

			// Bitmap bitmap = BitmapFactory.decodeFile(image_paths, 1);
			if (bitmap != null) {
				// 尺寸压缩
				Bitmap bitmap2 = ImageUtils.resizeImageByWidth(bitmap,
						getActivity().getWindowManager().getDefaultDisplay()
								.getWidth() / 7);
				if (bitmap2 != null) {
					up_value.setText("已选择文件" + image_count);
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
			Log.i("显示图片---------->   ", ""+image_count);	
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
				// 关闭软键盘
				KeyBoardUtil.is_openKeyBoard(getActivity(), getActivity());
				// 曝光按钮
				// 是否已登录
				SharedPreferences shared = getActivity().getSharedPreferences(
						"Login_UserInfo", Context.MODE_PRIVATE);
				if (shared != null) {
					boolean login_type = shared.getBoolean("login_type", false);
					if (login_type) {
						// Log.i("登录状态--->", login_type+"");
						long user_id = shared.getLong("user_id", 0);

						String string3 = name_value.getText().toString();//
						String string4 = money_value.getText().toString();//
						String string5 = url_value.getText().toString();//
						String string6 = content_value.getText().toString();//
						if (name_type && content_type) {
							if(!TextUtils.isEmpty(string5) && !new StrUtil().isWWW(string5)){
									Toast.makeText(getActivity(), "公司网址不合法", 0).show(); break;
							} 
							exosureContent = new ExosureContent(
									String.valueOf(user_id), nature_number,
									industry_number, string3, string4, string5,
									replaceBlank);
							isNetworkConnected();
						} else {
							if (!name_type) {
								// Log.i("jay_test--->", "no");
								Toast.makeText(getActivity(), "公司名称不为空", 0).show();
							} else if (!content_type) {
								Toast.makeText(getActivity(), "曝光内容不为空", 0).show();
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
				// 添加图片按钮
				// 读取相册的图片

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
				Map<String, String> map = new HashMap<String, String>();// 图片id;
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
				// 上传成功后的图片id
				Message message = new Message();
				message.what = 0;
				message.obj = map;
				handler.sendMessage(message);
				image_path.clear();
			}
		}
}
