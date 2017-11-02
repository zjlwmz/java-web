package cn.emay.laiqiang.entity;

/**
 * @Title 交易异常日志表
 * @author zjlwm
 * @date 2016-12-16 上午11:00:37
 *
 */
public class LqTransactionErrorLog {

	private long id;
	
	/**
	 * app订单id
	 */
	private String orderNo;
	
	/**
	 * 描述
	 */
	private String describe;
	
	/**
	 * 会员id
	 */
	private Long memberId;
	
	/**
	 * 交易类型
	 */
	private long transactionTypeId;
	
	
	/**
	 * 交易金额
	 */
	private Double amount;
	
	/**
	 * 
	 */
	private String errorMessage;
	
	/**
	 * 流量、话费充值返回订单id
	 */
	private String flowOrderNo;
	
	/**
	 * 创建时间
	 */
	private String createdTime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}


	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public long getTransactionTypeId() {
		return transactionTypeId;
	}

	public void setTransactionTypeId(long transactionTypeId) {
		this.transactionTypeId = transactionTypeId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public String getFlowOrderNo() {
		return flowOrderNo;
	}

	public void setFlowOrderNo(String flowOrderNo) {
		this.flowOrderNo = flowOrderNo;
	}
	
	
	
	
	
}
