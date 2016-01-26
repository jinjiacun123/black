package com.example.black.lib;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.example.black.R;
import com.example.black.main.MainActivity;
import com.example.black.view.custom.DialogUtil;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class UpdateManager {
	// 提示语
		private String updateMsg = "有最新的软件包哦，亲快下载吧~";
		// 版本校验路径
		public static final String update_url = "http://192.168.1.131/yms_api/Public/app/android/app_download.xml";
		// 下载apk路径
		private String download_url = null;
		/* 下载包安装路径 */
		private String saveFileName = null;
		// 下载中
		private static final int DOWN_UPDATE = 1;
		// 下载成功
		private static final int DOWN_OVER = 2;

		/* 进度条与通知ui刷新的handler和msg常量 */
		private ProgressBar mProgress;
		private Context mContext;
		private boolean interceptFlag = false;
		private int progress;
		private TextView progress_content;

		Handler mHandler = new Handler() {

			@Override
			public void handleMessage(android.os.Message msg) {
				switch (msg.what) {
				case DOWN_UPDATE:
					// 下载
					mProgress.setProgress(progress);
					progress_content.setText((progress + 1) + "%");
					break;
				case DOWN_OVER:
					// 安装
					installApk();
					break;
				default:
					break;
				}
			}
		};

		public UpdateManager(Context context, String url, String label) {
			this.mContext = context;
			this.download_url = url;
			this.saveFileName = "/" + label + ".apk";
			

			// 判断外置SD卡是否挂载：
			// if(Environment.getStorageState(Environment.).equals(Environment.MEDIA_MOUNTED))
			 //{
			// 为true的话，外置sd卡存在
			// }

		}

		// 外部接口让主Activity调用
		public void checkUpdateInfo() {
			showNoticeDialog();
		}

		private void showNoticeDialog() {
			DialogUtil.ShowDialog(mContext, "版本更新", updateMsg, "以后再说", "下载", null,
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							showDownloadDialog();
						}
					});

		}

		private void showDownloadDialog() {
			View view = LayoutInflater.from(mContext).inflate(
					R.layout.activity_progress, null);
			mProgress = (ProgressBar) view.findViewById(R.id.progress);
			progress_content = (TextView) view.findViewById(R.id.progress_content);
			AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
			builder.setTitle("版本更新");
			builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					interceptFlag = true;
					
				}
			});
			builder.setView(view);
			builder.show();
		
			new downLoadThread().start();
		}

		/**
		 * 安装apk
		 * 
		 * @param url
		 */
		private void installApk() {
			File directory = Environment.getExternalStorageDirectory();

			File apkfile = new File(directory.toString() + saveFileName);
			if (!apkfile.exists()) {
				return;
			}
			Intent i = new Intent(Intent.ACTION_VIEW);
			i.setDataAndType(Uri.parse("file://" + apkfile.toString()),
					"application/vnd.android.package-archive");
			mContext.startActivity(i);
		}

		public void openApk(Context context) {
			PackageManager manager = context.getPackageManager();
			// 这里的是你下载好的文件路径
			PackageInfo info = manager.getPackageArchiveInfo(
					Environment.getExternalStorageDirectory().getAbsolutePath()
							+ saveFileName, PackageManager.GET_ACTIVITIES);
			if (info != null) {
				Intent intent = manager
						.getLaunchIntentForPackage(info.applicationInfo.packageName);
				context.startActivity(intent);
			}
		}

		class downLoadThread extends Thread {
			@Override
			public void run() {
				try {				
					URL url = new URL(download_url);

					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					conn.connect();
					int length = conn.getContentLength();
					InputStream is = conn.getInputStream();
					if (Environment.getExternalStorageState().equals(
							Environment.MEDIA_MOUNTED)) {
						// 为true的话，内置sd卡存在
						File directory = Environment.getExternalStorageDirectory();
						File file = new File(directory.toString());
						if (!file.exists()) {
							file.mkdir();
						}
						String apkFile = directory.toString() + saveFileName;
						File ApkFile = new File(apkFile);
						FileOutputStream fos = new FileOutputStream(ApkFile);

						int count = 0;
						byte buf[] = new byte[1024];
						do {
							int numread = is.read(buf);
							count += numread;
							progress = (int) (((float) count / length) * 100);
							// 更新进度
							mHandler.sendEmptyMessage(DOWN_UPDATE);
							if (numread <= 0) {
								// 下载完成通知安装
								mHandler.sendEmptyMessage(DOWN_OVER);
								break;
							}
							fos.write(buf, 0, numread);
						} while (!interceptFlag);// 点击取消就停止下载.
						fos.close();
						is.close();
					}				
				} catch (Exception e) {
					e.printStackTrace();
				}
				super.run();
			}
		}

		// *
		// * 进入程序的主界面
		// */
		private void LoginMain() {
			Intent intent = new Intent(mContext, MainActivity.class);
			mContext.startActivity(intent);
			// 结束掉当前的activity
			// mContext.
		}
}
