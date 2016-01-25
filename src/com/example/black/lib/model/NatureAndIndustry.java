package com.example.black.lib.model;

/**
 * 公司性质和行业
 * 
 * @author Admin
 * 
 */
public class NatureAndIndustry {
	public String number;// 编号
	public String name;// 类型值

	public NatureAndIndustry() {
		// TODO Auto-generated constructor stub
	}

	public NatureAndIndustry(String number, String name) {
		super();
		this.number = number;
		this.name = name;
	}

	@Override
	public String toString() {
		return "NatureAndIndustry [number=" + number + ", name=" + name + "]";
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
