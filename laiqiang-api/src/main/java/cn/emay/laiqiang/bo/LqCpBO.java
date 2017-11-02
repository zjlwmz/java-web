package cn.emay.laiqiang.bo;



/**
 * 合作伙伴
 * @author lenovo
 *
 */
public class LqCpBO {

	private Long id;
	
	/**
	 * 合作伙伴名称
	 */
	private String name;
	
	
	/**
	 * 联系人
	 */
	private String contacts;
	
	
	
	
	/**
	 * 联系电话
	 */
	private String contactNumber;
	
	
	
	
	/**
	 * 开户银行
	 */
	private Long bankId;
	
	
	/**
	 * 账户名称
	 */
	private String accountName;
	
	
	
	/**
	 * 银行账号
	 */
	private String bankAccount;
	
	/**
	 * 创建时间
	 */
	private String createdTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public Long getBankId() {
		return bankId;
	}

	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}
	
	
	
	
}
