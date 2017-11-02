package cn.emay.laiqiang.dao;

import java.util.List;
import java.util.Map;

import cn.emay.laiqiang.common.persistence.CrudDao;
import cn.emay.laiqiang.common.persistence.annotation.MyBatisDao;
import cn.emay.laiqiang.entity.Rechargelog;


/**
 * 
 * @Title ������ֵ����DAO�ӿ�
 * @author zjlwm
 * @date 2016-12-15 ����4:34:45
 *
 */
@MyBatisDao
public interface RechargelogDao extends CrudDao<Rechargelog>{

	/**
	 * ������ֵ������¼
	 * @param params
	 * @return
	 */
	public List<Rechargelog>findRechargelogList(Map<String,Object>params);
	
	/**
	 *  ������ֵ���������ܽ��
	 * @param memberid
	 * @return
	 */
	public Long findRechargelogTotalMoney(Long memberid);
	
	
	
	/**
	 * ���ѳ�ֵ������¼
	 * @param params
	 * @return
	 */
	public List<Rechargelog>findWxrechargeList(Map<String,Object>params);
	
	/**
	 *  ���ѳ�ֵ���������ܽ��
	 * @param memberid
	 * @return
	 */
	public Long findWxrechargeTotalMoney(Long memberid);
	
	
	
	
}
