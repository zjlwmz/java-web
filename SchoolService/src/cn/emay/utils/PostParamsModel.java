package cn.emay.utils;

public class PostParamsModel {

	private String userId;
	
	private String siteId;
	/**
	 * 设备id
	 */
	private String DeviceId;
	
	
	private String postData;
	
	/**
	 * 签名
	 */
	private String signature;
	
	/**
	 * 随机数
	 */
	private String nonce;

	
	
	
	
	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDeviceId() {
		return DeviceId;
	}

	public void setDeviceId(String deviceId) {
		DeviceId = deviceId;
	}

	public String getPostData() {
		return postData;
	}

	public void setPostData(String postData) {
		this.postData = postData;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getNonce() {
		return nonce;
	}

	public void setNonce(String nonce) {
		this.nonce = nonce;
	}
	
	
	
	
}
