package cn.emay.laiqiang.dao;

import java.util.List;
import java.util.Map;

import cn.emay.laiqiang.common.persistence.CrudDao;
import cn.emay.laiqiang.common.persistence.annotation.MyBatisDao;
import cn.emay.laiqiang.entity.LqTransactionOrder;
import cn.emay.laiqiang.entity.Rechargelog;


/**
 * 
 * @Title 交易订单service接口
 * @author zjlwm
 * @date 2016-12-14 下午4:46:55
 *
 */
@MyBatisDao
public interface LqTransactionOrderDao extends CrudDao<LqTransactionOrder>{

	/**
	 * 根据订单编号查找
	 * @param orderNo
	 * @return
	 */
	public LqTransactionOrder findLqTransactionOrderByNo(String orderNo);
	
	/**
	 * 更新订单状态
	 * @param orderNo
	 * @param status
	 * @return
	 */
	public int updateOrderStatusByOrderNo(String orderNo,Integer status);
	
	
	/**
	 * 流量、话费 失败订单查询
	 * @param start
	 * @param end
	 * @param memberId
	 * @return
	 */
	public List<Rechargelog> findRechargelogList(Map<String,Object>params);
	
}
