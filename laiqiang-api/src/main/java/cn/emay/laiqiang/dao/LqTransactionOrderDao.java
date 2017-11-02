package cn.emay.laiqiang.dao;

import java.util.List;
import java.util.Map;

import cn.emay.laiqiang.common.persistence.CrudDao;
import cn.emay.laiqiang.common.persistence.annotation.MyBatisDao;
import cn.emay.laiqiang.entity.LqTransactionOrder;
import cn.emay.laiqiang.entity.Rechargelog;


/**
 * 
 * @Title ���׶���service�ӿ�
 * @author zjlwm
 * @date 2016-12-14 ����4:46:55
 *
 */
@MyBatisDao
public interface LqTransactionOrderDao extends CrudDao<LqTransactionOrder>{

	/**
	 * ���ݶ�����Ų���
	 * @param orderNo
	 * @return
	 */
	public LqTransactionOrder findLqTransactionOrderByNo(String orderNo);
	
	/**
	 * ���¶���״̬
	 * @param orderNo
	 * @param status
	 * @return
	 */
	public int updateOrderStatusByOrderNo(String orderNo,Integer status);
	
	
	/**
	 * ���������� ʧ�ܶ�����ѯ
	 * @param start
	 * @param end
	 * @param memberId
	 * @return
	 */
	public List<Rechargelog> findRechargelogList(Map<String,Object>params);
	
}
