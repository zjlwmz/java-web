package cn.emay.laiqiang.dao;

import java.util.List;
import java.util.Map;

import cn.emay.laiqiang.common.persistence.CrudDao;
import cn.emay.laiqiang.common.persistence.annotation.MyBatisDao;
import cn.emay.laiqiang.entity.LqAccountSeq;


/**
 * @Title 资金变动流水表DAO接口
 * @author zjlwm
 * @date 2017-1-10 下午2:40:30
 *
 */
@MyBatisDao
public interface LqAccountSeqDao extends CrudDao<LqAccountSeq>{

	/**
	 * 签到奖励次数
	 */
	public Long findSignInTodayCount(Map<String, Object> params);

	/**
	 * 任务奖励次数
	 * @param params
	 * @return
	 */
	public Long findTaskRewardCount(Map<String, Object> params);

	
	/**
	 * 资金账号分页查询
	 * @param params
	 * @return
	 */
	public List<LqAccountSeq> findLqAccountSeqList(Map<String, Object> params);
	
	
	
	/**
	 * 总推荐现金收益
	 */
	public Double getRecommendedTotalCash(Map<String, Object> params);

}
