package cn.emay.laiqiang.support;


/**
 * @Title 交易订单类型
 * @author zjlwm
 * @date 2016-12-15 上午10:35:13
 *
 */
public class TransactionOrderStatus {

	/**
	 * 未支付 0
	 */
	public final static int NotPaid=0;
	
	
	/**
	 * 已支付
	 */
	public final static int AlreadyPaid=1;
	
	/**
	 * 已取消 2
	 */
	public final static int HasBeenCanceled=2;
	
	
	/**
	 * 已退款 3
	 */
	public final static int Refunded=3;
	
	
	/**
	 * 已提交(提现专用) 4
	 */
	public final static int HasBeenSubmitted=4;
	
	
	
	
	
	
	
}
