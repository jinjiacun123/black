package com.example.black.lib.model;

import java.util.ArrayList;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * 获取企业评论的实体
 * 
 * @author admin
 * 
 */
public class Comment_Company2  implements Parcelable {
	//曝光部分实体类-----start
		private String title;//标题
		private String nature;//企业性质 
		private String trade;//所属行业 
		private long amount;//涉及金额
		private String website;//公司网址
		
		private long childs;
		private long last_child_time;
		private long last_user_id;
		//曝光部分实体类-------end

		private long id;// 评论Id
		private long user_id;// 用户id
		private long company_id;// 企业id
		private String company_name;// 公司名称
		private long parent_id;// 盖楼评论(默认0,盖楼为基层的id)
		private String nickname;//
		private String type;// 评论类型(点赞、提问、加黑)*
		private String parent_content;// 父评论
		private String content;// 评论内容*(点赞、提问、加黑)*
		private String pic_1_url;// 图片
		private String pic_2_url;// 图片
		private String pic_3_url;// 图片
		private String pic_4_url;// 图片
		private String pic_5_url;// 图片
		private long pic_1;// 图片
		private long pic_2;
		private long pic_3;
		private long pic_4;
		private long pic_5;
		private long is_validate;
		private long validate_time;
		private long is_anonymous;// 0为匿名 1不匿名
		private long top_num;// 顶数
		private long add_time;// 添加日期
		private long is_delete;
		private long has_child_ex;
		private long has_child;
		private long record_count;// 回复数
		private long last_time;// 最后回复人时间
		private String last_nickname;// 最后回复人
		private long v_last_time;//
		private String v_last_nickname;//
		private long v_last_is_anonymous;
		private List<Comment_Company2> list = new ArrayList<Comment_Company2>();// 附带评论集合

		@Override
		public String toString() {
			
			return "Comment_Company2 [id=" + id + ", user_id=" + user_id
					+ ", company_id=" + company_id + ", company_name="
					+ company_name + ", parent_id=" + parent_id + ", nickname="
					+ nickname + ", type=" + type + ", parent_content="
					+ parent_content + ", content=" + content + ", pic_1_url="
					+ pic_1_url + ", pic_2_url=" + pic_2_url + ", pic_3_url="
					+ pic_3_url + ", pic_4_url=" + pic_4_url + ", pic_5_url="
					+ pic_5_url + ", pic_1=" + pic_1 + ", pic_2=" + pic_2
					+ ", pic_3=" + pic_3 + ", pic_4=" + pic_4 + ", pic_5=" + pic_5
					+ ", is_validate=" + is_validate + ", validate_time="
					+ validate_time + ", is_anonymous=" + is_anonymous
					+ ", top_num=" + top_num + ", add_time=" + add_time
					+ ", is_delete=" + is_delete + ", has_child_ex=" + has_child_ex
					+ ", has_child=" + has_child + ", record_count=" + record_count
					+ ", last_time=" + last_time + ", last_nickname="
					+ last_nickname + ", v_last_time=" + v_last_time
					+ ", v_last_nickname=" + v_last_nickname
					+ ", v_last_is_anonymous=" + v_last_is_anonymous + ", list="
					+ list + "]";
		}
		
		public long getLast_user_id() {
			return last_user_id;
		}

		public void setLast_user_id(long last_user_id) {
			this.last_user_id = last_user_id;
		}

		public long getChilds() {
			return childs;
		}

		public void setChilds(long childs) {
			this.childs = childs;
		}

		public long getLast_child_time() {
			return last_child_time;
		}

		public void setLast_child_time(long last_child_time) {
			this.last_child_time = last_child_time;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
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

		public long getAmount() {
			return amount;
		}

		public void setAmount(long amount) {
			this.amount = amount;
		}

		public String getWebsite() {
			return website;
		}

		public void setWebsite(String website) {
			this.website = website;
		}

		public long getV_last_is_anonymous() {
			return v_last_is_anonymous;
		}

		public void setV_last_is_anonymous(long v_last_is_anonymous) {
			this.v_last_is_anonymous = v_last_is_anonymous;
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

		public List<Comment_Company2> getList() {
			return list;
		}

		public void setList(List<Comment_Company2> list) {
			this.list = list;
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

		public String getParent_content() {
			return parent_content;
		}

		public void setParent_content(String parent_content) {
			this.parent_content = parent_content;
		}

		public long getRecord_count() {
			return record_count;
		}

		public void setRecord_count(long record_count) {
			this.record_count = record_count;
		}

		public String getCompany_name() {
			return company_name;
		}

		public void setCompany_name(String company_name) {
			this.company_name = company_name;
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

		public String getNickname() {
			return nickname;
		}

		public void setNickname(String nickname) {
			this.nickname = nickname;
		}

		public void setPic_1(long pic_1) {
			this.pic_1 = pic_1;
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

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
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

		@Override
		public int describeContents() {
			return 0;
		}

		@Override
		public void writeToParcel(Parcel dest, int flags) {
			dest.writeLong(this.add_time);
			dest.writeLong(this.company_id);
			dest.writeLong(this.id);
			dest.writeLong(this.is_anonymous);
			dest.writeLong(this.parent_id);
			dest.writeLong(this.pic_1);
			dest.writeLong(this.pic_2);
			dest.writeLong(this.pic_3);
			dest.writeLong(this.pic_4);
			dest.writeLong(this.pic_5);
			dest.writeLong(this.top_num);
			dest.writeLong(this.user_id);
			dest.writeString(this.parent_content);
			dest.writeString(this.content);
			dest.writeString(this.nickname);
			dest.writeString(this.pic_1_url);
			dest.writeString(this.pic_2_url);
			dest.writeString(this.pic_3_url);
			dest.writeString(this.pic_4_url);
			dest.writeString(this.pic_5_url);
			dest.writeString(this.type);
			dest.writeString(this.company_name);
			dest.writeLong(this.is_validate);
			dest.writeLong(this.validate_time);
			dest.writeLong(this.is_delete);
			dest.writeLong(this.has_child_ex);
			dest.writeLong(this.has_child);
			dest.writeLong(this.record_count);
			dest.writeLong(this.last_time);
			dest.writeString(this.last_nickname);
			// dest.writeList(this.list);
			dest.writeLong(this.v_last_time);
			dest.writeString(this.v_last_nickname);
			dest.writeLong(this.v_last_is_anonymous);
		}

		public static final Parcelable.Creator<Comment_Company2> CREATOR = new Creator<Comment_Company2>() {

			@Override
			public Comment_Company2[] newArray(int size) {
				return new Comment_Company2[size];
			}
			
			@Override
			public Comment_Company2 createFromParcel(Parcel source) {
				Comment_Company2 comment_Company2 = new Comment_Company2();
				comment_Company2.setAdd_time(source.readLong());
				comment_Company2.setCompany_id(source.readLong());
				comment_Company2.setId(source.readLong());
				comment_Company2.setIs_anonymous(source.readLong());
				comment_Company2.setParent_id(source.readLong());
				comment_Company2.setPic_1(source.readLong());
				comment_Company2.setPic_2(source.readLong());
				comment_Company2.setPic_3(source.readLong());
				comment_Company2.setPic_4(source.readLong());
				comment_Company2.setPic_5(source.readLong());
				comment_Company2.setTop_num(source.readLong());
				comment_Company2.setUser_id(source.readLong());
				comment_Company2.setParent_content(source.readString());
				comment_Company2.setContent(source.readString());
				comment_Company2.setNickname(source.readString());
				comment_Company2.setPic_1_url(source.readString());
				comment_Company2.setPic_2_url(source.readString());
				comment_Company2.setPic_3_url(source.readString());
				comment_Company2.setPic_4_url(source.readString());
				comment_Company2.setPic_5_url(source.readString());
				comment_Company2.setType(source.readString());
				comment_Company2.setCompany_name(source.readString());
				comment_Company2.setIs_validate(source.readLong());
				comment_Company2.setValidate_time(source.readLong());
				comment_Company2.setIs_delete(source.readLong());
				comment_Company2.setHas_child_ex(source.readLong());
				comment_Company2.setHas_child(source.readLong());
				comment_Company2.setRecord_count(source.readLong());
				comment_Company2.setLast_time(source.readLong());
				comment_Company2.setLast_nickname(source.readString());
				comment_Company2.setV_last_time(source.readLong());
				comment_Company2.setV_last_nickname(source.readString());
				comment_Company2.setV_last_is_anonymous(source.readLong());
				// comment_Company2.setList(source.readArrayList(getClass()
				// .getClassLoader()));
				return comment_Company2;
			}
		};
}
