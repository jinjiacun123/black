package com.example.black.view;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.example.black.R;
import com.example.black.lib.AsyncBitmapLoader;
import com.example.black.lib.HttpPostThread;
import com.example.black.lib.HttpUrl;
import com.example.black.lib.HttpUtil;
import com.example.black.lib.ImageCallBack;
import com.example.black.lib.ImageCompress;
import com.example.black.lib.ImageCompressHttp;
import com.example.black.lib.JsonUtil;
import com.example.black.lib.KeyBoardUtil;
import com.example.black.lib.Md5;
import com.example.black.lib.NetworkUtil;
import com.example.black.lib.PhpUrl;
import com.example.black.lib.TimeUtils;
import com.example.black.view.custom.CircularImage;
import com.example.black.view.custom.DialogUtil;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

/**
 * �޸ĸ�����Ϣ
 * 
 * @author Admin
 * 
 */
@SuppressLint("SimpleDateFormat")
public class MineFragment_Update_UserInfo  extends FragmentActivity implements
OnClickListener {
	private static final int IMAGE_REQUEST_CODE = 0;
    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int RESULT_REQUEST_CODE = 2;
private TextView birthday_value;
private RadioGroup rg_sex;
private int sex = 1;// �Ա�
private EditText name_value;
private TextView phone_value;
private EditText work_value;
private TextView address_value;
private ProgressBar pb_update;
private String uid;
private ImageView iv_submit;
private boolean name_type = false;// �ǳ�
private boolean bri_type = false;// ����
private boolean work_type = false;// ����
private boolean address_type = false;// ��ַ
private CircularImage icon_value;//�û�ͷ��
private AsyncBitmapLoader asyn;//ͼƬ����
private RelativeLayout rl_icon;//ͷ�񲼾�

// ����ͷ��
Handler image_handler = new Handler() {
	@Override
	public void handleMessage(android.os.Message msg) {
		switch (msg.what) {
		case 0:
			int update_icon = JsonUtil.getUpdate_icon((String) msg.obj);
			switch (update_icon) {
			case 1:
				Toast.makeText(getApplicationContext(), "�û�ͷ����³ɹ�", 0)
						.show();
				Intent data = new Intent();
				data.putExtra("Update_Type", true);
				setResult(200, data);
				break;
			case 0:
				Toast.makeText(getApplicationContext(), "�û�ͷ�����ʧ��", 0)
						.show();
				break;
			case -1:
				Toast.makeText(getApplicationContext(), "����Ĳ������ڿ�ֵ", 0)
						.show();
				break;
			case -2:
				Toast.makeText(getApplicationContext(), "safekey�������Ϸ�", 0)
						.show();
				break;
			case -3:
				Toast.makeText(getApplicationContext(), "�û�������", 0).show();
				break;
			case -4:
				Toast.makeText(getApplicationContext(), "ͷ���ļ�����ʧ��", 0)
						.show();
				break;
			case -5:
				Toast.makeText(getApplicationContext(),
						"ͷ���ļ�����ָ����С���ƣ��ݶ�20KB��", 0).show();
				break;
			default:
				break;
			}
			DialogUtil.dismissProgressDialog();
			break;
		default:
			break;
		}
	};
};

//�����û���Ϣ
Handler handler = new Handler() {
	@Override
	public void handleMessage(android.os.Message msg) {
		switch (msg.what) {
		case 0:
			String result = (String) msg.obj;
			//Log.i("�õ�������--->", result);
			int find_Pwd = JsonUtil.getUpdate_Info(result);
			if (find_Pwd == 0) {
				Toast.makeText(getApplicationContext(), "�޸ĳɹ�", 0).show();
				Intent data = new Intent();
				data.putExtra("Update_Type", true);
				data.putExtra("Update_work", work_value.getText()
						.toString());
				setResult(200, data);
				finish();
			} else if (find_Pwd == -1) {
				Toast.makeText(getApplicationContext(), "�޸�ʧ��", 0).show();
			} else {
				Toast.makeText(getApplicationContext(), "����������ʧ��", 0)
						.show();
			}
			iv_submit.setClickable(true);
			//pb_update.setVisibility(View.INVISIBLE);
			DialogUtil.dismissProgressDialog();
			break;
		case 1 :
			
			break;
		default:
			break;
		}
	};
};
private RadioButton rb_man;
private RadioButton rb_woman;

@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_mine_update_userinfo);
	// ��ֹ�ı��༭������
	KeyBoardUtil.set_Hidden_KeyBoard(MineFragment_Update_UserInfo.this);
	initView();
	// ����ϸ�ҳ���ֵ
	GetIntent();
}



private void GetIntent() {
	Intent intent = getIntent();
	if (intent != null) {
		uid = intent.getStringExtra("uid");
		String name_value1 = intent.getStringExtra("name_value");//name
		String phone_value1 = intent.getStringExtra("phone_value");//phone
		String sex_value1 = intent.getStringExtra("sex_value");//sex
		String birthday_value1 = intent.getStringExtra("birthday_value");//birthday
		String work_value1 = intent.getStringExtra("work_value");//work
		String address_value1 = intent.getStringExtra("address_value");//address
		
		if (!TextUtils.isEmpty(name_value1)) name_value.setText(name_value1);
		if (!TextUtils.isEmpty(phone_value1)) phone_value.setText(phone_value1);
		if (!TextUtils.isEmpty(sex_value1)) if(Integer.valueOf(sex_value1) == 0) rb_woman.setChecked(true); else rb_man.setChecked(true);
		if (!TextUtils.isEmpty(address_value1)) address_value.setText(address_value1);
		if (!TextUtils.isEmpty(birthday_value1)) birthday_value.setText(birthday_value1);
		if (!TextUtils.isEmpty(work_value1)&&work_value1.equals("δ��д")) {
			work_value.setText("");
			work_value.setHint("��");
		}else {
			work_value.setText(work_value1);
		}
		
		if(!TextUtils.isEmpty(uid)){
			Bitmap bitmap = asyn.loaderBitmap(icon_value, PhpUrl.AVATARURl + getAvatarUrl(uid)
					+ "/avatar.jpg", new ImageCallBack() {
				
				@Override
				public void imageLoader(ImageView imageView, Bitmap bitmap) {
					if(bitmap != null){
						imageView.setImageBitmap(bitmap);
					}
				}
			}, 0);
		}
	}
}
// ��ȡͷ���Url
	private String getAvatarUrl(String uid) {
		String url = uid;
		StringBuffer sb = new StringBuffer();
		int j = 0;
		for (int i = 0; i < url.length(); i++) {
			if ((j + 2) < url.length()) {
				sb.append(url.substring(i, j + 2));
			} else {
				sb.append(url.substring(j));
				break;
			}
			sb.append("/");
			j += 2;
		}
		return sb.toString();
	}
private void isNetworkConnected() {
	// �ж������Ƿ�����
	boolean networkConnected = NetworkUtil
			.isNetworkConnected(MineFragment_Update_UserInfo.this);
	if (networkConnected) {
		// �ύ
		newDialog();
	} else {
		// ����������
		pb_update.setVisibility(View.INVISIBLE);
		iv_submit.setClickable(true);
		Toast.makeText(MineFragment_Update_UserInfo.this, "��������ʧ��", 0)
				.show();
	}
}

private void initView() {
	asyn = new AsyncBitmapLoader(this);
	icon_value = (CircularImage) this.findViewById(R.id.icon_value);
	rl_icon = (RelativeLayout) this.findViewById(R.id.rl_icon);
	// ������
	pb_update = (ProgressBar) findViewById(R.id.pb_update);
	// ����
	RelativeLayout iv_ActionBar = (RelativeLayout) findViewById(R.id.iv_ActionBar);
	iv_ActionBar.setVisibility(View.VISIBLE);
	iv_ActionBar.setOnClickListener(this);
	TextView tv_ClassName = (TextView) findViewById(R.id.tv_ClassName);
	tv_ClassName.setText("�޸ĸ�����Ϣ");
	// �ǳ�
	name_value = (EditText) findViewById(R.id.name_value);
	// �ֻ�
	phone_value = (TextView) findViewById(R.id.phone_value);
	// �Ա�
	rg_sex = (RadioGroup) findViewById(R.id.rg_sex);
	// ��
	rb_man = (RadioButton) findViewById(R.id.rb_man);
	// Ů
	rb_woman = (RadioButton) findViewById(R.id.rb_woman);
	// ����
	birthday_value = (TextView) findViewById(R.id.birthday_value);
	// ְҵ
	work_value = (EditText) findViewById(R.id.work_value);
	// ���ڵ�
	address_value = (TextView) findViewById(R.id.address_value);
	// ��ת��ѡ������ҳ��
	findViewById(R.id.rl_birthday).setOnClickListener(this);
	// ѡ���ַ
	findViewById(R.id.rl_address).setOnClickListener(this);
	// �ύ
	iv_submit = (ImageView) findViewById(R.id.iv_submit);
	iv_submit.setOnClickListener(this);
	// �����¼�
	setOnClickListener();
}

private void setOnClickListener() {
	rl_icon.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Intent intentFromGallery = new Intent();
           intentFromGallery.setType("image/*"); // �����ļ�����
           intentFromGallery
                           .setAction(Intent.ACTION_GET_CONTENT);
           MineFragment_Update_UserInfo.this.startActivityForResult(intentFromGallery,
                           IMAGE_REQUEST_CODE);
		}
	});
	
	// �ǳ�
	name_value.addTextChangedListener(new TextWatcher() {

		private int name_length;

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {

		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {

		}

		@Override
		public void afterTextChanged(Editable s) {
			if (s != null && !"".equals(s)) {
				try {
					name_length = s.toString().getBytes("gbk").length;
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				if (name_length < 1 || name_length > 16) {
					name_value.setError("���Ϊ16���ַ�");
					name_type = false;
				} else {
					name_type = true;
				}
			} else {
				name_value.setError("�ǳƲ�Ϊ��");
			}
		}
	});
	// ����
	birthday_value.addTextChangedListener(new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {

		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {

		}

		@Override
		public void afterTextChanged(Editable s) {
			if (s != null && !"".equals(s)) {
				bri_type = true;
			} else {
				bri_type = false;
				birthday_value.setError("���ղ�Ϊ��");
			}
		}
	});
	// ְҵ
	work_value.addTextChangedListener(new TextWatcher() {

		private int work_length;

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub

		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub

		}

		@Override
		public void afterTextChanged(Editable s) {
			// if (s != null && !"".equals(s) && s.length() > 0) {
			// try {
			// work_length = s.toString().getBytes("gbk").length;
			// } catch (UnsupportedEncodingException e) {
			// e.printStackTrace();
			// }
			// if (work_length > 30) {
			// //work_value.setError("���Ϊ30���ַ�");
			// work_type = false;
			// } else {
			// work_type = true;
			// }
			// } else {
			// //work_value.setError("ְҵ����Ϊ��");
			// }
		}
	});
	// �Ա�
	rg_sex.setOnCheckedChangeListener(new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			switch (checkedId) {
			case R.id.rb_man:
				sex = 1;
				break;
			case R.id.rb_woman:
				sex = 0;
				break;
			default:
				break;
			}
		}
	});
}

private void newDialog() {
	DialogUtil.ShowDialog(MineFragment_Update_UserInfo.this, "��ʾ", "�Ƿ��޸�?",
			"ȡ��", "ȷ��", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
				}

			}, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					DialogUtil.showProgressDialog(MineFragment_Update_UserInfo.this, "������...", 0);
					String work = work_value.getText().toString();
					iv_submit.setClickable(false);
					pb_update.setVisibility(View.VISIBLE);
					List<NameValuePair> params = new ArrayList<NameValuePair>();
					JSONObject object = new JSONObject();
					try {
						object.put("uid", uid);
						object.put("nickname", name_value.getText()
								.toString());
						object.put("sex", sex);
						object.put("birthday", birthday_value.getText()
								.toString());
						if (work != null && !"".equals(work)) {
							object.put("job", work);
						} else {
							object.put("job", "δ��д");
						}
						object.put("address", address_value.getText()
								.toString());
					} catch (Exception e) {
						e.printStackTrace();
					}
					params.add(new BasicNameValuePair("method",
							HttpUrl.update_method));
					params.add(new BasicNameValuePair("content", object
							.toString()));
					// Log.i("����ȥ�Ĳ���--->", object.toString());
					new HttpPostThread(params, handler, 0).start();
				}
			});
}

@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	// �����������
	if (resultCode == 200) {
		// String calendar_date = data.getStringExtra("calendar_date");
		// if (calendar_date!=null&&!"".equals(calendar_date)) {
		// birthday_value.setText(calendar_date);
		// }
		String year = null;
		String month = null;
		String day = null;
		ArrayList<String> calendar_list = data
				.getStringArrayListExtra("calendar_list");
		if (calendar_list != null && calendar_list.size() > 0) {
			year = calendar_list.get(0);
			month = calendar_list.get(1);
			day = calendar_list.get(2);
			if (month.length() == 1) {
				month = "0" + month;
			}
			if (day.length() == 1) {
				day = "0" + day;
			}
			birthday_value.setText(year + "-" + month + "-" + day);
			year = null;
			month = null;
			day = null;
		}
	}
	// ���ص�ַ���
	else if (resultCode == 300) {
		ArrayList<String> citys_list = data
				.getStringArrayListExtra("citys_list");
		if (citys_list != null && citys_list.size() > 0) {
			StringBuffer str=new StringBuffer();
			for (int i = 0; i < citys_list.size(); i++) {
				String string = citys_list.get(i);
				str.append(string+" ");
			}
			address_value.setText(str+"");
		}
	}
	switch (requestCode) {
   	case IMAGE_REQUEST_CODE:
   		if (data!=null) {
   			startPhotoZoom(data.getData());
			}
           break;
   	case RESULT_REQUEST_CODE:
       if (data != null) {
               getImageToView(data);
       }
       break;
}
	super.onActivityResult(requestCode, resultCode, data);
}

/**
* �ü�ͼƬ����ʵ��
* 
* @param uri
*/
public void startPhotoZoom(Uri uri) {

       Intent intent = new Intent("com.android.camera.action.CROP");
       intent.setDataAndType(uri, "image/*");
       // ���òü�
       intent.putExtra("crop", "true");
       // aspectX aspectY �ǿ�ߵı���
       intent.putExtra("aspectX", 1);
       intent.putExtra("aspectY", 1);
       // outputX outputY �ǲü�ͼƬ���
       intent.putExtra("outputX", 320);
       intent.putExtra("outputY", 320);
       intent.putExtra("return-data", true);
       startActivityForResult(intent, 2);
}

/**
* ����ü�֮���ͼƬ����
* 
* @param picdata
*/
private void getImageToView(Intent data) {
       Bundle extras = data.getExtras();
       if (extras != null) {
               Bitmap photo = extras.getParcelable("data");
               MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
               try {
					entity.addPart("uid",new StringBody(uid));
//					FileBody filBody = new FileBody(new File(MineFragment_Update_UserInfo.this.getApplicationContext().getCacheDir().getAbsolutePath()+"/avg.jpg"));
					 ByteArrayOutputStream stream = new ByteArrayOutputStream();
                   Bitmap bmp = ImageCompress.getSizeOfBitmap(photo,MineFragment_Update_UserInfo.this);
                   bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                   byte[] byteArray = stream.toByteArray();
					entity.addPart("imageUpLoad", new ByteArrayBody(byteArray, ""));
//					entity.addPart("imageUpLoad", new StringBody(text));
					entity.addPart("safekey",new StringBody(getSafekey()));
					Log.i("Test", "safekey-->"+getSafekey());
					Log.i("Test", "uid-->"+uid);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
               NetworkInfo info = HttpUtil.isNetworkInfo(MineFragment_Update_UserInfo.this);
               if (info!=null) {
               	DialogUtil.showProgressDialog(MineFragment_Update_UserInfo.this, "�ϴ���...", 0);
               	 new ImageCompressHttp(entity, image_handler, 0).start();
				}else {
					DialogUtil.dismissProgressDialog();
					Toast.makeText(MineFragment_Update_UserInfo.this, "��������ʧ��", 0).show();
				}
//               List<NameValuePair> parms = new ArrayList<NameValuePair>();
//               parms.add(new BasicNameValuePair("uid", uid));
//               ByteArrayOutputStream stream = new ByteArrayOutputStream();
//               Bitmap bmp = ImageCompress.getSizeOfBitmap(photo,MineFragment_Update_UserInfo.this);
//               bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
//               byte[] byteArray = stream.toByteArray();
//               parms.add(new BasicNameValuePair("imageUpLoad",new String(byteArray));
//               parms.add(new BasicNameValuePair("safekey", getSafekey()));
//               new ImageCompressHttp(parms, handler, 1).start();
               icon_value.setImageBitmap(ImageCompress.getSizeOfBitmap(photo,MineFragment_Update_UserInfo.this));
               
       }
}

@Override
public void onClick(View v) {
	switch (v.getId()) {
	case R.id.iv_ActionBar:
		// ����
		finish();
		break;
	case R.id.rl_birthday:
		// ����
		ShowDateDialog();
		break;
	case R.id.iv_submit:
		KeyBoardUtil.is_openKeyBoard(getApplicationContext(),
				MineFragment_Update_UserInfo.this);

		// �ύ
		String string = name_value.getText().toString();
		String string2 = birthday_value.getText().toString();
		// String string3 = work_value.getText().toString();
		String string4 = address_value.getText().toString();
		if (string.length() > 0 && string2.length() > 0
				&& string4.length() > 0 && name_type && bri_type
				&& string4.length() > 0) {
			isNetworkConnected();
		} else {
			if (string.length() == 0) {
				Toast.makeText(getApplicationContext(), "�ǳƲ�Ϊ��", 0).show();
			} else if (string2.length() == 0) {
				Toast.makeText(getApplicationContext(), "���ղ�Ϊ��", 0).show();
			} else if (string4.length() == 0) {
				Toast.makeText(getApplicationContext(), "��ַ��Ϊ��", 0).show();
			}
		}
		break;
	case R.id.rl_address:
		// ѡ���ַ
		if (TimeUtils.isFastDoubleClick(3000)) {
			Intent intent2 = new Intent(MineFragment_Update_UserInfo.this,
					MineFragment_Update_UserInfo_Address.class);
			startActivityForResult(intent2, 100);
		}
		break;
	default:
		break;
	}
}

private void ShowDateDialog() {
   Calendar calendar = Calendar.getInstance();
	DatePickerDialog dialog=new DatePickerDialog(MineFragment_Update_UserInfo.this, new OnDateSetListener() {
		
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			birthday_value.setText(year + " - " + (monthOfYear+1) + " - " + dayOfMonth);				
		}
	}, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

	dialog.show();
}

private String getSafekey(){ 
	StringBuffer sb = new StringBuffer();
	sb.append(uid);
	sb.append("|");
	SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
	Date date = new Date();
	sb.append(formatter.format(date));
	sb.append("|");
	sb.append("souhei975427");
	Log.i("Test", "sof----->"+sb.toString());
	Log.i("Test", "md5----->"+Md5.Md5(sb.toString()));
	return Md5.Md5(sb.toString());
}
}
