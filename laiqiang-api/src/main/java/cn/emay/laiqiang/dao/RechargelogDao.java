package cn.emay.laiqiang.dao;

import java.util.List;
import java.util.Map;

import cn.emay.laiqiang.common.persistence.CrudDao;
import cn.emay.laiqiang.common.persistence.annotation.MyBatisDao;
import cn.emay.laiqiang.entity.Rechargelog;


/**
 * 
 * @Title 流量充值订单DAO接口
 * @author zjlwm
 * @date 2016-12-15 下午4:34:45
 *
 */
@MyBatisDao
public interface RechargelogDao extends CrudDao<Rechargelog>{

	/**
	 * 流量充值订单记录
	 * @param params
	 * @return
	 */
	public List<Rechargelog>findRechargelogList(Map<String,Object>params);
	
	/**
	 *  流量充值订单发费总金额
	 * @param memberid
	 * @return
	 */
	public Long findRechargelogTotalMoney(Long memberid);
	
	
	
	/**
	 * 话费充值订单记录
	 * @param params
	 * @return
	 */
	public List<Rechargelog>findWxrechargeList(Map<String,Object>params);
	
	/**
	 *  话费充值订单发费总金额
	 * @param memberid
	 * @return
	 */
	public Long findWxrechargeTotalMoney(Long memberid);
	
	
	
	
}
