package cn.emay.laiqiang.bo;


public class LqMemberTaskBO {

	private String id;
	
	/**
	 * ��Աid
	 */
	private Long memberId;
	
	/**
	 * ����id
	 */
	private String taskId;
	
	
	/**
	 * ����ʱ��(����ʼʱ��)
	 */
	private String startTime;
	
	
	/**
	 * �ύʱ��
	 */
	private String submitTime;
	
//	/**
//	 * ��Ļ��ͼurl
//	 */
//	private String screenshot="";
	
	/**
	 * ״̬(1:���룻������3�����ύ��4����˳ɹ�)
	 */
	private Integer status;
	
	/**
	 * ���״̬(1:���ύ;2:����ˣ�3�����ͨ����4����˲���)
	 */
	private Integer auditStatus;
	
	
	/**
	 * ���ʱ��(���ʱ��)
	 */
	private String auditTime;
	
	/**
	 * �����
	 */
	private String auditBy;
	
	
	/**
	 * ����ԭ��
	 */
	private String rejectReason;
	
	/**
	 * ׬ȡ�����ܶ�
	 */
	private Double totalReward;
	
	
	/**
	 * ˲ʱ�ֶ�
	 * ��������
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
