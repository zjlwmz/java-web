package cn.emay.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.dao.FieldFilter;
import org.nutz.dao.entity.Record;
import org.nutz.ioc.impl.PropertiesProxy;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.trans.Atom;
import org.nutz.trans.Trans;

import cn.emay.common.util.DateUtils;
import cn.emay.common.util.IdGen;
import cn.emay.common.util.Unicode;
import cn.emay.dao.MemberDao;
import cn.emay.model.Member;
import cn.emay.model.MemberPraiseAdviser;
import cn.emay.model.School;
import cn.emay.model.SmsLog;
import cn.emay.model.Student;
import cn.emay.model.SysUser;
import cn.emay.model.consultant.MemberStar;
import cn.emay.utils.Config;

@IocBean
public class MemberService {

	@Inject
	private MemberDao memberDao;
	
	
	@Inject
	private PropertiesProxy custom;
	
	
	/**
	 * 根据会员id查找会员
	 * @param memberId
	 * @return
	 */
	public Member find(String memberId){
		return memberDao.find(memberId, Member.class);
	}
	
	/**
	 * 查询所有会员信息
	 * @return
	 */
	public List<Member>findList(){
		return memberDao.search(Member.class, Cnd.where("delFlag", "=", "0"));
	}
	
	
	/**
	 * 会员信息更新
	 * @param member
	 */
	public void updateMember(final Member member){
		FieldFilter.create(Member.class, true).run(new Atom(){
		    public void run(){
		    	memberDao.update(member);
		    }
		});
	}
	
	/**
	 * 更新会员密码
	 * @param member
	 */
	public void updatePassword(String salt,String password,String mobilephone){
		memberDao.update(Member.class, Chain.make("salt", salt).add("password", password), Cnd.where("mobilephone", "=", mobilephone));
	}
//	/**
//	 * 设置短信
//	 * @param memberId
//	 * @param sms
//	 * @return
//	 */
//	public boolean setSMS(String memberId,String sms){
//		return memberDao.update(Member.class, Chain.make("sms",sms), Cnd.where("id", "=", memberId));
//	}
	
	
	/**
	 * 用户注册所用密码、密码找回
	 * @param mobilephone
	 * @param smscode
	 * @return
	 */
	public boolean validateSMS(String mobilephone,String smscode){
		String timestr=custom.get("time");
		if(timestr==null)timestr="5";
		int time=Integer.parseInt(timestr);
		SmsLog smsLog=memberDao.findByCondition(SmsLog.class, Cnd.where("mobilephone", "=", mobilephone).and("status", "=", "0").desc("sendDate"));
		if(null!=smsLog){
			Date sendms=smsLog.getSendDate();
			Date currentDate=new Date();
			/**
			 * 在设置的时间范围内有效
			 */
			if((currentDate.getTime()-sendms.getTime())<time*60*1000){
				String dSms=smsLog.getSmscode();
				if(smscode.equals(dSms)){
					return true;
				}
			}
		}
		return false;
	}
	
	
//	/**
//	 * 验证短信码
//	 * @param memberId
//	 * @param sms
//	 * @return
//	 */
//	public int validateSMS(String memberId,String sms){
//		return memberDao.searchCount(Member.class, Cnd.where("id", "=", memberId).and("sms", "=", sms));
//	}
	
	/**
	 * 修改用户状态
	 * @param memberId
	 * @param status
	 * @return
	 */
	public boolean updateMemberStatus(String memberId,String status){
		return memberDao.update(Member.class,Chain.make("status", status) ,Cnd.where("id", "=", memberId));
	}
	
	
	
	/**
	 * 学生查询
	 * @param memberId
	 * @return
	 */
	public List<Student>findStudentByMemberId(String memberId){
		return memberDao.findStudentByMemberId(memberId);
	}
	
	/**
	 * 查找学生
	 * @param studentId
	 * @return
	 */
	public Student findStudentById(String studentId){
		return memberDao.find(studentId, Student.class);
	}
	
	
	
	
	
	/**
	 * 学生保存
	 */
	public void saveStudent(final Student student){
		if(StringUtils.isBlank(student.getId())){
			student.setId(IdGen.uuid());
			memberDao.saveStudent(student);
		}else{
			FieldFilter.create(Student.class, true).run(new Atom(){
			    public void run(){
			    	memberDao.update(student);
			    }
			});
		}
		
	}
	
	/**
	 * 根据手机号码查找会员
	 * @param mobilephone
	 * @return
	 */
	public Member findMemberByMobilePhone(String mobilephone){
		Member member=memberDao.findByCondition(Member.class, Cnd.where("mobilephone", "=", mobilephone).and("delFlag", "=", "0"));
		memberDao.findLink(member, "studentList");
		return member;
	}
	
	public Member findMemberByMobilePhone2(String mobilephone){
		Member member=memberDao.findByCondition(Member.class, Cnd.where("mobilephone", "=", mobilephone).and("delFlag", "=", "0"));
		return member;
	}
	
	/**
	 * 会员基本信息
	 * @return
	 */
	public Map<String,Object>findSimpleMember(String mobilephone){
		Member member=memberDao.findByCondition(Member.class, Cnd.where("mobilephone", "=", mobilephone).and("delFlag", "=", "0"));
		if(null==member){
			return null;
		}
		Map<String,Object> memberMap=new HashMap<String, Object>();
		Record imuser=memberDao.getHxUser(member.getId());
		if(null!=imuser){
			String server_ip=Unicode.fromEncodedUnicode(custom.get("server_ip"));
			String hxid="hxid"+imuser.getInt("id");
			memberMap.put("hxid", hxid);
			String headUrl=imuser.getString("head_url");
			headUrl=(headUrl==null ? "" : headUrl);
			memberMap.put("headUrl",server_ip+"/"+headUrl);
			
			memberMap.put("id", member.getId());
			memberMap.put("name", member.getName());
			memberMap.put("mobilephone", member.getMobilephone());
			memberMap.put("familyrelation", (member.getFamilyrelation()==null ? "" : member.getFamilyrelation()));
			return memberMap;
		 }
		return null;
	}
	
	
	/**
	 * 会员完整信息
	 * @return
	 */
	public Map<String,Object>findALLMember(String mobilephone){
		Member member=memberDao.findByCondition(Member.class, Cnd.where("mobilephone", "=", mobilephone).and("delFlag", "=", "0"));
		if(null==member){
			return null;
		}
		Map<String,Object> memberMap=new HashMap<String, Object>();
		Record imuser=memberDao.getHxUser(member.getId());
		if(null!=imuser){
			String server_ip=Unicode.fromEncodedUnicode(custom.get("server_ip"));
			String hxid="hxid"+imuser.getInt("id");
			memberMap.put("hxid", hxid);
			String headUrl=imuser.getString("head_url");
			headUrl=(headUrl==null ? "" : headUrl);
			memberMap.put("headUrl",server_ip+"/"+headUrl);
			
			memberMap.put("id", member.getId());
			memberMap.put("name", member.getName());
			memberMap.put("password", member.getPassword());
			memberMap.put("salt", member.getSalt());
			memberMap.put("mobilephone", member.getMobilephone());
			memberMap.put("familyrelation", (member.getFamilyrelation()==null ? "" : member.getFamilyrelation()));
			return memberMap;
		 }
		return null;
	}
	
	/**
	 * 班主任、顾问查找
	 */
	public SysUser findSysUserByLoginName(String loginName){
		SysUser sysuser=memberDao.findByCondition(SysUser.class, Cnd.where("delFlag", "=", "0").and("loginName", "=", loginName));
		return sysuser;
	}
	public SysUser findSysUserById(String userId){
		SysUser sysuser=memberDao.findByCondition(SysUser.class, Cnd.where("delFlag", "=", "0").and("id", "=", userId));
		return sysuser;
	}
	
	/**
	 * 会员是否存在
	 * @return
	 */
	public boolean existsMember(String mobilephone){
		int count=memberDao.searchCount(Member.class, Cnd.where("mobilephone", "=", mobilephone).and("delFlag", "=", "0"));
		if(count>0){
			return true;
		}
		return false;
	}
	
	
	/**
	 * 当日短信发送数量
	 */
	public int sendSMSCount(String mobilephone){
		String currentDate=DateUtils.getDate();
		int count=memberDao.searchCount(SmsLog.class, Cnd.where("mobilephone", "=", mobilephone).and("to_char(send_date,'yyyy-MM-dd') ", "=", currentDate));
		return count;
	}
	
	
	
	/**
	 * 根据学生班级查找家长
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> findMemberByClassId(String classId){
		List<Map<String,Object>> list=memberDao.findMemberByClassId(classId);
		return list;
	}
	
	

	
	/**
	 * 获取环信id
	 * @param uerId
	 * @return
	 */
	public String getHxid(String uerId){
		return memberDao.getHxid(uerId);
	}
	/**
	 * 删除环信id
	 * @param userId
	 */
	public void deleteHxid(String userId){
		memberDao.deleteHXUser(userId);
	}
	/**
	 * 保存
	 * @param userId
	 * @param userType
	 */
	public Record saveHXUser(String userId,String userType){
		return  memberDao.saveHXUser(userId, userType);
	}
	
	
	public Record getHxUser(String userId){
		return memberDao.getHxUser(userId);
	}
	
	/**
	 * 根据hxid查找会员信息
	 * @param hxid
	 * @return
	 */
	public Map<String,Object>findMemberByHxid(String hxid){
		Record record=memberDao.getUseridByHxid(hxid);
		String server_ip=Unicode.fromEncodedUnicode(custom.get("server_ip"));
		if(null!=record){
			String userId=record.getString("user_id");
			Member member=memberDao.findByCondition(Member.class, Cnd.where("id", "=", userId));
			if(null!=member){
				Map<String,Object> memberMap=new HashMap<String, Object>();
				memberMap.put("id", member.getId());
				memberMap.put("name", member.getName());
				memberMap.put("userName", member.getName());
				memberMap.put("mobilephone", member.getMobilephone());
				
				Record imuser=memberDao.getHxUser(userId);
				if(null!=imuser){
					String headUrl=imuser.getString("head_url")==null ? server_ip+"/"+Config.defaultAvatar : server_ip+"/"+imuser.getString("head_url");
					memberMap.put("headUrl", headUrl);
					memberMap.put("hxid", hxid);
					String familyrelation=member.getFamilyrelation();
					memberMap.put("familyrelation", (familyrelation==null ? "" :familyrelation));
					return memberMap;
			   }
			   return null;
			}
		}
		return null;
	}
	
	
	/**
	 * 修改头像
	 * @param memberId
	 */
	public int modifyHeadImage(String userid,String headUrl){
		return memberDao.updateHeadImage(userid, headUrl);
	}
	
	
	/**
	 * 更新会员登录时间
	 * @param memberId
	 */
	public void updateLoginDate(String memberId){
		memberDao.update(Member.class, Chain.make("loginDate", new Date()), Cnd.where("id", "=", memberId));
	}
	
	
	/**
	 * 学校查找
	 * @param schoolId
	 * @return
	 */
	public School findSchool(String schoolId){
		return memberDao.findByCondition(School.class, Cnd.where("id", "=", schoolId));
	}
	
//	/**
//	 * 学校查找
//	 * @return
//	 */
//	public School findSchool(String id){
//		return memberDao.findByCondition(School.class, Cnd.where("id", "=", id));
//	}
//	
	
	/**
	 * 默认顾问的通讯录
	 * 没有绑定顾问的家长列表
	 */
	public List<Member>findMembereByDefaultAdviser(){
		return memberDao.findMembereByDefaultAdviser();
	}
	
	/**
	 * 默认顾问的通讯录
	 * 没有绑定顾问的家长列表
	 */
	public List<Map<String,Object>>findMembereByDefaultAdviserForMap(){
		return memberDao.findMembereByDefaultAdviserForMap();
	}
	
	
	/**
	 * 顾问的通讯录
	 * 已绑定顾问的家长列表
	 */
	public List<Member>findMembereByAdviser(String adviserId){
		return memberDao.findMembereByAdviser(adviserId);
	}
	
	/**
	 * 顾问的通讯录
	 * 已绑定顾问的家长列表
	 */
	public List<Map<String,Object>>findMembereByAdviserForMap(String adviserId){
		return memberDao.findMembereByAdviserForMap(adviserId);
	}
	
	
	
	
	
	/**
	 * 顾问点赞接口
	 * 0：顾问不存在，1成功、2失败、3已经点过
	 */
	public int praiseAdviser(final String adviserId,final String memberId){
		final SysUser user=memberDao.findByCondition(SysUser.class, Cnd.where("id", "=", adviserId));
		if(null==user){
			return 0;
		}
		String createDate=DateUtils.getDate();
		MemberPraiseAdviser memberPraiseAdviser=memberDao.findByCondition(MemberPraiseAdviser.class, Cnd.where("memberId", "=", memberId).and("adviserId", "=", adviserId).and("createDate", "=", createDate));
		if(null!=memberPraiseAdviser){
			return 3;
		}
		
		// Begin transaction		
		Trans.exec(new Atom(){
			public void run() {
				Integer zanNum=user.getZanNumber();
				if(zanNum==null)zanNum=0;
				user.setZanNumber(zanNum+1);
				memberDao.update(SysUser.class, Chain.make("zanNumber", zanNum+1),Cnd.where("id", "=", adviserId));
				MemberPraiseAdviser memberPraiseAdviserNew=new MemberPraiseAdviser();
				memberPraiseAdviserNew.setMemberId(memberId);
				memberPraiseAdviserNew.setAdviserId(adviserId);
				memberPraiseAdviserNew.setCreateDate(DateUtils.getDate());
				memberDao.save(memberPraiseAdviserNew);
			}
		});
		// End transaction
				
		return 1;
	}
	
	
	
	
	/**
	 * 查询家长对顾问点赞状态
	 */
	public MemberPraiseAdviser findMemberPraiseAdviser(String adviserId,String memberId){
		String createDate=DateUtils.getDate();
		MemberPraiseAdviser memberPraiseAdviser=memberDao.findByCondition(MemberPraiseAdviser.class, Cnd.where("memberId", "=", memberId).and("adviserId", "=", adviserId).and("createDate", "=", createDate));
		return memberPraiseAdviser;
	}
	
	
	
	
	
	
	
	/**
	 * 更新家长星级设置
	 */
	public boolean updateMemberStar(final String memberId,final String startlevel){
		Trans.exec(new Atom() {
			@Override
			public void run() {
				memberDao.update(Member.class, Chain.make("startlevel", startlevel).add("updateDate", new Date()), Cnd.where("id", "=", memberId));
				MemberStar memberStar=new MemberStar();
				memberStar.setId(IdGen.uuid());
				memberStar.setCreateDate(new Date());
				memberStar.setMemberId(memberId);
				memberStar.setStarLevel(startlevel);
				memberDao.save(memberStar);
			}
		});
		return true;
	}
	
	/**
	 * 家长星级列表查询
	 * @param memberId
	 * @return
	 */
	public List<MemberStar> findMemberStar(String memberId){
		return memberDao.search(MemberStar.class, Cnd.where("memberId", "=", memberId));
	}
	
}
