package cn.emay.laiqiang.entity;


/**
 * @Title �����ײͶ���
 * @author zjlwm
 * @date 2016-12-15 ����6:02:55
 *
 */
public class Rechargebalance {

	private Long id;
	
	private long memberId;
	
	/**
	 * ����id
	 */
	private String orderid;
	
	/**
	 * ���
	 */
	private Integer money;
	
	
	/**
	 * �ײ�
	 */
	private Integer flowpackage;
	
	
	/**
	 * -2:�����رգ�-1:δ���ѣ�1����ֵ�ɹ���2����ֵʧ��
	 */
	private Integer status;
	
	/**
	 * ����ʱ��
	 */
	private String createtime;
	
	/**
	 * 
	 */
	private String returntime;
	
	/**
	 * ����id
	 */
	private Long codeid;
	
	/**
	 * �����˿�״̬1�������У�2�ܾ��˿3ͬ���˿4�˿�ɹ���5�˿�ʧ��
	 */
	private Integer rstatus;
	
	/**
	 * 
	 */
	private String rdescr;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public long getMemberId() {
		return memberId;
	}

	public void setMemberId(long memberId) {
		this.memberId = memberId;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public Integer getMoney() {
		return money;
	}

	public void setMoney(Integer money) {
		this.money = money;
	}

	public Integer getFlowpackage() {
		return flowpackage;
	}

	public void setFlowpackage(Integer flowpackage) {
		this.flowpackage = flowpackage;
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

	public String getReturntime() {
		return returntime;
	}

	public void setReturntime(String returntime) {
		this.returntime = returntime;
	}

	public Long getCodeid() {
		return codeid;
	}

	public void setCodeid(Long codeid) {
		this.codeid = codeid;
	}

	public Integer getRstatus() {
		return rstatus;
	}

	public void setRstatus(Integer rstatus) {
		this.rstatus = rstatus;
	}

	public String getRdescr() {
		return rdescr;
	}

	public void setRdescr(String rdescr) {
		this.rdescr = rdescr;
	}
	
	
	
	
}
