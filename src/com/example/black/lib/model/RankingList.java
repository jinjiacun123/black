package com.example.black.lib.model;

/**
 * 排行榜实体类
 * 
 * @author admin
 * 
 */
public class RankingList {
	private String id;// 公司序号
	private String logo_url;// logo图片
	private String company_name;// 公司名
	private String alias_list;// 别名
	private long add_blk_amount;// 加黑次数
	private long exp_amount;// 曝光次数
	private long com_amount;// 评论次数

	public RankingList() {
		// TODO Auto-generated constructor stub
	}

	public RankingList(String id, String logo_url, String company_name,
			String alias_list, long add_blk_amount, long exp_amount,
			long com_amount) {
		super();
		this.id = id;
		this.logo_url = logo_url;
		this.company_name = company_name;
		this.alias_list = alias_list;
		this.add_blk_amount = add_blk_amount;
		this.exp_amount = exp_amount;
		this.com_amount = com_amount;
	}

	@Override
	public String toString() {
		return "RankingList [id=" + id + ", logo_url=" + logo_url
				+ ", company_name=" + company_name + ", alias_list="
				+ alias_list + ", add_blk_amount=" + add_blk_amount
				+ ", exp_amount=" + exp_amount + ", com_amount=" + com_amount
				+ "]";
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

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	public String getAlias_list() {
		return alias_list;
	}

	public void setAlias_list(String alias_list) {
		this.alias_list = alias_list;
	}

	public long getAdd_blk_amount() {
		return add_blk_amount;
	}

	public void setAdd_blk_amount(long add_blk_amount) {
		this.add_blk_amount = add_blk_amount;
	}

	public long getExp_amount() {
		return exp_amount;
	}

	public void setExp_amount(long exp_amount) {
		this.exp_amount = exp_amount;
	}

	public long getCom_amount() {
		return com_amount;
	}

	public void setCom_amount(long com_amount) {
		this.com_amount = com_amount;
	}

}
