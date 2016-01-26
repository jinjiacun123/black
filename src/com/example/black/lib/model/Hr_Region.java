package com.example.black.lib.model;

/**
 * 城市
 * 
 * @author Admin
 * 
 */
public class Hr_Region {
	private String region_id;// 地区id
	private String parent_id;// 父亲id
	private String region_name;// 地区名称
	private String region_type;// 地区类型
	private String agency_id;// 代理id

	public Hr_Region() {
		// TODO Auto-generated constructor stub
	}

	public Hr_Region(String region_id, String parent_id, String region_name,
			String region_type, String agency_id) {
		super();
		this.region_id = region_id;
		this.parent_id = parent_id;
		this.region_name = region_name;
		this.region_type = region_type;
		this.agency_id = agency_id;
	}

	@Override
	public String toString() {
		return "Hr_Region [region_id=" + region_id + ", parent_id=" + parent_id
				+ ", region_name=" + region_name + ", region_type="
				+ region_type + ", agency_id=" + agency_id + "]";
	}

	public String getRegion_name() {
		return region_name;
	}

	public void setRegion_name(String region_name) {
		this.region_name = region_name;
	}

	public String getRegion_id() {
		return region_id;
	}

	public void setRegion_id(String region_id) {
		this.region_id = region_id;
	}

	public String getParent_id() {
		return parent_id;
	}

	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}

	public String getRegion_type() {
		return region_type;
	}

	public void setRegion_type(String region_type) {
		this.region_type = region_type;
	}

	public String getAgency_id() {
		return agency_id;
	}

	public void setAgency_id(String agency_id) {
		this.agency_id = agency_id;
	}

}
