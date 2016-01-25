package com.example.black.view;

import java.util.List;

import com.example.black.R;
import com.example.black.act.PublishComment;
import com.example.black.lib.Util;
import com.example.black.lib.WhatDayUtil;
import com.example.black.lib.model.Comment_Company2;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CommentReplyAdapter  extends BaseAdapter{
	private Context context;
	private LayoutInflater inflater;
	private List<Comment_Company2> comment_Company2s;
	private RelativeLayout rl_right;
	private FrameLayout fl_comment_reply_fl1;
	private EditText Nickname;
	public String new_Nickname;
	
	public void setOnclick(RelativeLayout rl_left,FrameLayout fl_comment_reply_fl1){
		this.rl_right=rl_right;
		this.fl_comment_reply_fl1=fl_comment_reply_fl1;
	}
	
	public void setNickname(EditText Nickname){
		this.Nickname=Nickname;
	}
	
	public String getnew_Nickname(){
		return new_Nickname;
	}
	
	public void setComment_Company2s(List<Comment_Company2> comment_Company2s) {
		this.comment_Company2s = comment_Company2s;
	}
	public CommentReplyAdapter(Context context,List<Comment_Company2> comment_Company2s) {
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		this.comment_Company2s = comment_Company2s;
	}
	@Override
	public int getCount() {
		if(comment_Company2s != null && comment_Company2s.size()>0){
			return comment_Company2s.size();
		}else{
			return 1;
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
	public View getView(int position, View convertView, ViewGroup parent) {
		if(comment_Company2s != null && comment_Company2s.size()>0){
			final Comment_Company2 comment_Company2 = comment_Company2s.get(position);
			if(convertView == null){
				convertView = inflater.inflate(R.layout.comment_relpy_listview_view, null);
				Holder holder = new Holder();
				holder.lin_comment_reply_lin1 = (LinearLayout) convertView.findViewById(R.id.lin_comment_reply_lin1);
				holder.tv_comment_reply = (TextView) convertView.findViewById(R.id.tv_comment_reply);
				holder.tv_addtime=(TextView) convertView.findViewById(R.id.tv_addtime);
				holder.ll_reply=(LinearLayout) convertView.findViewById(R.id.ll_reply);
				convertView.setTag(holder);
			}
			Holder holder = (Holder) convertView.getTag();
			
			long add_time = comment_Company2.getAdd_time();
					holder.tv_addtime.setText(WhatDayUtil.isDate(WhatDayUtil.getDateString("yyyy-MM-dd HH:mm:ss",add_time)));
			holder.tv_comment_reply.setText(PublishComment.getCommentReplyContent(comment_Company2, context));
			holder.lin_comment_reply_lin1.setBackgroundResource(R.drawable.comment_reply_select_back2);
			
				if (position>=0) {
					holder.ll_reply.setVisibility(View.VISIBLE);
				}else {
					holder.ll_reply.setVisibility(View.GONE);
				}
				//次回复点击事件
				holder.ll_reply.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						if (Util.getLoginType(context)) {
							fl_comment_reply_fl1.setVisibility(View.VISIBLE);
							String nickname = comment_Company2.getNickname();
							if (!TextUtils.isEmpty(nickname)) {
								new_Nickname=nickname;
								Nickname.setText("回复 "+nickname+" :");
							}else {
								Toast.makeText(context, "请输入评论的内容！",
										Toast.LENGTH_SHORT).show();
							}
						}else {
							context.startActivity(new Intent(context,MineFragment_Login.class));
						}
					}
				});
				
		}else{
			convertView = inflater.inflate(R.layout.comment_relpy_listview_view, null);
			//Holder.tv_top = (TextView) convertView.findViewById(R.id.tv_top);
			//Holder.tv_top.setVisibility(View.GONE);
		}
		return convertView;
	}

	static class Holder{
		TextView tv_addtime,tv_comment_reply,tv_reply;
		//static TextView tv_top;
		LinearLayout ll_reply,lin_comment_reply_lin1;
	}
}
