package com.example.black.lib.model;

/**
 * ·þÎñÆ÷°æ±¾
 * 
 * @author Admin
 * 
 */
public class VersionCode {
	private int versionCode;
	private String versionName;
	private String label;
	private String path;

	public VersionCode() {
		// TODO Auto-generated constructor stub
	}

	public VersionCode(int versionCode, String versionName, String label,
			String path) {
		super();
		this.versionCode = versionCode;
		this.versionName = versionName;
		this.label = label;
		this.path = path;
	}

	@Override
	public String toString() {
		return "VersionCode [versionCode=" + versionCode + ", versionName="
				+ versionName + ", label=" + label + ", path=" + path + "]";
	}

	public int getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(int versionCode) {
		this.versionCode = versionCode;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
