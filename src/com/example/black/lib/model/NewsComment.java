package com.example.black.lib.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 媒体曝光的评论
 * @author admin
 *
 */
public class NewsComment  implements Parcelable{
	private long id;//新闻评论id
	private long user_id;//用户id
	private String nickname;//用户昵称
	private long company_id;//企业id
	private long news_id;//新闻id
	private String content;//评论内容
	private long is_validate;
	private long assist_num;
	private long add_time;//添加的时间
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
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public long getCompany_id() {
		return company_id;
	}
	public void setCompany_id(long company_id) {
		this.company_id = company_id;
	}
	public long getNews_id() {
		return news_id;
	}
	public void setNews_id(long news_id) {
		this.news_id = news_id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public long getIs_validate() {
		return is_validate;
	}
	public void setIs_validate(long is_validate) {
		this.is_validate = is_validate;
	}
	public long getAssist_num() {
		return assist_num;
	}
	public void setAssist_num(long assist_num) {
		this.assist_num = assist_num;
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
		dest.writeLong(this.user_id);
		dest.writeString(this.nickname);
		dest.writeLong(this.company_id);
		dest.writeLong(this.news_id);
		dest.writeString(this.content);
		dest.writeLong(this.is_validate);
		dest.writeLong(this.assist_num);
		dest.writeLong(this.add_time);
	}
	
	
	public static final Parcelable.Creator<NewsComment> CREATOR = new Creator<NewsComment>() {
		
		@Override
		public NewsComment[] newArray(int size) {
			return new NewsComment[size];
		}
		
		@Override
		public NewsComment createFromParcel(Parcel source) {
			NewsComment newsComment = new NewsComment();
			newsComment.setId(source.readLong());
			newsComment.setUser_id(source.readLong());
			newsComment.setNickname(source.readString());
			newsComment.setCompany_id(source.readLong());
			newsComment.setNews_id(source.readLong());
			newsComment.setContent(source.readString());
			newsComment.setIs_validate(source.readLong());
			newsComment.setAssist_num(source.readLong());
			newsComment.setAdd_time(source.readLong());
			return newsComment;
		}
	};
}
