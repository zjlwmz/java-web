package cn.emay.laiqiang.dao;

import java.util.List;

import cn.emay.laiqiang.bo.LqAreaBO;
import cn.emay.laiqiang.common.persistence.CrudDao;
import cn.emay.laiqiang.common.persistence.annotation.MyBatisDao;


/**
 * @Title ����DAO�ӿ�
 * @author zjlwm
 * @date 2016-12-12 ����10:42:10
 *
 */
@MyBatisDao
public interface LqAreaDao extends CrudDao<LqAreaBO>{

	public List<LqAreaBO>findAreaByParentId(String parentId);
}
