package com.example.black.lib.model;

/**
 * 评论回复的实体
 * @author admin
 *
 */
public class Exosure_Reply {
	private String  nickname ;//用户昵称
	private String content;//评论内容
	private long add_time;//添加日期
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public long getAdd_time() {
		return add_time;
	}
	public void setAdd_time(long add_time) {
		this.add_time = add_time;
	}
		
	
}
