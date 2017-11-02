package cn.emay.laiqiang.dao;

import java.util.List;
import java.util.Map;

import cn.emay.laiqiang.common.persistence.CrudDao;
import cn.emay.laiqiang.common.persistence.annotation.MyBatisDao;
import cn.emay.laiqiang.entity.LqInviteLog;


/**
 * 
 * @Title ������־DAO�ӿ�
 * @author zjlwm
 * @date 2016-12-20 ����4:38:23
 *
 */
@MyBatisDao
public interface LqInviteLogDao extends CrudDao<LqInviteLog>{

	/**
	 * ��������б��ҳ��ѯ
	 * @param params
	 * @return
	 */
	public List<LqInviteLog>findLqInvitelogList(Map<String,Object>params);
	
	
	
}
