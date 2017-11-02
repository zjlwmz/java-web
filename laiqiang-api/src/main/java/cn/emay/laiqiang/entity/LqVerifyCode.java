package cn.emay.laiqiang.entity;


/**
 * 
 * @Title 短信(语音)验证码表
 * @author zjlwm
 * @date 2017-1-10 下午4:23:05
 *
 */
public class LqVerifyCode {

	private Long id;
	
	/**
	 * 会员id
	 */
	private Long memberId;
	
	/**
	 * 电话号码
	 */
	private String phoneNumber;
	
	
	/**
	 * 验证码
	 */
	private String verifyCode;
	
	/**
	 * 创建时间
	 */
	private String createdTime;
	
	
	/**
	 * 短信类型
	 */
	private short type;
	
	/**
	 * 发送状态
	 */
	private String code;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	public short getType() {
		return type;
	}

	public void setType(short type) {
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	
	
	
}
