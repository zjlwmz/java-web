package cn.emay.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.sql.SqlCallback;
import org.nutz.ioc.impl.PropertiesProxy;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.trans.Atom;
import org.nutz.trans.Trans;

import cn.emay.common.util.Unicode;
import cn.emay.model.Member;
import cn.emay.model.Student;
import cn.emay.model.consultant.PublishLearning;
import cn.emay.utils.Config;

@IocBean
public class MemberDao extends BasicDao{

	@Inject
	private PropertiesProxy custom;
	/**
	 * 获取学生列表
	 * @param memberId
	 * @return
	 */
	public List<Student> findStudentByMemberId(String memberId){
		Sql sql=Sqls.create("select s.* from s_member mm,s_student s,s_member_student ms where mm.id=ms.member_id and s.id=ms.student_id "+
				"and s.del_flag='0' and mm.del_flag='0' and mm.id=@memberId");
//		Sql sql=Sqls.create("select  s.id,s.student_name,s.school_id,s.birth,s.gender,s.grade_dict,s.headpicture_id,s.class_id,s.status from s_member mm,s_student s,s_member_student ms where mm.id=ms.member_id and s.id=ms.student_id "+
//					"and s.del_flag='0' and mm.del_flag='0' and mm.id=@memberId");
		sql.params().set("memberId",memberId);
		/*
		sql.setCallback(new SqlCallback() {
			public Object invoke(Connection conn, ResultSet rs, Sql sql) throws SQLException {
				List<Student> list = new LinkedList<Student>();
				while (rs.next()){
					Student student=new Student();
					list.add(rs.getString("name"));
					student.setStudentName(studentName);
					list.add(student);
				}
				return list;
			}
		});
		*/
		sql.setCallback(Sqls.callback.entities());
		sql.setEntity(dao.getEntity(Student.class));
		dao.execute(sql);
		return sql.getList(Student.class);
	}
	
	
	/**
	 * 学生保存
	 */
	public void saveStudent(final Student student){
		// Begin transaction		
		Trans.exec(new Atom(){
			public void run() {
				save(student);
				Sql sql=Sqls.create("insert into s_member_student (member_id,student_id) values (@memberId,@studentId)");
				sql.params().set("memberId", student.getMemberId());
				sql.params().set("studentId", student.getId());
				dao.execute(sql);
			}
		});
		// End transaction
	}
	
	
	
	/**
	 * 顾问发布的学习内容查找
	 */
	public List<PublishLearning> findPublishLearning(String areadId,String gradeCode){
		Sql sql=Sqls.create("select * from s_publish_learning pl where (pl.area_id=@areaId or pl.grade_code like @gradeCode) and pl.del_flag='0'");
		sql.params().set("areadId",areadId);
		sql.params().set("gradeCode","%"+gradeCode+"%");
		sql.setCallback(Sqls.callback.entities());
		sql.setEntity(dao.getEntity(PublishLearning.class));
		dao.execute(sql);
		return sql.getList(PublishLearning.class);
	}
	
	
	
	/**
	 * 查找家长根据孩子班级查找
	 * @param classId
	 */
	@SuppressWarnings("rawtypes")
	public List findMemberByClassId(String classId){
		String sqlstr="SELECT "+
						"mm.id, "+
						"mm.familyrelation,"+
					  "mm.name, "+
					  "mm.mobilephone ,"+
					  "ss.student_name"+
					" FROM "+
						"s_member mm, "+
						"s_student ss, "+
						"s_member_student ms "+
					"where  "+
					" mm.id = ms.member_id "+
					" AND ss.id = ms.student_id and class_id=@classId ";
		Sql sql=Sqls.create(sqlstr);
		sql.params().set("classId",classId);
		sql.setCallback(new SqlCallback() {
			public Object invoke(Connection conn, ResultSet rs, Sql sql) throws SQLException {
				List<Map<String,Object>> list = new LinkedList<Map<String,Object>>();
				while (rs.next()){
					Map<String,Object> map=new HashMap<String, Object>();
					map.put("id", rs.getString("id"));
					map.put("name", rs.getString("name"));
					map.put("mobilephone", rs.getString("mobilephone"));
					map.put("studentName", rs.getString("student_name"));
					String familyrelation=rs.getString("familyrelation");
					map.put("familyrelation", (familyrelation==null ? "" :familyrelation));
					
					 Record imuser=getHxUser(rs.getString("id"));
					 if(null!=imuser){
						 String server_ip=Unicode.fromEncodedUnicode(custom.get("server_ip"));
						String hxid="hxid"+imuser.getInt("id");
						String headUrl=imuser.getString("head_url")==null ? server_ip+"/"+Config.defaultAvatar : server_ip+"/"+imuser.getString("head_url");
						map.put("hxid", hxid);
						map.put("headUrl", headUrl);
						list.add(map);
					 }
				}
				return list;
			}
		});
		dao.execute(sql);
		return sql.getList(List.class);
	}
	
	
	
	
	/**
	 * 设置环信用户id
	 * @param userId
	 * @param userType 1家长、2顾问
	 */
	public Record saveHXUser(String userId,String userType){
		Record record=dao.fetch("s_user_hx", Cnd.where("user_id", "=", userId));
		if(null==record){
			dao.insert("s_user_hx", Chain.make("user_id", userId).add("user_type", userType));
			return record=dao.fetch("s_user_hx", Cnd.where("user_id", "=", userId));
		}else{
			return record;
		}
	}
	
	/**
	 * 删除环信用户记录
	 * @param userId
	 */
	public void deleteHXUser(String userId){
		String sqlstr="DELETE from s_user_hx where user_id='"+userId+"'";
		Sql sql=Sqls.create(sqlstr);
		dao.execute(sql);
	}
	
	
	/**
	 * 获取环信id
	 * @return
	 */
	public String getHxid(String userId){
		Record record=dao.fetch("s_user_hx", Cnd.where("user_id", "=", userId));
		if(null!=record){
			String hxid="hxid"+record.get("id");
			return hxid;
		}
		return null;
	}
	public Record getHxUser(String userId){
		Record record=dao.fetch("s_user_hx", Cnd.where("user_id", "=", userId));
		return record;
	}
	/**
	 * 环信用户对象
	 * @param hxid
	 * @return
	 */
	public Record getUseridByHxid(String hxid){
		try{
			String hxidNew=hxid=hxid.replace("hxid", "");
			Record record=dao.fetch("s_user_hx", Cnd.where("id", "=",Integer.parseInt(hxidNew)));
			return record;
		}catch (Exception e) {
			
		}
		return null;
	}
	
	
	/**
	 * 修改头像
	 */
	public int updateHeadImage(String userid,String headUrl){
		int result=dao.update("s_user_hx", Chain.make("head_url", headUrl), Cnd.where("user_id", "=", userid));
		return result;
	}
	
	
	
	/**
	 * 默认顾问--家长查询
	 * @return
	 */
	public List<Member> findMembereByDefaultAdviser(){
		String sqlstr="SELECT "+
							" * "+
						" FROM "+
							" ( "+
								" SELECT "+
									"sm.*, asm.member_id AS adsviser_member_id "+
								" FROM "+
									" s_member sm "+
								" LEFT JOIN s_adviser_service_member asm ON sm. ID = asm.member_id "+
							" ) s_member_adsviser where adsviser_member_id is null ";
		Sql sql=Sqls.create(sqlstr);
		sql.setCallback(Sqls.callback.entities());
		sql.setEntity(dao.getEntity(Member.class));
		dao.execute(sql);
		return sql.getList(Member.class);
	}
	
	/**
	 * 默认顾问--家长查询
	 * @return
	 */
	public List<Map<String,Object>> findMembereByDefaultAdviserForMap(){
		List<Member>list=findMembereByDefaultAdviser();
		List<Map<String,Object>>mapList=new ArrayList<Map<String,Object>>();
		for(Member member:list){
			Map<String,Object>mapFrind=new HashMap<String, Object>();
			mapFrind.put("userName", member.getName());
			mapFrind.put("mobilephone", member.getMobilephone());
			Record imuser=getHxUser(member.getId());
			if(null!=imuser){
				String server_ip=Unicode.fromEncodedUnicode(custom.get("server_ip"));
				String hxid="hxid"+imuser.getInt("id");
				String headUrl=imuser.getString("head_url")==null ? server_ip+"/"+Config.defaultAvatar : server_ip+"/"+imuser.getString("head_url");
				mapFrind.put("hxid", hxid);
				mapFrind.put("memberId", member.getId());
				mapFrind.put("headUrl", headUrl);
				mapList.add(mapFrind);
			 }
		}
		return mapList;
	}
	
	
	
	/**
	 * 顾问-家长列表
	 * @return
	 */
	public List<Member> findMembereByAdviser(String adviserId){
		String sqlstr="SELECT "+
							" * "+
						" FROM "+
							" ( "+
								" SELECT "+
									"sm.*, asm.member_id AS adsviser_member_id ,asm.adviser_id"+
								" FROM "+
									" s_member sm "+
								" LEFT JOIN s_adviser_service_member asm ON sm. ID = asm.member_id "+
							" ) s_member_adsviser where adsviser_member_id is not null and adviser_id=@adviserId";
		Sql sql=Sqls.create(sqlstr);
		sql.params().set("adviserId",adviserId);
		sql.setCallback(Sqls.callback.entities());
		sql.setEntity(dao.getEntity(Member.class));
		dao.execute(sql);
		return sql.getList(Member.class);
	}
	
	/**
	 *顾问--家长查询
	 * @return
	 */
	public List<Map<String,Object>> findMembereByAdviserForMap(String adviserId){
		List<Member>list=findMembereByAdviser(adviserId);
		List<Map<String,Object>>mapList=new ArrayList<Map<String,Object>>();
		for(Member member:list){
			Map<String,Object>mapFrind=new HashMap<String, Object>();
			mapFrind.put("userName", member.getName());
			mapFrind.put("mobilephone", member.getMobilephone());
			Record imuser=getHxUser(member.getId());
			if(null!=imuser){
				String server_ip=Unicode.fromEncodedUnicode(custom.get("server_ip"));
				String hxid="hxid"+imuser.getInt("id");
				String headUrl=imuser.getString("head_url")==null ? server_ip+"/"+Config.defaultAvatar : server_ip+"/"+imuser.getString("head_url");
				mapFrind.put("hxid", hxid);
				mapFrind.put("memberId", member.getId());
				mapFrind.put("headUrl", headUrl);
				mapList.add(mapFrind);
			 }
		}
		return mapList;
	}
	
	
	
	
}
