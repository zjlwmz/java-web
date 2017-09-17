package cn.emay.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.ManyMany;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

/**
* 注册用户信息(会员表,家长表)
*/
@Table("s_member")
public class Member {

	/**
	 * 
	 */
	@Name
	@Column("id")
	private String id;
	/**
	 * 姓名
	 */
	@Column("name")
	private String name;
	/**
	 * 手机号码
	 */
	@Column("mobilephone")
	private String mobilephone;
	
	/**
	 * 家庭亲属关系
	 */
	@Column("familyrelation")
	private String familyrelation;
	
	/**
	 * 环信id
	 */
	private String hxid;
	
	
	/**
	 * 
	 */
	@Column("create_by")
	private String createBy;
	/**
	 * 创建时间
	 */
	@Column("create_date")
	private java.util.Date createDate;
	/**
	 * 
	 */
	@Column("update_by")
	private String updateBy;
	/**
	 * 
	 */
	@Column("update_date")
	private java.util.Date updateDate;
	/**
	 * 
	 */
	@Column("remarks")
	private String remarks;
	/**
	 * 区域Id(通过区域Id确定官网信息来源）
	 */
	@Column("area_id")
	private String areaId;
	/**
	 * 是否冻结（0：未冻结；1：已冻结）
	 */
	@Column("freeze_flag")
	private String freezeFlag="0";
	/**
	 * 积分
	 */
	@Column("integral")
	private Integer integral;
	/**
	 * 是否允许评论（0：不允许；1：允许）
	 */
	@Column("can_pinglun")
	private String canPinglun="0";
	/**
	 * 家长等级（1，2，3，4，5）
	 */
	@Column("level")
	private String level="0";
	/**
	 * 
	 */
	@Column("del_flag")
	private String delFlag="0";
	/**
	 * 会员用户状态(1:临时；2:未验证；3:正式会员)
	 */
	@Column("status")
	private String status="1";
	
	@Column("password")
	private String password;
	
	@Column("salt")
	private String salt;
	
	@Column("sms")
	private String sms;
	
	@Column("login_date")
	private Date loginDate;
	
	/**
	 * 星级设置
	 * 1,2,3,4,5 
	 */
	@Column(value="startlevel")
	private String startlevel;
	
	@ManyMany(target = Student.class, relation = "s_member_student", from = "member_id", to = "student_id")
	private List<Student> studentList=new ArrayList<Student>();
	
	
	public Member(){
		this.createDate=new Date();
		this.updateDate=new Date();
		this.createBy="1";
		this.updateBy="1";
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobilephone() {
		return mobilephone;
	}
	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public java.util.Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public java.util.Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(java.util.Date updateDate) {
		this.updateDate = updateDate;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getAreaId() {
		return areaId;
	}
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	public String getFreezeFlag() {
		return freezeFlag;
	}
	public void setFreezeFlag(String freezeFlag) {
		this.freezeFlag = freezeFlag;
	}
	public Integer getIntegral() {
		return integral;
	}
	public void setIntegral(Integer integral) {
		this.integral = integral;
	}
	public String getCanPinglun() {
		return canPinglun;
	}
	public void setCanPinglun(String canPinglun) {
		this.canPinglun = canPinglun;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<Student> getStudentList() {
		return studentList;
	}
	public void setStudentList(List<Student> studentList) {
		this.studentList = studentList;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSms() {
		return sms;
	}

	public void setSms(String sms) {
		this.sms = sms;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getHxid() {
		return hxid;
	}

	public void setHxid(String hxid) {
		this.hxid = hxid;
	}

	public String getFamilyrelation() {
		return familyrelation;
	}

	public void setFamilyrelation(String familyrelation) {
		this.familyrelation = familyrelation;
	}

	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	public String getStartlevel() {
		return startlevel;
	}

	public void setStartlevel(String startlevel) {
		this.startlevel = startlevel;
	}
	
	
	
}