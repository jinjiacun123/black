package com.example.black.view;

import com.example.black.R;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.MediaColumns;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class SelectPicPopupWindow extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getWindow().setLayout(android.view.ViewGroup.LayoutParams.MATCH_PARENT,
				android.view.ViewGroup.LayoutParams.MATCH_PARENT);
		setContentView(R.layout.activity_alert_dialog);
		initView();
	}

	private void initView() {
		findViewById(R.id.btn_pick_photo).setOnClickListener(this);
		findViewById(R.id.btn_cancel).setOnClickListener(this);
	}

	// 实现onTouchEvent触屏函数但点击屏幕时销毁本Activity
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		finish();
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != -1) {// 此处的 RESULT_OK 是系统自定义得一个常量
			Log.e("jay_test--->", "ActivityResult resultCode error");
			// Toast.makeText(SelectPicPopupWindow.this, "获取图片失败", 0).show();
			// Intent intent=new Intent();
			// intent.putExtra("image_url", "");
			// setResult(200, intent);
			// manager.killActivity(SelectPicPopupWindow.this);
			return;
		} else {
			ContentResolver resolver = getContentResolver();
			// 这里开始的第二部分，获取图片的路径：
			Uri data2 = data.getData();
			if (data2 != null) {
				String[] proj = { MediaColumns.DATA };
				Cursor cursor = resolver.query(data2, proj, null, null, null);
				// 将光标移至开头 ，这个很重要，不小心很容易引起越界
				if (cursor.moveToFirst()) {
					Intent intent = new Intent();
					int column_index = cursor
							.getColumnIndexOrThrow(MediaColumns.DATA);
					String image_url = cursor.getString(column_index);
					if (image_url != null && !"".equals(image_url)) {
						// 截取图片格式(允许的图片格式类型 jpg,gif,jpeg,png )
						String type = image_url.substring(image_url
								.lastIndexOf(".") + 1);
						if ("jpg".equals(type)) {
							intent.putExtra("image_url", image_url);
						} else if ("gif".equals(type)) {
							intent.putExtra("image_url", image_url);
						} else if ("jpeg".equals(type)) {
							intent.putExtra("image_url", image_url);
						} else if ("png".equals(type)) {
							intent.putExtra("image_url", image_url);
						} else {
							Toast.makeText(getApplicationContext(), "图片格式不正确",
									0).show();
							return;
						}
						setResult(200, intent);
						finish();
					}
				}
				cursor.close();
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 查看相册
		case R.id.btn_pick_photo:
			Intent intent = new Intent(
					Intent.ACTION_PICK,
					android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			startActivityForResult(intent, 0);
			break;
		case R.id.btn_cancel:
			// 取消
			finish();
			break;
		default:
			break;
		}
	}
}
