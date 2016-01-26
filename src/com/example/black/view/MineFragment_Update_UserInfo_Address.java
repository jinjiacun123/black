package com.example.black.view;

import java.util.ArrayList;
import java.util.List;

import com.example.black.R;
import com.example.black.lib.PullParseService;
import com.example.black.lib.model.Hr_Region;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class MineFragment_Update_UserInfo_Address extends FragmentActivity implements
OnClickListener {
	final List<Hr_Region> list = new ArrayList<Hr_Region>();
	public List<String> citys = new ArrayList<String>();// 存放城市
	private TextView tv_return;
	private ListView lv_address;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mine_update_userinfo_address);
		getData();
		initView();
	}

	// 本地xml文件
	private List<Hr_Region> getData() {
		try {
			return PullParseService.getHr_Region(getResources().getAssets()
					.open("citys.xml"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	private void initView() {
		// 返回
		RelativeLayout iv_ActionBar = (RelativeLayout) findViewById(R.id.iv_ActionBar);
		//整个背景 
		RelativeLayout rl_content = (RelativeLayout) findViewById(R.id.rl_content);
		iv_ActionBar.setVisibility(View.VISIBLE);
		iv_ActionBar.setOnClickListener(this);
		TextView tv_ClassName = (TextView) findViewById(R.id.tv_ClassName);
		tv_ClassName.setText("所在地");
		
		lv_address = (ListView) findViewById(R.id.lv_address);
		final List<Hr_Region> data = getData();
		if (data != null && data.size() > 0) {
			for (Hr_Region hr_Region : data) {
				if (hr_Region.getParent_id().equals("1")) {
					list.add(hr_Region);
				}
			}
			
			final RegionAdapter adapter = new RegionAdapter(
					getApplicationContext(), list);
			lv_address.setAdapter(adapter);
			lv_address.setVisibility(View.VISIBLE);
			lv_address.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					Hr_Region hr_Region2 = list.get(position);
					String region_id = hr_Region2.getRegion_id();
					String region_name = hr_Region2.getRegion_name();
					citys.add(region_name);
					list.clear();
					for (Hr_Region hr_Region : data) {
						if (hr_Region.getParent_id().equals(region_id)) {
							list.add(hr_Region);
						}
					}
					
				 if (citys.size() == 3) {
					 Intent address_data1 = new Intent();
						address_data1.putStringArrayListExtra("citys_list",
								(ArrayList<String>) citys);
						setResult(300, address_data1);
						finish();
					} else if(citys.size()==2&&list.size()==0){
						Intent address_data2 = new Intent();
						address_data2.putStringArrayListExtra("citys_list",
								(ArrayList<String>) citys);
						setResult(300, address_data2);
						finish();
					}else {
						adapter.notifyDataSetChanged();
					}}
			});
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_ActionBar:
			finish();
			break;
		default:
			break;
		}
	}
}
