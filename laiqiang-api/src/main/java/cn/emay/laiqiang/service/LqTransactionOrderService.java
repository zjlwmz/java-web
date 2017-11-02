package cn.emay.laiqiang.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.emay.laiqiang.dao.LqTransactionOrderDao;
import cn.emay.laiqiang.entity.LqTransactionOrder;


/**
 * @Title ���׶�������
 * @author zjlwm
 * @date 2016-12-14 ����4:45:36
 *
 */
@Service
@Transactional(readOnly=true)
public class LqTransactionOrderService {

	/**
	 * ���׶���DAO�ӿ�
	 */
	@Autowired
	private LqTransactionOrderDao lqTransactionOrderDao;
	
	/**
	 * ���ݶ�����Ų��Ҷ���
	 * @param orderNo
	 * @return
	 */
	public LqTransactionOrder findLqTransactionOrderByOrderNo(String orderNo){
		return lqTransactionOrderDao.findLqTransactionOrderByNo(orderNo);
	}
	
	
	/**
	 * ��������
	 * @param lqTransactionOrderBO
	 * @return
	 */
	@Transactional(readOnly=false)
	public int insert(LqTransactionOrder lqTransactionOrderBO){
		return lqTransactionOrderDao.insert(lqTransactionOrderBO);
	}
	
	
	
	/**
	 * ���¶���״̬
	 */
	@Transactional(readOnly=false)
	public int update(LqTransactionOrder lqTransactionOrderBO){
		return lqTransactionOrderDao.update(lqTransactionOrderBO);
	}
	
}
