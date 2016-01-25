package com.example.black.lib.mqtt;

import android.os.Handler;
import android.util.Log;

import com.ibm.micro.client.mqttv3.MqttClient;
import com.ibm.micro.client.mqttv3.MqttConnectOptions;
import com.ibm.micro.client.mqttv3.MqttException;
import com.ibm.micro.client.mqttv3.MqttTopic;

public class MqttV3Service {
	String addr = "";
	String port = "";
	
	private static MqttClient client = null;
	private static MqttTopic topic = null;
	
	public static boolean connectionMqttServer(Handler handler, String ServAddress, String ServPort, String userID,  String Topic){
		String connUrl = "tcp://" + ServAddress + ":" + ServPort;
		Log.i("push", "connectionMqttServer is ------------->" + Topic);
		try {
			client = new MqttClient(connUrl,userID, null);
			topic = client.getTopic(Topic);
			CallBack callback = new CallBack(userID, handler);
			client.setCallback(callback);
			MqttConnectOptions conOptions = new MqttConnectOptions();
			conOptions.setCleanSession(false);
			
			client.connect(conOptions);
			client.subscribe(Topic, 1);
			
			Log.i("push", "client is ------------->" + Topic);
		} catch (MqttException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static boolean closeMqtt(){
		try {
			client.disconnect();
		} catch (MqttException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
