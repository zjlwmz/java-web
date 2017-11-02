package cn.emay.laiqiang.dao;

import java.util.List;
import java.util.Map;

import cn.emay.laiqiang.common.persistence.CrudDao;
import cn.emay.laiqiang.common.persistence.annotation.MyBatisDao;
import cn.emay.laiqiang.entity.Rechargebalance;


/**
 * @Title 话费套餐订单DAO接口
 * @author zjlwm
 * @date 2016-12-15 下午6:03:24
 *
 */
@MyBatisDao
public interface RechargebalanceDao extends CrudDao<Rechargebalance>{

	public List<Rechargebalance>findList(Map<String,Object>params);
	
	
}
