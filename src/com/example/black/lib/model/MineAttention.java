package com.example.black.lib.model;


//ÎÒµÄ¹Ø×¢
public class MineAttention  implements Comparable{
	private long id;
	private long user_id;
	private String user_nickname;
	private long company_id;
	private String company_name;
	private long add_time;

	public MineAttention() {
		// TODO Auto-generated constructor stub
	}

	public MineAttention(long id, long user_id, String user_nickname,
			long company_id, String company_name, long add_time) {
		super();
		this.id = id;
		this.user_id = user_id;
		this.user_nickname = user_nickname;
		this.company_id = company_id;
		this.company_name = company_name;
		this.add_time = add_time;
	}

	@Override
	public String toString() {
		return "MineAttention [id=" + id + ", user_id=" + user_id
				+ ", user_nickname=" + user_nickname + ", company_id="
				+ company_id + ", company_name=" + company_name + ", add_time="
				+ add_time + "]";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUser_id() {
		return user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	public String getUser_nickname() {
		return user_nickname;
	}

	public void setUser_nickname(String user_nickname) {
		this.user_nickname = user_nickname;
	}

	public long getCompany_id() {
		return company_id;
	}

	public void setCompany_id(long company_id) {
		this.company_id = company_id;
	}

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	public long getAdd_time() {
		return add_time;
	}

	public void setAdd_time(long add_time) {
		this.add_time = add_time;
	}

	@Override
	public int compareTo(Object another) {
		MineAttention attr = (MineAttention) another;
		return (Float.valueOf(this.id) < Float.valueOf(attr
				.getId()) ? -1
				: (Float.valueOf(this.id) == Float.valueOf(attr
						.getId()) ? 0 : 1));
	}

}
