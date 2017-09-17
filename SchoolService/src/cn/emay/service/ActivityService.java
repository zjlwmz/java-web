package cn.emay.service;

import java.util.List;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import cn.emay.dao.ActivityDao;
import cn.emay.model.Activity;


/**
 * 年级service
 * @author zjlWm
 * @version 2015-07-07
 */
@IocBean
public class ActivityService {

	@Inject
	private ActivityDao activityDao;
	
	/**
	 * 年级主题推广查找
	 * @param gradeCode
	 * @return
	 */
	public List<Activity>findActivity(String gradeCode){
		return activityDao.findActivity(gradeCode);
	}
}
