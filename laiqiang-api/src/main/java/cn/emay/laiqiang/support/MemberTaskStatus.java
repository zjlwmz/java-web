package cn.emay.laiqiang.support;

/**
 * 会员赚钱任务状态
 * @Title 
 * @author zjlwm
 * @date 2016-12-2 上午9:49:11
 *
 */
public class MemberTaskStatus {

	
	/**
	 * 未申请  1
	 */
	public static final Short Apply = 1;
	
	/**
	 * 工作 2
	 */
	public static final Short Work = 2;
	
	/**
	 * 已提交 3
	 */
	public static final Short hasSubmitted = 3;
	
	/**
	 * 审核成功(已完成) 4
	 */
	public static final Short AuditSuccess = 4;
	
	
	/**
	 * 审核失败 5
	 */
	public static final Short AuditFailure = 5;
	
	
	
	/**
	 * 已取消 6
	 */
	public static final Short HasBeenCanceled = 6;
	
}
