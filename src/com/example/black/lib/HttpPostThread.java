package com.example.black.lib;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;

/**
 * HttpPost请求的封装方法
 * @author stone
 *  这里是一个封装的httppost 请求的方法类 
 *  传如参数集合，handler ，type 
 *  就会异步返回一个json字符串
 *  如果result = "null" 就是服务器出问题 连不上数据库
 *  其他就是数据出问题 可以访问数据库
 */
public class HttpPostThread extends Thread{

	private List<NameValuePair> params;//请求参数
	private Handler handler;//异步通知的handler
	private int type ;//类型 handler.what的值
	private HttpPost post;
	private HttpClient client;
	private String result ="null";//返回的结果值
	private Message msg;
	
	public HttpPostThread(List<NameValuePair> params,Handler handler,int type) {
		this.handler = handler;
		this.params = params;
		this.type = type;
		addNameValuePair();
		msg = new Message();
	}
	
	@SuppressLint("SimpleDateFormat")
	private void addNameValuePair(){
		if (params!=null&&params.size()>0&&params.get(0).getName()=="method") {
			DES des=null;
			BasicNameValuePair basicNameValuePair=null;
			try {			
				des=new DES("");
				StringBuffer buffer=new StringBuffer(params.get(0).getValue());
				StringBuffer append = buffer.append(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
				basicNameValuePair = new BasicNameValuePair("token", des.encrypt(append.toString()));
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			this.params.add(basicNameValuePair);
		}
	}
	
	@Override
	public void run() {
		post = new HttpPost(PhpUrl.SOUHEIURL);
		client = new DefaultHttpClient();
		HttpParams httpParams = client.getParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, 5000);
		HttpConnectionParams.setSoTimeout(httpParams, 20000);
		try {
			post.setHeader("User-Agent", "Mozilla/5.0 (Linux; U; Android 0.5; en-us) AppleWebKit/522+ (KHTML, like Gecko) Safari/419.3");
			post.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
			HttpResponse response = client.execute(post);
			if(response.getStatusLine().getStatusCode() == 200){
				result = EntityUtils.toString(response.getEntity());
			}else{
				if (response.getStatusLine().getStatusCode() == 501) {
					handler.sendEmptyMessage(1000);
				}
			}
			msg.obj = result;
			msg.what = type;
			handler.sendMessage(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			client.getConnectionManager().shutdown();
		}
	}
}
