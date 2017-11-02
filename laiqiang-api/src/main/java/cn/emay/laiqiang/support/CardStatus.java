package cn.emay.laiqiang.support;


/**
 * @Title 卡卷状态
 * @author zjlwm
 * @date 2016-12-27 上午11:07:28
 *
 */
public class CardStatus {
	
    /**
     * 0:未领取
     */
	public final static  int NoReceive=0;
	
	 /**
     * 1：已领取
     */
	public final static int AlreadyReceive=1;
	
	
	
	 /**
     * 2：已使用（兑换成功）
     * Exchange success
     */
	public final static int AlreadyUsedSuccess=2;
	
	
	 /**
     * 3：已使用（M码兑换失败）
     */
	public final static int AlreadyUsedFail=3;
	
	
	
	/**
	 * 4: 提交中
	 */
	public final static int InSubmission =4;
}
