package com.example.black.act;

import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.black.R;
import com.example.black.lib.DisplayUtil;
import com.example.black.lib.HttpPostThread;
import com.example.black.lib.HttpUrl;
import com.example.black.lib.PhpUrl;
import com.example.black.lib.Util;
import com.example.black.lib.model.Comment_Company;
import com.example.black.lib.model.Comment_Company2;
import com.example.black.lib.model.Exosure_Company;

import Decoder.BASE64Decoder;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.text.Html;
import android.util.Log;

public class PublishComment {
	// �������۷���
		public static void publishComment(Comment_Company comment_Company,Map<String, String> map,
				Handler handler, int type) {
			List<NameValuePair> prams = new ArrayList<NameValuePair>();
			JSONObject jsonObject = new JSONObject();
			try {
				jsonObject.put("user_id", comment_Company.getUser_id());
				jsonObject.put("company_id", comment_Company.getCompany_id());
				jsonObject.put("parent_id", comment_Company.getParent_id());
				if (comment_Company.getType() == null) {
					jsonObject.put("type", "005001");
				} else {
					jsonObject.put("type", comment_Company.getType());
				}
				jsonObject.put("content", comment_Company.getContent());
				
				try {
					if (map != null) {
						for (int i = 0; i < 5; i++) {
							if (map.get("pic_" + (i + 1)) != null
									&& !"".equals(map.get("pic_" + (i + 1)))) {
								jsonObject.put("pic_" + (i + 1), map.get("pic_" + (i + 1)));
							} else {
								jsonObject.put("pic_" + (i + 1), "");
							}
						}
					} else {
						for (int i = 0; i < 5; i++) {
							jsonObject.put("pic_" + (i + 1), "");
						}
					}} catch (Exception e) {
						e.printStackTrace();
					}
				//jsonObject.put("pic_1", comment_Company.getPic_1());
				//jsonObject.put("pic_2", comment_Company.getPic_2());
				//jsonObject.put("pic_3", comment_Company.getPic_3());
				//jsonObject.put("pic_4", comment_Company.getPic_4());
				//jsonObject.put("pic_5", comment_Company.getPic_5());
				jsonObject.put("is_anonymous", comment_Company.getIs_anonymous());
			} catch (JSONException e) {
				e.printStackTrace();
			}
			prams.add(new BasicNameValuePair("method", PhpUrl.comment_add_method));
			prams.add(new BasicNameValuePair("content", jsonObject.toString()));
			//Log.i("Test", "�õ�������dsfgsdgdsf------------>"+prams.toString());
			new HttpPostThread(prams, handler, type).start();
		}
		
		public static void publishComment2(int comment_type,int comment_top,Context context,Comment_Company comment_Company,Map<String, String> map,
				Handler handler, int type) {
			List<NameValuePair> prams = new ArrayList<NameValuePair>();
			JSONObject jsonObject = new JSONObject();
			try {
				jsonObject.put("user_id", Util.getShare_User_id(context));
				jsonObject.put("is_depth", comment_Company.getIs_depth());
				if(comment_type==1){
					 jsonObject.put("company_id", comment_Company.getCompany_id()); 
					 jsonObject.put("parent_id", comment_Company.getParent_id());
				}
				if(comment_type==2) {
					jsonObject.put("exposal_id", comment_Company.getCompany_id());
					if (comment_top!=1) {
						jsonObject.put("parent_id", 0);
					}else {
						jsonObject.put("parent_id", comment_Company.getUser_id());
					}
				}
				if (comment_Company.getType() == null) {
					jsonObject.put("type", "005001");
				} else {
					jsonObject.put("type", comment_Company.getType());
				}
				jsonObject.put("content", comment_Company.getContent());
				
				try {
					if (map != null) {
						for (int i = 0; i < 5; i++) {
							if (map.get("pic_" + (i + 1)) != null
									&& !"".equals(map.get("pic_" + (i + 1)))) {
								jsonObject.put("pic_" + (i + 1), map.get("pic_" + (i + 1)));
							} else {
								jsonObject.put("pic_" + (i + 1), "");
							}
						}
					} else {
						for (int i = 0; i < 5; i++) {
							jsonObject.put("pic_" + (i + 1), "");
						}
					}} catch (Exception e) {
						e.printStackTrace();
					}
				//jsonObject.put("pic_1", comment_Company.getPic_1());
				//jsonObject.put("pic_2", comment_Company.getPic_2());
				//jsonObject.put("pic_3", comment_Company.getPic_3());
				//jsonObject.put("pic_4", comment_Company.getPic_4());
				//jsonObject.put("pic_5", comment_Company.getPic_5());
				jsonObject.put("is_anonymous", comment_Company.getIs_anonymous());
			} catch (JSONException e) {
				e.printStackTrace();
			}
			if (comment_type==1) prams.add(new BasicNameValuePair("method", PhpUrl.comment_add_method));
			if(comment_type==2) prams.add(new BasicNameValuePair("method", PhpUrl.Comexposal_add_method));
			prams.add(new BasicNameValuePair("content", jsonObject.toString()));
			//Log.i("Test", "���۵�����------------>"+prams.toString());
			new HttpPostThread(prams, handler, type).start();
		}
		

		//ȡ����ע
		public static void getCancelAttention(long id,Handler handler,int type){
			List<NameValuePair> params=new ArrayList<NameValuePair>();
			JSONObject object=new JSONObject();
			try {
				object.put("id", id);
			} catch (Exception e) {
				e.printStackTrace();
			}
			params.add(new BasicNameValuePair("method",HttpUrl.Attention_delete_method));
			params.add(new BasicNameValuePair("content",object.toString()));
			//Log.i("jay_test", "ȡ����ע����----->"+params.toString());
			new HttpPostThread(params, handler, type).start();
		}
		
		//��ҵ��ϸ��Ϣ
		public static void getCompanyInfo(long commpany_id,Handler handler,int type){
			List<NameValuePair> prams2 = new ArrayList<NameValuePair>();
			JSONObject object=new JSONObject();
			try {
				object.put("id", commpany_id);
			} catch (Exception e) {
			e.printStackTrace();
			}
			prams2.add(new BasicNameValuePair("method", "Company.get_info"));
			prams2.add(new BasicNameValuePair("content", object.toString()));
			new HttpPostThread(prams2, handler, type).start();
		}
		
		//��ѯ�����ع���Ϣ����ҵ��
		public static void getInexposalInfo(long exposal_id, Handler handler, int type){
			List<NameValuePair> prams = new ArrayList<NameValuePair>();
			JSONObject object = new JSONObject();
			try {
				object.put("id", exposal_id);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			prams.add(new BasicNameValuePair("method","Inexposal.get_info"));
			prams.add(new BasicNameValuePair("content", object.toString()));
			new HttpPostThread(prams, handler, type).start();
		}
		
		//��ѯ����������Ϣ����ҵ��
		public static void getCommentInfo(long comment_id, Handler handler, int type ){
			
			Log.i("push", "114==========>" );
			
			List<NameValuePair> prams = new ArrayList<NameValuePair>();
			JSONObject object = new JSONObject();
			try {
				object.put("id", comment_id);
				Log.i("push", "115==========>" );
			} catch (JSONException e) {
				e.printStackTrace();
				Log.i("push", "116==========>" );
			}
			prams.add(new BasicNameValuePair("method", "Comment.get_info"));
			prams.add(new BasicNameValuePair("content", object.toString()));
			new HttpPostThread(prams, handler, type).start();
			Log.i("push", "117==========>" );
		}
		
		//��ѯ�����ع���Ϣ����ҵ��
		public static void getComexposalInfo(long comment_id, Handler handler, int type ){
			List<NameValuePair> prams = new ArrayList<NameValuePair>();
			JSONObject object = new JSONObject();
			try {
				object.put("id", comment_id);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			prams.add(new BasicNameValuePair("method", "Comexposal.get_info"));
			prams.add(new BasicNameValuePair("content", object.toString()));
			new HttpPostThread(prams, handler, type).start();
		}
		
		// �Ӻ�ĳ����ҵ
		public static void addDarkCompany(long id1, long id2, Handler handler,
				int type) {
			List<NameValuePair> prams2 = new ArrayList<NameValuePair>();
			JSONObject jsonObject = new JSONObject();
			try {
				jsonObject.put("user_id", id1);
				jsonObject.put("company_id", id2);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			prams2.add(new BasicNameValuePair("method", PhpUrl.add_dark_method));
			prams2.add(new BasicNameValuePair("content", jsonObject.toString()));
			new HttpPostThread(prams2, handler, type).start();
		}
		
		//����
		public static void addPraiseCompany(long id1, long id2, Handler handler,
				int type){
			List<NameValuePair> prams2 = new ArrayList<NameValuePair>();
			JSONObject jsonObject = new JSONObject();
			try {
				jsonObject.put("user_id", id1);
				jsonObject.put("company_id", id2);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			prams2.add(new BasicNameValuePair("method", PhpUrl.add_praise_method));
			prams2.add(new BasicNameValuePair("content", jsonObject.toString()));
			Log.i("Test", "����----->"+prams2.toString());
			new HttpPostThread(prams2, handler, type).start();
		}

		// ��ȡĳ����ҵ������type2 0 ��ʾ������ҵ������ 1 ��ʾ����һ������
		//δ��֤
		//content:{'where':{'company_id':xxx,parent_id':xxx,'is_delete':0,'is_validate':1,'type':'005001'},'page_index':xxx,'page_size':xxx}
	    //content:{'where':{'company_id':xxx,parent_id':xxx,'is_delete':0,'is_validate':1,'_string':'user_id=xxx or is_validate=1'},'page_index':xxx,'page_size':xxx,'user_id':xxx}
		public static void getCompanyComment(Boolean login_type, long company_id, long user_id, Handler handler,
				int type, int page_index, int page_size) {
			List<NameValuePair> prams = new ArrayList<NameValuePair>();
			JSONObject jsonObject = new JSONObject();
			JSONObject jsonObject2 = new JSONObject();
			JSONObject object=new JSONObject();
			try {
				jsonObject.put("company_id", company_id);
				jsonObject.put("parent_id", 0);
				jsonObject.put("is_delete", 0);
				if (login_type) {
					jsonObject.put("_string", "user_id=" + user_id + " or is_validate=1");
					jsonObject2.put("user_id", user_id);
				}else {
					jsonObject.put("is_validate", 1);
				}
				jsonObject2.put("where", jsonObject);
				jsonObject2.put("order", object);
				object.put("v_last_time", "DESC");
				object.put("add_time", "DESC");
				jsonObject2.put("page_index", page_index);
				jsonObject2.put("page_size", page_size);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			prams.add(new BasicNameValuePair("method",
					PhpUrl.company_comment_list_method));
			prams.add(new BasicNameValuePair("content", jsonObject2.toString()));
			Log.i("jay_test", "login_type--->"+login_type+"   δ��֤---------------->"+jsonObject2.toString());
			new HttpPostThread(prams, handler, type).start();
		}
		
		//��ƽ̨
		//content:{'where':{'company_id':xxx,parent_id':xxx,'is_delete':0,'is_validate':1,'type':'005003'},'page_index':xxx,'page_size':xxx}
	    //content:{'where':{'company_id':xxx,parent_id':xxx,'is_delete':0,'is_validate':1,'_string':'user_id=xxx or (is_validate=1 and type='005003')'},'page_index':xxx,'page_size':xxx,'user_id':xxx}
		public static void getDarkPlatform(Boolean login_type, long company_id, long user_id, Handler handler,
				int type, int page_index, int page_size) {
			List<NameValuePair> prams = new ArrayList<NameValuePair>();
			JSONObject object = new JSONObject();
			JSONObject jsonObject = new JSONObject();
			JSONObject jsonObject2=new JSONObject();
			try {
				jsonObject.put("company_id", company_id);
				jsonObject.put("parent_id", 0);
				jsonObject.put("is_delete", 0);
				object.put("where", jsonObject);
				object.put("order", jsonObject2);
				jsonObject2.put("v_last_time", "DESC");
				jsonObject2.put("add_time", "DESC");
				if (login_type) {
					jsonObject.put("_string", "user_id= " + user_id + " or is_validate=1 and type=005003");
					object.put("user_id", user_id);
				}else {
					jsonObject.put("type", "005003");
					jsonObject.put("is_validate", 1);
				}
				object.put("page_index", page_index);
				object.put("page_size", page_size);
			} catch (Exception e) {
				e.printStackTrace();
			}
			prams.add(new BasicNameValuePair("method",
					PhpUrl.company_comment_list_method));
			prams.add(new BasicNameValuePair("content", object.toString()));
			Log.i("jay_test", "��ƽ̨-----------��"+object.toString());
			new HttpPostThread(prams, handler, type).start();
		}
		
		//�Ϲ�ƽ̨
		//content:{'where':{'company_id':xxx,parent_id':xxx,'is_delete':0,'is_validate':1,'type':'005001'},'page_index':xxx,'page_size':xxx}
	    //content:{'where':{'company_id':xxx,parent_id':xxx,'is_delete':0,'is_validate':1,'_string':'user_id=xxx or (is_validate=1 and type='005001')'},'page_index':xxx,'page_size':xxx,'user_id':xxx}
		public static void getCompliancePlatform(Boolean login_type, long company_id, long user_id, Handler handler,
				int type, int page_index, int page_size) {
			List<NameValuePair> prams = new ArrayList<NameValuePair>();
			JSONObject object = new JSONObject();
			JSONObject jsonObject = new JSONObject();
			JSONObject jsonObject2=new JSONObject();
			try {
				jsonObject.put("company_id", company_id);
				jsonObject.put("parent_id", 0);
				jsonObject.put("is_delete", 0);
				object.put("where", jsonObject);
				object.put("order", jsonObject2);
				jsonObject2.put("v_last_time", "DESC");
				jsonObject2.put("add_time", "DESC");
				if (login_type) {
					jsonObject.put("_string", "user_id= " + user_id + " or (is_validate=1 and type=005001)");
					object.put("user_id", user_id);
				}else {
					jsonObject.put("type", "005001");
					jsonObject.put("is_validate", 1);
				}
				object.put("page_index", page_index);
				object.put("page_size", page_size);
			} catch (Exception e) {
				e.printStackTrace();
			}
			prams.add(new BasicNameValuePair("method",
					PhpUrl.company_comment_list_method));
			prams.add(new BasicNameValuePair("content", object.toString()));
			Log.i("jay_test", "login_type----->"+login_type+"   ;�Ϲ�ƽ̨-----------��"+prams.toString());
			new HttpPostThread(prams, handler, type).start();
		}

		// ��ĳһ������
		public static void CommentComtop(long id, long id2, long id3,
				Handler handler, int type) {
			List<NameValuePair> prams = new ArrayList<NameValuePair>();
			JSONObject jsonObject = new JSONObject();
			try {
				jsonObject.put("user_id", id);
				jsonObject.put("company_id", id2);
				jsonObject.put("comment_id", id3);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			prams.add(new BasicNameValuePair("method",
					PhpUrl.company_comment_comtp_method));
			prams.add(new BasicNameValuePair("content", jsonObject.toString()));
	Log.i("jay_test", "��---------------->"+jsonObject.toString());
			new HttpPostThread(prams, handler, type).start();
		}
		
		//ȥ������ͼ��2
		public static CharSequence getCharSequence11(String content, final Context context){
			String html=content;
			html=html.replace("[", "<img src='");
			html=html.replace("]", "'/>");
			
			StringBuffer sb = new StringBuffer(html);
			
			CharSequence charSequence = Html.fromHtml(sb.toString(),
					new Html.ImageGetter() {

						@Override
						public Drawable getDrawable(String source) {
							// ���ϵͳ��Դ����Ϣ������ͼƬ��Ϣ
							Drawable drawable = null;
							try {
								if ("comment1".equals(source)
										|| "comment2".equals(source)
										|| "comment3".equals(source)) {
									drawable = context.getResources().getDrawable(
											getResourceId(source));
									drawable.setBounds(0, 0, DisplayUtil.dip2px(context, 34), DisplayUtil.dip2px(context, 17));
								} else {
									drawable = context.getResources().getDrawable(
											getResourceId(source));
									drawable.setBounds(0, 0, DisplayUtil.dip2px(context, 24), DisplayUtil.dip2px(context, 24));
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
							return drawable;
						}
					}, null);
			return charSequence;
		}
		
		//ȥ������ͼ��1
		public static CharSequence getCharSequence1(Comment_Company2 comment_Company2, final Context context){
			String html = comment_Company2.getContent();
			
			Log.i("Test", "html-->"+html);
			html = html.replace("[", "<img src='");
			html = html.replace("]", "'/>");
			StringBuffer sb = new StringBuffer(html);
			
			CharSequence charSequence = Html.fromHtml(sb.toString(),
					new Html.ImageGetter() {

						@Override
						public Drawable getDrawable(String source) {
							Log.i("Test", "source-->"+source);
							// ���ϵͳ��Դ����Ϣ������ͼƬ��Ϣ
							Drawable drawable=null;
							String head = source.substring(0);
							String end = source.substring(source.length()-1);
							//Log.i("Test", "head-->"+head);
							//Log.i("Test", "end-->"+end);
							try {
								if ("comment1".equals(source)
										|| "comment2".equals(source)
										|| "comment3".equals(source)) {
									drawable = context.getResources().getDrawable(
											getResourceId(source));
									drawable.setBounds(0, 0, DisplayUtil.dip2px(context, 34), DisplayUtil.dip2px(context, 17));
								} else {
									drawable = context.getResources().getDrawable(
											getResourceId(source));
									drawable.setBounds(0, 0, DisplayUtil.dip2px(context,24), DisplayUtil.dip2px(context, 24));
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
							return drawable;
						}
					}, null);
			return charSequence;
		}
		
		// �����û����۵�����
		public static CharSequence getCharSequence(
				Comment_Company2 comment_Company2, final Context context) {
			String html = comment_Company2.getContent();
			html = html.replace("[", "<img src='");
			html = html.replace("]", "'/>");
			StringBuffer sb = new StringBuffer();
			String type = comment_Company2.getType();
			if (type!=null&&!"".equals(type)) {	
			switch (type) {
			case "005001":
				sb.append("<img src ='comment1'/>  ");
				sb.append(html);
				break;
			case "005002":
				sb.append("<img src ='comment2'/>  ");
				sb.append(html);
				break;
			case "005003":
				sb.append("<img src ='comment3'/>  ");
				sb.append(html);
				break;
			default:
				sb.append(html);
				break;
				}
			} else {
				sb.append("<img src ='comment4'/>  ");
				sb.append(html);
			}
			//Log.i("jay_test", "ת��------>"+sb.toString());
			CharSequence charSequence = Html.fromHtml(sb.toString(),
					new Html.ImageGetter() {

						@Override
						public Drawable getDrawable(String source) {
							// ���ϵͳ��Դ����Ϣ������ͼƬ��Ϣ
							Drawable drawable=null;
							try {
								if ("comment1".equals(source)
										|| "comment2".equals(source)
										|| "comment3".equals(source)||"comment4".endsWith(source)) {
									drawable = context.getResources().getDrawable(
											getResourceId(source));
									drawable.setBounds(0, 0, DisplayUtil.dip2px(context, 34), DisplayUtil.dip2px(context,17));
								} else {
									drawable = context.getResources().getDrawable(
											getResourceId(source));
									drawable.setBounds(0, 0, DisplayUtil.dip2px(context, 24), DisplayUtil.dip2px(context, 24));
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
							return drawable;
						}
					}, null);

			return charSequence;
		}
		
		public static CharSequence getCharSequence111(
				Exosure_Company comment_Company2, final Context context) {
			String html = comment_Company2.getContent();
			//Log.i("jay_test", "ת��------>"+html);

			html = html.replace("[", "<img src='");
			html = html.replace("]", "'/>");
			StringBuffer sb = new StringBuffer();
				sb.append("<img src ='comment4'/>  ");
				sb.append(html);
			CharSequence charSequence = Html.fromHtml(sb.toString(),
					new Html.ImageGetter() {

						@Override
						public Drawable getDrawable(String source) {
							// ���ϵͳ��Դ����Ϣ������ͼƬ��Ϣ
							Drawable drawable=null;
							try {
								if ("comment4".equals(source)) {
									drawable = context.getResources().getDrawable(
											getResourceId(source));
									drawable.setBounds(0, 0, DisplayUtil.dip2px(context, 34), DisplayUtil.dip2px(context, 17));
								} else {
									drawable = context.getResources().getDrawable(
											getResourceId(source));
									drawable.setBounds(0, 0, DisplayUtil.dip2px(context, 24), DisplayUtil.dip2px(context, 24));
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
							return drawable;
						}
					}, null);

			return charSequence;
		}
		
		public static int getResourceId(String name) {
			try {
				// ������Դ��ID�ı��������Field�Ķ���ʹ�÷������ʵ�ֵ�
				Field field = R.drawable.class.getField(name);
				
				// ȡ�ò�������Դ��id���ֶΣ���̬��������ֵ��ʹ�÷������
				return Integer.parseInt(field.get(null).toString());

			} catch (Exception e) {
				e.printStackTrace();
			}
			return 0;
		}

		// ��ȡͷ���Url
		public static String getAvatarUrl(long id) {
			String url = id + "";
			StringBuffer sb = new StringBuffer();
			int j = 0;
			for (int i = 0; i < url.length(); i++) {
				if ((j + 2) < url.length()) {
					sb.append(url.substring(i, j + 2));
				} else {
					sb.append(url.substring(j));
					break;
				}
				sb.append("/");
				j += 2;
			}
			return sb.toString();
		}

		// ��ʱ���ת�����ַ���
		public static String getTime(long data) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
			return sdf.format(new Date(data * 1000));
		}

		// ��ʱ���ת�����ַ���
		public static String getTime2(long data) {
			SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
			return sdf.format(new Date(data * 1000));
		}

		// ��ʱ���ת�����ַ���
		public static String getTime3(long data) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
			return sdf.format(new Date(data * 1000));
		}

		// ��ȡĳ�����۵Ļظ�
		public static void getCommentReplyList(int result_type,long id1, long id2, long id3,
				Handler handler, int type, int page_index, int page_size) {
			String method="";
			List<NameValuePair> prams = new ArrayList<NameValuePair>();
			JSONObject jsonObject = new JSONObject();
			JSONObject jsonObject2 = new JSONObject();
			try {
				jsonObject2.put("where", jsonObject);
				switch (result_type) {
				case 1:
					jsonObject.put("company_id", id1);
					jsonObject.put("parent_id", id2);
					method=PhpUrl.company_comment_list_method;
					break;
				case 2:
					jsonObject.put("exposal_id", id1);
					jsonObject.put("parent_id", id2);
					method=PhpUrl.company_exposals_list_method;
					break;
				default:
					break;
				}
				
				jsonObject.put("is_delete", 0);
				jsonObject.put("_string", "user_id=" + id3 + " or is_validate=1");
				jsonObject2.put("page_index", page_index);
				jsonObject2.put("page_size", page_size);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			prams.add(new BasicNameValuePair("method",
					method));
			prams.add(new BasicNameValuePair("content", jsonObject2.toString()));
			//Log.i("jay_test", "���۸�����Ϣ------------>"+prams.toString());
			new HttpPostThread(prams, handler, type).start();
		}
		
		//�����ظ�(ȫ��)
			//δ��¼content:{'where':{'company_id':xxx,'parent_id':xxx,'is_deletie':0,'is_validate':1},'page_index':xxx,'page_size':xxx}
			//һ��¼content:{'where':{'company_id':xxx,parent_id':xxx,'is_delete':0,'_string':'user_id=xxx or is_validate=1'},'page_index':xxx,'page_size':xxx,','user_id':xxx}
		public static void getTopReply_All(int title_type,Comment_Company2 company,Boolean login_type,long Myuser_id,
				Handler handler, int type, int page_index) {
			String method=null;
			   List<NameValuePair> prams = new ArrayList<NameValuePair>();
					JSONObject jsonObject = new JSONObject();
					JSONObject jsonObject2 = new JSONObject();
					JSONObject jsonObject3=new JSONObject();
			try {
				jsonObject2.put("where", jsonObject);
				jsonObject2.put("order", jsonObject3);
				jsonObject3.put("add_time", "ASC");
				jsonObject.put("is_delete", 0);
				switch (title_type) {
				case 1:
					//�ҵ�����
					jsonObject.put("parent_id", company.getId());
					jsonObject.put("company_id", company.getCompany_id());
					method=PhpUrl.company_comment_list_method;
					break;
				case 2:
					//�����ع�
					jsonObject.put("parent_id", 0);
					jsonObject.put("exposal_id", company.getId());
					jsonObject2.put("user_id", Myuser_id);
					method=HttpUrl.Inexposal_get_list_method3;
					break;
				default:
					break;
				}
				if (login_type) {
					jsonObject.put("_string", "user_id=" + Myuser_id
							+ " or is_validate=1");
				} else {
					jsonObject.put("is_validate", 1);
				}
				jsonObject2.put("page_index", page_index);
				//jsonObject2.put("page_size", 10);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			prams.add(new BasicNameValuePair("method",
					method));
			prams.add(new BasicNameValuePair("content", jsonObject2.toString()));
			Log.i("jay_test", "�����ظ�(ȫ��)-------->"+prams.toString());
			new HttpPostThread(prams, handler, type).start();
		}
		
		//�����ظ�(��ͼ������)
		//content:{'where':{'company_id':xxx,'parent_id':xxx,'is_delete':0,'is_validate':1,'pic_1':['neq',0]},'page_index':xxx,'page_size':xxx}
	    //content:{'where':{'company_id':xxx,parent_id':xxx,'is_delete':0,'pic_1':['neq',0],'_string':'user_id=xxx or is_validate=1'},'page_index':xxx,'page_size':xxx,','user_id':xxx}
		public static void getTopReply_Images(int title_type,Comment_Company2 company,Boolean login_type,long Myuser_id,
				Handler handler, int type, int page_index) {
			String method=null;
			List<NameValuePair> prams = new ArrayList<NameValuePair>();
			JSONObject object=new JSONObject();
			JSONObject jsonObject=new JSONObject();
			JSONArray array=new JSONArray();
			JSONObject jsonObject3=new JSONObject();
			try {
				object.put("where", jsonObject);
				object.put("order", jsonObject3);
				jsonObject3.put("add_time", "ASC");
				
				jsonObject.put("is_delete", 0);
				array.put("neq").put(0);
				jsonObject.put("pic_1", array);
				
				switch (title_type) {
				case 1:
					jsonObject.put("company_id", company.getCompany_id());
					jsonObject.put("parent_id", company.getId());
					method=PhpUrl.company_comment_list_method;
					break;
				case 2:
					jsonObject.put("exposal_id", company.getId());
					jsonObject.put("parent_id", 0);
					method=HttpUrl.Inexposal_get_list_method3;
					break;
				}
				if (login_type) {
					jsonObject.put("_string", "user_id=" + Myuser_id+ " or is_validate=1");
					object.put("user_id", Myuser_id);
				}else {
					jsonObject.put("is_validate", 1);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			prams.add(new BasicNameValuePair("method",
					method));
			prams.add(new BasicNameValuePair("content", object.toString()));
			Log.i("jay_test", "�����ظ�(��ͼ������)-------->"+prams.toString());
			new HttpPostThread(prams, handler, type).start();
		}
		
		//�����ظ�(��������)
		//content:{'where':{'company_id':xxx,'parent_id':xxx,'is_delete':0,'is_validate':1,'has_child':['neq',0]},'page_index':xxx,'page_size':xxx}
	    //content:{'where':{'company_id':xxx,parent_id':xxx,'is_delete':0,'has_child':['neq',0],'_string':'user_id=xxx or is_validate=1'},'page_index':xxx,'page_size':xxx,','user_id':xxx}
		public static void getTopReply_Comment(int title_type,Comment_Company2 company,Boolean login_type,long Myuser_id, 
				Handler handler, int type, int page_index) {
			String method=null;
			List<NameValuePair> prams = new ArrayList<NameValuePair>();
			JSONObject object=new JSONObject();
			JSONObject jsonObject=new JSONObject();
			JSONArray array=new JSONArray(); 
			JSONObject jsonObject3=new JSONObject();
			try {
				object.put("where", jsonObject);
				object.put("order", jsonObject3);
				jsonObject3.put("add_time", "ASC");
				
				jsonObject.put("is_delete", 0);
				array.put("neq").put(0);
				jsonObject.put("has_child", array);
				
				switch (title_type) {
				case 1:
					jsonObject.put("company_id", company.getCompany_id());
					jsonObject.put("parent_id", company.getId());
					method=PhpUrl.company_comment_list_method;
					break;
				case 2:
					jsonObject.put("exposal_id", company.getId());
					jsonObject.put("parent_id", 0);
					method=HttpUrl.Inexposal_get_list_method3;
					break;
				default:
					break;
				}
				if (login_type) {
					jsonObject.put("_string", "user_id=" + Myuser_id+ " or is_validate=1");
					object.put("user_id", Myuser_id);
				}else {
					jsonObject.put("is_validate", 1);
				}
				object.put("page_index", page_index);
			} catch (Exception e) {
				e.printStackTrace();
			}
			prams.add(new BasicNameValuePair("method",
					method));
			prams.add(new BasicNameValuePair("content", object.toString()));
			Log.i("jay_test", "�����ظ�(��������)-------->"+prams.toString());
			new HttpPostThread(prams, handler, type).start();
		}
		
		//�����ظ�(¥����Ʒ)
		//content:{'where':{'company_id':xxx,'parent_id':xxx,'is_delete':0,'is_validate':1,'user_id':xxx},'page_index':xxx,'page_size':xxx}
	    //content:{'where':{'company_id':xxx,parent_id':xxx,'is_delete':0,'user_id':xxx,'_string':'user_id=xxx or is_validate=1'},'page_index':xxx,'page_size':xxx,','user_id':xxx}
		public static void getTopReply_Master(int title_type,Comment_Company2 company,Boolean login_type,
				Handler handler, int type, int page_index) {
			String method=null;
			List<NameValuePair> prams = new ArrayList<NameValuePair>();
			JSONObject object=new JSONObject();
			JSONObject jsonObject=new JSONObject();
			JSONObject json=new JSONObject(); 
			JSONObject object2=new JSONObject();
			try {
				object.put("where", jsonObject);
				object.put("where_ex", json);
				object.put("order", object2);
				object2.put("add_time", "ASC");
				
				jsonObject.put("is_delete", 0);
				jsonObject.put("user_id", company.getUser_id());
				jsonObject.put("is_anonymous", 0);
				jsonObject.put("is_validate", 1);
				
				//json.put("company_id", company_id);
				//json.put("parent_id", parent_id);
				json.put("is_delete", 0);
				json.put("is_validate", 1);
				json.put("is_anonymous", 0);
				//json.put("user_id", Top_user_id);
				
				switch (title_type) {
				case 1:
					jsonObject.put("company_id", company.getCompany_id());
					jsonObject.put("parent_id", company.getId());
					json.put("company_id", company.getCompany_id());
					method=PhpUrl.company_comment_list_method;
					break;
				case 2:
					jsonObject.put("exposal_id", company.getId());
					jsonObject.put("parent_id", 0);
					json.put("exposal_id", company.getId());
					method=HttpUrl.Inexposal_get_list_method3;
					break;
				default:
					break;
				}
				
				if (login_type) {
					//jsonObject.put("_string", "user_id=" + Top_user_id+ " or is_validate=1");
					//object.put("user_id", Myuser_id);
					//jsonObject.put("user_id", Top_user_id);
				}else {
					jsonObject.put("is_validate", 1);
				}
				object.put("page_index", page_index);
				//object.put("page_size", page_size); 
				object.put("order", object2);
			} catch (Exception e) {
				e.printStackTrace();
			}
			prams.add(new BasicNameValuePair("method",
					method));
			prams.add(new BasicNameValuePair("content", object.toString()));
			Log.i("jay_test", "�����ظ�(¥����Ʒ)-------->"+prams.toString());
			new HttpPostThread(prams, handler, type).start();
		}

		// �����û��Ļظ���������
		public static CharSequence getCommentReplyContent(
				Comment_Company2 comment_Company2, final Context context) {
			StringBuffer sb = new StringBuffer();
			SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
			String content = comment_Company2.getContent();
			Log.i("Test", "ԭʼ���� content-AAA-->" + content);
			content = content.replace("[", "<img src='");
			content = content.replace("]", "'/>");

			// content = content.replace("'/>", "[ <");
			sb.append("<font color='#3dafea'>");
			sb.append(comment_Company2.getNickname() + "��");
			sb.append("</font>");
			sb.append("<font color='#333'>");
			sb.append(content);
			sb.append("</font>");
			sb.append("<font color='#ffffff'>");
			sb.append(".      .");
			sb.append("</font>");
			//sb.append("<font color='#999999'>");
			//sb.append(sdf.format(new Date(comment_Company2.getAdd_time() * 1000)));
			//sb.append("</font>");
			content = sb.toString().replace("<img src='<", "[ <");
			for (int i = 0; i < sb.length(); i++) {
				
			}
			// Log.i("Test", "�����ع��������-AAA-->"+content);
			CharSequence charSequence = Html.fromHtml(content,
					new Html.ImageGetter() {

						@Override
						public Drawable getDrawable(String source) {
							// ���ϵͳ��Դ����Ϣ������ͼƬ��Ϣ
							Drawable drawable = null;
							try {
								drawable = context.getResources().getDrawable(
										getResourceId(source));
									drawable.setBounds(0, 0, DisplayUtil.dip2px(context, 24), DisplayUtil.dip2px(context, 24));
							} catch (Exception e) {
								e.printStackTrace();
							}
							return drawable;
						}
					}, null);
			return charSequence;
		}
		
		// �� BASE64 ������ַ��� s ���н���
		public static String getFromBASE64(String s) {
			if (s == null) {
				return null;
			} else {
				BASE64Decoder decoder = new BASE64Decoder();
				try {
					byte[] b = decoder.decodeBuffer(s);
					return new String(b);
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
			}
		}
}
