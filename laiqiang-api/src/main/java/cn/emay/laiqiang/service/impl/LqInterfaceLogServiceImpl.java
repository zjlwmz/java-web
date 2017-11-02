package cn.emay.laiqiang.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.emay.laiqiang.bo.LqInterfaceLogBO;
import cn.emay.laiqiang.common.utils.DateUtils;
import cn.emay.laiqiang.common.utils.RedisUtil;
import cn.emay.laiqiang.service.LqInterfaceLogService;
import cn.emay.laiqiang.support.JedisKeyUtils;

/**
 * 
 * @Title 接口调用日志service接口实现
 * @author zjlwm
 * @date 2016-12-29 下午5:00:55
 *
 */
@Service
public class LqInterfaceLogServiceImpl implements LqInterfaceLogService{

	
	
	
	/**
	 * 写数据redisDAO
	 */
	@Autowired
	private RedisUtil redisUtil;
	
	@Override
	public void insert(LqInterfaceLogBO lqInterfaceLogBO) {
		lqInterfaceLogBO.setCreatedTime(DateUtils.getDateTime());
		StringBuffer sqlBuf=new StringBuffer();
		sqlBuf.append("insert lq_interface_log (interface,description,error_message,created_time) values(");
		sqlBuf.append("'").append(lqInterfaceLogBO.getInterfaceName()).append("'").append(",");
		sqlBuf.append("'").append(lqInterfaceLogBO.getDescription()).append("'").append(",");
		sqlBuf.append("'").append(lqInterfaceLogBO.getErrorMessage()).append("'").append(",");
		sqlBuf.append("'").append(lqInterfaceLogBO.getCreatedTime()).append("'");
		sqlBuf.append(")");
		String sql=sqlBuf.toString();
		redisUtil.rpush(JedisKeyUtils.mysqlExecuteSqL, sql);
		
	}

}
