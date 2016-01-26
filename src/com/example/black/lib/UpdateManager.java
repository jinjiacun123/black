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
	// ��ʾ��
		private String updateMsg = "�����µ������Ŷ���׿����ذ�~";
		// �汾У��·��
		public static final String update_url = "http://192.168.1.131/yms_api/Public/app/android/app_download.xml";
		// ����apk·��
		private String download_url = null;
		/* ���ذ���װ·�� */
		private String saveFileName = null;
		// ������
		private static final int DOWN_UPDATE = 1;
		// ���سɹ�
		private static final int DOWN_OVER = 2;

		/* ��������֪ͨuiˢ�µ�handler��msg���� */
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
					// ����
					mProgress.setProgress(progress);
					progress_content.setText((progress + 1) + "%");
					break;
				case DOWN_OVER:
					// ��װ
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
			

			// �ж�����SD���Ƿ���أ�
			// if(Environment.getStorageState(Environment.).equals(Environment.MEDIA_MOUNTED))
			 //{
			// Ϊtrue�Ļ�������sd������
			// }

		}

		// �ⲿ�ӿ�����Activity����
		public void checkUpdateInfo() {
			showNoticeDialog();
		}

		private void showNoticeDialog() {
			DialogUtil.ShowDialog(mContext, "�汾����", updateMsg, "�Ժ���˵", "����", null,
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
			builder.setTitle("�汾����");
			builder.setPositiveButton("ȡ��", new DialogInterface.OnClickListener() {

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
		 * ��װapk
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
			// ������������غõ��ļ�·��
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
						// Ϊtrue�Ļ�������sd������
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
							// ���½���
							mHandler.sendEmptyMessage(DOWN_UPDATE);
							if (numread <= 0) {
								// �������֪ͨ��װ
								mHandler.sendEmptyMessage(DOWN_OVER);
								break;
							}
							fos.write(buf, 0, numread);
						} while (!interceptFlag);// ���ȡ����ֹͣ����.
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
		// * ��������������
		// */
		private void LoginMain() {
			Intent intent = new Intent(mContext, MainActivity.class);
			mContext.startActivity(intent);
			// ��������ǰ��activity
			// mContext.
		}
}
