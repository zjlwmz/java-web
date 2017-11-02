package cn.emay.laiqiang.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.emay.laiqiang.bo.LqMemberBO;
import cn.emay.laiqiang.bo.MemberBO;
import cn.emay.laiqiang.common.utils.DoubleUtil;
import cn.emay.laiqiang.dao.LqDrawCommissionLogDao;
import cn.emay.laiqiang.dto.InviteFriendsDTO;
import cn.emay.laiqiang.entity.LqAccount;
import cn.emay.laiqiang.entity.LqDrawCommissionLog;
import cn.emay.laiqiang.service.LqAccountService;
import cn.emay.laiqiang.service.LqDrawCommissionLogService;
import cn.emay.laiqiang.service.LqMemberService;
import cn.emay.laiqiang.service.MemberService;
import cn.emay.laiqiang.support.RewardType;

/**
 * 
 * @Title �����־ service�ӿ�ʵ��
 * @author zjlwm
 * @date 2016-12-21 ����9:36:23
 *
 */
@Service
@Transactional(readOnly=true)
public class LqDrawCommissionLogServiceImpl implements LqDrawCommissionLogService {

	/**
	 * �����־DAO�ӿ�
	 */
	@Autowired
	private LqDrawCommissionLogDao lqDrawCommissionLogDao;
	
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
	 * wx�û�service�ӿ�
	 */
	@Autowired
	private MemberService memberService;
	
	
//	@Transactional(readOnly=false)
//	@Override
//	public int insert(LqDrawCommissionLog lqDrawCommissionLog) {
//		return lqDrawCommissionLogDao.insert(lqDrawCommissionLog);
//	}

	
	/**
	 * ��ҳ��ѯ
	 */
	@Override
	public List<InviteFriendsDTO> findLqDrawCommissionLogList(Long memberId, Long start, Long end) {
		List<InviteFriendsDTO>inviteFriendsDTOList=new ArrayList<InviteFriendsDTO>();
		Map<String,Object>params=new HashMap<String, Object>();
		params.put("memberId", memberId);
		params.put("start", start);
		params.put("end", end);
		List<LqDrawCommissionLog>lqDrawCommissionLogList= lqDrawCommissionLogDao.findLqDrawCommissionLogList(params);
		for(LqDrawCommissionLog lqDrawCommissionLog:lqDrawCommissionLogList){
			InviteFriendsDTO inviteFriendsDTO=new InviteFriendsDTO();
			int rewardType=lqDrawCommissionLog.getRewardType();
			Double drawCommission=lqDrawCommissionLog.getDrawCommission();
			if(rewardType==RewardType.cash){
				inviteFriendsDTO.setMoney(drawCommission);
			}else{
				inviteFriendsDTO.setFlow(drawCommission.intValue());
			}
			Long invitee=lqDrawCommissionLog.getInvitee();
			LqMemberBO lqMemberBO=lqMemberService.getByMemberId(invitee);
			String uuid=lqMemberBO.getUuid();
			MemberBO memberBO =memberService.getMember(uuid);
			String wxname=memberBO.getWxname();
			String headimgurl=memberBO.getHeadimgurl();
			inviteFriendsDTO.setWxname(wxname);
			inviteFriendsDTO.setHeadimgurl(headimgurl);
			
			inviteFriendsDTO.setCreateDate(lqDrawCommissionLog.getCreatedTime());
			inviteFriendsDTOList.add(inviteFriendsDTO);
		}
		return inviteFriendsDTOList;
	}

	@Override
	public Double getTotalFlowRate(Long memberId) {
		LqAccount lqAccount = lqAccountService.get(memberId);
		// �Ƽ�����
		Integer totalflow102 = lqAccount.getTotalflow102();
		if (null == totalflow102) {
			totalflow102=0;
		}
		
		// ��������
		Integer totalflow103 = lqAccount.getTotalflow103();
		if (null == totalflow103) {
			totalflow103=0;
		}
		
		
		return DoubleUtil.add(totalflow102, totalflow103);
		
//		Map<String,Object>params=new HashMap<String, Object>();
//		params.put("memberId", memberId);
//		params.put("rewardType", RewardType.flow);
//		Double grossIncome= lqDrawCommissionLogDao.getGrossIncome(params);
//		
//		/**
//		 * �����Ƽ�����
//		 */
//		params.put("type", TransactionType.LanewAward);
//		Long recommendedTotalFlow=memberflowlogDao.getRecommendedTotalFlow(params);
//		
//		return DoubleUtil.add(grossIncome, recommendedTotalFlow);
		
		
	}

	
	
	
	
	@Override
	public Double getTotalAmountOfIncome(Long memberId) {
		
		
		LqAccount lqAccount = lqAccountService.get(memberId);
		// �Ƽ�����
		Double totalcash102 = lqAccount.getTotalcash102();
		if (null == totalcash102) {
			totalcash102=0.0;
		}
		
		// ��������
		Double totalcash103 = lqAccount.getTotalcash103();
		if (null == totalcash103) {
			totalcash103=0.0;
		}
		
		
		return DoubleUtil.add(totalcash102, totalcash103);
		
		
//		Map<String,Object>params=new HashMap<String, Object>();
//		params.put("memberId", memberId);
//		params.put("rewardType", RewardType.cash);
//		Double grossIncome= lqDrawCommissionLogDao.getGrossIncome(params);
//		
//		/**
//		 * �ֽ��Ƽ�����
//		 */
//		params.put("transactionTypeId", TransactionType.LanewAward);
//		Double recommendedTotalCash=lqAccountSeqDao.getRecommendedTotalCash(params);
//		
//		return DoubleUtil.add(grossIncome,recommendedTotalCash);
		
		
		
	}
	
	

}
