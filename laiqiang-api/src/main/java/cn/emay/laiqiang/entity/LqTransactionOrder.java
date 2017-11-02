package cn.emay.laiqiang.entity;


/**
 * @Title 交易订单
 * @author zjlwm
 * @date 2016-12-14 下午1:55:28
 */
public class LqTransactionOrder {

	private Long id;
	
	/**
	 * 交易类型
	 */
	private Long transactionTypeId;
	
	/**
	 * 订单编号
	 */
	private String orderNo;
	
	
	/**
	 * 流量相关订单号(为了与微信端逻辑对接)
	 */
	private String flowOrderNo;
	
	
	/**
	 * 交易金额
	 */
	private Double amount;
	
	/**
	 * 交易流量
	 */
	private Integer fownum;
	
	/**
	 * 提现手续费
	 */
	private Double cashPoundage;
	
	/**
	 * 会员id
	 */
	private Long memberId;
	
	/**
	 * 创建时间
	 */
	private String createdTime;

	/**
	 * 状态（0：未支付；4: 已提交(提现专用)；1：已支付；2：已取消；3：已退款）
	 */
	private Integer status;
	
	
	/**
	 * 交易结束时间(提现时用，或为到帐时间或为退款时间)
	 */
	private String finishOn;
	
	
	/**
	 * 前次交易结果查询时间
	 */
	private String queryLastOn;
	
	
	/**
	 * 交易结果查询次数
	 */
	private Integer queryTimes;
	
	
	/**
	 * 奖励类型(1:流量；2：现金)
	 */
	private String rewardType;
	
	
	/**
	 * 手机号码
	 */
	private String phoneNumber;
	
	
	/**
	 * 套餐类型
	 */
	private String packageType;
	
	
	/**
	 * 卡卷id
	 */
	private Long cardcodeId;
	
	/**
	 * 接口返回状态(1:成功;0:失败)
	 */
	private Integer interfaceReturnStatus;
	
	
	/**
	 * 任务id
	 */
	private Long taskId;
	
	
	/**
	 * 支付方式
	 */
	private Integer paymentId;
	
	
	
	
	/**
	 * 第三方支付的交易订单id
	 * @return
	 */
	private String thirdpartyOrderId;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTransactionTypeId() {
		return transactionTypeId;
	}

	public void setTransactionTypeId(Long transactionTypeId) {
		this.transactionTypeId = transactionTypeId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getCashPoundage() {
		return cashPoundage;
	}

	public void setCashPoundage(Double cashPoundage) {
		this.cashPoundage = cashPoundage;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getFinishOn() {
		return finishOn;
	}

	public void setFinishOn(String finishOn) {
		this.finishOn = finishOn;
	}

	public String getQueryLastOn() {
		return queryLastOn;
	}

	public void setQueryLastOn(String queryLastOn) {
		this.queryLastOn = queryLastOn;
	}

	public Integer getQueryTimes() {
		return queryTimes;
	}

	public void setQueryTimes(Integer queryTimes) {
		this.queryTimes = queryTimes;
	}

	public String getRewardType() {
		return rewardType;
	}

	public void setRewardType(String rewardType) {
		this.rewardType = rewardType;
	}

	public String getFlowOrderNo() {
		return flowOrderNo;
	}

	public void setFlowOrderNo(String flowOrderNo) {
		this.flowOrderNo = flowOrderNo;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPackageType() {
		return packageType;
	}

	public void setPackageType(String packageType) {
		this.packageType = packageType;
	}

	public Long getCardcodeId() {
		return cardcodeId;
	}

	public void setCardcodeId(Long cardcodeId) {
		this.cardcodeId = cardcodeId;
	}

	public Integer getInterfaceReturnStatus() {
		return interfaceReturnStatus;
	}

	public void setInterfaceReturnStatus(Integer interfaceReturnStatus) {
		this.interfaceReturnStatus = interfaceReturnStatus;
	}

	public Integer getFownum() {
		return fownum;
	}

	public void setFownum(Integer fownum) {
		this.fownum = fownum;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public Integer getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Integer paymentId) {
		this.paymentId = paymentId;
	}

	public String getThirdpartyOrderId() {
		return thirdpartyOrderId;
	}

	public void setThirdpartyOrderId(String thirdpartyOrderId) {
		this.thirdpartyOrderId = thirdpartyOrderId;
	}

	
	
}
