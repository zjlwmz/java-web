package cn.emay.laiqiang.dao;

import cn.emay.laiqiang.bo.LqOrderNoBO;
import cn.emay.laiqiang.common.persistence.CrudDao;
import cn.emay.laiqiang.common.persistence.annotation.MyBatisDao;


/**
 * @Title  �������DAO�ӿ�
 * @author zjlwm
 * @date 2016-12-14 ����4:01:41
 *
 */
@MyBatisDao
public interface LqOrderNoDao extends CrudDao<LqOrderNoBO>{
	
//	public int insert(LqOrderNoBO lqOrderNoBO);
//	
//	public int update(LqOrderNoBO lqOrderNoBO);
	
	public LqOrderNoBO findLqOrderNoBOByFdate(String fdate);
	/**
	 * ���µ�ǰʱ������
	 * @param fdate
	 * @return
	 */
	public int updateSerialNumber(String fdate);
	
	
	
}
