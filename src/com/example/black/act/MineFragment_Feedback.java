package com.example.black.act;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.example.black.R;
import com.example.black.lib.HttpPostThread;
import com.example.black.lib.HttpUrl;
import com.example.black.lib.HttpUtil;
import com.example.black.lib.JsonUtil;
import com.example.black.lib.KeyBoardUtil;
import com.example.black.lib.StrUtil;
import com.example.black.lib.Util;
import com.example.black.lib.model.ExosureImage;
import com.example.black.view.SelectPicPopupWindow;
import com.example.black.view.custom.DialogUtil;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 意见反馈
 * 
 * @author admin
 * 
 */
public class MineFragment_Feedback extends FragmentActivity {

	Handler handler=new Handler(){
		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				//上传图片
				ExosureImage exosureImage=(ExosureImage)msg.obj;
				//Log.i("jay_test", "上传图片--->"+exosureImage.toString());
				if (exosureImage.getIs_sucess() == 0) UpLoadFeedback(Long.valueOf(exosureImage.getId()));
				break;
			case 1:
				//Toast.makeText(getApplicationContext(), "上传图片失败", 0).show();
				break;
			case 2:
				//上传反馈
				String result = (String) msg.obj;
				//Log.i("jay_test", "上传反馈--->"+result);
				int info = JsonUtil.getUpdate_Info(result);
				if(info==0) {
					Toast.makeText(getApplicationContext(), "您的反馈已提交", 0).show();
					ed_content.setText("");
					ed_contact.setText("");
					iv_camera.setImageBitmap(null);
				}else {
					
				}
				DialogUtil.dismissProgressDialog();
			default:
				break;
			}
		};
	};

	private ImageView iv_camera;
	private String image_url;
	private EditText ed_content;
	private EditText ed_contact;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mine_fragment_feedback);
		KeyBoardUtil.set_Hidden_KeyBoard(MineFragment_Feedback.this);
		initView();
	}		

	private void initView() {
		// 返回
		RelativeLayout iv_ActionBar = (RelativeLayout) findViewById(R.id.iv_ActionBar);
		iv_ActionBar.setVisibility(View.VISIBLE);
		// 标题
		TextView tv_ClassName = (TextView) findViewById(R.id.tv_ClassName);
		tv_ClassName.setText("意见反馈");
		// 提交
		TextView iv_home = (TextView) findViewById(R.id.iv_home);
		iv_home.setVisibility(View.VISIBLE);
		iv_home.setText("提交");
		//反馈内容
		ed_content = (EditText) findViewById(R.id.ed_content);
		//邮箱，qq
		ed_contact = (EditText) findViewById(R.id.ed_contact);
		//照相
		iv_camera = (ImageView) findViewById(R.id.iv_camera);
		
		iv_camera.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// 读取相册的图片
				Intent intent = new Intent(MineFragment_Feedback.this,
						SelectPicPopupWindow.class);
				startActivityForResult(intent, 100);
			}
		});
		
		iv_ActionBar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();				
			}
		});
		
		iv_home.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//关闭键盘
				KeyBoardUtil.is_openKeyBoard(getApplicationContext(),
						MineFragment_Feedback.this);
				
			String ed_contact1 = ed_contact.getText().toString();
				String ed_content1 = ed_content.getText().toString();
				if (!TextUtils.isEmpty(ed_contact1)&&!TextUtils.isEmpty(ed_content1)) {
					boolean type=false;
					StrUtil strUtil = new StrUtil();
					//是否是email
					 if(strUtil.isEmail(ed_contact1)) type=true;
					 //是否是phone
					 if (strUtil.isPhone(ed_contact1)) type=true;
					 if (type) {
						 NetworkInfo info = HttpUtil.isNetworkInfo(MineFragment_Feedback.this);
							if (info!=null) {
								DialogUtil.showProgressDialog(MineFragment_Feedback.this, "加载中...", 0);
								if (image_url != null && !"".equals(image_url)) {
									new UploadImageThread(image_url).start();
								}else {
									UpLoadFeedback(0);
								}
							}else {
								Toast.makeText(MineFragment_Feedback.this,
										"您的神器好像没有联网！", Toast.LENGTH_SHORT).show();
								}
							} else {
								Toast.makeText(getApplicationContext(), "联系方式为QQ/邮箱/手机", 0).show();
							}
					}else {
					if (TextUtils.isEmpty(ed_content1)) {
						Toast.makeText(getApplicationContext(), "请输入反馈内容", 0)
								.show();
					} else if (TextUtils.isEmpty(ed_contact1)) {
						Toast.makeText(getApplicationContext(), "请输入联系方式", 0)
								.show();
					}
				}
			}
		});
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == 200) {
			image_url = data.getStringExtra("image_url");
			if (image_url != null && !"".equals(image_url)) {
				setBitmap(iv_camera, image_url);
			}
		}
	}
	
	private void setBitmap(ImageView view, String image_paths){
		Display display = getWindowManager().getDefaultDisplay();
		// 图片质量压缩
				Bitmap bitmap = Util.extractThumbNail(image_paths,
						60, 60, true);
				if (bitmap != null) {
						view.setImageBitmap(bitmap);
				}
	}
	
	private void UpLoadFeedback(long pic_id){
		List<NameValuePair> params=new ArrayList<NameValuePair>();
		JSONObject object=new JSONObject();
		try {
			object.put("content", ed_content.getText().toString());
			object.put("pic", pic_id);
			object.put("userip", "");
			object.put("contact", ed_contact.getText().toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		params.add(new BasicNameValuePair("method",HttpUrl.Ideaback_add_method));
		params.add(new BasicNameValuePair("content",object.toString()));
		  //Log.i("jay_test", "上传反馈参数--->"+params);
		new HttpPostThread(params, handler, 2).start();
	}
	
	class UploadImageThread extends Thread {
		private String image_url;

		public UploadImageThread(String image_url) {
			this.image_url = image_url;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			String upLoad = HttpUtil.UpLoad(image_url, "");
			if (upLoad != null && !"".equals(upLoad)) {
				ExosureImage exosureImage = JsonUtil.getExosureImageId(upLoad);
				if (exosureImage != null) {
					Message message=new Message();
					message.what=0;
					message.obj=exosureImage;
					handler.sendMessage(message);
				}else {
					handler.sendEmptyMessage(1);
				}
			}
		}
	}
}
