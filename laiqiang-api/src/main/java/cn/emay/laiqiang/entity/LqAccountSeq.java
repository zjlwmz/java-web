package cn.emay.laiqiang.entity;

/**
 * @Title 资金变动流水表
 * @author zjlwm
 * @date 2017-1-10 下午2:34:14
 *
 */
public class LqAccountSeq {

	private long id;
	
	/**
	 * 账户id
	 */
	private long memberId;
	
	/**
	 * 金额划拨方向（1：入；-1：出；0：内部流转）
	 */
	private Integer direction;
	
	
	/**
	 * 交易订单Id(商户订单id)
	 */
	private Long transactionOrderId;
	
	
	/**
	 * 交易类型
	 */
	private Long transactionTypeId;
	
	
	/**
	 * 交易金额
	 */
	private Double transactionAmount;
	
	/**
	 * 支付方式
	 */
	private Integer paymentId;
	
	
	/**
	 * 第三方支付平台交易流水号(支付接口返回的流水号)
	 */
	private String paymentPlatformOrderId;
	
	
	
	/**
	 * 变动前账户余额
	 */
	private Double preBalance;
	
	
	/**
	 * 变动后账户余额
	 */
	private Double balance;
	
	
	/**
	 * 创建时间
	 */
	private String createdTime;
	
	
	/**
	 * 备注
	 */
	private String remarks;
	
	/**
	 * 提现手续费
	 */
	private Double cashPoundage;
	
	/**
	 * 任务id
	 */
	private Long taskId;
	
	
	/**
	 * 活动id
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
