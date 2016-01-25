package com.example.black.lib;

public class HttpUrl {
	//注销、离线
	public static final String push_offline = "Token.offline";
	//推送
	public static final String push_method = "Token.add";
	// 图片拼接路径
	public static final String image_url = "http://192.168.1.31:8310/";
	// 注册
	public static final String register_method = "User.register";
	// 登录
	public static final String login_method = "User.login";
	// 获取登录信息
	public static final String get_login_method = "User.get_login_info";
	// 找回密码
	public static final String findpwd_method = "User.find_passwd";
	// 修改密码
	public static final String updatepwd_method = "User.update_passwd";
	// 获取用户信息
	public static final String userinfo_method = "User.get_info";
	// 修改用户信息
	public static final String update_method = "User.update";
	// 我要曝光
	public static final String exposure_method = "Inexposal.add";
	// 手机号码是否存在
	public static final String check_mobile_method = "User.check_mobile";
	// 图形验证码地址
	public static final String pic_validate_method = "User.get_pic_validate";
	// 发送手机验证码
	public static final String send_validate_method = "User.send_validate";
	// 关于我们
	// public static final String News_method = "News.get_info";
	// 下载地址
	public static final String News_method = "Down.index";
	// 检测第三方是否绑定
	public static final String check_loginname_method = "User.check_loginname";
	// 第三方微信绑定
	public static final String entry_weixin_method = "User.entry_weixin";
	// 第三方qq绑定
	public static final String entry_qq_method = "User.entry_qq";
	// 第三方微博绑定
	public static final String entry_weibo_method = "User.entry_weibo";
	// 微信登陆
	public static final String login_weixin_method = "User.login_weixin";
	// qq登陆
	public static final String login_qq_method = "User.login_qq";
	// 微博登陆
	public static final String login_weibo_method = "User.login_weibo";
	// 解绑
	public static final String cancel_bind_method = "User.cancel_bind";
	//加黑排行榜
	public static final String get_list_method = "Company.get_list";
	//评论排行榜
	public static final String get_list_com_method = "Company.get_list_com";
	//曝光排行榜
	public static final String get_list_exposal_method = "Company.get_list_exposal";
	//别名搜索
	public static final String search_method = "Company.search";
	//获取点赞数
	public static final String  amount_method="Companyassist.amount";
	//意见反馈
	public static final String  Ideaback_add_method="Ideaback.add";
	//我的评论
	public static final String  Comment_get_list_method="Comment.get_list";
	//(个人中心)我的曝光
	public static final String  Inexposal_get_list_method="Inexposal.get_list_com";
	//(平台详情页)网友曝光
	public static final String  Inexposal_get_list_method3="Comexposal.get_list_com_ex";
	//我的关注
	public static final String  Attention_get_list_method="Attention.get_list";
	//取消关注
	public static final String  Attention_delete_method="Attention.delete";
	//对企业进行关注
	public static final String  Attention_add_method="Attention.add";
	//公司识别次数
	public static final String Querylog_get_list_method="Querylog.get_list";
	//用户曝光次数
	public static final String Inexposal_get_list_method2 ="Inexposal.get_list";
	//曝光动态
	public static final String Inexposal_dynamic_method ="Inexposal.dynamic";
	//单条企业信息
	public static final String Company_get_info_method ="Company.get_info";
	//查询单条新闻信息
	public static final String News_get_info = "News.get_info";
}
