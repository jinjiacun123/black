package com.example.black.lib.mqtt;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.ibm.micro.client.mqttv3.MqttCallback;
import com.ibm.micro.client.mqttv3.MqttDeliveryToken;
import com.ibm.micro.client.mqttv3.MqttMessage;
import com.ibm.micro.client.mqttv3.MqttTopic;

public class CallBack  implements MqttCallback{
	private String instanceData = "";
	private Handler handler;

	public CallBack(String instance, Handler handler) {
		instanceData = instance;
		this.handler = handler;
	}

	//接收服务断端推送的消息
	@Override
	public void messageArrived(MqttTopic topic, MqttMessage message) {
		try {
			Message msg = Message.obtain();
			Bundle bundle = new Bundle();
			bundle.putString("content", message.toString());
			msg.what = 2;
			msg.setData(bundle);
			handler.sendMessage(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void connectionLost(Throwable cause) {
	}

	@Override
	public void deliveryComplete(MqttDeliveryToken token) {
	}
}
