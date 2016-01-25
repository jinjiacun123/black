package com.example.black.view;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.black.R;
import com.example.black.act.ExosureReplyActivity;
import com.example.black.act.PublishComment;
import com.example.black.act.SearchActivity_Images;
import com.example.black.lib.AsyncBitmapLoader;
import com.example.black.lib.AsyncBitmapLoader2;
import com.example.black.lib.ImageCallBack;
import com.example.black.lib.JsonUtil;
import com.example.black.lib.PhpUrl;
import com.example.black.lib.PublishExposal;
import com.example.black.lib.WhatDayUtil;
import com.example.black.lib.model.Comment_Company2;
import com.example.black.lib.model.Exosure_Company;
import com.example.black.lib.model.MineFragment_UserInfo;
import com.example.black.lib.model.Reply_Activity;
import com.example.black.lib.umeng.UMStaticConstant;
import com.example.black.view.custom.CircularImage;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 黑平台的adapter
 * 
 * @author Admin
 * 
 */
public class AdapterExosure extends BaseAdapter {

	private final UMSocialService mController = UMServiceFactory
            .getUMSocialService(UMStaticConstant.DESCRIPTOR);
	private Context context;
	private LayoutInflater inflater;
	private List<Exosure_Company> exosure_Companys;
	private AsyncBitmapLoader asyn;
	private AsyncBitmapLoader2 asyn2;
	private long user_id;
	private Handler handler;
	private long company_id;
	private int postions = -1;
	private String company_name;
	private Map<String, Object> map;
	private int type = 0;//类型 1 黑平台 2.未认证 3.合规 
	private int number;

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

	public int getPostions() {
		return postions;
	}

	public void setCompany_id(long company_id) {
		this.company_id = company_id;
	}

	public void setExosure_Companys(List<Exosure_Company> exosure_Companys) {
		this.exosure_Companys = exosure_Companys;
		
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}
	
	public void setComment_Number(int number){
		this.number=number;
		//Log.i("jay_test", "adapter拿到的次数----->"+number);
	}
	
	public void setResult(String result){
		int content = new JsonUtil().getRowContent(result);
		//Toast.makeText(context, "拿到的数据----->"+content, 0).show();
	}

	public AdapterExosure(Context context,
			List<Exosure_Company> exosure_Companys, Map<String, Object> map,String company_name,int type) {
		this.context = context;
		this.exosure_Companys = exosure_Companys;
		this.company_name = company_name;
		this.type = type;
		inflater = LayoutInflater.from(context);
		asyn = new AsyncBitmapLoader(context);
		asyn2 = new AsyncBitmapLoader2(context);
		if (map != null) {
			this.map = map;
		} else {
			this.map = new HashMap<String, Object>();
		}
	}

	@Override
	public int getCount() {
		if (exosure_Companys != null && exosure_Companys.size() > 0) {
			return exosure_Companys.size();
		} else {
			return 0;
		}
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (exosure_Companys != null && exosure_Companys.size() > 0) {
			final Exosure_Company exosure_Company = exosure_Companys.get(position);
			long id = exosure_Company.getId();
			if (convertView == null) {
				convertView = inflater.inflate(
						R.layout.activity_exosure_listview_view, null);
				Holder holder = new Holder();
				holder.rl_content = (RelativeLayout) convertView.findViewById(R.id.rl_content);
				holder.tv_comment_user=(TextView)convertView.findViewById(R.id.tv_comment_user);
				holder.tv_message_count = (TextView) convertView.findViewById(R.id.tv_message_count);
				holder.rl_count=(RelativeLayout) convertView.findViewById(R.id.rl_count);
				holder.tv_count = (TextView) convertView.findViewById(R.id.tv_count);
				holder.iv_exosure_user_icon = (CircularImage) convertView
						.findViewById(R.id.iv_exosure_user_icon);
				holder.tv_exosure_user_name = (TextView) convertView
						.findViewById(R.id.tv_exosure_user_name);
				holder.tv_exosure_listview_terracename = (TextView) convertView
						.findViewById(R.id.tv_exosure_listview_terracename);
				holder.tv_exosure_listview_companywebsite = (TextView) convertView
						.findViewById(R.id.tv_exosure_listview_companywebsite);
				holder.tv_exosure_user_content = (TextView) convertView
						.findViewById(R.id.tv_exosure_user_content);
				holder.lin_exosure_listview1 = (LinearLayout) convertView
						.findViewById(R.id.lin_exosure_listview1);
				holder.iv_exosure_user_icon1 = (ImageView) convertView
						.findViewById(R.id.iv_exosure_user_icon1);
				holder.iv_exosure_user_icon2 = (ImageView) convertView
						.findViewById(R.id.iv_exosure_user_icon2);
				holder.iv_exosure_user_icon3 = (ImageView) convertView
						.findViewById(R.id.iv_exosure_user_icon3);
				holder.iv_exosure_user_icon4 = (ImageView) convertView
						.findViewById(R.id.iv_exosure_user_icon4);
				holder.iv_exosure_user_addtime = (TextView) convertView
						.findViewById(R.id.iv_exosure_user_addtime);
				holder.tv_exosure_listview_crown = (TextView) convertView
						.findViewById(R.id.tv_exosure_listview_crown);
				holder.lin_exosure_listview2 = (LinearLayout) convertView
						.findViewById(R.id.lin_exosure_listview2);
				holder.tv_exosure_user_content2 = (TextView) convertView
						.findViewById(R.id.tv_exosure_user_content2);
				holder.tv_exosure_user_content3 = (TextView) convertView
						.findViewById(R.id.tv_exosure_user_content3);
				holder.tv_exosure_listview_reply = (TextView) convertView
						.findViewById(R.id.tv_exosure_listview_reply);
				holder.tv_exosure_listview_share = (TextView) convertView
						.findViewById(R.id.tv_exosure_listview_share);
				convertView.setTag(holder);
			}
			final Holder holder = (Holder) convertView.getTag();
			Bitmap bitmap = asyn.loaderBitmap(holder.iv_exosure_user_icon,
					PhpUrl.AVATARURl + getAvatarUrl(exosure_Companys.get(position))
							+ "/avatar.jpg", new ImageCallBack() {

						@Override
						public void imageLoader(ImageView imageView,
								Bitmap bitmap) {
							if (bitmap != null) {
								imageView.setImageBitmap(bitmap);
							}
						}
					}, 0);
			if (bitmap != null) {
				holder.iv_exosure_user_icon.setImageBitmap(bitmap);
			}
			
			//跳转用户资料
			holder.iv_exosure_user_icon.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent=new Intent(context,MineFragment_UserInfo.class);
					intent.putExtra("watch_type", false);
					intent.putExtra("Exosure_Type", true);
					intent.putExtra("user_id", exosure_Company.getUser_id());
					context.startActivity(intent);
				}
			});
			
			if (position==0) {					
				holder.tv_count.setText(number+"");
				holder.rl_count.setVisibility(View.VISIBLE);
			}else {
				holder.rl_count.setVisibility(View.GONE);
			}
			
			//回复数
			long record_count = exosure_Company.getRecord_count();
			if (record_count>0) {
				//有回复
				if (exosure_Company.getV_last_is_anonymous()==1) {
					holder.tv_comment_user.setText("****"
							+ exosure_Company.getV_last_nickname().substring(exosure_Company.getV_last_nickname().length()-1,
									exosure_Company.getV_last_nickname().length())
							);
				}else {
					holder.tv_comment_user.setText(exosure_Company.getV_last_nickname());
				}
				holder.iv_exosure_user_addtime.setText(WhatDayUtil.isDate(WhatDayUtil.getDateString("yyyy-MM-dd HH:mm:ss",exosure_Company.getV_last_time())));
//				if (map.get("R"+id)!=null &&!"".equals(map.get("R" + id))) {
//					List<Comment_Company2> companys = (List<Comment_Company2>) map.get(""+id);
//					if (companys!=null &&companys.size()>0) {
//						Comment_Company2 company2 = companys.get(0);
//						if (company2!=null) {
//							if (company2.getV_last_is_anonymous()==1) {
//								holder.tv_comment_user.setText("****"
//										+ company2.getNickname().substring(company2.getNickname().length()-1,
//												company2.getNickname().length()));
//							}else {
//								holder.tv_comment_user.setText(company2.getNickname());
//							}
//							holder.iv_exosure_user_addtime.setText(WhatDayUtil.isDate(WhatDayUtil.getDateString("yyyy-MM-dd HH:mm:ss",company2.getAdd_time())));
//						}
//					}
//				}
			}else {
				//无回复
				if (exosure_Company.getIs_anonymous()==1) {
					holder.tv_comment_user.setText("****"
							+ exosure_Company.getNickname().substring(exosure_Company.getNickname().length()-1,
									exosure_Company.getNickname().length()));
				}else {
					holder.tv_comment_user.setText(exosure_Company.getNickname());
				}
				holder.iv_exosure_user_addtime.setText(WhatDayUtil.isDate(WhatDayUtil.getDateString("yyyy-MM-dd HH:mm:ss",exosure_Company.getAdd_time())));
			}
			
//			//最后回复时间
//			long last_time = exosure_Company.getLast_time();
//			//回复时间
//			long add_time = exosure_Company.getAdd_time();
//			String format_now = new SimpleDateFormat("yyyy-MM-dd ").format(new Date());
//			//获取时间
//			String time = PublishComment.getTime(add_time);
//			//最后回复时间2(ss:HH:dd)
//			String last_time2 = PublishComment.getTime(last_time);
//			if (last_time>0) {
//					holder.iv_exosure_user_addtime.setText(WhatDayUtil.isDate(WhatDayUtil.getDateString("yyyy-MM-dd HH:mm:ss",last_time)));
//			}else {
//				holder.iv_exosure_user_addtime.setText(WhatDayUtil.isDate(WhatDayUtil.getDateString("yyyy-MM-dd HH:mm:ss",add_time)));
//			}
//			//最后回复人
//			String last_nickname = exosure_Company.getLast_nickname();
//			
//			if (!TextUtils.isEmpty(last_nickname)) {
//				holder.tv_comment_user.setText(last_nickname);
//			}else {
//				holder.tv_comment_user.setText(exosure_Company.getNickname());
//			}
			
			//跳转用户资料
			holder.tv_comment_user.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent=new Intent(context,MineFragment_UserInfo.class);
					intent.putExtra("watch_type", false);
					intent.putExtra("Exosure_Type", true);
					intent.putExtra("user_id", exosure_Company.getUser_id());
					context.startActivity(intent);
				}
			});
			
			holder.tv_message_count.setText(exosure_Company.getRecord_count()+"");
			
			holder.tv_exosure_user_name.setText(exosure_Company.getNickname());
			
			//跳转用户资料
			holder.tv_exosure_user_name.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent=new Intent(context,MineFragment_UserInfo.class);
					intent.putExtra("watch_type", false);
					intent.putExtra("Exosure_Type", true);
					intent.putExtra("user_id", exosure_Company.getUser_id());
					context.startActivity(intent);
				}
			});
			//holder.tv_exosure_listview_terracename.setText(exosure_Company
					//.getCompany_name());
			//holder.tv_exosure_listview_companywebsite.setText(exosure_Company
					//.getWebsite());
			//Log.i("jay_tset", "网友曝光------>"+exosure_Company.getContent());
			holder.tv_exosure_user_content
					.setText(PublishComment.getCharSequence111(exosure_Company,context));
			if ("".equals(exosure_Company.getPic_1_url())
					&& "".equals(exosure_Company.getPic_2_url())
					&& "".equals(exosure_Company.getPic_3_url())
					&& "".equals(exosure_Company.getPic_4_url())
					&& "".equals(exosure_Company.getPic_5_url())) {
				holder.lin_exosure_listview1.setVisibility(View.GONE);
				holder.tv_exosure_user_content.setLines(5);
			} else {
				holder.lin_exosure_listview1.setVisibility(View.VISIBLE);
				holder.tv_exosure_user_content.setLines(3);
				setImageIcon(exosure_Company, holder);
			}

			//查看图片
			holder.lin_exosure_listview1
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							Exosure_Company exosure_Company2 = exosure_Companys
									.get(position);
							if (exosure_Company2 != null) {
								Intent intent = new Intent(context,
										SearchActivity_Images.class);
								List<String> list = new ArrayList<String>();
								list.add(exosure_Company2.getPic_1_url());
								list.add(exosure_Company2.getPic_2_url());
								list.add(exosure_Company2.getPic_3_url());
								list.add(exosure_Company2.getPic_4_url());
								list.add(exosure_Company2.getPic_5_url());
								intent.putStringArrayListExtra("search_images",
										(ArrayList<String>) list);
								context.startActivity(intent);
							}
						}
					});

			//未用到
			if (map.get("R" + id) != null && !"".equals(map.get("R" + id)) && !"null".equals(map.get("R" + id))) {
				int row = Integer.parseInt(map.get("R" + id).toString());
				//Toast.makeText(context,"回复数_-_________>"+row, 0).show();
				if (row == 0) {
					holder.lin_exosure_listview2.setVisibility(View.GONE);
					holder.tv_exosure_listview_reply.setVisibility(View.GONE);
				} else {
					holder.lin_exosure_listview2.setVisibility(View.VISIBLE);
					List<Comment_Company2> comment_Company2s = (List<Comment_Company2>) map
							.get("" + id);
					if (row > 2) {
						//holder.tv_exosure_listview_reply
								//.setVisibility(View.VISIBLE);
						//holder.tv_exosure_listview_reply
								//.setVisibility(View.VISIBLE);
					} else {
						//holder.tv_exosure_listview_reply
								//.setVisibility(View.GONE);
					}

					if (comment_Company2s != null) {
						if (comment_Company2s.size() == 1) {
							Comment_Company2 comment_Company22 = comment_Company2s
									.get(0);
							if (comment_Company22 != null) {
								//holder.tv_exosure_user_content2
										//.setText(PublishComment
												//.getCommentReplyContent(
														//comment_Company22,
														//context));
								//holder.tv_exosure_user_content2
										//.setVisibility(View.VISIBLE);
								holder.tv_exosure_user_content3
										.setVisibility(View.GONE);
							}
						} else if (comment_Company2s.size() == 2) {
							Comment_Company2 comment_Company22 = comment_Company2s
									.get(0);
							Comment_Company2 comment_Company23 = comment_Company2s
									.get(1);
							if (comment_Company22 != null) {
								//holder.tv_exosure_user_content2
										//.setText(PublishComment
												//.getCommentReplyContent(
														//comment_Company22,
														//context));
								//holder.tv_exosure_user_content2
										//.setVisibility(View.VISIBLE);
							}
							if (comment_Company23 != null) {
								//holder.tv_exosure_user_content3
										//.setText(PublishComment
												//.getCommentReplyContent(
														//comment_Company23,
														//context));
								//holder.tv_exosure_user_content3
										//.setVisibility(View.VISIBLE);
							}
						}
					}
					if (row > 2) {
						//holder.tv_exosure_listview_reply.setText("查看更多"
								//+ (row - 2) + "条回复...");
						//holder.tv_exosure_listview_reply
								//.setVisibility(View.VISIBLE);
					}
				}
			} else {
				holder.lin_exosure_listview2.setVisibility(View.GONE);
				//Toast.makeText(context,"回复数_-_________>null", 0).show();
			}

			//holder.tv_exosure_listview_crown.setText("顶("+exosure_Company.getTop_num()+")");
			holder.tv_exosure_listview_crown.setVisibility(View.GONE);
			//分享//未用到
			holder.tv_exosure_listview_share.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					StringBuffer sb = new StringBuffer();
					String title = "搜黑平台查询_全国最大的查询曝光平台_搜黑";
					Bitmap bitmap = null;
					String  url = null;
					switch (type) {
					case 1:
						url = null;
						url = PhpUrl.SouheiSearch1+exosure_Companys.get(position).getCompany_id()+"/p/1.html";
						break;
					case 2:
						url = null;
						url = PhpUrl.SouheiSearch2+exosure_Companys.get(position).getCompany_id()+"/p/1.html";
						break;
					case 3:
						url = null;
						url = PhpUrl.SouheiSearch3+exosure_Companys.get(position).getCompany_id()+"/p/1.html";
						break;
					}
					if(holder.iv_exosure_user_icon1.getDrawable()!=null){
						bitmap = ((BitmapDrawable)holder.iv_exosure_user_icon1.getDrawable()).getBitmap();
					}else if(holder.iv_exosure_user_icon2.getDrawable()!=null){
						bitmap = ((BitmapDrawable)holder.iv_exosure_user_icon2.getDrawable()).getBitmap();
					}else if(holder.iv_exosure_user_icon3.getDrawable()!=null){
						bitmap = ((BitmapDrawable)holder.iv_exosure_user_icon3.getDrawable()).getBitmap();
					}
					sb.append(exosure_Companys.get(position).getNickname());	
					sb.append("评论了");
					sb.append(company_name+".");
					//友盟分享
//					UMShareManager.UMSendShare(context, mController, title, url, bitmap, sb.toString());
					
//					AlertDialog.Builder builder = new AlertDialog.Builder(context).setTitle("微信分享"); 
//					builder.setMessage("请选择分享到微信还是朋友圈");
//					builder.setIcon(R.drawable.search_logo).
//					setPositiveButton("好友", new DialogInterface.OnClickListener() {
//						
//						@Override
//						public void onClick(DialogInterface dialog, int which) {
//							StringBuffer sb = new StringBuffer();
//							String title = "搜黑平台查询_全国最大的查询曝光平台_搜黑";
//							Bitmap bitmap = null;
//							String  url = null;
//							switch (type) {
//							case 1:
//								url = null;
//								url = PhpUrl.SouheiSearch1+exosure_Companys.get(position).getCompany_id()+"/p/1.html";
//								break;
//							case 2:
//								url = null;
//								url = PhpUrl.SouheiSearch2+exosure_Companys.get(position).getCompany_id()+"/p/1.html";
//								break;
//							case 3:
//								url = null;
//								url = PhpUrl.SouheiSearch3+exosure_Companys.get(position).getCompany_id()+"/p/1.html";
//								break;
//							}
//							if(holder.iv_exosure_user_icon1.getDrawable()!=null){
//								bitmap = ((BitmapDrawable)holder.iv_exosure_user_icon1.getDrawable()).getBitmap();
//							}else if(holder.iv_exosure_user_icon2.getDrawable()!=null){
//								bitmap = ((BitmapDrawable)holder.iv_exosure_user_icon2.getDrawable()).getBitmap();
//							}else if(holder.iv_exosure_user_icon3.getDrawable()!=null){
//								bitmap = ((BitmapDrawable)holder.iv_exosure_user_icon3.getDrawable()).getBitmap();
//							}
//							sb.append(exosure_Companys.get(position).getNickname());	
//							sb.append("评论了");
//							sb.append(company_name+".");
////							WeiXinShareController.sendToWeiXin(context, WeixinShareManager.WEIXIN_SHARE_TYPE_TALK,
////									url, sb.toString(), title, bitmap);
//						}
//					}).setNegativeButton("朋友圈", new DialogInterface.OnClickListener() {
//						
//						@Override
//						public void onClick(DialogInterface dialog, int which) {
//							StringBuffer sb = new StringBuffer();
//							String title = "搜黑平台查询_全国最大的查询曝光平台_搜黑";
//							Bitmap bitmap = null;
//							String  url = null;
//							switch (type) {
//							case 1:
//								url = null;
//								url = PhpUrl.SouheiSearch1+exosure_Companys.get(position).getCompany_id()+"/p/1.html";
//								break;
//							case 2:
//								url = null;
//								url = PhpUrl.SouheiSearch2+exosure_Companys.get(position).getCompany_id()+"/p/1.html";
//								break;
//							case 3:
//								url = null;
//								url = PhpUrl.SouheiSearch3+exosure_Companys.get(position).getCompany_id()+"/p/1.html";
//								break;
//							}
//							if(holder.iv_exosure_user_icon1.getDrawable()!=null){
//								bitmap = ((BitmapDrawable)holder.iv_exosure_user_icon1.getDrawable()).getBitmap();
//							}else if(holder.iv_exosure_user_icon2.getDrawable()!=null){
//								bitmap = ((BitmapDrawable)holder.iv_exosure_user_icon2.getDrawable()).getBitmap();
//							}else if(holder.iv_exosure_user_icon3.getDrawable()!=null){
//								bitmap = ((BitmapDrawable)holder.iv_exosure_user_icon3.getDrawable()).getBitmap();
//							}
//							sb.append(exosure_Companys.get(position).getNickname());	
//							sb.append("评论了");
//							sb.append(company_name+".");
////							WeiXinShareController.sendToWeiXin(context, WeixinShareManager.WEIXIN_SHARE_TYPE_FRENDS,
////									url, sb.toString(), title, bitmap);
//						}
//					}).create().show();
				}
			});
			
			//顶//未用到
			holder.tv_exosure_listview_crown.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					postions = position;
					PublishExposal.ExosureComtop(user_id, exosure_Companys.get(position).getId(), handler, 10);
				}
			});
			
			//查看回复
			holder.tv_exosure_listview_reply.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent();
					intent.setClass(context, ExosureReplyActivity.class);
					intent.putExtra("position", position);
					intent.putExtra("exosure_company", exosure_Companys.get(position));
					intent.putExtra("company_name", company_name);
					intent.putExtra("type", type);
					context.startActivity(intent);
				}
			});
			//查看回复
			holder.tv_exosure_user_content.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					//Log.i("Test", "第一层的网友曝光--->"+exosure_Company.toString());
					Intent intent = new Intent();
					intent.setClass(context, Reply_Activity.class);
					intent.putExtra("position", position);
					intent.putExtra("companys_type", 1);
					intent.putExtra("title_type", 2);
					intent.putExtra("mqtt_push_type", 2);
					//楼主评论
					Comment_Company2 company2=new Comment_Company2();
					Log.i("Test", "曝光数据--->"+exosure_Company.toString());
					company2.setId(exosure_Company.getId());
					company2.setUser_id(exosure_Company.getUser_id());
					company2.setCompany_id(exosure_Company.getCompany_id());
					company2.setCompany_name(exosure_Company.getCompany_name());
					company2.setParent_id(exosure_Company.getParent_id());
					company2.setNickname(exosure_Company.getNickname());
					company2.setType(exosure_Company.getType());
					company2.setContent(exosure_Company.getContent());
					company2.setPic_1_url(exosure_Company.getPic_1_url());
					company2.setPic_2_url(exosure_Company.getPic_2_url());
					company2.setPic_3_url(exosure_Company.getPic_3_url());
					company2.setPic_4_url(exosure_Company.getPic_4_url());
					company2.setPic_5_url(exosure_Company.getPic_5_url());
					company2.setPic_1(exosure_Company.getPic_1());
					company2.setPic_2(exosure_Company.getPic_2());
					company2.setPic_3(exosure_Company.getPic_3());
					company2.setPic_4(exosure_Company.getPic_4());
					company2.setPic_5(exosure_Company.getPic_5());
					company2.setIs_validate(exosure_Company.getIs_validate());
					company2.setValidate_time(exosure_Company.getValidate_time());
					company2.setIs_anonymous(exosure_Company.getIs_anonymous());
					company2.setTop_num(exosure_Company.getTop_num());
					company2.setAdd_time(exosure_Company.getAdd_time());
					company2.setIs_delete(exosure_Company.getIs_delete());
					company2.setHas_child_ex(exosure_Company.getHas_child_ex());
					company2.setHas_child(exosure_Company.getHas_child());
					company2.setRecord_count(exosure_Company.getRecord_count());
					intent.putExtra("company_top", company2);
					//Log.i("jay_test", "网友曝光传过去的数据-----aaaa>"+company2);
					//intent.putParcelableArrayListExtra("companys", (ArrayList<? extends Parcelable>) exosure_Companys);
					intent.putExtra("company_name", company_name);
					intent.putExtra("company_id", company_id);
					intent.putExtra("type", type);
					context.startActivity(intent);
				}
			});
			
			//查看回复
			holder.rl_content.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent();
					intent.setClass(context, Reply_Activity.class);
					intent.putExtra("position", position);
					intent.putExtra("companys_type", 1);
					intent.putExtra("title_type", 2);
					intent.putExtra("mqtt_push_type", 2);
					//楼主评论
					Comment_Company2 company2=new Comment_Company2();
					company2.setId(exosure_Company.getId());
					company2.setUser_id(exosure_Company.getUser_id());
					company2.setCompany_id(exosure_Company.getCompany_id());
					company2.setCompany_name(exosure_Company.getCompany_name());
					company2.setParent_id(exosure_Company.getParent_id());
					company2.setNickname(exosure_Company.getNickname());
					company2.setType(exosure_Company.getType());
					company2.setContent(exosure_Company.getContent());
					company2.setPic_1_url(exosure_Company.getPic_1_url());
					company2.setPic_2_url(exosure_Company.getPic_2_url());
					company2.setPic_3_url(exosure_Company.getPic_3_url());
					company2.setPic_4_url(exosure_Company.getPic_4_url());
					company2.setPic_5_url(exosure_Company.getPic_5_url());
					company2.setPic_1(exosure_Company.getPic_1());
					company2.setPic_2(exosure_Company.getPic_2());
					company2.setPic_3(exosure_Company.getPic_3());
					company2.setPic_4(exosure_Company.getPic_4());
					company2.setPic_5(exosure_Company.getPic_5());
					company2.setIs_validate(exosure_Company.getIs_validate());
					company2.setValidate_time(exosure_Company.getValidate_time());
					company2.setIs_anonymous(exosure_Company.getIs_anonymous());
					company2.setTop_num(exosure_Company.getTop_num());
					company2.setAdd_time(exosure_Company.getAdd_time());
					company2.setIs_delete(exosure_Company.getIs_delete());
					company2.setHas_child_ex(exosure_Company.getHas_child_ex());
					company2.setHas_child(exosure_Company.getHas_child());
					company2.setRecord_count(exosure_Company.getRecord_count());
					intent.putExtra("company_top", company2);
					intent.putExtra("company_name", company_name);
					intent.putExtra("company_id", company_id);
					intent.putExtra("type", type);
					context.startActivity(intent);
				}
			});
		}else{
			convertView = inflater.inflate(R.layout.comment_relpy_listview_view, null);
			TextView tv = (TextView)convertView.findViewById(R.id.tv_comment_reply);
			tv.setText("还没有用户曝光过此公司那，我要去曝光一个!");
		}
		return convertView;
	}

	// 设置评论的图片
	private void setImageIcon(Exosure_Company exosure_Company, Holder holder) {
		List<String> img_url = new ArrayList<String>();
		if (!"".equals(exosure_Company.getPic_1_url())) {
			img_url.add(exosure_Company.getPic_1_url());
		}
		if (!"".equals(exosure_Company.getPic_2_url())) {
			img_url.add(exosure_Company.getPic_2_url());
		}
		if (!"".equals(exosure_Company.getPic_3_url())) {
			img_url.add(exosure_Company.getPic_3_url());
		}
		if (!"".equals(exosure_Company.getPic_4_url())) {
			img_url.add(exosure_Company.getPic_4_url());
		}
		if (!"".equals(exosure_Company.getPic_5_url())) {
			img_url.add(exosure_Company.getPic_5_url());
		}
		switch (img_url.size()) {
		case 1:
			holder.iv_exosure_user_icon1.setVisibility(View.VISIBLE);
			holder.iv_exosure_user_icon2.setVisibility(View.GONE);
			holder.iv_exosure_user_icon3.setVisibility(View.GONE);
			holder.iv_exosure_user_icon4.setVisibility(View.GONE);
			Bitmap bitmap = asyn2.loaderBitmap(holder.iv_exosure_user_icon1,
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
				holder.iv_exosure_user_icon1.setImageBitmap(bitmap);
			}
			break;
		case 2:
			holder.iv_exosure_user_icon1.setVisibility(View.VISIBLE);
			holder.iv_exosure_user_icon2.setVisibility(View.VISIBLE);
			holder.iv_exosure_user_icon3.setVisibility(View.GONE);
			holder.iv_exosure_user_icon4.setVisibility(View.GONE);
			Bitmap bitmap3 = asyn2.loaderBitmap(holder.iv_exosure_user_icon1,
					img_url.get(0), new ImageCallBack() {

						@Override
						public void imageLoader(ImageView imageView,
								Bitmap bitmap) {
							if (bitmap != null) {
								imageView.setImageBitmap(bitmap);
							}
						}
					}, 0);
			if (bitmap3 != null) {
				holder.iv_exosure_user_icon1.setImageBitmap(bitmap3);
			}
			Bitmap bitmap2 = asyn2.loaderBitmap(holder.iv_exosure_user_icon2,
					img_url.get(1), new ImageCallBack() {

						@Override
						public void imageLoader(ImageView imageView,
								Bitmap bitmap) {
							if (bitmap != null) {
								imageView.setImageBitmap(bitmap);
							}
						}
					}, 0);
			if (bitmap2 != null) {
				holder.iv_exosure_user_icon2.setImageBitmap(bitmap2);
			}
			break;
		case 3:
			holder.iv_exosure_user_icon1.setVisibility(View.VISIBLE);
			holder.iv_exosure_user_icon2.setVisibility(View.VISIBLE);
			holder.iv_exosure_user_icon3.setVisibility(View.VISIBLE);
			holder.iv_exosure_user_icon4.setVisibility(View.GONE);
			Bitmap bitmap4 = asyn2.loaderBitmap(holder.iv_exosure_user_icon1,
					img_url.get(0), new ImageCallBack() {

						@Override
						public void imageLoader(ImageView imageView,
								Bitmap bitmap) {
							if (bitmap != null) {
								imageView.setImageBitmap(bitmap);
							}
						}
					}, 0);
			if (bitmap4 != null) {
				holder.iv_exosure_user_icon1.setImageBitmap(bitmap4);
			}
			Bitmap bitmap5 = asyn2.loaderBitmap(holder.iv_exosure_user_icon2,
					img_url.get(1), new ImageCallBack() {

						@Override
						public void imageLoader(ImageView imageView,
								Bitmap bitmap) {
							if (bitmap != null) {
								imageView.setImageBitmap(bitmap);
							}
						}
					}, 1);
			if (bitmap5 != null) {
				holder.iv_exosure_user_icon2.setImageBitmap(bitmap5);
			}
			Bitmap bitmap6 = asyn2.loaderBitmap(holder.iv_exosure_user_icon3,
					img_url.get(2), new ImageCallBack() {

						@Override
						public void imageLoader(ImageView imageView,
								Bitmap bitmap) {
							if (bitmap != null) {
								imageView.setImageBitmap(bitmap);
							}
						}
					}, 0);
			if (bitmap6 != null) {
				holder.iv_exosure_user_icon3.setImageBitmap(bitmap6);
			}
			break;
		case 4:
		case 5:
			holder.iv_exosure_user_icon1.setVisibility(View.VISIBLE);
			holder.iv_exosure_user_icon2.setVisibility(View.VISIBLE);
			holder.iv_exosure_user_icon3.setVisibility(View.VISIBLE);
			holder.iv_exosure_user_icon4.setVisibility(View.VISIBLE);
			Bitmap bitmap7 = asyn2.loaderBitmap(holder.iv_exosure_user_icon1,
					img_url.get(0), new ImageCallBack() {

						@Override
						public void imageLoader(ImageView imageView,
								Bitmap bitmap) {
							if (bitmap != null) {
								imageView.setImageBitmap(bitmap);
							}
						}
					}, 0);
			if (bitmap7 != null) {
				holder.iv_exosure_user_icon1.setImageBitmap(bitmap7);
			}
			Bitmap bitmap8 = asyn2.loaderBitmap(holder.iv_exosure_user_icon2,
					img_url.get(1), new ImageCallBack() {

						@Override
						public void imageLoader(ImageView imageView,
								Bitmap bitmap) {
							if (bitmap != null) {
								imageView.setImageBitmap(bitmap);
							}
						}
					}, 0);
			if (bitmap8 != null) {
				holder.iv_exosure_user_icon2.setImageBitmap(bitmap8);
			}
			Bitmap bitmap9 = asyn2.loaderBitmap(holder.iv_exosure_user_icon3,
					img_url.get(2), new ImageCallBack() {

						@Override
						public void imageLoader(ImageView imageView,
								Bitmap bitmap) {
							if (bitmap != null) {
								imageView.setImageBitmap(bitmap);
							}
						}
					}, 0);
			if (bitmap9 != null) {
				holder.iv_exosure_user_icon3.setImageBitmap(bitmap9);
			}
			break;
		}
	}

	// 设置用户评论的内容
	private CharSequence getCharSequence(Exosure_Company exosure_Company) {
		String html = exosure_Company.getContent();
		html = html.replace("[", "<img src='");
		html = html.replace("]", "'/>");
		CharSequence charSequence = Html.fromHtml(html, new Html.ImageGetter() {

			@Override
			public Drawable getDrawable(String source) {
				// 获得系统资源的信息，比如图片信息
				Drawable drawable;
				if ("comment1".equals(source) || "comment2".equals(source)
						|| "comment3".equals(source)) {
					drawable = context.getResources().getDrawable(
							getResourceId(source));
					drawable.setBounds(0, 0, 80, 40);
				} else {
					drawable = context.getResources().getDrawable(
							getResourceId(source));
					drawable.setBounds(0, 0, 40, 40);
				}
				return drawable;
			}
		}, null);
		return charSequence;
	}
	public int getResourceId(String name) {
		try {
			// 根据资源的ID的变量名获得Field的对象，使用反射机制实现的
			Field field = R.drawable.class.getField(name);
			// 取得并返回资源的id的字段（静态变量）的值，使用反射机制
			return Integer.parseInt(field.get(null).toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	// 获取头像的Url
	private String getAvatarUrl(Exosure_Company exosure_company) {
		String url = exosure_company.getUser_id() + "";
		StringBuffer sb = new StringBuffer();
		int j = 0;
		for (int i = 0; i < url.length(); i++) {
			if ((j + 2) < url.length()) {
				sb.append(url.substring(i, j + 2));
			} else {
				sb.append(url.substring(j));
				break;
			}
			sb.append("/");
			j += 2;
		}
		return sb.toString();
	}

	class Holder {
		CircularImage iv_exosure_user_icon;
		TextView tv_count,tv_exosure_user_name,tv_comment_user,tv_message_count;
		TextView tv_exosure_listview_terracename;
		TextView tv_exosure_listview_companywebsite;
		TextView tv_exosure_user_content;
		LinearLayout lin_exosure_listview1;
		ImageView iv_exosure_user_icon1;
		ImageView iv_exosure_user_icon2;
		ImageView iv_exosure_user_icon3;
		ImageView iv_exosure_user_icon4;
		TextView iv_exosure_user_addtime;
		TextView tv_exosure_listview_crown;
		LinearLayout lin_exosure_listview2;
		TextView tv_exosure_user_content2;
		TextView tv_exosure_user_content3;
		TextView tv_exosure_listview_reply;
		TextView tv_exosure_listview_share;
		RelativeLayout rl_count,rl_content;
	}
}
