package com.example.black.lib;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Handler;

public class PublishSearch {
	//根据关键字那企业信息
		public static void CompanySearch(String search_key,long id,Handler handler,int type){
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			JSONObject jsonObject = new JSONObject();
			try {
				jsonObject.put("name", search_key);
//				jsonObject.put("user_id", id);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			params.add(new BasicNameValuePair("method",PhpUrl.search_method));
			params.add(new BasicNameValuePair("content",jsonObject.toString()));
			new HttpPostThread(params, handler, type).start();
		}
		//根据id那企业信息
		public  static void getCommpany(long id,Handler handler,int type){
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			JSONObject jsonObject = new JSONObject();
			try {
				jsonObject.put("id", id);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			params.add(new BasicNameValuePair("method",PhpUrl.getCommpany_method));
			params.add(new BasicNameValuePair("content",jsonObject.toString()));
			new HttpPostThread(params, handler, type).start();
		}
}
