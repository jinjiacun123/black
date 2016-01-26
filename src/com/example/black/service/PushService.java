package com.example.black.service;

import java.util.HashMap;
import java.util.Map;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.provider.Settings.Secure;
import android.text.TextUtils;
import android.util.Log;
import android.view.WindowManager;
import android.app.Service;

import com.example.black.R;
import com.example.black.act.CommentReplyActivity;
/*lib*/
import com.example.black.lib.PushInfo;
import com.example.black.lib.JsonUtil;
import com.example.black.lib.Util;
/*lib.mqtt*/
import com.example.black.lib.mqtt.MqttV3Service;
/*lib.model*/
import com.example.black.lib.model.Reply_Activity;
import com.example.black.lib.model.DarkTerraceActivity;
import com.example.black.lib.model.NoAttestationActivity;
import com.example.black.lib.model.QualifiedTerraceActivity;
import com.example.black.lib.model.NoStorageActivity;
import com.example.black.lib.model.NewsTextActivity;

public class PushService extends Service {
	private static final String MQTT_HOST = "www.koubei365.com.cn";
	private static String MQTT_BROKER_PORT_NUM = "1883";
	private String strResult = "";
	private String mDeviceID;
	private long user_id;
	private String token;
	private NotificationManager mNotifMan;
	public static String NOTIF_TITLE = "口碑"; 
	private String my_type;
	private String comment_id;
	private String nature;
	private String auth_level;
	private String company_id;
	private String news_id;
	private String exposal_id;//曝光id
	private PushInfo pushInfo;
	private String mcontent;
	private String mcomment;
	
	private int messageNotificationID = 1000;
	NotificationManager notificationManager; 

	//开启服务
	public static void actionStart(Context ctx) {
		Intent i = new Intent(ctx, PushService.class);
		ctx.startService(i);
	}
	
	//关闭服务
	public static void actionStop(Context ctx) {
		Intent i = new Intent(ctx, PushService.class);
		ctx.stopService(i);
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		mNotifMan = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		//初始化参数数据
		initData();
		//注册连接
		connect();
		notificationManager =  (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
	}
	//判断app是否在前台运行
    private boolean isRunningForeground (Context context){  
        ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);  
        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;  
        String currentPackageName = cn.getPackageName();  
        if(!TextUtils.isEmpty(currentPackageName) && currentPackageName.equals(getPackageName())){  
            return true ;  
        }  
        return false ;  
    }  
	
	private void initData() {
		// TODO Auto-generated method stub
		mDeviceID = Secure.getString(this.getContentResolver(), Secure.ANDROID_ID);
		user_id = Util.getShare_User_id(getApplicationContext());
		token = "souhei/" + user_id + "_" + mDeviceID; 
		
	}

	private synchronized void connect() {
		new Thread(new MqttProcThread()).start();
	}
	
	public class MqttProcThread implements Runnable {
		@Override
		public void run() {
			Message msg = new Message();
			String uid = String .valueOf(user_id);
			boolean ret = MqttV3Service.connectionMqttServer(mHandler, 
					MQTT_HOST, 
					MQTT_BROKER_PORT_NUM, 
					uid, 
					token);
			if(ret){
				msg.what = 1;
			}else{
				msg.what = 0;
			}
			msg.obj = strResult;
			mHandler.sendMessage(msg);
		}
	}
	
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == 1) {
				Log.i("push", "连接成功！");
			}else if(msg.what == 0){
				Log.i("push", "连接失败！");
			}else if  (msg.what == 2) {
				String strContent = "";
				strContent = msg.getData().getString("content");
				//拿取已解析的推送数据
				pushInfo = new JsonUtil().getpushInfo(strContent);
				if(pushInfo != null){
					mcontent = pushInfo.getContent();
					mcomment = pushInfo.getComment();
				}
				//字符串差分
				String[] spStr = mcomment.split(",");
				Map<String, String > map = new HashMap<String, String>();
				for(int i = 0 ;i< spStr.length; i++){
					String str = spStr[i];
					Log.i("push", "str----------->" + str);
					map.put((str.substring(0,str.indexOf(":"))).trim(), str.substring(str.indexOf(":")+1));
				}
				
				my_type = map.get("type");
				switch(my_type){
				case "010001":
					comment_id = map.get("comment_id");
					break;
					
				case "010002":
					comment_id = map.get("comment_id");
					break;
					
				case "010003":
					company_id = map.get("company_id");
					nature = map.get("nature");
					auth_level = map.get("auth_level");
					break;
					
				case "010004":
					news_id = map.get("news_id");
					break;
					
				case "010005":
					exposal_id = map.get("exposal_id");
					break;
					
				case "010006":
					comment_id = map.get("comment_id");
					break;
				}
				if(isRunningForeground(PushService.this)){
					shoudialog(mcontent);
				}else{
					showNotification(mcontent);
				}
			}
		}

		private void shoudialog(String info) {
			AlertDialog.Builder builder = new AlertDialog.Builder(PushService.this);
			builder.setIcon(R.drawable.icon);
			builder.setMessage(info);
			builder.setTitle("通知");
			builder.setPositiveButton("忽略", new OnClickListener() {
			    @Override
			    public void onClick(DialogInterface dialog, int which) {
			        //增加按钮,回调事件
			    	
			    }
			});
			builder.setNegativeButton("查看", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Intent intent = new Intent();
					switch(my_type){
					case "010001":
						intent.setClass(PushService.this, Reply_Activity.class);
						intent.putExtra("comment_id", comment_id);
						intent.putExtra("title_type", 1);
						intent.putExtra("mqtt_push_type", 1);
						intent.putExtra("companys_type", 1);
						break;
						
					case "010002":
						intent.setClass(PushService.this, CommentReplyActivity.class);
						intent.putExtra("comment_id", comment_id);
						intent.putExtra("mqtt_push_type", 1);
						intent.putExtra("push_type", 1);
						intent.putExtra("position", 1);
						break;
						
					case "010003":
						switch(auth_level){
						//黑平台
						case "006001":
							intent.setClass(PushService.this,DarkTerraceActivity.class);
							break;
						//未验证
						case "006002":
							intent.setClass(PushService.this,NoAttestationActivity.class);
							break;
						//合规	
						case "006003":
							intent.setClass(PushService.this,QualifiedTerraceActivity.class);
							break;
						default:
							intent.setClass(PushService.this, NoStorageActivity.class);
							break;
						}
						intent.putExtra("company_id", company_id);
						intent.putExtra("mqtt_push_type", 1);
						intent.putExtra("nature", nature);
						break;
						
					case "010004":
						intent.setClass(PushService.this, NewsTextActivity.class);
						intent.putExtra("news_id", news_id);
						intent.putExtra("mqtt_push_type", 1);
						break;
						
					case "010005":
						intent.setClass(PushService.this, Reply_Activity.class);
						intent.putExtra("exposal_id", exposal_id);
						intent.putExtra("mqtt_push_type", 3);
						intent.putExtra("title_type", 2);
						intent.putExtra("companys_type", 1);
						break;
						
					case "010006":
						intent.setClass(PushService.this, CommentReplyActivity.class);
						intent.putExtra("comment_id", comment_id);
						intent.putExtra("mqtt_push_type", 3);
						intent.putExtra("push_type", 2);
						intent.putExtra("position", 1);
						break;
					
					}
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(intent);
				}
			});
			builder.setCancelable(false);//弹出框不可以按返回键取消
			AlertDialog dialog = builder.create();
			dialog.getWindow().setType(WindowManager.LayoutParams.ALPHA_CHANGED);//小米bug解决,将弹出框设置为全局
			dialog.setCanceledOnTouchOutside(false);//失去焦点不会消失
			dialog.show();
		}

		@SuppressWarnings("deprecation")
		private void showNotification(String info) {
			Notification n = new Notification();
			
			n.flags |= Notification.FLAG_SHOW_LIGHTS;
	      	n.flags |= Notification.FLAG_AUTO_CANCEL;

	        n.defaults = Notification.DEFAULT_ALL;
	      	
			n.icon = com.example.black.R.drawable.icon;
			n.when = System.currentTimeMillis();
			
			Intent intent = new Intent();
			switch(my_type){
			case "010001":
				intent.setClass(PushService.this, Reply_Activity.class);
				intent.putExtra("comment_id", comment_id);
				intent.putExtra("title_type", 1);
				intent.putExtra("mqtt_push_type", 1);
				intent.putExtra("companys_type", 1);
				break;
				
			case "010002":
				intent.setClass(PushService.this, CommentReplyActivity.class);
				intent.putExtra("comment_id", comment_id);
				intent.putExtra("mqtt_push_type", 1);
				intent.putExtra("push_type", 1);
				intent.putExtra("position", 1);
				break;
				
			case "010003":
				switch(auth_level){
				//黑平台
				case "006001":
					intent.setClass(PushService.this,DarkTerraceActivity.class);
					break;
				//未验证
				case "006002":
					intent.setClass(PushService.this,NoAttestationActivity.class);
					break;
				//合规	
				case "006003":
					intent.setClass(PushService.this,QualifiedTerraceActivity.class);
					break;
				default:
					intent.setClass(PushService.this, NoStorageActivity.class);
					break;
				}
				intent.putExtra("company_id", company_id);
				intent.putExtra("mqtt_push_type", 1);
				intent.putExtra("nature", nature);
				break;
				
			case "010004":
				intent.setClass(PushService.this, NewsTextActivity.class);
				intent.putExtra("news_id", news_id);
				intent.putExtra("mqtt_push_type", 1);
				break;
				
			case "010005":
				intent.setClass(PushService.this, Reply_Activity.class);
				intent.putExtra("exposal_id", exposal_id);
				intent.putExtra("mqtt_push_type", 3);
				intent.putExtra("title_type", 2);
				intent.putExtra("companys_type", 1);
				break;
				
			case "010006":
				intent.setClass(PushService.this, CommentReplyActivity.class);
				intent.putExtra("comment_id", comment_id);
				intent.putExtra("mqtt_push_type", 3);
				intent.putExtra("push_type", 2);
				intent.putExtra("position", 1);
				break;
			
			}
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
			// Simply open the parent activity
			PendingIntent pi = PendingIntent.getActivity(PushService.this, 0,
					intent, PendingIntent.FLAG_UPDATE_CURRENT);
			
			// Change the name of the notification here
			n.setLatestEventInfo(PushService.this, NOTIF_TITLE, info, pi);

			mNotifMan.notify(messageNotificationID, n);
			messageNotificationID++;
		}
	};
	
	@Override
	public void onDestroy() {
		MqttV3Service.closeMqtt();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
}
