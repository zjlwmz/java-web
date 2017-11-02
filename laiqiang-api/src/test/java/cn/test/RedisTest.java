package cn.test;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import cn.emay.laiqiang.common.test.SpringTransactionalContextTests;
import cn.emay.laiqiang.service.BaseService;
import cn.emay.laiqiang.support.JedisKeyUtils;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;

/**
 * reidis DAO≤‚ ‘
 * @Title 
 * @author zjlwm
 * @date 2016-12-23 …œŒÁ11:14:49
 *
 */
@Transactional
@ContextConfiguration(locations = {"classpath*:/spring-context*.xml"}) 
public class RedisTest extends SpringTransactionalContextTests{

	@Autowired
	private BaseService baseService;
	
	@Autowired
	private DruidDataSource druidDataSource;
	
	
	/**
	 * redis sqlª∫¥Ê÷¥––≤‚ ‘
	 * @throws SQLException
	 */
	@Test
	public void appSqlTest() throws SQLException{
		System.out.println(druidDataSource);
		DruidPooledConnection connection=druidDataSource.getConnection();
		
		List<String>sqlList=baseService.jedisLists.lrange(JedisKeyUtils.mysqlExecuteSqL, 0, -1);
		for(String sql:sqlList){
			try{
				PreparedStatement ps=connection.prepareStatement(sql);
				ps.execute();
				baseService.jedisLists.lrem(JedisKeyUtils.mysqlExecuteSqL, 0, sql);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
