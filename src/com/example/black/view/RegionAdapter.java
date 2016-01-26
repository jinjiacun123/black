package com.example.black.view;

import java.util.List;

import com.example.black.R;
import com.example.black.lib.model.Hr_Region;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * —°‘Òµÿ÷∑  ≈‰∆˜
 * 
 * @author Administrator
 * 
 */
public class RegionAdapter extends BaseAdapter {
	private List<Hr_Region> list;
	private Context context;

	public RegionAdapter(Context context, List<Hr_Region> list) {
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
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
		ViewHolder holder;
		if (convertView == null) {
			convertView = LinearLayout.inflate(context,
					R.layout.activity_mine_address_item, null);
			holder = new ViewHolder();
			holder.store_item = (TextView) convertView
					.findViewById(R.id.store_item);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Hr_Region hr_Region = list.get(position);
		holder.store_item.setText(hr_Region.getRegion_name());
		return convertView;
	}

	static class ViewHolder {
		TextView store_item;
	}
}
