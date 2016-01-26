package com.example.black.lib;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.black.R;
import com.example.black.lib.model.NewsComment;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.text.Html;

public class PublishNew {

	//��ȡý�屨���б�
	public static void getCompanyNew(long id ,int sign,Handler handler ,int type,int page_index,int page_size){
		List<NameValuePair> prams = new ArrayList<NameValuePair>();
		JSONObject jsonObject = new JSONObject();
		JSONObject jsonObject2 = new JSONObject();
		try {
			jsonObject.put("company_id", id);
//			jsonObject.put("is_validate",1);
			switch (sign) {
			case 0:
				jsonObject.put("sign", sign);
				break;
			case 1:
				jsonObject.put("sign", sign);
				break;
			}
			//Log.i("Test", "sign-->"+sign);
			jsonObject2.put("where", jsonObject);
			jsonObject2.put("page_index", page_index);
			jsonObject2.put("page_size", page_size);
	} catch (JSONException e) {
		e.printStackTrace();
	}
		prams.add(new BasicNameValuePair("method",PhpUrl.company_news_list_method));
		prams.add(new BasicNameValuePair("content",jsonObject2
				.toString()));
		new HttpPostThread(prams, handler, type).start();
	}
	
	//��������
	public static void sendNewsComment(long id,long id2,long id3,String content,Handler handler ,int type){
		List<NameValuePair> prams = new ArrayList<NameValuePair>();
		JSONObject jsonObject = new JSONObject();
		
		try {
			jsonObject.put("user_id", id);
			jsonObject.put("company_id", id2);
			jsonObject.put("news_id", id3);
			jsonObject.put("content", content);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		prams.add(new BasicNameValuePair("method",PhpUrl.company_news_add_method));
		prams.add(new BasicNameValuePair("content",jsonObject
				.toString()));
		new HttpPostThread(prams, handler, type).start();
	}
	//��ȡý���ع������
	public static void getNewsReplyList(long id ,long id2 ,Handler handler,int type,int page_index,int page_size){
		List<NameValuePair> prams = new ArrayList<NameValuePair>();
		JSONObject jsonObject = new JSONObject();
		JSONObject jsonObject2 = new JSONObject();
		
		try {
			jsonObject.put("company_id",id);
			jsonObject.put("news_id",id2);
			jsonObject.put("is_validate",1);
			jsonObject2.put("where", jsonObject);
			jsonObject2.put("page_index", page_index);
			jsonObject2.put("page_size", page_size);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		prams.add(new BasicNameValuePair("method",PhpUrl.company_news_reply_list_method));
		prams.add(new BasicNameValuePair("content",jsonObject2
				.toString()));
		new HttpPostThread(prams, handler, type).start();
	}
	
	//�����û��Ļظ���������
	public static CharSequence getNewsReplyContent(NewsComment newsComment,final Context context){
		StringBuffer sb = new StringBuffer();
		String content = newsComment.getContent();
		content = content.replace("[", "<img src='");
		content = content.replace("]", "'/>");
		sb.append("<font color='#3dafea'>");
		sb.append(newsComment.getNickname()+"��");
		sb.append("</font>");
		sb.append("<font color='#333'>");
		sb.append(content);
		sb.append("</font>");
		
		CharSequence charSequence = Html.fromHtml(sb.toString(),new Html.ImageGetter() {
			
			@Override
			public Drawable getDrawable(String source) {
				//���ϵͳ��Դ����Ϣ������ͼƬ��Ϣ
				Drawable drawable;
					drawable = context.getResources().getDrawable(getResourceId(source));
					drawable.setBounds(0, 0, 40, 40);
		        return drawable;
			}
		} , null);
		return charSequence;
	}
		
		public static int getResourceId(String name){
		    try {
		      //������Դ��ID�ı��������Field�Ķ���ʹ�÷������ʵ�ֵ�
		      Field field = R.drawable.class.getField(name);
		      //ȡ�ò�������Դ��id���ֶΣ���̬��������ֵ��ʹ�÷������
		      return Integer.parseInt(field.get(null).toString());
		      
		    } catch (Exception e) {
		      e.printStackTrace();
		    }
		    return 0;
		  }
	//�������۵���
	public static void assistNewsComment(long id,long id2,long id3,long id4,Handler handler ,int type){
		List<NameValuePair> prams = new ArrayList<NameValuePair>();
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("user_id",id);
			jsonObject.put("company_id",id2);
			jsonObject.put("news_id",id3);
			jsonObject.put("comment_id",id4);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		prams.add(new BasicNameValuePair("method",PhpUrl.company_news_assist_method));
		prams.add(new BasicNameValuePair("content",jsonObject
				.toString()));
		new HttpPostThread(prams, handler, type).start();
	}
}
