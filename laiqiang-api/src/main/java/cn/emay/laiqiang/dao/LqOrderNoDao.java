package cn.emay.laiqiang.dao;

import cn.emay.laiqiang.bo.LqOrderNoBO;
import cn.emay.laiqiang.common.persistence.CrudDao;
import cn.emay.laiqiang.common.persistence.annotation.MyBatisDao;


/**
 * @Title  订单编号DAO接口
 * @author zjlwm
 * @date 2016-12-14 下午4:01:41
 *
 */
@MyBatisDao
public interface LqOrderNoDao extends CrudDao<LqOrderNoBO>{
	
//	public int insert(LqOrderNoBO lqOrderNoBO);
//	
//	public int update(LqOrderNoBO lqOrderNoBO);
	
	public LqOrderNoBO findLqOrderNoBOByFdate(String fdate);
	/**
	 * 更新当前时间序列
	 * @param fdate
	 * @return
	 */
	public int updateSerialNumber(String fdate);
	
	
	
}
