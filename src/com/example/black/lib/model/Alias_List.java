package com.example.black.lib.model;

/**
 * ���������б���
 * 
 * @author admin
 * 
 */
public class Alias_List {
	private String id;           // ��˾���
	private String logo_url;     // logoͼƬ
	private String nature;       // ��ҵ����
	private String trade;        // ������ҵ
	private String company_name; // ��˾��
	private String lias_list;    // ����

	public Alias_List() {
		// TODO Auto-generated constructor stub
	}

	public Alias_List(String id, String logo_url, String nature, String trade,
			String company_name, String lias_list) {
		super();
		this.id = id;
		this.logo_url = logo_url;
		this.nature = nature;
		this.trade = trade;
		this.company_name = company_name;
		this.lias_list = lias_list;
	}

	@Override
	public String toString() {
		return "Alias_List [id=" + id + ", logo_url=" + logo_url + ", nature="
				+ nature + ", trade=" + trade + ", company_name="
				+ company_name + ", lias_list=" + lias_list + "]";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLogo_url() {
		return logo_url;
	}

	public void setLogo_url(String logo_url) {
		this.logo_url = logo_url;
	}

	public String getNature() {
		return nature;
	}

	public void setNature(String nature) {
		this.nature = nature;
	}

	public String getTrade() {
		return trade;
	}

	public void setTrade(String trade) {
		this.trade = trade;
	}

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	public String getLias_list() {
		return lias_list;
	}

	public void setLias_list(String lias_list) {
		this.lias_list = lias_list;
	}
}
