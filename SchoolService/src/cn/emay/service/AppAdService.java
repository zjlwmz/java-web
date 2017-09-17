package cn.emay.service;

import java.util.List;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import cn.emay.dao.AppAdDao;
import cn.emay.model.AppAd;


/**
 * 广告接口
 * @author zjlWm
 * @date 2015-07-10
 */
@IocBean
public class AppAdService {

	/**
	 * 广告DAO接口
	 */
	@Inject
	private AppAdDao appAdDao;
	
	/**
	 * 
	 * @param adType 广告类型
	 * @param typeApp app类型
	 * @return
	 */
	public List<AppAd>findAppAdList(String adType,String typeApp){
		return appAdDao.findAppAdList(adType,typeApp);
	}
}
