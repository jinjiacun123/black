package com.example.black.lib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.black.lib.model.Alias_List;
import com.example.black.lib.model.Comment_Company2;
import com.example.black.lib.model.Company;
import com.example.black.lib.model.ExosureImage;
import com.example.black.lib.model.Exosure_Company;
import com.example.black.lib.model.Exosure_Reply;
import com.example.black.lib.model.Exposure_Dynamic;
import com.example.black.lib.model.MineAttention;
import com.example.black.lib.model.NewsComment;
import com.example.black.lib.model.News_Company;
import com.example.black.lib.model.Query_UserInfo;
import com.example.black.lib.model.RankingList;


import android.annotation.SuppressLint;
import android.text.TextUtils;

/*lib.model*/
import com.example.black.lib.model.UserInfo;

public class JsonUtil {
	private List<Company> companys = null;
	private List<Comment_Company2> comment_company2s = null;
	private List<Exosure_Company> exosure_companys = null;
	private List<News_Company> news_Companys = null;
	private List<Exosure_Reply> exosure_Replys = null;
	private List<NewsComment> newsComments = null;
	private Map<String , Object> map = null;
	
	// 注册用户
		public static UserInfo getUserInfo(String restult) {		
			UserInfo info = null;
			try {
				JSONObject object = new JSONObject(restult);
				int status_code = Integer.valueOf(object.getString("status_code"));
				if (status_code == 200) {
					JSONObject object2 = object.getJSONObject("content");
					if (object2 != null) {
						Integer is_success = Integer.valueOf(object2
								.getString("is_success"));
						info = new UserInfo();
						if (is_success == 0) {
							info.setIs_sucess(is_success);
							info.setUser_id(object2.getString("user_id"));
							info.setNickname(object2.getString("nickname"));
							info.setSex(object2.getString("sex"));
							info.setCur_date(object2.getString("cur_date"));
						} else {
							info.setIs_sucess(is_success);
						}
						return info;
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}
		
		//曝光动态
		public static List<Exposure_Dynamic> getExposure_Dynamic(String result){
			List<Exposure_Dynamic> list=new ArrayList<Exposure_Dynamic>();
			Exposure_Dynamic dynamic=null;
			if (result != null && !"".equals(result)) {
				try {
					JSONObject object = new JSONObject(result);
					int status_code = Integer.valueOf(object
							.getString("status_code"));
					if (status_code == 200) {
						JSONObject object2 = object.getJSONObject("content");
						if (object2 != null) {
							JSONArray dynamic_list = object2.getJSONArray("list");
							if (dynamic_list!=null) {
								for (int i = 0; i < dynamic_list.length(); i++) {
									JSONObject jsonObject = dynamic_list.getJSONObject(i);
									dynamic=new Exposure_Dynamic();
									dynamic.setId(jsonObject.getString("id"));
									dynamic.setCompany_id(jsonObject.getString("company_id"));
									dynamic.setUser_id(jsonObject.getString("user_id"));
									dynamic.setNickname(jsonObject.getString("nickname"));
									dynamic.setAdd_time(jsonObject.getString("add_time"));
									dynamic.setCompany_name(jsonObject.getString("company_name"));
									dynamic.setAuth_level(jsonObject.getString("auth_level"));
									dynamic.setContent(jsonObject.getString("content"));
									dynamic.setPic_1(jsonObject.getString("pic_1"));
									dynamic.setPic_1_url(jsonObject.getString("pic_1_url"));
									dynamic.setRe_amount(jsonObject.getString("re_amount"));
									list.add(dynamic);
								}
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return list;
		}
		
		//推送
		public static PushInfo getPushInfo(String result){
			PushInfo info = null;
			if(result != null && !"".equals(result)){
				try {
					JSONObject object = new JSONObject(result);
					int status_code = Integer.valueOf(object.getString("status_code"));
					if(status_code == 200){
						JSONObject jsonObject = object.getJSONObject("content");
						if(jsonObject != null){
							Integer is_success = Integer.valueOf(jsonObject
									.getString("is_success"));
							info = new PushInfo();
							info.setIs_sucess(is_success);
							return info;
						}
					}
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			return null;
		}

		// 用户登录
		public static UserInfo getLoginInfo(String result) {
			UserInfo info = null;
			if (result != null && !"".equals(result)) {
				try {
					JSONObject object = new JSONObject(result);
					int status_code = Integer.valueOf(object
							.getString("status_code"));
					if (status_code == 200) {
						JSONObject object2 = object.getJSONObject("content");
						if (object2 != null) {
							Integer is_success = Integer.valueOf(object2
									.getString("is_success"));
							info = new UserInfo();
							if (is_success == 0) {
								info.setIs_sucess(is_success);
								info.setHead_portrait(object2
										.getString("head_portrait"));
								info.setUser_id(object2.getString("user_id"));
								info.setNickname(object2.getString("nickname"));
								info.setSex(object2.getString("sex"));
								info.setCur_date(object2.getString("cur_date"));
							} else {
								info.setIs_sucess(is_success);
							}
							return info;
						}
					}

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			return null;
		}

		// 查看用户信息
		public static Query_UserInfo getQuery_UserInfo(String result) {
			Query_UserInfo info = null;
			try {
				JSONObject object = new JSONObject(result);
				int status_code = Integer.valueOf(object.getString("status_code"));
				if (status_code == 200) {
					JSONArray object2 = object.getJSONArray("content");
					if (object2 != null) {
						info = new Query_UserInfo();
						JSONObject jsonObject = object2.getJSONObject(0);
						info.setUI_Id(jsonObject.getString("UI_Id"));
						info.setUI_NickName(jsonObject.getString("UI_NickName"));
						info.setUI_LoginNames(jsonObject.getString("UI_LoginNames"));
						info.setUI_Sex(jsonObject.getString("UI_Sex"));
						info.setUI_BirthDay(jsonObject.getString("UI_BirthDay"));
						info.setUI_Job(jsonObject.getString("UI_Job"));
						info.setUI_Address(jsonObject.getString("UI_Address"));
						info.setUI_Avatar(jsonObject.getString("UI_Avatar"));
						return info;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		
		//排行榜
		public static List<RankingList> getRankingList(String result){
			List<RankingList> ranking_list=null;
			RankingList rankinglist=null;
			if (result!=null&&!"".equals(result)) {
				try {
					JSONObject jsonObject=new JSONObject(result);
					Integer status_code = Integer.valueOf(jsonObject.getString("status_code"));
					if (status_code==200) {
						JSONObject content = jsonObject.getJSONObject("content");
						if (content!=null) {
							JSONArray list = content.getJSONArray("list");
							if (list!=null) {
								ranking_list=new ArrayList<RankingList>();
								for (int i = 0; i < list.length(); i++) {							
									JSONObject object = (JSONObject) list.get(i);
									if (object!=null) {
										rankinglist=new RankingList();
										rankinglist.setId(object.getString("id"));
										rankinglist.setLogo_url(object.getString("logo_url"));
										rankinglist.setCompany_name(object.getString("company_name"));
										rankinglist.setAlias_list(object.getString("alias_list"));
										rankinglist.setAdd_blk_amount(object.getLong("add_blk_amount"));
										rankinglist.setCom_amount(object.getLong("com_amount"));
										rankinglist.setExp_amount(object.getLong("exp_amount"));
									    ranking_list.add(rankinglist);
									}
								}
								return ranking_list;
							}
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}		
			}		
			return null;
		}
		//id ,logo ,nature(企业性质) ,trade(所属行业) ,company_name (公司名),alias_list(别名)
		//获取别名列表
		public static List<Alias_List> getAlias_List(String result){
			List<Alias_List> alias_list=null;
			Alias_List aliaslist=null;
			if (result!=null&&!"".equals(result)) {
				try {
					JSONObject jsonObject=new JSONObject(result);
					Integer status_code = Integer.valueOf(jsonObject.getString("status_code"));
					if (status_code==200) {
						JSONObject content = jsonObject.getJSONObject("content");
						if (content!=null) {
							JSONArray list = content.getJSONArray("list");
							if (list!=null) {
								alias_list=new ArrayList<Alias_List>();
								for (int i = 0; i < list.length(); i++) {							
									JSONObject object = (JSONObject) list.get(i);
									if (object!=null) {
										aliaslist=new Alias_List();
										aliaslist.setId(object.getString("id"));
										aliaslist.setLogo_url(object.getString("logo_url"));
										aliaslist.setNature(object.getString("nature"));
										aliaslist.setTrade(object.getString("trade"));
										aliaslist.setCompany_name(object.getString("company_name"));
										aliaslist.setLias_list(object.getString("alias_list"));
										alias_list.add(aliaslist);
									}
								}
								return alias_list;
							}
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}		
			}
			return null;
		}

		// 曝光上传图片
		public static ExosureImage getExosureImageId(String result) {
			ExosureImage image = null;
			try {
				JSONObject object = new JSONObject(result);
				int status_code = Integer.valueOf(object.getString("status_code"));
				if (status_code == 200) {
					JSONObject object2 = object.getJSONObject("content");
					if (object2 != null) {
						int is_success = Integer.valueOf(object2
								.getString("is_success"));
						image = new ExosureImage();
						if (is_success == 0) {
							image.setIs_sucess(is_success);
							image.setImg(object2.getString("img"));
							image.setId(object2.getString("id"));
						} else {
							image.setIs_sucess(is_success);
						}
						return image;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		
		//我的关注
			public static List<MineAttention> getMineAttention(String result){
				List<MineAttention> list=null;
				MineAttention attention=null;
				if (result!=null &&!"".equals(result)) {
					try {
						JSONObject object = new JSONObject(result);
						int status_code = Integer.valueOf(object.getString("status_code"));
						if (status_code == 200) {
							JSONObject object2 = object.getJSONObject("content");
							if (object2 != null) {
								list =new ArrayList<MineAttention>();
								JSONArray jsonArray = object2.getJSONArray("list");
								for (int i = 0; i < jsonArray.length(); i++) {
									JSONObject object3 = (JSONObject) jsonArray.get(i);
									attention=new MineAttention();
									attention.setId(object3.getLong("id"));
									attention.setUser_id(object3.getLong("user_id"));
									attention.setUser_nickname(object3.getString("user_nickname"));
									attention.setCompany_id(object3.getLong("company_id"));
									attention.setCompany_name(object3.getString("company_name"));
									attention.setAdd_time(object3.getLong("add_time"));
									list.add(attention);
								}
								return list;
							}
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				return null;
			}

		// 检测号码是否存在
		public static int getCheck_Mobile(String result) {
			try {
				JSONObject object = new JSONObject(result);
				int status_code = Integer.valueOf(object.getString("status_code"));
				if (status_code == 200) {
					JSONObject object2 = object.getJSONObject("content");
					if (object2 != null) {
						return Integer.valueOf(object2.getString("is_exists"));
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return -2;
		}

		// 修改用户信息
		public static int getUpdate_Info(String result) {
			if (result != null && !"".equals(result)) {
				try {
					JSONObject object = new JSONObject(result);
					int status_code = Integer.valueOf(object
							.getString("status_code"));
					if (status_code == 200) {
						JSONObject object2 = object.getJSONObject("content");
						if (object2 != null) {
							return Integer.valueOf(object2.getString("is_success"));
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return -2;
		}
		
		//曝光
		public Map<String, Object> getExosureInfo(String result){
			Map<String, Object> map=null;
			if (!TextUtils.isEmpty(result)) {
				try {
					JSONObject object = new JSONObject(result);
					int status_code = Integer.valueOf(object
							.getString("status_code"));
					map.put("status_code", status_code);
					if (status_code == 200) {
						JSONObject object2 = object.getJSONObject("content");
						if (object2 != null) {
							Integer is_success = Integer.valueOf(object2.getString("is_success"));
							map.put("is_success", is_success);
							if (is_success==0) {
								map.put("id", Long.valueOf(object2.getString("id")));
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return map;
		}
		
		//修改用户头像
		public static int getUpdate_icon(String result) {
			if (result != null && !"".equals(result)) {			
				try {
					JSONObject object = new JSONObject(result);
					if (object!=null) {
						return Integer.valueOf(object
								.getString("State"));
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			return -7;
		}

		// 是否绑定第三方
		public static int check_loginname(String result) {
			if (result != null && !"".equals(result)) {
				try {
					JSONObject object = new JSONObject(result);
					int status_code = Integer.valueOf(object
							.getString("status_code"));
					if (status_code == 200) {
						JSONObject object2 = object.getJSONObject("content");
						if (object2 != null) {
							return Integer.valueOf(object2.getString("is_exists"));
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return -4;
		}

		// 图形验证码
		public static String getPic_Validate(String result) {
			try {
				if (result != null && !"".equals(result)) {
					JSONObject object = new JSONObject(result);
					int status_code = Integer.valueOf(object
							.getString("status_code"));
					if (status_code == 200) {
						String object2 = object.getString("content");
						if (object2 != null) {
							return object2;
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		// 关于我们
		public static String getUs(String result) {
			try {
				if (result != null && !"".equals(result)) {
					JSONObject object = new JSONObject(result);
					int status_code = Integer.valueOf(object
							.getString("status_code"));
					if (status_code == 200) {
						JSONObject object2 = object.getJSONObject("content");
						if (object2 != null) {
							return object2.getString("url");
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		
		//推送通知数据解析
		public PushInfo getpushInfo(String result){
			PushInfo pushInfo = null;
			try {
				JSONObject jsonObject = new JSONObject(result);
				if(jsonObject != null){
					pushInfo = new PushInfo();
					pushInfo.setContent(jsonObject.getString("content"));
					pushInfo.setComment(jsonObject.getString("comment"));
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return pushInfo;
		}
		
		//返会公司的数据
		public List<Company> getCompanys(String result){
			if(result!=null && !"".equals(result)){
				try {
					JSONObject jsonObject = new JSONObject(result);
					int code = jsonObject.getInt("status_code");
					if(code == 200){
						JSONObject obj = jsonObject.getJSONObject("content");					
						JSONArray array = obj.getJSONArray("list");
						companys = new ArrayList<Company>();
						for (int i = 0; i < array.length(); i++) {
							Company company = new Company();
							JSONObject object = array.getJSONObject(i);
							company.setCompany_id(object.getInt("id"));//id
							company.setLogo(object.getInt("logo"));//logo
							company.setBusin_license(object.getInt("busin_license"));
							company.setBusin_license_url(object.getString("busin_license_url"));
							company.setCode_certificate_url(object.getString("code_certificate_url"));
							company.setCertificate_url(object.getString("certificate_url"));
							company.setCode_certificate(object.getInt("code_certificate"));
							company.setCertificatE(object.getInt("certificate"));
							company.setAdd_time(object.getInt("add_time"));
							company.setNature(object.getString("nature"));//nature
							company.setTrade(object.getString("trade"));//trade
							company.setCompany_name(object.getString("company_name"));//company_name
							company.setAuth_level(object.getString("auth_level"));//auth_level
//							company.setCompany_type(object.getString("company_type"));
							company.setReg_address(object.getString("reg_address"));
							company.setTelephone(object.getString("telephone"));
							company.setWebsite(object.getString("website"));
							company.setRecord(object.getString("record"));
							company.setFind_website(object.getString("find_website"));
							company.setAgent_platform(object.getString("agent_platform"));
							company.setMem_sn(object.getString("mem_sn"));
							company.setAlias_list(object.getString("alias_list"));//alias_list
							company.setAdd_blk_amount(object.getInt("add_blk_amount"));
							company.setExp_amount(object.getInt("exp_amount"));
							company.setCom_amount(object.getInt("com_amount"));
							company.setLogo_url(object.getString("logo_url"));//logo_url
							company.setRegulators_id(object.getInt("regulators_id"));
							//company.setRegulators_id_n(object.getString("regulators_id_n"));
							company.setAgent_platform_n(object.getString("agent_platform_n"));
							companys.add(company);
						}	
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			return companys;
		}
		
		//单条公司记录
		public Company getOne_Companys(String result){
			Company company=null;
			if(result!=null && !"".equals(result)){
				try {
					JSONObject jsonObject = new JSONObject(result);
					int code = jsonObject.getInt("status_code");
					if(code == 200){
						JSONObject object = jsonObject.getJSONObject("content");
						if (object!=null) {
							company=new Company();
							company.setCompany_id(object.getInt("id"));//id
							company.setLogo(object.getInt("logo"));//logo
							company.setBusin_license(object.getInt("busin_license"));
							company.setBusin_license_url(object.getString("busin_license_url"));
							company.setCode_certificate_url(object.getString("code_certificate_url"));
							company.setCertificate_url(object.getString("certificate_url"));
							company.setCode_certificate(object.getInt("code_certificate"));
							company.setCertificatE(object.getInt("certificate"));
							company.setAdd_time(object.getInt("add_time"));
							company.setNature(object.getString("nature"));//nature
							company.setTrade(object.getString("trade"));//trade
							company.setCompany_name(object.getString("company_name"));//company_name
							company.setAuth_level(object.getString("auth_level"));//auth_level
//							company.setCompany_type(object.getString("company_type"));
							company.setReg_address(object.getString("reg_address"));
							company.setTelephone(object.getString("telephone"));
							company.setWebsite(object.getString("website"));
							company.setRecord(object.getString("record"));
							company.setFind_website(object.getString("find_website"));
							company.setAgent_platform(object.getString("agent_platform"));
							company.setMem_sn(object.getString("mem_sn"));
							company.setAlias_list(object.getString("alias_list"));//alias_list
							company.setAdd_blk_amount(object.getInt("add_blk_amount"));
							company.setExp_amount(object.getInt("exp_amount"));
							company.setCom_amount(object.getInt("com_amount"));
							company.setLogo_url(object.getString("logo_url"));//logo_url
							company.setRegulators_id(object.getInt("regulators_id"));
							//company.setRegulators_id_n(object.getString("regulators_id_n"));
							company.setAgent_platform_n(object.getString("agent_platform_n"));
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return company;
		}
		
		//排行榜
		public List<Company> getMyRankingList(String result,int type){
			if(result!=null && !"".equals(result)){
				try {
					JSONObject jsonObject = new JSONObject(result);
					int code = jsonObject.getInt("status_code");
					if(code == 200){
						JSONObject obj = jsonObject.getJSONObject("content");
						JSONArray array = obj.getJSONArray("list");
						companys = new ArrayList<Company>();
						for (int i = 0; i < array.length(); i++) {
							Company company = new Company();
							JSONObject object = array.getJSONObject(i);
							company.setCompany_id(object.getInt("id"));//id
							company.setLogo(object.getInt("logo"));//logo
							company.setBusin_license(object.getInt("busin_license"));
							company.setBusin_license_url(object.getString("busin_license_url"));
							company.setCode_certificate_url(object.getString("code_certificate_url"));
							company.setCertificate_url(object.getString("certificate_url"));
							company.setCode_certificate(object.getInt("code_certificate"));
							company.setCertificatE(object.getInt("certificate"));
							company.setAdd_time(object.getInt("add_time"));
							company.setNature(object.getString("nature"));//nature
							company.setTrade(object.getString("trade"));//trade
							company.setCompany_name(object.getString("company_name"));//company_name
							company.setAuth_level(object.getString("auth_level"));//auth_level
//							company.setCompany_type(object.getString("company_type"));
							company.setReg_address(object.getString("reg_address"));
							company.setTelephone(object.getString("telephone"));
							company.setWebsite(object.getString("website"));
							company.setRecord(object.getString("record"));
							company.setFind_website(object.getString("find_website"));
							company.setAgent_platform(object.getString("agent_platform"));
							company.setMem_sn(object.getString("mem_sn"));
							company.setAlias_list(object.getString("alias_list"));//alias_list
							company.setAdd_blk_amount(object.getInt("add_blk_amount"));
							company.setExp_amount(object.getInt("exp_amount"));
							company.setCom_amount(object.getInt("com_amount"));
							company.setLogo_url(object.getString("logo_url"));//logo_url
							company.setRegulators_id(object.getInt("regulators_id"));
							//company.setRegulators_id_n(object.getString("regulators_id_n"));
							company.setAgent_platform_n(object.getString("agent_platform_n"));
							
							switch (type) {
							case 1:
								//评论
								JSONObject sub_com = object.getJSONObject("sub_com");
								if (sub_com!=null) {
									company.setSub_com(sub_com.getString("content"));
								}
								break;
							case 2:
								//曝光
								JSONObject sub_exposal = object.getJSONObject("sub_exposal");
								if (sub_exposal!=null) {
									company.setSub_com(sub_exposal.getString("content"));
								}
								break;
							default:
								break;
							}
							
							companys.add(company);
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			return companys;
			}
		
		
		//企业评论的结果值
		public List<Comment_Company2> getComment_Company2s(int type,String result){
			if(result!=null && !"".equals(result)){
				try {
					JSONObject jsonObject = new JSONObject(result);
					int code = jsonObject.getInt("status_code");
					if(code == 200){
						JSONObject object = jsonObject.getJSONObject("content");
						JSONArray array = object.getJSONArray("list");
						comment_company2s =new ArrayList<Comment_Company2>();
						for (int i = 0; i < array.length(); i++) {
							JSONObject obj = array.getJSONObject(i);
							Comment_Company2 comment_company2 = new Comment_Company2();
							comment_company2.setId(obj.getInt("id"));
							comment_company2.setUser_id(obj.getInt("user_id"));
							comment_company2.setNickname(obj.getString("nickname"));
							//comment_company2.setCompany_id(obj.getInt("company_id"));
							comment_company2.setParent_id(obj.getInt("parent_id"));
							comment_company2.setType(obj.getString("type"));
							comment_company2.setContent(obj.getString("content"));
							comment_company2.setPic_1(obj.getInt("pic_1"));
							comment_company2.setPic_2(obj.getInt("pic_2"));
							comment_company2.setPic_3(obj.getInt("pic_3"));
							comment_company2.setPic_4(obj.getInt("pic_4"));
							comment_company2.setPic_5(obj.getInt("pic_5"));
							comment_company2.setPic_1_url(obj.getString("pic_1_url"));
							comment_company2.setPic_2_url(obj.getString("pic_2_url"));
							comment_company2.setPic_3_url(obj.getString("pic_3_url"));
							comment_company2.setPic_4_url(obj.getString("pic_4_url"));
							comment_company2.setPic_5_url(obj.getString("pic_5_url"));
							comment_company2.setIs_anonymous(obj.getInt("is_anonymous"));
							comment_company2.setTop_num(obj.getInt("top_num"));
							comment_company2.setAdd_time(obj.getLong("add_time"));
							comment_company2.setHas_child(obj.getLong("has_child"));
							comment_company2.setLast_time(obj.getLong("last_time"));
							comment_company2.setLast_nickname(obj.getString("last_nickname"));
							comment_company2.setV_last_time(obj.getLong("v_last_time"));
							comment_company2.setV_last_nickname(obj.getString("v_last_nickname"));
							comment_company2.setV_last_is_anonymous(obj.getLong("v_last_is_anonymous"));
							//comment_company2.setHas_child_ex(obj.getLong("has_child_ex"));
							
							if (type!=2) {
								comment_company2.setCompany_id(obj.getInt("company_id"));
								JSONObject re_sub = obj.getJSONObject("re_sub");
								comment_company2.setRecord_count(re_sub.getLong("record_count"));
							}else {
								comment_company2.setCompany_id(obj.getInt("exposal_id"));
							}
//							}
							//JSONObject re_sub = obj.getJSONObject("re_sub");					
							//comment_company2.setRecord_count(re_sub.getLong("record_count"));						
							comment_company2s.add(comment_company2);
						}
						
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			return comment_company2s;
		}	
		
		//加黑，评论返回结果值
		public  Map<String, String> getOperateResult(String result){
			Map<String , String> map = new HashMap<String, String>();
			map.put("is_success", -1+"");
			if(result!=null && !"".equals(result)){
				try {
					JSONObject jsonObject = new JSONObject(result);
					int code = jsonObject.getInt("status_code");
					if(code == 200){
						JSONObject object = jsonObject.getJSONObject("content");
						map.put("is_success", object.getInt("is_success")+"");
						map.put("message", object.getString("message"));
						//map.put("amount", object.getString("amount"));
						map.put("id", object.getInt("id")+"");
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return map;
		}
		
		//获取单条评论信息（企业）
		@SuppressLint("NewApi")
		public Comment_Company2 getCommentInfo(String result){
			Comment_Company2 comment_Company2 = null;
			if(result != null && !result.isEmpty()){
				try {
					JSONObject jsonObject = new JSONObject(result);
					int code = jsonObject.getInt("status_code");
					if(code == 200){
						JSONObject object = jsonObject.getJSONObject("content");
						if(object != null){
							comment_Company2 = new Comment_Company2();
							comment_Company2.setId(object.getInt("id"));
							comment_Company2.setUser_id(object.getInt("user_id"));
							comment_Company2.setNickname(object.getString("nickname"));
							comment_Company2.setCompany_id(object.getInt("company_id"));
							comment_Company2.setParent_id(object.getInt("parent_id"));
//								comment_Company2.setPparent_id(object.getInt("pparent_id"));
							comment_Company2.setType(object.getString("type"));
							comment_Company2.setContent(object.getString("content"));
							comment_Company2.setIs_anonymous(object.getInt("is_anonymous"));
							comment_Company2.setTop_num(object.getInt("top_num"));
//							comment_Company2.setHas_child(object.getLong("has_child"));
							comment_Company2.setAdd_time(object.getLong("add_time"));
							comment_Company2.setPic_1_url(object.getString("pic_1_url"));
							comment_Company2.setPic_2_url(object.getString("pic_2_url"));
							comment_Company2.setPic_3_url(object.getString("pic_3_url"));
							comment_Company2.setPic_4_url(object.getString("pic_4_url"));
							comment_Company2.setPic_5_url(object.getString("pic_5_url"));
						}
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return comment_Company2;
		} 
		
		//获取单条曝光信息二级（企业）
		@SuppressLint("NewApi") 
		public Comment_Company2 getComexposalInfo(String result){
			Comment_Company2 comment_Company2 = null;
			if(result != null && !result.isEmpty()){
				try {
					JSONObject jsonObject = new JSONObject(result);
					int code = jsonObject.getInt("status_code");
					if(code == 200){
						JSONObject object = jsonObject.getJSONObject("content");
						if(object != null){
							comment_Company2 = new Comment_Company2();
							comment_Company2.setId(object.getInt("id"));
							comment_Company2.setUser_id(object.getInt("user_id"));
							comment_Company2.setCompany_id(object.getInt("exposal_id"));
							comment_Company2.setParent_id(object.getInt("parent_id"));
							comment_Company2.setNickname(object.getString("nickname"));
							comment_Company2.setContent(object.getString("content"));
							comment_Company2.setIs_validate(object.getInt("is_validate"));
							comment_Company2.setValidate_time(object.getInt("validate_time"));
							comment_Company2.setIs_anonymous(object.getInt("is_anonymous"));
							comment_Company2.setIs_delete(object.getInt("is_delete"));
							comment_Company2.setPic_1(object.getInt("pic_1"));
							comment_Company2.setPic_2(object.getInt("pic_2"));
							comment_Company2.setPic_3(object.getInt("pic_3"));
							comment_Company2.setPic_4(object.getInt("pic_4"));
							comment_Company2.setPic_5(object.getInt("pic_5"));
							comment_Company2.setPic_1_url(object.getString("pic_1_url"));
							comment_Company2.setPic_2_url(object.getString("pic_2_url"));
							comment_Company2.setPic_3_url(object.getString("pic_3_url"));
							comment_Company2.setPic_4_url(object.getString("pic_4_url"));
							comment_Company2.setPic_5_url(object.getString("pic_5_url"));
							comment_Company2.setType(object.getString("type"));
							comment_Company2.setTop_num(object.getInt("top_num"));
							comment_Company2.setHas_child(object.getInt("has_child"));
							comment_Company2.setChilds(object.getInt("childs"));
							comment_Company2.setLast_child_time(object.getInt("last_child_time"));
							comment_Company2.setLast_time(object.getInt("last_time"));
							comment_Company2.setLast_user_id(object.getInt("last_user_id"));
							comment_Company2.setAdd_time(object.getLong("add_time"));
						}
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return comment_Company2;
		}
		
		//返回有多少条数
		public int getRowContent(String result){
			if(result!=null && !"".equals(result)){
				try {
					JSONObject jsonObject = new JSONObject(result);
					int code = jsonObject.getInt("status_code");
					if(code == 200){
						JSONObject object = jsonObject.getJSONObject("content");
					return object.getInt("record_count");
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}	
			
			return 0;
		}
		
		//获取企业曝光的回复
		public List<Exosure_Reply> getExsureReply(String result){
			if(result!=null && !"".equals(result)){
				try {
					JSONObject jsonObject = new JSONObject(result);
					int code = jsonObject.getInt("status_code");
					if(code == 200){
						JSONObject object = jsonObject.getJSONObject("content");
						JSONArray array = object.getJSONArray("list");
						exosure_Replys = new ArrayList<>();
						for (int i = 0; i < array.length(); i++) {
							JSONObject obj = array.getJSONObject(i);
							Exosure_Reply exosure_Reply = new Exosure_Reply();
							exosure_Reply.setNickname(obj.getString("nickname"));
							exosure_Reply.setContent(obj.getString("content"));
							exosure_Reply.setAdd_time(obj.getInt("add_time"));
							exosure_Replys.add(exosure_Reply);
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			return exosure_Replys;
		}
		
		//企业点赞
		public Map<String, String> getPraise_Company(String result){
			Map<String, String> map=new HashMap<String, String>();
			if (result!=null &&!"".equals(result)) {
				try {
					JSONObject jsonObject = new JSONObject(result);
					int code = jsonObject.getInt("status_code");
					if(code == 200){
						JSONObject object = jsonObject.getJSONObject("content");
						map.put("is_success", object.getInt("is_success")+"");
						map.put("message", object.getString("message"));
						map.put("amount", object.getString("amount"));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return map;
		}
		
		public String getRowContent22(String result){
			if(result!=null && !"".equals(result)){
				try {
					JSONObject jsonObject = new JSONObject(result);
					int code = jsonObject.getInt("status_code");
					if(code == 200){
						JSONObject object = jsonObject.getJSONObject("content");
					return object.getString("record_count");
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}	
			
			return "";
		}
		
		//某条评论下带的回复
		public Map<String , Object > getComment_Company2s2(int type,String result){
			map = new HashMap<>();
			if(result!=null && !"".equals(result)){
				try {
					JSONObject jsonObject = new JSONObject(result);
					int code = jsonObject.getInt("status_code");
					if(code == 200){
						JSONObject object = jsonObject.getJSONObject("content");
						JSONArray array = object.getJSONArray("list");
						for (int i = 0; i < array.length(); i++) {
							JSONObject obj = array.getJSONObject(i);
							String  re_sub=null;
							if (type!=2) {
								re_sub = obj.getJSONObject("re_sub").toString();
							}else {
								re_sub=obj.getJSONObject("sub").toString();
							}
							long id = obj.getLong("id");
							map.put("R"+id, getRowContent2(re_sub));
							map.put(""+id,getCommentCompany(type,re_sub));
						}
					}	
				} catch (JSONException e) {
						e.printStackTrace();
				}
			}
			return map;
	}
		
		//拿到某条评论带的回复数
		private String getRowContent2(String result){
			String row = "";
			try {
				JSONObject obj = new JSONObject(result);
				row = obj.getString("record_count");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return row;
		}
		
		//我的评论拿到某条评论带的回复集合
		private List<Comment_Company2> getCommentCompany(int type,String reult){
			List<Comment_Company2> list = new ArrayList<>();
			try {
				JSONObject object = new JSONObject(reult);
				JSONArray array = object.getJSONArray("list");
				for (int i = 0; i < array.length(); i++) {
					JSONObject obj = array.getJSONObject(i);
					Comment_Company2 comment_company2 = new Comment_Company2();
					comment_company2.setId(obj.getInt("id"));
					switch (type) {
					case 1:
						comment_company2.setCompany_id(obj.getInt("company_id"));
						break;
					case 2:
						comment_company2.setCompany_id(obj.getInt("exposal_id"));
						break;
					}
					comment_company2.setNickname(obj.getString("nickname"));
					comment_company2.setParent_id(obj.getInt("parent_id"));
					comment_company2.setType(obj.getString("type"));
					comment_company2.setContent(obj.getString("content"));
					comment_company2.setAdd_time(obj.getLong("add_time"));
					list.add(comment_company2);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return list;
		}
		
		//获取曝光列表
		public List<Exosure_Company> getExosure_Companys(String result){
			if(result!=null && !"".equals(result)){
				try {
					JSONObject jsonObject = new JSONObject(result);
					int code = jsonObject.getInt("status_code");
					if(code == 200){
						JSONObject object = jsonObject.getJSONObject("content");
						JSONArray array = object.getJSONArray("list");
						exosure_companys = new ArrayList<Exosure_Company>();
						for (int i = 0; i < array.length(); i++) {
							JSONObject obj = array.getJSONObject(i);
							Exosure_Company exosure_Company = new Exosure_Company();
							exosure_Company.setId(obj.getInt("id"));
							exosure_Company.setCompany_id(obj.getInt("company_id"));
							exosure_Company.setUser_id(obj.getInt("user_id"));
							exosure_Company.setNickname(obj.getString("nickname"));
							exosure_Company.setNature(obj.getString("nature"));
							exosure_Company.setTrade(obj.getString("trade"));
							exosure_Company.setCompany_name(obj.getString("company_name"));
							exosure_Company.setAmount(obj.getString("amount"));
							exosure_Company.setWebsite(obj.getString("website"));
							exosure_Company.setContent(obj.getString("content"));
							exosure_Company.setPic_1_url(obj.getString("pic_1_url"));
							exosure_Company.setPic_2_url(obj.getString("pic_2_url"));
							exosure_Company.setPic_3_url(obj.getString("pic_3_url"));
							exosure_Company.setPic_4_url(obj.getString("pic_4_url"));
							exosure_Company.setPic_5_url(obj.getString("pic_5_url"));
							exosure_Company.setPic_1(obj.getInt("pic_1"));
							exosure_Company.setPic_2(obj.getInt("pic_2"));
							exosure_Company.setPic_3(obj.getInt("pic_3"));
							exosure_Company.setPic_4(obj.getInt("pic_4"));
							exosure_Company.setPic_5(obj.getInt("pic_5"));
							exosure_Company.setTop_num(obj.getInt("top_num"));
							exosure_Company.setIs_delete(obj.getInt("is_delete"));
							exosure_Company.setAdd_time(obj.getInt("add_time"));
							exosure_Company.setLast_time(obj.getLong("last_time"));
							exosure_Company.setV_last_time(obj.getLong("v_last_time"));
							exosure_Company.setV_last_nickname(obj.getString("v_last_nickname"));
							exosure_Company.setV_last_is_anonymous(obj.getLong("v_last_is_anonymous"));
							exosure_Company.setLast_user_id(obj.getInt("last_user_id"));
							exosure_Company.setV_last_user_id(obj.getInt("v_last_user_id"));
							JSONObject sub = obj.getJSONObject("sub");						
							exosure_Company.setRecord_count(sub.getLong("record_count"));
							exosure_companys.add(exosure_Company);
						}
					}
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			return exosure_companys;
		}
		
		//获取曝光下面带 的回复数
		
		public Map<String , Object> getgetExosure_Company2s(String result){
			map = new HashMap<>();
			if(result!=null && !"".equals(result)){
				try {
					JSONObject jsonObject = new JSONObject(result);
					int code = jsonObject.getInt("status_code");
					if(code == 200){
						JSONObject object = jsonObject.getJSONObject("content");
						JSONArray array = object.getJSONArray("list");
						for (int i = 0; i < array.length(); i++) {
							JSONObject obj = array.getJSONObject(i);
							String  re_sub = obj.getJSONObject("sub").toString();
							long id = obj.getLong("id");
							map.put("R"+id, getRowContent2(re_sub));
							map.put(""+id,getCommentCompany(2,re_sub));
						}
					}	
				} catch (JSONException e) {
						e.printStackTrace();
				}
			}
			return map;
		}
		
		//获取哦评论列表
		public List<News_Company> getNews_Company(String result){ 
			if(result!=null && !"".equals(result)){
				try {
					JSONObject jsonObject = new JSONObject(result);
					int code = jsonObject.getInt("status_code");
					if(code == 200){
						JSONObject object = jsonObject.getJSONObject("content");
						JSONArray array = object.getJSONArray("list");
						news_Companys = new ArrayList<>();
						for (int i = 0; i < array.length(); i++) {
							JSONObject object2 = array.getJSONObject(i);
							News_Company news_Company = new News_Company();
							news_Company.setId(object2.getInt("id"));
							news_Company.setCompany_id(object2.getInt("company_id"));
							news_Company.setPic(object2.getInt("pic"));
//							news_Company.setPic_app(object2.getInt("pic_app"));
							news_Company.setAssist_num(object2.getInt("assist_num"));
							news_Company.setAdd_time(object2.getInt("add_time"));
							//news_Companys.
							news_Company.setTitle(object2.getString("title"));
							news_Company.setSource(object2.getString("source"));
							news_Company.setAuthor(object2.getString("author"));
							news_Company.setContent(object2.getString("content"));
							news_Company.setPic_url(object2.getString("pic_url"));
							news_Companys.add(news_Company);
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			return news_Companys;
		}
		public List<Company> getCompanys2(String result){
			if(result!=null && !"".equals(result)){
				try {
					JSONObject jsonObject = new JSONObject(result);
					int code = jsonObject.getInt("status_code");
					if(code == 200){
						JSONObject object = jsonObject.getJSONObject("content");
						companys = new ArrayList<Company>();
							Company company = new Company();
							company.setCompany_id(object.getInt("id"));
							company.setLogo(object.getInt("logo"));
							company.setBusin_license(object.getInt("busin_license"));
							company.setBusin_license_url(object.getString("busin_license_url"));
							company.setCode_certificate_url(object.getString("code_certificate_url"));
							company.setCertificate_url(object.getString("certificate_url"));
							company.setCode_certificate(object.getInt("code_certificate"));
							company.setCertificatE(object.getInt("certificate"));
							company.setAdd_time(object.getInt("add_time"));
							company.setNature(object.getString("nature"));
							company.setTrade(object.getString("trade"));
							company.setCompany_name(object.getString("company_name"));
							company.setAuth_level(object.getString("auth_level"));
//							company.setCompany_type(object.getString("company_type"));
							company.setReg_address(object.getString("reg_address"));
							company.setTelephone(object.getString("telephone"));
							company.setWebsite(object.getString("website"));
							company.setRecord(object.getString("record"));
							company.setFind_website(object.getString("find_website"));
							company.setAgent_platform(object.getString("agent_platform"));
							company.setMem_sn(object.getString("mem_sn"));
							company.setAlias_list(object.getString("alias_list"));
							company.setAdd_blk_amount(object.getInt("add_blk_amount"));
							company.setExp_amount(object.getInt("exp_amount"));
							company.setCom_amount(object.getInt("com_amount"));
							company.setLogo_url(object.getString("logo_url"));
							company.setRegulators_id(object.getInt("regulators_id"));
							company.setRegulators_id_n(object.getString("regulators_id_n"));
							company.setAgent_platform_n(object.getString("agent_platform_n"));
							companys.add(company);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			return companys;
		}
		
		//企业评论附带回复
		public List<Comment_Company2> getComment_Company2s_and_reply(int type,String result){
		List<Comment_Company2>	comment_company2s =new ArrayList<Comment_Company2>();
			if(result!=null && !"".equals(result)){
				try {
					JSONObject jsonObject = new JSONObject(result);
					int code = jsonObject.getInt("status_code");
					if(code == 200){
						JSONObject object = jsonObject.getJSONObject("content");
						JSONArray array = object.getJSONArray("list");
						if (array!=null) {
							for (int i = 0; i < array.length(); i++) {
								JSONObject obj = array.getJSONObject(i);
								Comment_Company2 comment_company2 = new Comment_Company2();
								comment_company2.setId(obj.getInt("id"));
								comment_company2.setUser_id(obj.getInt("user_id"));
								comment_company2.setNickname(obj.getString("nickname"));
								
								comment_company2.setParent_id(obj.getInt("parent_id"));
								comment_company2.setType(obj.getString("type"));
								comment_company2.setContent(obj.getString("content"));
								comment_company2.setPic_1(obj.getInt("pic_1"));
								comment_company2.setPic_2(obj.getInt("pic_2"));
								comment_company2.setPic_3(obj.getInt("pic_3"));
								comment_company2.setPic_4(obj.getInt("pic_4"));
								comment_company2.setPic_5(obj.getInt("pic_5"));
								comment_company2.setPic_1_url(obj.getString("pic_1_url"));
								comment_company2.setPic_2_url(obj.getString("pic_2_url"));
								comment_company2.setPic_3_url(obj.getString("pic_3_url"));
								comment_company2.setPic_4_url(obj.getString("pic_4_url"));
								comment_company2.setPic_5_url(obj.getString("pic_5_url"));
								comment_company2.setIs_anonymous(obj.getInt("is_anonymous"));
								comment_company2.setTop_num(obj.getInt("top_num"));
								comment_company2.setAdd_time(obj.getLong("add_time"));
								comment_company2.setHas_child(obj.getLong("has_child"));
								comment_company2.setLast_time(obj.getLong("last_time"));
								comment_company2.setLast_nickname(obj.getString("last_nickname"));
								
								if (type!=2) {
									comment_company2.setCompany_id(obj.getInt("company_id"));
									JSONObject re_sub = obj.getJSONObject("re_sub");
									comment_company2.setRecord_count(re_sub.getLong("record_count"));
								}else {
									comment_company2.setCompany_id(obj.getInt("exposal_id"));
								}
								//Log.i("jay_test", "haha--->"+re_sub);
//							JSONArray list = re_sub.getJSONArray("list");
////								//Log.i("jay_test", "haha--->"+list.toString());
//								if (list!=null&&list.length()>0) {
//									//Log.i("Test", "回复---->"+list.toString());
//									List<Comment_Company2> list2=new ArrayList<Comment_Company2>();
//									for (int j = 0; j < list.length(); j++) {
//										JSONObject jsonObject2 =  (JSONObject) list.get(j);
//										if (jsonObject2!=null) {
//											Log.i("jay_test", "haha-jsonObject2-->"+jsonObject2.toString());
//											Comment_Company2 comment_company22 = new Comment_Company2();
//											comment_company22.setId(jsonObject2.getLong("id"));
//											comment_company22.setUser_id(jsonObject2.getLong("user_id"));
//											comment_company22.setNickname(jsonObject2.getString("nickname"));
//											comment_company22.setCompany_id(jsonObject2.getInt("company_id"));
//											comment_company22.setCompany_name(jsonObject2.getString("company_name"));
//											comment_company22.setParent_id(jsonObject2.getInt("parent_id"));
//											comment_company22.setType(jsonObject2.getString("type"));
//											comment_company22.setContent(jsonObject2.getString("content"));
//											comment_company22.setPic_1(jsonObject2.getInt("pic_1"));
//											comment_company22.setPic_2(jsonObject2.getInt("pic_2"));
//											comment_company22.setPic_3(jsonObject2.getInt("pic_3"));
//											comment_company22.setPic_4(jsonObject2.getInt("pic_4"));
//											comment_company22.setPic_5(jsonObject2.getInt("pic_5"));
//											comment_company22.setPic_1_url(jsonObject2.getString("pic_1_url"));
//											comment_company22.setPic_2_url(jsonObject2.getString("pic_2_url"));
//											comment_company22.setPic_3_url(jsonObject2.getString("pic_3_url"));
//											comment_company22.setPic_4_url(jsonObject2.getString("pic_4_url"));
//											comment_company22.setPic_5_url(jsonObject2.getString("pic_5_url"));
//											comment_company22.setIs_anonymous(jsonObject2.getInt("is_anonymous"));
//											comment_company22.setTop_num(jsonObject2.getInt("top_num"));
//											comment_company22.setAdd_time(jsonObject2.getLong("add_time"));
//											comment_company22.setHas_child(jsonObject2.getLong("has_child"));
//											comment_company22.setLast_time(jsonObject2.getLong("last_time"));
//											comment_company22.setLast_nickname(jsonObject2.getString("last_nickname"));
//											list2.add(comment_company22);
//											Log.i("jay_test", "附带回复------------------->"+list2);
//										}else {
//											Log.i("jay_test", "hehe--->"+jsonObject2.toString());
//										}
//									}
//									comment_company2.setList(list2);
								//}
								//comment_company2.setRecord_count(re_sub.getLong("record_count"));						
								comment_company2s.add(comment_company2);
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return comment_company2s;
		}
}
