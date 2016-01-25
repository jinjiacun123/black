package com.example.black.lib;

public class PhpUrl {
	//测试服务器
//	public  static String SOUHEIURL = "http://192.168.1.131/yms_api/index.php?m=Soapi";
	//正式服务器	
	public  static String SOUHEIURL = "http://api.koubei365.com.cn";
//	public  static String SOUHEIURL = "http://api.souhei.com.cn";
	//public  static String SOUHEIURL2 = "http://192.168.1.131";
	public  static String SOUHEIURL2 = "http://www.souhei.com.cn";
	//public  static String AVATARURl = "http://192.168.1.31:8310/";
	//头像地址域名
	public  static String AVATARURl = "http://avatar.koubei365.com.cn/";
	public  static String SouheiSearch11 = "http://192.168.1.131/yms/index.php/Home/Query/query_hpt/resid/";
	public  static String SouheiSearch22 = "http://192.168.1.131/yms/index.php/Home/Query/query_wrz/resid/";
	public  static String SouheiSearch33 = "http://192.168.1.131/yms/index.php/Home/Query/query_yrz/resid/";
	//public  static String SouheiNews = "http://192.168.1.131/yms/index.php/Home/Article/article/id/";
	public  static String SouheiSearch1 = "http://souhei.com.cn/hpt?resid=";
	public  static String SouheiSearch2 = "http://souhei.com.cn/wrz?resid=";
	public  static String SouheiSearch3 = "http://souhei.com.cn/yrz?resid=";
//	public  static String SouheiNews = "http://www.souhei.com.cn/index.php/Home/Article/article/id/";
	public static String SouheiNews = "http://souhei.com.cn/article?id=";

	//用户接口域名
	//public static String update_aravter = "http://192.168.1.31:8300/Api/SetUserAvatarByUid";
	public static String update_aravter = "http://accountapi.koubei365.com.cn";
	//企业别名搜索
	public static String search_method = "Company.search";
	//企业id搜索
	public static String getCommpany_method = "Company.get_info";
	//企业评论
	public static String comment_add_method = "Comment.add";
	//曝光评论
	public static String Comexposal_add_method = "Comexposal.add";
	//企业加黑
	public static String add_dark_method = "Addblack.add";
	//企业点赞
	public static String add_praise_method = "Companyassist.add";
	//评论查询
	public static String company_comment_list_method = "Comment.get_list_ex";
	//评论顶
	public static String company_comment_comtp_method= "Comtop.add";
	//评论(企业曝光)查询
	public static String company_exposal_list_method= "Inexposal.get_list_com";
	//企业曝光的回复
	public static String company_exposals_list_method= "Comexposal.get_list";
	//发表企业曝光的回复
	public static String company_exposals_add_method= "Comexposal.add";
	//曝光顶
	public static String company_exposal_comtp_method = "Exposaltop.add";
	//曝光回复顶
	public static String Comexposaltop_add_method = "Comexposaltop.add";
	//评论新闻查询
	public static String company_news_list_method= "News.get_list";
	//新闻的回复
	public static String company_news_reply_list_method= "Comnews.get_list";
	//评论媒体报道
	public static String company_news_add_method= "Comnews.add";
	//媒体评论点赞
	public static String company_news_assist_method= "Comnewsassist.add";
}

