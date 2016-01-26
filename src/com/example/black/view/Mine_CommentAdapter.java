package com.example.black.view;

import java.util.ArrayList;
import java.util.List;

import com.example.black.R;
import com.example.black.act.CommentReplyActivity;
import com.example.black.act.PublishComment;
import com.example.black.act.SearchActivity_Images;
import com.example.black.lib.AsyncBitmapLoader2;
import com.example.black.lib.ImageCallBack;
import com.example.black.lib.WhatDayUtil;
import com.example.black.lib.model.Comment_Company2;
import com.example.black.lib.model.Reply_Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Mine_CommentAdapter  extends BaseAdapter {
	private Context context;
	private List<Comment_Company2> companys;
	private AsyncBitmapLoader2 asyn2;
	private LayoutInflater inflater;
	private Handler handler;

	public Mine_CommentAdapter(Context context,List<Comment_Company2> companys) {
		this.context = context;
		this.companys=companys;
		inflater = LayoutInflater.from(context);
		asyn2 = new AsyncBitmapLoader2(context);
	}
	public void sethandler(Handler handler) {
		this.handler=handler;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return companys.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return companys.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = inflater.inflate(
					R.layout.activity_mine_comment_fragment_item, null);
			holder=new ViewHolder();
			holder.rl_all = (RelativeLayout) convertView.findViewById(R.id.rl_all);
			holder.lin_comment_listview1=(LinearLayout) convertView.findViewById(R.id.lin_comment_listview1);
			//楼层数
			holder.tv_build=(TextView) convertView.findViewById(R.id.tv_build);
			//消息图片
			holder.iv_comment_user_icon = (ImageView) convertView
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
			holder.iv_comment_user_addtime=(TextView)convertView.findViewById(R.id.iv_comment_user_addtime);
			convertView.setTag(holder);
		}else {
		holder=	(ViewHolder) convertView.getTag();
		}
		final Comment_Company2 company = companys.get(position);
		
//		if (company.getIs_anonymous() == 1) {
//			holder.tv_comment_user_name.setText("****"
//					+ company.getNickname().substring(
//							company.getNickname().length() - 1,
//							company.getNickname().length()));
//		} else {
//			holder.tv_comment_user_name.setText(company
//					.getNickname());
//		}
		
		if (holder!=null&&company!=null) {
			
			final String parent_content = company.getParent_content();
			
			if (TextUtils.isEmpty(parent_content)) {
				holder.tv_build.setText("评论");
				holder.iv_comment_user_icon.setImageDrawable(context.getResources().getDrawable(R.drawable.message));
			}else {
				holder.tv_build.setText("回复");
				holder.iv_comment_user_icon.setImageDrawable(context.getResources().getDrawable(R.drawable.reply));
			}
			
			if(!company.getCompany_name().equals("null")){
				holder.tv_comment_user_name.setText(company.getCompany_name());
			}
		//Log.i("jay_test", "content------------>"+PublishComment
				//.getCharSequence(company, context));
		holder.tv_comment_user_content.setText(PublishComment
				.getCharSequence1(company, context));
		//holder.tv_comment_user_content.setText(company.getContent());
		
		long add_time = company.getAdd_time();
		holder.iv_comment_user_addtime.setText(WhatDayUtil.isDate(WhatDayUtil.getDateString("yyyy-MM-dd HH:mm:ss",add_time)));
		
		if (company.getPic_1() == 0
				&& company.getPic_2() == 0
				&& company.getPic_3() == 0
				&& company.getPic_4() == 0
				&& company.getPic_5() == 0) {
			holder.lin_comment_listview1.setVisibility(View.GONE);
			//Toast.makeText(context, text, duration)
		} else {
			holder.lin_comment_listview1.setVisibility(View.VISIBLE);
			setImageIcon(company, holder);
		}
		
		if(!company.getCompany_name().equals("null")){
			//查看图片
			holder.lin_comment_listview1
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
								Intent intent = new Intent(context,
										SearchActivity_Images.class);
								List<String> list = new ArrayList<String>();
								list.add(company.getPic_1_url());
								list.add(company.getPic_2_url());
								list.add(company.getPic_3_url());
								list.add(company.getPic_4_url());
								list.add(company.getPic_5_url());
								intent.putStringArrayListExtra("search_images",
										(ArrayList<String>) list);
								context.startActivity(intent);
						}
			});
		}
		
		if(!company.getCompany_name().equals("null")){
			//主题评论页或者评论回复页
			holder.rl_all.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (TextUtils.isEmpty(parent_content)) {
						// 主题回复带评论
						Intent intent = new Intent(context, Reply_Activity.class);
						//intent.putExtra("title_type", 1);
						intent.putExtra("company_top", company);
						intent.putExtra("companys_type", 1);
						intent.putExtra("title_type", 1);
						intent.putExtra("mqtt_push_type", 2);
						context.startActivity(intent);
						//handler.sendEmptyMessage(3);
					} else {
						// 单条回复
						Intent intent = new Intent(context,
								CommentReplyActivity.class);
						intent.putExtra("position", position);
						intent.putExtra("comment_Company2",
								companys.get(position));
						intent.putExtra("company_name", company.getCompany_name());
						intent.putExtra("type", 1);
						intent.putExtra("mqtt_push_type", 2);
	                    context.startActivity(intent);
	                    //CommentReplyActivity.reply_page.startActivityForResult(intent, 300);
	                   // handler.sendEmptyMessage(3);
					}
				}
			});
		}
		}
		return convertView;
	}

	private void setImageIcon(Comment_Company2 company, ViewHolder holder) {
		//Toast.makeText(context, "进来了----->", 0).show();
		Bitmap bitmap=null;
		List<String> img_url = new ArrayList<String>();
		if (company.getPic_1() != 0) {
			img_url.add(company.getPic_1_url());
		}
		if (company.getPic_2() != 0) {
			img_url.add(company.getPic_2_url());
		}
		if (company.getPic_3() != 0) {
			img_url.add(company.getPic_3_url());
		}
		if (company.getPic_4() != 0) {
			img_url.add(company.getPic_4_url());
		}
		if (company.getPic_5() != 0) {
			img_url.add(company.getPic_5_url());
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
		}
	}

	static class ViewHolder {
		RelativeLayout rl_all;
		ImageView iv_comment_user_icon, iv_comment_user_icon1,
				iv_comment_user_icon2, iv_comment_user_icon3,
				iv_comment_user_icon4;
		TextView tv_build, tv_comment_user_name, tv_comment_user_content,
				iv_comment_user_addtime;
		LinearLayout lin_comment_listview1;
	}
}
