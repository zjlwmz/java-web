package cn.geofound.service.impl;

import java.util.List;


import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import cn.geofound.dao.BasicDao;
import cn.geofound.domain.PhenoloyGlobalSum;
import cn.geofound.service.PhenoloyGlobalSumService;


/**
 * 物候图service接口实现
 * @author zhangjialu
 * @date 2017-10-1 下午9:32:50 
 * @Description
 */
@IocBean
public class PhenoloyGlobalSumServiceImpl implements PhenoloyGlobalSumService {

	@Inject
	private BasicDao basicDao;
	
	@Override
	public List<PhenoloyGlobalSum> findPhenoloyGlobalSumByCrops(String crops,String type) {
		return basicDao.search(PhenoloyGlobalSum.class, Cnd.where("crops", "=", crops).and("botany", "=", type));
	}

}
