package cn.emay.service;

import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import cn.emay.dao.AreaDao;
import cn.emay.model.Area;

/**
 * 区域service
 * @author zjlWm
 * @version 2015-04-08
 */
@IocBean
public class AreaService {

	@Inject
	private AreaDao areaDao;
	
	/**
	 * 查询省列表
	 * @return
	 */
	public List<Area>findProvinces(){
		return areaDao.search(Area.class, Cnd.where("delFlag", "=", "0").and("parentId", "=", "1").asc("code"));
	}
	
	/**
	 * 查询城市
	 * @param parentId
	 * @return
	 */
	public List<Area>findCitys(String parentId){
		return areaDao.search(Area.class, Cnd.where("delFlag", "=", "0").and("parentId", "=", parentId).asc("code"));
	}
}
