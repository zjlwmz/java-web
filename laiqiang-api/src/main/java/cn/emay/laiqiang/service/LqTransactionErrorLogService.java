package cn.emay.laiqiang.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.emay.laiqiang.dao.LqTransactionErrorLogDao;
import cn.emay.laiqiang.entity.LqTransactionErrorLog;

/**
 * 
 * @Title 
 * @author zjlwm
 * @date 2016-12-16 ÉÏÎç11:07:52
 *
 */
@Service
@Transactional(readOnly=true)
public class LqTransactionErrorLogService {

	@Autowired
	private LqTransactionErrorLogDao lqTransactionErrorLogDao;
	
	@Transactional(readOnly=false)
	public int insert(LqTransactionErrorLog lqTransactionErrorLog){
		return lqTransactionErrorLogDao.insert(lqTransactionErrorLog);
	}
}
