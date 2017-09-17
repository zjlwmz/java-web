package cn.emay.model;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

@Table("sys_sms_config")
public class SmsConfig {
	
	@Column("id")
	@Name
	private String id;
	
	/**
	 * 短信服务序列号
	 */
	@Column("serialnumber")
	private String serialnumber;
	
	/**
	 * 定义key
	 */
	@Column("key")
	private String key;
	
	/**
	 * 序列号密码
	 */
	@Column("password")
	private String password;
	
	
	/**
	 * 状态（0：未注册；1：已注册）
	 */
	@Column("status")
	private String status;
	
	@Column("del_flag")
	private String delFlag;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSerialnumber() {
		return serialnumber;
	}

	public void setSerialnumber(String serialnumber) {
		this.serialnumber = serialnumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	
}
