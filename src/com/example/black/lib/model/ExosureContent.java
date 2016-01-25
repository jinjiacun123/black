package com.example.black.lib.model;

//��Ҫ�ع��ύ������
public class ExosureContent {
	private String user_id;// id
	private String nature_value;// ��˾����
	private String industry_value;// ������ҵ
	private String name_value;// ��˾����
	private String money_value;// �漰���
	private String url_value;// ��˾��ַ
	private String content_value;// �ع�����

	public ExosureContent() {
		// TODO Auto-generated constructor stub
	}

	public ExosureContent(String user_id, String nature_value,
			String industry_value, String name_value, String money_value,
			String url_value, String content_value) {
		super();
		this.user_id = user_id;
		this.nature_value = nature_value;
		this.industry_value = industry_value;
		this.name_value = name_value;
		this.money_value = money_value;
		this.url_value = url_value;
		this.content_value = content_value;
	}

	@Override
	public String toString() {
		return "ExosureContent [user_id=" + user_id + ", nature_value="
				+ nature_value + ", industry_value=" + industry_value
				+ ", name_value=" + name_value + ", money_value=" + money_value
				+ ", url_value=" + url_value + ", content_value="
				+ content_value + "]";
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getNature_value() {
		return nature_value;
	}

	public void setNature_value(String nature_value) {
		this.nature_value = nature_value;
	}

	public String getIndustry_value() {
		return industry_value;
	}

	public void setIndustry_value(String industry_value) {
		this.industry_value = industry_value;
	}

	public String getName_value() {
		return name_value;
	}

	public void setName_value(String name_value) {
		this.name_value = name_value;
	}

	public String getMoney_value() {
		return money_value;
	}

	public void setMoney_value(String money_value) {
		this.money_value = money_value;
	}

	public String getUrl_value() {
		return url_value;
	}

	public void setUrl_value(String url_value) {
		this.url_value = url_value;
	}

	public String getContent_value() {
		return content_value;
	}

	public void setContent_value(String content_value) {
		this.content_value = content_value;
	}

}
