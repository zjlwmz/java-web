package cn.emay.laiqiang.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.emay.laiqiang.dao.LqTransactionOrderDao;
import cn.emay.laiqiang.dao.RechargelogDao;
import cn.emay.laiqiang.entity.Rechargelog;
import cn.emay.laiqiang.support.TransactionOrderStatus;
import cn.emay.laiqiang.support.TransactionType;


/**
 * @Title 充值订单service接口
 * @author zjlwm
 * @date 2016-12-16 上午9:30:27
 *
 */
@Service
public class RechargelogService {

	/**
	 * 充值订单DAO接口
	 */
	@Autowired
	private RechargelogDao rechargelogDao;
	
	
	/**
	 * 交易订单DAO接口
	 */
	@Autowired
	private LqTransactionOrderDao lqTransactionOrderDao;
	
	
	
	/**
	 * 流量订单
	 * @param memberid
	 * @param start
	 * @param end
	 * @return
	 */
	public List<Rechargelog>findRechargelogList(long memberid,long start,long end){
		Map<String,Object>params=new HashMap<String, Object>();
		params.put("memberid", memberid);
		params.put("start", start);
		params.put("end", end);
		List<Rechargelog>rechargelogList=rechargelogDao.findRechargelogList(params);
		
		//失败订单
		params.put("status", TransactionOrderStatus.AlreadyPaid);//已支付订单
		params.put("transactionTypeId", TransactionType.PurchaseFlow);//流量订单
		params.put("interfaceReturnStatus", 0);//失败状态
		params.put("orderStatus", 3);//订单失败
		List<Rechargelog>rechargelogFailList=lqTransactionOrderDao.findRechargelogList(params);
		rechargelogList.addAll(rechargelogFailList);
		
		Collections.sort(rechargelogList);
		return  rechargelogList;
	}
	
	
	/**
	 *  流量充值订单发费总金额
	 * @param memberid
	 * @return
	 */
	public Long findRechargelogTotalMoney(Long memberid){
		return rechargelogDao.findRechargelogTotalMoney(memberid);
	}
	
	/**
	 * 话费订单记录
	 * @param memberid
	 * @param start
	 * @param end
	 * @return
	 */
	public List<Rechargelog>findWxrechargeList(long memberid,long start,long end){
		Map<String,Object>params=new HashMap<String, Object>();
		params.put("memberid", memberid);
		params.put("start", start);
		params.put("end", end);
		List<Rechargelog>rechargelogList= rechargelogDao.findWxrechargeList(params);
		
		
		//失败订单
		params.put("status", TransactionOrderStatus.AlreadyPaid);//已支付订单
		params.put("transactionTypeId", TransactionType.BuyCalls);//话费订单
		params.put("interfaceReturnStatus", 0);//失败状态
		params.put("orderStatus", 3);//订单失败
		List<Rechargelog>rechargelogFailList=lqTransactionOrderDao.findRechargelogList(params);
		rechargelogList.addAll(rechargelogFailList);
		
		Collections.sort(rechargelogList);
				
				
		
		return rechargelogList;
	}
	
	
	/**
	 *  话费充值订单发费总金额
	 * @param memberid
	 * @return
	 */
	public Long findWxrechargeTotalMoney(Long memberid){
		return rechargelogDao.findWxrechargeTotalMoney(memberid);
	}
	
	
	
}
