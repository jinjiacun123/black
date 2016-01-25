package com.example.black.view;

import java.text.SimpleDateFormat;
import java.util.List;

import com.example.black.R;
import com.example.black.lib.AsyncBitmapLoader;
import com.example.black.lib.ImageCallBack;
import com.example.black.lib.WhatDayUtil;
import com.example.black.lib.model.NewsTextActivity;
import com.example.black.lib.model.News_Company;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 黑平台的adapter
 * @author Admin
 *
 */
public class AdapterNews extends BaseAdapter{

	private Context context ;
	private List<News_Company> news_Companys;
	private LayoutInflater inflater ;
	private AsyncBitmapLoader asyn;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
	private int number;
	
	public void setNews_Companys(List<News_Company> news_Companys) {
		this.news_Companys = news_Companys;
	}
	
	public void setComment_Number(int number){
		this.number=number;
	}

	public AdapterNews(Context context,List<News_Company> news_Companys) {
		 this.context = context ;
		 this.news_Companys = news_Companys;
		 this.inflater = LayoutInflater.from(context);
		 this.asyn = new AsyncBitmapLoader(context);
	}
	
	@Override
	public int getCount() {
		if(news_Companys != null && news_Companys.size()>0){
			return news_Companys.size();
		}else{
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
		if(news_Companys != null && news_Companys.size()>0){
			News_Company news_Company = news_Companys.get(position);
			if(convertView == null){
				convertView = inflater.inflate(R.layout.activity_news_listview_view, null);
				Holder holder = new Holder();
				holder.tv_count  = (TextView) convertView.findViewById(R.id.tv_count);
				holder.rl_count = (RelativeLayout) convertView.findViewById(R.id.rl_count);
				holder.iv_news_listview_icon = (ImageView) convertView.findViewById(R.id.iv_news_listview_icon);
				holder.tv_news_listview_title = (TextView) convertView.findViewById(R.id.tv_news_listview_title);
				holder.tv_news_listview_addtime = (TextView) convertView.findViewById(R.id.tv_news_listview_addtime);
				holder.rel_news_listview_view = (RelativeLayout) convertView.findViewById(R.id.rel_news_listview_view);
				holder.tv_news_suoros = (TextView) convertView.findViewById(R.id.tv_news_suoros);
				convertView.setTag(holder);
			}
			Holder holder = (Holder) convertView.getTag();
			String img_url = news_Company.getPic_url();
			Bitmap bitmap = asyn.loaderBitmap(holder.iv_news_listview_icon, img_url, new ImageCallBack() {
				
				@Override
				public void imageLoader(ImageView imageView, Bitmap bitmap) {
					//Log.i("Test", position+"bitmap2--->"+bitmap);
					if(bitmap!=null){
						imageView.setImageBitmap(bitmap);
					}
				}
			}, 1);
			if(bitmap != null){
				holder.iv_news_listview_icon.setImageBitmap(bitmap);
			}
			
			if (position==0) {				
				holder.tv_count.setText(number+"");
				holder.rl_count.setVisibility(View.VISIBLE);
			}else {
				holder.rl_count.setVisibility(View.GONE);
			}
			
			holder.tv_news_listview_title .setText(news_Company.getTitle());
			holder.tv_news_listview_addtime.setText(WhatDayUtil.getDateString("yyyy-MM-dd HH:mm",news_Company.getAdd_time()));
			holder.tv_news_suoros.setText(news_Company.getSource());
			
			holder.rel_news_listview_view.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent();
					intent.setClass(context, NewsTextActivity.class);
					intent.putExtra("mqtt_push_type", 2);
					intent.putExtra("news_Company", news_Companys.get(position));
					context.startActivity(intent);
				}
			});
		}else{
			convertView = inflater.inflate(R.layout.comment_relpy_listview_view, null);
			TextView tv = (TextView)convertView.findViewById(R.id.tv_comment_reply);
			tv.setText("哎呦，还不错哦，还没有媒体报告过这家公司！");
		}
		return convertView;
	}

	static class Holder{
		RelativeLayout rl_count,rel_news_listview_view;
		ImageView iv_news_listview_icon;
		TextView tv_count,tv_news_listview_title;
		TextView tv_news_listview_addtime;
		TextView tv_news_suoros;
	}
	
}
