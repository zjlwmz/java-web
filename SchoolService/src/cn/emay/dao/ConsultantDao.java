package cn.emay.dao;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.impl.PropertiesProxy;
import org.nutz.ioc.loader.annotation.Inject;

import cn.emay.common.util.Unicode;
import cn.emay.model.consultant.PublishLearning;
import cn.emay.model.consultant.StudentRemind;


/**
 * 顾问DAO操作
 * @author zjlWm
 * @date 2015-06-18
 *
 */
public class ConsultantDao extends BasicDao{

	@Inject
	private PropertiesProxy custom;
	
	/**
	 * 学习发布内容查找
	 * @return
	 */
	public List<PublishLearning> findPublishLearningList(String createBy){
		List<PublishLearning> list= search(PublishLearning.class, Cnd.where("delFlag", "=", "0").and("createBy", "=", createBy));
		String server_ip=Unicode.fromEncodedUnicode(custom.get("server_ip"));
		for(PublishLearning publishLearning:list){
			publishLearning.setUrl(server_ip+"/school/wap/html/grade/publishLearning?id="+publishLearning.getId());
		}
		
		return list;
	}
	
	
	/**
	 * 发布学习查找
	 * @return
	 */
	public List<PublishLearning> findPublishLearningByWhere(String userId,List<String>schoolIdList,List<String>areaIdList,List<String>gradeList){
		String server_ip=Unicode.fromEncodedUnicode(custom.get("server_ip"));
		StringBuffer sqlbuffere=new StringBuffer();
		/**
		 * 设置范围为自己顾问发布的内容
		 */
		sqlbuffere.append("select pl.* from s_publish_learning pl where create_by='"+userId+"' and del_flag='0' ");
		/**
		 * 区域查找
		 */
		if(null!=areaIdList && areaIdList.size()>0){
			sqlbuffere.append(" and (");
			for(int i=0;i<areaIdList.size();i++){
				sqlbuffere.append(" area_id='"+areaIdList.get(i)+"' ");
				if(i>=0 && i<areaIdList.size()-1){
					sqlbuffere.append(" or ");
				}
			}
			sqlbuffere.append(" )");
		}
		
		
		
		/**
		 * 学校查找
		 */
		if(null!=schoolIdList && schoolIdList.size()>0){
			sqlbuffere.append(" and (");
			for(int i=0;i<schoolIdList.size();i++){
				sqlbuffere.append(" school_id='"+schoolIdList.get(i)+"' ");
				if(i>=0 && i<areaIdList.size()-1){
					sqlbuffere.append(" or ");
				}
			}
			sqlbuffere.append(" )");
		}
		
		/**
		 * 年级查找
		 */
		if(null!=gradeList && gradeList.size()>0){
			sqlbuffere.append(" or (");
			for(int i=0;i<gradeList.size();i++){
				sqlbuffere.append(" grade_code='"+gradeList.get(i)+"' ");
				if(i>=0 && i<areaIdList.size()-1){
					sqlbuffere.append(" or ");
				}
			}
			sqlbuffere.append(" )");
		}
		
		
		sqlbuffere.append(" order by create_date desc");
		
		Sql sql=Sqls.create(sqlbuffere.toString());
		sql.setCallback(Sqls.callback.entities());
		sql.setEntity(dao.getEntity(PublishLearning.class));
		dao.execute(sql);
		List<PublishLearning>list=sql.getList(PublishLearning.class);
		for(PublishLearning publishLearning:list){
			publishLearning.setUrl(server_ip+"/school/wap/html/grade/publishLearning?id="+publishLearning.getId());
		}
		return list;
	}
	
	
	
	
	/**顾问 所有提醒
	 * 
	
			SELECT
			sr.*
		FROM
			(
				SELECT
					ms.*
				FROM
					s_adviser_service_member asm,
					s_member_student ms
				WHERE
					asm.member_id = ms.member_id
				AND asm.adviser_id = 'ce168730205b4dd3ab0f7f68c1da5d95'
			) AS member_student,
			s_student_remind AS sr
		WHERE
			member_student.member_id = sr.member_id
	
	
	 * 查询
	 */
	public List<StudentRemind> findAllStudentRemind(String adviserId,String status){
		String sqlstr="SELECT "+
		" sr.* "+
		" FROM "+
		" ( "+
		" SELECT "+
		" ms.* "+
		" FROM "+
		" s_adviser_service_member asm, "+
		" s_member_student ms "+
		" WHERE "+
		" asm.member_id = ms.member_id "+
		" AND asm.adviser_id = '"+adviserId+"' "+
		" ) AS member_student, "+
		" s_student_remind AS sr "+
		" WHERE "+
		" member_student.member_id = sr.member_id  ";
		
		/**
		 * 状态筛选
		 */
		if(StringUtils.isNotBlank(status)){
			sqlstr+=" and sr.status='"+status+"'";
		}
		
		Sql sql=Sqls.create(sqlstr.toString());
		sql.setCallback(Sqls.callback.entities());
		sql.setEntity(dao.getEntity(StudentRemind.class));
		dao.execute(sql);
		List<StudentRemind>list=sql.getList(StudentRemind.class);
		
		return list;
		
	}
}
