package com.example.black.lib.model;

/**
 * 曝光动态
 * 
 * @param $user_id
 *            会员id
 * @param $nickname
 *            昵称
 * @param $add_time
 *            曝光时间
 * @param $company_name
 *            企业名称
 * @param $content
 *            曝光内容
 * @param $pic_1
 *            图片id
 * @param $pic_1_url
 *            图片url
 * @param $re_amount
 *            曝光回复数(审核通过的数量)
 * 
 * @author admin
 * 
 */
public class Exposure_Dynamic {
	private String id;
	private String company_id;
	private String user_id;
	private String nickname;
	private String add_time;
	private String company_name;
	private String auth_level;
	private String content;
	private String pic_1;
	private String pic_1_url;
	private String re_amount;

	public Exposure_Dynamic() {
		// TODO Auto-generated constructor stub
	}

	public Exposure_Dynamic(String id, String company_id, String user_id,
			String nickname, String add_time, String company_name,
			String auth_level, String content, String pic_1, String pic_1_url,
			String re_amount) {
		super();
		this.id = id;
		this.company_id = company_id;
		this.user_id = user_id;
		this.nickname = nickname;
		this.add_time = add_time;
		this.company_name = company_name;
		this.auth_level = auth_level;
		this.content = content;
		this.pic_1 = pic_1;
		this.pic_1_url = pic_1_url;
		this.re_amount = re_amount;
	}

	@Override
	public String toString() {
		return "Exposure_Dynamic [id=" + id + ", company_id=" + company_id
				+ ", user_id=" + user_id + ", nickname=" + nickname
				+ ", add_time=" + add_time + ", company_name=" + company_name
				+ ", auth_level=" + auth_level + ", content=" + content
				+ ", pic_1=" + pic_1 + ", pic_1_url=" + pic_1_url
				+ ", re_amount=" + re_amount + "]";
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return this.id;
	}

	public void setCompany_id(String company_id) {
		this.company_id = company_id;
	}

	public String getCompany_id() {
		return this.company_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getUser_id() {
		return this.user_id;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getNickname() {
		return this.nickname;
	}

	public void setAdd_time(String add_time) {
		this.add_time = add_time;
	}

	public String getAdd_time() {
		return this.add_time;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	public String getCompany_name() {
		return this.company_name;
	}

	public void setAuth_level(String auth_level) {
		this.auth_level = auth_level;
	}

	public String getAuth_level() {
		return this.auth_level;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return this.content;
	}

	public void setPic_1(String pic_1) {
		this.pic_1 = pic_1;
	}

	public String getPic_1() {
		return this.pic_1;
	}

	public void setPic_1_url(String pic_1_url) {
		this.pic_1_url = pic_1_url;
	}

	public String getPic_1_url() {
		return this.pic_1_url;
	}

	public void setRe_amount(String re_amount) {
		this.re_amount = re_amount;
	}

	public String getRe_amount() {
		return this.re_amount;
	}
}
