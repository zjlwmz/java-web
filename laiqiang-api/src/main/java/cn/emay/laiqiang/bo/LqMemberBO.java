package cn.emay.laiqiang.bo;


/**
 * 
 * @Title app会员
 * @author zjlwm
 * @date 2016-12-13 下午4:27:27
 *
 */
public class LqMemberBO {

	private String uuid;
	
	/**
	 * 微信用户唯一标识
	 */
	private String unionid;
	
	private Long memberId;
	
	/**
	 * 支付密码
	 */
	private String payPassword;
	
	
	/**
	 * 支付加密混淆码
	 */
	private String paySalt;
	
	
	/**
	 * 最后登录的设备IMEI号
	 */
	private String imei;
	
	
	/**
	 * 邀请人id
	 */
	private Long inviter; 
	
	/**
	 * 注册来源(1:微信；2：app)
	 */
	private String registerFrom;
	
	
	/**
	 * 极光推送id
	 */
	private String pushId;

	
	/**
	 * 邀请码
	 */
	private String invitationCode;
	
	/**
	 * App端成功邀请人数
	 */
	private Long invitationNumber;

	private String createdTime;
	
	
	public String getUuid() {
		return uuid;
	}


	public void setUuid(String uuid) {
		this.uuid = uuid;
	}


	public String getPayPassword() {
		return payPassword;
	}


	public void setPayPassword(String payPassword) {
		this.payPassword = payPassword;
	}


	public String getPaySalt() {
		return paySalt;
	}


	public void setPaySalt(String paySalt) {
		this.paySalt = paySalt;
	}


	public String getImei() {
		return imei;
	}


	public void setImei(String imei) {
		this.imei = imei;
	}


	public Long getInviter() {
		return inviter;
	}


	public void setInviter(Long inviter) {
		this.inviter = inviter;
	}


	public String getRegisterFrom() {
		return registerFrom;
	}


	public void setRegisterFrom(String registerFrom) {
		this.registerFrom = registerFrom;
	}


	public String getPushId() {
		return pushId;
	}


	public void setPushId(String pushId) {
		this.pushId = pushId;
	}


	public String getInvitationCode() {
		return invitationCode;
	}


	public void setInvitationCode(String invitationCode) {
		this.invitationCode = invitationCode;
	}


	public Long getMemberId() {
		return memberId;
	}


	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}


	public String getUnionid() {
		return unionid;
	}


	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}


	public String getCreatedTime() {
		return createdTime;
	}


	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}


	public Long getInvitationNumber() {
		return invitationNumber;
	}


	public void setInvitationNumber(Long invitationNumber) {
		this.invitationNumber = invitationNumber;
	}
	
	
	
	
	
}
