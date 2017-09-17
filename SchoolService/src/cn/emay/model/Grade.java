package cn.emay.model;

import java.util.List;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;
/**
 * 年级
 * @author zjlWm
 * @version 2015-04-10
 *
 */
@Table("s_grade")
public class Grade {

	/**
	 * 年级代码
	 */
	@Id
	@Column("grade_code")
	private Integer gradeCode;
	
	/**
	 * 教育层次（1：幼儿园；2：小学；3初中；4：高中）
	 */
	@Column("educational_level")
	private Integer educationalLevel;
	
	/**
	 * 年级名称
	 */
	@Column("grade_name")
	private String gradeName;

	@Column("del_flag")
	private String delFlag;
	
	
	@Column("sort")
	private Integer sort;
	
	private List<Classes> list;
	
	public Integer getGradeCode() {
		return gradeCode;
	}

	public void setGradeCode(Integer gradeCode) {
		this.gradeCode = gradeCode;
	}

	public Integer getEducationalLevel() {
		return educationalLevel;
	}

	public void setEducationalLevel(Integer educationalLevel) {
		this.educationalLevel = educationalLevel;
	}

	public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

	public List<Classes> getList() {
		return list;
	}

	public void setList(List<Classes> list) {
		this.list = list;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	
	
}
