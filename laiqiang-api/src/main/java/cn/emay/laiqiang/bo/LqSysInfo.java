package cn.emay.laiqiang.bo;


/**
 * 系统信息
 * @Title 
 * @author zjlwm
 * @date 2016-12-6 下午1:09:51
 *
 */
public class LqSysInfo {

	private Long id;
	
	/**
	 * 功能介绍
	 */
	private String introduce;
	
	
	/**
	 * 联系我们
	 */
	private String contactUs;
	
	/**
	 * 市场合作
	 */
	private String cooperation;
	
	
	/**
	 * 用户使用条款
	 */
	private String agreement;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getIntroduce() {
		return introduce;
	}


	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}


	public String getContactUs() {
		return contactUs;
	}


	public void setContactUs(String contactUs) {
		this.contactUs = contactUs;
	}


	public String getCooperation() {
		return cooperation;
	}


	public void setCooperation(String cooperation) {
		this.cooperation = cooperation;
	}


	public String getAgreement() {
		return agreement;
	}


	public void setAgreement(String agreement) {
		this.agreement = agreement;
	}
	
	
	
	
	
}
