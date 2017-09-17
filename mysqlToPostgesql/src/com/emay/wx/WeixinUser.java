package com.emay.wx;

import java.util.Date;

import javax.persistence.Table;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Name;


@Table(name="weixin_user")
public class WeixinUser {

	@Name
	@Column(value="id")
	private String id;
	
	@Column(value="subscribe_time")
	private Date subscribeTime;

	@Column(value="mobilephone")
	private String mobilephone;
	
	@Column(value="username")
	private String username;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getSubscribeTime() {
		return subscribeTime;
	}

	public void setSubscribeTime(Date subscribeTime) {
		this.subscribeTime = subscribeTime;
	}

	public String getMobilephone() {
		return mobilephone;
	}

	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	
	
}
