package cn.emay.laiqiang.dao;

import java.util.List;
import java.util.Map;

import cn.emay.laiqiang.common.persistence.CrudDao;
import cn.emay.laiqiang.common.persistence.annotation.MyBatisDao;
import cn.emay.laiqiang.entity.Memberflowlog;


/**
 * @Title 用户流量消费日志DAO接口
 * @author zjlwm
 * @date 2016-12-20 下午3:37:03
 *
 */
@MyBatisDao
public interface MemberflowlogDao extends CrudDao<Memberflowlog>{

	/**
	 * 用户流量消费日志
	 * @param params
	 * @return
	 */
	public List<Memberflowlog>findMemberflowlogList(Map<String,Object>params);
	
	
	/**
	 * 今日签到次数
	 */
	public Long findSignInTodayCount(Map<String,Object>params);
	
	
	/**
	 * 分享任务次数
	 */
	public Long findTaskShareRewardCount(Map<String,Object>params);
	
	
	
	/**
	 * 推荐流量总收益
	 */
	public Long getRecommendedTotalFlow(Map<String,Object>params);
}
