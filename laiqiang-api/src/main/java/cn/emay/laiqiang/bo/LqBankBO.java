package cn.emay.laiqiang.bo;


/**
 * �����б�
 * @author lenovo
 *
 */

public class LqBankBO {

	private Long id;
	
	/**
	 * ��������
	 */
	private String bankName;
	
	
	/**
	 * ���д���
	 */
	private String bankCode;
	
	
	
	/**
	 * ״̬
	 */
	private Integer status;
	
	
	/**
	 * ����ȫ��
	 */
	private String bankFullname;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getBankName() {
		return bankName;
	}


	public void setBankName(String bankName) {
		this.bankName = bankName;
	}


	public String getBankCode() {
		return bankCode;
	}


	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}


	public Integer getStatus() {
		return status;
	}


	public void setStatus(Integer status) {
		this.status = status;
	}


	public String getBankFullname() {
		return bankFullname;
	}


	public void setBankFullname(String bankFullname) {
		this.bankFullname = bankFullname;
	}
	
	
	
	
	
}
