package cn.emay.laiqiang.bo;


/**
 * 
 * @Title  ���������Ͱ�
 * @author zjlwm
 * @date 2017-1-11 ����11:25:20
 *
 */
public class Balancepackage {

	private Long id;
	/**
	 * �ײ�
	 */
	private Integer flowpackage;
	/**
	 * �۸񣨷֣�
	 */
	private Integer balance;
	/**
	 * 0:δ����
	 */
	private Integer status;
	
	/**
	 * ����ʱ��
	 */
	private String createtime;
	
	/**
	 * ��������
	 */
	private String descr;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getFlowpackage() {
		return flowpackage;
	}
	public void setFlowpackage(Integer flowpackage) {
		this.flowpackage = flowpackage;
	}
	public Integer getBalance() {
		return balance;
	}
	public void setBalance(Integer balance) {
		this.balance = balance;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	
	
	
	
	
	
}
