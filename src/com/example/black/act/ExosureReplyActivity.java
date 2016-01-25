package com.example.black.act;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.black.R;
import com.example.black.lib.AsyncBitmapLoader;
import com.example.black.lib.AsyncBitmapLoader2;
import com.example.black.lib.DensityUtil;
import com.example.black.lib.HttpUtil;
import com.example.black.lib.ImageCallBack;
import com.example.black.lib.JsonUtil;
import com.example.black.lib.PhpUrl;
import com.example.black.lib.PublishExposal;
import com.example.black.lib.Util;
import com.example.black.lib.WeiXinShareController;
import com.example.black.lib.model.ExosureImage;
import com.example.black.lib.model.Exosure_Company;
import com.example.black.lib.model.Exosure_Reply;
import com.example.black.lib.model.FaceAdapter;
import com.example.black.lib.umeng.UMShareManager;
import com.example.black.lib.umeng.UMStaticConstant;
import com.example.black.view.ExosureReplyAdapter;
import com.example.black.view.FaceConversionUtil;
import com.example.black.view.MineFragment_Login;
import com.example.black.view.ViewPagerAdapter;
import com.example.black.view.custom.ChatEmoji;
import com.example.black.view.custom.CircularImage;
import com.example.black.view.custom.XListView;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.UMImage;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.SpannableString;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
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
 * 一条曝光的回复
 * @author stone
 *
 */
public class ExosureReplyActivity  extends Activity implements OnItemClickListener{
	private final UMSocialService mController = UMServiceFactory
            .getUMSocialService(UMStaticConstant.DESCRIPTOR);
	
	private ActionBar actionBar;//activity的actionbar
	private ImageView iv_comment_reply_action_icon;//回退按钮
	private TextView tv_content_centent;//中间
	private View view;//listview 的heard
	private FrameLayout fl_noattestation;//加载的布局
	private TextView tv_jiazai;//加载的文字
	private Exosure_Company exosure_company;//主评论的信息
	private XListView lv_exosure_reply;//listView
	private ExosureReplyAdapter replyAdapter;
	
	private CircularImage  iv_exosure_hrard_user_icon;//用户头像
	private TextView tv_exosure_hrard_user_name;//用户呢称
	private TextView tv_exosure_hrard_listview_terracename;//曝光公司
	private TextView tv_exosure_hrard_listview_companywebsite;//官方网站
	private TextView tv_exosure_hrard_user_content;//曝光内容
	private LinearLayout lin_exosure_hrard_listview1;//图片的布局
	private ImageView iv_exosure_hrard_user_icon1;
	private ImageView iv_exosure_hrard_user_icon2;
	private ImageView iv_exosure_hrard_user_icon3;
	private ImageView iv_exosure_hrard_user_icon4;
	private TextView iv_exosure_hrard_user_addtime;//添加的时间
	private TextView tv_exosure_hrard_listview_crown;//顶的次数
	
	private AsyncBitmapLoader asyn;//下载图片
	private AsyncBitmapLoader2 asyn2;
	private ConnectivityManager manager;//获取网咯的管理器
	private InputMethodManager inputmanger;//键盘管理器
	
	private List<Exosure_Reply> exosure_Replys;
	private long user_id = -1;
	private SharedPreferences preferences2;
	private Map<String , String> comment_map;//操作结果
	
	private FrameLayout fl_exosure_reply_fl22;//分享框
	private RelativeLayout rel_exosure_reply_rl21;//分享框
	private LinearLayout rel_exosure_reply_rl22;//分享框
	private Button reg_btn;//分享到好友
	private Button goto_send_btn;//分享到朋友圈
	private ImageView iv_exosure_reply_share;//分享
	
	private ImageView iv_exosure_reply_speak;//说一说
	private FrameLayout fl_exosure_reply_fl1;//评论框的
	private EditText et_exosure_reply_comment;//输入框
	private ImageView iv_exosure_reply_comments;//我要发布
	private RelativeLayout rel_exosure_reply_rl1;//
	
	private View view2;
	private String company_name;
	private int type = 0;//类型 1 黑平台 2.未认证 3.合规 
	
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			//曝光的回复
			case 0:
				String result = msg.obj.toString();
				if("null".equals(result)){
					Toast.makeText(ExosureReplyActivity.this, "对不起，服务器异常！", Toast.LENGTH_SHORT).show();
				}else{
					exosure_Replys = new JsonUtil().getExsureReply(result);
					replyAdapter = new ExosureReplyAdapter(ExosureReplyActivity.this, exosure_Replys);
					lv_exosure_reply.setAdapter(replyAdapter);
				}
				fl_noattestation.setVisibility(View.GONE);
				if(exosure_Replys!= null&&exosure_Replys.size()>0){
					lv_exosure_reply.removeFooterView(DensityUtil.dip2px(ExosureReplyActivity.this, 60));
				}else{
					lv_exosure_reply.removeFooterView(1);
				}
				break;
			//顶操作的返回结果
			case 1 :
				String result1 = msg.obj.toString();
				if("null".equals(result1)){
					Toast.makeText(ExosureReplyActivity.this, "对不起，服务器异常！", Toast.LENGTH_SHORT).show();
				}else{
					comment_map = new JsonUtil().getOperateResult(result1);
	 				switch (Integer.parseInt(comment_map.get("is_success"))) {
					case 0:
						exosure_company.setTop_num(exosure_company.getTop_num()+1);
						tv_exosure_hrard_listview_crown.setText("顶("+exosure_company.getTop_num()+")");
						Toast.makeText(ExosureReplyActivity.this, "顶成功！", Toast.LENGTH_SHORT).show();
						break;
					case -1:
						Toast.makeText(ExosureReplyActivity.this, "操作失败！", Toast.LENGTH_SHORT).show();
						break;
					case -2:
						Toast.makeText(ExosureReplyActivity.this, "对不起，24小时之内只能顶一次！", Toast.LENGTH_SHORT).show();
						break;
	 				}
				}
 				fl_noattestation.setVisibility(View.GONE);
				break;
			//刷新数据
			case 2:
				String result2 = msg.obj.toString();
				if("null".equals(result2)){
					Toast.makeText(ExosureReplyActivity.this, "对不起，服务器异常！", Toast.LENGTH_SHORT).show();
				}else{
					exosure_Replys = new JsonUtil().getExsureReply(result2);
					replyAdapter = new ExosureReplyAdapter(ExosureReplyActivity.this, exosure_Replys);
					lv_exosure_reply.setAdapter(replyAdapter);
				}
				lv_exosure_reply.stopRefresh();
				lv_exosure_reply.stopLoadMore();
				if(exosure_Replys!= null&&exosure_Replys.size()>0){
					lv_exosure_reply.removeFooterView(DensityUtil.dip2px(ExosureReplyActivity.this, 60));
				}else{
					lv_exosure_reply.removeFooterView(1);
				}
				break;
			//添加更多
			case 3:
				String result3 = msg.obj.toString();
				if("null".equals(result3)){
					lv_exosure_reply.removeFooterView(1);
					Toast.makeText(ExosureReplyActivity.this, "对不起，服务器异常！", Toast.LENGTH_SHORT).show();
				}else{
					List<Exosure_Reply> exosure_Replies = new JsonUtil().getExsureReply(result3);
					if(exosure_Replies!=null &&exosure_Replies.size()>0){
						exosure_Replys.addAll(exosure_Replies);
						replyAdapter.setexosure_companys(exosure_Replys);
						replyAdapter.notifyDataSetChanged();
					}else{
						Toast.makeText(ExosureReplyActivity.this, "没有更多数据", Toast.LENGTH_SHORT).show();
						lv_exosure_reply.removeFooterView(1);
					}
				}
				lv_exosure_reply.stopLoadMore();
				break;
				
			//发表回复
			case 4:
				fl_exosure_reply_fl1.setVisibility(View.GONE);
				inputmanger.hideSoftInputFromWindow(view2.getWindowToken(), 0);
				String result4 = msg.obj.toString();
				if("null".equals(result4)){
					Toast.makeText(ExosureReplyActivity.this, "对不起，服务器异常！", Toast.LENGTH_SHORT).show();
				}else{
					comment_map = new JsonUtil().getOperateResult(result4);
	 				if(Integer.parseInt(comment_map.get("is_success")) == 0){
						Toast.makeText(ExosureReplyActivity.this, "您的评论已经成功提交！", Toast.LENGTH_LONG).show();
						PublishExposal.getExosureReply(exosure_company.getId(),user_id,handler, 0, 1, 10);
					}else{
						Toast.makeText(ExosureReplyActivity.this, "您的评论提价失败！", Toast.LENGTH_LONG).show();
						fl_noattestation.setVisibility(View.GONE);
					}
				}
				break;
			}
		};
	};

	private EditText et_exosure_reply_comment2;

	private ImageView iv_exosure_reply_comment_icon1;

	private RelativeLayout ic_faces;

	private RelativeLayout rel_noattestation_rl3;
	private boolean faces_type = true;// 表情弹窗状态
	private boolean images_type = true;// 上传图片弹窗状态
	private int image_count = 0;//相册图片显示的坐标
	private List<String> image_path = new ArrayList<String>();// 存储的图片路径集合
	private boolean Delete_type=true;//是否删除图片的状态
	
	@SuppressLint("NewApi") @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exosure_reply);
		actionBar = this.getActionBar();
		//设置actionbar
		new HomePageController().setActionbar(LayoutInflater.from(this)
				.inflate(R.layout.comment_reply_actionbar, null), actionBar);
		
		initActionBar();
		getIntents();
		initView();
		initListViewHrard();
		setSpeak();
		setShare();
		
		FaceConversionUtil.getInstace();
		emojis = FaceConversionUtil.emojiLists;
		onCreate();
	}
	
	private void SetLogin(){
		startActivity(new Intent(ExosureReplyActivity.this,MineFragment_Login.class));		
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0){
			if(fl_exosure_reply_fl1 != null && fl_exosure_reply_fl1.getVisibility() == View.VISIBLE){
				fl_exosure_reply_fl1.setVisibility(View.GONE);
			}else{
				ExosureReplyActivity.this.finish();
			}
		}
		return false;
	}
	
	
	//分享
	private void setShare(){
		iv_exosure_reply_share = (ImageView) findViewById(R.id.iv_exosure_reply_share);
//		fl_exosure_reply_fl22 = (FrameLayout) this.findViewById(R.id.fl_exosure_reply_fl22);
//		rel_exosure_reply_rl21 = (RelativeLayout) this.findViewById(R.id.rel_exosure_reply_rl21);
//		rel_exosure_reply_rl22 = (LinearLayout) this.findViewById(R.id.rel_exosure_reply_rl22);
//		reg_btn = (Button) this.findViewById(R.id.reg_btn);
//		goto_send_btn = (Button) this.findViewById(R.id.goto_send_btn);
		
		//选择分享
		iv_exosure_reply_share.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				fl_exosure_reply_fl22.setVisibility(View.VISIBLE);
				StringBuffer sb = new StringBuffer();
				String  url = null;
				switch (type) {
				case 1:
					url = null;
					url = PhpUrl.SouheiSearch1+exosure_company.getCompany_id()+"/p/1.html";
					break;
				case 2:
					url = null;
					url = PhpUrl.SouheiSearch2+exosure_company.getCompany_id()+"/p/1.html";
					break;
				case 3:
					url = null;
					url = PhpUrl.SouheiSearch3+exosure_company.getCompany_id()+"/p/1.html";
					break;
				}
				String title = "搜黑平台查询_全国最大的查询曝光平台_搜黑";
				sb.append(exosure_company.getNickname());
				sb.append("评论了");
				sb.append(company_name);
				sb.append(".");
				UMImage umImage=new UMImage(ExosureReplyActivity.this, WeiXinShareController.myShot(ExosureReplyActivity.this));
				UMShareManager.UMSendShare(ExosureReplyActivity.this, mController, title, url, umImage, sb.toString());
			}
		});
//		fl_exosure_reply_fl22.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//			}
//		});
//		//隐藏分享
//		rel_exosure_reply_rl21.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				fl_exosure_reply_fl22.setVisibility(View.GONE);
//			}
//		});
//		
//		reg_btn.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				fl_exosure_reply_fl22.setVisibility(View.GONE);
//				StringBuffer sb = new StringBuffer();
//				String  url = null;
//				switch (type) {
//				case 1:
//					url = null;
//					url = PhpUrl.SouheiSearch1+exosure_company.getCompany_id()+"/p/1.html";
//					break;
//				case 2:
//					url = null;
//					url = PhpUrl.SouheiSearch2+exosure_company.getCompany_id()+"/p/1.html";
//					break;
//				case 3:
//					url = null;
//					url = PhpUrl.SouheiSearch3+exosure_company.getCompany_id()+"/p/1.html";
//					break;
//				}
//				String title = "搜黑平台查询_全国最大的查询曝光平台_搜黑";
//				Bitmap bitmap = WeiXinShareController.myShot(ExosureReplyActivity.this);
//				sb.append(exosure_company.getNickname());
//				sb.append("评论了");
//				sb.append(company_name);
//				sb.append(".");
////				WeiXinShareController.sendToWeiXin(ExosureReplyActivity.this, WeixinShareManager.WEIXIN_SHARE_TYPE_TALK,
////						url, sb.toString(), title,bitmap);
//			}
//		});
//		goto_send_btn.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				fl_exosure_reply_fl22.setVisibility(View.GONE);
//				String  url = null;
//				switch (type) {
//				case 1:
//					url = null;
//					url = PhpUrl.SouheiSearch1+exosure_company.getCompany_id()+"/p/1.html";
//					break;
//				case 2:
//					url = null;
//					url = PhpUrl.SouheiSearch2+exosure_company.getCompany_id()+"/p/1.html";
//					break;
//				case 3:
//					url = null;
//					url = PhpUrl.SouheiSearch3+exosure_company.getCompany_id()+"/p/1.html";
//					break;
//				}
//				StringBuffer sb = new StringBuffer();
//				String title = "搜黑平台查询_全国最大的查询曝光平台_搜黑";
//				Bitmap bitmap = WeiXinShareController.myShot(ExosureReplyActivity.this);
//				sb.append(exosure_company.getNickname());
//				sb.append("评论了");
//				sb.append(company_name);
//				sb.append(".");
////				WeiXinShareController.sendToWeiXin(ExosureReplyActivity.this, WeixinShareManager.WEIXIN_SHARE_TYPE_FRENDS,
////						url, sb.toString(), title, bitmap);
//			}
//		});
	}
	
	//设置说一说
	private void setSpeak(){
		iv_exosure_reply_speak = (ImageView) findViewById(R.id.iv_exosure_reply_speak);
		fl_exosure_reply_fl1 = (FrameLayout ) findViewById(R.id.fl_exosure_reply_fl1);
		et_exosure_reply_comment = (EditText) findViewById(R.id.et_exosure_reply_comment);
		iv_exosure_reply_comments = (ImageView) findViewById(R.id.iv_exosure_reply_comments);
		rel_exosure_reply_rl1 = (RelativeLayout) findViewById(R.id.rel_exosure_reply_rl1);
		
		//我要说一说
		iv_exosure_reply_speak.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				fl_exosure_reply_fl1.setVisibility(View.VISIBLE);
			}
		});
		
		fl_exosure_reply_fl1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			}
		});
		
		//收起评论框
		rel_exosure_reply_rl1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// 收起软键盘
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(
						et_exosure_reply_comment.getWindowToken(), 0);
				
				LayoutParams lp = rel_noattestation_rl3.getLayoutParams();
				lp.width = LayoutParams.MATCH_PARENT;
				lp.height = 1;
				rel_noattestation_rl3.setLayoutParams(lp);
				fl_exosure_reply_fl1.setVisibility(View.GONE);
				faces_type = true;
				images_type = true;			
			}
		});
		
		iv_exosure_reply_comments.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				boolean loginType = Util.getLoginType(ExosureReplyActivity.this);
				if (loginType) {
					String content = et_exosure_reply_comment.getText().toString();
					if(manager.getActiveNetworkInfo()!= null){
						if(content != null && content.trim().length()>0){
							et_exosure_reply_comment.setText("");
							fl_noattestation.setVisibility(View.VISIBLE);
							PublishExposal.sendExosureComment(user_id, exosure_company.getCompany_id(), exosure_company.getId(), content, handler, 4);
						}else{
							fl_noattestation.setVisibility(View.GONE);
							Toast.makeText(ExosureReplyActivity.this, "请输入评论的内容！", Toast.LENGTH_SHORT).show();
						}
					}else{
						Toast.makeText(ExosureReplyActivity.this, "您的神器好像没有联网！", Toast.LENGTH_SHORT).show();
					}
				}else {
					SetLogin();
				}				
			}
		});
	}
	
	//初始化控件
	private void initView(){
		fl_noattestation = (FrameLayout) findViewById(R.id.fl_noattestation);
		tv_jiazai = (TextView) findViewById(R.id.tv_jiazai);
		lv_exosure_reply = (XListView) findViewById(R.id.lv_exosure_reply);
		lv_exosure_reply.setPullLoadEnable(true);
		lv_exosure_reply.setPullRefreshEnable(true);
		view = LayoutInflater.from(this).inflate(R.layout.exosure_reipy_listview_heard, null);
		lv_exosure_reply.addHeaderView(view);
		
		rel_noattestation_rl3 = (RelativeLayout) this
				.findViewById(R.id.rel_noattestation_rl3);
		//编辑框
		et_exosure_reply_comment2 = (EditText) findViewById(R.id.et_exosure_reply_comment);
		// 表情
		iv_exosure_reply_comment_icon1 = (ImageView) this
						.findViewById(R.id.iv_exosure_reply_comment_icon1);
		// 表情布局
				ic_faces = (RelativeLayout) findViewById(R.id.ic_faces);
				
				// 表情事件
				iv_exosure_reply_comment_icon1
						.setOnClickListener(new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								if (faces_type) {
									// 收起软键盘
									InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
									imm.hideSoftInputFromWindow(
											et_exosure_reply_comment2.getWindowToken(),
											0);
									ic_faces.setVisibility(View.VISIBLE);																
									// 打开布局
									LayoutParams lp = rel_noattestation_rl3
											.getLayoutParams();
									lp.width = LayoutParams.MATCH_PARENT;
									lp.height = LayoutParams.WRAP_CONTENT;
									rel_noattestation_rl3.setLayoutParams(lp);
									faces_type = false;
								} else {						
										ic_faces.setVisibility(View.GONE);																	
									LayoutParams lp = rel_noattestation_rl3
											.getLayoutParams();
									lp.width = LayoutParams.MATCH_PARENT;
									lp.height = 1;
									rel_noattestation_rl3.setLayoutParams(lp);
									faces_type = true;
								}
							}
						});	
				
				// 输入框点击事件
				et_exosure_reply_comment2.setOnTouchListener(new OnTouchListener() {

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
		
		//刷新跟添加更多
		lv_exosure_reply.setXListViewListener(new XListView.IXListViewListener() {
			
			@Override
			public void onRefresh() {
				if(manager.getActiveNetworkInfo() != null ){
					if(exosure_company != null){
							PublishExposal.getExosureReply(exosure_company.getId(),user_id, handler, 2, 1,10 );
					}else{
						lv_exosure_reply.stopRefresh();
					}
				}else{
					lv_exosure_reply.stopRefresh();
					Toast.makeText(ExosureReplyActivity.this, "您的神器好像没有联网！", Toast.LENGTH_SHORT).show();
				}
			}
			
			@Override
			public void onLoadMore() {
				if(manager.getActiveNetworkInfo() != null ){
					if(exosure_Replys!=null){
						if(exosure_Replys.size()<10){
							PublishExposal.getExosureReply(exosure_company.getId(), user_id,handler, 3, exosure_Replys.size()/10+2,10);
						}else{
							if(exosure_Replys.size()==10){
								PublishExposal.getExosureReply(exosure_company.getId(),user_id, handler, 3, exosure_Replys.size()/10+1,10);
							}else{
								PublishExposal.getExosureReply(exosure_company.getId(),user_id, handler, 3, exosure_Replys.size()/10+2,10);
							}
						}
					}else{
						PublishExposal.getExosureReply(exosure_company.getId(),user_id, handler, 2, 1,10 );
					}
				}else{
					lv_exosure_reply.stopLoadMore();
					Toast.makeText(ExosureReplyActivity.this, "您的神器好像没有联网！", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
	}
	
	//初始化listview的heard
	private void initListViewHrard(){
		iv_exosure_hrard_user_icon = (CircularImage) view.findViewById(R.id.iv_exosure_hrard_user_icon);
		tv_exosure_hrard_user_name = (TextView) view.findViewById(R.id.tv_exosure_hrard_user_name);
		tv_exosure_hrard_listview_terracename = (TextView) view.findViewById(R.id.tv_exosure_hrard_listview_terracename);
		tv_exosure_hrard_listview_companywebsite = (TextView) view.findViewById(R.id.tv_exosure_hrard_listview_companywebsite);
		tv_exosure_hrard_user_content = (TextView) view.findViewById(R.id.tv_exosure_hrard_user_content);
		lin_exosure_hrard_listview1 = (LinearLayout) view.findViewById(R.id.lin_exosure_hrard_listview1);
		iv_exosure_hrard_user_icon1 = (ImageView) view.findViewById(R.id.iv_exosure_hrard_user_icon1);
		iv_exosure_hrard_user_icon2 = (ImageView) view.findViewById(R.id.iv_exosure_hrard_user_icon2);
		iv_exosure_hrard_user_icon3 = (ImageView) view.findViewById(R.id.iv_exosure_hrard_user_icon3);
		iv_exosure_hrard_user_icon4 = (ImageView) view.findViewById(R.id.iv_exosure_hrard_user_icon4);
		iv_exosure_hrard_user_addtime = (TextView) view.findViewById(R.id.iv_exosure_hrard_user_addtime);
		tv_exosure_hrard_listview_crown = (TextView) view.findViewById(R.id.tv_exosure_hrard_listview_crown);
		
		Bitmap bitmap = asyn.loaderBitmap(iv_exosure_hrard_user_icon, PhpUrl.AVATARURl+PublishComment.getAvatarUrl(exosure_company.getUser_id())+"/avatar.jpg", new ImageCallBack() {
			
			@Override
			public void imageLoader(ImageView imageView, Bitmap bitmap) {
				if(bitmap != null){
					imageView.setImageBitmap(bitmap);
				}
			}
		}, 0);
		if(bitmap!= null){
			iv_exosure_hrard_user_icon.setImageBitmap(bitmap); 
		}
		tv_exosure_hrard_user_name.setText(exosure_company.getNickname());
		tv_exosure_hrard_listview_terracename.setText(exosure_company.getCompany_name());
		tv_exosure_hrard_listview_companywebsite.setText(exosure_company.getWebsite());
		tv_exosure_hrard_user_content.setText(PublishExposal.getCharSequence(exosure_company, this));
		if(exosure_company.getPic_1() == 0 && exosure_company.getPic_2() == 0&&
				exosure_company.getPic_3() == 0 && exosure_company.getPic_4() == 0&&
						exosure_company.getPic_5() == 0){
				lin_exosure_hrard_listview1.setVisibility(View.GONE);
			}else{
				lin_exosure_hrard_listview1.setVisibility(View.VISIBLE);
				setImageIcon(exosure_company);
			}
		iv_exosure_hrard_user_addtime.setText(PublishComment.getTime(exosure_company.getAdd_time()));
		tv_exosure_hrard_listview_crown.setText("顶("+exosure_company.getTop_num()+")");
		
		//顶的曝光
		tv_exosure_hrard_listview_crown.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(manager.getActiveNetworkInfo() !=null){
					fl_noattestation.setVisibility(View.VISIBLE);
					tv_jiazai.setText("正在去加载");
					PublishExposal.ExosureComtop(user_id, exosure_company.getId(), handler, 1);
				}else{
					Toast.makeText(ExosureReplyActivity.this, "您的神器好像没有联网！", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
	
	//设置图片
	private void setImageIcon(Exosure_Company exosure_Company){
		Bitmap bitmap = null;
		List<String > img_url = new ArrayList<String>();
		if(exosure_Company.getPic_1()!= 0){
			img_url.add(exosure_Company.getPic_1_url());
		}
		if(exosure_Company.getPic_2()!= 0){
			img_url.add(exosure_Company.getPic_2_url());
		}
		if(exosure_Company.getPic_3()!= 0){
			img_url.add(exosure_Company.getPic_3_url());
		}
		if(exosure_Company.getPic_4()!= 0){
			img_url.add(exosure_Company.getPic_4_url());
		}
		if(exosure_Company.getPic_5()!= 0){
			img_url.add(exosure_Company.getPic_5_url());
		}
		switch (img_url.size()) {
		case 1:
			iv_exosure_hrard_user_icon1.setVisibility(View.VISIBLE);
			iv_exosure_hrard_user_icon2.setVisibility(View.GONE);
			iv_exosure_hrard_user_icon3.setVisibility(View.GONE);
			iv_exosure_hrard_user_icon4.setVisibility(View.GONE);
			bitmap = asyn2.loaderBitmap(iv_exosure_hrard_user_icon1, img_url.get(0), new ImageCallBack() {
				
				@Override
				public void imageLoader(ImageView imageView, Bitmap bitmap) {
					if(bitmap!= null){
						imageView.setImageBitmap(bitmap);
					}
				}
			}, 0);
			if(bitmap != null){
				iv_exosure_hrard_user_icon1.setImageBitmap(bitmap);
				bitmap = null;
			}
			 img_url.clear();
			break;
		case 2:
			iv_exosure_hrard_user_icon1.setVisibility(View.VISIBLE);
			iv_exosure_hrard_user_icon2.setVisibility(View.VISIBLE);
			iv_exosure_hrard_user_icon3.setVisibility(View.GONE);
			iv_exosure_hrard_user_icon4.setVisibility(View.GONE);
			bitmap = asyn2.loaderBitmap(iv_exosure_hrard_user_icon1, img_url.get(0), new ImageCallBack() {
				
				@Override
				public void imageLoader(ImageView imageView, Bitmap bitmap) {
					if(bitmap!= null){
						imageView.setImageBitmap(bitmap);
					}
				}
			}, 0);
			if(bitmap != null){
				iv_exosure_hrard_user_icon1.setImageBitmap(bitmap);
				bitmap = null;
			}
			bitmap = asyn2.loaderBitmap(iv_exosure_hrard_user_icon2, img_url.get(1), new ImageCallBack() {
				
				@Override
				public void imageLoader(ImageView imageView, Bitmap bitmap) {
					if(bitmap!= null){
						imageView.setImageBitmap(bitmap);
					}
				}
			}, 0);
			if(bitmap != null){
				iv_exosure_hrard_user_icon2.setImageBitmap(bitmap);
				bitmap = null;
			}
			 img_url.clear();
			break;
		default:
			iv_exosure_hrard_user_icon1.setVisibility(View.VISIBLE);
			iv_exosure_hrard_user_icon2.setVisibility(View.VISIBLE);
			iv_exosure_hrard_user_icon3.setVisibility(View.VISIBLE);
			if(img_url.size()==3){
				iv_exosure_hrard_user_icon4.setVisibility(View.GONE);	
			}else{
				iv_exosure_hrard_user_icon4.setVisibility(View.VISIBLE);
			}
			
			bitmap = asyn2.loaderBitmap(iv_exosure_hrard_user_icon1, img_url.get(0), new ImageCallBack() {
				
				@Override
				public void imageLoader(ImageView imageView, Bitmap bitmap) {
					if(bitmap!= null){
						imageView.setImageBitmap(bitmap);
					}
				}
			}, 0);
			if(bitmap != null){
				iv_exosure_hrard_user_icon1.setImageBitmap(bitmap);
				bitmap = null;
			}
			bitmap = asyn2.loaderBitmap(iv_exosure_hrard_user_icon2, img_url.get(1), new ImageCallBack() {
				
				@Override
				public void imageLoader(ImageView imageView, Bitmap bitmap) {
					if(bitmap!= null){
						imageView.setImageBitmap(bitmap);
					}
				}
			}, 0);
			if(bitmap != null){
				iv_exosure_hrard_user_icon2.setImageBitmap(bitmap);
				bitmap = null;
			}
			bitmap = asyn2.loaderBitmap(iv_exosure_hrard_user_icon3, img_url.get(2), new ImageCallBack() {
				
				@Override
				public void imageLoader(ImageView imageView, Bitmap bitmap) {
					if(bitmap!= null){
						imageView.setImageBitmap(bitmap);
					}
				}
			}, 0);
			if(bitmap != null){
				iv_exosure_hrard_user_icon3.setImageBitmap(bitmap);
				bitmap = null;
			}
			 img_url.clear();
			break;
		}
	
	}
	
	//获取intent的数据
	private void getIntents(){
		asyn = new AsyncBitmapLoader(this);
		asyn2 = new AsyncBitmapLoader2(this);
		manager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
		inputmanger = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
		view2 = getWindow().peekDecorView();
		Intent intent = this.getIntent();
		exosure_company = intent.getParcelableExtra("exosure_company");
		company_name = intent.getStringExtra("company_name");
		type = intent.getIntExtra("type", type);
		preferences2 = this.getSharedPreferences("Login_UserInfo", Context.MODE_PRIVATE);
		user_id = preferences2.getLong("user_id", -1);
		if(exosure_company != null){
			if(manager.getActiveNetworkInfo() != null){
				PublishExposal.getExosureReply(exosure_company.getId(),user_id,handler, 0, 1, 10);
			}else{
				Toast.makeText(ExosureReplyActivity.this, "您的神器好像没有联网！", Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	//初始化actionbar
		private void initActionBar(){
			iv_comment_reply_action_icon = (ImageView) this.findViewById(R.id.iv_comment_reply_action_icon);
			tv_content_centent = (TextView ) this.findViewById(R.id.tv_content_centent);
			tv_content_centent.setText("回复");
			
			iv_comment_reply_action_icon.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					ExosureReplyActivity.this.finish();
				}
			});
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
		private FrameLayout ic_images;
		private ImageView iv_add;

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
			et_sendmessage = (EditText) findViewById(R.id.et_noattestation_comment);
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
			View nullView1 = new View(ExosureReplyActivity.this);
			// 设置透明背景
			nullView1.setBackgroundColor(Color.TRANSPARENT);
			pageViews.add(nullView1);

			// 中间添加表情页

			faceAdapters = new ArrayList<FaceAdapter>();
			for (int i = 0; i < emojis.size(); i++) {
				GridView view = new GridView(ExosureReplyActivity.this);
				FaceAdapter adapter = new FaceAdapter(ExosureReplyActivity.this,
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
			View nullView2 = new View(ExosureReplyActivity.this);
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
				imageView = new ImageView(ExosureReplyActivity.this);
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
						.addFace(ExosureReplyActivity.this, emoji.getId(),
								emoji.getFaceName());
				// Log.i("jay_test--->", spannableString+"");
				et_sendmessage.append("[" + spannableString + "]");
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
				//images_handler.sendMessage(message);
				image_path.clear();
			}
		}
}
