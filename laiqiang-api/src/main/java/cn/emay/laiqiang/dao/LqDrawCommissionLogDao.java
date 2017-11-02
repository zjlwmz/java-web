package cn.emay.laiqiang.dao;

import java.util.List;
import java.util.Map;

import cn.emay.laiqiang.common.persistence.CrudDao;
import cn.emay.laiqiang.common.persistence.annotation.MyBatisDao;
import cn.emay.laiqiang.entity.LqDrawCommissionLog;


/**
 * 
 * @Title �����־DAO�ӿ�
 * @author zjlwm
 * @date 2016-12-21 ����9:23:32
 *
 */
@MyBatisDao
public interface LqDrawCommissionLogDao extends CrudDao<LqDrawCommissionLog>{

	/**
	 * �����־��ҳ��ѯ
	 * @param memberId
	 * @param start
	 * @param end
	 * @return
	 */
	public List<LqDrawCommissionLog> findLqDrawCommissionLogList(Map<String,Object>params);
	
	

	/**
	 * ��ȡ������
	 * Long inviter,Integer rewardType
	 * @param inviter
	 * @return
	 */
	public Double getGrossIncome(Map<String,Object>params);

	
	
	

}
