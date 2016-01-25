package com.example.black.lib.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 搜索返回的结果值
 * 
 * @author stone
 * 
 */
public class Company  implements Parcelable {

	private long company_id;// id
	private long logo;// 企业logoid
	private String logo_url;// 企业logoid
	private long busin_license;// 营业执照(图片id)
	private String busin_license_url;// 营业执照(图片url)
	private String code_certificate_url;// 营业执照(图片url)
	private String certificate_url;// 营业执照(图片url)
	private long code_certificate;// 机构代码证((图片id)
	private long certificate;// 资质证明(图片id)
	private long add_time;// 添加日期
	private String nature;// 企业性质
	private String trade;// 所属行业
	private String company_name;// 公司全称
	private String auth_level;// 认证级别
	private String company_type;// 企业类型
	private String reg_address;// 注册地址
	private String telephone;// 联系电话
	private String website;// 官方网址
	private String record;// 官网备案
	private String find_website;// 查询网址
	private String agent_platform;// 代理平台
	private String mem_sn;// 会员编号
	private String alias_list;// 公司别名
	private long add_blk_amount;// 加黑数
	private long exp_amount;// 曝光数
	private long com_amount;// 评论数
	private long regulators_id;// 监管机构id
	private String regulators_id_n;// 监管机构名称
	private String agent_platform_n;// 代理平台名称
	private String sub_com;// 附加评论
	private long record_count;
	
	@Override
	public String toString() {
		return "Company [company_id=" + company_id + ", logo=" + logo
				+ ", logo_url=" + logo_url + ", busin_license=" + busin_license
				+ ", busin_license_url=" + busin_license_url
				+ ", code_certificate_url=" + code_certificate_url
				+ ", certificate_url=" + certificate_url
				+ ", code_certificate=" + code_certificate + ", certificate="
				+ certificate + ", add_time=" + add_time + ", nature=" + nature
				+ ", trade=" + trade + ", company_name=" + company_name
				+ ", auth_level=" + auth_level + ", company_type="
				+ company_type + ", reg_address=" + reg_address
				+ ", telephone=" + telephone + ", website=" + website
				+ ", record=" + record + ", find_website=" + find_website
				+ ", agent_platform=" + agent_platform + ", mem_sn=" + mem_sn
				+ ", alias_list=" + alias_list + ", add_blk_amount="
				+ add_blk_amount + ", exp_amount=" + exp_amount
				+ ", com_amount=" + com_amount + ", regulators_id="
				+ regulators_id + ", regulators_id_n=" + regulators_id_n
				+ ", agent_platform_n=" + agent_platform_n + ", sub_com="
				+ sub_com + ", record_count=" + record_count + "]";
	}

	public long getRecord_count() {
		return record_count;
	}

	public void setRecord_count(long record_count) {
		this.record_count = record_count;
	}

	public String getSub_com() {
		return sub_com;
	}

	public void setSub_com(String sub_com) {
		this.sub_com = sub_com;
	}

	public String getAgent_platform_n() {
		return agent_platform_n;
	}

	public void setAgent_platform_n(String agent_platform_n) {
		this.agent_platform_n = agent_platform_n;
	}

	public long getRegulators_id() {
		return regulators_id;
	}

	public void setRegulators_id(long regulators_id) {
		this.regulators_id = regulators_id;
	}

	public String getRegulators_id_n() {
		return regulators_id_n;
	}

	public void setRegulators_id_n(String regulators_id_n) {
		this.regulators_id_n = regulators_id_n;
	}

	public long getAdd_blk_amount() {
		return add_blk_amount;
	}

	public void setAdd_blk_amount(long add_blk_amount) {
		this.add_blk_amount = add_blk_amount;
	}

	public long getExp_amount() {
		return exp_amount;
	}

	public void setExp_amount(long exp_amount) {
		this.exp_amount = exp_amount;
	}

	public long getCom_amount() {
		return com_amount;
	}

	public void setCom_amount(long com_amount) {
		this.com_amount = com_amount;
	}

	public String getAlias_list() {
		return alias_list;
	}

	public void setAlias_list(String alias_list) {
		this.alias_list = alias_list;
	}

	public long getLogo() {
		return logo;
	}

	public void setLogo(long logo) {
		this.logo = logo;
	}

	public String getLogo_url() {
		return logo_url;
	}

	public void setLogo_url(String logo_url) {
		this.logo_url = logo_url;
	}

	public String getBusin_license_url() {
		return busin_license_url;
	}

	public void setBusin_license_url(String busin_license_url) {
		this.busin_license_url = busin_license_url;
	}

	public String getCode_certificate_url() {
		return code_certificate_url;
	}

	public void setCode_certificate_url(String code_certificate_url) {
		this.code_certificate_url = code_certificate_url;
	}

	public String getCertificate_url() {
		return certificate_url;
	}

	public void setCertificate_url(String certificate_url) {
		this.certificate_url = certificate_url;
	}

	public static Parcelable.Creator<Company> getCreator() {
		return CREATOR;
	}

	public void setCertificate(long certificate) {
		this.certificate = certificate;
	}

	public long getCompany_id() {
		return company_id;
	}

	public void setCompany_id(long company_id) {
		this.company_id = company_id;
	}

	public long getBusin_license() {
		return busin_license;
	}

	public void setBusin_license(long busin_license) {
		this.busin_license = busin_license;
	}

	public long getCode_certificate() {
		return code_certificate;
	}

	public void setCode_certificate(long code_certificate) {
		this.code_certificate = code_certificate;
	}

	public long getCertificate() {
		return certificate;
	}

	public void setCertificatE(long certificatE) {
		this.certificate = certificatE;
	}

	public long getAdd_time() {
		return add_time;
	}

	public void setAdd_time(long add_time) {
		this.add_time = add_time;
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

	public String getAuth_level() {
		return auth_level;
	}

	public void setAuth_level(String auth_level) {
		this.auth_level = auth_level;
	}

	public String getCompany_type() {
		return company_type;
	}

	public void setCompany_type(String company_type) {
		this.company_type = company_type;
	}

	public String getReg_address() {
		return reg_address;
	}

	public void setReg_address(String reg_address) {
		this.reg_address = reg_address;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getRecord() {
		return record;
	}

	public void setRecord(String record) {
		this.record = record;
	}

	public String getFind_website() {
		return find_website;
	}

	public void setFind_website(String find_website) {
		this.find_website = find_website;
	}

	public String getAgent_platform() {
		return agent_platform;
	}

	public void setAgent_platform(String agent_platform) {
		this.agent_platform = agent_platform;
	}

	public String getMem_sn() {
		return mem_sn;
	}

	public void setMem_sn(String mem_sn) {
		this.mem_sn = mem_sn;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(this.company_id);
		dest.writeLong(this.busin_license);
		dest.writeLong(this.code_certificate);
		dest.writeLong(this.certificate);
		dest.writeLong(this.add_time);
		dest.writeLong(this.logo);
		dest.writeString(this.code_certificate_url);
		dest.writeString(this.certificate_url);
		dest.writeString(this.busin_license_url);
		dest.writeString(this.logo_url);
		dest.writeString(this.nature);
		dest.writeString(this.trade);
		dest.writeString(this.company_name);
		dest.writeString(this.auth_level);
		dest.writeString(this.company_type);
		dest.writeString(this.reg_address);
		dest.writeString(this.telephone);
		dest.writeString(this.website);
		dest.writeString(this.record);
		dest.writeString(this.find_website);
		dest.writeString(this.agent_platform);
		dest.writeString(this.mem_sn);
		dest.writeString(this.alias_list);
		dest.writeLong(this.add_blk_amount);
		dest.writeLong(this.exp_amount);
		dest.writeLong(this.com_amount);
		dest.writeLong(this.regulators_id);
		dest.writeString(this.regulators_id_n);
		dest.writeString(this.agent_platform_n);
		dest.writeString(this.sub_com);
		dest.writeLong(this.record_count);
	}

	public static final Parcelable.Creator<Company> CREATOR = new Creator<Company>() {

		@Override
		public Company[] newArray(int size) {
			return new Company[size];
		}

		@Override
		public Company createFromParcel(Parcel source) {
			Company company = new Company();
			company.setCompany_id(source.readLong());
			company.setBusin_license(source.readLong());
			company.setCode_certificate(source.readLong());
			company.setCertificatE(source.readLong());
			company.setAdd_time(source.readLong());
			company.setLogo(source.readLong());
			company.setCode_certificate_url(source.readString());
			company.setCertificate_url(source.readString());
			company.setBusin_license_url(source.readString());
			company.setLogo_url(source.readString());
			company.setNature(source.readString());
			company.setTrade(source.readString());
			company.setCompany_name(source.readString());
			company.setAuth_level(source.readString());
			company.setCompany_type(source.readString());
			company.setReg_address(source.readString());
			company.setTelephone(source.readString());
			company.setWebsite(source.readString());
			company.setRecord(source.readString());
			company.setFind_website(source.readString());
			company.setAgent_platform(source.readString());
			company.setMem_sn(source.readString());
			company.setAlias_list(source.readString());
			company.setAdd_blk_amount(source.readLong());
			company.setExp_amount(source.readLong());
			company.setCom_amount(source.readLong());
			company.setRegulators_id(source.readLong());
			company.setRegulators_id_n(source.readString());
			company.setAgent_platform_n(source.readString());
			company.setSub_com(source.readString());
			company.setRecord_count(source.readLong());
			return company;
		}
	};
}
