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
	// 网友评论发表
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
			//Log.i("Test", "拿到的数据dsfgsdgdsf------------>"+prams.toString());
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
			//Log.i("Test", "评论的数据------------>"+prams.toString());
			new HttpPostThread(prams, handler, type).start();
		}
		

		//取消关注
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
			//Log.i("jay_test", "取消关注参数----->"+params.toString());
			new HttpPostThread(params, handler, type).start();
		}
		
		//企业详细信息
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
		
		//查询带条曝光信息（企业）
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
		
		//查询单条评论信息（企业）
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
		
		//查询单条曝光信息（企业）
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
		
		// 加黑某个企业
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
		
		//点赞
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
			Log.i("Test", "点赞----->"+prams2.toString());
			new HttpPostThread(prams2, handler, type).start();
		}

		// 拿取某个企业的评论type2 0 表示是那企业的评论 1 表示那下一级评论
		//未认证
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
			Log.i("jay_test", "login_type--->"+login_type+"   未认证---------------->"+jsonObject2.toString());
			new HttpPostThread(prams, handler, type).start();
		}
		
		//黑平台
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
			Log.i("jay_test", "黑平台-----------》"+object.toString());
			new HttpPostThread(prams, handler, type).start();
		}
		
		//合规平台
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
			Log.i("jay_test", "login_type----->"+login_type+"   ;合规平台-----------》"+prams.toString());
			new HttpPostThread(prams, handler, type).start();
		}

		// 顶某一条评论
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
	Log.i("jay_test", "顶---------------->"+jsonObject.toString());
			new HttpPostThread(prams, handler, type).start();
		}
		
		//去掉点赞图标2
		public static CharSequence getCharSequence11(String content, final Context context){
			String html=content;
			html=html.replace("[", "<img src='");
			html=html.replace("]", "'/>");
			
			StringBuffer sb = new StringBuffer(html);
			
			CharSequence charSequence = Html.fromHtml(sb.toString(),
					new Html.ImageGetter() {

						@Override
						public Drawable getDrawable(String source) {
							// 获得系统资源的信息，比如图片信息
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
		
		//去掉点赞图标1
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
							// 获得系统资源的信息，比如图片信息
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
		
		// 设置用户评论的内容
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
			//Log.i("jay_test", "转移------>"+sb.toString());
			CharSequence charSequence = Html.fromHtml(sb.toString(),
					new Html.ImageGetter() {

						@Override
						public Drawable getDrawable(String source) {
							// 获得系统资源的信息，比如图片信息
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
			//Log.i("jay_test", "转移------>"+html);

			html = html.replace("[", "<img src='");
			html = html.replace("]", "'/>");
			StringBuffer sb = new StringBuffer();
				sb.append("<img src ='comment4'/>  ");
				sb.append(html);
			CharSequence charSequence = Html.fromHtml(sb.toString(),
					new Html.ImageGetter() {

						@Override
						public Drawable getDrawable(String source) {
							// 获得系统资源的信息，比如图片信息
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
				// 根据资源的ID的变量名获得Field的对象，使用反射机制实现的
				Field field = R.drawable.class.getField(name);
				
				// 取得并返回资源的id的字段（静态变量）的值，使用反射机制
				return Integer.parseInt(field.get(null).toString());

			} catch (Exception e) {
				e.printStackTrace();
			}
			return 0;
		}

		// 获取头像的Url
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

		// 把时间戳转换成字符串
		public static String getTime(long data) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
			return sdf.format(new Date(data * 1000));
		}

		// 把时间戳转换成字符串
		public static String getTime2(long data) {
			SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
			return sdf.format(new Date(data * 1000));
		}

		// 把时间戳转换成字符串
		public static String getTime3(long data) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
			return sdf.format(new Date(data * 1000));
		}

		// 拿取某条评论的回复
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
			//Log.i("jay_test", "评论附带信息------------>"+prams.toString());
			new HttpPostThread(prams, handler, type).start();
		}
		
		//主贴回复(全部)
			//未登录content:{'where':{'company_id':xxx,'parent_id':xxx,'is_deletie':0,'is_validate':1},'page_index':xxx,'page_size':xxx}
			//一登录content:{'where':{'company_id':xxx,parent_id':xxx,'is_delete':0,'_string':'user_id=xxx or is_validate=1'},'page_index':xxx,'page_size':xxx,','user_id':xxx}
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
					//我的评论
					jsonObject.put("parent_id", company.getId());
					jsonObject.put("company_id", company.getCompany_id());
					method=PhpUrl.company_comment_list_method;
					break;
				case 2:
					//网友曝光
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
			Log.i("jay_test", "主贴回复(全部)-------->"+prams.toString());
			new HttpPostThread(prams, handler, type).start();
		}
		
		//主贴回复(有图有真相)
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
			Log.i("jay_test", "主贴回复(有图有真相)-------->"+prams.toString());
			new HttpPostThread(prams, handler, type).start();
		}
		
		//主贴回复(精彩评论)
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
			Log.i("jay_test", "主贴回复(精彩评论)-------->"+prams.toString());
			new HttpPostThread(prams, handler, type).start();
		}
		
		//主贴回复(楼主出品)
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
			Log.i("jay_test", "主贴回复(楼主出品)-------->"+prams.toString());
			new HttpPostThread(prams, handler, type).start();
		}

		// 设置用户的回复评论内容
		public static CharSequence getCommentReplyContent(
				Comment_Company2 comment_Company2, final Context context) {
			StringBuffer sb = new StringBuffer();
			SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
			String content = comment_Company2.getContent();
			Log.i("Test", "原始数据 content-AAA-->" + content);
			content = content.replace("[", "<img src='");
			content = content.replace("]", "'/>");

			// content = content.replace("'/>", "[ <");
			sb.append("<font color='#3dafea'>");
			sb.append(comment_Company2.getNickname() + "：");
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
			// Log.i("Test", "网友曝光解析数据-AAA-->"+content);
			CharSequence charSequence = Html.fromHtml(content,
					new Html.ImageGetter() {

						@Override
						public Drawable getDrawable(String source) {
							// 获得系统资源的信息，比如图片信息
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
		
		// 将 BASE64 编码的字符串 s 进行解码
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
