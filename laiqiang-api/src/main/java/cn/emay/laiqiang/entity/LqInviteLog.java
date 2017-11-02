package cn.emay.laiqiang.entity;


/**
 * @Title ������־
 * @author zjlwm
 * @date 2016-12-20 ����4:29:50
 *
 */
public class LqInviteLog {

	private Long id;
	
	/**
	 * ������id
	 */
	private Long inviter;
	
	
	/**
	 * ������id
	 */
	private Long invitee;
	
	
	/**
	 * �����˽���
	 */
	private Double inviterCommission;
	
	
	/**
	 * �����˽���
	 */
	private Double inviteeCommission;
	
	/**
	 * ��������(1:������2���ֽ�)
	 */
	private Integer rewardType;
	
	
	/**
	 * ����ʱ��
	 */
	private String createdTime;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Long getInviter() {
		return inviter;
	}


	public void setInviter(Long inviter) {
		this.inviter = inviter;
	}


	public Long getInvitee() {
		return invitee;
	}


	public void setInvitee(Long invitee) {
		this.invitee = invitee;
	}


	public Double getInviterCommission() {
		return inviterCommission;
	}


	public void setInviterCommission(Double inviterCommission) {
		this.inviterCommission = inviterCommission;
	}


	public Double getInviteeCommission() {
		return inviteeCommission;
	}


	public void setInviteeCommission(Double inviteeCommission) {
		this.inviteeCommission = inviteeCommission;
	}


	public Integer getRewardType() {
		return rewardType;
	}


	public void setRewardType(Integer rewardType) {
		this.rewardType = rewardType;
	}


	public String getCreatedTime() {
		return createdTime;
	}


	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}
	
	
	
	
	
}
