package cn.emay.laiqiang.common.response.wxlq;


/**
 * @Title 流量包
 * @author zjlwm
 * @date 2016-12-12 下午12:53:19
 */
public class FlowPacket {

	private String gid;
	
	/**
	 * 流量包
	 */
	private String flowpackage;
	
	/**
	 * 运营商标识
	 */
	private String operator;
	
	
	/**
	 * 原始价格
	 */
	private String originalprice;
	
	
	
	/**
	 * 1:全网，2：省网
	 */
	private String packagetype;
	
	
	
	/**
	 * 价格
	 */
	private String price;
	
	
	/**
	 * 流量套餐属性；1：可余额充值2：支付充值3：全支持
	 */
	private String type;
	
	
	
	/**
	 * 创建时间
	 */
	private String createtime;



	public String getGid() {
		return gid;
	}



	public void setGid(String gid) {
		this.gid = gid;
	}



	public String getFlowpackage() {
		return flowpackage;
	}



	public void setFlowpackage(String flowpackage) {
		this.flowpackage = flowpackage;
	}



	public String getOperator() {
		return operator;
	}



	public void setOperator(String operator) {
		this.operator = operator;
	}



	public String getOriginalprice() {
		return originalprice;
	}



	public void setOriginalprice(String originalprice) {
		this.originalprice = originalprice;
	}



	public String getPackagetype() {
		return packagetype;
	}



	public void setPackagetype(String packagetype) {
		this.packagetype = packagetype;
	}



	public String getPrice() {
		return price;
	}



	public void setPrice(String price) {
		this.price = price;
	}



	public String getType() {
		return type;
	}



	public void setType(String type) {
		this.type = type;
	}



	public String getCreatetime() {
		return createtime;
	}



	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	
	
	
	
	
	
	
	
}
