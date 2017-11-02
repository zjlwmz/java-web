package cn.emay.laiqiang.dao;

import java.util.Map;

import cn.emay.laiqiang.bo.LqMemberBO;
import cn.emay.laiqiang.common.persistence.CrudDao;
import cn.emay.laiqiang.common.persistence.annotation.MyBatisDao;

/**
 * 
 * @Title ��ԱappDAO�ӿ�
 * @author zjlwm
 * @date 2016-12-14 ����11:17:20
 *
 */
@MyBatisDao
public interface LqMemberDao extends CrudDao<LqMemberBO>{

	public LqMemberBO getByMemberId(Long memberId);
	
	public LqMemberBO getByUuid(String uuid);
	
	public LqMemberBO getByUnionid(String unionid);
	
	public long getinvitedFriends(Long memberId);
	
	/**
	 * ��������
	 * @param lqMemberBO
	 * @return
	 */
	public int updatePassword(LqMemberBO lqMemberBO);
	
	
	
	/**
	 * ����������
	 */
	public int updateInviter(LqMemberBO lqMemberBO);
	
	

	/**
	 * �����������û���
	 * @param inviter
	 * @return
	 */
	public Long findInvitationNumber(Map<String,Object>params);
	
	
	/**
	 * ����App�˳ɹ���������
	 * @param lqMemberBO
	 * @return
	 */
	public int updateInvitationNumber(LqMemberBO lqMemberBO);
	
	
	
	/**
	 * �����������ѯ
	 */
	public LqMemberBO getByInvitationCode(String invitationCode);
	
	
	/**
	 * ���¼��������û�ID
	 * @param lqMemberBO
	 * @return
	 */
	public int updatePushId(LqMemberBO lqMemberBO);
}
