package cn.emay.dao;

import java.util.List;

import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.impl.PropertiesProxy;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import cn.emay.common.util.DateUtils;
import cn.emay.common.util.Unicode;
import cn.emay.model.GradeTheme;

/**
 * 年级主题DAO接口
 * @author zjlWm
 * @version 2015-07-07
 */
@IocBean
public class GradeThemeDao extends BasicDao{

	@Inject
	private PropertiesProxy custom;
	/**
	 * 年级主题推广查找
	 * @param gradeCode
	 * @return
	 */
	public List<GradeTheme>findGradeTheme(Integer gradeCode){
		String server_ip=Unicode.fromEncodedUnicode(custom.get("server_ip"));
		String currentDate=DateUtils.getDate("yyyy-MM-dd");
		String url=server_ip+"/school/wap/html/grade/theme?id=";
		String sqlstr="SELECT "+
						"cg.id, "+
						"cg.title, "+
						"cg.grade_code, "+
						"cg.create_date, "+
						"cg.picture_id, "+
						"cg.discription, "+
						"concat ('"+server_ip+"', sp.url) AS picture_url, "+
						"concat ('"+url+"', cg.id) AS url "+
					"FROM "+
						"c_gradeactivity cg "+
					"JOIN sys_picture sp ON cg.picture_id = sp. ID "+
					"where cg.del_flag='0' and  "+
					"cg.del_flag='0' AND cg.grade_code like '%,"+gradeCode+",%' AND "+
					 "to_char(cg.publish_startdate,'yyyy-MM-dd') <= '"+currentDate+"' "+
					 "AND to_char(cg.publish_enddate,'yyyy-MM-dd') >= '"+currentDate+"' ORDER BY cg.create_date DESC ";
		System.out.println(sqlstr);
		Sql sql= Sqls.create(sqlstr);
		sql.setCallback(Sqls.callback.entities());
		sql.setEntity(dao.getEntity(GradeTheme.class));
		dao.execute(sql);
		List<GradeTheme> list = sql.getList(GradeTheme.class);
		return list;
		
		/*
		Condition cnd=Cnd.where("delFlag", "=", "0").and("gradeCode", "=", gradeCode)
				.and("to_char(publish_startdate,'yyyy-MM-dd')",">=",currentDate)
				.and("to_char(publish_enddate,'yyyy-MM-dd')","<=",currentDate)
				.desc("createDate");
		return gradeThemeDao.search(GradeTheme.class, cnd);
		*/
	}
}
