package cn.emay.laiqiang.bo;


/**
 * 
 * @Title  流量余额加油包
 * @author zjlwm
 * @date 2017-1-11 上午11:25:20
 *
 */
public class Balancepackage {

	private Long id;
	/**
	 * 套餐
	 */
	private Integer flowpackage;
	/**
	 * 价格（分）
	 */
	private Integer balance;
	/**
	 * 0:未上线
	 */
	private Integer status;
	
	/**
	 * 创建时间
	 */
	private String createtime;
	
	/**
	 * 描述文字
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
