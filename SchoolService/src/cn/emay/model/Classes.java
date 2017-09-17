package cn.emay.model;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;
/**
 * 班级
 * @author zjlWm
 * @version 2015-04-10
 */
@Table("s_class")
public class Classes {
	
	
	@Name
	@Column("id")
	private String id;
	
	
	/**
	 * 班级名称
	 */
	@Column("class_name")
	private String className;
	
	/**
	 * 年级代码
	 */
	@Column("grade_code")
	private Integer gradeCode;
	/**
	 * 班级人数
	 */
	@Column("students_number")
	private Integer studentsNumber;
	
	/**
	 * 入学年份
	 */
	@Column("startyear")
	private Integer startyear;
	
	/**
	 * 学校id
	 */
	@Column("school_id")
	private String schoolId;
	
	@Column("del_flag")
	private String delFlag;
	
	/**
	 * 结业标记(0:正常、1:结业)
	 */
	@Column("graduation_flag")
	private String graduationFlag;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Integer getGradeCode() {
		return gradeCode;
	}

	public void setGradeCode(Integer gradeCode) {
		this.gradeCode = gradeCode;
	}

	public Integer getStudentsNumber() {
		return studentsNumber;
	}

	public void setStudentsNumber(Integer studentsNumber) {
		this.studentsNumber = studentsNumber;
	}

	public Integer getStartyear() {
		return startyear;
	}

	public void setStartyear(Integer startyear) {
		this.startyear = startyear;
	}

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public String getGraduationFlag() {
		return graduationFlag;
	}

	public void setGraduationFlag(String graduationFlag) {
		this.graduationFlag = graduationFlag;
	}
	
	
	
}
