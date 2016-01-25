package com.example.black.lib;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.black.R;
import com.example.black.lib.model.Exosure_Company;
import com.example.black.lib.model.Exosure_Reply;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.text.Html;
import android.util.Log;

public class PublishExposal {
	//��ȡ�ع��б�
		public static void getCompanyExposal(long id ,long id2,Handler handler ,int type,int page_index,int page_size){
			List<NameValuePair> prams = new ArrayList<NameValuePair>();
			JSONObject jsonObject = new JSONObject();
			JSONObject jsonObject2 = new JSONObject();
			JSONObject object=new JSONObject();
			try {
					jsonObject.put("company_id", id);
					jsonObject.put("type", 0);
					jsonObject.put("is_delete", 0);
//					jsonObject.put("is_validate",1);
					jsonObject2.put("where", jsonObject);
					jsonObject2.put("order", object);
					object.put("v_last_time", "DESC");
					object.put("add_time", "DESC");
					jsonObject2.put("user_id", id2);
					jsonObject2.put("page_index", page_index);
					jsonObject2.put("page_size", page_size);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			prams.add(new BasicNameValuePair("method",PhpUrl.company_exposal_list_method));
			prams.add(new BasicNameValuePair("content",jsonObject2
					.toString()));
			new HttpPostThread(prams, handler, type).start();
		}
		//ȥ��ĳһ���ع�
		public static void  ExosureComtop(Long id,Long id2,Handler handler ,int type){
			List<NameValuePair> prams = new ArrayList<NameValuePair>();
			JSONObject jsonObject = new JSONObject();
			try {
				jsonObject.put("user_id", id);
				jsonObject.put("exposal_id", id2);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			prams.add(new BasicNameValuePair("method",PhpUrl.company_exposal_comtp_method));
			prams.add(new BasicNameValuePair("content",jsonObject.toString()));
			Log.i("Test", "�عⶥ--->"+prams);
			new HttpPostThread(prams, handler, type).start();
		}
		
		//�ع�ظ���
			public static void commentExoTop(long user_id,long company_id ,long exposal_id,long comment_id,Handler handler,int type){
				List<NameValuePair> prams = new ArrayList<NameValuePair>();
				JSONObject jsonObject=new JSONObject();
				try {
					jsonObject.put("user_id", user_id);
					jsonObject.put("exposal_id", exposal_id);
					jsonObject.put("comment_id", comment_id);
					jsonObject.put("company_id", company_id);
				} catch (Exception e) {
					e.printStackTrace();
				}
				prams.add(new BasicNameValuePair("method",PhpUrl.Comexposaltop_add_method));
				prams.add(new BasicNameValuePair("content",jsonObject.toString()));
				Log.i("Test", "�عⶥ-------------��"+prams.toString());
				new HttpPostThread(prams, handler, type).start();
			}
		
		//�����û����۵�����
		public static CharSequence getCharSequence(Exosure_Company exosure_Company,final Context context){
			String html = exosure_Company.getContent();
			 html = html.replace("[", "<img src='");
			 html = html.replace("]", "'/>");
			 StringBuffer sb = new StringBuffer();
			 sb.append(html);
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
		
		//��ȡĳ�����۵Ļظ�
		public static void getExosureReply(long id ,long id2, Handler handler ,int type,int page_index,int page_size){
			List<NameValuePair> prams = new ArrayList<NameValuePair>();
			JSONObject jsonObject = new JSONObject();
			JSONObject jsonObject2 = new JSONObject();
			try {
				jsonObject.put("exposal_id", id);
				jsonObject.put("_string", "user_id="+id2+" or is_validate=1");
				jsonObject2.put("where", jsonObject);
				jsonObject2.put("page_index", page_index);
				jsonObject2.put("page_size", page_size);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			prams.add(new BasicNameValuePair("method",PhpUrl.company_exposals_list_method));
			prams.add(new BasicNameValuePair("content",jsonObject2
					.toString()));
			new HttpPostThread(prams, handler, type).start();
		}
		//�����û��Ļظ���������
				public static CharSequence getExosureReplyContent(Exosure_Reply exosure_Reply,final Context context){
					StringBuffer sb = new StringBuffer();
					SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
					String content = exosure_Reply.getContent();
					content = content.replace("[", "<img src='");
					content = content.replace("]", "'/>");
					sb.append("<font color='#3dafea'>");
					sb.append(exosure_Reply.getNickname()+"��");
					sb.append("</font>");
					sb.append("<font color='#333'>");
					sb.append(content);
					sb.append("</font>");
					sb.append("<font color='#ffffff'>");
					sb.append(".      .");
					sb.append("</font>");
					sb.append("<font color='#999999'>");
					sb.append(sdf.format(new Date(exosure_Reply.getAdd_time()*1000)));
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
		//������ҵ����
		public static void sendExosureComment(long id,long id2,long id3,String content,Handler handler,int type){
			List<NameValuePair> prams = new ArrayList<NameValuePair>();
			JSONObject jsonObject = new JSONObject();
			try {
				jsonObject.put("user_id", id);
				jsonObject.put("exposal_id",id3 );
				jsonObject.put("parent_id", 0);
				jsonObject.put("content", content);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			prams.add(new BasicNameValuePair("method",PhpUrl.company_exposals_add_method));
			prams.add(new BasicNameValuePair("content",jsonObject
					.toString()));
			new HttpPostThread(prams, handler, type).start();
		}
}
