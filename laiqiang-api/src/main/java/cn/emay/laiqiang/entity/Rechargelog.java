package cn.emay.laiqiang.entity;


import cn.emay.laiqiang.common.utils.DateUtils;


/**
 * @Title 充值订单记录
 * @author zjlwm
 * @date 2016-12-15 下午4:27:28
 *
 */
public class Rechargelog implements Comparable<Rechargelog>{
	
	/**
	 * 手机号
	 */
	private String phone;
	
	
	/**
	 * 运营商
	 */
	private String operator;
	
	
	/**
	 * 状态 -2:订单关闭，1(4)已提交 2成功 3失败,0:待提交
	 */
	private Integer status;
	
	/**
	 * 套餐
	 */
	private Integer packageType;
	
	
	
	
	/**
	 * 金额
	 */
	private String money="0.0";
	
	
	/**
	 * 创建时间
	 */
	private String createtime;
	
	/**
	 * 失败流量
	 */
	private Integer failflow;
	
	/**
	 * 0:流量支付 1：H5微信支付 2：M码兑换；3：App微信支付；4：资金账户余额支付；5：支付宝App支付
	 */
	private String type;
	
	
	/**
	 * 退款状态
	 * 申请退款状态1：申请中，2拒绝退款，3同意退款，4退款成功，5退款失败
	 */
	private String rstatus;


	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}



	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}



	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getPackageType() {
		return packageType;
	}

	public void setPackageType(Integer packageType) {
		this.packageType = packageType;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getRstatus() {
		return rstatus;
	}

	public void setRstatus(String rstatus) {
		this.rstatus = rstatus;
	}

	public Integer getFailflow() {
		return failflow;
	}

	public void setFailflow(Integer failflow) {
		this.failflow = failflow;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int compareTo(Rechargelog o) {
		Rechargelog rechargelog=(Rechargelog)o;
		Long createtime1=DateUtils.parseDate(rechargelog.getCreatetime()).getTime();
		Long createtime2=DateUtils.parseDate(this.getCreatetime()).getTime();
		return createtime1.compareTo(createtime2);
	}

	
	
	
	
	
	
	
	
}
