package cn.emay.model;

import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;


/**
 * 短信发送日志
 * @author zjlwm
 * @version 2015-05-04
 */
@Table("s_sms_log")
public class SmsLog {

	@Name
	@Column("id")
	private String id;
	
	@Column("content")
	private String content;
	
	@Column("send_date")
	private Date sendDate;
	
	@Column("mobilephone")
	private String mobilephone;
	
	/**
	 * 验证码
	 */
	@Column("smscode")
	private String smscode;
	
	@Column("status")
	private String status;


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public Date getSendDate() {
		return sendDate;
	}


	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getMobilephone() {
		return mobilephone;
	}


	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}


	public String getSmscode() {
		return smscode;
	}


	public void setSmscode(String smscode) {
		this.smscode = smscode;
	}
	
	
	
	
}
