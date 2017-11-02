package cn.emay.laiqiang.entity;


/**
 * 
 * @Title 提成日志
 * @author zjlwm
 * @date 2016-12-20 下午5:50:28
 *
 */
public class LqDrawCommissionLog {

	private Long id;
	
	/**
	 * 
	 */
	private Long taskId;
	
	
	/**
	 * 交易类型id
	 */
	private Long transactionTypeId;
	
	
	/**
	 * 邀请人id
	 */
	private Long inviter;
	
	
	/**
	 * 受邀人id
	 */
	private Long invitee;
	
	
	
	/**
	 * 提成奖励
	 */
	private Double drawCommission;
	
	
	
	/**
	 * 会员
	 */
	private Long memberflowlogId;
	
	
	/**
	 * 账号流水
	 */
	private Long accountSeqId;
	
	
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



	public Long getTaskId() {
		return taskId;
	}



	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}



	public Long getTransactionTypeId() {
		return transactionTypeId;
	}



	public void setTransactionTypeId(Long transactionTypeId) {
		this.transactionTypeId = transactionTypeId;
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



	public Double getDrawCommission() {
		return drawCommission;
	}



	public void setDrawCommission(Double drawCommission) {
		this.drawCommission = drawCommission;
	}



	public Long getMemberflowlogId() {
		return memberflowlogId;
	}



	public void setMemberflowlogId(Long memberflowlogId) {
		this.memberflowlogId = memberflowlogId;
	}



	public Long getAccountSeqId() {
		return accountSeqId;
	}



	public void setAccountSeqId(Long accountSeqId) {
		this.accountSeqId = accountSeqId;
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
