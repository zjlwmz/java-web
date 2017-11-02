package cn.emay.laiqiang.dao;

import java.util.Map;

import cn.emay.laiqiang.bo.LqMemberBO;
import cn.emay.laiqiang.common.persistence.CrudDao;
import cn.emay.laiqiang.common.persistence.annotation.MyBatisDao;

/**
 * 
 * @Title 会员appDAO接口
 * @author zjlwm
 * @date 2016-12-14 上午11:17:20
 *
 */
@MyBatisDao
public interface LqMemberDao extends CrudDao<LqMemberBO>{

	public LqMemberBO getByMemberId(Long memberId);
	
	public LqMemberBO getByUuid(String uuid);
	
	public LqMemberBO getByUnionid(String unionid);
	
	public long getinvitedFriends(Long memberId);
	
	/**
	 * 更新密码
	 * @param lqMemberBO
	 * @return
	 */
	public int updatePassword(LqMemberBO lqMemberBO);
	
	
	
	/**
	 * 设置邀请人
	 */
	public int updateInviter(LqMemberBO lqMemberBO);
	
	

	/**
	 * 查找邀请总用户数
	 * @param inviter
	 * @return
	 */
	public Long findInvitationNumber(Map<String,Object>params);
	
	
	/**
	 * 更新App端成功邀请人数
	 * @param lqMemberBO
	 * @return
	 */
	public int updateInvitationNumber(LqMemberBO lqMemberBO);
	
	
	
	/**
	 * 根据邀请码查询
	 */
	public LqMemberBO getByInvitationCode(String invitationCode);
	
	
	/**
	 * 更新激光推送用户ID
	 * @param lqMemberBO
	 * @return
	 */
	public int updatePushId(LqMemberBO lqMemberBO);
}
