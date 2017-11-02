package cn.emay.laiqiang.dao;

import cn.emay.laiqiang.common.persistence.CrudDao;
import cn.emay.laiqiang.common.persistence.annotation.MyBatisDao;
import cn.emay.laiqiang.entity.LqTransactionErrorLog;


/**
 * @Title 交易异常日志表DAO接口
 * @author zjlwm
 * @date 2016-12-16 上午11:03:40
 *
 */
@MyBatisDao
public interface LqTransactionErrorLogDao extends CrudDao<LqTransactionErrorLog>{

}
