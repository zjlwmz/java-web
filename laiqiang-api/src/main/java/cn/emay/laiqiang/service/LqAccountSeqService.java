package cn.emay.laiqiang.service;

import java.util.List;

import cn.emay.laiqiang.entity.LqAccountSeq;



/**
 * 
 * @Title 资金变动流水service接口
 * @author zjlwm
 * @date 2016-12-21 上午9:55:30
 *
 */
public interface LqAccountSeqService {

	/**
	 * 资金账号流水分页查询
	 */
	public List<LqAccountSeq>findLqAccountSeqList(Long memberId,Long start,Long end);
	
	/**
	 * 签到任务奖励次数
	 */
	public Long findTaskSigninRewardCount(Long memberId,String taskId,Long type);
	
	
	
	/**
	 * 分享任务奖励
	 */
	public Long findTaskShareRewardCount(Long memberId,String taskId,Long type);
}
