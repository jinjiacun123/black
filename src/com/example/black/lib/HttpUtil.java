package com.example.black.lib;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.util.Log;

public class HttpUtil {
	//测试
		//private static final String URL2 = "http://192.168.1.131/yms_api/index.php?m=Soapi";
		//
		private static final String URL2 = "http://api.souhei.com.cn";

		//上传图片
		public static String UpLoad(String image_url, String image_number) {
			if (image_url != null && !"".equals(image_url)) {
				// 文件路径（不包括文件名）
				String file_path = image_url.substring(0,
						image_url.lastIndexOf("/") + 1);
				// 文件后缀格式
				String file_type = image_url
						.substring(image_url.lastIndexOf(".") + 1);
				// 文件名
				String new_image_url = image_url.substring(image_url
						.lastIndexOf("/") + 1);
				String file_name = new_image_url.substring(0,
						new_image_url.lastIndexOf("."));

				HttpClient httpClient = null;
				try {
					httpClient = new DefaultHttpClient();
					HttpPost httpPost = new HttpPost(URL2);
					MultipartEntity entity = new MultipartEntity(
							HttpMultipartMode.BROWSER_COMPATIBLE);
					entity.addPart("method", new StringBody("Media.upload"));
					entity.addPart("type", new StringBody("resource"));
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("field_name", file_name);
					jsonObject.put("file_ext", file_type);
					jsonObject.put("file_name", file_name);
					jsonObject.put("module_sn", image_number);
					entity.addPart("content", new StringBody(jsonObject.toString()));
					FileBody filBody = new FileBody(new File(image_url));
					entity.addPart(file_name, filBody);
					httpPost.setEntity(entity);
					HttpResponse response = httpClient.execute(httpPost);
					if (response.getStatusLine().getStatusCode() == 200) {
					return  EntityUtils.toString(response.getEntity());
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return null;
		}
		
		//添加关注
		public static void initAttention_add(long user_id,long company_id,Handler handler,int type){
			List<NameValuePair> params=new ArrayList<NameValuePair>();
			JSONObject object=new JSONObject();
			try {
				object.put("user_id", user_id);
				object.put("company_id", company_id);
			} catch (Exception e) {
				e.printStackTrace();
			}
			params.add(new BasicNameValuePair("method", HttpUrl.Attention_add_method));
			params.add(new BasicNameValuePair("content",object.toString()));
			new HttpPostThread(params, handler, type).start();
		}
		
		//曝光动态
			public void initExposure_Dynamic(Handler handler,int type){
				List<NameValuePair> params=new ArrayList<NameValuePair>();
				JSONObject object=new JSONObject();
				try {
					object.put("page_size", 2);
				} catch (Exception e) {
					e.printStackTrace();
				}
				params.add(new BasicNameValuePair("method", HttpUrl.Inexposal_dynamic_method));
				params.add(new BasicNameValuePair("content",object.toString()));
				Log.e("Test", "--->"+params.toString());
				new HttpPostThread(params, handler, type).start();
			}
			
			//查询最多
			public static void initSearchMax(Handler handler,int type){
				List<NameValuePair> params=new ArrayList<NameValuePair>();
				JSONObject object=new JSONObject();
				JSONObject object2=new JSONObject();
				try {
					object2.put("select_amount", "DESC");
					object.put("order", object2);
					object.put("page_size", 1);
				} catch (Exception e) {
					e.printStackTrace();
				}
				params.add(new BasicNameValuePair("method", HttpUrl.get_list_method));
				params.add(new BasicNameValuePair("content",object.toString()));
				new HttpPostThread(params, handler, type).start();
			}
			
			//单条企业信息
			public static void initCompanyInfo(String id,Handler handler,int type){
				List<NameValuePair> params=new ArrayList<NameValuePair>();
				JSONObject object=new JSONObject();
				try {
					object.put("id", id);
				} catch (Exception e) {
					e.printStackTrace();
				}
				params.add(new BasicNameValuePair("method", HttpUrl.Company_get_info_method));
				params.add(new BasicNameValuePair("content",object.toString()));
				new HttpPostThread(params, handler, type).start();
			}
			
			//查询单条新闻信息
			public static void initNewsInfo(String id,Handler handler,int type){
				List<NameValuePair> params=new ArrayList<NameValuePair>();
				JSONObject object=new JSONObject();
				try {
					object.put("id", id);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				params.add(new BasicNameValuePair("method", HttpUrl.News_get_info));
				params.add(new BasicNameValuePair("content",object.toString()));
				new HttpPostThread(params, handler, type).start();
			}
		
		//排行榜
		public static void initRankingList(Boolean drak_type,String auth_level,long page_index,long page_size,String method, Handler handler,int type) {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			JSONObject object = new JSONObject();
			JSONObject object2 = new JSONObject();
			JSONObject object3=new JSONObject();
			JSONObject object5 = new JSONObject();	
			JSONArray array = new JSONArray();
			JSONArray array2=new JSONArray();
			try {
				
				array.put("neq").put(0);
				array2.put("neq").put("006003");
				
				JSONObject object4 = new JSONObject();
				object4.put(auth_level, array);
				
				if (drak_type) {
					object4.put("auth_level", array2);
				}
				
				object5.put("where", object4);						
				
				JSONObject object7 = new JSONObject();
//				object7.put("order", object6);

				object5.put("page_index", page_index);
				
				JSONObject object6 = new JSONObject();
				object6.put(auth_level, "DESC");
				
				object5.put("order", object6);
				object5.put("page_size", page_size);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			params.add(new BasicNameValuePair("method",method));
			params.add(new BasicNameValuePair("content", object5.toString()));
			 Log.i("Test", "排行榜--->"+params.toString());
			new HttpPostThread(params, handler, type).start();
		}
		
		
		
		//用户信息(我的曝光)
		
		//"auth_level": [
		             //  "in",
		               //"006001,006002"
		         //  ]
		public static void initMineExosureFragment(Context context,long user_id,Handler handler,int page_index,int type){
			List<NameValuePair> params=new ArrayList<NameValuePair>();
			JSONObject object=new JSONObject();
			JSONObject object2=new JSONObject();
			JSONObject object3=new JSONObject();
			JSONArray array=new JSONArray();
			JSONArray array2=new JSONArray();
			try {
				object2.put("user_id", user_id);
				object.put("where", object2);
				object.put("where_ex", object3);
				object.put("page_index", page_index);
				//Log.i("Test", "ta--->"+user_id+" my--->"+Util.getShare_User_id(context));
				if (user_id!=Util.getShare_User_id(context)) {
					object2.put("auth_level", array);
					object2.put("company_id", array2);
					array.put("in").put("006001,006002");
					array2.put("neq").put(0);
					object3.put("_string", "is_validate =1");
				}else {
					object3.put("_string", "user_id=" + user_id + " or is_validate =1");
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			params.add(new BasicNameValuePair("method",HttpUrl.Inexposal_get_list_method));
			params.add(new BasicNameValuePair("content",object.toString()));
			//Log.i("Test", "我的曝光----------------->"+params.toString());
			new HttpPostThread(params, handler, type).start();
		}
		
		//我的评论
		public static void initMine_CommentFragment(Context context,long user_id,Handler handler,int page_index,int type){
			List<NameValuePair> params=new ArrayList<NameValuePair>();
			JSONObject object=new JSONObject();
			JSONObject object2=new JSONObject();
			try {
				object2.put("user_id", user_id);
				object.put("where", object2);
				object.put("page_index", page_index);
				object2.put("is_delete", 0);
				object2.put("tag", 1);
				if (user_id!=Util.getShare_User_id(context)) {
					object2.put("is_validate", "1");
					object2.put("_string", "(auth_level =006003 and type = 005001) or (auth_level =006001 and type = 005003) or auth_level =006002");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			params.add(new BasicNameValuePair("method",HttpUrl.Comment_get_list_method));
			params.add(new BasicNameValuePair("content",object.toString()));
			//Log.e("Test", "我的评论----------------->"+params.toString());
			new HttpPostThread(params, handler, type).start();
		}
		
		//我的关注
		public static void initMineFragment(Long user_id, Handler handler,String method,int page_index,int type){
			List<NameValuePair> params=new ArrayList<NameValuePair>();
			JSONObject object=new JSONObject();
			JSONObject object2=new JSONObject();
			JSONObject object3=new JSONObject();
			try {
				object2.put("user_id", user_id);
				
				object.put("where", object2);
				object.put("page_index", page_index);
			} catch (Exception e) {
				e.printStackTrace();
			}
			params.add(new BasicNameValuePair("method",method));
			params.add(new BasicNameValuePair("content",object.toString()));
			//Log.i("Test", "我的评论----------------->"+params.toString());
			new HttpPostThread(params, handler, type).start();
		}
		
		public static NetworkInfo isNetworkInfo(Context context){
			ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			 return manager.getActiveNetworkInfo();
		}
		
		public  void initCompanyDiscernCount(Handler handler,int type){
			List<NameValuePair> params=new ArrayList<NameValuePair>();
			JSONObject object = new JSONObject();
			try {
				object.put("page_size", 1);
			} catch (Exception e) {
				e.printStackTrace();
			}
			params.add(new BasicNameValuePair("method",HttpUrl.Querylog_get_list_method));
			params.add(new BasicNameValuePair("content",object.toString()));
			new HttpPostThread(params, handler, type).start();
		}
		
		public  void initCompanyPlatformCount(Handler handler,int type){
			List<NameValuePair> params=new ArrayList<NameValuePair>();
			JSONObject object = new JSONObject();
			try {
				object.put("page_size", 1);
			} catch (Exception e) {
				e.printStackTrace();
			}
			params.add(new BasicNameValuePair("method",HttpUrl.get_list_method));
			params.add(new BasicNameValuePair("content",new JSONObject().toString()));
			new HttpPostThread(params, handler, type).start();
		}
		
		public  void initCompanyExosureCount(Handler handler,int type){
			List<NameValuePair> params=new ArrayList<NameValuePair>();
			JSONObject object=new JSONObject();
			try {
				object.put("type", 0);
				object.put("page_size", 1);
			} catch (Exception e) {
				e.printStackTrace();
			}
			params.add(new BasicNameValuePair("method",HttpUrl.Inexposal_get_list_method2));
			params.add(new BasicNameValuePair("content",object.toString()));
			new HttpPostThread(params, handler, type).start();
		}
		
		public void initVip(Long id, int page_index, int page_size, Handler handler, int type) {
			List<NameValuePair> params=new ArrayList<NameValuePair>();
			JSONObject object=new JSONObject();
			JSONObject jsonObject=new JSONObject();
			try {
				object.put("page_index", page_index);
				object.put("page_size", page_size);
				jsonObject.put("agent_platform", id);
				jsonObject.put("nature", "003001");
				object.put("where", jsonObject);
			} catch (Exception e) {
				e.printStackTrace();
			}
			params.add(new BasicNameValuePair("method",HttpUrl.get_list_method));
			params.add(new BasicNameValuePair("content",object.toString()));
			Log.e("Test", params.toString());
			new HttpPostThread(params, handler, type).start();
		}
		
		public void initPush(long user_id, long is_offline , String token, Handler handler, int type){
			List<NameValuePair> params=new ArrayList<NameValuePair>();
			JSONObject object=new JSONObject();
			try {
				object.put("user_id", user_id);
				object.put("token", token);
				object.put("type", "008002");
				object.put("is_offline", is_offline);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			params.add(new BasicNameValuePair("method",HttpUrl.push_method));
			params.add(new BasicNameValuePair("content",object.toString()));
			Log.e("Test", params.toString());
			new HttpPostThread(params, handler, type).start();
		}
		//注销时的离线接口调用
		public void initOffline(long user_id,  Handler handler, int type){
			List<NameValuePair> params=new ArrayList<NameValuePair>();
			JSONObject object=new JSONObject();
			try {
				object.put("user_id", user_id);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			params.add(new BasicNameValuePair("method",HttpUrl.push_offline));
			params.add(new BasicNameValuePair("content",object.toString()));
			new HttpPostThread(params, handler, type).start();
		}
}
