package cn.emay.laiqiang.bo;

/**
 * 支付方式配置信息
 * @author lenovo
 *
 */
public class LqPaymentBO {

	private Long id;
	
	/**
	 * 支付方式代码
	 */
	private String pay_code;
	
	/**
	 * 支付方式名称
	 */
	private String pay_name;
	
	
	/**
	 * 支付方式logo url
	 */
	private String logo_url;
	
	/**
	 * 描述
	 */
	private String description;
	
	/**
	 * 手续费
	 */
	private Double poundage;
	
	/**
	 * 手续费方式(1:百分比;2:固定值)
	 */
	private Integer poundageType;
	
	/**
	 * 启用状态
	 */
	private Integer isEnabled;
	
	/**
	 * 排序
	 */
	private Integer sort;
	
	/**
	 * 是否在线支付(1:线上；0：线下)
	 */
	private Integer isOnline;
	
	
	/**
	 * 1:PC端 2:移动端 3:通用
	 */
	private Integer clientType;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getPay_code() {
		return pay_code;
	}


	public void setPay_code(String pay_code) {
		this.pay_code = pay_code;
	}


	public String getPay_name() {
		return pay_name;
	}


	public void setPay_name(String pay_name) {
		this.pay_name = pay_name;
	}


	public String getLogo_url() {
		return logo_url;
	}


	public void setLogo_url(String logo_url) {
		this.logo_url = logo_url;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public Double getPoundage() {
		return poundage;
	}


	public void setPoundage(Double poundage) {
		this.poundage = poundage;
	}


	public Integer getPoundageType() {
		return poundageType;
	}


	public void setPoundageType(Integer poundageType) {
		this.poundageType = poundageType;
	}


	public Integer getIsEnabled() {
		return isEnabled;
	}


	public void setIsEnabled(Integer isEnabled) {
		this.isEnabled = isEnabled;
	}


	public Integer getSort() {
		return sort;
	}


	public void setSort(Integer sort) {
		this.sort = sort;
	}


	public Integer getIsOnline() {
		return isOnline;
	}


	public void setIsOnline(Integer isOnline) {
		this.isOnline = isOnline;
	}


	public Integer getClientType() {
		return clientType;
	}


	public void setClientType(Integer clientType) {
		this.clientType = clientType;
	}
	
	
	
	
}
