package cn.emay.laiqiang.dao;

import java.util.List;
import java.util.Map;

import cn.emay.laiqiang.bo.MemberBO;
import cn.emay.laiqiang.common.persistence.CrudDao;
import cn.emay.laiqiang.common.persistence.annotation.MyBatisDao;


/**
 * ��ԱDAO�ӿ�
 * @author zjlwm
 * @date 2016-11-28
 */
@MyBatisDao
public interface MemberDao extends CrudDao<MemberBO>{

	public MemberBO get(String uuid);
	
	/**
	 * ����ID���һ�Ա
	 * @param id
	 * @return
	 */
	public MemberBO getById(Long id);
	/**
	 * ��������б�
	 * @param params
	 * @return
	 */
	public List<MemberBO>findMemberBOByMemberId(Map<String,Object>params);
	
}
