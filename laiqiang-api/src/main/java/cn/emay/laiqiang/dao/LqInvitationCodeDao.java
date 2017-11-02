package cn.emay.laiqiang.dao;

import cn.emay.laiqiang.bo.LqInvitationCodeBO;
import cn.emay.laiqiang.common.persistence.CrudDao;
import cn.emay.laiqiang.common.persistence.annotation.MyBatisDao;


/**
 * 
 * @Title ÑûÇëÂëDAO
 * @author zjlwm
 * @date 2016-12-14 ÉÏÎç9:55:49
 *
 */
@MyBatisDao
public interface LqInvitationCodeDao extends CrudDao<LqInvitationCodeBO>{

	/**
	 * »ñÈ¡ÑûÇëÂë
	 * @param memberId
	 * @return
	 */
	public LqInvitationCodeBO get(Long memberId);
}
