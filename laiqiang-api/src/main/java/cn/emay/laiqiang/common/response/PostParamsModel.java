package cn.emay.laiqiang.common.response;


/**
 * {unionid:"aa",deviceId:"dd",postData:{},signature:"",nonce:"",token:"dd"}
 * @author lenovo
 *
 */
public class PostParamsModel {

	/**
	 * 唯一ID
	 */
	private String uuid;
	
	
	/**
	 * 提交数据
	 */
	private String postData;
	
	/**
	 * 签名
	 */
	private String signature;
	
	/**
	 * 随机数
	 */
	private String nonce;
	
	
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

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
}
