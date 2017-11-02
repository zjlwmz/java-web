package cn.emay.laiqiang.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.emay.laiqiang.common.utils.DateUtils;
import cn.emay.laiqiang.dao.LqAccountSeqDao;
import cn.emay.laiqiang.entity.LqAccountSeq;
import cn.emay.laiqiang.service.LqAccountSeqService;


/**
 * 
 * @Title �ʽ�䶯��ˮservice�ӿ�ʵ��
 * @author zjlwm
 * @date 2016-12-21 ����9:56:14
 *
 */
@Service
@Transactional(readOnly=true)
public class LqAccountSeqServiceImpl implements LqAccountSeqService {

	/**
	 * 
	 * �ʽ�䶯��ˮ��DAO�ӿ�
	 *
	 */
	@Autowired
	private LqAccountSeqDao lqAccountSeqDao;

	/**
	 * ������
	 */
	public Long findTaskShareRewardCount(Long memberId,String taskId,Long type){
		Map<String,Object>params=new HashMap<String, Object>();
		params.put("memberId", memberId);
		params.put("taskId", taskId);
		params.put("type", type);
		return lqAccountSeqDao.findTaskRewardCount(params);
	}

	
	/**
	 * ǩ������ ��������
	 */
	@Override
	public Long findTaskSigninRewardCount(Long memberId, String taskId,Long type) {
		Map<String,Object>params=new HashMap<String, Object>();
		String date=DateUtils.getDate();
		params.put("memberId", memberId);
		params.put("taskId", taskId);
		params.put("type", type);//TransactionType.SigninReward ǩ������
		params.put("date", date);
		return lqAccountSeqDao.findSignInTodayCount(params);
	}


	/**
	 * �ʽ��˺ŷ�ҳ��ѯ
	 */
	@Override
	public List<LqAccountSeq> findLqAccountSeqList(Long memberId, Long start, Long end) {
		Map<String,Object>params=new HashMap<String, Object>();
		params.put("memberId", memberId);
		params.put("start", start);
		params.put("end", end);
		return lqAccountSeqDao.findLqAccountSeqList(params);
	}
	
	
}
