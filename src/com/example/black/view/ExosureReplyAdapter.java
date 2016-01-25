package com.example.black.view;

import java.util.List;

import com.example.black.R;
import com.example.black.lib.PublishExposal;
import com.example.black.lib.model.Exosure_Reply;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ExosureReplyAdapter  extends BaseAdapter{
	private Context context;
	private LayoutInflater inflater;
	private List<Exosure_Reply> exosure_Replys;
	
	
	public void setexosure_companys(List<Exosure_Reply> exosure_Replys) {
		this.exosure_Replys = exosure_Replys;
	}
	public ExosureReplyAdapter(Context context,List<Exosure_Reply> exosure_Replys) {
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		this.exosure_Replys = exosure_Replys;
	}
	@Override
	public int getCount() {
		if(exosure_Replys != null && exosure_Replys.size()>0){
			return exosure_Replys.size();
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
		if(exosure_Replys != null && exosure_Replys.size()>0){
			Exosure_Reply exosure_Reply = exosure_Replys.get(position);
			if(convertView == null){
				convertView = inflater.inflate(R.layout.comment_relpy_listview_view, null);
				Holder holder = new Holder();
				holder.lin_comment_reply_lin1 = (LinearLayout) convertView.findViewById(R.id.lin_comment_reply_lin1);
				holder.tv_comment_reply = (TextView) convertView.findViewById(R.id.tv_comment_reply);
				convertView.setTag(holder);
			}
			Holder holder = (Holder) convertView.getTag();
			holder.tv_comment_reply.setText(PublishExposal.getExosureReplyContent(exosure_Reply, context));
			if(position == 0){
				holder.lin_comment_reply_lin1.setBackgroundResource(R.drawable.comment_reply_select_back2);
			}
			
		}else{
			convertView = inflater.inflate(R.layout.comment_relpy_listview_view, null);
		}
//		if(position == 0){
//			convertView.setBackgroundResource(R.drawable.comment_reply_select_back2);;
//		}
		return convertView;
	}

	class Holder{
		TextView tv_comment_reply;
		LinearLayout lin_comment_reply_lin1;
	}
}
