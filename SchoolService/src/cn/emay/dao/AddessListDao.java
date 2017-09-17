package cn.emay.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.sql.SqlCallback;
import org.nutz.ioc.impl.PropertiesProxy;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import cn.emay.common.util.Unicode;
import cn.emay.dto.Adviser;
import cn.emay.dto.Headmaster;
import cn.emay.utils.Config;

import com.google.common.collect.Lists;


/**
 * 通讯录Dao接口
 * @author zjlWm
 * @date 2015-06-18
 */
@IocBean
public class AddessListDao extends BasicDao{

	
	/**
	 * 会员Dao接口
	 */
	@Inject
	private MemberDao memberDao;
	
	@Inject
	private PropertiesProxy custom;
	
	/**
	 * 全部顾问列表
	 * @return
	 */
	public List<Adviser>getAdviserList(){
		String sqlstr="select u.id,u.name,u.mobile_phone from sys_user u where u.platform_type='1' and u.user_type='1' and u.del_flag='0'";
		Sql sql= Sqls.create(sqlstr);
		sql.setCallback(new SqlCallback() {
			@Override
			public Object invoke(Connection conn, ResultSet rs, Sql sql)
					throws SQLException {
				List<Adviser>list=Lists.newArrayList();
				while(rs.next()){
					Adviser adviser=new Adviser();
					adviser.setAdviserId(rs.getString("id"));
					adviser.setAdviserName(rs.getString("name"));
					adviser.setAdviserMobile(rs.getString("mobile_phone"));
					adviser.setHxid(memberDao.getHxid(adviser.getAdviserId()));
					list.add(adviser);
				}
				return list;
			}
		});
		dao.execute(sql);
		return sql.getList(Adviser.class);
	}
	
	/**
	 * 顾问列表
	 */
	public Adviser getAdviserByMemberId(String memberId){
		String sqlstr="select u.name as \"userName\",u.id as \"userId\",u.mobile_phone,u.gender,u.introduction,u.assistant_name,u.assistant_phone,u.assistant_sex,u.zan_number from s_member sm ,s_adviser_service_member ssm,sys_user u"+
							" where sm.id= ssm.member_id and ssm.adviser_id=u.id"+
							" and sm.del_flag='0' and u.del_flag='0'"+
							" and sm.id=@smid";
		Sql sql= Sqls.create(sqlstr);
		sql.params().set("smid", memberId);
		sql.setCallback(new SqlCallback() {
			
			@Override
			public Object invoke(Connection conn, ResultSet rs, Sql sql)
					throws SQLException {
				List<Adviser>list=Lists.newArrayList();
				while(rs.next()){
					Adviser adviser=new Adviser();
					adviser.setAdviserId(rs.getString("userId"));
					adviser.setAdviserName(rs.getString("userName"));
					adviser.setAdviserMobile(rs.getString("mobile_phone"));
					adviser.setGender(rs.getString("gender"));
					adviser.setIntroduction(rs.getString("introduction"));
					adviser.setAssistantPhone(rs.getString("assistant_phone"));
					adviser.setAssistantName(rs.getString("assistant_name"));
					adviser.setAssistantSex(rs.getString("assistant_sex"));
					Integer zanNumber=rs.getInt("zan_number");
					if(null==zanNumber)zanNumber=0;
					adviser.setZanNumber(zanNumber);
					Record imuser=memberDao.getHxUser(adviser.getAdviserId());
					if(null!=imuser){
						String hxid="hxid"+imuser.getInt("id");
						String server_ip=Unicode.fromEncodedUnicode(custom.get("server_ip"));
						String headUrl=imuser.getString("head_url")==null ? server_ip+"/"+Config.defaultAvatar : server_ip+"/"+imuser.getString("head_url");
						
						adviser.setHxid(hxid);
						adviser.setHeadUrl(headUrl);
						list.add(adviser);
					}
				}
				return list;
			} 
		});
		dao.execute(sql);
		List<Adviser>adviserList=sql.getList(Adviser.class);
		if(adviserList.size()>0){
			return adviserList.get(0);
		}
		return null;
	}
	
	
	
	/*
	SELECT 
					s.class_id,s.student_name,ct.name as tearcher_name,ct.id as tearcher_id,sm.mobilephone
				FROM
					s_student s,
					s_member sm,
					s_member_student ms,
					(
						SELECT
							ct.class_id,
							u.id,
							u.name
						FROM
							s_class_teacher ct join sys_user u ON
							ct.teacher_id=u.id 
           ) as ct
				WHERE
					s."id" = ms.student_id
				AND sm."id" = ms.member_id
				AND sm."id" = 'f33bc2a8da7c4d699e663677f8a32a3f'
			 and ct.class_id=s.class_id
	*/
	/**
	 * 班主任
	 * 
	 * 
	 */
	public List<Headmaster> headmaster(String memberId){
		/*
		String sqlstr="SELECT "+ 
					"s.class_id,s.student_name,ct.name as tearcher_name,ct.id as tearcher_id,sm.mobilephone "+
				"FROM "+ 
					"s_student s,"+ 
					"s_member sm, "+ 
					"s_member_student ms, "+ 
					"("+ 
						"SELECT "+ 
							"ct.class_id,"+ 
							"u.id, "+ 
							"u.name "+ 
						"FROM "+ 
							"s_class_teacher ct join sys_user u ON "+ 
							"ct.teacher_id=u.id "+  
           ") as ct "+ 
				"WHERE "+ 
					"s.id = ms.student_id "+ 
				"AND sm.id = ms.member_id "+ 
				"AND sm.id = @smid "+ 
			 "and ct.class_id=s.class_id";
		*/
		
		
		String sqlstr2="select  u.id as \"userId\",u.name as \"userName\",u.mobile_phone  from sys_user u where u.id in("+
					" select ct.teacher_id from s_class_teacher ct where ct.class_id in(select DISTINCT s.class_id from s_student s ,s_member sm,s_member_student ms "+
					" where s.\"id\"= ms.student_id and sm.\"id\"=ms.member_id"+
					" and sm.\"id\"=@smid))";
		
		
		Sql sql= Sqls.create(sqlstr2);
		sql.params().set("smid", memberId);
		sql.setCallback(new SqlCallback() {
			@Override
			public Object invoke(Connection conn, ResultSet rs, Sql sql)
					throws SQLException {
				 List<Headmaster> list=Lists.newArrayList();
				 while(rs.next()){
					 Headmaster headmaster=new Headmaster();
					 headmaster.setMobilePhone(rs.getString("mobile_phone"));
					 headmaster.setTearcherId(rs.getString("userId"));
					 headmaster.setTearcherName(rs.getString("userName"));
					 Record imuser=memberDao.getHxUser(headmaster.getTearcherId());
					 if(null!=imuser){
						 String hxid="hxid"+imuser.getInt("id");
						 String server_ip=Unicode.fromEncodedUnicode(custom.get("server_ip"));
						 String headUrl=imuser.getString("head_url")==null ? server_ip+"/"+Config.defaultAvatar : server_ip+"/"+imuser.getString("head_url");
						 headmaster.setHxid(hxid);
						 headmaster.setHeadUrl(headUrl);
						 list.add(headmaster);
					 }
				 }
				return list;
			}
		});
		dao.execute(sql);
		return sql.getList(Headmaster.class);
	}
	
	
	
	
	
	
	/**
	 * 顾问下的学生（家长）
	 */
}
