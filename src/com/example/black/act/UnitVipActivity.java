package com.example.black.act;

import java.util.ArrayList;
import java.util.List;

import com.example.black.R;
import com.example.black.lib.HttpUtil;
import com.example.black.lib.JsonUtil;
import com.example.black.lib.NetworkUtil;
import com.example.black.lib.Util;
import com.example.black.lib.model.Company;
import com.example.black.view.UnitViplistAdapter;
import com.example.black.view.custom.DialogUtil;
import com.example.black.view.custom.XListView;
import com.example.black.view.custom.XListView.IXListViewListener;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class UnitVipActivity extends FragmentActivity{
	private List<Company> companyVipList;
	private UnitViplistAdapter adapter;
	private XListView mlistview;
	private int page_count = 1;
	private long company_id;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_unit_vip);
		getintent();
		initView();
		initData();
	}

	private void getintent() {
		// TODO Auto-generated method stub
		Intent intent = this.getIntent();
		company_id = intent.getLongExtra("company_id", 0);
	}

	private void initData() {
		if(NetworkUtil.isNetworkConnected(getApplicationContext())){
			DialogUtil.showProgressDialog(UnitVipActivity.this,"加载中...", 0 );
			new HttpUtil().initVip(company_id, 1 , 10 , handler, 0);
		}else {
			Toast.makeText(UnitVipActivity.this,
					"您的神器好像没有联网！", Toast.LENGTH_SHORT).show();
		}
	}
	
	Handler handler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				//加载数据
				String result = (String) msg.obj;
				companyVipList = new JsonUtil().getMyRankingList(result,0);
				if (companyVipList != null && companyVipList.size() > 0) {
					adapter = new UnitViplistAdapter(
							UnitVipActivity.this, companyVipList);
					mlistview.setAdapter(adapter);
					if (companyVipList.size()<10) {
						mlistview.removeFooterView(1);
						mlistview.setPullLoadEnable(false);
					}else {
						page_count = page_count + 1;
					}
				} else {
					Toast.makeText(UnitVipActivity.this, "服务器连接失败", 0).show();
				}
				DialogUtil.dismissProgressDialog();
				break;
				//刷新
			case 1:
				String result_Refresh = (String) msg.obj;
				List<Company>companyVipList_Refresh = new 
						JsonUtil().getMyRankingList(result_Refresh,0);
				if(companyVipList_Refresh != null && companyVipList_Refresh.size() > 0){
					companyVipList.clear();
					companyVipList.addAll(companyVipList_Refresh);
					adapter = null;
					adapter = new UnitViplistAdapter(
							UnitVipActivity.this, companyVipList);
					mlistview.setAdapter(adapter);
					mlistview.removeFooterView(0);
					mlistview.setPullLoadEnable(true);
					if (companyVipList.size()<10) {
						mlistview.removeFooterView(1);
						mlistview.setPullLoadEnable(false);
					}
				}
				page_count = 1;	
				mlistview.stopRefresh();
				break;
				//加载更多
			case 2:
				String result_more = (String) msg.obj;
				List<Company>companyVipList_more = new 
						JsonUtil().getMyRankingList(result_more,0);
				if(companyVipList_more != null && companyVipList_more.size() > 0){
					companyVipList.addAll(companyVipList_more);
					adapter.notifyDataSetChanged();
					if(companyVipList_more.size() < 10){
						mlistview.removeFooterView(1);
					}
					page_count=page_count+1;
				}else{
					mlistview.removeFooterView(1);
					Toast.makeText(UnitVipActivity.this, "没有更多数据", 0).show();
				}
				mlistview.stopLoadMore();
				break;
			}
		}
	};

	private void initView() {
		// TODO Auto-generated method stub
		mlistview = (XListView)findViewById(R.id.mlistview);
		mlistview.setPullLoadEnable(true);
		mlistview.setFocusable(false);
		mlistview.setPullRefreshEnable(true);	
		mlistview.removeFooterView(0);
		mlistview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				List<Company> list = new ArrayList<>();
				list.add(companyVipList.get(position - 1));
				Util.JumpToResultPage(UnitVipActivity.this, list, 0);
			}
		});
		
		mlistview.setXListViewListener(new IXListViewListener() {
			
			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				if(NetworkUtil.isNetworkConnected(getApplicationContext())){
					new HttpUtil().initVip(company_id, 1 , 10 , handler, 1);
				}else {
					mlistview.stopRefresh();
					Toast.makeText(UnitVipActivity.this,
							"您的神器好像没有联网！", Toast.LENGTH_SHORT).show();
				}
			}
			
			@Override
			public void onLoadMore() {
				// TODO Auto-generated method stub
				if(NetworkUtil.isNetworkConnected(getApplicationContext())){
					new HttpUtil().initVip(company_id, page_count , 10 , handler, 2);
				}else {
					mlistview.stopLoadMore();
					Toast.makeText(UnitVipActivity.this,
							"您的神器好像没有联网！", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		ImageView call_back = (ImageView)findViewById(R.id.call_back);
		call_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				UnitVipActivity.this.finish();
			}
		});
	}
}
