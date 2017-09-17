/**
 * 
 */
package cn.emay.model;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

/**
 * 积分规则
 * @author zjlWm
 * @date 2015-09-08
 */
@Table("s_integral_rule")
public class IntegralRule {
	
	/**
	 * 名称
	 */
	@Column("name")
	@Name
	private String name;
	
	/**
	 * 积分规则
	 */
	@Column("integral")
	private Integer integral;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getIntegral() {
		return integral;
	}

	public void setIntegral(Integer integral) {
		this.integral = integral;
	}
	
	
	
	
	
}
