package com.emay.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;

import com.emay.dao.BasicPostgresqlDao2;
import com.emay.dao.BasicSqlServerDao;
import com.emay.lnglatoffset.Offset;


@IocBean
public class OffsetController {
	static Logger logger = Logger.getLogger(OffsetController.class.getName());
	
	@Inject
	private BasicPostgresqlDao2 basicPostgresqlDao2;
	
	@Inject
	private BasicSqlServerDao basicSqlServerDao;
	
	public BasicPostgresqlDao2 getBasicPostgresqlDao2() {
		return basicPostgresqlDao2;
	}

	public void setBasicPostgresqlDao2(BasicPostgresqlDao2 basicPostgresqlDao2) {
		this.basicPostgresqlDao2 = basicPostgresqlDao2;
	}

	public BasicSqlServerDao getBasicSqlServerDao() {
		return basicSqlServerDao;
	}

	public void setBasicSqlServerDao(BasicSqlServerDao basicSqlServerDao) {
		this.basicSqlServerDao = basicSqlServerDao;
	}

	@At("/offset")
	@GET
	@Ok("json")
	public Map<String,Object> offset(){
		Map<String,Object>params=new HashMap<String,Object>();
		System.out.println("-----");
		Integer count=basicSqlServerDao.searchCount(Offset.class);
		Integer pageTotal=count/50000+1;
		System.out.println(count);
		Integer currentPage=224;//224
		while(currentPage<=pageTotal){
			try{
				List<Offset>offsetList=basicSqlServerDao.searchByPage(Offset.class, Cnd.where("1", "=", 1).desc("id"), currentPage, 50000);//50000
				currentPage++;
				System.out.println(offsetList.size());
				params.put("offsetListSize",offsetList.size());
				basicPostgresqlDao2.save(offsetList);
			}catch (Exception e) {
				logger.debug(currentPage+"插入异常", e);
				e.printStackTrace();
				params.put("currentpage", currentPage+"插入异常"+e.getMessage());
			}
		}
		params.put("status", "OK");
		
		return params;
	}
}
