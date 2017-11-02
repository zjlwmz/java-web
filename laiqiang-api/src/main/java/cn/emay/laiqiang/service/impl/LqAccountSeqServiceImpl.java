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
 * @Title 资金变动流水service接口实现
 * @author zjlwm
 * @date 2016-12-21 上午9:56:14
 *
 */
@Service
@Transactional(readOnly=true)
public class LqAccountSeqServiceImpl implements LqAccountSeqService {

	/**
	 * 
	 * 资金变动流水表DAO接口
	 *
	 */
	@Autowired
	private LqAccountSeqDao lqAccountSeqDao;

	/**
	 * 分享奖励
	 */
	public Long findTaskShareRewardCount(Long memberId,String taskId,Long type){
		Map<String,Object>params=new HashMap<String, Object>();
		params.put("memberId", memberId);
		params.put("taskId", taskId);
		params.put("type", type);
		return lqAccountSeqDao.findTaskRewardCount(params);
	}

	
	/**
	 * 签到任务 奖励次数
	 */
	@Override
	public Long findTaskSigninRewardCount(Long memberId, String taskId,Long type) {
		Map<String,Object>params=new HashMap<String, Object>();
		String date=DateUtils.getDate();
		params.put("memberId", memberId);
		params.put("taskId", taskId);
		params.put("type", type);//TransactionType.SigninReward 签到奖励
		params.put("date", date);
		return lqAccountSeqDao.findSignInTodayCount(params);
	}


	/**
	 * 资金账号分页查询
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
