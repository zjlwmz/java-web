package cn.emay.laiqiang.bo;


/**
 * 资金变动流水表
 * @author lenovo
 *
 */
public class LqAccountSeqBO {

	private Long id;
	
	/**
	 * 账户id
	 */
	private Long memberId;
	
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
	 * 交易流量
	 */
	private Integer transactionFownum;
	
	/**
	 * 支付方式
	 */
	private Long paymentId;
	
	/**
	 * 第三方支付平台交易流水号(支付接口返回的流水号)
	 */
	private String paymentPlatformOrderId;
	
	/**
	 * 变动前账户余额
	 */
	private Double pre_balance;
	
	
	/**
	 * 变动后账户余额
	 */
	private Double balance;
	
	/**
	 * 创建时间
	 */
	private String createdOn;
	
	
	/**
	 * 备注
	 */
	private String remarks;
	
	
	
	/**
	 * 提现手续费
	 */
	private Double cashPoundage;
	
	
	/**
	 * 会员任务id
	 */
	private Long memberTaskId;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Long getMemberId() {
		return memberId;
	}


	public void setMemberId(Long memberId) {
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


	public Integer getTransactionFownum() {
		return transactionFownum;
	}


	public void setTransactionFownum(Integer transactionFownum) {
		this.transactionFownum = transactionFownum;
	}


	public Long getPaymentId() {
		return paymentId;
	}


	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}


	public String getPaymentPlatformOrderId() {
		return paymentPlatformOrderId;
	}


	public void setPaymentPlatformOrderId(String paymentPlatformOrderId) {
		this.paymentPlatformOrderId = paymentPlatformOrderId;
	}


	public Double getPre_balance() {
		return pre_balance;
	}


	public void setPre_balance(Double pre_balance) {
		this.pre_balance = pre_balance;
	}


	public Double getBalance() {
		return balance;
	}


	public void setBalance(Double balance) {
		this.balance = balance;
	}


	public String getCreatedOn() {
		return createdOn;
	}


	public void setCreated_on(String created_on) {
		this.createdOn = created_on;
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


	public Long getMemberTaskId() {
		return memberTaskId;
	}


	public void setMemberTaskId(Long memberTaskId) {
		this.memberTaskId = memberTaskId;
	}
	
	
	
	
}
