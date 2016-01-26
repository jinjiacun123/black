package com.example.black.lib.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.example.black.R;
import com.example.black.act.FiltratePageActivity;
import com.example.black.act.HomePageController;
import com.example.black.act.PublishComment;
import com.example.black.act.UnitVipActivity;
import com.example.black.lib.AppManager;
import com.example.black.lib.AsyncBitmapLoader;
import com.example.black.lib.DensityUtil;
import com.example.black.lib.HttpPostThread;
import com.example.black.lib.HttpUrl;
import com.example.black.lib.HttpUtil;
import com.example.black.lib.ImageCallBack;
import com.example.black.lib.ImageUtils;
import com.example.black.lib.JsonUtil;
import com.example.black.lib.KeyBoardUtil;
import com.example.black.lib.NetworkUtil;
import com.example.black.lib.PhpUrl;
import com.example.black.lib.PublishExposal;
import com.example.black.lib.PublishNew;
import com.example.black.lib.PublishSearch;
import com.example.black.lib.Util;
import com.example.black.lib.WeiXinShareController;
import com.example.black.lib.umeng.UMShareManager;
import com.example.black.lib.umeng.UMStaticConstant;
import com.example.black.view.AdapterComment;
import com.example.black.view.AdapterExosure;
import com.example.black.view.AdapterNews;
import com.example.black.view.All_fragment;
import com.example.black.view.FaceConversionUtil;
import com.example.black.view.MineFragment_Login;
import com.example.black.view.SelectPicPopupWindow;
import com.example.black.view.ViewPagerAdapter;
import com.example.black.view.VipAdapter;
import com.example.black.view.custom.ChatEmoji;
import com.example.black.view.custom.DialogUtil;
import com.example.black.view.custom.XListView;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.UMSsoHandler;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.SpannableString;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

@SuppressLint("NewApi")
public class DarkTerraceActivity extends FragmentActivity implements
OnItemClickListener {
	private final UMSocialService mController = UMServiceFactory
			.getUMSocialService(UMStaticConstant.DESCRIPTOR);
	private XListView lv_darkterrace;
	private TextView tv_darktext;
	private View view;
	private ActionBar actionBar;// activity的actionbar
	private ImageView iv_actionbar_search_icon;// 回退控件
	private ImageView iv_actionbar_search;// 去搜索控件
	private EditText et_actionbar_search;// 输入框的值
	private TextView tv_actionbar_search;// 右边的值
	private SharedPreferences preferences;
	private SharedPreferences.Editor editor;
	private int comment_type = 1;// 评论的类型 1：网友评论 2：网友曝光 3：媒体报道
	private String str = "数据正在整理...";
	//private FrameLayout fl_noattestation;// 加载布局
	private ConnectivityManager manager;// 获取网咯的管理器
	private InputMethodManager inputmanger;// 键盘管理器
	private View view2;
	private long user_id = -1;
	private SharedPreferences preferences2;
	private List<String> image_url;
	
	private int platform_type=1;

	private RelativeLayout rel_darkterrace_rel3;// 我要评论的布局
	private RelativeLayout rel_darkterrace3;// 网友评论
	private RelativeLayout rel_darkterrace4;// 网友曝光
	private RelativeLayout rel_darkterrace5;// 媒体报道
	private TextView tv_darkterrace_comment;// 网友评论
	private TextView tv_darkterrace_exosure;// 网友曝光
	private TextView tv_darkterrace_media;// 媒体报道
	private ListAdapter adapter;

	private RelativeLayout iv_darkterrace_adddark;// 加黑
	private TextView tv_darkterrace_hraad_havadarknumber;// 加黑的数目
	private TextView tv_darkterrace_hraad_commentnumber;// 评论的数目
	private TextView tv_darkterrace_hraad_exosure;// 曝光的数目
	private ImageView iv_darkterrace_terraceicon;// 企业logo
	private TextView tv_darkterrace_terracename;// 企业名称
	private TextView tv_darkterrace_terracename2;// 企业别名
	// private TextView tv_darkterrace_company_license;//公司资质
	// private TextView tv_darkterrace_company_type;//企业类型
	private TextView tv_darkterrace_agent_terrace;// 代理平台
	private TextView tv_darkterrace_sito_officiale;// 官方网站
	private TextView tv_darkterrace_record_officiale;// 官网备案
	private TextView tv_darkterrace_phone;// 联系电话
	private TextView tv_darkterrace_registered_address;// 注册地址
	private TextView tv_darkterrace_mem_sn;// 会员编号
	private TextView tv_darkterrace_regulators;// 会员编号
	private LinearLayout linl_darktrerrace1;
	private LinearLayout linl_darktrerrace2;

	private RelativeLayout rel_darkterrace2;// 平台信息的布局
	private ImageView iv_darkterrace_icon;// 伸缩图标

	private AdapterComment adapterComment;// 评论的adapter
	private AdapterExosure adapterExosure;// 曝光的adapter
	private AdapterNews adapterNews;// 新闻的adapt
	private String search_key;// 搜索关键字
	private List<Company> companys;// 公司数据的集合
	private AsyncBitmapLoader async;
	private Company company;//

	private RelativeLayout rel_darkterrace_rl1;// 透明部分
	private FrameLayout fl_darkterrace_fl1;// 评论框的布局
	private ImageView iv_darkterrace_share1;// 去分享
	private RelativeLayout iv_darkterrace_speak;// 说一说
	private RadioButton rb_darkterrace_1;// 点赞
	private RadioButton rb_darkterrace_2;// 提问
	private RadioButton rb_darkterrace_3;// 加黑
	private EditText et_darkterrace_comment;// 评论的内容
	private CheckBox cb_noattestatiob_anon;// 是否匿名
	private ImageView iv_darkterrace_comments;// 我要发布
	private Comment_Company comment_company;// 添加评论实体类
	private RadioGroup rg_noattextation_commenttype;// 评论的类型

	private Map<String, String> comment_map;// 操作结果
	private List<Comment_Company2> comment_company2s;// 评论结果实体类的值
	private List<Exosure_Company> exosure_Companys;// 企业曝光结果集
	private List<News_Company> news_Company;// 媒体报道结果集
	private int commentcontent;
	private int exosuretcontent;
	private int newstcontent;
	private Map<String, Object> mapcomment;
	private Map<String, Object> mapexosure;

	private FrameLayout fl_darkterrace_fl22;// 分享框
	private RelativeLayout rel_darkterrace_rl21;// 分享框
	private LinearLayout rel_darkterrace_rl22;// 分享框
	private Button reg_btn;// 分享到好友
	private Button goto_send_btn;// 分享到朋友圈
	private boolean type = true;
	private TextView tv_count;//vip数目
	private TextView tv_more;//更多
	private LinearLayout ic_vip;//vip列表
	private  GridView gv_content;//图标及编号列表
	private List<Company> companys2;//会员单位
	
	//推送
	private String company_id;
	private int mqtt_push_type;
	private String nature; 
	

	//上传图片返回的结果
		private Handler images_handler=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 0:
					Map<String, String> maps = (Map<String, String>) msg.obj;
					if (maps != null) {
						 Log.i("map---->", maps + "");
						 
						//setExosure(maps);
						PublishComment.publishComment(
							comment_company, maps,handler,
							comment_type);
						 maps.clear();
						image_count = 0;
						setNullBitmap(iv_pic1);
						setNullBitmap(iv_pic2);
						setNullBitmap(iv_pic3);
						setNullBitmap(iv_pic4);
						setNullBitmap(iv_pic5); 
						//iv_add.setVisibility(View.VISIBLE);
						//pb_exosure.setVisibility(View.INVISIBLE);
						//up_value.setText("未选择文件");
						System.gc();
					} else {
						Toast.makeText(DarkTerraceActivity.this, "上传图片失败", 0).show();
					}
					break;

				default:
					break;
				}
			};
		};
		
	// 获取点赞数和点赞功能
	Handler handler2 = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				// 点赞
				String result = (String) msg.obj;
				// Log.i("Test", "点赞--->"+result);
				Map<String, String> map = new JsonUtil()
						.getPraise_Company(result);
				if (map != null) {
					//fl_noattestation.setVisibility(View.GONE);
					DialogUtil.dismissProgressDialog();
					switch (Integer.parseInt(map.get("is_success"))) {
					case 0:
						Toast.makeText(DarkTerraceActivity.this, "您已经成功点赞！",
								Toast.LENGTH_LONG).show();
						//tv_darkterrace_hraad_havapraisenumber.setText(map
								//.get("amount"));
						break;
					case -1:
						Toast.makeText(DarkTerraceActivity.this, "您点赞失败了！",
								Toast.LENGTH_LONG).show();
						break;
					case -2:
						Toast.makeText(DarkTerraceActivity.this,
								"对不起，24小时之内只能一次点赞！", Toast.LENGTH_LONG).show();
						break;
					case -3:
						Toast.makeText(DarkTerraceActivity.this,
								"企业不存在或者被删除", Toast.LENGTH_SHORT).show();
						break;
					default:
						break;
					}
				} else {
					Toast.makeText(getApplicationContext(), "网络连接失败", 0).show();
				}
				break;
			case 1:
				// 获取点赞数
				String result1 = (String) msg.obj;
				// Log.i("Test", "获取点赞数--->"+result1);
				Map<String, String> map1 = new JsonUtil()
						.getPraise_Company(result1);
				if (map1 != null) {
					switch (Integer.parseInt(map1.get("is_success"))) {
					case 0:
						//tv_darkterrace_hraad_havapraisenumber.setText(map1
								//.get("amount"));
						break;
					case -1:

						break;
					case -2:

						break;
					default:
						break;
					}
					//fl_noattestation.setVisibility(View.GONE);
					DialogUtil.dismissProgressDialog();
				} else {
					//fl_noattestation.setVisibility(View.GONE);
					DialogUtil.dismissProgressDialog();
					Toast.makeText(getApplicationContext(), "网络连接失败", 0).show();
				}
				break;
			case 2:
				String string=(String)msg.obj;
				Log.e("Test", string);
				JsonUtil uitl=new JsonUtil();
				companys2 = uitl.getCompanys(string);
				String rowContent222 = uitl.getRowContent22(string);
				if (companys2!=null &&companys2.size()>0) {
					tv_count.setText(rowContent222);
					VipAdapter adapter=new VipAdapter(DarkTerraceActivity.this,companys2);
					gv_content.setAdapter(adapter);
					ic_vip.setVisibility(View.VISIBLE);
					if (Integer.valueOf(rowContent222)>5) {
					tv_more.setVisibility(View.VISIBLE);
					}
				}
				break;
			default:
				break;
			}
		};
	};
	
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				String result0 = msg.obj.toString();
				search_key = et_actionbar_search.getText().toString();
				if ("null".equals(result0)) {
					Toast.makeText(DarkTerraceActivity.this, "对不起，服务器异常！",
							Toast.LENGTH_SHORT).show();
				} else {
					companys = new JsonUtil().getCompanys(result0);
					if (companys != null && companys.size() > 0) {
						// 拿到数据
						if (companys.size() > 1) {
							Intent intent = new Intent(DarkTerraceActivity.this,
									FiltratePageActivity.class);
							intent.putExtra("search_key", search_key);
							startActivity(intent);
						}else {
							company = companys.get(position);
							if (company != null) {
								Intent intent = new Intent();
								intent.putExtra("position", 0);
								intent.putExtra("mqtt_push_type", 2);
								intent.putExtra("search_key", search_key);
								intent.putParcelableArrayListExtra("companys",
										(ArrayList<? extends Parcelable>) companys);
								switch (company.getAuth_level()) {
								// 黑平台
								case "006001":
									intent.setClass(DarkTerraceActivity.this,
											DarkTerraceActivity.class);
									DarkTerraceActivity.this.startActivity(intent);
									break;
								// 未验证
								case "006002":
									intent.setClass(DarkTerraceActivity.this,
											NoAttestationActivity.class);
									DarkTerraceActivity.this.startActivity(intent);
									break;
								// 合规
								case "006003":
									intent.setClass(DarkTerraceActivity.this,
											QualifiedTerraceActivity.class);
									DarkTerraceActivity.this.startActivity(intent);
									DarkTerraceActivity.this.finish();
									break;
								default:
									intent.setClass(DarkTerraceActivity.this,
											NoStorageActivity.class);
									DarkTerraceActivity.this.startActivity(intent);
									break;
								}
							}
						}
					} else {
						Intent intent = new Intent();
						intent.setClass(DarkTerraceActivity.this,
								NoStorageActivity.class);
						intent.putExtra("search_key", search_key);
						DarkTerraceActivity.this.startActivity(intent);
					}
				}
				//fl_noattestation.setVisibility(View.GONE);
				DialogUtil.dismissProgressDialog();
				break;
			// 网友评论返回的json 数据
			case 1:
				String result = msg.obj.toString();
				if ("null".equals(result)) {
					Toast.makeText(DarkTerraceActivity.this, "对不起，服务器异常！",
							Toast.LENGTH_SHORT).show();
				} else {
					comment_map = new JsonUtil().getOperateResult(result);
					int is_success=Integer.parseInt(comment_map.get("is_success")) ;
					if (is_success== 0) {
						Toast.makeText(DarkTerraceActivity.this, "您的评论已经成功提交！",
								Toast.LENGTH_LONG).show();
						
						ic_faces.setVisibility(View.GONE);
						ic_images.setVisibility(View.GONE);
						LayoutParams params = rel_noattestation_rl3.getLayoutParams();
						params.width=LayoutParams.MATCH_PARENT;
						params.height=1;
						rel_noattestation_rl3.setLayoutParams(params);
						
						PublishComment.getDarkPlatform(Util.getLoginType(DarkTerraceActivity.this),
								company.getCompany_id(), user_id, handler, 3,
								1, 10);
					}else if(is_success==-1){
						Toast.makeText(DarkTerraceActivity.this, "您的评论提价失败！",
								Toast.LENGTH_LONG).show();
						DialogUtil.dismissProgressDialog();
					}else if(is_success==-6){
						Toast.makeText(DarkTerraceActivity.this, "该企业不存在或者已删除！",
								Toast.LENGTH_LONG).show();
						DialogUtil.dismissProgressDialog();
					}else {
						Toast.makeText(DarkTerraceActivity.this, "该评论不存在或者已删除！",
								Toast.LENGTH_LONG).show();
						DialogUtil.dismissProgressDialog();
					}
				}
				et_darkterrace_comment.setText("");
				inputmanger.hideSoftInputFromWindow(view2.getWindowToken(), 0);
				fl_darkterrace_fl1.setVisibility(View.GONE);
				break;
			// 加黑返回的json 数据
			case 2:
				String result2 = msg.obj.toString();
				if ("null".equals(result2)) {
					Toast.makeText(DarkTerraceActivity.this, "对不起，服务器异常！",
							Toast.LENGTH_SHORT).show();
				} else { 
					comment_map = new JsonUtil().getOperateResult(result2);
					switch (Integer.parseInt(comment_map.get("is_success"))) {
					case 0:
						Toast.makeText(DarkTerraceActivity.this, "您已经成功加黑！",
								Toast.LENGTH_LONG).show();
						///////////////////////////// 
						 String replace = tv_darkterrace_hraad_havadarknumber.getText().toString().replace("(", "").replace(")", "");
							tv_darkterrace_hraad_havadarknumber.setText("("+(Integer.valueOf(replace)+1)
									+ ")");
						
						break;
					case -1:
						Toast.makeText(DarkTerraceActivity.this, "您加黑失败了！",
								Toast.LENGTH_LONG).show();
						break;
					case -2:
						Toast.makeText(DarkTerraceActivity.this,
								"对不起，24小时之内只能一次加黑！", Toast.LENGTH_LONG).show();
						break;
					case -3:
						Toast.makeText(DarkTerraceActivity.this,
								"企业不存在或者被删除！", Toast.LENGTH_LONG).show();
						break;
					}
				}
				//fl_noattestation.setVisibility(View.GONE);
				DialogUtil.dismissProgressDialog();
				break;
			// 企业评论返回的json 数据
			case 3:
				String reult3 = msg.obj.toString();
				if ("null".equals(reult3)) {
					Toast.makeText(DarkTerraceActivity.this, "对不起，服务器异常！",
							Toast.LENGTH_SHORT).show();
				} else {
					comment_company2s = new JsonUtil()
							.getComment_Company2s(1,reult3);
					mapcomment = new JsonUtil().getComment_Company2s2(1,reult3);
					commentcontent = new JsonUtil().getRowContent(reult3);					
					//tv_darkterrace_comment.setText("网友评论(" + commentcontent
							//+ ")");
					adapterComment = new AdapterComment(
							DarkTerraceActivity.this, comment_company2s,
							mapcomment, company.getCompany_name(), 1);
					adapterComment.setHandler(handler);
					adapterComment.setComment_Number(commentcontent);
					adapterComment.setUser_id(user_id);
					lv_darkterrace.setAdapter(adapterComment);
					//tv_count1.setText(commentcontent+"");
				}
				et_actionbar_search.setVisibility(View.VISIBLE);
				et_actionbar_search.setText(search_key);
				if (comment_company2s != null && comment_company2s.size() > 0) {
					lv_darkterrace.removeFooterView(DensityUtil.dip2px(
							DarkTerraceActivity.this, 60));
					setLayoutParams(1);
					if (comment_company2s.size()<10) {
						lv_darkterrace.removeFooterView(1);
						}
				} else {
					lv_darkterrace.removeFooterView(1);
					tv_darktext.setText("还没有人评论过此公司哦，赶紧去吐槽吧！");
					setLayoutParams(0);
				}
				//fl_noattestation.setVisibility(View.GONE);
				DialogUtil.dismissProgressDialog();
				break;
			// 刷新数据
			case 4:
				String result4 = msg.obj.toString();
				if ("null".equals(result4)) {
					Toast.makeText(DarkTerraceActivity.this, "对不起，服务器异常！",
							Toast.LENGTH_SHORT).show();
				} else {
					comment_company2s = new JsonUtil()
							.getComment_Company2s(1,result4);
					mapcomment = new JsonUtil().getComment_Company2s2(1,result4);
					adapterComment = new AdapterComment(
							DarkTerraceActivity.this, comment_company2s,
							mapcomment, company.getCompany_name(), 1);
					commentcontent = new JsonUtil().getRowContent(result4);
					//tv_darkterrace_comment.setText("网友评论(" + commentcontent
							//+ ")");
					adapterComment.setHandler(handler);
					adapterComment.setComment_Number(commentcontent);
					adapterComment.setUser_id(user_id);
					lv_darkterrace.setAdapter(adapterComment);
					//tv_count1.setText(commentcontent+"");
				}
				lv_darkterrace.stopRefresh();
				lv_darkterrace.stopLoadMore();
				if (comment_company2s != null && comment_company2s.size() > 0) {
					lv_darkterrace.removeFooterView(DensityUtil.dip2px(
							DarkTerraceActivity.this, 60));
					setLayoutParams(1);
					if (comment_company2s.size()<10) {
						lv_darkterrace.removeFooterView(1);
						}
				} else {
					lv_darkterrace.removeFooterView(1);
					tv_darktext.setText("还没有人评论过此公司哦，赶紧去吐槽吧！");
					setLayoutParams(0);
				}
				break;
			// 添加更多
			case 5:
				String result5 = msg.obj.toString();
				if ("null".equals(result5)) {
					lv_darkterrace.removeFooterView(1);
					Toast.makeText(DarkTerraceActivity.this, "对不起，服务器异常！",
							Toast.LENGTH_SHORT).show();
				} else {
					List<Comment_Company2> comment_company2slLsit = new JsonUtil()
							.getComment_Company2s(1,result5);
					Map<String, Object> map = new JsonUtil()
							.getComment_Company2s2(1,result5);
					adapterComment.setHandler(handler);
					adapterComment.setUser_id(user_id);

					if (comment_company2slLsit.size() > 0
							&& comment_company2slLsit != null) {
						comment_company2s.addAll(comment_company2slLsit);
						if (adapterComment != null) {
							mapcomment.putAll(map);
							adapterComment.setMap(mapcomment);
							adapterComment
									.setComment_Company2s(comment_company2s);
							adapterComment.notifyDataSetChanged();
						} else {
							adapterComment = new AdapterComment(
									DarkTerraceActivity.this,
									comment_company2s, map,
									company.getCompany_name(), 1);
							lv_darkterrace.setAdapter(adapterComment);
						}
						adapterComment.setHandler(handler);
						adapterComment.setUser_id(user_id);
					} else {
						lv_darkterrace.removeFooterView(1);
						//lv_darkterrace.setPullLoadEnable(false);
						Toast.makeText(DarkTerraceActivity.this, "没有更多数据",
								Toast.LENGTH_SHORT).show();
					}
				}
				lv_darkterrace.stopLoadMore();
				break;
			// 顶评论的结果值
			case 6:
				String result6 = msg.obj.toString();
				if ("null".equals(result6)) {
					Toast.makeText(DarkTerraceActivity.this, "对不起，服务器异常！",
							Toast.LENGTH_SHORT).show();
				} else {
					comment_map = new JsonUtil().getOperateResult(result6);
					switch (Integer.parseInt(comment_map.get("is_success"))) {
					case 0:
						int pso = adapterComment.getPostions();
						if (pso != -1) {
							comment_company2s.get(pso)
									.setTop_num(
											comment_company2s.get(pso)
													.getTop_num() + 1);
							adapterComment
									.setComment_Company2s(comment_company2s);
							adapterComment.notifyDataSetChanged();
						}
						Toast.makeText(DarkTerraceActivity.this, "顶成功！",
								Toast.LENGTH_SHORT).show();
						break;
					case -1:
						Toast.makeText(DarkTerraceActivity.this, "操作失败！",
								Toast.LENGTH_SHORT).show();
						break;
					case -2:
						Toast.makeText(DarkTerraceActivity.this,
								"对不起，24小时之内只能顶一次！", Toast.LENGTH_SHORT).show();
						break;
					}
				}
				break;
			// 网友曝光数据
			case 7:
				String result7 = msg.obj.toString();
				if ("null".equals(result7)) {
					Toast.makeText(DarkTerraceActivity.this, "对不起，服务器异常！",
							Toast.LENGTH_SHORT).show();
				} else {
					Log.i("jay_test", "网友曝光拿到的数据------>"+result7);
					exosure_Companys = new JsonUtil()
							.getExosure_Companys(result7);
					exosuretcontent = new JsonUtil().getRowContent(result7);
					//tv_darkterrace_exosure.setText("网友曝光(" + exosuretcontent
							//+ ")");
					mapexosure = new JsonUtil()
							.getgetExosure_Company2s(result7);
					adapterExosure = new AdapterExosure(
							DarkTerraceActivity.this, exosure_Companys,
							mapexosure, company.getCompany_name(), 1);
					adapterExosure.setHandler(handler);
					adapterExosure.setUser_id(user_id);
					adapterExosure.setComment_Number(exosuretcontent);
					adapterExosure.setResult(result7);
					Log.i("jay_test", "网友曝光数据拿到的次数----->"+exosuretcontent);
					lv_darkterrace.setAdapter(adapterExosure);
					//tv_count1.setText(exosuretcontent+"");
				}
				//fl_noattestation.setVisibility(View.GONE);
				DialogUtil.dismissProgressDialog();
				if (exosure_Companys != null && exosure_Companys.size() > 0) {
					lv_darkterrace.removeFooterView(DensityUtil.dip2px(
							DarkTerraceActivity.this, 60));
					setLayoutParams(1);
					if (exosure_Companys.size()<10) {
						lv_darkterrace.removeFooterView(1);
						}
				} else {
					lv_darkterrace.removeFooterView(1);
					//tv_darktext.setText("还没有用户曝光过此公司呢，我要去曝光一个!");
					setLayoutParams(0);
				}
				break;
			// 网友曝光数据刷新
			case 8:
				String result8 = msg.obj.toString();
				if ("null".equals(result8)) {
					Toast.makeText(DarkTerraceActivity.this, "对不起，服务器异常！",
							Toast.LENGTH_SHORT).show();
				} else {
					exosure_Companys = new JsonUtil()
							.getExosure_Companys(result8);
					exosuretcontent = new JsonUtil().getRowContent(result8);
					mapexosure = new JsonUtil()
							.getgetExosure_Company2s(result8);
					//tv_darkterrace_exosure.setText("网友曝光(" + exosuretcontent
							//+ ")");
					adapterExosure = new AdapterExosure(
							DarkTerraceActivity.this, exosure_Companys,
							mapexosure, company.getCompany_name(), 1);
					adapterExosure.setHandler(handler);
					adapterExosure.setUser_id(user_id);
					adapterExosure.setComment_Number(exosuretcontent);
					Log.i("jay_test", "网友曝光数据刷新拿到的次数----->"+exosuretcontent);
					lv_darkterrace.setAdapter(adapterExosure);
					//tv_count1.setText(exosuretcontent+"");
				}
				lv_darkterrace.stopRefresh();
				lv_darkterrace.stopLoadMore();
				if (exosure_Companys != null && exosure_Companys.size() > 0) {
					lv_darkterrace.removeFooterView(DensityUtil.dip2px(
							DarkTerraceActivity.this, 60));
					setLayoutParams(1);
					if (exosure_Companys.size()<10) {
						lv_darkterrace.removeFooterView(1);
						}
				} else {
					lv_darkterrace.removeFooterView(1);
					tv_darktext.setText("还没有用户曝光过此公司呢，我要去曝光一个!");
					setLayoutParams(0);
				}
				break;
			// 网友曝光数据添加更多
			case 9:
				String result9 = msg.obj.toString();
				if ("null".equals(result9)) {
					lv_darkterrace.removeFooterView(1);
					Toast.makeText(DarkTerraceActivity.this, "对不起，服务器异常！",
							Toast.LENGTH_SHORT).show();
				} else {
					adapterExosure.setHandler(handler);
					adapterExosure.setUser_id(user_id);
					List<Exosure_Company> exosure_Companies = new JsonUtil()
							.getExosure_Companys(result9);
					Map<String, Object> map1 = new JsonUtil()
							.getgetExosure_Company2s(result9);
					if (exosure_Companies != null
							&& exosure_Companies.size() > 0) {
						exosure_Companys.addAll(exosure_Companies);
						if (adapterExosure == null) {
							adapterExosure = new AdapterExosure(
									DarkTerraceActivity.this, exosure_Companys,
									map1, company.getCompany_name(), 1);
							lv_darkterrace.setAdapter(adapterExosure);
						} else {
							adapterExosure
									.setExosure_Companys(exosure_Companys);
							adapterExosure.setMap(mapexosure);
							adapterExosure.notifyDataSetChanged();
						}
					} else {
						Toast.makeText(DarkTerraceActivity.this, "没有更多数据",
								Toast.LENGTH_SHORT).show();
						lv_darkterrace.removeFooterView(1);
						//lv_darkterrace.setPullLoadEnable(false);
					}
				}
				lv_darkterrace.stopLoadMore();
				break;
			case 10:
				String result10 = msg.obj.toString();
				if ("null".equals(result10)) {
					Toast.makeText(DarkTerraceActivity.this, "对不起，服务器异常！",
							Toast.LENGTH_SHORT).show();
				} else {
					comment_map = new JsonUtil().getOperateResult(result10);
					switch (Integer.parseInt(comment_map.get("is_success"))) {
					case 0:
						int pso = adapterExosure.getPostions();
						if (pso != -1) {
							exosure_Companys.get(pso).setTop_num(
									exosure_Companys.get(pso).getTop_num() + 1);
							adapterExosure
									.setExosure_Companys(exosure_Companys);
							adapterExosure.notifyDataSetChanged();
						}
						Toast.makeText(DarkTerraceActivity.this, "顶成功！",
								Toast.LENGTH_SHORT).show();
						break;
					case -1:
						Toast.makeText(DarkTerraceActivity.this, "操作失败！",
								Toast.LENGTH_SHORT).show();
						break;
					case -2:
						Toast.makeText(DarkTerraceActivity.this,
								"对不起，24小时之内只能顶一次！", Toast.LENGTH_SHORT).show();
						break;
					}
				}
				break;
			// 媒体报道数据
			case 11:
				String result11 = msg.obj.toString();
				if ("null".equals(result11)) {
					Toast.makeText(DarkTerraceActivity.this, "对不起，服务器异常！",
							Toast.LENGTH_SHORT).show();
				} else {
					news_Company = new JsonUtil().getNews_Company(result11);
					newstcontent = new JsonUtil().getRowContent(result11);
					//tv_darkterrace_media.setText("媒体报道(" + newstcontent + ")");
					adapterNews = new AdapterNews(DarkTerraceActivity.this,
							news_Company);
					//adapterNews.set
					adapterNews.setComment_Number(newstcontent);
					lv_darkterrace.setAdapter(adapterNews);
					//tv_count1.setText(newstcontent+"");
					//if (news_Company.size()<10) {
						//lv_darkterrace.removeFooterView(1);
						//lv_darkterrace.setPullLoadEnable(false);
					//}
				}
				//fl_noattestation.setVisibility(View.GONE);\
				DialogUtil.dismissProgressDialog();
				if (news_Company != null && news_Company.size() > 0) {
					lv_darkterrace.removeFooterView(DensityUtil.dip2px(
							DarkTerraceActivity.this, 60));
					setLayoutParams(1);
					if (news_Company.size()<10) {
						lv_darkterrace.removeFooterView(1);
						}
				} else {
					lv_darkterrace.removeFooterView(1);
					//tv_darktext.setText("哎呦，还不错哦，还没有媒体报告过这家公司！");
					setLayoutParams(0);
				}
				break;
			// 刷新媒体报道数据
			case 12:
				String result12 = msg.obj.toString();
				if ("null".equals(result12)) {
					Toast.makeText(DarkTerraceActivity.this, "对不起，服务器异常！",
							Toast.LENGTH_SHORT).show();
				} else {
					news_Company = new JsonUtil().getNews_Company(result12);
					newstcontent = new JsonUtil().getRowContent(result12);
					//tv_darkterrace_media.setText("媒体报道(" + newstcontent + ")");
					adapterNews = new AdapterNews(DarkTerraceActivity.this,
							news_Company);
					adapterNews.setComment_Number(newstcontent);
					lv_darkterrace.setAdapter(adapterNews);
					//tv_count1.setText(newstcontent+"");
				}
				lv_darkterrace.stopRefresh();
				lv_darkterrace.stopLoadMore();
				if (news_Company != null && news_Company.size() > 0) {
					lv_darkterrace.removeFooterView(DensityUtil.dip2px(
							DarkTerraceActivity.this, 60));
					setLayoutParams(1);
					if (news_Company.size()<10) {
						lv_darkterrace.removeFooterView(1);
						}
				} else {
					lv_darkterrace.removeFooterView(1);
					tv_darktext.setText("哎呦，还不错哦，还没有媒体报告过这家公司！");
					setLayoutParams(0);
				}
				break;
			// 添加更多媒体报道数据
			case 13:
				String result13 = msg.obj.toString();
				if ("null".equals(result13)) {
					lv_darkterrace.removeFooterView(1);
					Toast.makeText(DarkTerraceActivity.this, "对不起，服务器异常！",
							Toast.LENGTH_SHORT).show();
				} else {
					List<News_Company> news_Companys = new JsonUtil()
							.getNews_Company(result13);
					if (news_Companys != null && news_Companys.size() > 0) {
						news_Company.addAll(news_Companys);
						adapterNews.setNews_Companys(news_Company);
						adapterNews.notifyDataSetChanged();
					} else {
						Toast.makeText(DarkTerraceActivity.this, "没有更多数据",
								Toast.LENGTH_SHORT).show();
						lv_darkterrace.removeFooterView(1);
						//lv_darkterrace.setPullLoadEnable(false);
					}
				}
				lv_darkterrace.stopLoadMore();
				break;
			// 网友曝光的条数
			case 14:
				String result14 = msg.obj.toString();
				
				if ("null".equals(result14)) {
					Toast.makeText(DarkTerraceActivity.this, "对不起，服务器异常！",
							Toast.LENGTH_SHORT).show();
				} else {
					exosure_Companys = new JsonUtil()
							.getExosure_Companys(result14);
					exosuretcontent = new JsonUtil().getRowContent(result14);
					//tv_darkterrace_exosure.setText("网友曝光(" + exosuretcontent
							//+ ")");
					mapexosure = new JsonUtil()
							.getgetExosure_Company2s(result14);
					adapterExosure = new AdapterExosure(
							DarkTerraceActivity.this, exosure_Companys,
							mapexosure, company.getCompany_name(), 1);
					adapterExosure.setHandler(handler);
					adapterExosure.setComment_Number(exosuretcontent);
					adapterExosure.setUser_id(user_id);
					//tv_count1.setText(exosuretcontent+"");
				}
				break;
			// 媒体报道的条数
			case 15:
				String result15 = msg.obj.toString();
				if ("null".equals(result15)) {
					Toast.makeText(DarkTerraceActivity.this, "对不起，服务器异常！",
							Toast.LENGTH_SHORT).show();
				} else {
					news_Company = new JsonUtil().getNews_Company(result15);
					newstcontent = new JsonUtil().getRowContent(result15);
					//tv_darkterrace_media.setText("媒体报道(" + newstcontent + ")");
					adapterNews = new AdapterNews(DarkTerraceActivity.this,
							news_Company);
					adapterNews.setComment_Number(newstcontent);
					
					//if (news_Company.size()<10) {
						//lv_darkterrace.removeFooterView(1);
						//lv_darkterrace.setPullLoadEnable(false);}
					
					//tv_count1.setText(newstcontent+"");
				}
				break;
			// 刷新的企业信息
			case 16:
				String result16 = msg.obj.toString();
				if ("null".equals(result16)) {
					Toast.makeText(DarkTerraceActivity.this, "对不起，服务器异常！",
							Toast.LENGTH_SHORT).show();
				} else {
					List<Company> companys2 = new JsonUtil()
							.getCompanys2(result16);
					if (companys2 != null && companys2.size() > 0) {
						companys = companys2;
						company = companys2.get(0);
						setCompany();
					}
				}
				break;
			// 跳转到企业信息
			case 17:
				String result17 = msg.obj.toString();
				if ("null".equals(result17)) {
					Toast.makeText(DarkTerraceActivity.this, "对不起，服务器异常！",
							Toast.LENGTH_SHORT).show();
				} else {
					List<Company> companys3 = new JsonUtil()
							.getCompanys2(result17);
					if (companys3 != null && companys3.size() > 0) {
						// 拿到数据
						Company company2 = companys3.get(0);
						if (company != null) {
							Intent intent = new Intent();
							intent.putExtra("search_key",
									company2.getCompany_name());
							intent.putExtra("position", 0);
							intent.putExtra("mqtt_push_type", 2);
							intent.putParcelableArrayListExtra("companys",
									(ArrayList<? extends Parcelable>) companys3);
							switch (company.getAuth_level()) {
							// 黑平台
							case "006001":
								intent.setClass(DarkTerraceActivity.this,
										DarkTerraceActivity.class);
								DarkTerraceActivity.this.startActivity(intent);
								break;
							// 未验证
							case "006002":
								intent.setClass(DarkTerraceActivity.this,
										NoAttestationActivity.class);
								DarkTerraceActivity.this.startActivity(intent);
								break;
							// 合规
							case "006003":
								intent.setClass(DarkTerraceActivity.this,
										QualifiedTerraceActivity.class);
								DarkTerraceActivity.this.startActivity(intent);
								break;
							default:
								intent.setClass(DarkTerraceActivity.this,
										NoStorageActivity.class);
								DarkTerraceActivity.this.startActivity(intent);
								break;
							}
						}
					} else {
						Intent intent = new Intent();
						intent.putExtra("search_key", company.getCompany_name());
						intent.setClass(DarkTerraceActivity.this,
								NoStorageActivity.class);
						DarkTerraceActivity.this.startActivity(intent);
					}
				}
				//fl_noattestation.setVisibility(View.GONE);
				DialogUtil.dismissProgressDialog();
				break;
			//推送的数据拉取
			case 18:
				String result_push=(String)msg.obj;
				if(result_push != null && !result_push.isEmpty()){
					company = new JsonUtil().getOne_Companys(result_push);
					if(company != null){
						search_key = company.getCompany_name();	
						et_actionbar_search.setText(search_key);
						setCompany();
						//对企业进行关注
						initData(company);
						getCommentThread();
					}
				}else{
					Toast.makeText(DarkTerraceActivity.this, "对不起，服务器异常！",
							Toast.LENGTH_SHORT).show();
				}
				break;
			}
		};
	};
	private LinearLayout rel_noattestation_rl333;
	private ImageView iv_darkterrace_comment_icon1;
	private RelativeLayout ic_faces;
	private FrameLayout ic_images;
	private ImageView iv_add;
	private ImageView iv_pic1;
	private ImageView iv_pic2;
	private ImageView iv_pic3;
	private ImageView iv_pic4;
	private ImageView iv_pic5;
	private int image_count = 0;//相册图片显示的坐标
	private List<String> image_path = new ArrayList<String>();// 存储的图片路径集合
	private boolean Delete_type=true;//是否删除图片的状态
	private RelativeLayout rel_noattestation_rl3;
	private String Image_url;
	private TextView tv_count1;
	public static DarkTerraceActivity dark_page;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dark_terrace);
		dark_page=this;
		actionBar = this.getActionBar();
		// 设置actionbar
		new HomePageController().setActionbar(LayoutInflater.from(this)
				.inflate(R.layout.activity_search_result_actionbar, null),
				actionBar);		
		
		initActionBar();
		initView();

		FaceConversionUtil.getInstace();
		emojis = FaceConversionUtil.emojiLists;
		onCreate();
		AppManager.getInstance().addActivity(DarkTerraceActivity.this);
	}

	private void SetLogin(){
		startActivity(new Intent(DarkTerraceActivity.this,MineFragment_Login.class));		
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			if (fl_darkterrace_fl1 != null
					&& fl_darkterrace_fl1.getVisibility() == View.VISIBLE) {
				fl_darkterrace_fl1.setVisibility(View.GONE);
			} else if (fl_darkterrace_fl22 != null
					&& fl_darkterrace_fl22.getVisibility() == View.VISIBLE) {
				fl_darkterrace_fl22.setVisibility(View.GONE);
			} else {
				DarkTerraceActivity.this.finish();
			}
		}
		return false;
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (All_fragment.change_type1) {
			PublishComment.getDarkPlatform(Util.getLoginType(DarkTerraceActivity.this),
					company.getCompany_id(), user_id, handler, 4,
					1, 10);
			PublishSearch.getCommpany(company.getCompany_id(),
					handler, 16);
			All_fragment.change_type1=false;
		}else if(All_fragment.change_type2){
			PublishExposal.getCompanyExposal(
					company.getCompany_id(), user_id, handler, 8,
					1, 10);
			PublishSearch.getCommpany(company.getCompany_id(),
					handler, 16);
			All_fragment.change_type1=false;
		}
	}

	// 初始化分享
	private void setShare() {
		// fl_darkterrace_fl22 = (FrameLayout)
		// this.findViewById(R.id.fl_darkterrace_fl22);
		// rel_darkterrace_rl21 = (RelativeLayout)
		// this.findViewById(R.id.rel_darkterrace_rl21);
		// rel_darkterrace_rl22 = (LinearLayout)
		// this.findViewById(R.id.rel_darkterrace_rl22);
		// reg_btn = (Button) this.findViewById(R.id.reg_btn);
		// goto_send_btn = (Button) this.findViewById(R.id.goto_send_btn);

		// 选择分享
		iv_darkterrace_share1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// fl_darkterrace_fl22.setVisibility(View.VISIBLE);
				// 添加新浪SSO授权
				// mController.getConfig().setSsoHandler(new SinaSsoHandler());
				//
				// UMImage urlImage = new UMImage(DarkTerraceActivity.this,
				// "http://www.umeng.com/images/pic/social/integrated_3.png");
				// WeiXinShareContent weixinContent = new WeiXinShareContent();
				// weixinContent.setShareContent("社交分享功能-微信。http://www.jd.com   呵呵呵");
				// weixinContent.setTitle("友盟社会化分享组件-微信    呵呵呵");
				// weixinContent.setTargetUrl("http://www.baidu.com");
				// weixinContent.setShareMedia(urlImage);
				// mController.setShareMedia(weixinContent);
				//
				// // 设置朋友圈分享的内容
				// CircleShareContent circleMedia = new CircleShareContent();
				// circleMedia.setShareContent("社交分享功能-朋友圈。http://www.baidu.com");
				// circleMedia.setTitle("友盟社会化分享组件-朋友圈");
				// circleMedia.setShareMedia(urlImage);
				// // circleMedia.setShareMedia(uMusic);
				// // circleMedia.setShareMedia(video);
				// circleMedia.setTargetUrl("http://www.jd.com");
				// mController.setShareMedia(circleMedia);
				//
				// QZoneShareContent qzone = new QZoneShareContent();
				// qzone.setShareContent("share test");
				// qzone.setTargetUrl("http://www.umeng.com");
				// qzone.setTitle("QZone title");
				// qzone.setShareMedia(urlImage);
				// // qzone.setShareMedia(uMusic);
				// mController.setShareMedia(qzone);
				//
				// SinaShareContent sinaContent = new SinaShareContent();
				// sinaContent.setShareContent("来自友盟社会化组件（SDK）让移动应用快速整合社交分享功能-新浪微博。http://www.umeng.com/social");
				// sinaContent.setTargetUrl("http://www.umeng.com");
				// sinaContent.setTitle("QZone title");
				// sinaContent.setShareMedia(urlImage);
				// mController.setShareMedia(sinaContent);
				// mController.getConfig().setPlatforms(SHARE_MEDIA.WEIXIN,
				// SHARE_MEDIA.WEIXIN_CIRCLE,SHARE_MEDIA.QZONE,SHARE_MEDIA.SINA);
				// mController.openShare(DarkTerraceActivity.this, false);
				
				
//				StringBuffer sb = new StringBuffer();
//				String title = "搜黑平台查询_全国最大的查询曝光平台_搜黑";
//				Bitmap bitmap = WeiXinShareController
//						.myShot(DarkTerraceActivity.this);
//				sb.append("黑平台：");
//				sb.append(company.getCompany_name());
//				sb.append(".");
//				UMShareManager.UMSendShare(DarkTerraceActivity.this,
//						mController, title,
//						PhpUrl.SouheiSearch1 + company.getCompany_id()
//								+ "/p/1.html", bitmap, sb.toString());
			}
		});
		// fl_darkterrace_fl22.setOnClickListener(new View.OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// }
		// });
		// //隐藏分享
		// rel_darkterrace_rl21.setOnClickListener(new View.OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// fl_darkterrace_fl22.setVisibility(View.GONE);
		// }
		// });
		//
		// reg_btn.setOnClickListener(new View.OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// fl_darkterrace_fl22.setVisibility(View.GONE);
		// StringBuffer sb = new StringBuffer();
		// String title = "搜黑平台查询_全国最大的查询曝光平台_搜黑";
		// Bitmap bitmap =
		// WeiXinShareController.myShot(DarkTerraceActivity.this);
		// sb.append("黑平台：");
		// sb.append(company.getCompany_name());
		// sb.append(".");
		// // WeiXinShareController.sendToWeiXin(DarkTerraceActivity.this,
		// WeixinShareManager.WEIXIN_SHARE_TYPE_TALK,
		// // PhpUrl.SouheiSearch1+company.getCompany_id()+"/p/1.html",
		// sb.toString(), title, bitmap);
		// }
		// });
		// goto_send_btn.setOnClickListener(new View.OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// fl_darkterrace_fl22.setVisibility(View.GONE);
		// StringBuffer sb = new StringBuffer();
		// String title = "搜黑平台查询_全国最大的查询曝光平台_搜黑";
		// Bitmap bitmap =
		// WeiXinShareController.myShot(DarkTerraceActivity.this);
		// sb.append("未认证平台：");
		// sb.append(company.getCompany_name());
		// sb.append(".");
		// // WeiXinShareController.sendToWeiXin(DarkTerraceActivity.this,
		// WeixinShareManager.WEIXIN_SHARE_TYPE_FRENDS,
		// // PhpUrl.SouheiSearch1+company.getCompany_id()+"/p/1.html",
		// sb.toString(), title, bitmap);
		// }
		// });
	}

	// 初始化数据
	private void initView() {
		preferences2 = this.getSharedPreferences("Login_UserInfo",
				Context.MODE_PRIVATE);
		user_id = preferences2.getLong("user_id", -1);
		async = new AsyncBitmapLoader(this);
		manager = (ConnectivityManager) this
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		inputmanger = (InputMethodManager) this
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		view2 = getWindow().peekDecorView();

		lv_darkterrace = (XListView) this.findViewById(R.id.lv_darkterrace);
		tv_darktext = (TextView) this.findViewById(R.id.tv_darktext);

		view = LayoutInflater.from(this).inflate(
				R.layout.activity_darkterrace_hread, null);
		
		// vip列表
				ic_vip = (LinearLayout) view.findViewById(R.id.ic_vip);
				tv_count = (TextView) view.findViewById(R.id.tv_count);
				tv_more = (TextView) view.findViewById(R.id.tv_more);
				gv_content = (GridView) view.findViewById(R.id.gv_content);
				gv_content.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						List<Company> list = new ArrayList<>();
						list.add(companys2.get(position));
						Util.JumpToResultPage(DarkTerraceActivity.this, list, 0);
					}
				});
				tv_more.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent = new Intent();
						intent.putExtra("company_id", company.getCompany_id());
						intent.setClass(getApplicationContext(), UnitVipActivity.class);
						startActivity(intent);
					}
				});
		
		// 尾布局
		//View footview = LinearLayout.inflate(DarkTerraceActivity.this,
				//R.layout.xlistview_footerview, null);
		// 尾布局主题个数
		//tv_count1 = (TextView) footview.findViewById(R.id.tv_count1);
		view.setFocusable(false);
		lv_darkterrace.addHeaderView(view);
		//lv_darkterrace.addFooterView(footview);
		lv_darkterrace.setPullLoadEnable(true);
		lv_darkterrace.setFocusable(false);
		lv_darkterrace.setPullRefreshEnable(true);
		initComment();
		setTerrace();

		iv_darkterrace_adddark = (RelativeLayout) view
				.findViewById(R.id.iv_darkterrace_adddark);
		tv_darkterrace_hraad_havadarknumber = (TextView) view
				.findViewById(R.id.tv_darkterrace_hraad_havadarknumber);
		//点赞数		
		//tv_darkterrace_hraad_havapraisenumber = (TextView) findViewById(R.id.tv_darkterrace_hraad_havapraisenumber);
		tv_darkterrace_hraad_commentnumber = (TextView) view
				.findViewById(R.id.tv_darkterrace_hraad_commentnumber);
		tv_darkterrace_hraad_exosure = (TextView) view
				.findViewById(R.id.tv_darkterrace_hraad_exosurenumber);
		iv_darkterrace_terraceicon = (ImageView) view
				.findViewById(R.id.iv_darkterrace_terraceicon);
		tv_darkterrace_terracename = (TextView) view
				.findViewById(R.id.tv_darkterrace_terracename);
		tv_darkterrace_terracename2 = (TextView) view
				.findViewById(R.id.tv_darkterrace_terracename2);
		tv_darkterrace_agent_terrace = (TextView) view
				.findViewById(R.id.tv_darkterrace_agent_terrace);
		tv_darkterrace_sito_officiale = (TextView) view
				.findViewById(R.id.tv_darkterrace_sito_officiale);
		tv_darkterrace_record_officiale = (TextView) view
				.findViewById(R.id.tv_darkterrace_record_officiale);
		tv_darkterrace_phone = (TextView) view
				.findViewById(R.id.tv_darkterrace_phone);
		tv_darkterrace_registered_address = (TextView) view
				.findViewById(R.id.tv_darkterrace_registered_address);
		tv_darkterrace_mem_sn = (TextView) view
				.findViewById(R.id.tv_darkterrace_mem_sn);
		tv_darkterrace_regulators = (TextView) view
				.findViewById(R.id.tv_darkterrace_regulators);

		//fl_noattestation = (FrameLayout) findViewById(R.id.fl_noattestation);
		rel_darkterrace_rl1 = (RelativeLayout) this
				.findViewById(R.id.rel_darkterrace_rl1);
		fl_darkterrace_fl1 = (FrameLayout) this
				.findViewById(R.id.fl_darkterrace_fl1);
		iv_darkterrace_share1 = (ImageView) this
				.findViewById(R.id.iv_darkterrace_share1);
		iv_darkterrace_speak = (RelativeLayout) this
				.findViewById(R.id.iv_darkterrace_speak);
		rb_darkterrace_1 = (RadioButton) this
				.findViewById(R.id.rb_darkterrace_1);
		rb_darkterrace_2 = (RadioButton) this
				.findViewById(R.id.rb_darkterrace_2);
		rb_darkterrace_3 = (RadioButton) this
				.findViewById(R.id.rb_darkterrace_3);
		et_darkterrace_comment = (EditText) this
				.findViewById(R.id.et_darkterrace_comment);
		cb_noattestatiob_anon = (CheckBox) this
				.findViewById(R.id.cb_noattestatiob_anon);
		iv_darkterrace_comments = (ImageView) this
				.findViewById(R.id.iv_darkterrace_comments);
		rg_noattextation_commenttype = (RadioGroup) this
				.findViewById(R.id.rg_noattextation_commenttype);
		rel_noattestation_rl3 = (RelativeLayout) this
				.findViewById(R.id.rel_noattestation_rl3);
		
		//输入框背景
		LinearLayout lin_darkterrace_lin1 = (LinearLayout) findViewById(R.id.lin_darkterrace_lin1);
		
		//点赞		
		//rl_left = (RelativeLayout) findViewById(R.id.rl_left);
		//曝光
		RelativeLayout rl_right = (RelativeLayout) findViewById(R.id.rl_right);

		// 表情
		iv_darkterrace_comment_icon1 = (ImageView) this
				.findViewById(R.id.iv_darkterrace_comment_icon1);
		// 表情布局
		ic_faces = (RelativeLayout) findViewById(R.id.ic_faces);
		// 上传图片
		ImageView iv_darkterrace_comment_icon2 = (ImageView) findViewById(R.id.iv_darkterrace_comment_icon2);
		// 上传图片布局
				ic_images = (FrameLayout) findViewById(R.id.ic_images);
				//上传图片里的添加图片按钮		
				iv_add = (ImageView) findViewById(R.id.iv_add);
				// 图片集合
						iv_pic1 = (ImageView) findViewById(R.id.iv_pic1);
						iv_pic2 = (ImageView) findViewById(R.id.iv_pic2);
						iv_pic3 = (ImageView) findViewById(R.id.iv_pic3);
						iv_pic4 = (ImageView) findViewById(R.id.iv_pic4);
				        iv_pic5 = (ImageView) findViewById(R.id.iv_pic5);
		
		getIntents();
		addDarkCompany();
		//addPraiseCompany();
		setSpeak();
		setShare();				
		
		rl_right.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				goExosure();				
			}
		});
		
		// 表情事件
		iv_darkterrace_comment_icon1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (ic_faces.getVisibility()==View.GONE) {
					// 收起软键盘
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(
							et_darkterrace_comment.getWindowToken(),
							0);
					ic_faces.setVisibility(View.VISIBLE);
						ic_images.setVisibility(View.GONE);
					// 打开布局
					LayoutParams lp = rel_noattestation_rl3
							.getLayoutParams();
					lp.width = LayoutParams.MATCH_PARENT;
					lp.height = LayoutParams.WRAP_CONTENT;
					rel_noattestation_rl3.setLayoutParams(lp);
				} else if(ic_faces.getVisibility()==View.VISIBLE) {						
						ic_faces.setVisibility(View.GONE);
						ic_images.setVisibility(View.GONE);							
					LayoutParams lp = rel_noattestation_rl3
							.getLayoutParams();
					lp.width = LayoutParams.MATCH_PARENT;
					lp.height = 1;
					rel_noattestation_rl3.setLayoutParams(lp);
				}
			}
		});

		// 显示上传图片布局
		iv_darkterrace_comment_icon2
						.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								if (ic_images.getVisibility()==View.GONE) {
									// 收起软键盘
									InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
									imm.hideSoftInputFromWindow(
											et_darkterrace_comment.getWindowToken(),
											0);

										ic_faces.setVisibility(View.GONE);
									ic_images.setVisibility(View.VISIBLE);
									// 打开上传布局
									LayoutParams lp = rel_noattestation_rl3
											.getLayoutParams();
									lp.width = LayoutParams.MATCH_PARENT;
									lp.height = LayoutParams.WRAP_CONTENT;
									rel_noattestation_rl3.setLayoutParams(lp);
								} else if(ic_images.getVisibility()==View.VISIBLE) {
									
									ic_faces.setVisibility(View.GONE);
									ic_images.setVisibility(View.GONE);
									LayoutParams lp = rel_noattestation_rl3
											.getLayoutParams();
									lp.width = LayoutParams.MATCH_PARENT;
									lp.height = 1;
									rel_noattestation_rl3.setLayoutParams(lp);
								}
							}
						});		

		// 输入框点击事件
		
		lin_darkterrace_lin1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		
		et_darkterrace_comment.setOnTouchListener(new OnTouchListener() {

					@Override
					public boolean onTouch(View v, MotionEvent event) {
						// 强制显示软键盘
						// 得到InputMethodManager的实例
						InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
						boolean showSoftInput = imm.showSoftInput(v,
								InputMethodManager.SHOW_FORCED);

						ic_faces.setVisibility(View.GONE);
						ic_images.setVisibility(View.GONE);
						LayoutParams lp = rel_noattestation_rl3.getLayoutParams();
						lp.width = LayoutParams.MATCH_PARENT;
						lp.height = 1;
						rel_noattestation_rl3.setLayoutParams(lp);
						return false;
					}
				});
		
		//上传图片事件
		iv_add.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// 读取相册的图片
				Intent intent = new Intent(getApplicationContext(),
						SelectPicPopupWindow.class);
				startActivityForResult(intent, 100);				
			}
		});
		
		lv_darkterrace.setXListViewListener(new XListView.IXListViewListener() {

			@Override
			public void onRefresh() {
				if (manager.getActiveNetworkInfo() != null) {
					switch (comment_type) {
					case 1:
						PublishComment.getDarkPlatform(Util.getLoginType(DarkTerraceActivity.this),
								company.getCompany_id(), user_id, handler, 4,
								1, 10);
						PublishSearch.getCommpany(company.getCompany_id(),
								handler, 16);
						break;
					case 2:
						PublishExposal.getCompanyExposal(
								company.getCompany_id(), user_id, handler, 8,
								1, 10);
						PublishSearch.getCommpany(company.getCompany_id(),
								handler, 16);
						break;
					case 3:
						PublishNew.getCompanyNew(company.getCompany_id(), 1,
								handler, 12, 1, 10);
						PublishSearch.getCommpany(company.getCompany_id(),
								handler, 16);
						break;
					}

				} else {
					Toast.makeText(DarkTerraceActivity.this, "您的神器好像没有联网！",
							Toast.LENGTH_SHORT).show();
					lv_darkterrace.stopRefresh();
				}
			}

			@Override
			public void onLoadMore() {
				if (manager.getActiveNetworkInfo() != null) {
					switch (comment_type) {
					case 1:
						if (comment_company2s != null
								&& comment_company2s.size() > 0) {
							if (comment_company2s.size() < 10) {
								PublishComment.getDarkPlatform(Util.getLoginType(DarkTerraceActivity.this),
										company.getCompany_id(), user_id,
										handler, 5,
										comment_company2s.size() / 10 + 2, 10);
							} else {
								if (comment_company2s.size()%10 == 0) {
									PublishComment.getDarkPlatform(Util.getLoginType(DarkTerraceActivity.this),
											company.getCompany_id(), user_id,
											handler, 5,
											comment_company2s.size() / 10 + 1,
											10);
								} else {
									PublishComment.getDarkPlatform(Util.getLoginType(DarkTerraceActivity.this),
											company.getCompany_id(), user_id,
											handler, 5,
											comment_company2s.size() / 10 + 2,
											10);
								}
							}
						} else {
							PublishComment.getDarkPlatform(Util.getLoginType(DarkTerraceActivity.this),
									company.getCompany_id(), user_id, handler,
									4, 1, 10);
						}
						break;
					case 2:
						if (exosure_Companys != null
								&& exosure_Companys.size() > 0) {
							if (exosure_Companys.size() < 10) {
								PublishExposal.getCompanyExposal(
										company.getCompany_id(), user_id,
										handler, 9,
										exosure_Companys.size() / 10 + 2, 10);
							} else {
								if (exosure_Companys.size() %10 == 0) {
									PublishExposal.getCompanyExposal(
											company.getCompany_id(), user_id,
											handler, 9,
											exosure_Companys.size() / 10 + 1,
											10);
								} else {
									PublishExposal.getCompanyExposal(
											company.getCompany_id(), user_id,
											handler, 9,
											exosure_Companys.size() / 10 + 2,
											10);
								}
							}
						} else {
							PublishExposal.getCompanyExposal(
									company.getCompany_id(), user_id, handler,
									8, 1, 10);
						}
						break;
					case 3:
						if (news_Company != null && news_Company.size() > 0) {
							if (news_Company.size() < 10) {
								PublishNew.getCompanyNew(
										company.getCompany_id(), 1, handler,
										13, news_Company.size() / 10 + 2, 10);
							} else {
								if (news_Company.size()%10 == 0) {
									PublishNew.getCompanyNew(
											company.getCompany_id(), 1,
											handler, 13,
											news_Company.size() / 10 + 1, 10);
								} else {
									PublishNew.getCompanyNew(
											company.getCompany_id(), 1,
											handler, 13,
											news_Company.size() / 10 + 2, 10);
								}
							}
						} else {
							PublishNew.getCompanyNew(company.getCompany_id(),
									1, handler, 12, 1, 10);
						}
						break;
					}

				} else {
					Toast.makeText(DarkTerraceActivity.this, "您的神器好像没有联网！",
							Toast.LENGTH_SHORT).show();
					lv_darkterrace.stopLoadMore();
				}

			}
		});
		;
	}

	// 点击说一说
	private void setSpeak() {
		// 点击透明区域退出评论
		rel_darkterrace_rl1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// 收起软键盘
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(
						et_darkterrace_comment.getWindowToken(), 0);

				ic_faces.setVisibility(View.GONE);
				ic_images.setVisibility(View.GONE);
				LayoutParams lp = rel_noattestation_rl3.getLayoutParams();
				lp.width = LayoutParams.MATCH_PARENT;
				lp.height = 1;
				rel_noattestation_rl3.setLayoutParams(lp);
				fl_darkterrace_fl1.setVisibility(View.GONE);
			}
		});

		fl_darkterrace_fl1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});
		// 点击说一说
		iv_darkterrace_speak.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (Util.getLoginType(DarkTerraceActivity.this)) {
					fl_darkterrace_fl1.setVisibility(View.VISIBLE);
					switch (comment_type) {
					case 1:
						rb_darkterrace_1.setEnabled(true);
						rb_darkterrace_1.setChecked(true);
						rb_darkterrace_2.setEnabled(true);
						rb_darkterrace_3.setEnabled(true);
						break;
					default:
						rb_darkterrace_1.setEnabled(false);
						rb_darkterrace_2.setEnabled(false);
						rb_darkterrace_3.setEnabled(false);
						rb_darkterrace_1.setChecked(false);
						rb_darkterrace_2.setChecked(false);
						rb_darkterrace_3.setChecked(false);
						break;
					}
				}else {
					SetLogin();
				}
			}
		});

		// 发表评论
		iv_darkterrace_comments.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean loginType = Util.getLoginType(DarkTerraceActivity.this);
				if (loginType) {
					String ed_content = et_darkterrace_comment.getText().toString();
					if (manager.getActiveNetworkInfo() != null) {
						
						initData(companys.get(position));
						
						if (ed_content != null && ed_content.trim().length() > 0) {
							switch (comment_type) {
							// 企业评论
							case 1:
								//fl_noattestation.setVisibility(View.VISIBLE);
								DialogUtil.showProgressDialog(DarkTerraceActivity.this, "加载中...", 0);
								comment_company = new Comment_Company();
								comment_company.setUser_id(user_id);
								comment_company.setCompany_id(company
										.getCompany_id());
								comment_company.setContent(ed_content);
								if (cb_noattestatiob_anon.isChecked()) {
									comment_company.setIs_anonymous(1);
								} else {
									comment_company.setIs_anonymous(0);
								}
								switch (rg_noattextation_commenttype
										.getCheckedRadioButtonId()) {
								case R.id.rb_darkterrace_1:
									comment_company.setType("005001");
									break;
								case R.id.rb_darkterrace_2:
									comment_company.setType("005002");
									break;
								case R.id.rb_darkterrace_3:
									comment_company.setType("005003");
									break;
								}
								
								if (image_path != null && image_path.size() > 0) {
									//pb_exosure.setVisibility(View.VISIBLE);
									new ExoThread(image_path,comment_company.getType()).start();
								}else {
									PublishComment.publishComment(
											comment_company, null,handler,
											comment_type);
								}	
								//PublishComment.publishComment(comment_company,
										//null, handler, comment_type);
								break;
							// 网友曝光
							case 2:

								break;
							// 新闻评论
							case 3:

								break;
							}

						} else {
							//fl_noattestation.setVisibility(View.GONE);
							DialogUtil.dismissProgressDialog();
							Toast.makeText(DarkTerraceActivity.this, "请输入评论的内容！",
									Toast.LENGTH_SHORT).show();
						}
					} else {
						Toast.makeText(DarkTerraceActivity.this, "您的神器好像没有联网！",
								Toast.LENGTH_SHORT).show();
					}
				}else {
					SetLogin();
				}				
			}
		});
	}

	// 去加黑这个企业
	private void addDarkCompany() {
		iv_darkterrace_adddark.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean loginType = Util.getLoginType(DarkTerraceActivity.this);
				if (loginType) {
					if (manager.getActiveNetworkInfo() != null) {
						
						initData(companys.get(position));
						
						//fl_noattestation.setVisibility(View.VISIBLE);
						DialogUtil.showProgressDialog(DarkTerraceActivity.this, "加载中...", 0);
						PublishComment.addDarkCompany(Util.getShare_User_id(DarkTerraceActivity.this),
								company.getCompany_id(), handler, 2);
					} else {
						Toast.makeText(DarkTerraceActivity.this, "您的神器好像没有联网！",
								Toast.LENGTH_SHORT).show();
					}
				}else {
					SetLogin();
				}
				
			}
		});
	}
	
	//点赞
//		private void addPraiseCompany(){
//			rl_left.setOnClickListener(new OnClickListener() {
//				
//				@Override
//				public void onClick(View v) {
//					//登陆状态
//					boolean loginType = Util.getLoginType(DarkTerraceActivity.this);
//					if (loginType) {
//						if (manager.getActiveNetworkInfo() != null) {
//							
//							initData(companys.get(position));
//							
//							fl_noattestation.setVisibility(View.VISIBLE);
//							tv_jiazai.setText("正在点赞企业！");
//							PublishComment.addPraiseCompany(user_id,
//									company.getCompany_id(), handler2, 0);
//						} else {
//							Toast.makeText(DarkTerraceActivity.this, "您的神器好像没有联网！",
//									Toast.LENGTH_SHORT).show();
//						}
//					}else {
//						SetLogin();
//					}	
//					
//				}
//			});
//		}

	// 联网去呢企业评论
	private void getCommentThread() {
		if (manager.getAllNetworkInfo() != null) {
			DialogUtil.showProgressDialog(DarkTerraceActivity.this, "加载中...", 0);
			PublishComment.getDarkPlatform(Util.getLoginType(DarkTerraceActivity.this),company.getCompany_id(), user_id,
					handler, 3, 1, 10);
			PublishExposal.getCompanyExposal(company.getCompany_id(), user_id,
					handler, 14, 1, 10);
			PublishNew.getCompanyNew(company.getCompany_id(), 1, handler, 15,
					1, 10);
		} else {
			Toast.makeText(DarkTerraceActivity.this, "您的神器好像没有联网！",
					Toast.LENGTH_SHORT).show();
		}
	}

	// 接收上一个页面传来的数据和数据初始化
	private void getIntents() {
		Intent intent = this.getIntent();
		mqtt_push_type = intent.getIntExtra("mqtt_push_type", -1);
		//推送的intent
		if(mqtt_push_type == 1){
			company_id = intent.getStringExtra("company_id");
			nature = intent.getStringExtra("nature");
			initpushdata(company_id);
		//上级页面的intent
		}else if(mqtt_push_type == 2){
			companys = intent.getParcelableArrayListExtra("companys");
			position = intent.getIntExtra("position", -1);
			company = companys.get(position);
			search_key = intent.getStringExtra("search_key");	
			et_actionbar_search.setText(search_key);
			setCompany();
			//对企业进行关注
			initData(company);
			getCommentThread();
		}
	}
	
	private void initpushdata(String company_id) {
		if(NetworkUtil.isNetworkConnected(getApplicationContext())){
			HttpUtil.initCompanyInfo(company_id, handler, 18);
		}else {
			Toast.makeText(getApplicationContext(), "网络连接失败", 0).show();
		}
	}

	Handler attention_handler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				String result=(String)msg.obj;
				//Log.i("jay_test", "关注---->"+result);
				int update_Info = JsonUtil.getUpdate_Info(result);
				if (update_Info==0) {
					//Toast.makeText(DarkTerraceActivity.this, "关注成功", 0).show();
				}else {
					//Log.i("jay_test", "关注失败----->");
				}
				break;
			default:
				break;
			}
		};
	};
	
	private void initData(Company company) {
		if (HttpUtil.isNetworkInfo(DarkTerraceActivity.this)!=null) {
			long share_User_id = Util.getShare_User_id(DarkTerraceActivity.this);
			if (share_User_id>0) {
				HttpUtil.initAttention_add(share_User_id, company.getCompany_id(), attention_handler, 0);
			}else {
				//Log.i("jay_test", "未登录----->");
			}
		}else {
			Toast.makeText(DarkTerraceActivity.this, "网络连接失败", 0).show();
		}
	}
	
	private void getPraiseCount(String company_id){
		List<NameValuePair> params=new ArrayList<NameValuePair>();
		JSONObject object=new JSONObject();
		try {
			object.put("company_id", company_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		params.add(new BasicNameValuePair("method",HttpUrl.amount_method));
		params.add(new BasicNameValuePair("content",object.toString()));
		new HttpPostThread(params, handler2, 1).start();
	}

	// 设置平台信息的值
	private void setCompany() {
		//Log.i("Test", "position--->"+position);
		//Log.i("Test", "company--->"+company);
		//Log.i("Test", "companys--->"+companys);
		Bitmap bitmap;
		linl_darktrerrace1 = (LinearLayout) this
				.findViewById(R.id.linl_darktrerrace1);
		linl_darktrerrace2 = (LinearLayout) this
				.findViewById(R.id.linl_darktrerrace2);
		LinearLayout linl_darktrerrace3 = (LinearLayout) view.findViewById(R.id.linl_darktrerrace3);
		LinearLayout linl_darktrerrace4 = (LinearLayout) view.findViewById(R.id.linl_darktrerrace4);
		LinearLayout linl_darktrerrace5 = (LinearLayout) view.findViewById(R.id.linl_darktrerrace5);
		LinearLayout linl_darktrerrace6 = (LinearLayout) view.findViewById(R.id.linl_darktrerrace6);
		LinearLayout linl_darktrerrace7 = (LinearLayout) view.findViewById(R.id.linl_darktrerrace7);
		
		ImageView iv_noattestation_business_licence = (ImageView) view.findViewById(R.id.iv_noattestation_business_licence);
		ImageView iv_noattestation_code_certificate = (ImageView) view.findViewById(R.id.iv_noattestation_code_certificate);
		
		if (company != null) {
			// 设置logo
			iv_darkterrace_terraceicon.setVisibility(View.VISIBLE);
			if (company.getLogo_url() != null
					&& !"".equals(company.getLogo_url())) {
				bitmap = async.loaderBitmap(iv_darkterrace_terraceicon,
						company.getLogo_url(), new ImageCallBack() {

							@Override
							public void imageLoader(ImageView imageView,
									Bitmap bitmap) {
								if (bitmap != null) {
									imageView.setImageBitmap(bitmap);
								}
							}
						}, 0);
				if (bitmap != null) {
					iv_darkterrace_terraceicon.setImageBitmap(bitmap);
				}
			} else {
				iv_darkterrace_terraceicon.setVisibility(View.GONE);
			}
			// 设置公司的名称
			if (company.getCompany_name() != null
					&& !"".equals(company.getCompany_name())) {
				tv_darkterrace_terracename.setText(company.getCompany_name());
			} else {
				tv_darkterrace_terracename.setText(str);
			}
			// 设置公司别名
			if (company.getAlias_list() != null
					&& !"".equals(company.getAlias_list())) {
				tv_darkterrace_terracename2.setText(company.getAlias_list());
			} else {
				tv_darkterrace_terracename2.setText(str);
			}
			// 设置加黑数，评论数，曝光数
			tv_darkterrace_hraad_havadarknumber.setText("("+company
					.getAdd_blk_amount() + ")");
			tv_darkterrace_hraad_commentnumber.setText(company.getCom_amount()
					+ "");
			tv_darkterrace_hraad_exosure.setText(company.getExp_amount() + "");
			// 设置公司的资质和代理平台
			if (company.getAgent_platform_n() != null
					&& !"".equals(company.getAgent_platform_n())) {
				tv_darkterrace_agent_terrace.setText(company
						.getAgent_platform_n());
			} else {
				tv_darkterrace_agent_terrace.setText(str);
				linl_darktrerrace1.setVisibility(View.GONE);
			}
			
			//营业执照
			if (company.getBusin_license() != 0) {
				iv_noattestation_business_licence
						.setImageResource(R.drawable.conmpany_icon_b);
			} else {
				iv_noattestation_business_licence
						.setImageResource(R.drawable.conmpany_icon_a);
			}
			
			//机构代码证
			if (company.getCode_certificate() != 0) {
				iv_noattestation_code_certificate
						.setImageResource(R.drawable.conmpany_icon_b);
			} else {
				iv_noattestation_code_certificate
						.setImageResource(R.drawable.conmpany_icon_a);
			}
			
			//获取点赞数			
			getPraiseCount(String.valueOf(company.getCompany_id()));
			
			// 打开代理平台
			tv_darkterrace_agent_terrace
					.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							if (manager.getActiveNetworkInfo() != null) {
								if (company.getAgent_platform() != null
										&& !"".equals(company
												.getAgent_platform())
										&& !"0".equals(company
												.getAgent_platform())) {
									//fl_noattestation
											//.setVisibility(View.VISIBLE);
									DialogUtil.showProgressDialog(DarkTerraceActivity.this, "加载中...", 0);
									PublishSearch.getCommpany(Long
											.parseLong(company
													.getAgent_platform()),
											handler, 17);
									//aa
								}
							} else {
								Toast.makeText(DarkTerraceActivity.this,
										"您的神器好像没有联网！", Toast.LENGTH_SHORT)
										.show();
							}
						}
					});
			// 设置会员编号
			if (company.getMem_sn() != null && !"".equals(company.getMem_sn())) {
				tv_darkterrace_mem_sn.setText(company.getMem_sn());
			} else {
				tv_darkterrace_mem_sn.setText(str);
				linl_darktrerrace2.setVisibility(View.GONE);
			}
			// 设置监管机构
			if (company.getRegulators_id_n() != null
					&& !"".equals(company.getRegulators_id_n())) {
				tv_darkterrace_regulators.setText("冒充"
						+ company.getRegulators_id_n() + "监管");
			} else {
				tv_darkterrace_regulators.setText(str);
				linl_darktrerrace3.setVisibility(View.GONE);
			}
			// 设置平台和会员编号 资质证明
			// rel_darkterrace2.setLayoutParams(new
			// LayoutParams(LayoutParams.MATCH_PARENT, 200));
			LayoutParams params = rel_darkterrace2.getLayoutParams();
			params.width = LayoutParams.MATCH_PARENT;
			//switch (company.getNature()) {
			//case "003001":
				//linl_darktrerrace1.setVisibility(View.VISIBLE);
				//linl_darktrerrace2.setVisibility(View.VISIBLE);
				//params.height = DensityUtil.dip2px(DarkTerraceActivity.this,
						//200);
				//break;
			//case "003002":
				//linl_darktrerrace1.setVisibility(View.GONE);
				//linl_darktrerrace2.setVisibility(View.GONE);
				//params.height = DensityUtil.dip2px(DarkTerraceActivity.this,
						//150);
				//break;
			//}
			//rel_darkterrace2.setLayoutParams(params);
			// 设置官方网站
			if (company.getWebsite() != null
					&& !"".equals(company.getWebsite())) {
				tv_darkterrace_sito_officiale.setText(company.getWebsite());
			} else {
				tv_darkterrace_sito_officiale.setText(str);
				linl_darktrerrace5.setVisibility(View.GONE);
			}
			// 设置联系电话
			if (company.getTelephone() != null
					&& !"".equals(company.getTelephone())) {
				tv_darkterrace_phone.setText(company.getTelephone());
			} else {
				tv_darkterrace_phone.setText(str);
				linl_darktrerrace4.setVisibility(View.GONE);
			}

			// 设置官方备案
			if (company.getRecord() != null && !"".equals(company.getRecord())) {
				tv_darkterrace_record_officiale.setText(company.getRecord());
			} else {
				tv_darkterrace_record_officiale.setText(str);
				linl_darktrerrace6.setVisibility(View.GONE);
			}
			// 设置注册地址
			if (company.getReg_address() != null
					&& !"".equals(company.getReg_address())) {
				tv_darkterrace_registered_address.setText(company
						.getReg_address());
			} else {
				tv_darkterrace_registered_address.setText(str);
				linl_darktrerrace7.setVisibility(View.GONE);
			}
			//
			if (company.getNature().equals("003002")) {
				new HttpUtil().initVip(company.getCompany_id(),1,5 , handler2, 2);
			}
		}

	}	

	// 设置平台信息的收缩
	private void setTerrace() {
		rel_darkterrace2 = (RelativeLayout) this
				.findViewById(R.id.rel_darkterrace2);
		iv_darkterrace_icon = (ImageView) this
				.findViewById(R.id.iv_darkterrace_icon);
		iv_darkterrace_icon.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				switch (rel_darkterrace2.getVisibility()) {
				case View.GONE:
					rel_darkterrace2.setVisibility(View.VISIBLE);
					iv_darkterrace_icon
							.setImageResource(R.drawable.down);
					break;
				case View.VISIBLE:
					rel_darkterrace2.setVisibility(View.GONE);
					iv_darkterrace_icon
							.setImageResource(R.drawable.open);
					break;
				}
			}
		});
	}

	// 初始化actionBar
	private void initActionBar() {
		iv_actionbar_search = (ImageView) this
				.findViewById(R.id.iv_actionbar_search);
		iv_actionbar_search_icon = (ImageView) this
				.findViewById(R.id.iv_actionbar_search_icon);
		et_actionbar_search = (EditText) this
				.findViewById(R.id.et_actionbar_search);
		tv_actionbar_search = (TextView) this
				.findViewById(R.id.tv_actionbar_search);
		preferences = this.getSharedPreferences("Statuse",
				Context.MODE_PRIVATE);
		editor = preferences.edit();

		tv_actionbar_search.setText("分享");
		// 回退控件
		iv_actionbar_search_icon.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				DarkTerraceActivity.this.finish();
			}
		});
		// 去曝光的页面
		tv_actionbar_search.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				//goExosure();
				StringBuffer sb = new StringBuffer();
				//String title = "搜黑平台查询_全国最大的查询曝光平台_搜黑";
				String title = "最热的投资口碑平台—搜黑";
				sb.append("投资平台不靠谱？手指一点就知道");
				//sb.append("黑平台：");
				//sb.append(company.getCompany_name());
				//sb.append(".");
				//UMShareManager.UMSendShare(DarkTerraceActivity.this,
						//mController, title,
						//PhpUrl.SouheiSearch1 + company.getCompany_id()
								//+ "/p/1.html", bitmap, sb.toString());
				UMImage umImage=new UMImage(DarkTerraceActivity.this, WeiXinShareController.myShot(DarkTerraceActivity.this));
				UMShareManager.UMSendShare(DarkTerraceActivity.this, mController, title, PhpUrl.SouheiSearch1+company.getCompany_id(), umImage, sb.toString());
			}
		});

		// 去查询
		iv_actionbar_search.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				KeyBoardUtil.is_openKeyBoard(getApplicationContext(),DarkTerraceActivity.this);
				
				String search_key = et_actionbar_search.getText().toString();
				if (search_key != null && !"".equals(search_key)) {
					// 去查询
					if (manager.getActiveNetworkInfo() != null) {
						//fl_noattestation.setVisibility(View.VISIBLE);
						DialogUtil.showProgressDialog(DarkTerraceActivity.this, "加载中...", 0);
						PublishSearch.CompanySearch(search_key, user_id,
								handler, 0);
					} else {
						Toast.makeText(DarkTerraceActivity.this, "您的神器好像没有联网！",
								Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(DarkTerraceActivity.this, "您请输入搜索的关键字！", 0)
							.show();
				}
			}
		});
	}

	// 去曝光
	private void goExosure() {
		//筛选页
		//FiltratePageActivity filtrate_page = FiltratePageActivity.filtrate_page;
		//MineFragment_UserInfo userinfo_page = MineFragment_UserInfo.userinfo_page;
		//if(filtrate_page!=null) filtrate_page.finish();
		//if(userinfo_page!=null) userinfo_page.finish();
		//finish();
		AppManager.getInstance().killAllActivity();
		editor.putString("main_type", "Exosure").commit();
		//Intent intent = new Intent(DarkTerraceActivity.this, MainActivity.class);
		//DarkTerraceActivity.this.startActivity(intent);
	}

	// 设置评论的表头
	private void initComment() {
		rel_darkterrace3 = (RelativeLayout) this
				.findViewById(R.id.rel_darkterrace3);
		rel_darkterrace4 = (RelativeLayout) this
				.findViewById(R.id.rel_darkterrace4);
		rel_darkterrace5 = (RelativeLayout) this
				.findViewById(R.id.rel_darkterrace5);
		rel_darkterrace_rel3 = (RelativeLayout) this
				.findViewById(R.id.rel_darkterrace_rel3);
		tv_darkterrace_comment = (TextView) this
				.findViewById(R.id.tv_darkterrace_comment);
		tv_darkterrace_exosure = (TextView) this
				.findViewById(R.id.tv_darkterrace_exosure);
		tv_darkterrace_media = (TextView) this
				.findViewById(R.id.tv_darkterrace_media);

		// 点击网友评论
		rel_darkterrace3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (comment_type != 1) {
					rel_darkterrace_rel3.setVisibility(View.VISIBLE);
					rel_darkterrace3
							.setBackgroundResource(R.drawable.darkterrace_hread_select3);
					tv_darkterrace_comment
							.setTextColor(DarkTerraceActivity.this
									.getResources().getColor(R.color.blue));
					setBackground(comment_type);
					if (adapterComment != null) {
						
						lv_darkterrace.setAdapter(adapterComment);
						//tv_count1.setText(commentcontent+"");
						if (comment_company2s != null
								&& comment_company2s.size() > 0) {
							lv_darkterrace.removeFooterView(DensityUtil.dip2px(
									DarkTerraceActivity.this, 60));
							setLayoutParams(1);
							if (comment_company2s.size()<10) {
								lv_darkterrace.removeFooterView(1);
							}
						} else {
							lv_darkterrace.removeFooterView(1);
							tv_darktext.setText("还没有人评论过此公司哦，赶紧去吐槽吧！");
							setLayoutParams(0);
						}
					} else {
						if (manager.getAllNetworkInfo() != null) {
							//fl_noattestation.setVisibility(View.VISIBLE);
							DialogUtil.showProgressDialog(DarkTerraceActivity.this, "加载中...", 0);
							PublishComment.getDarkPlatform(Util.getLoginType(DarkTerraceActivity.this),
									company.getCompany_id(), user_id, handler,
									3, 1, 10);
						} else {
							Toast.makeText(DarkTerraceActivity.this,
									"您的神器好像没有联网！", Toast.LENGTH_SHORT).show();
						}
					}
					comment_type = 1;

				}
			}
		});

		// 点击网友曝光
		rel_darkterrace4.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (comment_type != 2) {
					rel_darkterrace_rel3.setVisibility(View.GONE);
					rel_darkterrace4
							.setBackgroundResource(R.drawable.darkterrace_hread_select3);
					tv_darkterrace_exosure
							.setTextColor(DarkTerraceActivity.this
									.getResources().getColor(R.color.blue));
					;
					setBackground(comment_type);
					comment_type = 2;
					if (adapterExosure == null) {
						if (manager.getAllNetworkInfo() != null) {
							if (company != null) {
								//fl_noattestation.setVisibility(View.VISIBLE);
								DialogUtil.showProgressDialog(DarkTerraceActivity.this, "加载中...", 0);
								PublishExposal.getCompanyExposal(
										company.getCompany_id(), user_id,
										handler, 7, 1, 10);
							}
						} else {
							Toast.makeText(DarkTerraceActivity.this,
									"您的神器好像没有联网！", Toast.LENGTH_SHORT).show();
						}
					} else {
						lv_darkterrace.setAdapter(adapterExosure);
						//tv_count1.setText(commentcontent+"");
						if (exosure_Companys != null
								&& exosure_Companys.size() > 0) {
							lv_darkterrace.removeFooterView(DensityUtil.dip2px(
									DarkTerraceActivity.this, 60));
							setLayoutParams(1);
							if (exosure_Companys.size()<10) {
								lv_darkterrace.removeFooterView(1);
							}
						} else {
							lv_darkterrace.removeFooterView(1);
							tv_darktext.setText("还没有用户曝光过此公司呢，我要去曝光一个!");
							setLayoutParams(0);
						}
					}
				}
			}
		});

		// 点击媒体报道
		rel_darkterrace5.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (comment_type != 3) {
					rel_darkterrace_rel3.setVisibility(View.GONE);
					rel_darkterrace5
							.setBackgroundResource(R.drawable.darkterrace_hread_select3);
					tv_darkterrace_media.setTextColor(DarkTerraceActivity.this
							.getResources().getColor(R.color.blue));
					setBackground(comment_type);
					comment_type = 3;
					if (adapterNews == null) {
						if (manager.getActiveNetworkInfo() != null) {
							if (company != null) {
								//fl_noattestation.setVisibility(View.VISIBLE);
								DialogUtil.showProgressDialog(DarkTerraceActivity.this, "加载中...", 0);
								PublishNew.getCompanyNew(
										company.getCompany_id(), 1, handler,
										11, 1, 10);
							}
						} else {
							Toast.makeText(DarkTerraceActivity.this,
									"您的神器好像没有联网！", Toast.LENGTH_SHORT).show();
						}
					} else {
						lv_darkterrace.setAdapter(adapterNews);
						if (news_Company != null && news_Company.size() > 0) {
							lv_darkterrace.removeFooterView(DensityUtil.dip2px(
									DarkTerraceActivity.this, 60));
							setLayoutParams(1);
							if (news_Company.size()<10) {
								lv_darkterrace.removeFooterView(1);
							}
						} else {
							lv_darkterrace.removeFooterView(1);
							tv_darktext.setText("哎呦，还不错哦，还没有媒体报告过这家公司！");
							setLayoutParams(0);
						}
					}
				}
			}
		});
	}

	// 设置空白位置 0 listview 的高度为WRAP_CONTENT，其他的为FILL_PARENT
	private void setLayoutParams(int type) {
		LayoutParams lp = lv_darkterrace.getLayoutParams();
		lp.width = LayoutParams.MATCH_PARENT;
		if (type == 0) {
			lp.height = LayoutParams.WRAP_CONTENT;
			tv_darktext.setVisibility(View.VISIBLE);
		} else {
			lp.height = LayoutParams.FILL_PARENT;
			tv_darktext.setVisibility(View.GONE);
		}
		lv_darkterrace.setLayoutParams(lp);
	}

	// 把评论的表头设置成未点击
	private void setBackground(int type) {
		switch (type) {
		case 1:
			rel_darkterrace3.setBackgroundResource(0);
			tv_darkterrace_comment.setTextColor(DarkTerraceActivity.this
					.getResources().getColor(R.color.dark));
			break;
		case 2:
			rel_darkterrace4.setBackgroundResource(0);
			tv_darkterrace_exosure.setTextColor(DarkTerraceActivity.this
					.getResources().getColor(R.color.dark));
			break;
		case 3:
			rel_darkterrace5.setBackgroundResource(0);
			tv_darkterrace_media.setTextColor(DarkTerraceActivity.this
					.getResources().getColor(R.color.dark));
			break;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		/**使用SSO授权必须添加如下代码 */
	    UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(requestCode) ;
	    if(ssoHandler != null){
	       ssoHandler.authorizeCallBack(requestCode, resultCode, data);
	    }
		
		// 读取相册图片
		if (resultCode == 200) {
			Image_url = data.getStringExtra("image_url");
			// Toast.makeText(getActivity(), "jay_test--->"+image_url,
			// 0).show();
			if (Image_url != null && !"".equals(Image_url)) {
				//if (Delete_type) {
					image_count++;
					image_path.add(Image_url);
				//}				
			//Log.i("读取图片---------->   ", ""+image_count);				
				switch (image_count) {
				case 1:
					setBitmap(iv_pic1, Image_url, image_count);
					setOnLongClickListener(iv_pic1);
					// setOnImageChangeListener(iv_pic1);
					break;
				case 2:
					setBitmap(iv_pic2, Image_url, image_count);
					setOnLongClickListener(iv_pic2);
					// setOnImageChangeListener(iv_pic2);
					break;
				case 3:
					setBitmap(iv_pic3, Image_url, image_count);
					setOnLongClickListener(iv_pic3);
					// setOnImageChangeListener(iv_pic3);
					break;
				case 4:
					setBitmap(iv_pic4, Image_url, image_count);
					setOnLongClickListener(iv_pic4);
					// setOnImageChangeListener(iv_pic4);
					break;
				case 5:
					setBitmap(iv_pic5, Image_url, image_count);
					setOnLongClickListener(iv_pic5);
					// setOnImageChangeListener(iv_pic5);
					Toast.makeText(DarkTerraceActivity.this, "亲,最多上传五张图片", 0).show();
					//fl_add.setVisibility(View.INVISIBLE);
					ic_images.setVisibility(View.GONE);
					iv_add.setVisibility(View.GONE);
					//iv_exposure.setClickable(true);
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
			Display display = getWindowManager().getDefaultDisplay();
			// 图片质量压缩
			Bitmap bitmap = Util.extractThumbNail(image_paths,
					display.getWidth() / 5, display.getHeight() / 5, true);

			// Bitmap bitmap = BitmapFactory.decodeFile(image_paths, 1);
			if (bitmap != null) {
				// 尺寸压缩
				Bitmap bitmap2 = ImageUtils.resizeImageByWidth(bitmap,
						getWindowManager().getDefaultDisplay()
								.getWidth() / 7);
				if (bitmap2 != null) {
					//up_value.setText("已选择文件" + image_count);
					view.setImageBitmap(bitmap2);
					view.setVisibility(View.VISIBLE);
					// if (!bitmap2.isRecycled()) {
					// bitmap2.recycle();
					// bitmap2 = null;
					// }
				}else {
					image_count--;
					image_path.remove(Image_url);	
				}
				// if (!bitmap.isRecycled()) {
				// bitmap.recycle();
				// bitmap = null;
				// }
				// System.gc();
			} else {
				image_count--;
				image_path.remove(Image_url);
			}
			//Delete_type=true;
			//Log.i("显示图片---------->   ", ""+image_count);			
		}
	
	// 长按删除图片
		private void setOnLongClickListener(final ImageView image) {

			image.setOnLongClickListener(new OnLongClickListener() {

				@Override
				public boolean onLongClick(View v) {
					DialogUtil.ShowDialog(DarkTerraceActivity.this, "警告", "是否删除?", "取消", "确定",
							null, new DialogInterface.OnClickListener() {								

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									image_count--;
									image.setImageBitmap(null);
									image.setVisibility(View.GONE);
									if (image_count > 0) {
										
										//up_value.setText("已选择文件" + (image_count));
										//iv_add.setVisibility(View.VISIBLE);
									} else {
										//up_value.setText("未选择文件");
									}
									//Delete_type=false;
									//Log.i("删除图片---------->   ", ""+image_count);
								}
							});

					return false;
				}
			});

		}
		
		private void setNullBitmap(ImageView view) {
			view.setImageBitmap(null);
			view.setVisibility(View.GONE);
		}	

	/** 显示表情页的viewpager */
	private ViewPager vp_face;
	/** 输入框 */
	private EditText et_sendmessage;
	/** 游标显示布局 */
	private LinearLayout layout_point;
	/** 表情区域 */
	private View view1;
	/** 表情页界面集合 */
	public static ArrayList<View> pageViews;
	/** 当前表情页 */
	private int current = 0;
	/** 游标点集合 */
	public static ArrayList<ImageView> pointViews;
	/** 表情数据填充器 */
	public static List<FaceAdapter> faceAdapters;
	/** 表情集合 */
	public static List<List<ChatEmoji>> emojis;
	/** 表情页的监听事件 */
	private OnCorpusSelectedListener mListener;
	private TextView tv_darkterrace_hraad_havapraisenumber;
	private RelativeLayout rl_left;
	private int position;

	/**
	 * 表情选择监听
	 * 
	 * @author naibo-liao
	 * @时间： 2013-1-15下午04:32:54
	 */
	public interface OnCorpusSelectedListener {

		void onCorpusSelected(ChatEmoji emoji);

		void onCorpusDeleted();
	}

	private void onCreate() {
		Init_View();
		Init_viewPager();
		Init_Point();
		Init_Data();
	}

	/**
	 * 初始化控件
	 */
	private void Init_View() {
		vp_face = (ViewPager) findViewById(R.id.vp_contains);
		et_sendmessage = (EditText) findViewById(R.id.et_darkterrace_comment);
		layout_point = (LinearLayout) findViewById(R.id.iv_image);
		// et_sendmessage.setOnClickListener(this);
		// findViewById(R.id.btn_face).setOnClickListener(this);
		view1 = findViewById(R.id.ll_facechoose);
	}

	/**
	 * 初始化显示表情的viewpager
	 */
	private void Init_viewPager() {
		pageViews = new ArrayList<View>();
		// 左侧添加空页
		View nullView1 = new View(DarkTerraceActivity.this);
		// 设置透明背景
		nullView1.setBackgroundColor(Color.TRANSPARENT);
		pageViews.add(nullView1);

		// 中间添加表情页

		faceAdapters = new ArrayList<FaceAdapter>();
		for (int i = 0; i < emojis.size(); i++) {
			GridView view = new GridView(DarkTerraceActivity.this);
			FaceAdapter adapter = new FaceAdapter(DarkTerraceActivity.this,
					emojis.get(i));
			view.setAdapter(adapter);
			faceAdapters.add(adapter);
			view.setOnItemClickListener(this);
			view.setNumColumns(7);
			view.setBackgroundColor(Color.TRANSPARENT);
			view.setHorizontalSpacing(1);
			view.setVerticalSpacing(1);
			view.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
			view.setCacheColorHint(0);
			view.setPadding(5, 0, 5, 0);
			view.setSelector(new ColorDrawable(Color.TRANSPARENT));
			view.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
					LayoutParams.WRAP_CONTENT));
			view.setGravity(Gravity.CENTER);
			pageViews.add(view);
		}
		// 右侧添加空页面
		View nullView2 = new View(DarkTerraceActivity.this);
		// 设置透明背景
		nullView2.setBackgroundColor(Color.TRANSPARENT);
		pageViews.add(nullView2);
	}

	/**
	 * 初始化游标
	 */
	private void Init_Point() {

		pointViews = new ArrayList<ImageView>();
		ImageView imageView;
		for (int i = 0; i < pageViews.size(); i++) {
			imageView = new ImageView(DarkTerraceActivity.this);
			imageView.setBackgroundResource(R.drawable.d1);
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
					new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT));
			layoutParams.leftMargin = 10;
			layoutParams.rightMargin = 10;
			layoutParams.width = 8;
			layoutParams.height = 8;
			layout_point.addView(imageView, layoutParams);
			if (i == 0 || i == pageViews.size() - 1) {
				imageView.setVisibility(View.GONE);
			}
			if (i == 1) {
				imageView.setBackgroundResource(R.drawable.d2);
			}
			pointViews.add(imageView);

		}
	}

	/**
	 * 填充数据
	 */
	private void Init_Data() {
		vp_face.setAdapter(new ViewPagerAdapter(pageViews));

		vp_face.setCurrentItem(1);
		current = 0;
		vp_face.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				current = arg0 - 1;
				// 描绘分页点
				draw_Point(arg0);
				// 如果是第一屏或者是最后一屏禁止滑动，其实这里实现的是如果滑动的是第一屏则跳转至第二屏，如果是最后一屏则跳转到倒数第二屏.
				if (arg0 == pointViews.size() - 1 || arg0 == 0) {
					if (arg0 == 0) {
						vp_face.setCurrentItem(arg0 + 1);// 第二屏 会再次实现该回调方法实现跳转.
						pointViews.get(1).setBackgroundResource(R.drawable.d2);
					} else {
						vp_face.setCurrentItem(arg0 - 1);// 倒数第二屏
						pointViews.get(arg0 - 1).setBackgroundResource(
								R.drawable.d2);
					}
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});

	}

	/**
	 * 绘制游标背景
	 */
	public void draw_Point(int index) {
		for (int i = 1; i < pointViews.size(); i++) {
			if (index == i) {
				pointViews.get(i).setBackgroundResource(R.drawable.d2);
			} else {
				pointViews.get(i).setBackgroundResource(R.drawable.d1);
			}
		}
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		ChatEmoji emoji = (ChatEmoji) faceAdapters.get(current).getItem(
				position);
		// Log.i("jay-test--->", emoji+"");
		if (emoji.getId() == R.drawable.face_del_icon) {
			int selection = et_sendmessage.getSelectionStart();
			String text = et_sendmessage.getText().toString();
			if (selection > 0) {
				String text2 = text.substring(selection - 1);
				if ("]".equals(text2)) {
					int start = text.lastIndexOf("[");
					int end = selection;
					et_sendmessage.getText().delete(start, end);
					return;
				}
				et_sendmessage.getText().delete(selection - 1, selection);
			}
		}
		if (!TextUtils.isEmpty(emoji.getCharacter())) {
			if (mListener != null)
				mListener.onCorpusSelected(emoji);
			Log.i("emoji.getId()", emoji.getId() + "");
			Log.i("emoji.getCharacter()", emoji.getCharacter() + "");
			Log.i("emoji.getFaceName()", emoji.getFaceName() + "");
			SpannableString spannableString = FaceConversionUtil.getInstace()
					.addFace(DarkTerraceActivity.this, emoji.getId(),
							emoji.getFaceName());
			// Log.i("jay_test--->", spannableString+"");
			et_sendmessage.append("[" + spannableString + "]");
		}
	}

	// 上传图片
	class ExoThread extends Thread {

		private List<String> list;
		private ExosureImage exosureImage;
		private String type;

		public ExoThread(List<String> list, String type) {
			this.list = list;
			this.type = type;
		}

		@Override
		public void run() {
			Map<String, String> map = new HashMap<String, String>();// 图片id;
			for (int i = 0; i < list.size(); i++) {
				String upLoad = HttpUtil.UpLoad(list.get(i), type);
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
			images_handler.sendMessage(message);
			image_path.clear();
		}
	}
}
