package cn.emay.laiqiang.bo;

/**
 * װ����־
 * @Title 
 * @author zjlwm
 * @date 2016-12-5 ����1:51:40
 *
 */
public class LqInstallLogBO {

	/**
	 * װ��ʱ��
	 */
	private String installTime;
	
	/**
	 * �豸IMEL��
	 */
	private String imei;
	
	/**
	 * ƽ̨���ͣ�1��android��2:IOS��
	 */
	private Short osType = 1;
	
	/**
	 * �豸�ͺ�
	 */
	private String deviceType;
	
	/**
	 * ��Աid
	 */
	private Long memberId;
	
	/**
	 * Ʒ��
	 */
	private String brand;
	
	/**
	 * ����ϵͳ�汾
	 */
	private String osVersion;

	
	/**
	 * APP�汾
	 */
	private String appVersion;
	
	public String getInstallTime() {
		return installTime;
	}

	public void setInstallTime(String installTime) {
		this.installTime = installTime;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public Short getOsType() {
		return osType;
	}

	public void setOsType(Short osType) {
		this.osType = osType;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getOsVersion() {
		return osVersion;
	}

	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}
	
	
	
}
