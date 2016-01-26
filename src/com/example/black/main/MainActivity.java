package com.example.black.main;

import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings.Secure;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.black.R;
/*lib*/
import com.example.black.lib.HomePageFragment;
import com.example.black.lib.RankingListFragment;
import com.example.black.lib.ExosureFragment;
import com.example.black.lib.MineFragment;
import com.example.black.lib.Util;
import com.example.black.lib.NetworkUtil;
import com.example.black.lib.HttpUtil;
import com.example.black.lib.PushInfo;
import com.example.black.lib.JsonUtil;
/*service*/
import com.example.black.service.PushService;


 
public class MainActivity extends FragmentActivity {
	
	private FragmentTabHost f_tab_host;
	private String[] tab_tag = { "home_page",
			                     "ranking_list", 
			                     "exosure", 
			                     "mine", };
	
	private String[] tab_text = { "首页",
			                      "排行榜",
			                      "曝光", 
			                      "我的" };
	
	
	private static Class[] cla = { HomePageFragment.class,
								   RankingListFragment.class,
								   ExosureFragment.class, 
								   MineFragment.class };
	
	// 没有被选中的图片的id
	private int[] imageid = { R.drawable.homepage_a,
			                  R.drawable.homepage_a, 
			                  R.drawable.exosure_a,
			                  R.drawable.mine_a };
	
	private LayoutInflater           inflater;// 布局解析器
	private SharedPreferences        preferences;
	private SharedPreferences.Editor editor;
	private String                   main_type = "";
	
	//推送
	private String m_device_id;
	private long   user_id;
	private String token;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        initDate();
		initPush();
		connect();
    }
    
    private void initDate() {
		inflater   = LayoutInflater.from(MainActivity.this);
		f_tab_host = (FragmentTabHost) this.findViewById(android.R.id.tabhost);
		f_tab_host.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
		
		preferences = this.getSharedPreferences("Statuse",
				Context.MODE_PRIVATE);
		editor      = preferences.edit();

		for (int i = 0; i < tab_text.length; i++) {
			f_tab_host.addTab(
					f_tab_host.newTabSpec(this.tab_tag[i])
					          .setIndicator(getView(i)),
					                        MainActivity.cla[i], 
					                        null);
		}
	}
    
    private void initPush() {
		m_device_id = Secure.getString(this.getContentResolver(), Secure.ANDROID_ID);
		user_id = Util.getShare_User_id(getApplicationContext());
		token = "souhei/" + user_id + "_" + m_device_id;  
		if(Util.getLoginType(getApplicationContext())){
			if(NetworkUtil.isNetworkConnected(getApplicationContext())){
				new HttpUtil().initPush(user_id, 0,  token, handler, 0);
			}
		}
	}
    
    private void connect() {
		PushService.actionStart(getApplicationContext());
	}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    Handler handler = new Handler(){
		@Override
		public void handleMessage(android.os.Message msg){
			switch(msg.what){
			case 0:
				String result = msg.obj.toString();
				final PushInfo pushInfo = JsonUtil.getPushInfo(result);
				if(pushInfo != null){
					switch(pushInfo.getIs_sucess()){
					case 0:
						Log.i("push", "success" + pushInfo.getIs_sucess());
						break;
					case -1:
						Log.i("push", "failure" + pushInfo.getIs_sucess());
						break;
					}
				}
			}
		}
	};
    
	
	private View getView(int i) {
		View view = inflater.inflate(R.layout.activity_main_tabview, null);
		TextView tabtext = (TextView) view.findViewById(R.id.tvtext);
		ImageView tabicon = (ImageView) view.findViewById(R.id.tabicon);
		tabtext.setText(this.tab_text[i]);
		switch (i) {
		case 0:
			tabicon.setBackgroundResource(R.drawable.selector_tab_homepage);
			break;
		case 1:
			tabicon.setBackgroundResource(R.drawable.selector_tab_rankinglist);
			break;
		case 2:
			tabicon.setBackgroundResource(R.drawable.selector_tab_exosure);
			break;
		case 3:
			tabicon.setBackgroundResource(R.drawable.selector_tab_mine);
			break;
		}
		return view;
	}
}
