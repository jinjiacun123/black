package com.example.black.view;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.black.R;
import com.example.black.act.PublishComment;
import com.example.black.act.SearchActivity_Images;
import com.example.black.lib.AsyncBitmapLoader;
import com.example.black.lib.AsyncBitmapLoader2;
import com.example.black.lib.ImageCallBack;
import com.example.black.lib.MyMap;
import com.example.black.lib.PhpUrl;
import com.example.black.lib.WhatDayUtil;
import com.example.black.lib.model.Comment_Company2;
import com.example.black.lib.model.MineFragment_UserInfo;
import com.example.black.lib.model.Reply_Activity;
import com.example.black.lib.umeng.UMStaticConstant;
import com.example.black.view.custom.CircularImage;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.os.Handler;
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
public class AdapterComment  extends BaseAdapter {
	private final UMSocialService mController = UMServiceFactory
            .getUMSocialService(UMStaticConstant.DESCRIPTOR);
	private final Context context;
	private List<Comment_Company2> comment_Company2s;
	private LayoutInflater inflater;
	private long user_id;
	private Handler handler;
	private AsyncBitmapLoader asyn;
	private AsyncBitmapLoader2 asyn2;
	private int postions = -1;
	private Bitmap bitmap;
	DecimalFormat df = new DecimalFormat("#");
	private Map<String, Object> map;
	private String company_name;
	private ConnectivityManager manager;//获取网咯的管理器
	private int type = 0;//类型 1 黑平台 2.未认证 3.合规 
	private int number;
	private MyMap myMap;

	public void setMap(Map<String, Object> map) {
		this.map = map;		
	}

	public int getPostions() {
		return postions;
	}

	public void setComment_Company2s(List<Comment_Company2> comment_Company2s) {
		this.comment_Company2s = comment_Company2s;
		
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}
	
	public void setComment_Number(int number){
		this.number=number;
	}

	public AdapterComment(Context context,
			List<Comment_Company2> comment_Company2s, Map<String, Object> map,String company_name,int type) {
		this.context = context;
		this.comment_Company2s = comment_Company2s;
		this.company_name = company_name;
		this.type = type;
		
		manager = (ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (map != null) {
			this.map = map;
		} else {
			this.map = new HashMap<String, Object>();
		}
		inflater = LayoutInflater.from(context);
		asyn = new AsyncBitmapLoader(context);
		asyn2 = new AsyncBitmapLoader2(context);
	}

	@Override
	public int getCount() {
		if (comment_Company2s != null && comment_Company2s.size() > 0) {
			return comment_Company2s.size();
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
		if(comment_Company2s != null && comment_Company2s.size()>0){
			final Comment_Company2 comment_Company2 = comment_Company2s.get(position);
			//Log.i("Test", "网友评论-a--->"+comment_Company2.toString());
			final long id = comment_Company2.getId();
			final long company_id = comment_Company2.getCompany_id();
			if (convertView == null) {
				convertView = inflater.inflate(
						R.layout.activity_comment_listview_view, null);
				Holder holder = new Holder();
				holder.rl_content = (RelativeLayout) convertView.findViewById(R.id.rl_content);
				//消息数
				holder.tv_message_count=(TextView) convertView.findViewById(R.id.tv_message_count);
				//最后回复用户名				
				holder.tv_comment_user=(TextView) convertView.findViewById(R.id.tv_comment_user);
				//中间一根线
				holder.tv_90=(TextView) convertView.findViewById(R.id.tv_90);
				holder.rl_count=(RelativeLayout) convertView.findViewById(R.id.rl_count);
				holder.tv_count = (TextView) convertView.findViewById(R.id.tv_count);
				holder.iv_comment_user_icon = (CircularImage) convertView
						.findViewById(R.id.iv_comment_user_icon);
				holder.tv_comment_user_name = (TextView) convertView
						.findViewById(R.id.tv_comment_user_name);
				holder.tv_comment_user_content = (TextView) convertView
						.findViewById(R.id.tv_comment_user_content);
				holder.iv_comment_user_icon1 = (ImageView) convertView
						.findViewById(R.id.iv_comment_user_icon1);
				holder.iv_comment_user_icon2 = (ImageView) convertView
						.findViewById(R.id.iv_comment_user_icon2);
				holder.iv_comment_user_icon3 = (ImageView) convertView
						.findViewById(R.id.iv_comment_user_icon3);
				holder.iv_comment_user_icon4 = (ImageView) convertView
						.findViewById(R.id.iv_comment_user_icon4);
				holder.tv_89 = (TextView) convertView.findViewById(R.id.tv_89);
				holder.iv_comment_user_addtime = (TextView) convertView
						.findViewById(R.id.iv_comment_user_addtime);
				holder.tv_comment_listview_share = (TextView) convertView
						.findViewById(R.id.tv_comment_listview_share);
				holder.tv_comment_listview_crown = (TextView) convertView
						.findViewById(R.id.tv_comment_listview_crown);
				holder.lin_comment_listview1 = (LinearLayout) convertView
						.findViewById(R.id.lin_comment_listview1);
				holder.lin_comment_listview2 = (LinearLayout) convertView
						.findViewById(R.id.lin_comment_listview2);
				holder.tv_comment_user_content2 = (TextView) convertView
						.findViewById(R.id.tv_comment_user_content2);
				holder.tv_comment_user_content3 = (TextView) convertView
						.findViewById(R.id.tv_comment_user_content3);
				holder.tv_comment_listview_reply = (TextView) convertView
						.findViewById(R.id.tv_comment_listview_reply);
				convertView.setTag(holder);
			}
			final Holder holder = (Holder) convertView.getTag();
			
			Bitmap bitmap2 = asyn.loaderBitmap(holder.iv_comment_user_icon,
					PhpUrl.AVATARURl + getAvatarUrl(comment_Company2s.get(position))
							+ "/avatar.jpg", new ImageCallBack() {

						@Override
						public void imageLoader(ImageView imageView,
								Bitmap bitmap) {
							if (bitmap != null) {
								imageView.setImageBitmap(bitmap);
								bitmap = null;
							}
						}
					}, 0);
			if (bitmap2 != null) {
				holder.iv_comment_user_icon.setImageBitmap(bitmap2);
				bitmap2 = null;
			}
			
			//跳转用户资料
			holder.iv_comment_user_icon.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent=new Intent(context,MineFragment_UserInfo.class);
					intent.putExtra("watch_type", false);
					intent.putExtra("user_id", comment_Company2.getUser_id());
					context.startActivity(intent);
				}
			});
			
			//是否匿名
			if (comment_Company2.getIs_anonymous() == 1) {
				holder.tv_comment_user_name.setText("****"
						+ comment_Company2.getNickname().substring(comment_Company2.getNickname().length()-1,
								comment_Company2.getNickname().length()));
			} else {
				holder.tv_comment_user_name.setText(comment_Company2
						.getNickname());
			}
			
			//跳转用户资料
			holder.tv_comment_user_name.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent=new Intent(context,MineFragment_UserInfo.class);
					intent.putExtra("watch_type", false);
					intent.putExtra("user_id", comment_Company2.getUser_id());
					context.startActivity(intent);
				}
			});
			
			if (position==0) {				
				holder.tv_count.setText(number+"");
				holder.rl_count.setVisibility(View.VISIBLE);
			}else {
				holder.rl_count.setVisibility(View.GONE);
			}	
			holder.tv_comment_listview_share.setVisibility(View.INVISIBLE);
			holder.tv_90.setVisibility(View.INVISIBLE);
			holder.tv_message_count.setText(comment_Company2.getRecord_count()+"");
			
			//回复数
			long record_count = comment_Company2.getRecord_count();
			if (record_count>0) {
				//有回复
				if (comment_Company2.getV_last_is_anonymous()==1) {
					holder.tv_comment_user.setText("****"
							+ comment_Company2.getV_last_nickname().substring(comment_Company2.getV_last_nickname().length()-1,
									comment_Company2.getV_last_nickname().length())
							);
				}else {
					holder.tv_comment_user.setText(comment_Company2.getV_last_nickname());
				}
				holder.iv_comment_user_addtime.setText(WhatDayUtil.isDate(WhatDayUtil.getDateString("yyyy-MM-dd HH:mm:ss",comment_Company2.getV_last_time())));
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
//							holder.iv_comment_user_addtime.setText(WhatDayUtil.isDate(WhatDayUtil.getDateString("yyyy-MM-dd HH:mm:ss",company2.getAdd_time())));
//						}
//					}
				//}
			}else {
				//无回复
				if (comment_Company2.getIs_anonymous()==1) {
					holder.tv_comment_user.setText("****"
							+ comment_Company2.getNickname().substring(comment_Company2.getNickname().length()-1,
									comment_Company2.getNickname().length()));
				}else {
					holder.tv_comment_user.setText(comment_Company2.getNickname());
				}
				holder.iv_comment_user_addtime.setText(WhatDayUtil.isDate(WhatDayUtil.getDateString("yyyy-MM-dd HH:mm:ss",comment_Company2.getAdd_time())));
			}
			
			//最后回复时间
//			long last_time = comment_Company2.getV_last_time();
//			long last_time3 = comment_Company2.getLast_time();
//			Log.i("Test", "最后时间-last_time-->"+last_time);
//			Log.i("Test", "最后时间-last_time3-->"+last_time3);
//			//回复时间
//			long add_time = comment_Company2.getAdd_time();
//			//Toast.makeText(context, "last_time-->"+last_time+" "+"add_time"+add_time, 0).show();
//			//Log.i("Test", "Last_time()-->"+PublishComment.getTime2(last_time3));
//			//Log.i("Test", "getV_last_time()-->"+PublishComment.getTime2(last_time));
//			//Log.i("Test", "getAdd_time()-->"+PublishComment.getTime2(add_time));
//			String format_now = new SimpleDateFormat("yyyy-MM-dd ").format(new Date());
//			//获取时间
//			String time = PublishComment.getTime(add_time);
//			//最后回复时间2(ss:HH:dd)
//			String last_time2 = PublishComment.getTime(last_time);
//			if (last_time>0) {
//						holder.iv_comment_user_addtime.setText(WhatDayUtil.isDate(WhatDayUtil.getDateString("yyyy-MM-dd HH:mm:ss",last_time)));
//			}else {
//				//添加时间
//					    holder.iv_comment_user_addtime.setText(WhatDayUtil.isDate(WhatDayUtil.getDateString("yyyy-MM-dd HH:mm:ss",add_time)));
//			}
//			//最后回复人
//			String last_nickname = comment_Company2.getV_last_nickname();
//			//Toast.makeText(context, "---->"+last_nickname, 0).show();
//			if (TextUtils.isEmpty(last_nickname)) {
//				if (comment_Company2.getIs_anonymous() == 1) {
//					//Toast.makeText(context, "---->", 0).show();
//					holder.tv_comment_user.setText("****"
//							+ comment_Company2.getNickname().substring(comment_Company2.getNickname().length()-1,
//									comment_Company2.getNickname().length()));
//				}else {
//					holder.tv_comment_user.setText(last_nickname);
//				}
//			}else {
//				if (comment_Company2.getV_last_is_anonymous() == 1) {
//					//Toast.makeText(context, "---->", 0).show();
//					holder.tv_comment_user.setText("****"
//							+ last_nickname.substring(last_nickname.length()-1,
//									last_nickname.length()));
//				}else {
//					holder.tv_comment_user.setText(last_nickname);
//				}
//			}
			//if (comment_Company2.getRecord_count()>0) {
			//holder.tv_comment_user.setText("XXXXX");
			//holder.tv_comment_user.setText(comment_Company2.getLast_nickname());
		//}else {
			//holder.tv_comment_user.setText(comment_Company2.getNickname()+"");
		//}
			
			holder.tv_comment_user_content.setText(PublishComment.getCharSequence(comment_Company2, context));
			holder.tv_89.setText("");
			
			holder.tv_comment_user_content.setText(PublishComment
					.getCharSequence(comment_Company2, context));
			if (comment_Company2.getPic_1() == 0
					&& comment_Company2.getPic_2() == 0
					&& comment_Company2.getPic_3() == 0
				&& comment_Company2.getPic_4() == 0
					&& comment_Company2.getPic_5() == 0) {
				holder.lin_comment_listview1.setVisibility(View.GONE);
			} else {
				holder.lin_comment_listview1.setVisibility(View.VISIBLE);
				setImageIcon(comment_Company2, holder);
				//holder.lin_comment_listview1.setVisibility(View.GONE);
			}
			
			//查看图片
			holder.lin_comment_listview1
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							Comment_Company2 comment_Company22 = comment_Company2s
									.get(position);
							if (comment_Company22 != null) {
								Intent intent = new Intent(context,
										SearchActivity_Images.class);
								List<String> list = new ArrayList<String>();
								list.add(comment_Company22.getPic_1_url());
								list.add(comment_Company22.getPic_2_url());
								list.add(comment_Company22.getPic_3_url());
								list.add(comment_Company22.getPic_4_url());
								list.add(comment_Company22.getPic_5_url());
								intent.putStringArrayListExtra("search_images",
										(ArrayList<String>) list);							
								context.startActivity(intent);
							}
						}
					});		
			//final Map new_map = (Map) map.get("R" + id);
			
			//未用到
			final List<Comment_Company2> comment_Company222 = (List<Comment_Company2>) map
					.get("" + id);
			//Log.i("jay_test", "评论------------>"+comment_Company222.toString());
			if (map.get("R" + id) != null && !"".equals(map.get("R" + id))) {
				//myMap = new MyMap();
				//myMap.setMap(new_map);
				int row = Integer.parseInt(map.get("R" + id) + "");
				if (row == 0) {
					//Log.i("Test","测试--------------->"+row);
					//holder.lin_comment_listview2.setVisibility(View.GONE);
					//holder.tv_comment_listview_reply.setVisibility(View.GONE);
				} else {
					//holder.lin_comment_listview2.setVisibility(View.VISIBLE);
					//List<Comment_Company2> comment_Company2s = (List<Comment_Company2>) map
							//.get("" + id);
					if (row > 2) {
						//holder.tv_comment_listview_reply
								//.setVisibility(View.VISIBLE);
						//holder.tv_comment_listview_reply
								//.setVisibility(View.VISIBLE);
					} else {
						holder.tv_comment_listview_reply
								.setVisibility(View.GONE);
					}

					if (comment_Company2s != null) {
						if (comment_Company2s.size() == 1) {
							Comment_Company2 comment_Company22 = comment_Company2s
									.get(0);
							if (comment_Company22 != null) {
								//Log.i("jay_test", "测试数据==============>"+PublishComment
												//.getCommentReplyContent(
														//comment_Company22,
														//context));
								//holder.tv_comment_user_content2
										//.setText(PublishComment
												//.getCommentReplyContent(
														//comment_Company22,
														//context));
								//holder.tv_comment_user_content2
										//.setVisibility(View.VISIBLE);
								holder.tv_comment_user_content3
										.setVisibility(View.GONE);
							}else {
								//Log.i("jay_test", "测试数据==============>"+null);
							}
						} else if (comment_Company2s.size() == 2) {
							//Comment_Company2 comment_Company22 = comment_Company2s
									//.get(0);
							//Comment_Company2 comment_Company23 = comment_Company2s
									//.get(1);
							//if (comment_Company22 != null) {
								//holder.tv_comment_user_content2
										//.setText(PublishComment
												//.getCommentReplyContent(
														//comment_Company22,
														//context));
								//holder.tv_comment_user_content2
										//.setVisibility(View.VISIBLE);
							//}
							//if (comment_Company23 != null) {
								//holder.tv_comment_user_content3
										//.setText(PublishComment
												//.getCommentReplyContent(
														//comment_Company23,
														//context));
								//holder.tv_comment_user_content3
										//.setVisibility(View.VISIBLE);
							//}
						}
					}
					//if (row > 2) {
						//holder.tv_comment_listview_reply.setText("查看更多"
								//+ (row - 2) + "条回复...");
						//holder.tv_comment_listview_reply
								//.setVisibility(View.VISIBLE);
					//}
				}
				
				//未用到
				holder.tv_comment_listview_share.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
//						StringBuffer sb = new StringBuffer();
//						String title = "搜黑平台查询_全国最大的查询曝光平台_搜黑";
//						bitmap = null;
//						String  url = null;
//						switch (type) {
//						case 1:
//							url = null;
//							url = PhpUrl.SouheiSearch1+comment_Company2s.get(position).getCompany_id()+"/p/1.html";
//							break;
//						case 2:
//							url = null;
//							url = PhpUrl.SouheiSearch2+comment_Company2s.get(position).getCompany_id()+"/p/1.html";
//							break;
//						case 3:
//							url = null;
//							url = PhpUrl.SouheiSearch3+comment_Company2s.get(position).getCompany_id()+"/p/1.html";
//							break;
//						}
//						if(holder.iv_comment_user_icon1.getDrawable()!=null){
//							bitmap = ((BitmapDrawable)holder.iv_comment_user_icon1.getDrawable()).getBitmap();
//						}else if(holder.iv_comment_user_icon2.getDrawable()!=null){
//							bitmap = ((BitmapDrawable)holder.iv_comment_user_icon2.getDrawable()).getBitmap();
//						}else if(holder.iv_comment_user_icon3.getDrawable()!=null){
//							bitmap = ((BitmapDrawable)holder.iv_comment_user_icon3.getDrawable()).getBitmap();
//						}
//						sb.append(comment_Company2s.get(position).getNickname());	
//						sb.append("评论了");
//						sb.append(company_name+".");
						
						
						
//						UMShareManager.UMSendShare(context, mController, title, url, bitmap, sb.toString());
//						AlertDialog.Builder builder = new AlertDialog.Builder(context).setTitle("微信分享"); 
//						builder.setMessage("请选择分享到微信还是朋友圈");
//						builder.setIcon(R.drawable.search_logo).
//						setPositiveButton("好友", new DialogInterface.OnClickListener() {
//							
//							@Override
//							public void onClick(DialogInterface dialog, int which) {
//								StringBuffer sb = new StringBuffer();
//								String title = "搜黑平台查询_全国最大的查询曝光平台_搜黑";
//								bitmap = null;
//								String  url = null;
//								switch (type) {
//								case 1:
//									url = null;
//									url = PhpUrl.SouheiSearch1+comment_Company2s.get(position).getCompany_id()+"/p/1.html";
//									break;
//								case 2:
//									url = null;
//									url = PhpUrl.SouheiSearch2+comment_Company2s.get(position).getCompany_id()+"/p/1.html";
//									break;
//								case 3:
//									url = null;
//									url = PhpUrl.SouheiSearch3+comment_Company2s.get(position).getCompany_id()+"/p/1.html";
//									break;
//								}
//								if(holder.iv_comment_user_icon1.getDrawable()!=null){
//									bitmap = ((BitmapDrawable)holder.iv_comment_user_icon1.getDrawable()).getBitmap();
//								}else if(holder.iv_comment_user_icon2.getDrawable()!=null){
//									bitmap = ((BitmapDrawable)holder.iv_comment_user_icon2.getDrawable()).getBitmap();
//								}else if(holder.iv_comment_user_icon3.getDrawable()!=null){
//									bitmap = ((BitmapDrawable)holder.iv_comment_user_icon3.getDrawable()).getBitmap();
//								}
//								sb.append(comment_Company2s.get(position).getNickname());	
//								sb.append("评论了");
//								sb.append(company_name+".");
////								WeiXinShareController.sendToWeiXin(context, WeixinShareManager.WEIXIN_SHARE_TYPE_TALK,
////										url, sb.toString(), title, bitmap);
//							}
//						}).setNegativeButton("朋友圈", new DialogInterface.OnClickListener() {
//							
//							@Override
//							public void onClick(DialogInterface dialog, int which) {
//								StringBuffer sb = new StringBuffer();
//								String title = "搜黑平台查询_全国最大的查询曝光平台_搜黑";
//								bitmap = null;
//								String  url = null;
//								switch (type) {
//								case 1:
//									url = null;
//									url = PhpUrl.SouheiSearch1+comment_Company2s.get(position).getCompany_id()+"/p/1.html";
//									break;
//								case 2:
//									url = null;
//									url = PhpUrl.SouheiSearch2+comment_Company2s.get(position).getCompany_id()+"/p/1.html";
//									break;
//								case 3:
//									url = null;
//									url = PhpUrl.SouheiSearch3+comment_Company2s.get(position).getCompany_id()+"/p/1.html";
//									break;
//								}
//								if(holder.iv_comment_user_icon1.getDrawable()!=null){
//									bitmap = ((BitmapDrawable)holder.iv_comment_user_icon1.getDrawable()).getBitmap();
//								}else if(holder.iv_comment_user_icon2.getDrawable()!=null){
//									bitmap = ((BitmapDrawable)holder.iv_comment_user_icon2.getDrawable()).getBitmap();
//								}else if(holder.iv_comment_user_icon3.getDrawable()!=null){
//									bitmap = ((BitmapDrawable)holder.iv_comment_user_icon3.getDrawable()).getBitmap();
//								}
//								sb.append(comment_Company2s.get(position).getNickname());	
//								sb.append("评论了");
//								sb.append(company_name+".");
////								WeiXinShareController.sendToWeiXin(context, WeixinShareManager.WEIXIN_SHARE_TYPE_FRENDS,
////										url, sb.toString(), title, bitmap);
//							}
//						}).create().show();
					}
				});
			} else {
				holder.lin_comment_listview2.setVisibility(View.GONE);			
			}
//			//查看更多回复
//			holder.tv_comment_listview_reply.setOnClickListener(new View.OnClickListener() {
//				
//				@Override
//				public void onClick(View v) {
//					Intent intent = new Intent();
//					intent.putExtra("position", position);
//					intent.putExtra("comment_Company2", comment_Company2s.get(position));
//					intent.putExtra("company_name", company_name);
//					intent.putExtra("type", type);
//					//intent.setClass(context, Reply_Activity.class);
//					intent.setClass(context, CommentReplyActivity.class);
//					context.startActivity(intent);
//				}
//			});
			
			//设置顶的点击事件
//			holder.tv_comment_listview_crown.setOnClickListener(new View.OnClickListener() {
//				
//				@Override
//				public void onClick(View v) {
//						//postions = position;
//						PublishComment.CommentComtop(user_id, comment_Company2s.get(position).getCompany_id(), comment_Company2s.get(position).getId(), handler, 6);
//				}
//			});
			//网友评论查看回复
			holder.tv_comment_user_content.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent();
					intent.putExtra("position", position);					
					//intent.putExtra("comment_Company2", comment_Company2s.get(position));
					//intent.putExtra("myMap", myMap);
					//intent.putExtras(extras);
					intent.putExtra("companys_type", 1);
					intent.putExtra("title_type", 1);
					//intent.putExtra("map", new MyMap().setMap(map));
					//楼主评论
					intent.putExtra("company_top", comment_Company2);
					
					//Log.i("jay_test", "拿到的楼主数据----->"+comment_Company2s.get(position));
					//楼主一下评论
					//intent.putParcelableArrayListExtra("companys", (ArrayList<? extends Parcelable>) map.get("" + id));
					intent.putExtra("company_name", company_name);
					intent.putExtra("company_id", company_id);
					intent.putExtra("type", type);
					intent.putExtra("mqtt_push_type", 2);
					//intent.setClass(context, CommentReplyActivity.class);
					intent.setClass(context, Reply_Activity.class);
					context.startActivity(intent);
				}
			});
			
			//网友评论查看回复
			holder.rl_content.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent();
					intent.putExtra("position", position);					
					intent.putExtra("companys_type", 1);
					intent.putExtra("title_type", 1);
					//楼主评论
					intent.putExtra("company_top", comment_Company2);
					//楼主一下评论
					intent.putExtra("company_name", company_name);
					intent.putExtra("company_id", company_id);
					intent.putExtra("type", type);
					intent.putExtra("mqtt_push_type", 2);
					intent.setClass(context, Reply_Activity.class);
					context.startActivity(intent);
				}
			});
		}
		return convertView;
	}

	// 设置评论的图片
	private void setImageIcon(Comment_Company2 comment_Company2, Holder holder) {
		List<String> img_url = new ArrayList<String>();
		if (comment_Company2.getPic_1() != 0) {
			img_url.add(comment_Company2.getPic_1_url());
		}
		if (comment_Company2.getPic_2() != 0) {
			img_url.add(comment_Company2.getPic_2_url());
		}
		if (comment_Company2.getPic_3() != 0) {
			img_url.add(comment_Company2.getPic_3_url());
		}
		if (comment_Company2.getPic_4() != 0) {
			img_url.add(comment_Company2.getPic_4_url());
		}
		if (comment_Company2.getPic_5() != 0) {
			img_url.add(comment_Company2.getPic_5_url());
		}
		if (img_url!=null &&img_url.size()>0) {
			Log.i("Test", "图片数据AdapterComment--------------->"+img_url);
		}
		switch (img_url.size()) {
		case 1:
			holder.iv_comment_user_icon1.setVisibility(View.VISIBLE);
			holder.iv_comment_user_icon2.setVisibility(View.GONE);
			holder.iv_comment_user_icon3.setVisibility(View.GONE);
			holder.iv_comment_user_icon4.setVisibility(View.GONE);
			bitmap = asyn2.loaderBitmap(holder.iv_comment_user_icon1,
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
				holder.iv_comment_user_icon1.setImageBitmap(bitmap);
				bitmap = null;
			}
			img_url.clear();
			break;
		case 2:
			holder.iv_comment_user_icon1.setVisibility(View.VISIBLE);
			holder.iv_comment_user_icon2.setVisibility(View.VISIBLE);
			holder.iv_comment_user_icon3.setVisibility(View.GONE);
			holder.iv_comment_user_icon4.setVisibility(View.GONE);
			bitmap = asyn2.loaderBitmap(holder.iv_comment_user_icon1,
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
				holder.iv_comment_user_icon1.setImageBitmap(bitmap);
				bitmap = null;
			}
			bitmap = asyn2.loaderBitmap(holder.iv_comment_user_icon2,
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
				holder.iv_comment_user_icon2.setImageBitmap(bitmap);
				bitmap = null;
			}
			img_url.clear();
			break;
		default:
			holder.iv_comment_user_icon1.setVisibility(View.VISIBLE);
			holder.iv_comment_user_icon2.setVisibility(View.VISIBLE);
			holder.iv_comment_user_icon3.setVisibility(View.VISIBLE);
			if (img_url.size() == 3) {
				holder.iv_comment_user_icon4.setVisibility(View.GONE);
			} else {
				holder.iv_comment_user_icon4.setVisibility(View.VISIBLE);
			}

			bitmap = asyn2.loaderBitmap(holder.iv_comment_user_icon1,
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
				holder.iv_comment_user_icon1.setImageBitmap(bitmap);
				bitmap = null;
			}
			bitmap = asyn2.loaderBitmap(holder.iv_comment_user_icon2,
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
				holder.iv_comment_user_icon2.setImageBitmap(bitmap);
				bitmap = null;
			}
			bitmap = asyn2.loaderBitmap(holder.iv_comment_user_icon3,
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
				holder.iv_comment_user_icon3.setImageBitmap(bitmap);
				bitmap = null;
			}
			img_url.clear();
			break;
		// case 4:
		// case 5:
		// holder.iv_comment_user_icon1.setVisibility(View.VISIBLE);
		// holder.iv_comment_user_icon2.setVisibility(View.VISIBLE);
		// holder.iv_comment_user_icon3.setVisibility(View.VISIBLE);
		// holder.iv_comment_user_icon4.setVisibility(View.VISIBLE);
		// asyn.loaderBitmap(holder.iv_comment_user_icon1, img_url.get(0), new
		// ImageCallBack() {
		//
		// @Override
		// public void imageLoader(ImageView imageView, Bitmap bitmap) {
		// if(bitmap!= null){
		// imageView.setImageBitmap(bitmap);
		// }
		// }
		// }, 0);
		// asyn.loaderBitmap(holder.iv_comment_user_icon2, img_url.get(1), new
		// ImageCallBack() {
		//
		// @Override
		// public void imageLoader(ImageView imageView, Bitmap bitmap) {
		// if(bitmap!= null){
		// imageView.setImageBitmap(bitmap);
		// }
		// }
		// }, 0);
		// asyn.loaderBitmap(holder.iv_comment_user_icon3, img_url.get(2), new
		// ImageCallBack() {
		//
		// @Override
		// public void imageLoader(ImageView imageView, Bitmap bitmap) {
		// if(bitmap!= null){
		// imageView.setImageBitmap(bitmap);
		// }
		// }
		// }, 0);
		// break;
		}
	}

	// 获取头像的Url
	private String getAvatarUrl(Comment_Company2 comment_Company2) {
		String url = comment_Company2.getUser_id() + "";
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
		TextView tv_message_count,tv_comment_user,tv_count,tv_comment_user_name;
		TextView tv_comment_user_content;
		CircularImage iv_comment_user_icon;
		ImageView iv_comment_user_icon1;
		ImageView iv_comment_user_icon2;
		ImageView iv_comment_user_icon3;
		ImageView iv_comment_user_icon4;
		TextView tv_89,tv_90;
		TextView iv_comment_user_addtime;
		TextView tv_comment_listview_share;
		TextView tv_comment_listview_crown;
		LinearLayout lin_comment_listview1;
		LinearLayout lin_comment_listview2;
		TextView tv_comment_user_content2;
		TextView tv_comment_user_content3;
		TextView tv_comment_listview_reply;
		RelativeLayout rl_count,rl_content;
	}
}
