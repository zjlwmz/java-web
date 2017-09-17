package cn.emay.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;


import cn.emay.dao.SchoolDao;
import cn.emay.model.Classes;
import cn.emay.model.Grade;
import cn.emay.model.School;

@IocBean
public class SchoolService {
	
	@Inject
	private SchoolDao schoolDao;
	
	public School saveSchool(School school){
		return schoolDao.save(school);
	}
	
	/**
	 * 返回学校名称
	 * @return
	 */
	public String findSchoolName(String id){
		if(StringUtils.isNotBlank(id)){
			School school=schoolDao.find(id, School.class);
			if(null!=school){
				return school.getSchoolName();
			}
		}
		return "";
	}
	
	/*
	 * 返回年级
	 */
	public String gradeDictName(Integer gradeCode){
		Grade grade=schoolDao.find(gradeCode,Grade.class);
		if(null!=grade){
			return grade.getGradeName();
		}
		return "";
	}
	
	/**
	 * 查找班级名称
	 * @param classId
	 * @return
	 */
	public String getClassName(String classId){
		if(StringUtils.isNotBlank(classId)){
			Classes classes=schoolDao.find(classId,Classes.class);
			if(null!=classes){
				return classes.getClassName();
			}
		}
		return "";
	}
	
	
	
	/**
	 * 查找学校
	 * @param areaId
	 * @return
	 */
	public List<School>findSchool(String areaId){
		String areaIdList[]=areaId.split(",");
		StringBuffer areadIdBuffere=new StringBuffer(); 
		for(String areaid:areaIdList){
			areadIdBuffere.append("'"+areaid+"',");
		}
		String idIn=areadIdBuffere.toString().substring(0,areadIdBuffere.toString().length()-1);
		return schoolDao.search(School.class, Cnd.where("delFlag", "=", "0").and("freezeFlag", "=", "0").and("areaId", "in", idIn));
	}
	
	/**
	 * 年级查找
	 * @return
	 */
	public List<Grade>findGrade(){
		return schoolDao.search(Grade.class, Cnd.where("delFlag", "=", "0").asc("sort"));
	}
	
	/**
	 * 查找班级
	 * @param schoolId
	 * @return
	 */
	public List<Classes>findClasses(Classes classes){
		return schoolDao.search(Classes.class, Cnd.where("delFlag", "=", "0").and("graduation_flag", "=", "0").and("schoolId", "=", classes.getSchoolId()).and("gradeCode", "=", classes.getGradeCode()));
	}
}
