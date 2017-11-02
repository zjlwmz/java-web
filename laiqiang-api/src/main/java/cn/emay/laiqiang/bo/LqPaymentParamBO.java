package cn.emay.laiqiang.bo;

/**
 * 支付方式参数设置
 * @author lenovo
 *
 */
public class LqPaymentParamBO {

	private Long id;
	
	private Long paymentId;
	
	/**
	 * 参数名
	 */
	private String name;
	
	
	/**
	 * 参数值
	 */
	private String value;
	
	/**
	 * 参数标签
	 */
	private String lable;
	
	/**
	 * 参数描述
	 */
	private String description;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getLable() {
		return lable;
	}

	public void setLable(String lable) {
		this.lable = lable;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
	
}
