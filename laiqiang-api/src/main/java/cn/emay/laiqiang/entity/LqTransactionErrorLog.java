package cn.emay.laiqiang.entity;

/**
 * @Title �����쳣��־��
 * @author zjlwm
 * @date 2016-12-16 ����11:00:37
 *
 */
public class LqTransactionErrorLog {

	private long id;
	
	/**
	 * app����id
	 */
	private String orderNo;
	
	/**
	 * ����
	 */
	private String describe;
	
	/**
	 * ��Աid
	 */
	private Long memberId;
	
	/**
	 * ��������
	 */
	private long transactionTypeId;
	
	
	/**
	 * ���׽��
	 */
	private Double amount;
	
	/**
	 * 
	 */
	private String errorMessage;
	
	/**
	 * ���������ѳ�ֵ���ض���id
	 */
	private String flowOrderNo;
	
	/**
	 * ����ʱ��
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
