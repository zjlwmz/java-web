package cn.emay.laiqiang.bo;

/**
 * 
 * @Title ����
 * @author zjlwm
 * @date 2016-12-19 ����10:37:45
 *
 */
public class LqParamBO {

	private Long id;
	
	/**
	 * �������ͣ�1��׬Ǯ���ģ�
	 */
	private Integer paramType;
	
	
	/**
	 * ������
	 */
	private String paramName;
	
	
	
	/**
	 * ����ֵ
	 */
	private Double paramValue;
	
	/**
	 * ����ֵ����(1:�ٷֱȣ�2��������3���ֽ�)
	 */
	private Integer valueType;
	
	/**
	 * ��������
	 */
	private String paramTitle;
	
	
	/**
	 * ��ע
	 */
	private String remarks;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Integer getParamType() {
		return paramType;
	}


	public void setParamType(Integer paramType) {
		this.paramType = paramType;
	}


	public String getParamName() {
		return paramName;
	}


	public void setParamName(String paramName) {
		this.paramName = paramName;
	}


	public Double getParamValue() {
		return paramValue;
	}


	public void setParamValue(Double paramValue) {
		this.paramValue = paramValue;
	}


	public Integer getValueType() {
		return valueType;
	}


	public void setValueType(Integer valueType) {
		this.valueType = valueType;
	}


	public String getParamTitle() {
		return paramTitle;
	}


	public void setParamTitle(String paramTitle) {
		this.paramTitle = paramTitle;
	}


	public String getRemarks() {
		return remarks;
	}


	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
	
	
	
	
	
	
}
