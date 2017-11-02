package cn.emay.laiqiang.bo;


/**
 * @Title 交易类型
 * @author zjlwm
 * @date 2016-12-19 上午11:11:03
 *
 */
public class LqTransactionTypeBO {

	private long id;
	
	/**
	 * 交易事务类型名称
	 */
	private String transactionTypeName;
	
	
	/**
	 * 账户类型（1：会员账户；2：平台账户；）
	 */
	private Integer accountType;
	
	
	/**
	 * 备注
	 */
	private String remarks;
	
	
	/**
	 * 是否可被推荐人提成
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
