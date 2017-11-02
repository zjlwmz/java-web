package cn.emay.laiqiang.entity;


/**
 * @Title 话费套餐订单
 * @author zjlwm
 * @date 2016-12-15 下午6:02:55
 *
 */
public class Rechargebalance {

	private Long id;
	
	private long memberId;
	
	/**
	 * 订单id
	 */
	private String orderid;
	
	/**
	 * 金额
	 */
	private Integer money;
	
	
	/**
	 * 套餐
	 */
	private Integer flowpackage;
	
	
	/**
	 * -2:订单关闭，-1:未付费，1：充值成功；2：充值失败
	 */
	private Integer status;
	
	/**
	 * 创建时间
	 */
	private String createtime;
	
	/**
	 * 
	 */
	private String returntime;
	
	/**
	 * 卡卷id
	 */
	private Long codeid;
	
	/**
	 * 申请退款状态1：申请中，2拒绝退款，3同意退款，4退款成功，5退款失败
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
