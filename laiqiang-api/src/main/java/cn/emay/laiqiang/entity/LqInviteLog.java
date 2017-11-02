package cn.emay.laiqiang.entity;


/**
 * @Title 邀请日志
 * @author zjlwm
 * @date 2016-12-20 下午4:29:50
 *
 */
public class LqInviteLog {

	private Long id;
	
	/**
	 * 邀请人id
	 */
	private Long inviter;
	
	
	/**
	 * 受邀人id
	 */
	private Long invitee;
	
	
	/**
	 * 邀请人奖励
	 */
	private Double inviterCommission;
	
	
	/**
	 * 受邀人奖励
	 */
	private Double inviteeCommission;
	
	/**
	 * 奖励类型(1:流量；2：现金)
	 */
	private Integer rewardType;
	
	
	/**
	 * 创建时间
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
