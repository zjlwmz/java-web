package cn.emay.laiqiang.common.response.app;


/**
 * 
 * @Title 交易订单
 * @author zjlwm
 * @date 2016-12-13 下午2:29:49
 *
 */
public class TransactionOrder {

	/**
	 * 支付类型
	 */
	private String paymentType;
	
	/**
	 * 交易金额
	 */
	private Double amount;
	
	/**
	 * 
	 */
	private Integer fownum;

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Integer getFownum() {
		return fownum;
	}

	public void setFownum(Integer fownum) {
		this.fownum = fownum;
	}
	
	
	
	
	
}
