package com.example.black.view;

import java.util.List;

import com.example.black.R;
import com.example.black.lib.model.Hr_Region;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MyWheelAdapter implements WheelViewAdapter {
	private List<Hr_Region> list;
	private Context context;
	private LayoutInflater inflater;

	public MyWheelAdapter(Context context, List<Hr_Region> list) {
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getItemsCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public View getItem(int index, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.activity_mine_address_item,
					null);
			holder = new ViewHolder();
			holder.store_item = (TextView) convertView
					.findViewById(R.id.store_item);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Hr_Region hr_Region = list.get(index);
		holder.store_item.setText(hr_Region.getRegion_name());
		return convertView;
	}

	@Override
	public View getEmptyItem(View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void registerDataSetObserver(DataSetObserver observer) {
		// TODO Auto-generated method stub

	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver observer) {
		// TODO Auto-generated method stub

	}

	class ViewHolder {
		TextView store_item;
	}
}
