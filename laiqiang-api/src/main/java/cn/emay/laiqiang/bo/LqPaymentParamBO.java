package cn.emay.laiqiang.bo;

/**
 * ֧����ʽ��������
 * @author lenovo
 *
 */
public class LqPaymentParamBO {

	private Long id;
	
	private Long paymentId;
	
	/**
	 * ������
	 */
	private String name;
	
	
	/**
	 * ����ֵ
	 */
	private String value;
	
	/**
	 * ������ǩ
	 */
	private String lable;
	
	/**
	 * ��������
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
