package cn.emay.laiqiang.bo;


/**
 * @Title Ԥ�����ñ�
 * @author zjlwm
 * @date 2017-3-3 ����1:56:41
 */
public class LqEarlyWarningBO {

	private Long id;
	
	
	/**
	 * ���ͣ�1��http�ӿ��쳣����
	 */
	private Long type;
	
	
	/**
	 * �ֻ����룬����ֻ������ö��ŷָ�
	 */
	private String phoneNumber;
	
	
	
	/**
	 * ��������
	 */
	private String smsContent;
	
	
	/**
	 * Ԥ��ʱ��������λ����
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
