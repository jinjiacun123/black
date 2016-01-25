package com.example.black.lib.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 企业曝光的实体
 * 
 * @author admin
 * 
 */
public class Exosure_Company  implements Parcelable {
	private long id;// id
	private long company_id;// 关联企业id
	private long user_id;// 用户id
	private long parent_id;// 盖楼评论(默认0,盖楼为基层的id)
	private long last_user_id;// last_user_id
	private long v_last_user_id;// v_last_user_id
	private String type;// 评论类型(点赞、提问、加黑)*
	private String nickname;// 用户昵称
	private String nature;// id企业性质
	private String trade;// id所属行业
	private String company_name;// id公司名称
	private String amount;// 涉及金额
	private String website;// id公司网址
	private String content;// id曝光内容
	private long pic_1;// 图片
	private long pic_2;
	private long pic_3;
	private long pic_4;
	private long pic_5;
	private String pic_1_url;// 图片url
	private String pic_2_url;
	private String pic_3_url;
	private String pic_4_url;
	private String pic_5_url;
	private long top_num;// 顶的数目
	private long add_time;// 添加日期
	private long is_validate;
	private long validate_time;
	private long is_anonymous;// 0为匿名 1不匿名
	private long is_delete;
	private long has_child_ex;
	private long has_child;
	private long record_count;// 回复数
	private long last_time;// 最后回复人时间
	private String last_nickname;// 最后回复人
	private long v_last_time;//
	private String v_last_nickname;//
	private long v_last_is_anonymous;

	@Override
	public String toString() {
		return "Exosure_Company [id=" + id + ", company_id=" + company_id
				+ ", user_id=" + user_id + ", parent_id=" + parent_id
				+ ", last_user_id = " + last_user_id + ",v_last_user_id = " + v_last_user_id 
				+ " , type=" + type + ", nickname=" + nickname + ", nature="
				+ nature + ", trade=" + trade + ", company_name="
				+ company_name + ", amount=" + amount + ", website=" + website
				+ ", content=" + content + ", pic_1=" + pic_1 + ", pic_2="
				+ pic_2 + ", pic_3=" + pic_3 + ", pic_4=" + pic_4 + ", pic_5="
				+ pic_5 + ", pic_1_url=" + pic_1_url + ", pic_2_url="
				+ pic_2_url + ", pic_3_url=" + pic_3_url + ", pic_4_url="
				+ pic_4_url + ", pic_5_url=" + pic_5_url + ", top_num="
				+ top_num + ", add_time=" + add_time + ", is_validate="
				+ is_validate + ", validate_time=" + validate_time
				+ ", is_anonymous=" + is_anonymous + ", is_delete=" + is_delete
				+ ", has_child_ex=" + has_child_ex + ", has_child=" + has_child
				+ ", record_count=" + record_count + ", last_time=" + last_time
				+ ", last_nickname=" + last_nickname + ", v_last_time="
				+ v_last_time + ", v_last_nickname=" + v_last_nickname
				+ ", v_last_is_anonymous=" + v_last_is_anonymous + "]";
	}
	
	public long getLast_user_id() {
		return last_user_id;
	}

	public void setLast_user_id(long last_user_id) {
		this.last_user_id = last_user_id;
	}

	public long getV_last_user_id() {
		return v_last_user_id;
	}

	public void setV_last_user_id(long v_last_user_id) {
		this.v_last_user_id = v_last_user_id;
	}

	public long getV_last_time() {
		return v_last_time;
	}

	public void setV_last_time(long v_last_time) {
		this.v_last_time = v_last_time;
	}

	public String getV_last_nickname() {
		return v_last_nickname;
	}

	public void setV_last_nickname(String v_last_nickname) {
		this.v_last_nickname = v_last_nickname;
	}

	public long getV_last_is_anonymous() {
		return v_last_is_anonymous;
	}

	public void setV_last_is_anonymous(long v_last_is_anonymous) {
		this.v_last_is_anonymous = v_last_is_anonymous;
	}

	public long getLast_time() {
		return last_time;
	}

	public void setLast_time(long last_time) {
		this.last_time = last_time;
	}

	public String getLast_nickname() {
		return last_nickname;
	}

	public void setLast_nickname(String last_nickname) {
		this.last_nickname = last_nickname;
	}

	public long getIs_anonymous() {
		return is_anonymous;
	}

	public void setIs_anonymous(long is_anonymous) {
		this.is_anonymous = is_anonymous;
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

	public long getIs_validate() {
		return is_validate;
	}

	public void setIs_validate(long is_validate) {
		this.is_validate = is_validate;
	}

	public long getValidate_time() {
		return validate_time;
	}

	public void setValidate_time(long validate_time) {
		this.validate_time = validate_time;
	}

	public long getIs_delete() {
		return is_delete;
	}

	public void setIs_delete(long is_delete) {
		this.is_delete = is_delete;
	}

	public long getHas_child_ex() {
		return has_child_ex;
	}

	public void setHas_child_ex(long has_child_ex) {
		this.has_child_ex = has_child_ex;
	}

	public long getHas_child() {
		return has_child;
	}

	public void setHas_child(long has_child) {
		this.has_child = has_child;
	}

	public long getRecord_count() {
		return record_count;
	}

	public void setRecord_count(long record_count) {
		this.record_count = record_count;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getCompany_id() {
		return company_id;
	}

	public void setCompany_id(long company_id) {
		this.company_id = company_id;
	}

	public long getUser_id() {
		return user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
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

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
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

	public String getPic_1_url() {
		return pic_1_url;
	}

	public void setPic_1_url(String pic_1_url) {
		this.pic_1_url = pic_1_url;
	}

	public String getPic_2_url() {
		return pic_2_url;
	}

	public void setPic_2_url(String pic_2_url) {
		this.pic_2_url = pic_2_url;
	}

	public String getPic_3_url() {
		return pic_3_url;
	}

	public void setPic_3_url(String pic_3_url) {
		this.pic_3_url = pic_3_url;
	}

	public String getPic_4_url() {
		return pic_4_url;
	}

	public void setPic_4_url(String pic_4_url) {
		this.pic_4_url = pic_4_url;
	}

	public String getPic_5_url() {
		return pic_5_url;
	}

	public void setPic_5_url(String pic_5_url) {
		this.pic_5_url = pic_5_url;
	}

	public long getTop_num() {
		return top_num;
	}

	public void setTop_num(long top_num) {
		this.top_num = top_num;
	}

	public long getAdd_time() {
		return add_time;
	}

	public void setAdd_time(long add_time) {
		this.add_time = add_time;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(this.id);
		dest.writeLong(this.company_id);
		dest.writeLong(this.user_id);
		dest.writeLong(this.last_user_id);
		dest.writeLong(this.v_last_user_id);
		dest.writeString(this.nickname);
		dest.writeString(this.nature);
		dest.writeString(this.trade);
		dest.writeString(this.company_name);
		dest.writeString(this.amount);
		dest.writeString(this.website);
		dest.writeString(this.content);
		dest.writeLong(this.pic_1);
		dest.writeLong(this.pic_2);
		dest.writeLong(this.pic_3);
		dest.writeLong(this.pic_4);
		dest.writeLong(this.pic_5);
		dest.writeString(this.pic_1_url);
		dest.writeString(this.pic_2_url);
		dest.writeString(this.pic_3_url);
		dest.writeString(this.pic_4_url);
		dest.writeString(this.pic_5_url);
		dest.writeLong(this.top_num);
		dest.writeLong(this.add_time);
		dest.writeLong(this.is_validate);
		dest.writeLong(this.validate_time);
		dest.writeLong(this.is_delete);
		dest.writeLong(this.has_child_ex);
		dest.writeLong(this.has_child);
		dest.writeLong(this.record_count);
		dest.writeLong(this.parent_id);
		dest.writeString(this.type);
		dest.writeLong(this.last_time);
		dest.writeString(this.last_nickname);
		dest.writeLong(this.v_last_time);
		dest.writeString(this.v_last_nickname);
		dest.writeLong(this.v_last_is_anonymous);
	}

	public static final Parcelable.Creator<Exosure_Company> CREATOR = new Creator<Exosure_Company>() {

		@Override
		public Exosure_Company[] newArray(int size) {
			return new Exosure_Company[size];
		}

		@Override
		public Exosure_Company createFromParcel(Parcel source) {
			Exosure_Company exosure_Company = new Exosure_Company();
			exosure_Company.setId(source.readLong());
			exosure_Company.setLast_user_id(source.readLong());
			exosure_Company.setV_last_user_id(source.readLong());
			exosure_Company.setCompany_id(source.readLong());
			exosure_Company.setUser_id(source.readLong());
			exosure_Company.setNickname(source.readString());
			exosure_Company.setNature(source.readString());
			exosure_Company.setTrade(source.readString());
			exosure_Company.setCompany_name(source.readString());
			exosure_Company.setAmount(source.readString());
			exosure_Company.setWebsite(source.readString());
			exosure_Company.setContent(source.readString());
			exosure_Company.setPic_1(source.readLong());
			exosure_Company.setPic_2(source.readLong());
			exosure_Company.setPic_3(source.readLong());
			exosure_Company.setPic_4(source.readLong());
			exosure_Company.setPic_5(source.readLong());
			exosure_Company.setPic_1_url(source.readString());
			exosure_Company.setPic_2_url(source.readString());
			exosure_Company.setPic_3_url(source.readString());
			exosure_Company.setPic_4_url(source.readString());
			exosure_Company.setPic_5_url(source.readString());
			exosure_Company.setTop_num(source.readLong());
			exosure_Company.setAdd_time(source.readLong());
			exosure_Company.setIs_validate(source.readLong());
			exosure_Company.setValidate_time(source.readLong());
			exosure_Company.setIs_delete(source.readLong());
			exosure_Company.setHas_child_ex(source.readLong());
			exosure_Company.setHas_child(source.readLong());
			exosure_Company.setRecord_count(source.readLong());
			exosure_Company.setParent_id(source.readLong());
			exosure_Company.setType(source.readString());
			exosure_Company.setLast_time(source.readLong());
			exosure_Company.setLast_nickname(source.readString());
			exosure_Company.setV_last_time(source.readLong());
			exosure_Company.setV_last_nickname(source.readString());
			exosure_Company.setV_last_is_anonymous(source.readLong());
			return exosure_Company;
		}
	};
}
