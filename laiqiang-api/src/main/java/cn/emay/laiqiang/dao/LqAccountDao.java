package cn.emay.laiqiang.dao;

import cn.emay.laiqiang.common.persistence.CrudDao;
import cn.emay.laiqiang.common.persistence.annotation.MyBatisDao;
import cn.emay.laiqiang.entity.LqAccount;


/**
 * 
 * @Title 会员资金账户DAO接口
 * @author zjlwm
 * @date 2016-12-16 下午1:09:53
 *
 */
@MyBatisDao
public interface LqAccountDao extends CrudDao<LqAccount>{

	public LqAccount getLqAccount(Long memberId);
}
