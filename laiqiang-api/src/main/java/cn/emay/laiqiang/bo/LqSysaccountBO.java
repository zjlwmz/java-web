package cn.emay.laiqiang.bo;

import java.util.Date;

/**
 * ƽ̨�˻�
 * @author lenovo
 *
 */
public class LqSysaccountBO {

	private Long id;
	
	/**
	 * ���п�����
	 */
	private String accountName;
	
	/**
	 * ��������
	 */
	private Long bankId;
	
	/**
	 * ����֧������
	 */
	private String subbankName;
	
	
	/**
	 * �����˺�
	 */
	private String bankAccount;
	
	/**
	 * ����������
	 */
	private String legalPerson;
	
	/**
	 * �˻����
	 */
	private Double amount;
	
	
	/**
	 * ��������
	 */
	private Date updated_time;
	
	/**
	 * ������
	 */
	private Integer updatedby;
	
	/**
	 * ƽ̨�����ѻ���
	 */
	private Double poundageTotal;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public Long getBankId() {
		return bankId;
	}

	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}

	public String getSubbankName() {
		return subbankName;
	}

	public void setSubbankName(String subbankName) {
		this.subbankName = subbankName;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getLegalPerson() {
		return legalPerson;
	}

	public void setLegalPerson(String legalPerson) {
		this.legalPerson = legalPerson;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Date getUpdated_time() {
		return updated_time;
	}

	public void setUpdated_time(Date updated_time) {
		this.updated_time = updated_time;
	}

	public Integer getUpdatedby() {
		return updatedby;
	}

	public void setUpdatedby(Integer updatedby) {
		this.updatedby = updatedby;
	}

	public Double getPoundageTotal() {
		return poundageTotal;
	}

	public void setPoundageTotal(Double poundageTotal) {
		this.poundageTotal = poundageTotal;
	}
	
	
	
	
}
