package cn.emay.laiqiang.bo;


/**
 * �ʽ�䶯��ˮ��
 * @author lenovo
 *
 */
public class LqAccountSeqBO {

	private Long id;
	
	/**
	 * �˻�id
	 */
	private Long memberId;
	
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
	 * ��������
	 */
	private Integer transactionFownum;
	
	/**
	 * ֧����ʽ
	 */
	private Long paymentId;
	
	/**
	 * ������֧��ƽ̨������ˮ��(֧���ӿڷ��ص���ˮ��)
	 */
	private String paymentPlatformOrderId;
	
	/**
	 * �䶯ǰ�˻����
	 */
	private Double pre_balance;
	
	
	/**
	 * �䶯���˻����
	 */
	private Double balance;
	
	/**
	 * ����ʱ��
	 */
	private String createdOn;
	
	
	/**
	 * ��ע
	 */
	private String remarks;
	
	
	
	/**
	 * ����������
	 */
	private Double cashPoundage;
	
	
	/**
	 * ��Ա����id
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
