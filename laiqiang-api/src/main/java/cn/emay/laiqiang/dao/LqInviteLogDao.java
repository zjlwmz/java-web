package cn.emay.laiqiang.dao;

import java.util.List;
import java.util.Map;

import cn.emay.laiqiang.common.persistence.CrudDao;
import cn.emay.laiqiang.common.persistence.annotation.MyBatisDao;
import cn.emay.laiqiang.entity.LqInviteLog;


/**
 * 
 * @Title 邀请日志DAO接口
 * @author zjlwm
 * @date 2016-12-20 下午4:38:23
 *
 */
@MyBatisDao
public interface LqInviteLogDao extends CrudDao<LqInviteLog>{

	/**
	 * 邀请好友列表分页查询
	 * @param params
	 * @return
	 */
	public List<LqInviteLog>findLqInvitelogList(Map<String,Object>params);
	
	
	
}
