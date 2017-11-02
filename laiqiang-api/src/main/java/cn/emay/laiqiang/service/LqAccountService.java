package cn.emay.laiqiang.service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.emay.laiqiang.bo.LqMemberBO;
import cn.emay.laiqiang.common.utils.DateUtils;
import cn.emay.laiqiang.common.utils.DoubleUtil;
import cn.emay.laiqiang.common.utils.FormatUtils;
import cn.emay.laiqiang.common.utils.RedisUtil;
import cn.emay.laiqiang.common.utils.StringUtils;
import cn.emay.laiqiang.dao.LqAccountDao;
import cn.emay.laiqiang.dao.LqAccountSeqDao;
import cn.emay.laiqiang.dao.LqTransactionOrderDao;
import cn.emay.laiqiang.entity.LqAccount;
import cn.emay.laiqiang.entity.LqAccountSeq;
import cn.emay.laiqiang.entity.LqTransactionOrder;
import cn.emay.laiqiang.support.Direction;
import cn.emay.laiqiang.support.JedisKeyUtils;
import cn.emay.laiqiang.support.RewardType;
import cn.emay.laiqiang.support.TransactionOrderStatus;
import cn.emay.laiqiang.support.TransactionType;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @Title ��Ա����˺�service�ӿ�
 * @author zjlwm
 * @date 2016-12-16 ����1:16:04
 *
 */
@Service
@Transactional(readOnly=true)
public class LqAccountService extends BaseService{

	private static Logger logger = Logger.getLogger(LqAccountService.class.getName());
	
	/**
	 * д����redisDAO
	 */
	@Autowired
	private RedisUtil redisUtil;
	
	
	/**
	 * ��Ա�˺�DAO�ӿ�
	 */
	@Autowired
	private LqAccountDao lqAccountDao;
	
	
	/**
	 * ���׶���DAO�ӿ�
	 */
	@Autowired
	private LqTransactionOrderDao lqTransactionOrderDao;
	
	
	
	/**
	 * �ʽ�䶯��ˮ��DAO�ӿ�
	 * 
	 */
	@Autowired
	private LqAccountSeqDao lqAccountSeqDao;
	
	
	/**
	 * app��Աservice�ӿ�
	 */
	@Autowired
	private LqMemberService lqMemberService;
	
	
	
	/**
	 * ����service�ӿ�
	 */
	@Autowired
	private LaiqiangService laiqiangService;
	
	@Autowired
	private LqTaskService lqTaskService;
	
	/**
	 * app�ʽ��˺���Ϣ
	 */
	public LqAccount get(Long memberId){
		LqAccount lqAccount=null;
		/**
		 * app�ʽ��˺���Ϣ
		 */
		String lqAccountKey=JedisKeyUtils.getLqAccountByMemberId+memberId;
		String lqAccountInfo=jedisStrings.get(lqAccountKey);
		if(StringUtils.isNotBlank(lqAccountInfo)){
			lqAccount=JSON.parseObject(lqAccountInfo, LqAccount.class);
		}else{
			lqAccount=lqAccountDao.getLqAccount(memberId);
			if(null!=lqAccount){
				jedisStrings.set(lqAccountKey, JSON.toJSONString(lqAccount));
			}
		}
		return lqAccount;
	}
	
	
	/**
	 * �ʽ��˺���������
	 * @param transactionype
	 * @param flow
	 */
	public void updateLqAccountTotalflow(Long memberId,long transactionype,Integer flow){
		
		LqAccount lqAccount= get(memberId);
		StringBuffer sqlBuff=new StringBuffer();
		sqlBuff.append("update lq_account set ");
		
		if(transactionype==101){
			Integer totalflow101=lqAccount.getTotalflow101();
			if(null==totalflow101){
				totalflow101=0;
				sqlBuff.append("totalflow101 = "+flow);
			}else{
				sqlBuff.append("totalflow101 = totalflow101+"+flow);
			}
			lqAccount.setTotalflow101(totalflow101+flow);
		}
		else if(transactionype==102){
			
			Integer totalflow102=lqAccount.getTotalflow102();
			if(null==totalflow102){
				totalflow102=0;
				sqlBuff.append("totalflow102 = "+flow);
			}else{
				sqlBuff.append("totalflow102 = totalflow102+"+flow);
			}
			lqAccount.setTotalflow102(totalflow102+flow);
		}
		else if(transactionype==103){
			
			Integer totalflow103=lqAccount.getTotalflow103();
			if(null==totalflow103){
				totalflow103=0;
				sqlBuff.append("totalflow103 = "+flow);
			}else{
				sqlBuff.append("totalflow103 = totalflow103+"+flow);
			}
			lqAccount.setTotalflow103(totalflow103+flow);
		}
		else if(transactionype==104){
			
			Integer totalflow104=lqAccount.getTotalflow104();
			if(null==totalflow104){
				totalflow104=0;
				sqlBuff.append("totalflow104 = "+flow);
			}else{
				sqlBuff.append("totalflow104 = totalflow104+"+flow);
			}
			lqAccount.setTotalflow104(totalflow104+flow);
		}
		else if(transactionype==105){
			Integer totalflow105=lqAccount.getTotalflow105();
			if(null==totalflow105){
				totalflow105=0;
				sqlBuff.append("totalflow105 = "+flow);
			}else{
				sqlBuff.append("totalflow105 = totalflow105+"+flow);
			}
			lqAccount.setTotalflow105(totalflow105+flow);
		}else if(transactionype==109){
			Integer totalflow109=lqAccount.getTotalflow109();
			if(null==totalflow109){
				totalflow109=0;
				sqlBuff.append("totalflow109 = "+flow);
			}else{
				sqlBuff.append("totalflow109 = totalflow109+"+flow);
			}
			lqAccount.setTotalflow109(totalflow109+flow);
		}else{
			return;
		}
		
		
		
		sqlBuff.append(" where member_id=").append(memberId);
		
		String updateSql=sqlBuff.toString();
		/**
		 * ����sql
		 */
		redisUtil.rpush(JedisKeyUtils.mysqlExecuteSqL, updateSql);
		
		
		String lqAccountKey=JedisKeyUtils.getLqAccountByMemberId+memberId;
		jedisStrings.set(lqAccountKey, JSON.toJSONString(lqAccount));
		
		
		
		
		/**
		 * ��������ͳ��
		 */
		update7daysAccountIncomeFlow(String.valueOf(memberId), flow);
		
	}
	
	
	
	
	/**
	 * �ʽ��˺Ž�����
	 * @param transactionype
	 * @param flow
	 */
	public void updateLqAccountTotalCash(Long memberId,long transactionype,Double cash){
		
		LqAccount lqAccount= get(memberId);
		StringBuffer sqlBuff=new StringBuffer();
		sqlBuff.append("update lq_account set ");
		
		if(transactionype==101){
			
			Double totalcash101=lqAccount.getTotalcash101();
			if(null==totalcash101){
				totalcash101=0d;
				sqlBuff.append("totalcash101 = "+cash);
			}else{
				sqlBuff.append("totalcash101 = totalcash101+"+cash);
			}
			
			//������
			Double totalRewardNew=DoubleUtil.add(totalcash101, cash);
			//��double���͵����ֱ�����λС�����������룩
			String totalRewardNewStr=FormatUtils.formatNumber(totalRewardNew);
			double totalRewardNew2=Double.parseDouble(totalRewardNewStr);
			
			lqAccount.setTotalcash101(totalRewardNew2);
		}
		else if(transactionype==102){
			
			Double totalcash102=lqAccount.getTotalcash102();
			if(null==totalcash102){
				totalcash102=0d;
				sqlBuff.append("totalcash102 = "+cash);
			}else{
				sqlBuff.append("totalcash102 = totalflow102+"+cash);
			}
			
			//������
			Double totalRewardNew=DoubleUtil.add(totalcash102, cash);
			//��double���͵����ֱ�����λС�����������룩
			String totalRewardNewStr=FormatUtils.formatNumber(totalRewardNew);
			double totalRewardNew2=Double.parseDouble(totalRewardNewStr);
			
			
			lqAccount.setTotalcash102(totalRewardNew2);
		}
		else if(transactionype==103){
			
			Double totalcash103=lqAccount.getTotalcash103();
			if(null==totalcash103){
				totalcash103=0d;
				sqlBuff.append("totalcash103 = "+cash);
			}else{
				sqlBuff.append("totalcash103 = totalcash103+"+cash);
			}
			
			//������
			Double totalRewardNew=DoubleUtil.add(totalcash103, cash);
			//��double���͵����ֱ�����λС�����������룩
			String totalRewardNewStr=FormatUtils.formatNumber(totalRewardNew);
			double totalRewardNew2=Double.parseDouble(totalRewardNewStr);
			
			
			lqAccount.setTotalcash103(totalRewardNew2);
		}
		else if(transactionype==104){
			
			Double totalcash104=lqAccount.getTotalcash104();
			if(null==totalcash104){
				totalcash104=0d;
				sqlBuff.append("totalcash104 = "+cash);
			}else{
				sqlBuff.append("totalcash104 = totalcash104+"+cash);
			}
			
			//������
			Double totalRewardNew=DoubleUtil.add(totalcash104, cash);
			//��double���͵����ֱ�����λС�����������룩
			String totalRewardNewStr=FormatUtils.formatNumber(totalRewardNew);
			double totalRewardNew2=Double.parseDouble(totalRewardNewStr);
			
			lqAccount.setTotalcash104(totalRewardNew2);
		}
		else if(transactionype==105){
			
			Double totalcash105=lqAccount.getTotalcash105();
			if(null==totalcash105){
				totalcash105=0d;
				sqlBuff.append("totalcash105 = "+cash);
			}else{
				sqlBuff.append("totalcash105 = totalcash105+"+cash);
			}
			
			//������
			Double totalRewardNew=DoubleUtil.add(totalcash105, cash);
			//��double���͵����ֱ�����λС�����������룩
			String totalRewardNewStr=FormatUtils.formatNumber(totalRewardNew);
			double totalRewardNew2=Double.parseDouble(totalRewardNewStr);
			
			lqAccount.setTotalcash105(totalRewardNew2);
		}else if(transactionype==109){
			
			Double totalcash109=lqAccount.getTotalcash109();
			if(null==totalcash109){
				totalcash109=0d;
				sqlBuff.append("totalcash109 = "+cash);
			}else{
				sqlBuff.append("totalcash109 = totalcash109+"+cash);
			}
			
			//������
			Double totalRewardNew=DoubleUtil.add(totalcash109, cash);
			//��double���͵����ֱ�����λС�����������룩
			String totalRewardNewStr=FormatUtils.formatNumber(totalRewardNew);
			double totalRewardNew2=Double.parseDouble(totalRewardNewStr);
			
			
			lqAccount.setTotalcash109(totalRewardNew2);
		}else{
			return;
		}
		
		sqlBuff.append(" where member_id=").append(memberId);
		
		String updateSql=sqlBuff.toString();
		/**
		 * ����sql
		 */
		redisUtil.rpush(JedisKeyUtils.mysqlExecuteSqL, updateSql);
		
		
		String lqAccountKey=JedisKeyUtils.getLqAccountByMemberId+memberId;
		jedisStrings.set(lqAccountKey, JSON.toJSONString(lqAccount));
		
		
		
		
		
		/**
		 * �ֽ�����ͳ��
		 */
		update7daysAccountIncomeCash(String.valueOf(memberId), cash);
	}
	

	/**
	 * �����������������
	 */
	public void update7daysAccountIncomeFlow(String memberId,Integer flow){
		int seconds=3600*24*7;//7��
		String get7daysAccountIncomeFlowKey=JedisKeyUtils.get7daysAccountIncomeFlow;
		get7daysAccountIncomeFlowKey=get7daysAccountIncomeFlowKey.replace("{0}", memberId);
		String today=DateUtils.getDate("yyyyMMdd");
		get7daysAccountIncomeFlowKey=get7daysAccountIncomeFlowKey.replace("{1}", today);
		
		String get7daysAccountIncomeFlow=jedisStrings.get(get7daysAccountIncomeFlowKey);
		if(null!=get7daysAccountIncomeFlow){
			Integer totalFlow=Integer.parseInt(get7daysAccountIncomeFlow);
			totalFlow+=flow;
			jedisStrings.setEx(get7daysAccountIncomeFlowKey,seconds, totalFlow+"");
		}else{
			jedisStrings.setEx(get7daysAccountIncomeFlowKey, seconds, flow+"");
		}
	}
	
	
	
	/**
	 * ��ȡ���7����������
	 */
	public Integer[] get7daysAccountIncomeFlow(String memberId){
		Integer flow[]=new Integer[7];
		int day=-6;
		for(int i=0;i<7;i++){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.DATE, day);
			Date newDate=calendar.getTime();
			String beforeDay=DateUtils.formatDate(newDate, "yyyyMMdd");
			String get7daysAccountIncomeFlowKey=JedisKeyUtils.get7daysAccountIncomeFlow;
			get7daysAccountIncomeFlowKey=get7daysAccountIncomeFlowKey.replace("{0}", memberId);
			get7daysAccountIncomeFlowKey=get7daysAccountIncomeFlowKey.replace("{1}", beforeDay);
			String get7daysAccountIncomeFlow=jedisStrings.get(get7daysAccountIncomeFlowKey);
			if(null!=get7daysAccountIncomeFlow){
				Integer totalFlow=Integer.parseInt(get7daysAccountIncomeFlow);
				flow[i]=totalFlow;
			}else{
				flow[i]=0;
			}
			day++;
		}
		
		return flow;
	}
	
	
	/**
	 * ���������������
	 */
	public void update7daysAccountIncomeCash(String memberId,Double cash){
		int seconds=3600*24*7;//7��
		String get7daysAccountIncomeCashKey=JedisKeyUtils.get7daysAccountIncomeCash;
		get7daysAccountIncomeCashKey=get7daysAccountIncomeCashKey.replace("{0}", memberId);
		String today=DateUtils.getDate("yyyyMMdd");
		get7daysAccountIncomeCashKey=get7daysAccountIncomeCashKey.replace("{1}", today);
		
		String get7daysAccountIncomeCash=jedisStrings.get(get7daysAccountIncomeCashKey);
		if(null!=get7daysAccountIncomeCash){
			Double totalCash=Double.parseDouble(get7daysAccountIncomeCash);
			totalCash+=cash;
			jedisStrings.setEx(get7daysAccountIncomeCashKey,seconds, totalCash+"");
		}else{
			jedisStrings.setEx(get7daysAccountIncomeCashKey,seconds, cash+"");
		}
	}
	
	
	
	/**
	 * ��ȡ���7��������
	 */
	public double[] get7daysAccountIncomeCash(String memberId){
		double cash[]=new double[7];
		int day=-6;
		for(int i=0;i<7;i++){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.DATE, day);
			Date newDate=calendar.getTime();
			String beforeDay=DateUtils.formatDate(newDate, "yyyyMMdd");
			String get7daysAccountIncomeCashKey=JedisKeyUtils.get7daysAccountIncomeCash;
			get7daysAccountIncomeCashKey=get7daysAccountIncomeCashKey.replace("{0}", memberId);
			get7daysAccountIncomeCashKey=get7daysAccountIncomeCashKey.replace("{1}", beforeDay);
			
			String get7daysAccountIncomeCash=jedisStrings.get(get7daysAccountIncomeCashKey);
			if(null!=get7daysAccountIncomeCash){
				double totalCash=Double.parseDouble(get7daysAccountIncomeCash);
				cash[i]=totalCash;
			}else{
				cash[i]=0d;
			}
			day++;
		}
		
		return cash;
	}
	
	
	
	
	/**
	 *  �˺��ʽ����֧��
	 *  ����֧������
	 */
	@Transactional(readOnly=false)
	public boolean callbackRecharge(String transactionOrderNo, String transactionNo, Integer paymentId){
		LqTransactionOrder lqTransactionOrder=lqTransactionOrderDao.findLqTransactionOrderByNo(transactionOrderNo);
		
		if(lqTransactionOrder.getStatus().equals(TransactionOrderStatus.AlreadyPaid)){
			return false;
		}
		lqTransactionOrder.setStatus(TransactionOrderStatus.AlreadyPaid);
		
		lqTransactionOrder.setPaymentId(paymentId);
		lqTransactionOrderDao.update(lqTransactionOrder);
		
		
		/**
		 * �ʽ�䶯��ˮ��
		 */
		LqAccountSeq lqAccountSeq=new LqAccountSeq();
		Long memberId=lqTransactionOrder.getMemberId();
//		LqAccount lqAccount=lqAccountDao.getLqAccount(memberId);
		LqAccount lqAccount=get(memberId);
				
				
				
				
		lqAccountSeq.setMemberId(lqTransactionOrder.getMemberId());
		lqAccountSeq.setDirection(Direction.out);//��������1���룻-1������0���ڲ���ת��
		lqAccountSeq.setTransactionOrderId(lqTransactionOrder.getId());
		lqAccountSeq.setTransactionTypeId(lqTransactionOrder.getTransactionTypeId());
		lqAccountSeq.setTransactionAmount(lqTransactionOrder.getAmount());
		lqAccountSeq.setPaymentId(paymentId);
		lqAccountSeq.setPreBalance(lqAccount.getBalance());//�仯ǰ���
		lqAccountSeq.setBalance(lqAccountSeq.getPreBalance()-lqAccountSeq.getTransactionAmount());
		lqAccountSeq.setCreatedTime(DateUtils.getDateTime());
		lqAccountSeq.setRemarks("");
		lqAccountSeqDao.insert(lqAccountSeq);
		
		//�˺Ž��
		lqAccount.setBalance(lqAccountSeq.getBalance());
		lqAccount.setUpdatedTime(DateUtils.getDateTime());
		lqAccountDao.update(lqAccount);
		
		
		
		
		
		/**
		 * ����
		 * 
		 */
		String lqAccountKey=JedisKeyUtils.getLqAccountByMemberId+memberId;
		jedisStrings.set(lqAccountKey, JSON.toJSONString(lqAccount));
		
		
		return true;
	}
	
	
	
	
	
	/**
	 * �ڲ��������ʽ��˺�
	 */
	@Transactional(readOnly=false)
	public void updateAccount(Long memberId,LqAccountSeq lqAccountSeq){
		/**
		 * �ʽ�䶯��ˮ��
		 */
		LqAccount lqAccount=get(memberId);
		
		lqAccount.setBalance(lqAccountSeq.getBalance());
		
		lqAccountDao.update(lqAccount);
		lqAccountSeqDao.insert(lqAccountSeq);
		
		
		/**
		 * ����
		 * 
		 */
		String lqAccountKey=JedisKeyUtils.getLqAccountByMemberId+memberId;
		jedisStrings.set(lqAccountKey, JSON.toJSONString(lqAccount));
	}
	
	
	
	/**
	 * ������������
	 * ��Ա�������������
	 * @param memberId
	 * @param transactionype
	 * @param taskId
	 * @param flow
	 */
	public void updateMemberFlowCommission(Long memberId,Long transactionype,String taskId,Integer flow){
		try{
			LqMemberBO lqMemberBO =lqMemberService.getByMemberId(memberId);
			if(lqMemberBO==null)return;
			
			/**
			 * ������Id
			 */
			Long  inviter=lqMemberBO.getInviter();
			if(null==inviter)return;
			LqMemberBO inviterLqMemberBO =lqMemberService.getByMemberId(inviter);
			
			/**
			 * ���������˺������ӿ�
			 */
			Map<String,String>updateBalanceParams=new HashMap<String, String>();
			updateBalanceParams.put("unionid", inviterLqMemberBO.getUnionid());
			updateBalanceParams.put("flow", flow+"");
			updateBalanceParams.put("descr", "��������");
			updateBalanceParams.put("type", TransactionType.RebateIncome+"");//
			updateBalanceParams.put("orderid", taskId);//����id
			
			Map<String,String>updateBalanceResult=laiqiangService.updatememberbalance(updateBalanceParams);
			if(updateBalanceResult.get("status").equals("OK")){
				/**
				 * �����ʽ��˺� ������������ ����
				 */
				updateLqAccountTotalflow(inviter, TransactionType.RebateIncome, flow);
				
				
				//����������
				lqTaskService.updateLqTaskTotalflow(taskId, TransactionType.DownloadBonus, flow);
				
				/**
				 * �����־��¼
				 * 
				 */
				StringBuffer drawCommissionLogSql=new StringBuffer();
				drawCommissionLogSql.append("insert into lq_draw_commission_log(task_id,transaction_type_id,inviter,invitee,draw_commission,reward_type,created_time) values(");
				drawCommissionLogSql.append(taskId).append(",");
				drawCommissionLogSql.append(transactionype).append(",");
				drawCommissionLogSql.append(inviter).append(",");//������
				drawCommissionLogSql.append(memberId).append(",");//������
				drawCommissionLogSql.append(flow).append(",");
				drawCommissionLogSql.append(RewardType.flow).append(",");
				drawCommissionLogSql.append("'").append(DateUtils.getDateTime()).append("'");
				drawCommissionLogSql.append(")");
				
				/**
				 * �����־��¼ ����
				 */
				String insertSql=drawCommissionLogSql.toString();
				redisUtil.rpush(JedisKeyUtils.mysqlExecuteSqL, insertSql);
			}else{
				String message=updateBalanceResult.get("message");
				logger.error(message);
			}
		}catch (Exception e) {
			logger.error("��Ա�������������", e);
		}
		
	}
	
	
	
	
	/**
	 * ������������
	 * ��Ա�����˽�����
	 * @param memberId
	 * @param transactionype ��������id
	 * @param taskId		 ����ID
	 * @param cash			�������
	 */
	public void updateMemberCashCommission(Long memberId,Long transactionype,String taskId,double cash){
		try{
			LqMemberBO lqMemberBO =lqMemberService.getByMemberId(memberId);
			if(lqMemberBO==null)return;
			
			/**
			 * ������Id
			 */
			Long  inviter=lqMemberBO.getInviter();
			if(null==inviter)return;
			
			/**
			 * �ʽ���ˮ
			 */
			LqAccount lqAccount=get(inviter);
			LqAccountSeq lqAccountSeq=new LqAccountSeq();
			lqAccountSeq.setMemberId(inviter);
			lqAccountSeq.setDirection(Direction.enter);
			lqAccountSeq.setTransactionTypeId(TransactionType.RebateIncome);
			lqAccountSeq.setTransactionAmount(cash);
			lqAccountSeq.setPreBalance(lqAccount.getBalance());
			
			//�˻����
			double balance=DoubleUtil.add(lqAccount.getBalance(), cash);
			////��double���͵����ֱ�����λС�����������룩
			String balanceStr=FormatUtils.formatNumber(balance);
			double newBlance=Double.parseDouble(balanceStr);
			
			
			lqAccountSeq.setBalance(newBlance);
			lqAccountSeq.setCreatedTime(DateUtils.getDateTime());
			lqAccountSeq.setRemarks("��������");
			lqAccountSeq.setTaskId(Long.parseLong(taskId));
			
			/**
			 * �����ʽ��˺�
			 */
			updateAccount(inviter, lqAccountSeq);
			
			
			/**
			 * �����ʽ��˺� ������������ ����
			 */
			updateLqAccountTotalCash(inviter, TransactionType.RebateIncome, cash);
			
			
			
			//����������
			lqTaskService.updateLqTaskTotalCash(taskId, TransactionType.RebateIncome, cash);
			
			
			/**
			 * �����־��¼
			 * 
			 */
			StringBuffer drawCommissionLogSql=new StringBuffer();
			drawCommissionLogSql.append("insert into lq_draw_commission_log(task_id,transaction_type_id,inviter,invitee,draw_commission,reward_type,created_time) values(");
			drawCommissionLogSql.append(taskId).append(",");
			drawCommissionLogSql.append(transactionype).append(",");
			drawCommissionLogSql.append(inviter).append(",");//������
			drawCommissionLogSql.append(memberId).append(",");//������
			drawCommissionLogSql.append(cash).append(",");
			drawCommissionLogSql.append(RewardType.cash).append(",");
			drawCommissionLogSql.append("'").append(DateUtils.getDateTime()).append("'");
			drawCommissionLogSql.append(")");
			
			/**
			 * �����־��¼ ����
			 */
			String insertSql=drawCommissionLogSql.toString();
			redisUtil.rpush(JedisKeyUtils.mysqlExecuteSqL, insertSql);
		}catch (Exception e) {
			logger.error("��Ա�����˽�����", e);
		}
		
	}
}
