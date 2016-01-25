package com.example.black.view;


import com.example.black.R;
import com.example.black.lib.AppManager;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 用户注册协议
 * 
 * @author Admin
 * 
 */
public class MineFragment_Clause  extends Activity implements OnClickListener {
	private AppManager manager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mine_fragment_clause);
		initView();
		manager = AppManager.getInstance();
		manager.addActivity(MineFragment_Clause.this);
	}

	private void initView() {
		// 返回
		RelativeLayout iv_ActionBar = (RelativeLayout) findViewById(R.id.iv_ActionBar);
		iv_ActionBar.setVisibility(View.VISIBLE);
		iv_ActionBar.setOnClickListener(this);
		TextView tv_ClassName = (TextView) findViewById(R.id.tv_ClassName);
		tv_ClassName.setText("用户协议");
		// 用户协议
		WebView wv_clause = (WebView) findViewById(R.id.wv_clause);
		wv_clause.loadUrl("file:///android_asset/xieyi.html");
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_ActionBar:
			// 返回
			manager.killActivity(MineFragment_Clause.this);
			break;

		default:
			break;
		}

	}
}
