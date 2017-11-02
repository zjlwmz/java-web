package cn.emay.laiqiang.service;

import java.util.List;

import cn.emay.laiqiang.dto.InviteFriendsDTO;
import cn.emay.laiqiang.entity.LqInviteLog;

/**
 * 
 * @Title 邀请日志service接口
 * @author zjlwm
 * @date 2016-12-20 下午4:44:17
 *
 */
public interface LqInviteLogService {

	/**
	 * 保存
	 * @param lqInviteLog
	 * @return
	 */
	public int insert(LqInviteLog lqInviteLog);
	
	
	
	/**
	 * 邀请日志分页查询
	 * @param memberId
	 * @param start
	 * @param end
	 * @return
	 */
	public List<InviteFriendsDTO>findLqInvitelogList(Long memberId,Long start,Long end);
	
	
}
