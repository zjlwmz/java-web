package cn.emay.laiqiang.dao;

import cn.emay.laiqiang.bo.LqInvitationCodeBO;
import cn.emay.laiqiang.common.persistence.CrudDao;
import cn.emay.laiqiang.common.persistence.annotation.MyBatisDao;


/**
 * 
 * @Title ������DAO
 * @author zjlwm
 * @date 2016-12-14 ����9:55:49
 *
 */
@MyBatisDao
public interface LqInvitationCodeDao extends CrudDao<LqInvitationCodeBO>{

	/**
	 * ��ȡ������
	 * @param memberId
	 * @return
	 */
	public LqInvitationCodeBO get(Long memberId);
}
