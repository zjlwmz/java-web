package cn.emay.laiqiang.dao;

import java.util.List;
import java.util.Map;

import cn.emay.laiqiang.common.persistence.CrudDao;
import cn.emay.laiqiang.common.persistence.annotation.MyBatisDao;
import cn.emay.laiqiang.entity.LqAccountSeq;


/**
 * @Title �ʽ�䶯��ˮ��DAO�ӿ�
 * @author zjlwm
 * @date 2017-1-10 ����2:40:30
 *
 */
@MyBatisDao
public interface LqAccountSeqDao extends CrudDao<LqAccountSeq>{

	/**
	 * ǩ����������
	 */
	public Long findSignInTodayCount(Map<String, Object> params);

	/**
	 * ����������
	 * @param params
	 * @return
	 */
	public Long findTaskRewardCount(Map<String, Object> params);

	
	/**
	 * �ʽ��˺ŷ�ҳ��ѯ
	 * @param params
	 * @return
	 */
	public List<LqAccountSeq> findLqAccountSeqList(Map<String, Object> params);
	
	
	
	/**
	 * ���Ƽ��ֽ�����
	 */
	public Double getRecommendedTotalCash(Map<String, Object> params);

}
