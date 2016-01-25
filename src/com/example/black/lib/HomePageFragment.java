package com.example.black.lib;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.example.black.R;
import com.example.black.act.FiltratePageActivity;
import com.example.black.lib.model.Company;
import com.example.black.lib.model.DarkTerraceActivity;
import com.example.black.lib.model.Exposure_Dynamic;
import com.example.black.lib.model.NoAttestationActivity;
import com.example.black.lib.model.NoStorageActivity;
import com.example.black.lib.model.QualifiedTerraceActivity;
import com.example.black.view.MineFragment_Register;
import com.example.black.view.custom.DialogUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class HomePageFragment extends Fragment {
	private View view;// 缓存Fragment view
	private Button btn_homepage_regist;// 注册控件
	private TextView tv_homepage_version;// 版本号控件
	private EditText et_homepage_search;// 搜索输入框
	private ImageView iv_homepage_search;// 搜索控件
	private PackageManager manager;// 包管理器
	private PackageInfo packageinfo;// 当前项目的包
	private SharedPreferences preferences;
	private boolean login_type = false;
	private ConnectivityManager manager2;// 获取网咯的管理器
	private long id;
	private List<Company> companys;
	private Company company;
	private String search_key;// 搜索关键字
	private String old_count;// 获取的公司识别次数
	private List<Company> companys2;
	private TextView tv_exocommpany1;
	private TextView tv_exocontent1;
	private TextView tv_exoname1;
	private TextView tv_exotime1;
	private ImageView tv_exoimage2;
	private TextView tv_exocommpany2;
	private TextView tv_exocontent2;
	private TextView tv_exoname2;
	private TextView tv_exotime2;
	private LinearLayout layout;
	private Exposure_Dynamic dynamic;//第一条动态
	private Exposure_Dynamic exposure_Dynamic2;//第二条动态

	private Handler handler = new Handler() {
		ArrayList<Company> arrayList=null;
		
		@Override
		@SuppressLint("NewApi")
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				String result = msg.obj.toString();
				if ("null".equals(result)) {
					Toast.makeText(getActivity(), "对不起，服务器异常！",
							Toast.LENGTH_SHORT).show();
				} else {
					companys = new JsonUtil().getCompanys(result);
					if (companys != null && companys.size() > 0) {
						// 拿到数据
						company = companys.get(0);
						if (company != null) {
							Intent intent = new Intent();
							intent.putExtra("search_key", search_key);
							intent.putExtra("mqtt_push_type", 2);
							intent.putParcelableArrayListExtra("companys",
									(ArrayList<? extends Parcelable>) companys);
							switch (company.getAuth_level()) {
							// 黑平台
							case "006001":
								intent.setClass(getActivity(),
										DarkTerraceActivity.class);
								getActivity().startActivity(intent);
								break;
							// 未验证
							case "006002":
								intent.setClass(getActivity(),
										NoAttestationActivity.class);
								getActivity().startActivity(intent);
								break;
							// 合规
							case "006003":
								intent.setClass(getActivity(),
										QualifiedTerraceActivity.class);
								getActivity().startActivity(intent);
								break;
							default:
								intent.setClass(getActivity(),
										NoStorageActivity.class);
								getActivity().startActivity(intent);
								break;
							}
						}

					} else {
						Intent intent = new Intent();
						intent.setClass(getActivity(), NoStorageActivity.class);
						intent.putExtra("search_key", search_key);
						getActivity().startActivity(intent);
					}
				}
				break;
			case 2:
				//第一条曝光跳转
				String result2=(String)msg.obj;
				Company one_Companys = new JsonUtil().getOne_Companys(result2);
				if (one_Companys!=null) {
					arrayList = new ArrayList<Company>();
					arrayList.add(one_Companys);
					JumpToResultPage(arrayList, 0);
				}
				break;
			case 3:
				//第二条曝光跳转
				String result3=(String)msg.obj;
				Company one_Companys2 = new JsonUtil().getOne_Companys(result3);
				if (one_Companys2 != null) {
					arrayList = new ArrayList<Company>();
					arrayList.add(one_Companys2);
					JumpToResultPage(arrayList, 0);
				}
				break;
			case 4:
				//查询最多
				String result4=(String)msg.obj;
				List<Company> search_list = new JsonUtil().getMyRankingList(result4, 0);
				if (search_list!=null && search_list.size()>0) {
					JumpToResultPage(search_list, 0);
				}
				break;
			case 5:
			//曝光最多
				String result5=(String)msg.obj;
				List<Company> exo_list = new JsonUtil().getMyRankingList(result5,2);
				if (exo_list!=null && exo_list.size()>0) {
					JumpToResultPage(exo_list, 0);
				}
			break;
			case 6:
				//加黑最多
				String result6=(String)msg.obj;
				List<Company> dark_list = new JsonUtil().getMyRankingList(result6, 0);
				if (dark_list!=null && dark_list.size()>0) {
					JumpToResultPage(dark_list, 0);
				}
				break;
			case 9:
				// 搜索框搜索数据
				String edit_result = (String) msg.obj;
				//Log.i("Test", "搜索框搜索数据--->"+edit_result);
				companys2 = new JsonUtil()
						.getCompanys(edit_result);
				if (companys2 != null && companys2.size() > 0) {
					if (companys2.size() > 1) {
//						AliasListAdapter adapter=new AliasListAdapter(getActivity(),
//								companys2);
//						lv_filtratelist.setAdapter(adapter);
//						lv_filtratelist.setVisibility(View.VISIBLE);
						Intent intent = new Intent(getActivity(),
								FiltratePageActivity.class);
						intent.putExtra("search_key", search_key);
						startActivity(intent);
					} else {
						JumpToResultPage(companys2, 0);
					}
				}else {
					//tv_center.setText(0 + "");
					//lv_filtratelist.setVisibility(View.GONE);
					Intent intent=new Intent(getActivity(),
							NoStorageActivity.class);
					startActivity(intent);
				}
				DialogUtil.dismissProgressDialog();
//				fl_main.setVisibility(View.GONE);
				//List_type=2;
				break;
				//=====================
			case 10:
				//曝光最新动态
				String result_exo=(String) msg.obj;
				List<Exposure_Dynamic> exposure_Dynamic = JsonUtil.getExposure_Dynamic(result_exo);
				if (exposure_Dynamic!=null &&exposure_Dynamic.size()>0) {
					if (exposure_Dynamic.size()==1) {
						dynamic = exposure_Dynamic.get(0);
						if (dynamic!=null) {
							tv_exocommpany1.setText(dynamic.getCompany_name());
							tv_exocontent1.setText(dynamic.getContent());
							tv_exoname1.setText(dynamic.getNickname());
							tv_exotime1.setText(WhatDayUtil.isDate(WhatDayUtil.getDateString("yyyy-MM-dd HH:mm:ss",
									Long.valueOf(dynamic.getAdd_time()))));
						}
					}else if(exposure_Dynamic.size()==2){
						dynamic = exposure_Dynamic.get(0);
						exposure_Dynamic2 = exposure_Dynamic.get(1);
						if (dynamic!=null) {
							tv_exocommpany1.setText(dynamic.getCompany_name());
							tv_exocontent1.setText(dynamic.getContent());
							tv_exoname1.setText(dynamic.getNickname());
							tv_exotime1.setText(WhatDayUtil.isDate(WhatDayUtil.getDateString("yyyy-MM-dd HH:mm:ss",
									Long.valueOf(dynamic.getAdd_time()))));
						}
						if (exposure_Dynamic2!=null) {
							if (Integer.valueOf(exposure_Dynamic2.getPic_1())>0) {
								ImageLoader.getInstance().displayImage(exposure_Dynamic2.getPic_1_url(), tv_exoimage2);
							}else {
								tv_exoimage2.setVisibility(View.GONE);
								tv_background.setVisibility(View.GONE);
							}
							tv_exocommpany2.setText(exposure_Dynamic2.getCompany_name());
							tv_exocontent2.setText(exposure_Dynamic2.getContent());
							tv_exoname2.setText(exposure_Dynamic2.getNickname());
							tv_exotime2.setText(WhatDayUtil.getDateString("yyyy-MM-dd HH:mm:ss",
									Long.valueOf(exposure_Dynamic2.getAdd_time())));
						}
					}
				}
				break;
			}
		};
	};
	private TextView tv_background;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.activity_homepage_fragment, null);
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
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
		
		// PublishSearch.CompanySearch("monica", id, handler, 1);

		// Intent intent=new Intent(getActivity(),FiltratePageActivity.class);
		 //intent.putExtra("search_key", "天津贵金属");
		//startActivity(intent);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initCount();
	}
	
	//===========
	private void setData(String name, String nature, String trade, int type) {
		if (HttpUtil.isNetworkInfo(getActivity()) != null) {
			DialogUtil.showProgressDialog(getActivity(), "加载中...",
					0);
//			fl_main.setVisibility(View.VISIBLE);

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			JSONObject object = new JSONObject();
			try {
				object.put("name", name);
				object.put("nature", nature);
				object.put("trade", trade);
			} catch (Exception e) {
				e.printStackTrace();
			}
			params.add(new BasicNameValuePair("method", HttpUrl.search_method));
			params.add(new BasicNameValuePair("content", object.toString()));
			// Log.i("Test", params.toString());
			new HttpPostThread(params, handler, type).start();
		} else {
			Toast.makeText(getActivity(), "网络连接失败", 0).show();
		}
	}
	
	private void JumpToResultPage(List<Company> list, int position) {
		Company company = list.get(position);
		if (company != null) {
			List<Company> list2=new ArrayList<Company>();
			list2.add(company);
			Intent intent = new Intent();
			intent.putExtra("search_key", company.getCompany_name());
			intent.putExtra("position", 0);
			intent.putExtra("mqtt_push_type", 2);
			intent.putParcelableArrayListExtra("companys",
					(ArrayList<? extends Parcelable>) list2);
			switch (company.getAuth_level()) {
			// 黑平台
			case "006001":
				intent.setClass(getActivity(),
						DarkTerraceActivity.class);
				break;
			// 未验证
			case "006002":
				intent.setClass(getActivity(),
						NoAttestationActivity.class);
				break;
			// 合规
			case "006003":
				intent.setClass(getActivity(),
						QualifiedTerraceActivity.class);
				break;
	 		default:
				intent.setClass(getActivity(),
						NoStorageActivity.class);
				break;
			}
			this.startActivity(intent);
		}
	}

	private void initCount() {
		if (HttpUtil.isNetworkInfo(getActivity()) != null) {
			HttpUtil httpUtil = new HttpUtil();
			//曝光动态
			httpUtil.initExposure_Dynamic(handler, 10);
			// 公司识别次数
			//httpUtil.initCompanyDiscernCount(handler, 2);
			// 公司及平台次数
			//httpUtil.initCompanyPlatformCount(handler, 3);
			//用户曝光次数
			//httpUtil.initCompanyExosureCount(handler, 8);
		} else {
			Toast.makeText(getActivity(), "网络连接失败", 0).show();
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		//是否登陆，登陆就隐藏注册按钮
		login_type = preferences.getBoolean("login_type", false);
		if (login_type) {
			btn_homepage_regist.setVisibility(View.GONE);
		} else {
			btn_homepage_regist.setVisibility(View.VISIBLE);
		}
	}

	// 初始化控件
	private void initView() {
		//第一条曝光的内容
		LinearLayout ll_content = (LinearLayout) getActivity().findViewById(R.id.ll_content);
		tv_exocommpany1 = (TextView) getActivity().findViewById(R.id.tv_exocommpany1);
		tv_exocontent1 = (TextView) getActivity().findViewById(R.id.tv_exocontent1);
		tv_exoname1 = (TextView) getActivity().findViewById(R.id.tv_exoname1);
		tv_exotime1 = (TextView) getActivity().findViewById(R.id.tv_exotime1);
		
		//第一条曝光的整个布局
		LinearLayout ll_top = (LinearLayout) getActivity().findViewById(R.id.ll_top);
		
		//第二条曝光的内容
		tv_exoimage2 = (ImageView) getActivity().findViewById(R.id.tv_exoimage2);
		
		//图片和内容的间距
		tv_background = (TextView) getActivity().findViewById(R.id.tv_background);
		tv_exocommpany2 = (TextView) getActivity().findViewById(R.id.tv_exocommpany2);
		tv_exocontent2 = (TextView) getActivity().findViewById(R.id.tv_exocontent2);
		tv_exoname2 = (TextView) getActivity().findViewById(R.id.tv_exoname2);
		tv_exotime2 = (TextView) getActivity().findViewById(R.id.tv_exotime2);
		
		//第二条曝光的整个布局
		LinearLayout ll_center = (LinearLayout) getActivity().findViewById(R.id.ll_center);
		
		// 查询最多
		ImageView iv_search = (ImageView) getActivity().findViewById(
				R.id.iv_search);
		
		// 曝光最多
		ImageView iv_exposure = (ImageView) getActivity().findViewById(
				R.id.iv_exposure);
		
		// 加黑最多
		ImageView iv_dark = (ImageView) getActivity()
				.findViewById(R.id.iv_dark);
		// 公司识别次数布局
		//rl_left = (RelativeLayout) getActivity().findViewById(R.id.rl_left);
		// 公司及平台数
		//rl_right = (RelativeLayout) getActivity().findViewById(R.id.rl_right);
		manager2 = (ConnectivityManager) getActivity().getSystemService(
				Context.CONNECTIVITY_SERVICE);
		btn_homepage_regist = (Button) this.getView().findViewById(
				R.id.btn_homepage_regist);
		// tv_homepage_version = (TextView) this.getView().findViewById(
		// R.id.tv_homepage_version);
		et_homepage_search = (EditText) this.getView().findViewById(
				R.id.et_homepage_search);
		iv_homepage_search = (ImageView) this.getView().findViewById(
				R.id.iv_homepage_search);
		preferences = this.getActivity().getSharedPreferences("Login_UserInfo",
				Context.MODE_PRIVATE);
		id = preferences.getLong("user_id", 0);
		// iv_ex = (ImageView)this.getView().findViewById(R.id.iv_ex);
		// 设置去注册事件
		btn_homepage_regist.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),
						MineFragment_Register.class);
				startActivity(intent);
			}
		});

		// 根据关键字去搜索
		iv_homepage_search.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				search_key = et_homepage_search.getText().toString();
				if (search_key != null && !"".equals(search_key)) {
					if (manager2.getActiveNetworkInfo() != null) {
						

						// 强制关闭键盘
						InputMethodManager imm = (InputMethodManager) getActivity()
								.getSystemService(Context.INPUT_METHOD_SERVICE);
						imm.hideSoftInputFromWindow(
								et_homepage_search.getWindowToken(), 0);
						
						setData(search_key, null,null, 9);
						
					} else {
						Toast.makeText(getActivity(), "您的神器好像没有联网！",
								Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(getActivity(), "您请输入搜索的关键字！",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		ll_top.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//第一条企业查询
				if (manager2.getActiveNetworkInfo() != null) {
					if(dynamic!=null) if(TimeUtils.isFastDoubleClick(1500)) HttpUtil.initCompanyInfo(dynamic.getCompany_id(), handler, 2);
				}else {
					Toast.makeText(getActivity(), "您的神器好像没有联网！",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		ll_center.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//第二条企业查询
				if (manager2.getActiveNetworkInfo() != null) {
					if(exposure_Dynamic2!=null) if(TimeUtils.isFastDoubleClick(1500)) HttpUtil.initCompanyInfo(exposure_Dynamic2.getCompany_id(), handler, 3);
				} else {
					Toast.makeText(getActivity(), "您的神器好像没有联网！",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		iv_search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (manager2.getActiveNetworkInfo() != null) {
					if(TimeUtils.isFastDoubleClick(1500)) HttpUtil.initSearchMax(handler, 4);
				} else {
					Toast.makeText(getActivity(), "您的神器好像没有联网！",
							Toast.LENGTH_SHORT).show();
				}
			}
		});

		iv_exposure.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (manager2.getActiveNetworkInfo() != null) {
					if(TimeUtils.isFastDoubleClick(1500)) HttpUtil.initRankingList(true,"exp_amount",1,1,HttpUrl.get_list_exposal_method, handler, 5);
				} else {
					Toast.makeText(getActivity(), "您的神器好像没有联网！",
							Toast.LENGTH_SHORT).show();
				}
			}
		});

		iv_dark.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (manager2.getActiveNetworkInfo() != null) {
					if(TimeUtils.isFastDoubleClick(1500)) HttpUtil.initRankingList(true, "add_blk_amount",1,1,HttpUrl.get_list_method, handler, 6);
				} else {
					Toast.makeText(getActivity(), "您的神器好像没有联网！",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	// 获取当前版本
	private void initVersion() {
		manager = getActivity().getPackageManager();
		try {
			packageinfo = manager.getPackageInfo(
					getActivity().getPackageName(), 0);
			String app_version = packageinfo.versionName;
			tv_homepage_version.setText("版本号：" + app_version);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
	}
}
