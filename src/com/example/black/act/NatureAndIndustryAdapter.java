package com.example.black.act;

import java.util.List;

import com.example.black.R;
import com.example.black.lib.model.NatureAndIndustry;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class NatureAndIndustryAdapter  extends BaseAdapter {

	private List<NatureAndIndustry> list;
	private Context context;

	public NatureAndIndustryAdapter(Context context,
			List<NatureAndIndustry> list) {
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
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = View.inflate(context,
					R.layout.activity_exosure_fragment_item, null);
			holder = new ViewHolder();
			holder.exosure_item = (TextView) convertView
					.findViewById(R.id.exosure_item);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		NatureAndIndustry natureAndIndustry = list.get(position);
		holder.exosure_item.setText(natureAndIndustry.getName());
		return convertView;
	}

	static class ViewHolder {
		TextView exosure_item;
	}
}
