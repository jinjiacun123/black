package com.example.black.view;

import java.util.List;

import com.example.black.R;
import com.example.black.lib.ImageLoaderUtil;
import com.example.black.lib.model.Company;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class UnitViplistAdapter extends BaseAdapter{
	private Context context;
	private LayoutInflater inflater;
	private List<Company> companyVipList;

	public UnitViplistAdapter(Context context,
			List<Company> companyVipList) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.companyVipList = companyVipList;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return companyVipList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return companyVipList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.activity_unit_vip_item, null);
			holder = new ViewHolder();
			holder.icon = (ImageView)convertView.findViewById(R.id.icon);
			holder.vip_name = (TextView)convertView.findViewById(R.id.vip_name);
			holder.vip_number = (TextView)convertView.findViewById(R.id.vip_number);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		final Company companyVipList2 = companyVipList.get(position);
		if (holder!=null && companyVipList2 != null) {
			String micon = companyVipList2.getLogo_url();
			String vip_name = companyVipList2.getCompany_name();
			
			holder.icon.setVisibility(View.INVISIBLE);
			if (micon != null && !"".equals(micon)) {
				DisplayImageOptions options = ImageLoaderUtil
						.init_DisplayImageOptions(0);
				ImageLoader.getInstance().displayImage(micon,
						holder.icon, options);
				holder.icon.setVisibility(View.VISIBLE);
			}else{
				holder.icon.setVisibility(View.GONE);
			}
			if (vip_name != null && !"".equals(vip_name)) 
				    holder.vip_name.setText(vip_name);
			holder.vip_number.setText(companyVipList2.getMem_sn()+"");
		}
		return convertView;
	}
	class ViewHolder{
		ImageView icon;
		TextView vip_number,vip_name;
	}
}
