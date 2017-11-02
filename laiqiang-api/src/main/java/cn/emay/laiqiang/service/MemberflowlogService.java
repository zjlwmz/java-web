package cn.emay.laiqiang.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.emay.laiqiang.common.utils.DateUtils;
import cn.emay.laiqiang.dao.MemberflowlogDao;
import cn.emay.laiqiang.entity.Memberflowlog;

/**
 * 
 * @Title 用户流量消费日志service接口
 * @author zjlwm
 * @date 2016-12-20 下午3:34:16
 *
 */
@Service
@Transactional(readOnly=true)
public class MemberflowlogService {

	/**
	 * 用户流量消费日志DAOj接口
	 *
	 */
	@Autowired
	private MemberflowlogDao memberflowlogDao;
	
	
	/**
	 * 用户流量消费日志分页查询
	 * @param memberId
	 * @param start
	 * @param end
	 * @return
	 */
	public List<Memberflowlog>findMemberflowlog(Long memberId,Long start,Long end){
		Map<String,Object>params=new HashMap<String, Object>();
		params.put("memberId", memberId);
		params.put("start", start);
		params.put("end", end);
		return memberflowlogDao.findMemberflowlogList(params);
	}
	
	
	
	/**
	 * 签到任务奖励次数
	 */
	public Long findTaskSigninRewardCount(Long memberId,String taskId,Long type){
		Map<String,Object>params=new HashMap<String, Object>();
		String date=DateUtils.getDate();
		params.put("memberId", memberId);
		params.put("taskId", taskId);
		params.put("type", type);
		params.put("date", date);
		return memberflowlogDao.findSignInTodayCount(params);
	}
	
	
	/**
	 * 分享奖励
	 */
	public Long findTaskShareRewardCount(Long memberId,String taskId,Long type){
		Map<String,Object>params=new HashMap<String, Object>();
		params.put("memberId", memberId);
		params.put("taskId", taskId);
		params.put("type", type);//分享奖励
		return memberflowlogDao.findTaskShareRewardCount(params);
	}
	
	
}
