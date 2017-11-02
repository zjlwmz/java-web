package cn.emay.laiqiang.dao;

import java.util.List;
import java.util.Map;

import cn.emay.laiqiang.common.persistence.CrudDao;
import cn.emay.laiqiang.common.persistence.annotation.MyBatisDao;

/**
 * 
 * @Title 系统DAO接口
 * @author zjlwm
 * @date 2016-12-19 下午4:53:51
 *
 */
@MyBatisDao
public interface SysDao  extends CrudDao<Object>{

	/**
	 * 会员个人未读消息条数
	 * @param params
	 * @return
	 */
	public Long findNoReadMessageCount(Map<String,Object>params);
	
	/**
	 * 已读群消息条数
	 * @param params
	 * @return
	 */
	public Long findReadMessagePushCount(Map<String,Object>params);
	
	
	
	/**
	 * 需要批量导入奖励的用户ID
	 */
	public List<Long>rewardExport();
	
	/** 
	 * 查询所有用户
	 * @return
	 */
	public List<Long>findAllLqMember();
	
}
