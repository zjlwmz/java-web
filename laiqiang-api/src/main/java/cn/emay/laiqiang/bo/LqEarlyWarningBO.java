package cn.emay.laiqiang.bo;


/**
 * @Title 预警配置表
 * @author zjlwm
 * @date 2017-3-3 下午1:56:41
 */
public class LqEarlyWarningBO {

	private Long id;
	
	
	/**
	 * 类型（1：http接口异常；）
	 */
	private Long type;
	
	
	/**
	 * 手机号码，多个手机号码用逗号分隔
	 */
	private String phoneNumber;
	
	
	
	/**
	 * 短信内容
	 */
	private String smsContent;
	
	
	/**
	 * 预警时间间隔，单位分钟
	 */
	private Integer timeInterval;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Long getType() {
		return type;
	}


	public void setType(Long type) {
		this.type = type;
	}


	public String getPhoneNumber() {
		return phoneNumber;
	}


	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}


	public String getSmsContent() {
		return smsContent;
	}


	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}


	public Integer getTimeInterval() {
		return timeInterval;
	}


	public void setTimeInterval(Integer timeInterval) {
		this.timeInterval = timeInterval;
	}
	
	
	
	
	
	
	
}
