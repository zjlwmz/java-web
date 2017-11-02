package cn.emay.laiqiang.service;

import java.util.List;


/**
 * 
 * @Title 活动日志service接口
 * @author zjlwm
 * @date 2017-2-15 下午4:44:30
 *
 */
public interface LqActivityService {
	
	/**
	 * 活动奖励
	 */
	public void activityReward(long memberId,String imeiNew);
	
	
	public List<Long>rewardExport();
	
}
