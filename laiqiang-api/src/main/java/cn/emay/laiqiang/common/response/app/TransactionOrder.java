package cn.emay.laiqiang.common.response.app;


/**
 * 
 * @Title ���׶���
 * @author zjlwm
 * @date 2016-12-13 ����2:29:49
 *
 */
public class TransactionOrder {

	/**
	 * ֧������
	 */
	private String paymentType;
	
	/**
	 * ���׽��
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
