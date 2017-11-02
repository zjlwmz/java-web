package cn.emay.laiqiang.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.emay.laiqiang.bo.MemberBO;
import cn.emay.laiqiang.dao.LqInviteLogDao;
import cn.emay.laiqiang.dao.MemberDao;
import cn.emay.laiqiang.dto.InviteFriendsDTO;
import cn.emay.laiqiang.entity.LqInviteLog;
import cn.emay.laiqiang.service.LqInviteLogService;


/**
 * 
 * @Title 邀请日志service接口实现
 * @author zjlwm
 * @date 2016-12-20 下午4:46:34
 *
 */
@Service
@Transactional(readOnly=true)
public class LqInviteLogServiceImpl implements LqInviteLogService {

	
	/**
	 * 邀请日志DAO接口
	 */
	@Autowired
	private LqInviteLogDao lqInviteLogDao;
	
	@Autowired
	private MemberDao memberDao;
	
	
	
	@Override
	@Transactional(readOnly=false)
	public int insert(LqInviteLog lqInviteLog) {
		return lqInviteLogDao.insert(lqInviteLog);
	}
	
	
	/**
	 * 邀请日志分页查询
	 * @param memberId
	 * @param start
	 * @param end
	 * @return
	 */
	public List<InviteFriendsDTO>findLqInvitelogList(Long memberId,Long start,Long end){
		List<InviteFriendsDTO>inviteFriendsList=new ArrayList<InviteFriendsDTO>();
		
		
		Map<String,Object>params=new HashMap<String, Object>();
		params.put("memberId", memberId);
		params.put("start", start);
		params.put("end", end);
		List<MemberBO>memberBOList=memberDao.findMemberBOByMemberId(params);
		for(MemberBO memberBO:memberBOList){
			InviteFriendsDTO inviteFriends=new InviteFriendsDTO();
			inviteFriends.setWxname(memberBO.getWxname());
			inviteFriends.setHeadimgurl(memberBO.getHeadimgurl());
			inviteFriendsList.add(inviteFriends);
			
		}
		
//		List<LqInviteLog>lqInviteLogList= lqInviteLogDao.findLqInvitelogList(params);
//		for(LqInviteLog lqInviteLog:lqInviteLogList){
//			Integer rewardType =lqInviteLog.getRewardType();
//			InviteFriendsDTO inviteFriends=new InviteFriendsDTO();
//			if(rewardType==RewardType.flow){
//				inviteFriends.setFlow(lqInviteLog.getInviterCommission().intValue());
//			}else{
//				inviteFriends.setMoney(lqInviteLog.getInviterCommission());
//			}
//			inviteFriends.setCreateDate(lqInviteLog.getCreatedTime());
//			
//			/**
//			 * 受邀人
//			 */
//			Long invitee=lqInviteLog.getInvitee();
//			LqMemberBO lqMemberBO= lqMemberService.getByMemberId(invitee);
//			String uuid=lqMemberBO.getUuid();
//			MemberBO memberBO =memberService.getMember(uuid);
//			inviteFriends.setWxname(memberBO.getWxname());
//			inviteFriends.setHeadimgurl(memberBO.getHeadimgurl());
//			InviteFriendsList.add(inviteFriends);
//		}
		return inviteFriendsList;
	}

}
