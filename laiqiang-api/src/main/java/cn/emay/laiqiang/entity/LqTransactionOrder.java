package cn.emay.laiqiang.entity;


/**
 * @Title ���׶���
 * @author zjlwm
 * @date 2016-12-14 ����1:55:28
 */
public class LqTransactionOrder {

	private Long id;
	
	/**
	 * ��������
	 */
	private Long transactionTypeId;
	
	/**
	 * �������
	 */
	private String orderNo;
	
	
	/**
	 * ������ض�����(Ϊ����΢�Ŷ��߼��Խ�)
	 */
	private String flowOrderNo;
	
	
	/**
	 * ���׽��
	 */
	private Double amount;
	
	/**
	 * ��������
	 */
	private Integer fownum;
	
	/**
	 * ����������
	 */
	private Double cashPoundage;
	
	/**
	 * ��Աid
	 */
	private Long memberId;
	
	/**
	 * ����ʱ��
	 */
	private String createdTime;

	/**
	 * ״̬��0��δ֧����4: ���ύ(����ר��)��1����֧����2����ȡ����3�����˿
	 */
	private Integer status;
	
	
	/**
	 * ���׽���ʱ��(����ʱ�ã���Ϊ����ʱ���Ϊ�˿�ʱ��)
	 */
	private String finishOn;
	
	
	/**
	 * ǰ�ν��׽����ѯʱ��
	 */
	private String queryLastOn;
	
	
	/**
	 * ���׽����ѯ����
	 */
	private Integer queryTimes;
	
	
	/**
	 * ��������(1:������2���ֽ�)
	 */
	private String rewardType;
	
	
	/**
	 * �ֻ�����
	 */
	private String phoneNumber;
	
	
	/**
	 * �ײ�����
	 */
	private String packageType;
	
	
	/**
	 * ����id
	 */
	private Long cardcodeId;
	
	/**
	 * �ӿڷ���״̬(1:�ɹ�;0:ʧ��)
	 */
	private Integer interfaceReturnStatus;
	
	
	/**
	 * ����id
	 */
	private Long taskId;
	
	
	/**
	 * ֧����ʽ
	 */
	private Integer paymentId;
	
	
	
	
	/**
	 * ������֧���Ľ��׶���id
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
