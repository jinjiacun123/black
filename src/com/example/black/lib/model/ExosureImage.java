package com.example.black.lib.model;

/**
 * ÆØ¹âÍ¼Æ¬
 * 
 * @author Admin
 * 
 */
public class ExosureImage {
	private int is_sucess;// ×´Ì¬Âë
	private String img;// Í¼Æ¬Â·¾¶
	private String id;// ÉÏ´«Í¼Æ¬id

	public ExosureImage() {
		// TODO Auto-generated constructor stub
	}

	public ExosureImage(int is_sucess, String img, String id) {
		super();
		this.is_sucess = is_sucess;
		this.img = img;
		this.id = id;
	}

	@Override
	public String toString() {
		return "ExosureImage [is_sucess=" + is_sucess + ", img=" + img
				+ ", id=" + id + "]";
	}

	public int getIs_sucess() {
		return is_sucess;
	}

	public void setIs_sucess(int is_sucess) {
		this.is_sucess = is_sucess;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
