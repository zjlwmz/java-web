package cn.emay.laiqiang.entity;


/**
 * @Title 会员资金账户
 * @author zjlwm
 * @date 2016-12-16 下午1:05:12
 *
 */
public class LqAccount {

	private Long memberId;
	
	/**
	 * 账号余额
	 */
	private Double balance;
	
	/**
	 * 创建时间
	 */
	private String createdTime;
	
	
	/**
	 * 更新时间
	 */
	private String updatedTime;
	
	
	private Integer version;
	
	/**
	 * 流量收益汇总(类型:101)
	 */
	private Integer totalflow101;
	
	/**
	 * 流量收益汇总(类型:102)
	 */
	private Integer totalflow102;
	
	
	/**
	 * 流量收益汇总(类型:103)
	 */
	private Integer totalflow103;
	
	
	/**
	 * 流量收益汇总(类型:104)
	 */
	private Integer totalflow104;
	
	/**
	 * 流量收益汇总(类型:105)
	 */
	private Integer totalflow105;
	
	/**
	 * 流量收益汇总(类型:109)
	 */
	private Integer totalflow109;
	
	
	
	/**
	 * 资金收益汇总(类型:101)
	 */
	private Double totalcash101;
	
	/**
	 * 资金收益汇总(类型:102)
	 */
	private Double totalcash102;
	
	
	/**
	 * 资金收益汇总(类型:103)
	 */
	private Double totalcash103;
	
	/**
	 * 资金收益汇总(类型:104)
	 */
	private Double totalcash104;
	
	
	/**
	 * 资金收益汇总(类型:105)
	 */
	private Double totalcash105;


	/**
	 * 资金收益汇总(类型:109)
	 */
	private Double totalcash109;
	
	
	
	public Long getMemberId() {
		return memberId;
	}


	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}


	public Double getBalance() {
		return balance;
	}


	public void setBalance(Double balance) {
		this.balance = balance;
	}


	public String getCreatedTime() {
		return createdTime;
	}


	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}


	public String getUpdatedTime() {
		return updatedTime;
	}


	public void setUpdatedTime(String updatedTime) {
		this.updatedTime = updatedTime;
	}


	public Integer getVersion() {
		return version;
	}


	public void setVersion(Integer version) {
		this.version = version;
	}


	public Integer getTotalflow101() {
		return totalflow101;
	}


	public void setTotalflow101(Integer totalflow101) {
		this.totalflow101 = totalflow101;
	}


	public Integer getTotalflow102() {
		return totalflow102;
	}


	public void setTotalflow102(Integer totalflow102) {
		this.totalflow102 = totalflow102;
	}


	public Integer getTotalflow103() {
		return totalflow103;
	}


	public void setTotalflow103(Integer totalflow103) {
		this.totalflow103 = totalflow103;
	}


	public Integer getTotalflow104() {
		return totalflow104;
	}


	public void setTotalflow104(Integer totalflow104) {
		this.totalflow104 = totalflow104;
	}


	public Integer getTotalflow105() {
		return totalflow105;
	}


	public void setTotalflow105(Integer totalflow105) {
		this.totalflow105 = totalflow105;
	}


	public Double getTotalcash101() {
		return totalcash101;
	}


	public void setTotalcash101(Double totalcash101) {
		this.totalcash101 = totalcash101;
	}


	public Double getTotalcash102() {
		return totalcash102;
	}


	public void setTotalcash102(Double totalcash102) {
		this.totalcash102 = totalcash102;
	}


	public Double getTotalcash103() {
		return totalcash103;
	}


	public void setTotalcash103(Double totalcash103) {
		this.totalcash103 = totalcash103;
	}


	public Double getTotalcash104() {
		return totalcash104;
	}


	public void setTotalcash104(Double totalcash104) {
		this.totalcash104 = totalcash104;
	}


	public Double getTotalcash105() {
		return totalcash105;
	}


	public void setTotalcash105(Double totalcash105) {
		this.totalcash105 = totalcash105;
	}


	public Integer getTotalflow109() {
		return totalflow109;
	}


	public void setTotalflow109(Integer totalflow109) {
		this.totalflow109 = totalflow109;
	}


	public Double getTotalcash109() {
		return totalcash109;
	}


	public void setTotalcash109(Double totalcash109) {
		this.totalcash109 = totalcash109;
	}
	
	
	
	
	
	
}
