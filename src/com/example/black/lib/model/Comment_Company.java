package com.example.black.lib.model;

/**
 * ��ҵ�������ʵ��
 * 
 * @author admin
 * 
 */
public class Comment_Company {
	private long user_id;// �û�id
	private long company_id;// ��ҵid
	private long parent_id;// ��¥����(Ĭ��0,��¥Ϊ�����id)
	private String type;// ��������(���ޡ����ʡ��Ӻ�)*
	private String content;// ��������*(���ޡ����ʡ��Ӻ�)*
	private long pic_1;// ͼƬ
	private long pic_2;
	private long pic_3;
	private long pic_4;
	private long pic_5;
	private long is_anonymous;// 0Ϊ���� 1������
	private long is_depth;//�Ƿ񳬹�������(0-û[Ĭ��],1-����)

	@Override
	public String toString() {
		return "Comment_Company [user_id=" + user_id + ", company_id="
				+ company_id + ", parent_id=" + parent_id + ", type=" + type
				+ ", content=" + content + ", pic_1=" + pic_1 + ", pic_2="
				+ pic_2 + ", pic_3=" + pic_3 + ", pic_4=" + pic_4 + ", pic_5="
				+ pic_5 + ", is_anonymous=" + is_anonymous + ",is_depth = " + is_depth + "]";
	}
	
	public long getIs_depth() {
		return is_depth;
	}

	public void setIs_depth(long is_depth) {
		this.is_depth = is_depth;
	}

	public long getUser_id() {
		return user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	public long getCompany_id() {
		return company_id;
	}

	public void setCompany_id(long company_id) {
		this.company_id = company_id;
	}

	public long getParent_id() {
		return parent_id;
	}

	public void setParent_id(long parent_id) {
		this.parent_id = parent_id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public long getPic_1() {
		return pic_1;
	}

	public void setPic_1(long pic_1) {
		this.pic_1 = pic_1;
	}

	public long getPic_2() {
		return pic_2;
	}

	public void setPic_2(long pic_2) {
		this.pic_2 = pic_2;
	}

	public long getPic_3() {
		return pic_3;
	}

	public void setPic_3(long pic_3) {
		this.pic_3 = pic_3;
	}

	public long getPic_4() {
		return pic_4;
	}

	public void setPic_4(long pic_4) {
		this.pic_4 = pic_4;
	}

	public long getPic_5() {
		return pic_5;
	}

	public void setPic_5(long pic_5) {
		this.pic_5 = pic_5;
	}

	public long getIs_anonymous() {
		return is_anonymous;
	}

	public void setIs_anonymous(long is_anonymous) {
		this.is_anonymous = is_anonymous;
	}
}
