package cn.geofound.service;

import java.util.List;

import cn.geofound.domain.PhenoloyGlobalSum;


/**
 * 物候图service接口
 * @author zhangjialu
 * @date 2017-10-1 下午9:32:30 
 * @Description
 */
public interface PhenoloyGlobalSumService {

	/**
	 * 
	 * @param crops
	 * @return
	 */
	public List<PhenoloyGlobalSum>findPhenoloyGlobalSumByCrops(String crops,String type);
	
}
