package cn.emay.laiqiang.entity;


/**
 * 
 * @Title �����־
 * @author zjlwm
 * @date 2016-12-20 ����5:50:28
 *
 */
public class LqDrawCommissionLog {

	private Long id;
	
	/**
	 * 
	 */
	private Long taskId;
	
	
	/**
	 * ��������id
	 */
	private Long transactionTypeId;
	
	
	/**
	 * ������id
	 */
	private Long inviter;
	
	
	/**
	 * ������id
	 */
	private Long invitee;
	
	
	
	/**
	 * ��ɽ���
	 */
	private Double drawCommission;
	
	
	
	/**
	 * ��Ա
	 */
	private Long memberflowlogId;
	
	
	/**
	 * �˺���ˮ
	 */
	private Long accountSeqId;
	
	
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
