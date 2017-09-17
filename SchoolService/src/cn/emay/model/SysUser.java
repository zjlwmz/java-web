package cn.emay.model;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Name;


/**
 * 用户表
 * @author zjlWm
 * @version 2015-03-19
 */

public class SysUser {
	/**
	 * 
	 */
	@Name
	@Column("id")
	private String id;
	
	@Column("login_name")
	public String loginName;
	
	/**
	 * 姓名
	 */
	@Column("name")
	private String name;
	
	/**
	 * 手机号码
	 */
	@Column("mobile_phone")
	private String mobilePhone;
	
	/**
	 * 是否冻结（0：未冻结；1：已冻结）
	 */
	@Column("freeze_flag")
	private String freezeFlag;
	
	/**
	 * 顾问签名
	 */
	@Column("signature")
	private String signature;
	
	/**
	 * 性别
	 */
	@Column("gender")
	private String gender;
	
	@Column("password")
	private String password;
	
	/**
	 * 简介
	 */
	@Column("introduction")
	private String introduction;
	
	/**
	 * 顾问助理
	 */
	@Column("assistant_name")
	private String assistantName;
	
	/**
	 * 助理电话
	 */
	@Column("assistant_phone")
	private String assistantPhone;
	
	@Column("assistant_sex")
	private String assistantSex;
	
	@Column("del_flag")
	private String delFlag="0";
	
	
	@Column("zan_number")
	private Integer zanNumber;
	
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getLoginName() {
		return loginName;
	}


	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getDelFlag() {
		return delFlag;
	}


	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}


	public String getIntroduction() {
		return introduction;
	}


	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}


	public String getAssistantName() {
		return assistantName;
	}


	public void setAssistantName(String assistantName) {
		this.assistantName = assistantName;
	}


	public String getAssistantPhone() {
		return assistantPhone;
	}


	public void setAssistantPhone(String assistantPhone) {
		this.assistantPhone = assistantPhone;
	}


	public String getAssistantSex() {
		return assistantSex;
	}


	public void setAssistantSex(String assistantSex) {
		this.assistantSex = assistantSex;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getMobilePhone() {
		return mobilePhone;
	}


	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}


	public String getFreezeFlag() {
		return freezeFlag;
	}


	public void setFreezeFlag(String freezeFlag) {
		this.freezeFlag = freezeFlag;
	}


	public String getSignature() {
		return signature;
	}


	public void setSignature(String signature) {
		this.signature = signature;
	}


	public String getGender() {
		return gender;
	}


	public void setGender(String gender) {
		this.gender = gender;
	}


	public Integer getZanNumber() {
		return zanNumber;
	}


	public void setZanNumber(Integer zanNumber) {
		this.zanNumber = zanNumber;
	}
	
	
	
}
