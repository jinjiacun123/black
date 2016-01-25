package com.example.black.lib.model;

/**
 * 用户信息
 * 
 * @author Admin
 * 
 */
public class UserInfo {
	private int is_sucess;
	private String user_id;// id
	private String nickname;// 昵称
	private String sex;// 性别
	private String cur_date;// 当前时间
	private String head_portrait;// 用户图片

	public UserInfo() {
		// TODO Auto-generated constructor stub
	}

	public UserInfo(int is_sucess, String user_id, String nickname, String sex,
			String cur_date, String head_portrait) {
		super();
		this.is_sucess = is_sucess;
		this.user_id = user_id;
		this.nickname = nickname;
		this.sex = sex;
		this.cur_date = cur_date;
		this.head_portrait = head_portrait;
	}

	public int getIs_sucess() {
		return is_sucess;
	}

	public void setIs_sucess(int is_sucess) {
		this.is_sucess = is_sucess;
	}

	@Override
	public String toString() {
		return "UserInfo [is_sucess=" + is_sucess + ", user_id=" + user_id
				+ ", nickname=" + nickname + ", sex=" + sex + ", cur_date="
				+ cur_date + ", head_portrait=" + head_portrait + "]";
	}

	public String getHead_portrait() {
		return head_portrait;
	}

	public void setHead_portrait(String head_portrait) {
		this.head_portrait = head_portrait;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getCur_date() {
		return cur_date;
	}

	public void setCur_date(String cur_date) {
		this.cur_date = cur_date;
	}
}
