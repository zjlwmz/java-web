/**
 * 
 */
package cn.emay.model;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

/**
 * 家长对顾问点赞
 * @author zjlWm
 * @date 2015-09-18
 */
@Table("member_praise_adviser")
public class MemberPraiseAdviser {

	@Column("member_id")
	private String memberId;
	
	@Column("adviser_id")
	private String adviserId;
	
	@Column("create_date")
	private String createDate;

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getAdviserId() {
		return adviserId;
	}

	public void setAdviserId(String adviserId) {
		this.adviserId = adviserId;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	
	
	
	
}
