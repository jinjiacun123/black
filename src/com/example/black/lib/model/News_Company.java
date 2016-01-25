package com.example.black.lib.model;

import android.os.Parcel;
import android.os.Parcelable;

public class News_Company  implements Parcelable {
	private long id;//
	private long company_id;// 企业id
	private String title;// 标题
	private String source;// 标题
	private String author;// 标题
	private String content;// 标题
	private long pic;// 标题
	private long pic_app;// 标题
	private int assist_num;// 标题
	private long add_time;// 标题
	private long show_time;
	private String pic_url;// 标题

	public long getShow_time() {
		return show_time;
	}

	public void setShow_time(long show_time) {
		this.show_time = show_time;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public long getPic() {
		return pic;
	}

	public void setPic(long pic) {
		this.pic = pic;
	}

	public long getPic_app() {
		return pic_app;
	}

	public void setPic_app(long pic_app) {
		this.pic_app = pic_app;
	}

	public int getAssist_num() {
		return assist_num;
	}

	public void setAssist_num(int assist_num) {
		this.assist_num = assist_num;
	}

	public long getAdd_time() {
		return add_time;
	}

	public void setAdd_time(long add_time) {
		this.add_time = add_time;
	}

	public String getPic_url() {
		return pic_url;
	}

	public void setPic_url(String pic_url) {
		this.pic_url = pic_url;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(this.id);
		dest.writeLong(this.company_id);
		dest.writeString(this.title);
		dest.writeString(this.source);
		dest.writeString(this.author);
		dest.writeString(this.content);
		dest.writeLong(this.pic);
		dest.writeLong(this.pic_app);
		dest.writeInt(this.assist_num);
		dest.writeLong(this.add_time);
		dest.writeString(this.pic_url);
	}

	public static final Parcelable.Creator<News_Company> CREATOR = new Creator<News_Company>() {

		@Override
		public News_Company[] newArray(int size) {
			return new News_Company[size];
		}

		@Override
		public News_Company createFromParcel(Parcel source) {
			News_Company news_Company = new News_Company();
			news_Company.setId(source.readLong());
			news_Company.setCompany_id(source.readLong());
			news_Company.setTitle(source.readString());
			news_Company.setSource(source.readString());
			news_Company.setAuthor(source.readString());
			news_Company.setContent(source.readString());
			news_Company.setPic(source.readLong());
			news_Company.setPic_app(source.readLong());
			news_Company.setAssist_num(source.readInt());
			news_Company.setAdd_time(source.readLong());
			news_Company.setPic_url(source.readString());
			return news_Company;
		}
	};
}
