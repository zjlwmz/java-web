package cn.emay.laiqiang.bo;


/**
 * @Title ��������
 * @author zjlwm
 * @date 2016-12-19 ����11:11:03
 *
 */
public class LqTransactionTypeBO {

	private long id;
	
	/**
	 * ����������������
	 */
	private String transactionTypeName;
	
	
	/**
	 * �˻����ͣ�1����Ա�˻���2��ƽ̨�˻�����
	 */
	private Integer accountType;
	
	
	/**
	 * ��ע
	 */
	private String remarks;
	
	
	/**
	 * �Ƿ�ɱ��Ƽ������
	 */
	private Boolean canDrawCommission;


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getTransactionTypeName() {
		return transactionTypeName;
	}


	public void setTransactionTypeName(String transactionTypeName) {
		this.transactionTypeName = transactionTypeName;
	}


	public Integer getAccountType() {
		return accountType;
	}


	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}


	public String getRemarks() {
		return remarks;
	}


	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}


	public Boolean getCanDrawCommission() {
		return canDrawCommission;
	}


	public void setCanDrawCommission(Boolean canDrawCommission) {
		this.canDrawCommission = canDrawCommission;
	}
	
	
	
	
	
}
