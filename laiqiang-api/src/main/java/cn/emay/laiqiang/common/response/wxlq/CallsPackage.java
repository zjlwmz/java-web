package cn.emay.laiqiang.common.response.wxlq;


/**
 * @Title 话费包
 * @author zjlwm
 * @date 2016-12-12 下午4:40:32
 *
 */
public class CallsPackage {

	/**
	 * 运营商标识
	 */
	private String operator;
	
	/**
	 * 实际价格
	 */
	private String originalprice;
	
	
	/**
	 * 价格
	 */
	private String price;
	
	/**
	 * 话费包
	 */
	private String recharge;
	
	/**
	 * 
	 */
	private String areaid;
	
	/**
	 * 创建时间
	 */
	private String createtime;
	
	
	private String id;


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


	public String getPrice() {
		return price;
	}


	public void setPrice(String price) {
		this.price = price;
	}


	public String getRecharge() {
		return recharge;
	}


	public void setRecharge(String recharge) {
		this.recharge = recharge;
	}


	public String getAreaid() {
		return areaid;
	}


	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}


	public String getCreatetime() {
		return createtime;
	}


	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}
	
	
	
	
}
