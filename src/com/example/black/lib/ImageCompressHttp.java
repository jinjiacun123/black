package com.example.black.lib;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.os.Handler;
import android.os.Message;
import android.util.Log;


/**
 * 头像上传网络请求类
 * @author Admin
 *
 */
public class ImageCompressHttp extends Thread{
	private MultipartEntity entity;//请求参数
//	private List<NameValuePair> params;
	private Handler handler;//异步通知的handler
	private int type ;//类型 handler.what的值
	private HttpPost post;
	private HttpClient client;
	private String result ="null";//返回的结果值
	private Message msg;
	
	public ImageCompressHttp(MultipartEntity entity,Handler handler,int type) {
		this.handler = handler;
		this.entity = entity;
//		this.params = params;
		this.type = type;
		msg = new Message();
	}
	
	@Override
	public void run() {
		post = new HttpPost(PhpUrl.update_aravter);
		client = new DefaultHttpClient();
//		 client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 20000);
//		 client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 20000);
		try {
//			post.setHeader("User-Agent", "Mozilla/5.0 (Linux; U; Android 0.5; en-us) AppleWebKit/522+ (KHTML, like Gecko) Safari/419.3");
//			Log.i("Test", "entity-->"+params);
//			post.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
			post.setEntity(entity);
			HttpResponse response = client.execute(post);
			if(response.getStatusLine().getStatusCode() == 200){
				result = EntityUtils.toString(response.getEntity());
			}else{
				Log.i("Test", "result2-->"+EntityUtils.toString(response.getEntity()));
			}
			Log.i("Test", "result-->"+result);
			Log.i("Test", "code-->"+response.getStatusLine().getStatusCode());
				msg.obj = result;
				msg.what = type;
				handler.sendMessage(msg);
		} catch (Exception e) {
			//handler.sendEmptyMessage(what)
			e.printStackTrace();
		}finally{
			client.getConnectionManager().shutdown();
		}
	}
}
