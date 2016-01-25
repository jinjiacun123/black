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
 * HttpPost����ķ�װ����
 * @author stone
 *  ������һ����װ��httppost ����ķ����� 
 *  ����������ϣ�handler ��type 
 *  �ͻ��첽����һ��json�ַ���
 *  ���result = "null" ���Ƿ����������� ���������ݿ�
 *  �����������ݳ����� ���Է������ݿ�
 */
public class HttpPostThread extends Thread{

	private List<NameValuePair> params;//�������
	private Handler handler;//�첽֪ͨ��handler
	private int type ;//���� handler.what��ֵ
	private HttpPost post;
	private HttpClient client;
	private String result ="null";//���صĽ��ֵ
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
