package cn.emay.laiqiang.bo;

/**
 * 装机日志
 * @Title 
 * @author zjlwm
 * @date 2016-12-5 下午1:51:40
 *
 */
public class LqInstallLogBO {

	/**
	 * 装机时间
	 */
	private String installTime;
	
	/**
	 * 设备IMEL号
	 */
	private String imei;
	
	/**
	 * 平台类型（1：android；2:IOS）
	 */
	private Short osType = 1;
	
	/**
	 * 设备型号
	 */
	private String deviceType;
	
	/**
	 * 会员id
	 */
	private Long memberId;
	
	/**
	 * 品牌
	 */
	private String brand;
	
	/**
	 * 操作系统版本
	 */
	private String osVersion;

	
	/**
	 * APP版本
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
