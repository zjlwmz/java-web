package cn.emay.laiqiang.bo;


public class LqMemberTaskBO {

	private String id;
	
	/**
	 * 会员id
	 */
	private Long memberId;
	
	/**
	 * 任务id
	 */
	private String taskId;
	
	
	/**
	 * 申请时间(任务开始时间)
	 */
	private String startTime;
	
	
	/**
	 * 提交时间
	 */
	private String submitTime;
	
//	/**
//	 * 屏幕截图url
//	 */
//	private String screenshot="";
	
	/**
	 * 状态(1:申请；工作；3：已提交；4：审核成功)
	 */
	private Integer status;
	
	/**
	 * 审核状态(1:待提交;2:待审核；3：审核通过；4：审核驳回)
	 */
	private Integer auditStatus;
	
	
	/**
	 * 审核时间(完成时间)
	 */
	private String auditTime;
	
	/**
	 * 审核人
	 */
	private String auditBy;
	
	
	/**
	 * 驳回原因
	 */
	private String rejectReason;
	
	/**
	 * 赚取奖励总额
	 */
	private Double totalReward;
	
	
	/**
	 * 瞬时字段
	 * 任务类型
	 */
	private String taskType;
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}


	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(String submitTime) {
		this.submitTime = submitTime;
	}

	public String getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(String auditTime) {
		this.auditTime = auditTime;
	}

	public String getAuditBy() {
		return auditBy;
	}

	public void setAuditBy(String auditBy) {
		this.auditBy = auditBy;
	}

	public String getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	public Double getTotalReward() {
		return totalReward;
	}

	public void setTotalReward(Double totalReward) {
		this.totalReward = totalReward;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	
	
	
	
	
}
