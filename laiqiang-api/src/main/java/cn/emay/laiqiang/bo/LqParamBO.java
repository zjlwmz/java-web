package cn.emay.laiqiang.bo;

/**
 * 
 * @Title 参数
 * @author zjlwm
 * @date 2016-12-19 上午10:37:45
 *
 */
public class LqParamBO {

	private Long id;
	
	/**
	 * 参数类型（1：赚钱中心）
	 */
	private Integer paramType;
	
	
	/**
	 * 参数名
	 */
	private String paramName;
	
	
	
	/**
	 * 参数值
	 */
	private Double paramValue;
	
	/**
	 * 参数值类型(1:百分比；2：流量；3：现金)
	 */
	private Integer valueType;
	
	/**
	 * 参数标题
	 */
	private String paramTitle;
	
	
	/**
	 * 备注
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
