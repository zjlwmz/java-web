package cn.emay.service;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import cn.emay.dao.StatisticsDao;
import cn.emay.model.AppStatistics;

/**
 * 区域service
 * @author zjlWm
 * @version 2015-05-18
 */
@IocBean
public class StatisticsService {
	
	@Inject
	private StatisticsDao statisticsDao;
	
	/*
	 * 统计保持
	 */
	public void saveStatistics(AppStatistics appStatistics){
		statisticsDao.save(appStatistics);
	}
}
