package cn.emay.laiqiang.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.emay.laiqiang.dao.LqTransactionOrderDao;
import cn.emay.laiqiang.entity.LqTransactionOrder;


/**
 * @Title 交易订单保存
 * @author zjlwm
 * @date 2016-12-14 下午4:45:36
 *
 */
@Service
@Transactional(readOnly=true)
public class LqTransactionOrderService {

	/**
	 * 交易订单DAO接口
	 */
	@Autowired
	private LqTransactionOrderDao lqTransactionOrderDao;
	
	/**
	 * 根据订单编号查找订单
	 * @param orderNo
	 * @return
	 */
	public LqTransactionOrder findLqTransactionOrderByOrderNo(String orderNo){
		return lqTransactionOrderDao.findLqTransactionOrderByNo(orderNo);
	}
	
	
	/**
	 * 订单保存
	 * @param lqTransactionOrderBO
	 * @return
	 */
	@Transactional(readOnly=false)
	public int insert(LqTransactionOrder lqTransactionOrderBO){
		return lqTransactionOrderDao.insert(lqTransactionOrderBO);
	}
	
	
	
	/**
	 * 更新订单状态
	 */
	@Transactional(readOnly=false)
	public int update(LqTransactionOrder lqTransactionOrderBO){
		return lqTransactionOrderDao.update(lqTransactionOrderBO);
	}
	
}
