package com.example.black.act;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.black.R;
import com.example.black.lib.AppManager;
import com.example.black.lib.AsyncBitmapLoader;
import com.example.black.lib.AsyncBitmapLoader2;
import com.example.black.lib.DensityUtil;
import com.example.black.lib.HttpUtil;
import com.example.black.lib.ImageCallBack;
import com.example.black.lib.ImageUtils;
import com.example.black.lib.JsonUtil;
import com.example.black.lib.NetworkUtil;
import com.example.black.lib.PhpUrl;
import com.example.black.lib.PublishExposal;
import com.example.black.lib.Util;
import com.example.black.lib.WeiXinShareController;
import com.example.black.lib.WhatDayUtil;
import com.example.black.lib.model.Comment_Company;
import com.example.black.lib.model.Comment_Company2;
import com.example.black.lib.model.ExosureImage;
import com.example.black.lib.model.FaceAdapter;
import com.example.black.lib.model.Reply_Activity;
import com.example.black.lib.umeng.UMShareManager;
import com.example.black.lib.umeng.UMStaticConstant;
import com.example.black.view.CommentReplyAdapter;
import com.example.black.view.FaceConversionUtil;
import com.example.black.view.MineFragment_Login;
import com.example.black.view.SelectPicPopupWindow;
import com.example.black.view.ViewPagerAdapter;
import com.example.black.view.custom.ChatEmoji;
import com.example.black.view.custom.CircularImage;
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
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 某一条评论的详情
 * 
 * @author admin
 * 
 */
public class CommentReplyActivity  extends FragmentActivity implements
OnItemClickListener {
	private final UMSocialService mController = UMServiceFactory
			.getUMSocialService(UMStaticConstant.DESCRIPTOR);

	private ActionBar actionBar;// activity的actionbar
	private ImageView iv_comment_reply_action_icon;// 回退按钮
	private TextView tv_content_centent;// 回退按钮
	private XListView lv_comment_reply;
	private View view;// listview 的heard
	//private FrameLayout fl_noattestation;// 加载的布局
	//private TextView tv_jiazai;// 加载的文字
	private CommentReplyAdapter commentReplyAdapter;
	private int postion;// 几楼
	private Comment_Company2 comment_company2;// 主评论的对象
	private CircularImage iv_comment_header_user_icon;// 评论的用户头像
	private TextView tv_comment_header_user_name;// 用户名称
	private TextView tv_comment_header_user_content;// 用户发表评论
	private LinearLayout lin_comment_header_listview1;// 发表的图片
	private ImageView iv_comment_header_user_icon1;// 图片
	private ImageView iv_comment_header_user_icon2;//
	private ImageView iv_comment_header_user_icon3;//
	private ImageView iv_comment_header_user_icon4;//
	private TextView tv_89;// 几楼
	private TextView tv_comment_header_user_addtime;// 添加的时间
	private TextView tv_comment_header_listview_crown;// 顶
	private AsyncBitmapLoader asyn;// 下载图片
	private AsyncBitmapLoader2 asyn2;
	private ConnectivityManager manager;// 获取网咯的管理器
	private InputMethodManager inputmanger;// 键盘管理器
	private List<Comment_Company2> comment_company2s;
	private CommentReplyAdapter replyAdapter;// 回复的适配器
	private long user_id = -1;
	private SharedPreferences preferences;

	private FrameLayout fl_comment_reply_fl22;// 分享框
	private RelativeLayout rel_comment_reply_rl21;// 分享框
	private LinearLayout rel_comment_reply_rl22;// 分享框
	private Button reg_btn;// 分享到好友
	private Button goto_send_btn;// 分享到朋友圈
	private RelativeLayout rl_right;// 曝光

	private Map<String, String> comment_map;// 操作结果

	private FrameLayout fl_comment_reply_fl1;// 评论输入框的布局
	private EditText et_comment_reply_comment;// 评论的内容
	private ImageView iv_comment_reply_comments;// 发布
	private RelativeLayout rel_comment_reply_rl1;//
	private RelativeLayout rl_left;// 说一说
	private View view2;
	private int type = 0;// 1.评论  2.曝光
	private boolean faces_type = true;
	private boolean images_type = true;
	private String company_name;// 公司name
	private Comment_Company comment_Company;
	private Map<String, Object> map;//楼主分享信息
	public static boolean change_type=false;//顶状态
	
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
	public static CommentReplyActivity reply_page;
	//推送
	private String comment_id;
	private int mqtt_push_type;
	private int push_type;

	//上传图片返回的结果
		private Handler images_handler=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 0:
					Map<String, String> maps = (Map<String, String>) msg.obj;
					if (maps != null) {
						// Log.i("map---->", maps + "");
						 
						//setExosure(maps);
						PublishComment.publishComment(
								comment_Company, maps,handler,
							5);
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
						Toast.makeText(CommentReplyActivity.this, "上传图片失败", 0).show();
					}
					break;

				default:
					break;
				}
				//faces_type=true;
				//images_type=true;
			};
		};
	
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			// 拿取评论的回复
			case 0:
				//fl_noattestation.setVisibility(View.GONE);
				
				String result1 = msg.obj.toString();
				//Log.i("Test", "某条评论的回复------>"+result1);
				if ("null".equals(result1)) {
					Toast.makeText(CommentReplyActivity.this, "对不起，服务器异常！",
							Toast.LENGTH_SHORT).show();
				} else {
					comment_company2s = new JsonUtil()
							.getComment_Company2s(type,result1);
					commentReplyAdapter = new CommentReplyAdapter(
							CommentReplyActivity.this, comment_company2s);
					commentReplyAdapter.setOnclick(rl_left,fl_comment_reply_fl1);
					commentReplyAdapter.setNickname(et_comment_reply_comment);
					lv_comment_reply.setAdapter(commentReplyAdapter);
				}
				//fl_noattestation.setVisibility(View.GONE);
				DialogUtil.dismissProgressDialog();
				if (comment_company2s != null && comment_company2s.size() > 0) {
					lv_comment_reply.removeFooterView(DensityUtil.dip2px(
							CommentReplyActivity.this, 60));
					if (comment_company2s.size()<10) {
						lv_comment_reply.removeFooterView(1);
					}
				} else {
					lv_comment_reply.removeFooterView(1);
				}
				break;
			// 刷新数据
			case 2:
				String result2 = msg.obj.toString();
				if ("null".equals(result2)) {
					Toast.makeText(CommentReplyActivity.this, "对不起，服务器异常！",
							Toast.LENGTH_SHORT).show();
				} else {
					comment_company2s = new JsonUtil()
							.getComment_Company2s(type,result2);
					commentReplyAdapter = new CommentReplyAdapter(
							CommentReplyActivity.this, comment_company2s);
					lv_comment_reply.setAdapter(commentReplyAdapter);
				}
				lv_comment_reply.stopRefresh();
				lv_comment_reply.stopLoadMore();
				if (comment_company2s != null && comment_company2s.size() > 0) {
					lv_comment_reply.removeFooterView(DensityUtil.dip2px(
							CommentReplyActivity.this, 60));
					if (comment_company2s.size()<10) {
						lv_comment_reply.removeFooterView(1);
					}
				} else {
					lv_comment_reply.removeFooterView(1);
				}
				break;
			// 添加跟多
			case 3:
				String result3 = msg.obj.toString();
				if ("null".equals(result3)) {
					Toast.makeText(CommentReplyActivity.this, "对不起，服务器异常！",
							Toast.LENGTH_SHORT).show();
					lv_comment_reply.removeFooterView(1);
				} else {
					List<Comment_Company2> comment_Cmpany2s = new JsonUtil()
							.getComment_Company2s(type,result3);
					if (comment_Cmpany2s.size() > 0) {
						comment_company2s.addAll(comment_Cmpany2s);
						if (commentReplyAdapter != null) {
							commentReplyAdapter
									.setComment_Company2s(comment_company2s);
							commentReplyAdapter.notifyDataSetChanged();
							if (comment_company2s.size()<10) {
								lv_comment_reply.removeFooterView(1);
							}
						} else {
							commentReplyAdapter = new CommentReplyAdapter(
									CommentReplyActivity.this,
									comment_company2s);
							lv_comment_reply.setAdapter(commentReplyAdapter);
						}
					} else {
						lv_comment_reply.removeFooterView(1);
						Toast.makeText(CommentReplyActivity.this, "没有更多数据",
								Toast.LENGTH_SHORT).show();
					}
				}
				lv_comment_reply.stopLoadMore();
				break;
			// 顶某一条评论
			case 4:
				String result4 = msg.obj.toString();
				if ("null".equals(result4)) {
					Toast.makeText(CommentReplyActivity.this, "对不起，服务器异常！",
							Toast.LENGTH_SHORT).show();
				} else {
					comment_map = new JsonUtil().getOperateResult(result4);
					switch (Integer.parseInt(comment_map.get("is_success"))) {
					case 0:
						tv_comment_header_listview_crown.setText("顶("
								+ (comment_company2.getTop_num() + 1) + ")");
						//顶成功的状态
						change_type=true;
						Toast.makeText(CommentReplyActivity.this, "顶成功！",
								Toast.LENGTH_SHORT).show();
						break;
					case -1:
						Toast.makeText(CommentReplyActivity.this, "操作失败！",
								Toast.LENGTH_SHORT).show();
						break;
					case -2:
						Toast.makeText(CommentReplyActivity.this,
								"对不起，24小时之内只能顶一次！", Toast.LENGTH_SHORT).show();
						break;
					case -3:
						Toast.makeText(CommentReplyActivity.this,
								"此评论已删除", Toast.LENGTH_SHORT).show();
						break;
					case -4:
						Toast.makeText(CommentReplyActivity.this,
								"此评论不存在", Toast.LENGTH_SHORT).show();
						break;
					case -5:
						Toast.makeText(CommentReplyActivity.this,
								"此评论的企业不存在或者已删除", Toast.LENGTH_SHORT).show();
						break;
					}
				}
				//fl_noattestation.setVisibility(View.GONE);
				DialogUtil.dismissProgressDialog();
				break;
			// 发表评论(评论)
			case 5:
				String result5 = msg.obj.toString();
				//Log.i("Test", "评论返回值------>"+result5);
				if ("null".equals(result5)) {
					Toast.makeText(CommentReplyActivity.this, "对不起，服务器异常！",
							Toast.LENGTH_SHORT).show();
				} else {
					comment_map = new JsonUtil().getOperateResult(result5);
					int is_success=Integer.parseInt(comment_map.get("is_success"));
					if (is_success == 0) {
						Toast.makeText(CommentReplyActivity.this,
								"您的评论已经成功提交！", Toast.LENGTH_LONG).show();
						
						PublishComment.getCommentReplyList(type,
								comment_company2.getCompany_id(),
								comment_company2.getId(), user_id, handler, 0,
								1, 10);
					} else if(is_success==-1){
						Toast.makeText(CommentReplyActivity.this, "您的评论提价失败！",
								Toast.LENGTH_LONG).show();
						DialogUtil.dismissProgressDialog();
					}else if(is_success==-6){
						Toast.makeText(CommentReplyActivity.this, "该企业不存在或者已删除！",
								Toast.LENGTH_LONG).show();
						DialogUtil.dismissProgressDialog();
					}else {
						Toast.makeText(CommentReplyActivity.this, "该评论不存在或者已删除！",
								Toast.LENGTH_LONG).show();
						DialogUtil.dismissProgressDialog();
					}
				}
				commentReplyAdapter.setNickname(null);
				et_comment_reply_comment.setText("");
				fl_comment_reply_fl1.setVisibility(View.GONE);
				inputmanger.hideSoftInputFromWindow(view2.getWindowToken(), 0);
				break;
			case 6:
				
				break;
			}
		};
	};

	private RelativeLayout ic_faces;
	private FrameLayout ic_images;
	private ImageView iv_add;
	private ImageView iv_pic1;
	private ImageView iv_pic2;
	private ImageView iv_pic4;
	private ImageView iv_pic3;
	private ImageView iv_pic5;

	private RelativeLayout rel_noattestation_rl3;
	private String Image_url;
	private int image_count = 0;//相册图片显示的坐标
	private List<String> image_path = new ArrayList<String>();// 存储的图片路径集合
	private boolean Delete_type=true;//是否删除图片的状态

	@SuppressLint("NewApi") @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comment_reply);
		actionBar = this.getActionBar();
		reply_page = this;
		// 设置actionbar
		new HomePageController().setActionbar(LayoutInflater.from(this)
				.inflate(R.layout.comment_reply_actionbar, null), actionBar);
		
		initActionBar();
		initView();
		getIntents();
		initSpeck();
		initShare();

		FaceConversionUtil.getInstace();
		emojis = FaceConversionUtil.emojiLists;
		onCreate();
		AppManager.getInstance().addActivity(CommentReplyActivity.this);
	}

	//编辑框存在，按返回键隐藏编辑框，反之finish
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			if (fl_comment_reply_fl1 != null
					&& fl_comment_reply_fl1.getVisibility() == View.VISIBLE) {
				fl_comment_reply_fl1.setVisibility(View.GONE);
			} else {
				CommentReplyActivity.this.finish();
			}
		}
		return false;
	}

	private void initShare() {
		rl_right = (RelativeLayout) findViewById(R.id.rl_right);
		// fl_comment_reply_fl22 = (FrameLayout)
		// this.findViewById(R.id.fl_comment_reply_fl22);
		// rel_comment_reply_rl21 = (RelativeLayout)
		// this.findViewById(R.id.rel_comment_reply_rl21);
		// rel_comment_reply_rl22 = (LinearLayout)
		// this.findViewById(R.id.rel_comment_reply_rl22);
		// reg_btn = (Button) this.findViewById(R.id.reg_btn);
		// goto_send_btn = (Button) this.findViewById(R.id.goto_send_btn);
		
		//我要曝光
		rl_right.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				//DarkTerraceActivity dark_page = DarkTerraceActivity.dark_page;
				//QualifiedTerraceActivity qualified_page = QualifiedTerraceActivity.qualified_page;
				//NoAttestationActivity no_attestation_page = NoAttestationActivity.no_attestation_page;
				//Reply_Activity reply_page = Reply_Activity.reply_page;
				//if(dark_page!=null) dark_page.finish();
				//if(qualified_page!=null) qualified_page.finish();
				//if(no_attestation_page!=null) no_attestation_page.finish();
				//if(reply_page!=null) reply_page.finish();
				//finish();
				AppManager.getInstance().killAllActivity();
				getSharedPreferences("Statuse",Context.MODE_PRIVATE).edit().putString("main_type", "Exosure").commit();
			}
		});
	}

	// 初始化评论框 的
	private void initSpeck() {
		rl_left = (RelativeLayout) findViewById(R.id.rl_left);
		fl_comment_reply_fl1 = (FrameLayout) findViewById(R.id.fl_comment_reply_fl1);
		et_comment_reply_comment = (EditText) findViewById(R.id.et_comment_reply_comment);
		iv_comment_reply_comments = (ImageView) findViewById(R.id.iv_comment_reply_comments);
		rel_comment_reply_rl1 = (RelativeLayout) findViewById(R.id.rel_comment_reply_rl1);

		et_comment_reply_comment.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// 强制显示软键盘
				// 得到InputMethodManager的实例
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				boolean showSoftInput = imm.showSoftInput(v,
						InputMethodManager.SHOW_FORCED);

				LayoutParams lp = rel_noattestation_rl3.getLayoutParams();
				lp.width = LayoutParams.MATCH_PARENT;
				lp.height = 1;
				rel_noattestation_rl3.setLayoutParams(lp);
				faces_type = true;
				images_type = true;
				return false;
			}
		});

		// 设置说一说监听事件
		rl_left.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (Util.getLoginType(CommentReplyActivity.this)) {
					commentReplyAdapter.setNickname(null);
					et_comment_reply_comment.setText("");
					fl_comment_reply_fl1.setVisibility(View.VISIBLE);	
				}else {
					SetLogin();
				}
			}
		});

		fl_comment_reply_fl1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});

		// 设置评论框消失
		rel_comment_reply_rl1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// 收起软键盘
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(
						et_comment_reply_comment.getWindowToken(), 0);

				ic_faces.setVisibility(View.GONE);
				LayoutParams lp = rel_noattestation_rl3.getLayoutParams();
				lp.width = LayoutParams.MATCH_PARENT;
				lp.height = 1;
				rel_noattestation_rl3.setLayoutParams(lp);
				fl_comment_reply_fl1.setVisibility(View.GONE);
				faces_type = true;
				images_type = true;
			}
		});
		
		// 设置发布评论
		iv_comment_reply_comments
				.setOnClickListener(new View.OnClickListener() {			

					@Override
					public void onClick(View v) {
						boolean loginType = Util.getLoginType(CommentReplyActivity.this);
						if (loginType) {
							String content = et_comment_reply_comment.getText()
									.toString();
							if (manager.getActiveNetworkInfo() != null) {
								if (content != null && content.trim().length() > 0) {
									if(!content.equals("回复 "+commentReplyAdapter.getnew_Nickname()+" :")){
									comment_Company = new Comment_Company();
									//fl_noattestation.setVisibility(View.VISIBLE);
									DialogUtil.showProgressDialog(CommentReplyActivity.this, "加载中...", 0);
									int top_type=0;
									//comment_Company.setCompany_id(comment_company2
											//.getCompany_id());
									//comment_Company.setParent_id(comment_company2
											//.getId());
									comment_Company.setCompany_id(comment_company2
									.getCompany_id());
									switch (type) {
									case 1:
										//网友评论
										//comment_Company.setCompany_id(comment_company2
												//.getCompany_id());
										comment_Company.setParent_id(comment_company2
												.getId());
										break;
									case 2:
										//网友曝光
										top_type=1;
										//comment_Company.setCompany_id(comment_Top
												//.getId());
										comment_Company.setUser_id(comment_company2.getId());
										comment_Company.setParent_id(comment_company2.getParent_id());
										break;
									default:
										break;
									}
									comment_Company.setContent(content);
									comment_Company.setIs_anonymous(0);
									String mNickname = commentReplyAdapter.getnew_Nickname();
									//是否超过第三层
									if(mNickname != null){
										comment_Company.setIs_depth(1);
									}
									PublishComment.publishComment2(type,top_type,CommentReplyActivity.this,comment_Company,
											null, handler, 5);
									
										}else {
											//fl_noattestation.setVisibility(View.GONE);
											DialogUtil.dismissProgressDialog();
											Toast.makeText(CommentReplyActivity.this,
													"请输入评论的内容！", Toast.LENGTH_SHORT).show();
										}
								} else {
									//fl_noattestation.setVisibility(View.GONE);
									DialogUtil.dismissProgressDialog();
									Toast.makeText(CommentReplyActivity.this,
											"请输入评论的内容！", Toast.LENGTH_SHORT).show();
								}
							} else {
								DialogUtil.dismissProgressDialog();
								Toast.makeText(CommentReplyActivity.this,
										"您的神器好像没有联网！", Toast.LENGTH_SHORT).show();
							}
						}	else {
							SetLogin();
						}					
					}
				});
	}
	
	private void SetLogin(){
		startActivity(new Intent(CommentReplyActivity.this,MineFragment_Login.class));		
	}

	// 获取上一个页面传来的数据
	private void getIntents() {
		Intent intent = this.getIntent();
		if (intent!=null) {
			mqtt_push_type = intent.getIntExtra("mqtt_push_type", -1);
			postion = intent.getIntExtra("position", -1);
			map=Reply_Activity.map;
			//推送的intent_评论
			if(mqtt_push_type == 1){
				comment_id = intent.getStringExtra("comment_id");
				type = intent.getIntExtra("push_type", -1);
				initpushdata(comment_id);
			//推送的intent_曝光
			}else if(mqtt_push_type == 3){
				comment_id = intent.getStringExtra("comment_id");
				type = intent.getIntExtra("push_type", -1);
				initpushdata_bg(comment_id);
			//上级页面的intent	
			}else if(mqtt_push_type == 2){
				//item
				comment_company2 = intent.getParcelableExtra("comment_Company2");
				company_name = intent.getStringExtra("company_name");
				type = intent.getIntExtra("type", 0);
				//Toast.makeText(getApplicationContext(), "---->"+type, 0).show();
				if (comment_company2 != null) {
					setListViewHeader();
					if (manager.getActiveNetworkInfo() != null) {
						DialogUtil.showProgressDialog(CommentReplyActivity.this, "加载中...", 0);
						PublishComment.getCommentReplyList(type,
								comment_company2.getCompany_id(),
								comment_company2.getId(), user_id, handler, 0, 1, 10);
						//fl_noattestation.setVisibility(View.GONE);
						//DialogUtil.dismissProgressDialog();
					} else {
						Toast.makeText(this, "您的神器好像没有联网！", Toast.LENGTH_SHORT).show();
					}
				}
			}
		}
	}

	//曝光
	private void initpushdata_bg(String comment_id) {
		long comment_id2 = Long.valueOf(comment_id);
		if(NetworkUtil.isNetworkConnected(getApplicationContext())){
			PublishComment.getComexposalInfo(comment_id2, pushhandler, 1);
		}else {
			Toast.makeText(getApplicationContext(), "网络连接失败", 0).show();
		}
	}

	//评论
	private void initpushdata(String comment_id) {
		long comment_id2 = Long.valueOf(comment_id);
		if(NetworkUtil.isNetworkConnected(getApplicationContext())){
			PublishComment.getCommentInfo(comment_id2, pushhandler, 0);
		}else {
			Toast.makeText(getApplicationContext(), "网络连接失败", 0).show();
		}
	}
	
	Handler pushhandler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				//获取单条评论信息（企业）
				String result = (String) msg.obj;
				comment_company2 = new JsonUtil().getCommentInfo(result);
				if (comment_company2 != null) {
					setListViewHeader();
					if (manager.getActiveNetworkInfo() != null) {
						DialogUtil.showProgressDialog(CommentReplyActivity.this, "加载中...", 0);
						PublishComment.getCommentReplyList(type,
								comment_company2.getCompany_id(),
								comment_company2.getId(), user_id, handler, 0, 1, 10);
						//fl_noattestation.setVisibility(View.GONE);
						//DialogUtil.dismissProgressDialog();
					} else {
						Toast.makeText(CommentReplyActivity.this, "您的神器好像没有联网！", Toast.LENGTH_SHORT).show();
					}
				}
				break;
			case 1:
				//获取单条曝光信息（企业）
				String result_Comexposal = (String) msg.obj;
				comment_company2 = new JsonUtil().getComexposalInfo(result_Comexposal);
				if(comment_company2 != null){
					setListViewHeader();
					if (NetworkUtil.isNetworkConnected(getApplicationContext())) {
						DialogUtil.showProgressDialog(CommentReplyActivity.this, "加载中...", 0);
						PublishComment.getCommentReplyList(type,
								comment_company2.getCompany_id(),
								comment_company2.getId(), user_id, handler, 0, 1, 10);
						//fl_noattestation.setVisibility(View.GONE);
						//DialogUtil.dismissProgressDialog();
					} else {
						Toast.makeText(CommentReplyActivity.this, "您的神器好像没有联网！", Toast.LENGTH_SHORT).show();
					}
				}
				break;
			}
		}
	};

	// 设置主评论
	private void setListViewHeader() {
		Bitmap bitmap = null;
		Bitmap bitmap2 = asyn.loaderBitmap(
				iv_comment_header_user_icon,
				PhpUrl.AVATARURl
						+ PublishComment.getAvatarUrl(comment_company2
								.getUser_id()) + "/avatar.jpg",
				new ImageCallBack() {

					@Override
					public void imageLoader(ImageView imageView, Bitmap bitmap) {
						if (bitmap != null) {
							imageView.setImageBitmap(bitmap);
						}
					}
				}, 0);
		if (bitmap2 != null) {
			iv_comment_header_user_icon.setImageBitmap(bitmap2);
		}
		if (comment_company2.getIs_anonymous() == 1) {
			tv_comment_header_user_name.setText("****"
					+ comment_company2.getNickname().substring(
							comment_company2.getNickname().length() - 1,
							comment_company2.getNickname().length()));
		} else {
			tv_comment_header_user_name.setText(comment_company2.getNickname());
		}
		tv_comment_header_user_content.setText(PublishComment.getCharSequence(
				comment_company2, this));

		if (comment_company2.getPic_1() == 0
				&& comment_company2.getPic_2() == 0
				&& comment_company2.getPic_3() == 0
				&& comment_company2.getPic_4() == 0
				&& comment_company2.getPic_5() == 0) {
			lin_comment_header_listview1.setVisibility(View.GONE);
		} else {
			lin_comment_header_listview1.setVisibility(View.VISIBLE);
			setImageIcon(comment_company2);
		}
		//aa
		tv_89.setText((postion) + "楼");
		
		long add_time = comment_company2.getAdd_time();
		tv_comment_header_user_addtime.setText(WhatDayUtil.isDate(WhatDayUtil.getDateString("yyyy-MM-dd HH:mm:ss",add_time)));
		String format_now = new SimpleDateFormat("yyyy-MM-dd ").format(new Date());
		
		tv_comment_header_listview_crown.setText("顶("
				+ comment_company2.getTop_num() + ")");

		// 设置顶
		tv_comment_header_listview_crown
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						Boolean login_type=Util.getLoginType(CommentReplyActivity.this);
						if (login_type) {
							if (manager.getActiveNetworkInfo() != null) {
								//fl_noattestation.setVisibility(View.VISIBLE);
								//DialogUtil.showProgressDialog(DarkTerraceActivity.this, "加载中...", 0);
								//PublishComment.CommentComtop(comment_company2.getUser_id(),
								switch (type) {
								case 1:
									//网友评论
									PublishComment.CommentComtop(Util.getShare_User_id(CommentReplyActivity.this),
											comment_company2.getCompany_id(),
											comment_company2.getId(), handler, 4);
									break;
								case 2:
									//网友曝光
									PublishExposal.commentExoTop(Util.getShare_User_id(CommentReplyActivity.this), comment_company2.getCompany_id(), comment_company2.getParent_id(), comment_company2.getId(), handler, 4);
									break;
								}
							} else {
								Toast.makeText(CommentReplyActivity.this,
										"您的神器好像没有联网！", Toast.LENGTH_SHORT).show();
							}
						}else {
							SetLogin();
						}
					}
				});
	}

	// 初始化控件
	private void initView() {
		preferences = this.getSharedPreferences("Login_UserInfo",
				Context.MODE_PRIVATE);
		user_id = preferences.getLong("user_id", -1);
		inputmanger = (InputMethodManager) this
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		view2 = getWindow().peekDecorView();
		manager = (ConnectivityManager) this
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		lv_comment_reply = (XListView) this.findViewById(R.id.lv_comment_reply);
		view = LayoutInflater.from(this).inflate(
				R.layout.comment_reipy_listview_heard, null);
		lv_comment_reply.addHeaderView(view);
		lv_comment_reply.setPullRefreshEnable(true);
		lv_comment_reply.setPullLoadEnable(true);
		asyn = new AsyncBitmapLoader(this);
		asyn2 = new AsyncBitmapLoader2(this);

		iv_comment_header_user_icon = (CircularImage) view
				.findViewById(R.id.iv_comment_header_user_icon);
		iv_comment_header_user_icon1 = (ImageView) view
				.findViewById(R.id.iv_comment_header_user_icon1);
		iv_comment_header_user_icon2 = (ImageView) view
				.findViewById(R.id.iv_comment_header_user_icon2);
		iv_comment_header_user_icon3 = (ImageView) view
				.findViewById(R.id.iv_comment_header_user_icon3);
		iv_comment_header_user_icon4 = (ImageView) view
				.findViewById(R.id.iv_comment_header_user_icon4);
		tv_comment_header_user_name = (TextView) view
				.findViewById(R.id.tv_comment_header_user_name);
		tv_comment_header_user_content = (TextView) view
				.findViewById(R.id.tv_comment_header_user_content);
		tv_comment_header_user_addtime = (TextView) view
				.findViewById(R.id.tv_comment_header_user_addtime);
		tv_comment_header_listview_crown = (TextView) view
				.findViewById(R.id.tv_comment_header_listview_crown);
		lin_comment_header_listview1 = (LinearLayout) view
				.findViewById(R.id.lin_comment_header_listview1);
		tv_89 = (TextView) view.findViewById(R.id.tv_89);
		//回复
		TextView tv_comment_listview_crown = (TextView) view.findViewById(R.id.tv_comment_listview_crown);

		rel_noattestation_rl3 = (RelativeLayout) this
				.findViewById(R.id.rel_noattestation_rl3);
		// 表情
		ImageView iv_comment_reply_comment_icon1 = (ImageView) findViewById(R.id.iv_comment_reply_comment_icon1);
		// 表情布局
		ic_faces = (RelativeLayout) findViewById(R.id.ic_faces);
		// 上传图片
		ImageView iv_comment_reply_comment_icon2 = (ImageView) findViewById(R.id.iv_comment_reply_comment_icon2);
		// 上传图片布局
		ic_images = (FrameLayout) findViewById(R.id.ic_images);
		// 上传图片里的添加图片按钮
		iv_add = (ImageView) findViewById(R.id.iv_add);
		// 图片集合
		iv_pic1 = (ImageView) findViewById(R.id.iv_pic1);
		iv_pic2 = (ImageView) findViewById(R.id.iv_pic2);
		iv_pic3 = (ImageView) findViewById(R.id.iv_pic3);
		iv_pic4 = (ImageView) findViewById(R.id.iv_pic4);
		iv_pic5 = (ImageView) findViewById(R.id.iv_pic5);

		//主评论回复点击事件
		tv_comment_listview_crown.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (Util.getLoginType(CommentReplyActivity.this)) {
					commentReplyAdapter.setNickname(null);
					et_comment_reply_comment.setText("");
					fl_comment_reply_fl1.setVisibility(View.VISIBLE);
				}else {
					SetLogin();
				}
			}
		});
		
		// 显示表情
		iv_comment_reply_comment_icon1
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (faces_type) {
							// 收起软键盘
							InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
							imm.hideSoftInputFromWindow(
									et_comment_reply_comment.getWindowToken(),
									0);

							ic_faces.setVisibility(View.VISIBLE);
							if (ic_images.getVisibility() == View.VISIBLE) {
								ic_images.setVisibility(View.GONE);
							}
							// 打开布局
							LayoutParams lp = rel_noattestation_rl3
									.getLayoutParams();
							lp.width = LayoutParams.MATCH_PARENT;
							lp.height = LayoutParams.WRAP_CONTENT;
							rel_noattestation_rl3.setLayoutParams(lp);
							faces_type = false;
						} else {
							ic_faces.setVisibility(View.GONE);
							ic_images.setVisibility(View.GONE);
							LayoutParams lp = rel_noattestation_rl3
									.getLayoutParams();
							lp.width = LayoutParams.MATCH_PARENT;
							lp.height = 1;
							rel_noattestation_rl3.setLayoutParams(lp);
							faces_type = true;
						}
					}
				});
		
		// 显示上传图片
		iv_comment_reply_comment_icon2
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (images_type) {
							// 收起软键盘
							InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
							imm.hideSoftInputFromWindow(
									et_comment_reply_comment.getWindowToken(),
									0);

							if (ic_faces.getVisibility() == View.VISIBLE) {
								ic_faces.setVisibility(View.GONE);
							}
							ic_images.setVisibility(View.VISIBLE);
							// 打开上传布局
							LayoutParams lp = rel_noattestation_rl3
									.getLayoutParams();
							lp.width = LayoutParams.MATCH_PARENT;
							lp.height = LayoutParams.WRAP_CONTENT;
							rel_noattestation_rl3.setLayoutParams(lp);
							images_type = false;
						} else {
							ic_faces.setVisibility(View.GONE);
							ic_images.setVisibility(View.GONE);
							LayoutParams lp = rel_noattestation_rl3
									.getLayoutParams();
							lp.width = LayoutParams.MATCH_PARENT;
							lp.height = 1;
							rel_noattestation_rl3.setLayoutParams(lp);
							images_type = true;
						}
					}
				});

		// 上传图片事件
		iv_add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 读取相册的图片
				Intent intent = new Intent(getApplicationContext(),
						SelectPicPopupWindow.class);
				startActivityForResult(intent, 100);
			}
		});

		// listview的刷新跟添加更多
		lv_comment_reply
				.setXListViewListener(new XListView.IXListViewListener() {

					// 刷新回复
					@Override
					public void onRefresh() {
						if (manager.getActiveNetworkInfo() != null) {
							if (comment_company2 != null) {
								PublishComment.getCommentReplyList(type,
										comment_company2.getCompany_id(),
										comment_company2.getId(), user_id,
										handler, 2, 1, 10);
							}
						} else {
							Toast.makeText(CommentReplyActivity.this,
									"您的神器好像没有联网！", Toast.LENGTH_SHORT).show();
							lv_comment_reply.stopRefresh();
						}
					}

					// 添加更多
					@Override
					public void onLoadMore() {
						if (manager.getActiveNetworkInfo() != null) {
							if (comment_company2 != null
									&& comment_company2s != null
									&& comment_company2s.size() > 0) {
								if (comment_company2s.size() < 10) {
									PublishComment.getCommentReplyList(type,
											comment_company2.getCompany_id(),
											comment_company2.getId(), user_id,
											handler, 3,
											comment_company2s.size() / 10 + 2,
											10);
								} else {
									if (comment_company2s.size() == 10) {
										PublishComment.getCommentReplyList(type,
												comment_company2
														.getCompany_id(),
												comment_company2.getId(),
												user_id,
												handler,
												3,
												comment_company2s.size() / 10 + 1,
												10);
									} else {
										PublishComment.getCommentReplyList(type,
												comment_company2
														.getCompany_id(),
												comment_company2.getId(),
												user_id,
												handler,
												3,
												comment_company2s.size() / 10 + 2,
												10);
									}
								}
							} else {
								PublishComment.getCommentReplyList(type,
										comment_company2.getCompany_id(),
										comment_company2.getId(), user_id,
										handler, 2, 1, 10);
							}
						} else {
							Toast.makeText(CommentReplyActivity.this,
									"您的神器好像没有联网！", Toast.LENGTH_SHORT).show();
							lv_comment_reply.stopLoadMore();
							;
						}
					}
				});
	}

	// 设置图片
	private void setImageIcon(Comment_Company2 comment_company2) {
		Bitmap bitmap = null;
		List<String> img_url = new ArrayList<String>();
		if (comment_company2.getPic_1() != 0) {
			img_url.add(comment_company2.getPic_1_url());
		}
		if (comment_company2.getPic_2() != 0) {
			img_url.add(comment_company2.getPic_2_url());
		}
		if (comment_company2.getPic_3() != 0) {
			img_url.add(comment_company2.getPic_3_url());
		}
		if (comment_company2.getPic_4() != 0) {
			img_url.add(comment_company2.getPic_4_url());
		}
		if (comment_company2.getPic_5() != 0) {
			img_url.add(comment_company2.getPic_5_url());
		}
		switch (img_url.size()) {
		case 1:
			iv_comment_header_user_icon1.setVisibility(View.VISIBLE);
			iv_comment_header_user_icon2.setVisibility(View.GONE);
			iv_comment_header_user_icon3.setVisibility(View.GONE);
			iv_comment_header_user_icon4.setVisibility(View.GONE);
			bitmap = asyn2.loaderBitmap(iv_comment_header_user_icon1,
					img_url.get(0), new ImageCallBack() {

						@Override
						public void imageLoader(ImageView imageView,
								Bitmap bitmap) {
							if (bitmap != null) {
								imageView.setImageBitmap(bitmap);
							}
						}
					}, 0);
			if (bitmap != null) {
				iv_comment_header_user_icon1.setImageBitmap(bitmap);
				bitmap = null;
			}
			img_url.clear();
			break;
		case 2:
			iv_comment_header_user_icon1.setVisibility(View.VISIBLE);
			iv_comment_header_user_icon2.setVisibility(View.VISIBLE);
			iv_comment_header_user_icon3.setVisibility(View.GONE);
			iv_comment_header_user_icon4.setVisibility(View.GONE);
			bitmap = asyn2.loaderBitmap(iv_comment_header_user_icon1,
					img_url.get(0), new ImageCallBack() {

						@Override
						public void imageLoader(ImageView imageView,
								Bitmap bitmap) {
							if (bitmap != null) {
								imageView.setImageBitmap(bitmap);
							}
						}
					}, 0);
			if (bitmap != null) {
				iv_comment_header_user_icon1.setImageBitmap(bitmap);
				bitmap = null;
			}
			bitmap = asyn2.loaderBitmap(iv_comment_header_user_icon2,
					img_url.get(1), new ImageCallBack() {

						@Override
						public void imageLoader(ImageView imageView,
								Bitmap bitmap) {
							if (bitmap != null) {
								imageView.setImageBitmap(bitmap);
							}
						}
					}, 0);
			if (bitmap != null) {
				iv_comment_header_user_icon2.setImageBitmap(bitmap);
				bitmap = null;
			}
			img_url.clear();
			break;
		default:
			iv_comment_header_user_icon1.setVisibility(View.VISIBLE);
			iv_comment_header_user_icon2.setVisibility(View.VISIBLE);
			iv_comment_header_user_icon3.setVisibility(View.VISIBLE);
			if (img_url.size() == 3) {
				iv_comment_header_user_icon4.setVisibility(View.GONE);
			} else {
				iv_comment_header_user_icon4.setVisibility(View.VISIBLE);
			}

			bitmap = asyn2.loaderBitmap(iv_comment_header_user_icon1,
					img_url.get(0), new ImageCallBack() {

						@Override
						public void imageLoader(ImageView imageView,
								Bitmap bitmap) {
							if (bitmap != null) {
								imageView.setImageBitmap(bitmap);
							}
						}
					}, 0);
			if (bitmap != null) {
				iv_comment_header_user_icon1.setImageBitmap(bitmap);
				bitmap = null;
			}
			bitmap = asyn2.loaderBitmap(iv_comment_header_user_icon2,
					img_url.get(1), new ImageCallBack() {

						@Override
						public void imageLoader(ImageView imageView,
								Bitmap bitmap) {
							if (bitmap != null) {
								imageView.setImageBitmap(bitmap);
							}
						}
					}, 0);
			if (bitmap != null) {
				iv_comment_header_user_icon2.setImageBitmap(bitmap);
				bitmap = null;
			}
			bitmap = asyn2.loaderBitmap(iv_comment_header_user_icon3,
					img_url.get(2), new ImageCallBack() {

						@Override
						public void imageLoader(ImageView imageView,
								Bitmap bitmap) {
							if (bitmap != null) {
								imageView.setImageBitmap(bitmap);
							}
						}
					}, 0);
			if (bitmap != null) {
				iv_comment_header_user_icon3.setImageBitmap(bitmap);
				bitmap = null;
			}
			img_url.clear();
			break;
		}
	}

	// 初始化actionbar
	private void initActionBar() {
		iv_comment_reply_action_icon = (ImageView) this
				.findViewById(R.id.iv_comment_reply_action_icon);
		tv_content_centent = (TextView) this
				.findViewById(R.id.tv_content_centent);
		tv_content_centent.setText("用户回复");
		//分享
		TextView tv_content_reght = (TextView) findViewById(R.id.tv_content_reght);
		tv_content_reght.setText("分享");
		
		iv_comment_reply_action_icon
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						CommentReplyActivity.this.finish();
					}
				});
		
		//分享
		tv_content_reght.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String title=null;
				String url =null;
				String SouheiCommentPic_1_url=null;
				StringBuffer sb = new StringBuffer();
				if (map!=null) {
					url = (String) map.get("SouheiCommentTop");
					SouheiCommentPic_1_url=(String) map.get("SouheiCommentPic_1_url");
				}else {
					url="http://souhei.com.cn/posts_pl?qy="+comment_company2.getCompany_id() + "&pl="+ comment_company2.getId();
					SouheiCommentPic_1_url=comment_company2.getPic_1_url();
				}
				switch (type) {
				case 1:
					title="网友精彩回复—搜黑";
					sb.append("少年被多人围攻，危在旦夕，立即围观");
					break;
				case 2:
					title = (String) map.get("SouheiCommentTitle");
					sb.append(map.get("SouheiCommentContent"));
					break;
				}
				UMImage umImage=null;
				if(!TextUtils.isEmpty(SouheiCommentPic_1_url)){
					umImage=new UMImage(CommentReplyActivity.this, SouheiCommentPic_1_url);
				}else {
					umImage=new UMImage(CommentReplyActivity.this, WeiXinShareController.myShot(CommentReplyActivity.this));
				} 
				UMShareManager.UMSendShare(CommentReplyActivity.this,
						mController, title, url, umImage, sb.toString());
			}
		});
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
				if (Delete_type) {
					image_count++;
					image_path.add(Image_url);
				}				
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
					Toast.makeText(CommentReplyActivity.this, "亲,最多上传五张图片", 0).show();
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
						DialogUtil.ShowDialog(CommentReplyActivity.this, "警告", "是否删除?", "取消", "确定",
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
					.addFace(CommentReplyActivity.this, emoji.getId(),
							emoji.getFaceName());
			// Log.i("jay_test--->", spannableString+"");
			et_sendmessage.append("[" + spannableString + "]");
		}
	}

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
		et_sendmessage = (EditText) findViewById(R.id.et_comment_reply_comment);
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
		View nullView1 = new View(CommentReplyActivity.this);
		// 设置透明背景
		nullView1.setBackgroundColor(Color.TRANSPARENT);
		pageViews.add(nullView1);

		// 中间添加表情页

		faceAdapters = new ArrayList<FaceAdapter>();
		for (int i = 0; i < emojis.size(); i++) {
			GridView view = new GridView(CommentReplyActivity.this);
			FaceAdapter adapter = new FaceAdapter(CommentReplyActivity.this,
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
		View nullView2 = new View(CommentReplyActivity.this);
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
			imageView = new ImageView(CommentReplyActivity.this);
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
	
	//上传图片
		class ExoThread extends Thread {

			private List<String> list;
			private ExosureImage exosureImage;
			private String type;

			public ExoThread(List<String> list,String type) {
				this.list = list;
				this.type=type;
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
