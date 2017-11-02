package cn.emay.laiqiang.dao;

import java.util.List;
import java.util.Map;

import cn.emay.laiqiang.common.persistence.CrudDao;
import cn.emay.laiqiang.common.persistence.annotation.MyBatisDao;
import cn.emay.laiqiang.entity.LqDrawCommissionLog;


/**
 * 
 * @Title 提成日志DAO接口
 * @author zjlwm
 * @date 2016-12-21 上午9:23:32
 *
 */
@MyBatisDao
public interface LqDrawCommissionLogDao extends CrudDao<LqDrawCommissionLog>{

	/**
	 * 提成日志分页查询
	 * @param memberId
	 * @param start
	 * @param end
	 * @return
	 */
	public List<LqDrawCommissionLog> findLqDrawCommissionLogList(Map<String,Object>params);
	
	

	/**
	 * 获取总收益
	 * Long inviter,Integer rewardType
	 * @param inviter
	 * @return
	 */
	public Double getGrossIncome(Map<String,Object>params);

	
	
	

}
