package cn.emay.laiqiang.dao;

import java.util.List;
import java.util.Map;

import cn.emay.laiqiang.common.persistence.CrudDao;
import cn.emay.laiqiang.common.persistence.annotation.MyBatisDao;

/**
 * 
 * @Title ϵͳDAO�ӿ�
 * @author zjlwm
 * @date 2016-12-19 ����4:53:51
 *
 */
@MyBatisDao
public interface SysDao  extends CrudDao<Object>{

	/**
	 * ��Ա����δ����Ϣ����
	 * @param params
	 * @return
	 */
	public Long findNoReadMessageCount(Map<String,Object>params);
	
	/**
	 * �Ѷ�Ⱥ��Ϣ����
	 * @param params
	 * @return
	 */
	public Long findReadMessagePushCount(Map<String,Object>params);
	
	
	
	/**
	 * ��Ҫ�������뽱�����û�ID
	 */
	public List<Long>rewardExport();
	
	/** 
	 * ��ѯ�����û�
	 * @return
	 */
	public List<Long>findAllLqMember();
	
}
