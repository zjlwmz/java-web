package cn.emay.laiqiang.bo;

/**
 * ֧����ʽ������Ϣ
 * @author lenovo
 *
 */
public class LqPaymentBO {

	private Long id;
	
	/**
	 * ֧����ʽ����
	 */
	private String pay_code;
	
	/**
	 * ֧����ʽ����
	 */
	private String pay_name;
	
	
	/**
	 * ֧����ʽlogo url
	 */
	private String logo_url;
	
	/**
	 * ����
	 */
	private String description;
	
	/**
	 * ������
	 */
	private Double poundage;
	
	/**
	 * �����ѷ�ʽ(1:�ٷֱ�;2:�̶�ֵ)
	 */
	private Integer poundageType;
	
	/**
	 * ����״̬
	 */
	private Integer isEnabled;
	
	/**
	 * ����
	 */
	private Integer sort;
	
	/**
	 * �Ƿ�����֧��(1:���ϣ�0������)
	 */
	private Integer isOnline;
	
	
	/**
	 * 1:PC�� 2:�ƶ��� 3:ͨ��
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
