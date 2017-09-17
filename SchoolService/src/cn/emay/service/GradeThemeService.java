package cn.emay.service;

import java.util.List;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import cn.emay.dao.GradeThemeDao;
import cn.emay.model.GradeTheme;

/**
 * 年级service
 * @author zjlWm
 * @version 2015-07-07
 */
@IocBean
public class GradeThemeService {
	
	@Inject
	private GradeThemeDao gradeThemeDao;
	
	
	
	
	
	/**
	 * 年级主题推广查找
	 * @param gradeCode
	 * @return
	 */
	public List<GradeTheme>findGradeTheme(Integer gradeCode){
		return gradeThemeDao.findGradeTheme(gradeCode);
	}
}
