package com.example.black.lib.model;

/**
 * 用户信息详情页
 * 
 * @author Admin
 * 
 */
public class Query_UserInfo {
	private String UI_Id;// 用户id
	private String UI_NickName;// 用户昵称
	private String UI_LoginNames;// 绑定手机
	private String UI_Sex;// 性别
	private String UI_BirthDay;// 生日
	private String UI_Job;// 职业
	private String UI_Address;// 所在地
	private String UI_Avatar;// 用户图片

	public Query_UserInfo() {
		// TODO Auto-generated constructor stub
	}

	public Query_UserInfo(String uI_Id, String uI_NickName,
			String uI_LoginNames, String uI_Sex, String uI_BirthDay,
			String uI_Job, String uI_Address, String uI_Avatar) {
		super();
		UI_Id = uI_Id;
		UI_NickName = uI_NickName;
		UI_LoginNames = uI_LoginNames;
		UI_Sex = uI_Sex;
		UI_BirthDay = uI_BirthDay;
		UI_Job = uI_Job;
		UI_Address = uI_Address;
		UI_Avatar = uI_Avatar;
	}

	@Override
	public String toString() {
		return "Query_UserInfo [UI_Id=" + UI_Id + ", UI_NickName="
				+ UI_NickName + ", UI_LoginNames=" + UI_LoginNames
				+ ", UI_Sex=" + UI_Sex + ", UI_BirthDay=" + UI_BirthDay
				+ ", UI_Job=" + UI_Job + ", UI_Address=" + UI_Address
				+ ", UI_Avatar=" + UI_Avatar + "]";
	}

	public String getUI_Avatar() {
		return UI_Avatar;
	}

	public void setUI_Avatar(String uI_Avatar) {
		UI_Avatar = uI_Avatar;
	}

	public String getUI_Id() {
		return UI_Id;
	}

	public void setUI_Id(String uI_Id) {
		UI_Id = uI_Id;
	}

	public String getUI_NickName() {
		return UI_NickName;
	}

	public void setUI_NickName(String uI_NickName) {
		UI_NickName = uI_NickName;
	}

	public String getUI_LoginNames() {
		return UI_LoginNames;
	}

	public void setUI_LoginNames(String uI_LoginNames) {
		UI_LoginNames = uI_LoginNames;
	}

	public String getUI_Sex() {
		return UI_Sex;
	}

	public void setUI_Sex(String uI_Sex) {
		UI_Sex = uI_Sex;
	}

	public String getUI_BirthDay() {
		return UI_BirthDay;
	}

	public void setUI_BirthDay(String uI_BirthDay) {
		UI_BirthDay = uI_BirthDay;
	}

	public String getUI_Job() {
		return UI_Job;
	}

	public void setUI_Job(String uI_Job) {
		UI_Job = uI_Job;
	}

	public String getUI_Address() {
		return UI_Address;
	}

	public void setUI_Address(String uI_Address) {
		UI_Address = uI_Address;
	}
}
