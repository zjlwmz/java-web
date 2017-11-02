package cn.emay.laiqiang.dao;

import java.util.List;
import java.util.Map;

import cn.emay.laiqiang.common.persistence.CrudDao;
import cn.emay.laiqiang.common.persistence.annotation.MyBatisDao;
import cn.emay.laiqiang.entity.Memberflowlog;


/**
 * @Title �û�����������־DAO�ӿ�
 * @author zjlwm
 * @date 2016-12-20 ����3:37:03
 *
 */
@MyBatisDao
public interface MemberflowlogDao extends CrudDao<Memberflowlog>{

	/**
	 * �û�����������־
	 * @param params
	 * @return
	 */
	public List<Memberflowlog>findMemberflowlogList(Map<String,Object>params);
	
	
	/**
	 * ����ǩ������
	 */
	public Long findSignInTodayCount(Map<String,Object>params);
	
	
	/**
	 * �����������
	 */
	public Long findTaskShareRewardCount(Map<String,Object>params);
	
	
	
	/**
	 * �Ƽ�����������
	 */
	public Long getRecommendedTotalFlow(Map<String,Object>params);
}
