package cn.emay.laiqiang.bo;


/**
 * 
 * @Title app��Ա
 * @author zjlwm
 * @date 2016-12-13 ����4:27:27
 *
 */
public class LqMemberBO {

	private String uuid;
	
	/**
	 * ΢���û�Ψһ��ʶ
	 */
	private String unionid;
	
	private Long memberId;
	
	/**
	 * ֧������
	 */
	private String payPassword;
	
	
	/**
	 * ֧�����ܻ�����
	 */
	private String paySalt;
	
	
	/**
	 * ����¼���豸IMEI��
	 */
	private String imei;
	
	
	/**
	 * ������id
	 */
	private Long inviter; 
	
	/**
	 * ע����Դ(1:΢�ţ�2��app)
	 */
	private String registerFrom;
	
	
	/**
	 * ��������id
	 */
	private String pushId;

	
	/**
	 * ������
	 */
	private String invitationCode;
	
	/**
	 * App�˳ɹ���������
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
