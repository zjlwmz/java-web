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
 * @Title ��ֵ����service�ӿ�
 * @author zjlwm
 * @date 2016-12-16 ����9:30:27
 *
 */
@Service
public class RechargelogService {

	/**
	 * ��ֵ����DAO�ӿ�
	 */
	@Autowired
	private RechargelogDao rechargelogDao;
	
	
	/**
	 * ���׶���DAO�ӿ�
	 */
	@Autowired
	private LqTransactionOrderDao lqTransactionOrderDao;
	
	
	
	/**
	 * ��������
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
		
		//ʧ�ܶ���
		params.put("status", TransactionOrderStatus.AlreadyPaid);//��֧������
		params.put("transactionTypeId", TransactionType.PurchaseFlow);//��������
		params.put("interfaceReturnStatus", 0);//ʧ��״̬
		params.put("orderStatus", 3);//����ʧ��
		List<Rechargelog>rechargelogFailList=lqTransactionOrderDao.findRechargelogList(params);
		rechargelogList.addAll(rechargelogFailList);
		
		Collections.sort(rechargelogList);
		return  rechargelogList;
	}
	
	
	/**
	 *  ������ֵ���������ܽ��
	 * @param memberid
	 * @return
	 */
	public Long findRechargelogTotalMoney(Long memberid){
		return rechargelogDao.findRechargelogTotalMoney(memberid);
	}
	
	/**
	 * ���Ѷ�����¼
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
		
		
		//ʧ�ܶ���
		params.put("status", TransactionOrderStatus.AlreadyPaid);//��֧������
		params.put("transactionTypeId", TransactionType.BuyCalls);//���Ѷ���
		params.put("interfaceReturnStatus", 0);//ʧ��״̬
		params.put("orderStatus", 3);//����ʧ��
		List<Rechargelog>rechargelogFailList=lqTransactionOrderDao.findRechargelogList(params);
		rechargelogList.addAll(rechargelogFailList);
		
		Collections.sort(rechargelogList);
				
				
		
		return rechargelogList;
	}
	
	
	/**
	 *  ���ѳ�ֵ���������ܽ��
	 * @param memberid
	 * @return
	 */
	public Long findWxrechargeTotalMoney(Long memberid){
		return rechargelogDao.findWxrechargeTotalMoney(memberid);
	}
	
	
	
}
