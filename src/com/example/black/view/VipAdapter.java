package com.example.black.view;

import java.util.List;

import com.example.black.R;
import com.example.black.lib.model.Company;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class VipAdapter extends BaseAdapter{
	private LayoutInflater inflater;
	private Context context;
	private List<Company> list;
	private ImageView iv_icon;
	private TextView tv_number;
	private int width;

	public VipAdapter(Context context, List<Company> list) {
		this.context = context;
		this.list = list;
		this.width=width;		
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size() > 0 ? list.size() : 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.activity_vip_item, null);
		iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);
		tv_number = (TextView) convertView.findViewById(R.id.tv_number);
		}
		Company company=list.get(position);						
		ImageLoader.getInstance().displayImage(company.getLogo_url(), iv_icon);	
		tv_number.setText(company.getMem_sn());
		return convertView;
	}
}
