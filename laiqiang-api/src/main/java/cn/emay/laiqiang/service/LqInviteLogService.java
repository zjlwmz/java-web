package cn.emay.laiqiang.service;

import java.util.List;

import cn.emay.laiqiang.dto.InviteFriendsDTO;
import cn.emay.laiqiang.entity.LqInviteLog;

/**
 * 
 * @Title ������־service�ӿ�
 * @author zjlwm
 * @date 2016-12-20 ����4:44:17
 *
 */
public interface LqInviteLogService {

	/**
	 * ����
	 * @param lqInviteLog
	 * @return
	 */
	public int insert(LqInviteLog lqInviteLog);
	
	
	
	/**
	 * ������־��ҳ��ѯ
	 * @param memberId
	 * @param start
	 * @param end
	 * @return
	 */
	public List<InviteFriendsDTO>findLqInvitelogList(Long memberId,Long start,Long end);
	
	
}
