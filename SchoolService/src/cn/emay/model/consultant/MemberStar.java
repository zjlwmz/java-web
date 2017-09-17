/**
 * 
 */
package cn.emay.model.consultant;

import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

/**
 * 会员星级
 * @author zjlWm
 * @date
 */
@Table("s_member_star")
public class MemberStar {

	@Name
	private String id;
	
	/**
	 * 会员id
	 */
	@Column(value="member_id")
	private String memberId;
	
	/**
	 * 星级
	 */
	@Column(value="star_level")
	private String starLevel;
	
	/**
	 * 创建时间
	 */
	@Column(value="create_date")
	private Date createDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getStarLevel() {
		return starLevel;
	}

	public void setStarLevel(String starLevel) {
		this.starLevel = starLevel;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	
	
}
