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
 * �������
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
				//�ϴ�ͼƬ
				ExosureImage exosureImage=(ExosureImage)msg.obj;
				//Log.i("jay_test", "�ϴ�ͼƬ--->"+exosureImage.toString());
				if (exosureImage.getIs_sucess() == 0) UpLoadFeedback(Long.valueOf(exosureImage.getId()));
				break;
			case 1:
				//Toast.makeText(getApplicationContext(), "�ϴ�ͼƬʧ��", 0).show();
				break;
			case 2:
				//�ϴ�����
				String result = (String) msg.obj;
				//Log.i("jay_test", "�ϴ�����--->"+result);
				int info = JsonUtil.getUpdate_Info(result);
				if(info==0) {
					Toast.makeText(getApplicationContext(), "���ķ������ύ", 0).show();
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
		// ����
		RelativeLayout iv_ActionBar = (RelativeLayout) findViewById(R.id.iv_ActionBar);
		iv_ActionBar.setVisibility(View.VISIBLE);
		// ����
		TextView tv_ClassName = (TextView) findViewById(R.id.tv_ClassName);
		tv_ClassName.setText("�������");
		// �ύ
		TextView iv_home = (TextView) findViewById(R.id.iv_home);
		iv_home.setVisibility(View.VISIBLE);
		iv_home.setText("�ύ");
		//��������
		ed_content = (EditText) findViewById(R.id.ed_content);
		//���䣬qq
		ed_contact = (EditText) findViewById(R.id.ed_contact);
		//����
		iv_camera = (ImageView) findViewById(R.id.iv_camera);
		
		iv_camera.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// ��ȡ����ͼƬ
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
				//�رռ���
				KeyBoardUtil.is_openKeyBoard(getApplicationContext(),
						MineFragment_Feedback.this);
				
			String ed_contact1 = ed_contact.getText().toString();
				String ed_content1 = ed_content.getText().toString();
				if (!TextUtils.isEmpty(ed_contact1)&&!TextUtils.isEmpty(ed_content1)) {
					boolean type=false;
					StrUtil strUtil = new StrUtil();
					//�Ƿ���email
					 if(strUtil.isEmail(ed_contact1)) type=true;
					 //�Ƿ���phone
					 if (strUtil.isPhone(ed_contact1)) type=true;
					 if (type) {
						 NetworkInfo info = HttpUtil.isNetworkInfo(MineFragment_Feedback.this);
							if (info!=null) {
								DialogUtil.showProgressDialog(MineFragment_Feedback.this, "������...", 0);
								if (image_url != null && !"".equals(image_url)) {
									new UploadImageThread(image_url).start();
								}else {
									UpLoadFeedback(0);
								}
							}else {
								Toast.makeText(MineFragment_Feedback.this,
										"������������û��������", Toast.LENGTH_SHORT).show();
								}
							} else {
								Toast.makeText(getApplicationContext(), "��ϵ��ʽΪQQ/����/�ֻ�", 0).show();
							}
					}else {
					if (TextUtils.isEmpty(ed_content1)) {
						Toast.makeText(getApplicationContext(), "�����뷴������", 0)
								.show();
					} else if (TextUtils.isEmpty(ed_contact1)) {
						Toast.makeText(getApplicationContext(), "��������ϵ��ʽ", 0)
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
		// ͼƬ����ѹ��
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
		  //Log.i("jay_test", "�ϴ���������--->"+params);
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
