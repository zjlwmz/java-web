package cn.emay.laiqiang.dao;

import java.util.List;
import java.util.Map;

import cn.emay.laiqiang.bo.MemberBO;
import cn.emay.laiqiang.common.persistence.CrudDao;
import cn.emay.laiqiang.common.persistence.annotation.MyBatisDao;


/**
 * 会员DAO接口
 * @author zjlwm
 * @date 2016-11-28
 */
@MyBatisDao
public interface MemberDao extends CrudDao<MemberBO>{

	public MemberBO get(String uuid);
	
	/**
	 * 根据ID查找会员
	 * @param id
	 * @return
	 */
	public MemberBO getById(Long id);
	/**
	 * 邀请好友列表
	 * @param params
	 * @return
	 */
	public List<MemberBO>findMemberBOByMemberId(Map<String,Object>params);
	
}
