package com.example.black.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.black.R;
import com.example.black.lib.PullParseService;
import com.example.black.lib.model.Hr_Region;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MineFragment_Update_UserInfo_Address1  extends Activity implements OnWheelChangedListener{
	private WheelView wheel_shen;
	private List<Hr_Region> list_all;//整个城市数据
	private List<Hr_Region> list_shen=new ArrayList<Hr_Region>();//省
	private List<Hr_Region> list_shi=new ArrayList<Hr_Region>();//市
	private List<Hr_Region> list_qu=new ArrayList<Hr_Region>();//市
	private WheelView wheel_shi;
	private WheelView wheel_qu;
	private String region_name;
	private String region_name2;
	private Button bt_ok;
	private String region_name3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_address);
		getData();
		findViewById();
		initView();
	}

	private void getData() {
		try {
			list_all = PullParseService.getHr_Region(getResources()
					.getAssets().open("citys.xml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void fix_shen(){
			for (Hr_Region hr_Region : list_all) {
				if (hr_Region.getParent_id().equals("1")) {
					list_shen.add(hr_Region);
				}}
	}

	private void findViewById() {
		wheel_shen = (WheelView) findViewById(R.id.wheel_shen);
		wheel_shi = (WheelView) findViewById(R.id.wheel_shi);
		wheel_qu = (WheelView) findViewById(R.id.wheel_qu);
		bt_ok = (Button) findViewById(R.id.bt_ok);
		wheel_shen.setVisibleItems(5);
		wheel_shi.setVisibleItems(5);
		wheel_qu.setVisibleItems(5);
		wheel_shen.addChangingListener(this);
		wheel_shi.addChangingListener(this);
		wheel_qu.addChangingListener(this);
	}

	private void initView() {
		fix_shen();
		wheel_shen.setViewAdapter(new MyWheelAdapter(MineFragment_Update_UserInfo_Address1.this,list_shen));
		update_shi();
		update_qu();
		
		bt_ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(MineFragment_Update_UserInfo_Address1.this, region_name+" "+region_name2+" "+region_name3, 0).show();
			}
		});
	}

	@Override
	public void onChanged(WheelView wheel, int oldValue, int newValue) {
		if (wheel==wheel_shen) {
		update_shi();
		}else if(wheel==wheel_shi){
			update_qu();
		}else if(wheel==wheel_qu){
			region_name3 = list_qu.get(wheel_qu.getCurrentItem()).getRegion_name();
		}
	}
	
	private void update_shi(){
		list_shi=new ArrayList<Hr_Region>();
		//当前被选中的省的item
		int currentItem = wheel_shen.getCurrentItem();
		Hr_Region hr_Region = list_shen.get(currentItem);
		String region_id = hr_Region.getRegion_id();
		region_name = hr_Region.getRegion_name();
		for (Hr_Region iterable_element : list_all) {
			if (iterable_element.getParent_id().equals(region_id)) {
				list_shi.add(iterable_element);
			}
		}
		wheel_shi.setViewAdapter(new MyWheelAdapter(MineFragment_Update_UserInfo_Address1.this, list_shi));
	}
	
	private void update_qu(){
		list_qu=new ArrayList<Hr_Region>();
		//当前被选中的市的item
		int currentItem = wheel_shi.getCurrentItem();
		Hr_Region hr_Region = list_shi.get(currentItem);
		String region_id = hr_Region.getRegion_id();
		region_name2 = hr_Region.getRegion_name();
		for (Hr_Region iterable_element : list_all) {
			if (iterable_element.getParent_id().equals(region_id)) {
				list_qu.add(iterable_element);
			}
		}
		wheel_qu.setViewAdapter(new MyWheelAdapter(MineFragment_Update_UserInfo_Address1.this, list_qu));
	}
}
