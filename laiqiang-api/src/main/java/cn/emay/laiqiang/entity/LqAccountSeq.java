package cn.emay.laiqiang.entity;

/**
 * @Title �ʽ�䶯��ˮ��
 * @author zjlwm
 * @date 2017-1-10 ����2:34:14
 *
 */
public class LqAccountSeq {

	private long id;
	
	/**
	 * �˻�id
	 */
	private long memberId;
	
	/**
	 * ��������1���룻-1������0���ڲ���ת��
	 */
	private Integer direction;
	
	
	/**
	 * ���׶���Id(�̻�����id)
	 */
	private Long transactionOrderId;
	
	
	/**
	 * ��������
	 */
	private Long transactionTypeId;
	
	
	/**
	 * ���׽��
	 */
	private Double transactionAmount;
	
	/**
	 * ֧����ʽ
	 */
	private Integer paymentId;
	
	
	/**
	 * ������֧��ƽ̨������ˮ��(֧���ӿڷ��ص���ˮ��)
	 */
	private String paymentPlatformOrderId;
	
	
	
	/**
	 * �䶯ǰ�˻����
	 */
	private Double preBalance;
	
	
	/**
	 * �䶯���˻����
	 */
	private Double balance;
	
	
	/**
	 * ����ʱ��
	 */
	private String createdTime;
	
	
	/**
	 * ��ע
	 */
	private String remarks;
	
	/**
	 * ����������
	 */
	private Double cashPoundage;
	
	/**
	 * ����id
	 */
	private Long taskId;
	
	
	/**
	 * �id
	 */
	private Long activityId;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getMemberId() {
		return memberId;
	}

	public void setMemberId(long memberId) {
		this.memberId = memberId;
	}

	public Integer getDirection() {
		return direction;
	}

	public void setDirection(Integer direction) {
		this.direction = direction;
	}

	public Long getTransactionOrderId() {
		return transactionOrderId;
	}

	public void setTransactionOrderId(Long transactionOrderId) {
		this.transactionOrderId = transactionOrderId;
	}

	public Long getTransactionTypeId() {
		return transactionTypeId;
	}

	public void setTransactionTypeId(Long transactionTypeId) {
		this.transactionTypeId = transactionTypeId;
	}

	public Double getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(Double transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public Integer getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Integer paymentId) {
		this.paymentId = paymentId;
	}

	public String getPaymentPlatformOrderId() {
		return paymentPlatformOrderId;
	}

	public void setPaymentPlatformOrderId(String paymentPlatformOrderId) {
		this.paymentPlatformOrderId = paymentPlatformOrderId;
	}

	

	public Double getPreBalance() {
		return preBalance;
	}

	public void setPreBalance(Double preBalance) {
		this.preBalance = preBalance;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Double getCashPoundage() {
		return cashPoundage;
	}

	public void setCashPoundage(Double cashPoundage) {
		this.cashPoundage = cashPoundage;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public Long getActivityId() {
		return activityId;
	}

	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}
	
	
	
	
	
	
	
	
	
	
	
}
