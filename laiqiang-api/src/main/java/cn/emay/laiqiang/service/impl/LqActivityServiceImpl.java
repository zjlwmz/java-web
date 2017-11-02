package cn.emay.laiqiang.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.emay.laiqiang.bo.LqActivityBO;
import cn.emay.laiqiang.bo.LqMemberBO;
import cn.emay.laiqiang.common.utils.DateUtils;
import cn.emay.laiqiang.common.utils.DoubleUtil;
import cn.emay.laiqiang.common.utils.FormatUtils;
import cn.emay.laiqiang.common.utils.StringUtils;
import cn.emay.laiqiang.dao.SysDao;
import cn.emay.laiqiang.entity.LqAccount;
import cn.emay.laiqiang.entity.LqAccountSeq;
import cn.emay.laiqiang.service.BaseService;
import cn.emay.laiqiang.service.LaiqiangService;
import cn.emay.laiqiang.service.LqAccountService;
import cn.emay.laiqiang.service.LqActivityService;
import cn.emay.laiqiang.service.LqMemberService;
import cn.emay.laiqiang.support.Direction;
import cn.emay.laiqiang.support.JedisKeyUtils;
import cn.emay.laiqiang.support.RewardType;
import cn.emay.laiqiang.support.TransactionType;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @Title ���־service�ӿ�ʵ��
 * @author zjlwm
 * @date 2017-2-15 ����4:58:09
 * 
 */
@Service
public class LqActivityServiceImpl extends BaseService implements LqActivityService {

	@Autowired
	private SysDao sysDao;
	
	/**
	 * �ʽ��˻�servic�ӿ�
	 */
	@Autowired
	private LqAccountService lqAccountService;

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

	/**
	 * ���Ϣ
	 * 
	 * @param id
	 * @return
	 */
	public LqActivityBO get(String id) {
		String lqActivityKey = JedisKeyUtils.getlqActivityById + id;
		String taskInfoJson = jedisStrings.get(lqActivityKey);
		if (StringUtils.isBlank(taskInfoJson)) {
			return null;
		}
		LqActivityBO lqActivityBO=JSON.parseObject(taskInfoJson, LqActivityBO.class);
		if(null!=lqActivityBO){
			String endTime=lqActivityBO.getEndTime();
			if(StringUtils.isNotBlank(endTime)){
				Date endDate=DateUtils.parseDate(endTime);
				Date currentDate=new Date();
				if(endDate.getTime()>currentDate.getTime()){
					return lqActivityBO;
				}else{
					return null;
				}
			}else{
				return null;
			}
		}else{
			return null;
		}
	}

	/**
	 * ��Ƿ�����ȡ��
	 */
	public boolean activityMemberIdReceive(long memberId, String activityId) {
		String activityReceiveKey = JedisKeyUtils.getlqActivityLogByMemberId + memberId;
		boolean result = false;
		try {
			result = jedisSets.sismember(activityReceiveKey, activityId);
		} catch (Exception e) {
		}
		return result;
	}

	
	/**
	 * ��Ƿ�����ȡ��
	 */
	public boolean activityImeiReceive(String imei, String activityId) {
		String activityReceiveKey = JedisKeyUtils.getlqActivityLogByImei + imei;
		boolean result = false;
		try {
			result = jedisSets.sismember(activityReceiveKey, activityId);
		} catch (Exception e) {
		}
		return result;
	}
	
	
	
	@Override
	public void activityReward(long memberId,String imei) {
		String key = JedisKeyUtils.getlqActivityList;
		Set<String> activityList =jedisSortSet.zrange(key, 0, -1);
		if (null != activityList) {
			for (String activityId : activityList) {
				LqActivityBO lqActivityBO = get(activityId);
				if (null != lqActivityBO) {

					/**
					 * �����û�ID ��֤�Ѿ���ȡ��
					 */
					boolean isReceiveByMemberId = activityMemberIdReceive(memberId, activityId);
					
					
					boolean isReceiveByImei=false;
					if(StringUtils.isNotBlank(imei) && !"null".equals(imei)){
						isReceiveByImei=activityImeiReceive(imei, activityId);
					}
					
					
					if (!isReceiveByMemberId && !isReceiveByImei) {
						
						//����
						String activityReceiveKey = JedisKeyUtils.getlqActivityLogByMemberId + memberId;
						jedisSets.sadd(activityReceiveKey, activityId);
						
						
						if(StringUtils.isNotBlank(imei)){
							String activityImeiReceiveKey = JedisKeyUtils.getlqActivityLogByImei + imei;
							jedisSets.sadd(activityImeiReceiveKey, activityId);
						}
						
						/**
						 * ��������(1:������2���ֽ�)
						 */
						int rewardType = lqActivityBO.getRewardType();
						// �������
						Double rewardQuantity = lqActivityBO.getRewardQuantity();

						if (RewardType.cash == rewardType) {
							/**
							 * app��װ��� ��ʼ��ȡ���沿���߼�����
							 * 
							 */
							LqAccount lqAccount = lqAccountService.get(memberId);
							LqAccountSeq lqAccountSeq = new LqAccountSeq();
							lqAccountSeq.setMemberId(memberId);
							lqAccountSeq.setDirection(Direction.enter);
							lqAccountSeq.setTransactionTypeId(TransactionType.DownloadBonus);
							lqAccountSeq.setTransactionAmount(rewardQuantity);
							lqAccountSeq.setPreBalance(lqAccount.getBalance());

							// �˻����
							Double balance = DoubleUtil.add(lqAccount.getBalance(), rewardQuantity);
							// ��double���͵����ֱ�����λС�����������룩
							String balanceStr = FormatUtils.formatNumber(balance);
							double newBlance = Double.parseDouble(balanceStr);

							lqAccountSeq.setBalance(newBlance);

							lqAccountSeq.setCreatedTime(DateUtils.getDateTime());
							lqAccountSeq.setRemarks("�����������棨�״���������App���棩");
							lqAccountSeq.setActivityId(Long.parseLong(activityId));

							/**
							 * �����ʽ��˺�
							 */
							lqAccountService.updateAccount(memberId, lqAccountSeq);

							/**
							 * �����ʽ��˺� ���ؽ��� ����
							 */
							lqAccountService.updateLqAccountTotalCash(memberId, TransactionType.DownloadBonus, rewardQuantity);

						} else if (RewardType.flow == rewardType) {
							LqMemberBO lqMemberBO = lqMemberService.getByMemberId(memberId);
							/**
							 * ���������˺������ӿ�
							 */
							Map<String, String> updateBalanceParams = new HashMap<String, String>();
							updateBalanceParams.put("unionid", lqMemberBO.getUnionid());

							updateBalanceParams.put("flow", rewardQuantity.intValue() + "");
							updateBalanceParams.put("descr", "�����������棨�״���������App���棩");
							updateBalanceParams.put("type", TransactionType.DownloadBonus + "");// ���ؽ���
							updateBalanceParams.put("orderid", activityId);// ��ʱ����

							Map<String, String> updateBalanceResult = laiqiangService.updatememberbalance(updateBalanceParams);
							if (updateBalanceResult.get("status").equals("OK")) {
								/**
								 * �����ʽ��˺� ���ؽ��� ����
								 */
								lqAccountService.updateLqAccountTotalflow(memberId, TransactionType.DownloadBonus, rewardQuantity.intValue());

							}

						}
					}

				} else {
//					jedisSortSet.zrem(key, activityId);
				}
			}
		}
	}

	@Override
	public List<Long> rewardExport() {
		return sysDao.rewardExport();
	}

}
