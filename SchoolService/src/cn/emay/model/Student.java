package cn.emay.model;

import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;


@Table("s_student")
public class Student {
	/**
	 * 
	 */
	@Name
	@Column("id")
	private String id;
	
	/**
	 * 学生姓名
	 */
	@Column("student_name")
	private String studentName;
	
	/**
	 * 学校id 
	 */
	@Column("school_id")
	private String schoolId;
	/**
	 * 学校名称
	 */
	private String schoolName;
	
	@Column("birth")
	private Date birth;
	
	/**
	 * 性别
	 */
	@Column("gender")
	private String gender;
	
	
	/**
	 * 年级
	 */
	@Column("grade_dict")
	private String gradeDict;
	
	private String gradeDictName;
	
	@Column("headpicture_id")
	private String headpictureId;
	
	
	/**
	 * 班级
	 */
	@Column("class_id")
	private String classId;
	
	
	private String className;
	
	
	/**
	 * 状态（1：未审核；2：已审核）
	 */
	@Column("status")
	private String status="1";
	
	@Column("create_date")
	private Date createDate;
	
	
	@Column("create_by")
	private String createBy;
	
	
	@Column("update_date")
	private Date updateDate;
	
	
	@Column("update_by")
	private String updateBy;


	@Column("del_flag")
	private String delFlag="0";
	
	private String memberId;
	
	public Student(){
		this.createDate=new Date();
		this.updateDate=this.createDate;
		this.createBy="1";
		this.updateBy=this.createBy;
	}
	
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getStudentName() {
		return studentName;
	}


	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	
	
	
	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getSchoolId() {
		return schoolId;
	}


	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}


	public Date getBirth() {
		return birth;
	}


	public void setBirth(Date birth) {
		this.birth = birth;
	}


	public String getGender() {
		return gender;
	}


	public void setGender(String gender) {
		this.gender = gender;
	}


	public String getGradeDict() {
		return gradeDict;
	}


	public void setGradeDict(String gradeDict) {
		this.gradeDict = gradeDict;
	}


	public String getHeadpictureId() {
		return headpictureId;
	}


	public void setHeadpictureId(String headpictureId) {
		this.headpictureId = headpictureId;
	}


	public String getClassId() {
		return classId;
	}


	public void setClassId(String classId) {
		this.classId = classId;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public Date getCreateDate() {
		return createDate;
	}


	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}


	public String getCreateBy() {
		return createBy;
	}


	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}


	public Date getUpdateDate() {
		return updateDate;
	}


	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}


	public String getUpdateBy() {
		return updateBy;
	}


	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}


	public String getDelFlag() {
		return delFlag;
	}


	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getGradeDictName() {
		return gradeDictName;
	}

	public void setGradeDictName(String gradeDictName) {
		this.gradeDictName = gradeDictName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
	
	
	
	
}
