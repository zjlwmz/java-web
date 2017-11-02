package cn.emay.laiqiang.service;

import java.util.List;

import cn.emay.laiqiang.dto.InviteFriendsDTO;


/**
 * 
 * @Title 提成日志 service接口
 * @author zjlwm
 * @date 2016-12-21 上午9:35:37
 *
 */
public interface LqDrawCommissionLogService {

//	public int insert(LqDrawCommissionLog lqDrawCommissionLog);
	
	/**
	 * 提成日志 分页查询
	 * @param memberId
	 * @param start
	 * @param end
	 * @return
	 */
	public List<InviteFriendsDTO>findLqDrawCommissionLogList(Long memberId,Long start,Long end);
	
	
	/**
	 * 好友提供总流量收益
	 * @param memberId
	 * @return
	 */
	public Double getTotalFlowRate(Long memberId);
	
	
	/**
	 * 好友提供总金额收益
	 * @param memberId
	 * @return
	 */
	public Double getTotalAmountOfIncome(Long memberId);
	
	
	
}
